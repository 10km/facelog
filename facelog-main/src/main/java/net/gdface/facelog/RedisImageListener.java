package net.gdface.facelog;

import com.google.common.base.Preconditions;

import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;
/**
 * 图像表({@code fl_image})变动侦听器<br>
 * @author guyadong
 *
 */
class RedisImageListener extends TableListener.Adapter<ImageBean> implements CommonConstant{
	private final TableListener<PersonBean> personListener;
	private final Dao dao;
	public RedisImageListener() {
		this(null, null);
	}
	public RedisImageListener(TableListener<PersonBean> personListener, Dao dao) {
		this.personListener = Preconditions.checkNotNull(personListener);
		this.dao = Preconditions.checkNotNull(dao);
	}
	/** 
	 * 删除图像数据记录{@link ImageBean}时,如果图像被{@link PersonBean#getImageMd5()}引用则需要发送{@link PersonBean}更新通知
	 * @see net.gdface.facelog.db.TableListener.Adapter#afterDelete(java.lang.Object)
	 */
	@Override
	public void afterDelete(ImageBean bean) {
		try{
			PersonBean personBean = dao._getPersonByIndexImageMd5(bean.getMd5());
			if(null != personBean)
				personListener.afterDelete(personBean);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

}
