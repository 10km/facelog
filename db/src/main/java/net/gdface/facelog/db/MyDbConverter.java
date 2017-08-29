package net.gdface.facelog.db;

import net.gdface.facelog.db.mysql.DbConverter;

public class MyDbConverter extends DbConverter {
	public static final IBeanConverter<FaceLightBean,FaceBean> faceLightConverter=new IBeanConverter.AbstractHandle<FaceLightBean, FaceBean>() {

		@Override
		protected FaceLightBean _newInstanceL() {
			return new FaceLightBean();
		}

		@Override
		protected FaceBean _newInstanceR() {
			return new FaceBean();
		}
		
		@Override
		public void _fromRight(FaceLightBean left,FaceBean right) {
            left.isNew(right.isNew());
            if(right.getMd5() != null)
                left.setMd5(right.getMd5());
            if(right.getPersonId() != null)
                left.setPersonId(right.getPersonId());
            if(right.getImgMd5() != null)
                left.setImgMd5(right.getImgMd5());
            if(right.getFaceLeft() != null)
                left.setFaceLeft(right.getFaceLeft());
            if(right.getFaceTop() != null)
                left.setFaceTop(right.getFaceTop());
            if(right.getFaceWidth() != null)
                left.setFaceWidth(right.getFaceWidth());
            if(right.getFaceHeight() != null)
                left.setFaceHeight(right.getFaceHeight());
            if(right.getEyeLeftx() != null)
                left.setEyeLeftx(right.getEyeLeftx());
            if(right.getEyeLefty() != null)
                left.setEyeLefty(right.getEyeLefty());
            if(right.getEyeRightx() != null)
                left.setEyeRightx(right.getEyeRightx());
            if(right.getEyeRighty() != null)
                left.setEyeRighty(right.getEyeRighty());
            if(right.getMouthX() != null)
                left.setMouthX(right.getMouthX());
            if(right.getMouthY() != null)
                left.setMouthY(right.getMouthY());
            if(right.getNoseX() != null)
                left.setNoseX(right.getNoseX());
            if(right.getNoseY() != null)
                left.setNoseY(right.getNoseY());
            if(right.getAngleYaw() != null)
                left.setAngleYaw(right.getAngleYaw());
            if(right.getAnglePitch() != null)
                left.setAnglePitch(right.getAnglePitch());
            if(right.getAngleRoll() != null)
                left.setAngleRoll(right.getAngleRoll());
            if(right.getExtInfo() != null)
                left.setExtInfo(right.getExtInfo());
            if(right.getCreateTime() != null)
                left.setCreateTime(right.getCreateTime());
        }

		@Override
		public void _toRight(FaceLightBean left,FaceBean right) {
            right.isNew(left.isNew());
            if(left.getMd5() != null)
                right.setMd5(left.getMd5());
            if(left.getPersonId() != null)
                right.setPersonId(left.getPersonId());
            if(left.getImgMd5() != null)
                right.setImgMd5(left.getImgMd5());
            if(left.getFaceLeft() != null)
                right.setFaceLeft(left.getFaceLeft());
            if(left.getFaceTop() != null)
                right.setFaceTop(left.getFaceTop());
            if(left.getFaceWidth() != null)
                right.setFaceWidth(left.getFaceWidth());
            if(left.getFaceHeight() != null)
                right.setFaceHeight(left.getFaceHeight());
            if(left.getEyeLeftx() != null)
                right.setEyeLeftx(left.getEyeLeftx());
            if(left.getEyeLefty() != null)
                right.setEyeLefty(left.getEyeLefty());
            if(left.getEyeRightx() != null)
                right.setEyeRightx(left.getEyeRightx());
            if(left.getEyeRighty() != null)
                right.setEyeRighty(left.getEyeRighty());
            if(left.getMouthX() != null)
                right.setMouthX(left.getMouthX());
            if(left.getMouthY() != null)
                right.setMouthY(left.getMouthY());
            if(left.getNoseX() != null)
                right.setNoseX(left.getNoseX());
            if(left.getNoseY() != null)
                right.setNoseY(left.getNoseY());
            if(left.getAngleYaw() != null)
                right.setAngleYaw(left.getAngleYaw());
            if(left.getAnglePitch() != null)
                right.setAnglePitch(left.getAnglePitch());
            if(left.getAngleRoll() != null)
                right.setAngleRoll(left.getAngleRoll());
            if(left.getExtInfo() != null)
                right.setExtInfo(left.getExtInfo());
// IGNORE field fl_face.create_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
//             if(bean.getCreateTime() != null)
//                 nativeBean.setCreateTime(bean.getCreateTime());
        }};
	public MyDbConverter() {
		this.setBeanConverter(FaceLightBean.class,FaceBean.class, faceLightConverter);
	}

}
