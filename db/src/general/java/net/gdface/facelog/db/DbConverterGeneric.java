// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: impl.dbconverter.generic.java.vm
// ______________________________________________________
package net.gdface.facelog.db;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.LogLightBean;
/**
 * generic type converter of {@link IDbConverter} implementation<br>
 * usage:<pre>new DbConverterGeneric&lt;Model1,Model2,Model3,Model4,Model5,Model6,Model7,Model8&gt;(){};</pre>
 * @author guyadong
 *
 * @param <R_DEVICE> native type for fl_device
 * @param <R_FACE> native type for fl_face
 * @param <R_FEATURE> native type for fl_feature
 * @param <R_IMAGE> native type for fl_image
 * @param <R_LOG> native type for fl_log
 * @param <R_PERSON> native type for fl_person
 * @param <R_STORE> native type for fl_store
 * @param <R_LOGLIGHT> native type for fl_log_light
 *
 */
public abstract class DbConverterGeneric<R_DEVICE,R_FACE,R_FEATURE,R_IMAGE,R_LOG,R_PERSON,R_STORE,R_LOGLIGHT> 
    implements IDbConverter<R_DEVICE,R_FACE,R_FEATURE,R_IMAGE,R_LOG,R_PERSON,R_STORE,R_LOGLIGHT>, Constant{
    private final IBeanConverter<DeviceBean,R_DEVICE> converterDeviceBean;
    private final IBeanConverter<FaceBean,R_FACE> converterFaceBean;
    private final IBeanConverter<FeatureBean,R_FEATURE> converterFeatureBean;
    private final IBeanConverter<ImageBean,R_IMAGE> converterImageBean;
    private final IBeanConverter<LogBean,R_LOG> converterLogBean;
    private final IBeanConverter<PersonBean,R_PERSON> converterPersonBean;
    private final IBeanConverter<StoreBean,R_STORE> converterStoreBean;
    private final IBeanConverter<LogLightBean,R_LOGLIGHT> converterLogLightBean;
    private static Class<?> getRawClass(Type type){
        if(type instanceof Class<?>){
            return (Class<?>) type;
        } else if(type instanceof ParameterizedType){
            return getRawClass(((ParameterizedType) type).getRawType());
        } else{
            throw new IllegalArgumentException("invalid type");
        }
    }
    /** 
     * usage: <pre>new DbConverterGeneric&lt;Model...&gt;(javaFields...){};</pre>
     * each 'javaFields' parameter is a comma splice string,including all field name of right type,<br>
     * if null or empty, use default string of each table
     */
    @SuppressWarnings("unchecked")
    public DbConverterGeneric(String javaFieldsOfDevice,
                    String javaFieldsOfFace,
                    String javaFieldsOfFeature,
                    String javaFieldsOfImage,
                    String javaFieldsOfLog,
                    String javaFieldsOfPerson,
                    String javaFieldsOfStore,
                    String javaFieldsOfLogLight){
        if(null == javaFieldsOfDevice || javaFieldsOfDevice.isEmpty())javaFieldsOfDevice = FL_DEVICE_JAVA_FIELDS;
        if(null == javaFieldsOfFace || javaFieldsOfFace.isEmpty())javaFieldsOfFace = FL_FACE_JAVA_FIELDS;
        if(null == javaFieldsOfFeature || javaFieldsOfFeature.isEmpty())javaFieldsOfFeature = FL_FEATURE_JAVA_FIELDS;
        if(null == javaFieldsOfImage || javaFieldsOfImage.isEmpty())javaFieldsOfImage = FL_IMAGE_JAVA_FIELDS;
        if(null == javaFieldsOfLog || javaFieldsOfLog.isEmpty())javaFieldsOfLog = FL_LOG_JAVA_FIELDS;
        if(null == javaFieldsOfPerson || javaFieldsOfPerson.isEmpty())javaFieldsOfPerson = FL_PERSON_JAVA_FIELDS;
        if(null == javaFieldsOfStore || javaFieldsOfStore.isEmpty())javaFieldsOfStore = FL_STORE_JAVA_FIELDS;
        if(null == javaFieldsOfLogLight || javaFieldsOfLogLight.isEmpty())javaFieldsOfLogLight = FL_LOG_LIGHT_JAVA_FIELDS;
        
        Type[] typeArguments = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments();
        this.converterDeviceBean = new BeanConverterUtils.DeviceBeanConverter<R_DEVICE>(DeviceBean.class,
            (Class<R_DEVICE>)getRawClass(typeArguments[0]),javaFieldsOfDevice);            
        this.converterFaceBean = new BeanConverterUtils.FaceBeanConverter<R_FACE>(FaceBean.class,
            (Class<R_FACE>)getRawClass(typeArguments[1]),javaFieldsOfFace);            
        this.converterFeatureBean = new BeanConverterUtils.FeatureBeanConverter<R_FEATURE>(FeatureBean.class,
            (Class<R_FEATURE>)getRawClass(typeArguments[2]),javaFieldsOfFeature);            
        this.converterImageBean = new BeanConverterUtils.ImageBeanConverter<R_IMAGE>(ImageBean.class,
            (Class<R_IMAGE>)getRawClass(typeArguments[3]),javaFieldsOfImage);            
        this.converterLogBean = new BeanConverterUtils.LogBeanConverter<R_LOG>(LogBean.class,
            (Class<R_LOG>)getRawClass(typeArguments[4]),javaFieldsOfLog);            
        this.converterPersonBean = new BeanConverterUtils.PersonBeanConverter<R_PERSON>(PersonBean.class,
            (Class<R_PERSON>)getRawClass(typeArguments[5]),javaFieldsOfPerson);            
        this.converterStoreBean = new BeanConverterUtils.StoreBeanConverter<R_STORE>(StoreBean.class,
            (Class<R_STORE>)getRawClass(typeArguments[6]),javaFieldsOfStore);            
        this.converterLogLightBean = new BeanConverterUtils.LogLightBeanConverter<R_LOGLIGHT>(LogLightBean.class,
            (Class<R_LOGLIGHT>)getRawClass(typeArguments[7]),javaFieldsOfLogLight);            
    }
    public DbConverterGeneric(){
        this(null,null,null,null,null,null,null,null);
    }
    @Override
    public <L,R>IBeanConverter<L,R>getBeanConverter(Class<L> lClass,Class<R> rClass){
        throw new UnsupportedOperationException();
    }
    
    @Override
    public <L,R>void setBeanConverter(Class<L> lClass,Class<R> rClass,IBeanConverter<L,R>converter){
        throw new UnsupportedOperationException();
    }

    @Override
    public IBeanConverter<DeviceBean, R_DEVICE> getDeviceBeanConverter() {
        return converterDeviceBean;
    }
    @Override
    public IBeanConverter<FaceBean, R_FACE> getFaceBeanConverter() {
        return converterFaceBean;
    }
    @Override
    public IBeanConverter<FeatureBean, R_FEATURE> getFeatureBeanConverter() {
        return converterFeatureBean;
    }
    @Override
    public IBeanConverter<ImageBean, R_IMAGE> getImageBeanConverter() {
        return converterImageBean;
    }
    @Override
    public IBeanConverter<LogBean, R_LOG> getLogBeanConverter() {
        return converterLogBean;
    }
    @Override
    public IBeanConverter<PersonBean, R_PERSON> getPersonBeanConverter() {
        return converterPersonBean;
    }
    @Override
    public IBeanConverter<StoreBean, R_STORE> getStoreBeanConverter() {
        return converterStoreBean;
    }
    @Override
    public IBeanConverter<LogLightBean, R_LOGLIGHT> getLogLightBeanConverter() {
        return converterLogLightBean;
    }
}
