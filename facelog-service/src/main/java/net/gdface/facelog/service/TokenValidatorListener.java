package net.gdface.facelog.service;

import com.google.common.base.Preconditions;

import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * @author guyadong
 *
 */
public class TokenValidatorListener extends TableListener.Adapter<PersonBean> {
	private final Dao dao;
	private final TlsTokenHandler token = TlsTokenHandler.INSTANCE;
	public TokenValidatorListener(Dao dao) {
		this.dao = Preconditions.checkNotNull(dao,"dao is null");
	}

	@Override
	public void beforeInsert(PersonBean bean) throws RuntimeDaoException {
	}

	@Override
	public void beforeUpdate(PersonBean bean) throws RuntimeDaoException {
	}

	@Override
	public void beforeDelete(PersonBean bean) throws RuntimeDaoException {
	}

}
