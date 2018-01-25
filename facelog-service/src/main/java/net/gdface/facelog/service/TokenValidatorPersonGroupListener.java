package net.gdface.facelog.service;

import net.gdface.facelog.db.PersonGroupBean;

/**
 * 基于{@link BaseTokenValidatorListener}的fl_person_group表权限验证侦听器实现
 * @author guyadong
 *
 */
class TokenValidatorPersonGroupListener extends BaseTokenValidatorListener<PersonGroupBean> {
	TokenValidatorPersonGroupListener(BaseDao dao) {
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
