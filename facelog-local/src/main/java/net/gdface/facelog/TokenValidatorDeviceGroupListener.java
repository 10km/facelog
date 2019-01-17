package net.gdface.facelog;

import net.gdface.facelog.db.DeviceGroupBean;

/**
 * 基于{@link BaseTokenValidatorListener}的fl_device_group表权限验证侦听器实现
 * @author guyadong
 *
 */
class TokenValidatorDeviceGroupListener extends BaseTokenValidatorListener<DeviceGroupBean> {
	TokenValidatorDeviceGroupListener(BaseDao dao) {
		super(dao);
	}
	@Override
	protected String getOperatorAllowKey() {
		return SECURITY_OPERATOR_TABLE_DEVICE_GROUP_ALLOW;
	}
	@Override
	protected String getDeviceAllowKey() {
		return SECURITY_DEVICE_TABLE_DEVICE_GROUP_ALLOW;
	}

}
