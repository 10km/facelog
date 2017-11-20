package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.primitives.Bytes;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.ServiceSecurityException.SecurityExceptionType;
import net.gdface.utils.FaceUtilits;

/**
 * 令牌管理模块
 * @author guyadong
 *
 */
class TokenMangement implements ServiceConstant {
	private final Dao dao;
	/**  {@code 设备ID -> token} 映射表 */
	private final RedisTable<Token> deviceTokenTable;
	/**  {@code 人员ID -> token} 映射表 */
	private final RedisTable<Token> personTokenTable;
	/** 是否执行设备令牌验证 */
	private final boolean validateDeviceToken;
	/** 是否执行人员令牌验证 */
	private final boolean validatePersonToken;
	/**
	 * @param dao
	 * @param validateDeviceToken 是否执行设备令牌验证
	 * @param validatePersonToken 是否执行人员令牌验证
	 */
	TokenMangement(Dao dao, boolean validateDeviceToken, boolean validatePersonToken) {
		this.dao = checkNotNull(dao,"dao is null");
		this.validateDeviceToken = validateDeviceToken;
		this.validatePersonToken = validatePersonToken;
		this.deviceTokenTable =  RedisFactory.getTable(TABLE_DEVICE_TOKEN, JedisPoolLazy.getDefaultInstance());
		this.personTokenTable =  RedisFactory.getTable(TABLE_PERSON_TOKEN, JedisPoolLazy.getDefaultInstance());
		this.personTokenTable.setKeyHelper(new Function<Token,String>(){
			@Override
			public String apply(Token input) {
				return null == input ? null : Integer.toString(input.getId());
			}});
		this.personTokenTable.setExpire(DEFAULT_PERSON_TOKEN_EXPIRE, TimeUnit.MINUTES);
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
	/** 验证设备令牌是否有效 */
	private boolean isValidDeviceToken(Token token){
		if(validateDeviceToken){
			return null == token ? false : Objects.equal(token,deviceTokenTable.get(Integer.toString(token.getId())));			
		}else{
			return true;
		}		
	}
	/** 验证人员令牌是否有效 */
	private boolean isValidPersonToken(Token token){
		if(validatePersonToken){
			return null == token ? false : Objects.equal(token,personTokenTable.get(Integer.toString(token.getId())));
		}else{
			return true;
		}			
	}
	/**
	 * 令牌无效抛出异常
	 * @param token
	 * @throws ServiceSecurityException
	 */
	protected void checkValidToken(Token token) throws ServiceSecurityException{
		if(null != token){
			if(isValidDeviceToken(token) || isValidPersonToken(token)){
				return;
			}
		}
		throw new ServiceSecurityException(SecurityExceptionType.INVALID_TOKEN);
	}
	/** 检查数据库是否存在指定的设备记录,没有则抛出异常{@link ServiceSecurityException} */
	protected void checkValidDeviceId(Integer deviceId) throws ServiceSecurityException{
		if(!this.dao.daoExistsDevice(deviceId)){
			throw new ServiceSecurityException(String.format("NOT EXISTS device %d", deviceId))
					.setType(SecurityExceptionType.INVALID_DEVICE_ID);
		}
	}
	protected static Token makeToken(byte[] source){
		ByteBuffer buffer = ByteBuffer.wrap(new byte[8]);
		buffer.asLongBuffer().put(System.nanoTime());
		byte[] md5 = FaceUtilits.getMD5(Bytes.concat(checkNotNull(source),buffer.array()));
		ByteBuffer byteBuffers = ByteBuffer.wrap(md5);
		return new Token(byteBuffers.getLong(), byteBuffers.getLong());
	}
	protected static Token makeToken(Object ...objs){
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
	protected static Token makeDeviceTokenOf(DeviceBean device){
		checkArgument(null != device
				&& null != device.getId() 
				&& null != device.getMac() 
				&& null != device.getSerialNo(),
				"null device argument(id,mac,serialNo)");
		return makeToken(device.getId(),device.getMac(),device.getSerialNo());
	}
	protected static Token makePersonTokenOf(int personId){
		ByteBuffer buffer = ByteBuffer.wrap(new byte[8]);
		buffer.asLongBuffer().put(personId);
		return makeToken(buffer.array());
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
	protected DeviceBean registerDevice(DeviceBean newDevice)
			throws RuntimeDaoException, ServiceSecurityException{
		checkArgument(null != newDevice,"deviceBean must not be null");
	    checkArgument(newDevice.isNew() && null == newDevice.getId(),
	    		"for device registeration the 'newDevice' must be a new record,so the _isNew field must be true and id must be null");
		checkValidMac(newDevice.getMac());
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
	protected void unregisterDevice(int deviceId,Token token)
			throws RuntimeDaoException, ServiceSecurityException{
		checkValidToken(token);
		checkValidDeviceId(deviceId);
		this.dao.daoDeleteDevice(deviceId);
	}
	/**
	 * 申请设备令牌
	 * @param loginDevice 申请信息设备信息，必须提供{@code id, mac, serialNo}字段
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceSecurityException
	 */
	protected Token applyDeviceToken(DeviceBean loginDevice)
			throws RuntimeDaoException, ServiceSecurityException{
		checkValidDeviceId(loginDevice.getId());
		DeviceBean device = dao.daoGetDevice(loginDevice.getId());
		if(!Objects.equal(device.getMac(), loginDevice.getMac()) ){
			throw new ServiceSecurityException(String.format("MISMATCH MAC:%s", device.getMac()))
			.setType(SecurityExceptionType.INVALID_MAC);
		}
		if(!Objects.equal(device.getSerialNo(), loginDevice.getSerialNo())){
			throw new ServiceSecurityException(String.format("MISMATCH Serial Number:%s", device.getSerialNo()))
			.setType(SecurityExceptionType.INVALID_SN);
		}
		// 生成一个新令牌
		Token token = makeDeviceTokenOf(device).setId(device.getId());
		deviceTokenTable.set(device.getId().toString(), token, false);
		return token;
	}
	/**
	 * 释放设备令牌
	 * @param token 当前持有的令牌
	 * @throws RuntimeDaoException
	 * @throws ServiceSecurityException
	 */
	protected void releaseDeviceToken(Token token)
			throws RuntimeDaoException, ServiceSecurityException{
		checkValidToken(token);
		removeDeviceTokenOf(token.getId());
	}
	/**
	 * 申请人员访问令牌
	 * @param personId
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceSecurityException
	 */
	protected Token applyPersonToken(int personId)
			throws RuntimeDaoException, ServiceSecurityException{
		// 生成一个新令牌
		if(!dao.daoExistsPerson(personId)){
			throw new ServiceSecurityException(SecurityExceptionType.INVALID_PERSON_ID);
		}
		Token token = makePersonTokenOf(personId).setId(personId);
		String key = Integer.toString(personId);
		deviceTokenTable.set(key, token, false);
		deviceTokenTable.expire(token);
		return token;
	}
	/**
	 * 释放人员访问令牌
	 * @param token 当前持有的令牌
	 * @throws RuntimeDaoException
	 * @throws ServiceSecurityException
	 */
	protected void releasePersonToken(Token token)
			throws RuntimeDaoException, ServiceSecurityException{
		checkValidToken(token);
		removePersonTokenOf(token.getId());
	}
}
