package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.primitives.Bytes;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisUtils;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.exception.ObjectRetrievalException;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.ServiceSecurityException.SecurityExceptionType;
import net.gdface.facelog.service.Token.TokenType;
import net.gdface.utils.FaceUtilits;

/**
 * 令牌管理模块
 * @author guyadong
 *
 */
class TokenMangement implements ServiceConstant {
	private static final String ACK_PREFIX = "ack_";
	private final BaseDao dao;
	/**  {@code 设备ID -> token} 映射表 */
	private final RedisTable<Token> deviceTokenTable;
	/**  {@code 人员ID -> token} 映射表 */
	private final RedisTable<Token> personTokenTable;
	/** {@code cmd sn -> 人员ID} */
	private RedisTable<Integer> cmdSnTable;
	/** {@code channel -> 人员ID} */
	private RedisTable<Integer> ackChannelTable;
	/** 是否执行设备令牌验证 */
	private final boolean validateDeviceToken;
	/** 是否执行人员令牌验证 */
	private final boolean validatePersonToken;
	/** 人员令牌失效时间(分钟) */
	private final int personTokenExpire;
	/** password加密盐值 */
	private final String salt;
	/** 是否拒绝普通人员申请令牌 */
	private final boolean rejectZero;
	/**
	 * @param dao
	 */
	TokenMangement(BaseDao dao) {
		this.dao = checkNotNull(dao,"dao is null");
		this.salt = CONFIG.getString(TOKEN_SALT);
		this.rejectZero = CONFIG.getBoolean(TOKEN_PERSON_REJECTZERO);
		this.validateDeviceToken = CONFIG.getBoolean(TOKEN_DEVICE_VALIDATE);
		this.validatePersonToken = CONFIG.getBoolean(TOKEN_PERSON_VALIDATE);
		this.personTokenExpire =CONFIG.getInt(TOKEN_PERSON_EXPIRE);
		this.deviceTokenTable =  RedisFactory.getTable(TABLE_DEVICE_TOKEN, JedisPoolLazy.getDefaultInstance());
		this.personTokenTable =  RedisFactory.getTable(TABLE_PERSON_TOKEN, JedisPoolLazy.getDefaultInstance());
		this.deviceTokenTable.setKeyHelper(Token.KEY_HELPER);
		this.personTokenTable.setKeyHelper(Token.KEY_HELPER);
		this.personTokenTable.setExpire(personTokenExpire, TimeUnit.MINUTES);
		this.cmdSnTable =  RedisFactory.getTable(TABLE_CMD_SN, JedisPoolLazy.getDefaultInstance());
		this.ackChannelTable =  RedisFactory.getTable(TABLE_ACK_CHANNEL, JedisPoolLazy.getDefaultInstance());
		this.cmdSnTable.setExpire(CONFIG.getInt(TOKEN_CMD_SERIALNO_EXPIRE), TimeUnit.SECONDS);
		this.ackChannelTable.setExpire(CONFIG.getInt(TOKEN_CMD_ACKCHANNEL_EXPIRE), TimeUnit.SECONDS);
		GlobalConfig.logTokenParameters();
	}
	/** 验证MAC地址是否有效(HEX格式,12字符,无分隔符,不区分大小写) */
	protected static final boolean isValidMac(String mac){
		return !Strings.isNullOrEmpty(mac) && mac.matches("^[a-fA-F0-9]{12}$");
	}
	protected static final void checkValidMac(String mac) throws ServiceSecurityException{
		if(!isValidMac(mac)){
			throw new ServiceSecurityException(String.format("INVALID MAC:%s ", mac))
			.setType(SecurityExceptionType.INVALID_MAC);
		}
	}
	/** 验证序列号是否有效 */
	protected boolean isValidSerialNo(String sn){
		return true;
	}
	/** 序列号已经被占用则抛出异常 */
	protected void checkNotOccupiedSerialNo(String sn) throws ServiceSecurityException{
		DeviceBean bean = null == sn ? null : dao.daoGetDeviceByIndexSerialNo(sn);
		if(null != bean){
			throw new ServiceSecurityException(
					String.format("serian no:%s be occupied by device ID[%d] MAC[%s]", sn,bean.getId(),bean.getMac()))
					.setType(SecurityExceptionType.OCCUPIED_SN).setDeviceID(bean.getId());
		}			
	}
	/** 序列号无效则抛出异常 */
	protected void checkValidSerialNo(String sn) throws ServiceSecurityException{
		if(!isValidSerialNo(sn)){
			throw new ServiceSecurityException(String.format("INVALID serial number:%s", sn))
					.setType(SecurityExceptionType.INVALID_SN);
		}
	}
	/** 令牌操作 */
	public enum TokenOp {
		/** 未初始化 */UNINITIALIZED,
		/** 设备注册  */REGISTER,
		/** 设备注销 */UNREGISTER,
		/** 申请令牌 */APPLY,
		/** 释放令牌 */RELEASE,
		/** 验证令牌 */VALIDATE,
		/** 验证密码 */VALIDPWD;
		/** 指定为上下文{@link TokenContext}中的令牌操作类型 */
		public void asContextTokenOp(){
			TokenContext.getCurrentTokenContext().setTokenOp(this);
		}
	}

