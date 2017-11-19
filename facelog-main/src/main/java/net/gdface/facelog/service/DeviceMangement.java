package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.ByteBuffer;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

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
	/**  {@code 设备ID -> token} 映射表*/
	private final RedisTable<Long> tokenTable;

	DeviceMangement(Dao dao) {
		this.dao = checkNotNull(dao,"dao is null");
		this.tokenTable =  RedisFactory.getTable(TABLE_DEVICE_TOKEN, JedisPoolLazy.getDefaultInstance());

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
	protected boolean isValidToken(int deviceId, long token){
		return Objects.equal(token,tokenTable.get(Integer.toString(deviceId)));
	}
	protected void checkValidToken(int deviceId, long token) throws DeviceException{
		if(!isValidToken(deviceId, token)){
			throw new DeviceException(DeviceExceptionType.INVALID_TOKEN);
		}
	}
	/** 检查数据库是否存在指定的设备记录,没有则抛出异常{@link DeviceException} */
	protected void checkValidDeviceId(Integer deviceId) throws DeviceException{
		if(!this.dao.daoExistsDevice(deviceId)){
			throw new DeviceException(String.format("NOT EXISTS device %d", deviceId))
					.setType(DeviceExceptionType.INVALID_DEVICE_ID);
		}
	}
	protected DeviceBean daoRegisterDevice(DeviceBean newDevice)
			throws RuntimeDaoException, DeviceException{
		checkArgument(null != newDevice,"deviceBean must not be null");
        checkArgument(newDevice.isNew() && null == newDevice.getId(),
        		"for device registeration the 'deviceBean' must be a new record,so the _isNew field must be true and id must be null");
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
	protected void daoUnregisterDevice(int deviceId,long token)
			throws RuntimeDaoException, DeviceException{
		checkValidToken(deviceId, token);
		checkValidDeviceId(deviceId);
		this.dao.daoDeleteDevice(deviceId);
	}

	/**
	 * 计算设备令牌
	 * @param device 设备参数(包括设备ID,MAC地址,序列号)为{@code null}抛出异常
	 * @return 设备访问令牌
	 * @throws IllegalArgumentException 设备参数为{@code null}
	 */
	protected long makeTokenOf(DeviceBean device){
		checkArgument(null != device
				&& null != device.getId() 
				&& null != device.getMac() 
				&& null != device.getSerialNo(),
				"null device argument(id,mac,serialNo)");
		String buffer = new StringBuffer(64)
				.append(device.getId())
				.append(device.getMac())
				.append(device.getSerialNo())
				.append(System.nanoTime())
				.toString();
		byte[] md5 = FaceUtilits.getMD5(buffer.getBytes());
		ByteBuffer byteBuffers = ByteBuffer.wrap(md5);
		return byteBuffers.getLong() ^ byteBuffers.getLong();
	}
	/**
	 * 从{@link #tokenTable}删除指定设备的令牌
	 * @param deviceId
	 */
	protected void removeTokenOf(int deviceId){
		tokenTable.remove(Integer.toString(deviceId));
	}
	protected long daoLoginDevice(DeviceBean loginDevice)
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
		long token = makeTokenOf(device);
		tokenTable.set(device.getId().toString(), token, false);
		return token;
	}
	protected void daoLogoutDevice(int deviceId,long token)
			throws RuntimeDaoException, DeviceException{
		checkValidToken(deviceId,token);
		removeTokenOf(deviceId);
	}
}
