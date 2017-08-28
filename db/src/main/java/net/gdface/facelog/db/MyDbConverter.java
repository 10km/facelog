package net.gdface.facelog.db;

import net.gdface.facelog.db.mysql.DbConverter;

public class MyDbConverter extends DbConverter {
	public static final IBeanConverter<FaceLightBean,FaceBean> faceLightConverter=new IBeanConverter.AbstractHandle<FaceLightBean, FaceBean>() {

		@Override
		public FaceLightBean fromRight(FaceBean bean) {
			FaceLightBean generalBean= new FaceLightBean();
            generalBean.isNew(bean.isNew());
            if(bean.getMd5() != null)
                generalBean.setMd5(bean.getMd5());
            if(bean.getPersonId() != null)
                generalBean.setPersonId(bean.getPersonId());
            if(bean.getImgMd5() != null)
                generalBean.setImgMd5(bean.getImgMd5());
            if(bean.getFaceLeft() != null)
                generalBean.setFaceLeft(bean.getFaceLeft());
            if(bean.getFaceTop() != null)
                generalBean.setFaceTop(bean.getFaceTop());
            if(bean.getFaceWidth() != null)
                generalBean.setFaceWidth(bean.getFaceWidth());
            if(bean.getFaceHeight() != null)
                generalBean.setFaceHeight(bean.getFaceHeight());
            if(bean.getEyeLeftx() != null)
                generalBean.setEyeLeftx(bean.getEyeLeftx());
            if(bean.getEyeLefty() != null)
                generalBean.setEyeLefty(bean.getEyeLefty());
            if(bean.getEyeRightx() != null)
                generalBean.setEyeRightx(bean.getEyeRightx());
            if(bean.getEyeRighty() != null)
                generalBean.setEyeRighty(bean.getEyeRighty());
            if(bean.getMouthX() != null)
                generalBean.setMouthX(bean.getMouthX());
            if(bean.getMouthY() != null)
                generalBean.setMouthY(bean.getMouthY());
            if(bean.getNoseX() != null)
                generalBean.setNoseX(bean.getNoseX());
            if(bean.getNoseY() != null)
                generalBean.setNoseY(bean.getNoseY());
            if(bean.getAngleYaw() != null)
                generalBean.setAngleYaw(bean.getAngleYaw());
            if(bean.getAnglePitch() != null)
                generalBean.setAnglePitch(bean.getAnglePitch());
            if(bean.getAngleRoll() != null)
                generalBean.setAngleRoll(bean.getAngleRoll());
            if(bean.getExtInfo() != null)
                generalBean.setExtInfo(bean.getExtInfo());
            if(bean.getCreateTime() != null)
                generalBean.setCreateTime(bean.getCreateTime());
            return generalBean;
        }

		@Override
		public FaceBean toRight(FaceLightBean bean) {
            FaceBean nativeBean= new FaceBean();
            nativeBean.isNew(bean.isNew());
            if(bean.getMd5() != null)
                nativeBean.setMd5(bean.getMd5());
            if(bean.getPersonId() != null)
                nativeBean.setPersonId(bean.getPersonId());
            if(bean.getImgMd5() != null)
                nativeBean.setImgMd5(bean.getImgMd5());
            if(bean.getFaceLeft() != null)
                nativeBean.setFaceLeft(bean.getFaceLeft());
            if(bean.getFaceTop() != null)
                nativeBean.setFaceTop(bean.getFaceTop());
            if(bean.getFaceWidth() != null)
                nativeBean.setFaceWidth(bean.getFaceWidth());
            if(bean.getFaceHeight() != null)
                nativeBean.setFaceHeight(bean.getFaceHeight());
            if(bean.getEyeLeftx() != null)
                nativeBean.setEyeLeftx(bean.getEyeLeftx());
            if(bean.getEyeLefty() != null)
                nativeBean.setEyeLefty(bean.getEyeLefty());
            if(bean.getEyeRightx() != null)
                nativeBean.setEyeRightx(bean.getEyeRightx());
            if(bean.getEyeRighty() != null)
                nativeBean.setEyeRighty(bean.getEyeRighty());
            if(bean.getMouthX() != null)
                nativeBean.setMouthX(bean.getMouthX());
            if(bean.getMouthY() != null)
                nativeBean.setMouthY(bean.getMouthY());
            if(bean.getNoseX() != null)
                nativeBean.setNoseX(bean.getNoseX());
            if(bean.getNoseY() != null)
                nativeBean.setNoseY(bean.getNoseY());
            if(bean.getAngleYaw() != null)
                nativeBean.setAngleYaw(bean.getAngleYaw());
            if(bean.getAnglePitch() != null)
                nativeBean.setAnglePitch(bean.getAnglePitch());
            if(bean.getAngleRoll() != null)
                nativeBean.setAngleRoll(bean.getAngleRoll());
            if(bean.getExtInfo() != null)
                nativeBean.setExtInfo(bean.getExtInfo());
// IGNORE field fl_face.create_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
//             if(bean.getCreateTime() != null)
//                 nativeBean.setCreateTime(bean.getCreateTime());
            return nativeBean;
        }};
	public MyDbConverter() {
		this.setBeanConverter(FaceLightBean.class,FaceBean.class, faceLightConverter);
	}

}
