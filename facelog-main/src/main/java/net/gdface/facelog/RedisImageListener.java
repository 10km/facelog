package net.gdface.facelog;

import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.TableListener;

public class RedisImageListener extends TableListener.Adapter<ImageBean> implements CommonConstant{
	private final TableListener<PersonBean> personListner;
	
	public RedisImageListener() {
		this(null);
	}
	public RedisImageListener(TableListener<PersonBean> personListener) {
		personListner = personListener;
	}
	/** 
	 * 删除图像数据记录{@link ImageBean}时,如果图像被{@link PersonBean#getImageMd5()}引用则需要发送{@link PersonBean}更新通知
	 * @see net.gdface.facelog.db.TableListener.Adapter#afterDelete(java.lang.Object)
	 */
	@Override
	public void afterDelete(ImageBean bean) {
		try{
			PersonBean personBean = personManager.loadByIndexImageMd5(bean.getMd5());
			if(null != personBean)
				personListner.afterDelete(personBean);
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

}
