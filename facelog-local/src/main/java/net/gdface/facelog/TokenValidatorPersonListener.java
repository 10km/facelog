package net.gdface.facelog;

import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.BaseDao.WriteOp;
import net.gdface.facelog.Token.TokenType;

/**
 * 基于{@link BaseTokenValidatorListener}的fl_person表权限验证侦听器实现
 * @author guyadong
 *
 */
class TokenValidatorPersonListener extends BaseTokenValidatorListener<PersonBean> {
	TokenValidatorPersonListener(BaseDao dao) {
		super(dao);
	}
	/**
	 * 检查当前令牌的用户是否有权对{@code bean}执行{@code writeOp}操作
	 * @param bean
	 * @param writeOp
	 */
	private void checkForPersonToken(PersonBean bean, WriteOp writeOp){
		if(validatePersonToken){
			Token token = TokenContext.getCurrentTokenContext().getToken();
			if(token.getType() == TokenType.PERSON){				
				PersonRank opRank =  rankFromToken();
				PersonRank beanRank = originalRankOf(bean);
				// opRank 在这里不会为null,因为调用本方法时父类方法已经做了不为null的检查
				if( null != beanRank && opRank.rank <= beanRank.rank ){
					// 不可修改同级或高级用户
					throw new RuntimeDaoException(new ServiceSecurityException(
							String.format("RANK %s NO PERMISSION to %s adminstroator account ",opRank,writeOp)));
				}
				// null == beanRank 也不合法，但这个错误交给数据库来检查
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
			Token token = TokenContext.getCurrentTokenContext().getToken();
			if(token.getType() == TokenType.DEVICE){
				PersonRank beanRank = PersonRank.fromRank(bean.getRank());
				if(!PersonRank.person.equals(beanRank)){
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
