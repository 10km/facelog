package net.gdface.facelog.service;

import net.gdface.facelog.db.DeviceBean;

/**
 * 基于{@link BaseTokenValidatorListener}的fl_device表权限验证侦听器实现
 * @author guyadong
 *
 */
class TokenValidatorDeviceListener extends BaseTokenValidatorListener<DeviceBean> {
	TokenValidatorDeviceListener(Dao dao) {
		super(dao);
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
