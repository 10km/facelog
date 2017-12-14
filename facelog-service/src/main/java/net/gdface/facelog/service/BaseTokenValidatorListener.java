package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import com.google.common.collect.ImmutableSet;
import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.Dao.WriteOp;
import net.gdface.facelog.service.Token.TokenType;

/**
 * 基于侦听器{@link TableListener}实现数据库记录在增加/删除/更新之前对操作令牌进行权限检查<br>
 * 权限检查未通过则将{@link ServiceSecurityException}封装在{@link RuntimeDaoException}抛出
 * @author guyadong
 *
 */
abstract class BaseTokenValidatorListener<B extends BaseBean<B>> extends TableListener.Adapter<B> implements ServiceConstant {
	protected final Dao dao;
	private final ImmutableSet<WriteOp> operatorAllow;
	private final ImmutableSet<WriteOp> deviceAllow;
	private final Class<B> type;
	protected final boolean validateDeviceToken;
	protected final boolean validatePersonToken;
	@SuppressWarnings("unchecked")
	protected BaseTokenValidatorListener(Dao dao) {
		this.dao = checkNotNull(dao,"dao is null");
		this.operatorAllow = GlobalConfig.getEnumSet(WriteOp.class, getOperatorAllowKey());
		this.deviceAllow = GlobalConfig.getEnumSet(WriteOp.class, getDeviceAllowKey());
        Type superClass = getClass().getGenericSuperclass();
        this.type = (Class<B>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
		this.validateDeviceToken = CONFIG.getBoolean(TOKEN_DEVICE_VALIDATE);
		this.validatePersonToken = CONFIG.getBoolean(TOKEN_PERSON_VALIDATE);
	}
	/**
	 * 返回读取配置文件中允许操作员操作的KEY
	 * @return
	 */
	protected abstract String getOperatorAllowKey();
	/**
	 * 返回读取配置文件中允许设备操作的KEY
	 * @return
	 */
	protected abstract String getDeviceAllowKey();
	/**
	 * 返回{@link Token}代表的用户等级,设备令牌返回{@code null}
	 * @return
	 */
	protected PersonRank rankFromToken(){
		switch(TokenContext.getCurrentTokenContext().getToken().getType()){
		case ROOT:
			return PersonRank.root;
		case DEVICE:
			return null;
		case PERSON:{
			PersonBean bean = dao.daoGetPerson(TokenContext.getCurrentTokenContext().getToken().getId());
			if(null != bean){
				return PersonRank.fromRank(bean.getRank());
			}
		}			
		default:
		}
		return null;
	}
	/**
	 * 返回{@code bean}原有用户等级
	 * @param bean
	 * @return
	 */
	protected final PersonRank originalRankOf(PersonBean bean){
		PersonBean b = dao.daoGetPerson(bean.getId());
		if(null != b){
			// on update, delete时使用数据库中原有记录
			return PersonRank.fromRank(b.getRank());
		}else{
			// on insert时，使用新记录中的rank字段
			return PersonRank.fromRank(bean.getRank());
		}
	}
	/**
	 * 检查是否允许{@code opRank}级别的用户操作执行{@code writeOp}指定的操作
	 * @param writeOp
	 */
	protected final void checkWriteOpForPersonToken(WriteOp writeOp){
		boolean deny = false;
		PersonRank opRank =  rankFromToken();
		if(null == opRank || opRank.rank < PersonRank.operator.rank ){
			// 非管理员/操作员无权操作
			deny = true;
		}else if(opRank== PersonRank.operator 
				&& !operatorAllow.contains(writeOp)){
			deny = true;
		}
		if(deny){
			throw new RuntimeDaoException(
					new ServiceSecurityException(
							String.format("RANK %s no %s permission for %s",
									opRank,writeOp,type.getSimpleName())));
		}
	}
	/**
	 * 根据配置文件中的设置检查是否允许设备执行{@code writeOp}指定的操作
	 * @param writeOp
	 */
	protected final void checkWriteOpForDeviceToken(WriteOp writeOp){
		if(!deviceAllow.contains(writeOp)){
			throw new RuntimeDaoException(
					new ServiceSecurityException(
						String.format("device no %s permission for %s"
								,writeOp,type.getSimpleName()))
						.setDeviceID(TokenContext.getCurrentTokenContext().getToken().getId()));
		}
	}
	/**
	 * 检查当前令牌是否有指定的写操作权限
	 * @param bean
	 * @param writeOp
	 */
	protected final void checkWriteOp(B bean, WriteOp writeOp){
		TokenType tokenType = TokenContext.getCurrentTokenContext().getToken().getType();
		checkState(null !=tokenType,"typeToken is null");
		switch(tokenType){
		case DEVICE:{
			if(validateDeviceToken){
				checkWriteOpForDeviceToken(writeOp);
			}
			break;
		}
		case PERSON:{
			if(validatePersonToken){
				checkWriteOpForPersonToken(writeOp);
			}
			break;
		}
		case ROOT:
			break;
		case UNINITIALIZED:
			if(!validatePersonToken && !validatePersonToken){
				break;
			}
		default:
			throw new RuntimeDaoException(
					new ServiceSecurityException(String.format("INVALID token %s",tokenType)));
		}
	}
	@Override
	public void beforeInsert(B bean) throws RuntimeDaoException {
		checkWriteOp(bean, WriteOp.insert);
	}

	@Override
	public void beforeUpdate(B bean) throws RuntimeDaoException {
		checkWriteOp(bean, WriteOp.update);
	}

	@Override
	public void beforeDelete(B bean) throws RuntimeDaoException {
		checkWriteOp(bean, WriteOp.delete);
	}

}
