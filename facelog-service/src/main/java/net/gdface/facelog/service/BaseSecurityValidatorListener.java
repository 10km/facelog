package net.gdface.facelog.service;

import java.util.List;
import java.util.Set;

import org.weakref.jmx.com.google.common.collect.Sets;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;

import net.gdface.facelog.db.BaseBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.Dao.PersonRank;
import net.gdface.facelog.service.Dao.WriteOp;

/**
 * @author guyadong
 *
 */
abstract class BaseSecurityValidatorListener<B extends BaseBean<B>> extends TableListener.Adapter<B> implements ServiceConstant {
	protected final Dao dao;
	protected final TlsTokenHandler tlsToken = TlsTokenHandler.INSTANCE;
	BaseSecurityValidatorListener(Dao dao) {
		this.dao = Preconditions.checkNotNull(dao,"dao is null");
	}
	protected abstract String getAllKey();
	/**
	 * 返回{@link Token}代表的用户等级
	 * @param token
	 * @return
	 */
	protected PersonRank rankFrom(Token token){
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
	protected Set<WriteOp> getAllowFromConfig(String key){
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
	private void checkWriteOpForOperatorByConfig(PersonRank opRank,WriteOp writeOp){
		if(opRank== PersonRank.operator 
				&& !getAllowFromConfig(getAllKey()).contains(writeOp)){
			throw new RuntimeDaoException(
					new ServiceSecurityException(String.format("operator no  %s permission ,defined in config",writeOp)));
		}
	}
	private void checkWriteOpForDeviceByConfig(WriteOp writeOp, int deviceId){
		if(!getAllowFromConfig(getAllKey()).contains(writeOp)){
			throw new RuntimeDaoException(
					new ServiceSecurityException(String.format("device no permission to create person,defined in config",writeOp))
						.setDeviceID(deviceId));
		}
	}
	protected void check(B bean, WriteOp writeOp){
		Token token = tlsToken.get();
		switch(token.getType()){
		case DEVICE:{
			checkWriteOpForDeviceByConfig(writeOp, token.getId());
			break;
		}
		case PERSON:{
			PersonRank opRank =  rankFrom(token);
			checkWriteOpForOperatorByConfig(opRank, writeOp);
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
	public void beforeInsert(B bean) throws RuntimeDaoException {
		check(bean, WriteOp.insert);
	}

	@Override
	public void beforeUpdate(B bean) throws RuntimeDaoException {
		check(bean, WriteOp.update);
	}

	@Override
	public void beforeDelete(B bean) throws RuntimeDaoException {
		check(bean, WriteOp.delete);
	}

}
