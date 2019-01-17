package net.gdface.facelog;

import com.google.common.base.Objects;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.BaseDao.WriteOp;
import net.gdface.facelog.Token.TokenType;

/**
 * 基于{@link BaseTokenValidatorListener}的fl_device表权限验证侦听器实现
 * @author guyadong
 *
 */
class TokenValidatorDeviceListener extends BaseTokenValidatorListener<DeviceBean> {
	TokenValidatorDeviceListener(BaseDao dao) {
		super(dao);
	}
	/**
	 * 设备端只允许修改本设备的记录
	 * @param bean
	 * @param writeOp
	 */
	private void checkSelftDevice(DeviceBean bean, WriteOp writeOp){
		if(validateDeviceToken){
			Token token = TokenContext.getCurrentTokenContext().getToken();
			if(token.getType() == TokenType.DEVICE  && !Objects.equal(token.getId(), bean.getId())){
				// 只允许修改本设备的记录
				throw new RuntimeDaoException(
						new ServiceSecurityException(
								String.format("NO PERMISSION to %s other device's record from device",writeOp))
						.setDeviceID(token.getId()));
			}
		}
	}
	@Override
	public void beforeInsert(DeviceBean bean) throws RuntimeDaoException {
		Token token = TokenContext.getCurrentTokenContext().getToken();
		// 设备端新增加设备记录时还没有令牌,不需要检查
		if(token.getType() != TokenType.UNINITIALIZED 
				|| TokenOp.REGISTER != TokenContext.getCurrentTokenContext().getTokenOp()){
			super.beforeInsert(bean);
		}		
	}
	@Override
	public void beforeUpdate(DeviceBean bean) throws RuntimeDaoException {
		super.beforeUpdate(bean);
		checkSelftDevice(bean, WriteOp.update);
	}
	@Override
	public void beforeDelete(DeviceBean bean) throws RuntimeDaoException {
		super.beforeUpdate(bean);
		checkSelftDevice(bean, WriteOp.delete);
	}
	@Override
	protected String getOperatorAllowKey() {
		return SECURITY_OPERATOR_TABLE_DEVICE_ALLOW;
	}
	@Override
	protected String getDeviceAllowKey() {
		return SECURITY_DEVICE_TABLE_DEVICE_ALLOW;
	}

}