	/** 允许的令牌类型 */
	enum Enable{
		/** 允许所有令牌类型 */ALL,
		/** 只允许人员令牌 */PERSON_ONLY,
		/** 只允许设备令牌 */DEVICE_ONLY,
		/** 只允许root令牌 */ROOT_ONLY;
		
		boolean isValid(TokenMangement tm,Token token){
			TokenOp.VALIDATE.asContextTokenOp();
			switch(this){
			case PERSON_ONLY:
				return tm.isValidPersonToken(token) || tm.isValidRootToken(token);
			case DEVICE_ONLY:
				return tm.isValidDeviceToken(token);
			case ROOT_ONLY:
				return tm.isValidRootToken(token);
			case ALL:
				return tm.isValidPersonToken(token) || tm.isValidDeviceToken(token) || tm.isValidRootToken(token);
			default:
				return false;
			}
		}
		/** 
		 * 验证令牌是否有效,无效抛出异常
		 * @throws ServiceSecurityException 
		 */
		void check(TokenMangement tm,Token token) throws ServiceSecurityException{
			if(isValid(tm,token)){
				return;
			}
			StringBuffer message = new StringBuffer("INVALID TOKEN");
			if(null != token){
				switch(this){
				case PERSON_ONLY:
					message.append(",Person Token required");
					break;
				case DEVICE_ONLY:
					message.append(",Device Token required");
					break;
				case ROOT_ONLY:
					message.append(",root Token required");
					break;
				default:
					break;
				}
			}else{
				message.append(",null token");
			}
			throw new ServiceSecurityException(message.toString())
								.setType(SecurityExceptionType.INVALID_TOKEN);
		}
	}
	/** 验证设备令牌是否有效 */
	private boolean isValidDeviceToken(Token token){
		if(validateDeviceToken){
			return null == token ? false : token.equals(deviceTokenTable.get(Integer.toString(token.getId())));			
		}else{
			return true;
		}
	}
	/** 验证人员令牌是否有效 */
	private boolean isValidPersonToken(Token token){
		if(validatePersonToken){
			return null == token ? false : token.equals(personTokenTable.get(Integer.toString(token.getId())));
		}else{
			return true;
		}
	}
	/** 验证root令牌是否有效 */
	private boolean isValidRootToken(Token token){
		if(validatePersonToken){
			return isValidPersonToken(token) && TokenType.ROOT.equals(token.getType());
		}else{
			return true;
		}
	}
	/** 检查数据库是否存在指定的设备记录,没有则抛出异常{@link ServiceSecurityException} */
	protected void checkValidDeviceId(Integer deviceId) throws ServiceSecurityException{
		if(!this.dao.daoExistsDevice(deviceId)){
			throw new ServiceSecurityException(String.format("NOT EXISTS device %d", deviceId))
					.setType(SecurityExceptionType.INVALID_DEVICE_ID);
		}
	}
	private static Token makeToken(byte[] source){
		ByteBuffer buffer = ByteBuffer.wrap(new byte[8]);
		buffer.asLongBuffer().put(System.nanoTime());
		byte[] md5 = FaceUtilits.getMD5(Bytes.concat(checkNotNull(source),buffer.array()));
		ByteBuffer byteBuffers = ByteBuffer.wrap(md5);
		return new Token(byteBuffers.getLong(), byteBuffers.getLong()).asContextToken();
	}
	private static Token makeToken(Object ...objs){
		checkArgument(null != objs && 0 != objs.length,"objs must not be null or empty");
		StringBuffer buffer = new StringBuffer(64);
		for(Object obj :objs){
			buffer.append(obj);
		}
		return makeToken(buffer.toString().getBytes());
	}
	/**
	 * 计算设备令牌
	 * @param device 设备参数(包括设备ID,MAC地址,序列号)为{@code null}抛出异常
	 * @return 设备访问令牌
	 * @throws IllegalArgumentException 设备参数为{@code null}
	 */
	private static Token makeDeviceTokenOf(DeviceBean device){
		checkArgument(null != device,"device is null");
		checkArgument(
						null != device.getId() 
				&& 	null != device.getMac() 
				&& 	null != device.getSerialNo(),
				"null device argument(id,mac,serialNo)");
		return makeToken(device.getId(),device.getMac(),device.getSerialNo()).asDeviceToken(device.getId());
	}
	private static Token makePersonTokenOf(int personId){
		ByteBuffer buffer = ByteBuffer.wrap(new byte[8]);
		buffer.asLongBuffer().put(personId);
		return makeToken(buffer.array()).asPersonToken(personId);
	}
	private static Token makeRootToken(String password){
		ByteBuffer buffer = ByteBuffer.wrap(new byte[8]);
		buffer.asLongBuffer().put(System.currentTimeMillis());
		return makeToken(Bytes.concat(password.getBytes(),buffer.array())).asRootToken();
	}
	/**
	 * 从{@link #deviceTokenTable}删除指定设备的令牌
	 * @param deviceId
	 */
	private void removeDeviceTokenOf(int deviceId){
		deviceTokenTable.remove(Integer.toString(deviceId));
	}
	/**
	 * 从{@link #personTokenTable}删除指定人员的令牌
	 * @param personId
	 */
	private void removePersonTokenOf(int personId){
		personTokenTable.remove(Integer.toString(personId));
	}
	
