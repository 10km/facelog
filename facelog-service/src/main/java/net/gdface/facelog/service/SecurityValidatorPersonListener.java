package net.gdface.facelog.service;

import java.util.List;
import java.util.Set;

import org.weakref.jmx.com.google.common.collect.Sets;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;

import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.Dao.PersonRank;
import net.gdface.facelog.service.Dao.WriteOp;

/**
 * @author guyadong
 *
 */
class SecurityValidatorPersonListener extends TableListener.Adapter<PersonBean> implements ServiceConstant {
	private final Dao dao;
	private final TlsTokenHandler tlsToken = TlsTokenHandler.INSTANCE;
	SecurityValidatorPersonListener(Dao dao) {
		this.dao = Preconditions.checkNotNull(dao,"dao is null");
	}
	/**
	 * 返回{@link Token}代表的用户等级
	 * @param token
	 * @return
	 */
	private PersonRank rankFrom(Token token){
		switch(token.getType()){
		case ROOT:
			return PersonRank.root;
		case DEVICE:
			return null;
		case PERSON:{
			PersonBean bean = dao.daoGetPerson(token.getId());
			if(null != bean){
				return PersonRank.fromRank(bean.getRank());
			}
		}			
		default:
		}
		return null;
	}
	private Set<WriteOp> getAllowFromConfig(String key){
		List<String> list = GlobalConfig.getExplodedStringAsList(CONFIG.getString(key,""));
		List<WriteOp> opList = Lists.transform(list, new Function<String,Dao.WriteOp>(){
			@Override
			public WriteOp apply(String input) {
				try{
					return WriteOp.valueOf(input.toLowerCase());
				}catch(RuntimeException e){
					return null;
				}
			}});
		return Sets.newHashSet(Iterators.filter(opList.iterator(), Predicates.notNull()));
	}
	private void checkWriteOpForOperatorByConfig(PersonRank opRank,String key, WriteOp writeOp){
		if(opRank== PersonRank.operator 
				&& !getAllowFromConfig(key).contains(writeOp)){
			// 无权创建用户
			throw new RuntimeDaoException(new ServiceSecurityException("operator no permission to create person,defined in config"));
		}
	}
	@Override
	public void beforeInsert(PersonBean bean) throws RuntimeDaoException {
		Token token = tlsToken.get();
		switch(token.getType()){
		case DEVICE:{
			PersonRank rank = PersonRank.fromRank(bean.getRank());
			if(!PersonRank.person.equals(rank)){
				// 不允许从设备端创建管理员/操作员用户
				throw new RuntimeDaoException(
						new ServiceSecurityException("CANT create operator or admin user from device").setDeviceID(token.getId()));
			}
			break;
		}
		case PERSON:{
			PersonRank rank = PersonRank.fromRank(bean.getRank());
			PersonRank opRank =  rankFrom(token);
			if(null == opRank || opRank.rank < PersonRank.operator.rank ){
				// 非管理员/操作员无权创建用户
				throw new RuntimeDaoException(new ServiceSecurityException("NO PERMISSION to create person"));
			}
			if( null != rank && opRank.rank <= rank.rank ){
				// 不可创建同级或高级用户
				throw new RuntimeDaoException(new ServiceSecurityException("NO PERMISSION to create admin account"));
			}
			checkWriteOpForOperatorByConfig(opRank, SECURITY_OPERATOR_TABLE_PERSON_ALLOW, WriteOp.insert);
			break;
		}
		case ROOT:
			break;
		case UNINITIALIZED:
		default:
			throw new RuntimeDaoException(new ServiceSecurityException("INVALID token"));
		}
	}

	@Override
	public void beforeUpdate(PersonBean bean) throws RuntimeDaoException {
		Token token = tlsToken.get();
		switch(token.getType()){
		case DEVICE:{
			// 不允许从设备端执行删除用户操作
			throw new RuntimeDaoException(new ServiceSecurityException("CANT update user from device").setDeviceID(token.getId()));
		}
		case PERSON:{
			PersonRank rank = PersonRank.fromRank(bean.getRank());
			PersonRank opRank =  rankFrom(token);
			if(null == opRank || opRank.rank < PersonRank.operator.rank ){
				// 非管理员/操作员无权删除用户
				throw new RuntimeDaoException(new ServiceSecurityException("NO PERMISSION to delete person"));
			}
			if( null != rank && opRank.rank <= rank.rank ){
				// 不可删除同级或高级用户
				throw new RuntimeDaoException(new ServiceSecurityException("NO PERMISSION to delete admin account"));
			}
			checkWriteOpForOperatorByConfig(opRank, SECURITY_OPERATOR_TABLE_PERSON_ALLOW, WriteOp.update);
			break;
		}
		case ROOT:
			break;
		case UNINITIALIZED:
		default:
			throw new RuntimeDaoException(new ServiceSecurityException("INVALID token"));
		}
	}

	@Override
	public void beforeDelete(PersonBean bean) throws RuntimeDaoException {
		Token token = tlsToken.get();
		switch(token.getType()){
		case DEVICE:{
			// 不允许从设备端执行删除用户操作
			throw new RuntimeDaoException(new ServiceSecurityException("CANT delete user from device").setDeviceID(token.getId()));
		}
		case PERSON:{
			PersonRank rank = PersonRank.fromRank(bean.getRank());
			PersonRank opRank =  rankFrom(token);
			if(null == opRank || opRank.rank < PersonRank.operator.rank ){
				// 非管理员/操作员无权删除用户
				throw new RuntimeDaoException(new ServiceSecurityException("NO PERMISSION to delete person"));
			}
			if( null != rank && opRank.rank <= rank.rank ){
				// 不可删除同级或高级用户
				throw new RuntimeDaoException(new ServiceSecurityException("NO PERMISSION to delete admin account"));
			}
			checkWriteOpForOperatorByConfig(opRank, SECURITY_OPERATOR_TABLE_PERSON_ALLOW, WriteOp.delete);
			break;
		}
		case ROOT:
			break;
		case UNINITIALIZED:
		default:
			throw new RuntimeDaoException(new ServiceSecurityException("INVALID token"));
		}
	
	}

}
