package net.gdface.facelog;

import net.gdface.facelog.db.ITableCache;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableLoadCaching;
import net.gdface.facelog.db.mysql.Cache;

public class PersonCache implements ITableCache<Integer, PersonBean>,CommonConstant{
	private final TableLoadCaching<Integer, PersonBean> idCache;
	private final TableLoadCaching<String, PersonBean> papersNumCache;
	public PersonCache(long maximumSize,long durationMinutes) {
		idCache = new Cache.PersonCache(maximumSize,durationMinutes);
		papersNumCache = new TableLoadCaching<String, PersonBean>(maximumSize,durationMinutes) {
			@Override
			protected String returnKey(PersonBean bean) {
				return bean.getPapersNum();
			}
			@Override
			protected PersonBean loadfromDatabase(String key) {
				return personManager.loadByIndexPapersNum(key);
			}
			@Override
			public void registerListener() {
				personManager.registerListener(tableListener);	
			}
			@Override
			public void unregisterListener() {
				personManager.unregisterListener(tableListener);				
			}
		};
	}

	public PersonBean getBeanByPersonId(Integer key) {
		return idCache.getBean(key);
	}

	public PersonBean getBeanByPapersNum(String key) {
		return papersNumCache.getBean(key);
	}
	@Override
	public void registerListener(){
		idCache.registerListener();
		papersNumCache.registerListener();
	}
	@Override
	public void unregisterListener(){
		idCache.unregisterListener();
		papersNumCache.unregisterListener();
	}

	@Override
	public PersonBean getBean(Integer key) {
		return getBeanByPersonId(key);
	}
}