	/**
	 * 设备注册
	 * @param newDevice
	 * @return
	 * @throws ServiceSecurityException
	 */
	protected DeviceBean registerDevice(DeviceBean newDevice)
			throws ServiceSecurityException{
		TokenOp.REGISTER.asContextTokenOp();
		checkArgument(null != newDevice,"deviceBean must not be null");
	    // 检查是否为新记录，
	    checkArgument(newDevice.isNew(),
	    		"for device registeration the 'newDevice' must be a new record,so the _isNew field must be true ");
	    // ID为自增长键，新记录ID字段不能指定，由数据库分配
	    checkArgument(
	    		!newDevice.isModified(net.gdface.facelog.db.Constant.FL_DEVICE_ID_ID) 
	    		|| Objects.equal(0,newDevice.getId()),
	    		"for device registeration the 'newDevice' must be a new record,so id field must be not be set or be zero");
		DeviceBean dmac = this.dao.daoGetDeviceByIndexMac(newDevice.getMac());
		DeviceBean dsn = this.dao.daoGetDeviceByIndexSerialNo(newDevice.getSerialNo());
		if(null !=dmac ){
			if(dmac.equals(dsn) || Objects.equal(newDevice.getSerialNo(),dmac.getSerialNo())){
				// 设备已经注册,序列号一致
				return dmac;
			}
			// 设备已经注册,序列号不一致
			if(isValidSerialNo(dmac.getSerialNo())){
				// 原序列号有效就返回原记录
				return dmac;
			}else{
				checkNotOccupiedSerialNo(newDevice.getSerialNo());
				checkValidSerialNo(newDevice.getSerialNo());
				// 用新序列号替换原记录中无效的序列号
				dmac.setSerialNo(newDevice.getSerialNo());
				return dao.daoSaveDevice(dmac);
			}
		}else{
			checkNotOccupiedSerialNo(newDevice.getSerialNo());
			checkValidSerialNo(newDevice.getSerialNo());
			return this.dao.daoSaveDevice(newDevice);
		}
	}
	/**
	 * 设备注销
	 * @param deviceId
	 * @param token
	 * @throws ServiceSecurityException
	 */
	protected void unregisterDevice(int deviceId,Token token)
			throws ServiceSecurityException{
		TokenOp.UNREGISTER.asContextTokenOp();
		Enable.DEVICE_ONLY.check(this, token);
		checkValidDeviceId(deviceId);
		this.dao.daoDeleteDevice(deviceId);
	}
	/**
	 * 申请设备令牌
	 * @param loginDevice 申请信息设备信息，必须提供{@code id, mac, serialNo}字段
	 * @return
	 * @throws ServiceSecurityException
	 */
	protected Token applyDeviceToken(DeviceBean loginDevice)
			throws ServiceSecurityException{
		TokenOp.APPLY.asContextTokenOp();
		checkValidDeviceId(loginDevice.getId());

		DeviceBean device = dao.daoGetDevice(loginDevice.getId());
		if(!Objects.equal(device.getMac(), loginDevice.getMac()) ){
			throw new ServiceSecurityException(
					String.format("MISMATCH MAC:%s", device.getMac()))
				.setType(SecurityExceptionType.INVALID_MAC);
		}
		if(!Objects.equal(device.getSerialNo(), loginDevice.getSerialNo())){
			throw new ServiceSecurityException(
					String.format("MISMATCH Serial Number:%s", device.getSerialNo()))
				.setType(SecurityExceptionType.INVALID_SN);
		}
		// 生成一个新令牌
		Token token = makeDeviceTokenOf(device);
		deviceTokenTable.set(device.getId().toString(), token, false);
		return token;
	}
	/**
	 * 释放设备令牌
	 * @param token 当前持有的令牌
	 * @throws ServiceSecurityException
	 */
	protected void releaseDeviceToken(Token token)
			throws ServiceSecurityException{
		TokenOp.RELEASE.asContextTokenOp();;
		Enable.DEVICE_ONLY.check(this, token);
		removeDeviceTokenOf(token.getId());
	}
	/**
	 * 申请人员访问令牌
	 * @param personId
	 * @param password 
	 * @param isMd5 
	 * @return
	 * @throws ServiceSecurityException
	 * @see {@link #checkValidPassword(String, String, boolean)}
	 */
	protected Token applyPersonToken(int personId, String password, boolean isMd5)
			throws ServiceSecurityException{
		TokenOp.APPLY.asContextTokenOp();
		checkValidPassword(Integer.toString(personId), password, isMd5);
		if(CommonConstant.PersonRank.person.equals(CommonConstant.PersonRank.fromRank(dao.daoGetPerson(personId).getRank()))
			&&	rejectZero ){
			// 当配置参数指定不允许普通人员申请令牌时抛出异常
			throw new ServiceSecurityException(
					String.format("REJECTION OF APPLICATION for rank 0 user (id = %d)",personId))
				.setType(SecurityExceptionType.REJECT_APPLY);
		}
		Token token = makePersonTokenOf(personId);
		String key = Integer.toString(personId);
		personTokenTable.set(key, token, false);
		personTokenTable.expire(token);
		return token;
	}
	/**
	 * 释放人员访问令牌
	 * @param token 当前持有的令牌
	 * @throws ServiceSecurityException
	 */
	protected void releasePersonToken(Token token)
			throws ServiceSecurityException{
		TokenOp.RELEASE.asContextTokenOp();

		Enable.PERSON_ONLY.check(this, token);
		removePersonTokenOf(token.getId());
	}
	/**
	 * 申请root访问令牌
	 * @param password root密码
	 * @param isMd5 为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为32位MD5密文(小写)
	 * @return
	 * @throws ServiceSecurityException
	 */
	protected Token applyRootToken(String password, boolean isMd5)
			throws ServiceSecurityException{
		TokenOp.APPLY.asContextTokenOp();
		checkValidPassword(ROOT_NAME,password,isMd5);
		Token token = makeRootToken(password);
		String key = Integer.toString(token.getId());
		personTokenTable.set(key, token, false);
		personTokenTable.expire(token);
		return token;
	}
	/**
	 * 释放root访问令牌
	 * @param token 当前持有的令牌
	 * @throws ServiceSecurityException
	 */
	protected void releaseRootToken(Token token)
			throws ServiceSecurityException{
		TokenOp.RELEASE.asContextTokenOp();

		Enable.ROOT_ONLY.check(this, token);
		removePersonTokenOf(token.getId());
	}
	/**
	 * 根据盐值生成{@code password}的密文
	 * @param password
	 * @param isMd5 {@code password}是否为MD5,
	 * 						为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为MD5密文
	 * @return
	 */
	protected String generate(String password,boolean isMd5){
		// 避免 password为null
		password = String.valueOf(password);
		String passwordMd5 = isMd5 && FaceUtilits.validMd5(password) 
				? password
				: FaceUtilits.getMD5String(password.getBytes());
		StringBuffer buffer = new StringBuffer(passwordMd5.length() + salt.length());
		// 将盐值和password md5交替掺在一起形成一个新的字符串
		for(int i = 0,endIndex = Math.max(passwordMd5.length(), salt.length());i<endIndex;++i){
			try{
				buffer.append(salt.charAt(i));
			}catch(IndexOutOfBoundsException e){}
			try{
				buffer.append(passwordMd5.charAt(i));
			}catch(IndexOutOfBoundsException e){}
		}
		return FaceUtilits.getMD5String(buffer.toString().getBytes());
	}
	/**
	 * 验证用户密码是否匹配
	 * @param userId 用户id字符串,root用户id即为{@link CommonConstant#ROOT_NAME}
	 * @param password 用户密码
	 * @param isMd5 为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为32位MD5密文(小写)
	 * @return
	 * @throws RuntimeDaoException 
	 * @throws ServiceSecurityException 
	 * @throws IllegalArgumentException {@code userId} 无效
	 */
	protected boolean isValidPassword(String userId,String password, boolean isMd5) throws RuntimeDaoException, ServiceSecurityException {
		TokenOp.VALIDPWD.asContextTokenOp();
		checkArgument(!Strings.isNullOrEmpty(userId),"INVALID argument,must not be null or empty");
		if(ROOT_NAME.equals(userId)){
			// 从配置文件中读取root密码算出MD5与输入的密码比较
			return generate(CONFIG.getString(ROOT_PASSWORD),false).equals(generate(password,isMd5));
		}else{
			// 从数据库中读取用户密码(已经掺盐加密)与输入的密码比较
			try{
				Integer id = Integer.valueOf(userId);
				String passwordMd5InDb = dao.daoGetPersonChecked(id).getPassword();
				return generate(password,isMd5).equals(passwordMd5InDb);
			}catch(ObjectRetrievalException e){
				throw new ServiceSecurityException(SecurityExceptionType.INVALID_PERSON_ID);
			}catch(NumberFormatException e){
				throw new ServiceSecurityException(SecurityExceptionType.INVALID_PERSON_ID);
			}
		}
	}
	/**
	 * 检查密码是否正确
	 * @param userId
	 * @param password
	 * @param isMd5
	 * @throws ServiceSecurityException 密码不匹配,{@code userId}无效
	 * @see {@link #isValidPassword(String, String, boolean)}
	 */
	protected void checkValidPassword(String userId,String password, boolean isMd5) throws ServiceSecurityException{
		if(!isValidPassword(userId, password, isMd5)){
			throw new ServiceSecurityException(
					String.format("INVALID password [%s]for user [%s]",password,userId))
				.setType(SecurityExceptionType.INVALID_PASSWORD);
		}
	}
	/** 申请一个唯一的命令序列号 
	 * @throws ServiceSecurityException */
	protected long applyCmdSn(Token token) throws ServiceSecurityException{
		Enable.PERSON_ONLY.check(this, token);

		long sn = JedisUtils.incr(KEY_CMD_SN);
		String key = Long.toString(sn);
		this.cmdSnTable.set(key, token.getId(), false);
		this.cmdSnTable.expire(key);
		return sn;
	}
	/** 申请一个唯一的命令响应通道 
	 * @param token
	 * @param duration 通道有效时间 >0有效,否则使用默认的有效期
	 * @throws ServiceSecurityException */
	protected String applyAckChannel(Token token, long duration) throws ServiceSecurityException{
		Enable.PERSON_ONLY.check(this, token);

		String channel = new StringBuffer(ACK_PREFIX)
				.append(JedisUtils.incr(KEY_ACK_SN))
				.toString();
		this.ackChannelTable.set(channel, token.getId(), false);
		if(duration>0){
			this.ackChannelTable.expire(channel,duration,TimeUnit.SECONDS);
		}else{
			this.ackChannelTable.expire(channel);
		}
		return channel;
	}
	
	/**
	 * 判断命令序列号是否有效
	 * @param cmdSn
	 * @return
	 */
	protected boolean isValidCmdSn(long cmdSn){
		return this.cmdSnTable.containsKey(Long.toString(cmdSn));
	}
	/**
	 * 判断命令响应通道是否有效
	 * @param ackChannel
	 * @return
	 */
	protected boolean isValidAckChannel(String ackChannel){
		return this.ackChannelTable.containsKey(ackChannel);
	}
}
