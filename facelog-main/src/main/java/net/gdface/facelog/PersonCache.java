package net.gdface.facelog;

import net.gdface.facelog.db.PersonBean;

public class PersonCache extends TableLoadCaching<Integer,PersonBean> implements CommonConstant {
	private static PersonCache instance;
	public static PersonCache getInstance(long maximumSize, long durationMinutes){
		// Double Checked Locking
		if(null == instance){
			synchronized(PersonCache.class){
				if(null == instance){
					instance = new PersonCache( maximumSize, durationMinutes);
				}
			}
		}
		return instance;
	}
	private PersonCache(long maximumSize, long durationMinutes) {
		super(maximumSize, durationMinutes);
	}

	@Override
	protected Integer returnPrimaryKey(PersonBean bean) {
		return bean.getId();
	}

	@Override
	protected PersonBean loadfromDatabase(Integer pk) {
		return personManager.loadByPrimaryKey(pk);
	}

}
