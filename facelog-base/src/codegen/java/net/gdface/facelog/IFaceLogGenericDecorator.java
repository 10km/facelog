package net.gdface.facelog;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.utils.FaceUtilits;
/**
 * decorator pattern 装饰者模式实现基于{@link IFaceLogDecorator}的支持泛型方法参数{@link IFaceLog}接口<br>
 * 所有 byte[] 类型的参数都支持泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}<br>
 * 注意:<br>
 * 参数为{@link java.io.InputStream}的方法返回时会自动执行{@link java.io.InputStream#close()}关闭流<br>
 * 参见 {@link net.gdface.utils.FaceUtilits#readBytes(java.io.InputStream)}<br>
 * unchecked后缀的方法将所有显式申明的异常封装到{@link RuntimeException}抛出<br>
 * 计算机生成代码(generated by automated tools DecoratorGenerator @author guyadong)<br>
 * @author guyadong
 *
 */
public class IFaceLogGenericDecorator extends IFaceLogDecorator{
    public IFaceLogGenericDecorator(IFaceLog delegate) {
        super(delegate);
    }
    //1
    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.Integer,boolean,byte[],FaceBean,java.lang.Integer,Token)}对应的泛型方法<br>
     * @param feature
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param personId
     * @param asIdPhotoIfAbsent
     * @param featurePhoto
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param faceBean
     * @param deviceId
     * @param token
     * @return 

     * @see IFaceLog#addFeature(byte[],java.lang.Integer,boolean,byte[],FaceBean,java.lang.Integer,Token)
     */    
    public <T> FeatureBean addFeature (T feature,Integer personId,boolean asIdPhotoIfAbsent,T featurePhoto,FaceBean faceBean,Integer deviceId,Token token) throws DuplicateRecordException,IOException{
        return addFeature(FaceUtilits.getBytesNotEmpty(feature),personId,asIdPhotoIfAbsent,FaceUtilits.getBytesNotEmpty(featurePhoto),faceBean,deviceId,token);
    }
    //2
    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.Integer,boolean,byte[],FaceBean,java.lang.Integer,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param feature
     * @param personId
     * @param asIdPhotoIfAbsent
     * @param featurePhoto
     * @param faceBean
     * @param deviceId
     * @param token
     * @return FeatureBean
     */
    public <T> FeatureBean addFeatureUnchecked (T feature,Integer personId,boolean asIdPhotoIfAbsent,T featurePhoto,FaceBean faceBean,Integer deviceId,Token token) {
        try{
            return addFeature(feature,personId,asIdPhotoIfAbsent,featurePhoto,faceBean,deviceId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //3
    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.Integer,List,Token)}对应的泛型方法<br>
     * @param feature
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param personId
     * @param faecBeans
     * @param token
     * @return 

     * @throws DuplicateRecordException
     * @throws IOException
     * @see IFaceLog#addFeature(byte[],java.lang.Integer,List,Token)
     */    
    public <T> FeatureBean addFeature (T feature,Integer personId,List<FaceBean> faecBeans,Token token) throws DuplicateRecordException,IOException{
        return addFeature(FaceUtilits.getBytesNotEmpty(feature),personId,faecBeans,token);
    }
    //4
    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.Integer,List,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param feature
     * @param personId
     * @param faecBeans
     * @param token
     * @return FeatureBean
     */
    public <T> FeatureBean addFeatureUnchecked (T feature,Integer personId,List<FaceBean> faecBeans,Token token) {
        try{
            return addFeature(feature,personId,faecBeans,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //5
    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.Integer,Map,java.lang.Integer,Token)}对应的泛型方法<br>
     * @param feature
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param personId
     * @param faceInfo
     * @param deviceId
     * @param token
     * @return 

     * @throws DuplicateRecordException
     * @throws IOException
     * @see IFaceLog#addFeature(byte[],java.lang.Integer,Map,java.lang.Integer,Token)
     */    
    public <T> FeatureBean addFeature (T feature,Integer personId,Map<java.nio.ByteBuffer, FaceBean> faceInfo,Integer deviceId,Token token) throws DuplicateRecordException,IOException{
        return addFeature(FaceUtilits.getBytesNotEmpty(feature),personId,faceInfo,deviceId,token);
    }
    //6
    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.Integer,Map,java.lang.Integer,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param feature
     * @param personId
     * @param faceInfo
     * @param deviceId
     * @param token
     * @return FeatureBean
     */
    public <T> FeatureBean addFeatureUnchecked (T feature,Integer personId,Map<java.nio.ByteBuffer, FaceBean> faceInfo,Integer deviceId,Token token) {
        try{
            return addFeature(feature,personId,faceInfo,deviceId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //7
    /**
     * {@link IFaceLog#addImage(byte[],java.lang.Integer,FaceBean,java.lang.Integer,Token)}对应的泛型方法<br>
     * @param imageData
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param deviceId
     * @param faceBean
     * @param personId
     * @param token
     * @return 

     * @throws DuplicateRecordException
     * @throws IOException
     * @see IFaceLog#addImage(byte[],java.lang.Integer,FaceBean,java.lang.Integer,Token)
     */    
    public <T> ImageBean addImage (T imageData,Integer deviceId,FaceBean faceBean,Integer personId,Token token) throws DuplicateRecordException,IOException{
        return addImage(FaceUtilits.getBytesNotEmpty(imageData),deviceId,faceBean,personId,token);
    }
    //8
    /**
     * {@link IFaceLog#addImage(byte[],java.lang.Integer,FaceBean,java.lang.Integer,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param imageData
     * @param deviceId
     * @param faceBean
     * @param personId
     * @param token
     * @return ImageBean
     */
    public <T> ImageBean addImageUnchecked (T imageData,Integer deviceId,FaceBean faceBean,Integer personId,Token token) {
        try{
            return addImage(imageData,deviceId,faceBean,personId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //9
    /**
     * {@link IFaceLog#addLog(LogBean,FaceBean,byte[],Token)}对应的泛型方法<br>
     * @param logBean
     * @param faceBean
     * @param featureImage
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param token
     * @return 

     * @throws DuplicateRecordException
     * @throws IOException
     * @see IFaceLog#addLog(LogBean,FaceBean,byte[],Token)
     */    
    public <T> void addLog (LogBean logBean,FaceBean faceBean,T featureImage,Token token) throws DuplicateRecordException,IOException{
         addLog(logBean,faceBean,FaceUtilits.getBytesNotEmpty(featureImage),token);
    }
    //10
    /**
     * {@link IFaceLog#addLog(LogBean,FaceBean,byte[],Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param logBean
     * @param faceBean
     * @param featureImage
     * @param token
     * @return void
     */
    public <T> void addLogUnchecked (LogBean logBean,FaceBean faceBean,T featureImage,Token token) {
        try{
             addLog(logBean,faceBean,featureImage,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //11
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],byte[],byte[],FaceBean,java.lang.Integer,Token)}对应的泛型方法<br>
     * @param personBean
     * @param idPhoto
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param feature
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param featureImage
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param featureFaceBean
     * @param deviceId
     * @param token
     * @return 

     * @throws DuplicateRecordException
     * @throws IOException
     * @see IFaceLog#savePerson(PersonBean,byte[],byte[],byte[],FaceBean,java.lang.Integer,Token)
     */    
    public <T> PersonBean savePerson (PersonBean personBean,T idPhoto,T feature,T featureImage,FaceBean featureFaceBean,Integer deviceId,Token token) throws IOException{
        return savePerson(personBean,FaceUtilits.getBytesNotEmpty(idPhoto),FaceUtilits.getBytesNotEmpty(feature),FaceUtilits.getBytesNotEmpty(featureImage),featureFaceBean,deviceId,token);
    }
    //12
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],byte[],byte[],FaceBean,java.lang.Integer,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param personBean
     * @param idPhoto
     * @param feature
     * @param featureImage
     * @param featureFaceBean
     * @param deviceId
     * @param token
     * @return PersonBean
     */
    public <T> PersonBean savePersonUnchecked (PersonBean personBean,T idPhoto,T feature,T featureImage,FaceBean featureFaceBean,Integer deviceId,Token token) {
        try{
            return savePerson(personBean,idPhoto,feature,featureImage,featureFaceBean,deviceId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //13
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],byte[],List,Token)}对应的泛型方法<br>
     * @param personBean
     * @param idPhoto
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param feature
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param faceBeans
     * @param token
     * @return 

     * @throws IOException
     * @see IFaceLog#savePerson(PersonBean,byte[],byte[],List,Token)
     */    
    public <T> PersonBean savePerson (PersonBean personBean,T idPhoto,T feature,List<FaceBean> faceBeans,Token token) throws IOException{
        return savePerson(personBean,FaceUtilits.getBytesNotEmpty(idPhoto),FaceUtilits.getBytesNotEmpty(feature),faceBeans,token);
    }
    //14
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],byte[],List,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param personBean
     * @param idPhoto
     * @param feature
     * @param faceBeans
     * @param token
     * @return PersonBean
     */
    public <T> PersonBean savePersonUnchecked (PersonBean personBean,T idPhoto,T feature,List<FaceBean> faceBeans,Token token) {
        try{
            return savePerson(personBean,idPhoto,feature,faceBeans,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //15
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],byte[],Map,java.lang.Integer,Token)}对应的泛型方法<br>
     * @param personBean
     * @param idPhoto
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param feature
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param faceInfo
     * @param deviceId
     * @param token
     * @return 

     * @throws IOException
     * @see IFaceLog#savePerson(PersonBean,byte[],byte[],Map,java.lang.Integer,Token)
     */    
    public <T> PersonBean savePerson (PersonBean personBean,T idPhoto,T feature,Map<java.nio.ByteBuffer, FaceBean> faceInfo,Integer deviceId,Token token) throws IOException{
        return savePerson(personBean,FaceUtilits.getBytesNotEmpty(idPhoto),FaceUtilits.getBytesNotEmpty(feature),faceInfo,deviceId,token);
    }
    //16
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],byte[],Map,java.lang.Integer,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param personBean
     * @param idPhoto
     * @param feature
     * @param faceInfo
     * @param deviceId
     * @param token
     * @return PersonBean
     */
    public <T> PersonBean savePersonUnchecked (PersonBean personBean,T idPhoto,T feature,Map<java.nio.ByteBuffer, FaceBean> faceInfo,Integer deviceId,Token token) {
        try{
            return savePerson(personBean,idPhoto,feature,faceInfo,deviceId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //17
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],Token)}对应的泛型方法<br>
     * @param personBean
     * @param idPhoto
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param token
     * @return 

     * @throws IOException
     * @see IFaceLog#savePerson(PersonBean,byte[],Token)
     */    
    public <T> PersonBean savePerson (PersonBean personBean,T idPhoto,Token token) throws IOException{
        return savePerson(personBean,FaceUtilits.getBytesNotEmpty(idPhoto),token);
    }
    //18
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param personBean
     * @param idPhoto
     * @param token
     * @return PersonBean
     */
    public <T> PersonBean savePersonUnchecked (PersonBean personBean,T idPhoto,Token token) {
        try{
            return savePerson(personBean,idPhoto,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //19
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],FeatureBean,java.lang.Integer,Token)}对应的泛型方法<br>
     * @param personBean
     * @param idPhoto
     *            泛型参数,参见 {@link net.gdface.utils.FaceUtilits#getBytesNotEmpty(Object)}
     * @param featureBean
     * @param deviceId
     * @param token
     * @return 

     * @throws IOException
     * @see IFaceLog#savePerson(PersonBean,byte[],FeatureBean,java.lang.Integer,Token)
     */    
    public <T> PersonBean savePerson (PersonBean personBean,T idPhoto,FeatureBean featureBean,Integer deviceId,Token token) throws IOException{
        return savePerson(personBean,FaceUtilits.getBytesNotEmpty(idPhoto),featureBean,deviceId,token);
    }
    //20
    /**
     * {@link IFaceLog#savePerson(PersonBean,byte[],FeatureBean,java.lang.Integer,Token)}泛型方法对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param personBean
     * @param idPhoto
     * @param featureBean
     * @param deviceId
     * @param token
     * @return PersonBean
     */
    public <T> PersonBean savePersonUnchecked (PersonBean personBean,T idPhoto,FeatureBean featureBean,Integer deviceId,Token token) {
        try{
            return savePerson(personBean,idPhoto,featureBean,deviceId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}