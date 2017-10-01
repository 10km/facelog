package net.gdface.facelog;

import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.TableListener;

public class RedisStoreListener extends TableListener.Adapter<StoreBean> implements CommonConstant{
	private TableListener<ImageBean> imageListener;
	
	public RedisStoreListener(TableListener<ImageBean> imageListener) {
		this.imageListener = imageListener;
	}
	/** 
	 * 删除存储数据记录{@link StoreBean}时,如果是图像触发 {@link #imageListener}
	 * @see net.gdface.facelog.db.TableListener.Adapter#afterDelete(java.lang.Object)
	 */
	@Override
	public void afterDelete(StoreBean bean) {
		try{
			ImageBean imageBean = imageManager.loadByPrimaryKey(bean.getMd5());
			if(null != imageBean){
				imageListener.afterDelete(imageBean);
			}
		}catch (Exception e){
			logger.error(e.getMessage(),e);
		}
	}

}
