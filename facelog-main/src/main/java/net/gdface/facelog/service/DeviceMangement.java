package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.primitives.Bytes;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.DeviceException.DeviceExceptionType;
import net.gdface.utils.FaceUtilits;

/**
 * 设备管理模块
 * @author guyadong
 *
 */
class DeviceMangement implements ServiceConstant {
	private final Dao dao;
	/**  {@code 设备ID -> token} 映射表 */
	private final RedisTable<Token> deviceTokenTable;
	/**  {@code 人员ID -> token} 映射表 */
	private final RedisTable<Token> personTokenTable;
	DeviceMangement(Dao dao) {
		this.dao = checkNotNull(dao,"dao is null");
		this.deviceTokenTable =  RedisFactory.getTable(TABLE_DEVICE_TOKEN, JedisPoolLazy.getDefaultInstance());
		this.personTokenTable =  RedisFactory.getTable(TABLE_PERSON_TOKEN, JedisPoolLazy.getDefaultInstance());
		this.personTokenTable.setExpire(DEFAULT_PERSON_TOKEN_EXPIRE, TimeUnit.MINUTES);
	}
	/** 验证MAC地址是否有效 */
	public static final boolean isValidMac(String mac){
		return !Strings.isNullOrEmpty(mac) && mac.matches("[a-fA-F0-9]{12}");
	}
	public static final void checkValidMac(String mac) throws DeviceException{
		if(!isValidMac(mac)){
			throw new DeviceException(String.format("INVALID MAC:%s ", mac))
			.setType(DeviceExceptionType.INVALID_MAC);
		}
	}
	/** 验证序列号是否有效 */
	protected boolean isValidSerialNo(String sn){
		return true;
	}
	/** 序列号已经被占用则抛出异常 */
	protected void checkNotOccupiedSerialNo(String sn) throws DeviceException{
		DeviceBean bean = dao.daoGetDeviceByIndexSerialNo(sn);
		if(null != bean){
			throw new DeviceException(String.format("serian no:%s occupied by %d", sn,bean.getId()))
					.setType(DeviceExceptionType.OCCUPIED_SN);
		}			
	}
	/** 序列号无效则抛出异常 */
	protected void checkValidSerialNo(String sn) throws DeviceException{
		if(!isValidSerialNo(sn)){
			throw new DeviceException(String.format("INVALID serial number:%s", sn))
					.setType(DeviceExceptionType.INVALID_SN);
		}
	}
	/** 验证设备令牌是否有效 */
	protected boolean isValidDeviceToken(int deviceId, Token token){
		return null == token ? false : Objects.equal(token,deviceTokenTable.get(Integer.toString(deviceId)));
	}
	/** 验证人员令牌是否有效 */
	protected boolean isValidPersonToken(int personId, Token token){
		return null == token ? false : Objects.equal(token,personTokenTable.get(Integer.toString(personId)));
	}
	/**
	 * 令牌无效抛出异常
	 * @param id
	 * @param token
	 * @throws DeviceException
	 */
	protected void checkValidToken(int id, Token token) throws DeviceException{
		if(null != token){
			if(isValidDeviceToken(id, token) || isValidPersonToken(id, token)){
				return;
			}
		}
		throw new DeviceException(DeviceExceptionType.INVALID_TOKEN);
	}
	/** 检查数据库是否存在指定的设备记录,没有则抛出异常{@link DeviceException} */
	protected void checkValidDeviceId(Integer deviceId) throws DeviceException{
		if(!this.dao.daoExistsDevice(deviceId)){
			throw new DeviceException(String.format("NOT EXISTS device %d", deviceId))
					.setType(DeviceExceptionType.INVALID_DEVICE_ID);
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
	protected void removeDeviceTokenOf(int deviceId){
		deviceTokenTable.remove(Integer.toString(deviceId));
	}
	/**
	 * 从{@link #personTokenTable}删除指定人员的令牌
	 * @param personId
	 */
	protected void removePersonTokenOf(int personId){
		personTokenTable.remove(Integer.toString(personId));
	}
	protected DeviceBean daoRegisterDevice(DeviceBean newDevice)
			throws RuntimeDaoException, DeviceException{
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
			throws RuntimeDaoException, DeviceException{
		checkValidToken(deviceId, token);
		checkValidDeviceId(deviceId);
		this.dao.daoDeleteDevice(deviceId);
	}
	protected Token loginDevice(DeviceBean loginDevice)
			throws RuntimeDaoException, DeviceException{
		checkValidDeviceId(loginDevice.getId());
		DeviceBean device = dao.daoGetDevice(loginDevice.getId());
		if(!Objects.equal(device.getMac(), loginDevice.getMac()) ){
			throw new DeviceException(String.format("MISMATCH MAC:%s", device.getMac()))
			.setType(DeviceExceptionType.INVALID_MAC);
		}
		if(!Objects.equal(device.getSerialNo(), loginDevice.getSerialNo())){
			throw new DeviceException(String.format("MISMATCH Serial Number:%s", device.getSerialNo()))
			.setType(DeviceExceptionType.INVALID_SN);
		}
		// 生成一个新令牌
		Token token = makeDeviceTokenOf(device);
		deviceTokenTable.set(device.getId().toString(), token, false);
		return token;
	}
	protected void logoutDevice(int deviceId,Token token)
			throws RuntimeDaoException, DeviceException{
		checkValidToken(deviceId,token);
		removeDeviceTokenOf(deviceId);
	}
	protected Token loginPerson(int personId)
			throws RuntimeDaoException, DeviceException{
		// 生成一个新令牌
		Token token = makePersonTokenOf(personId);
		deviceTokenTable.set(Integer.toString(personId), token, false);
		return token;
	}
	protected void logoutPerson(int personId,Token token)
			throws RuntimeDaoException, DeviceException{
		checkValidToken(personId,token);
		removePersonTokenOf(personId);
	}
}
