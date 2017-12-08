package net.gdface.facelog.service;

import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.Dao.PersonRank;
import net.gdface.facelog.service.Dao.WriteOp;
import net.gdface.facelog.service.Token.TokenType;

/**
 * fl_person表权限验证侦听器实现
 * @author guyadong
 *
 */
class TokenValidatorPersonListener extends BaseTokenValidatorListener<PersonBean> {
	TokenValidatorPersonListener(Dao dao) {
		super(dao);
	}
	private void checkForPersonToken(PersonBean bean, WriteOp writeOp){
		if(validatePersonToken){
			Token token = tlsToken.get();
			if(token.getType() == TokenType.PERSON){
				PersonRank opRank =  rankFromToken();
				PersonRank rank = PersonRank.fromRank(bean.getRank());
				if( null != rank && opRank.rank <= rank.rank ){
					// 不可修改同级或高级用户
					throw new RuntimeDaoException(new ServiceSecurityException(
							String.format("NO PERMISSION to %s adminstroator account ",writeOp)));
				}
			}
		}
	}
	/**
	 * 设备端可以添加用户，但不能创建管理帐户
	 * @param bean
	 * @param writeOp
	 */
	private void checkInsertForDeviceToken(PersonBean bean, WriteOp writeOp){
		if(validateDeviceToken){
			Token token = tlsToken.get();
			if(token.getType() == TokenType.DEVICE){
				PersonRank rank = PersonRank.fromRank(bean.getRank());
				if(!PersonRank.person.equals(rank)){
					// 不允许从设备端创建管理员/操作员用户
					throw new RuntimeDaoException(
							new ServiceSecurityException(
									String.format("NO PERMISSION to %s adminstroator from device",writeOp))
							.setDeviceID(token.getId()));
				}
			}
		}
	}
	@Override
	public void beforeInsert(PersonBean bean) throws RuntimeDaoException {
		super.beforeInsert(bean);
		checkForPersonToken(bean, WriteOp.insert);
		checkInsertForDeviceToken(bean, WriteOp.insert);
	}

	@Override
	public void beforeUpdate(PersonBean bean) throws RuntimeDaoException {
		super.beforeUpdate(bean);
		checkForPersonToken(bean, WriteOp.update);
	}

	@Override
	public void beforeDelete(PersonBean bean) throws RuntimeDaoException {
		super.beforeDelete(bean);
		checkForPersonToken(bean, WriteOp.delete);
	}
	@Override
	protected String getOperatorAllowKey() {
		return SECURITY_OPERATOR_TABLE_PERSON_ALLOW;
	}
	@Override
	protected String getDeviceAllowKey() {
		return SECURITY_DEVICE_TABLE_PERSON_ALLOW;
	}

}
