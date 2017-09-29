// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db;

/**
 * converter between general type({@code <L>}) and native type ({@code <R>}) for  all Beans
 * @author guyadong
 *
 */
public interface IDbConverter<N_DEVICE,N_FACE,N_IMAGE,N_LOG,N_PERSON,N_STORE,N_LOGLIGHT> {
    public abstract IBeanConverter<DeviceBean, N_DEVICE> getDeviceBeanConverter();
    public abstract IBeanConverter<FaceBean, N_FACE> getFaceBeanConverter();
    public abstract IBeanConverter<ImageBean, N_IMAGE> getImageBeanConverter();
    public abstract IBeanConverter<LogBean, N_LOG> getLogBeanConverter();
    public abstract IBeanConverter<PersonBean, N_PERSON> getPersonBeanConverter();
    public abstract IBeanConverter<StoreBean, N_STORE> getStoreBeanConverter();
    public abstract IBeanConverter<LogLightBean, N_LOGLIGHT> getLogLightBeanConverter();

    /**
     * @param <L> general type
     * @param <R> native type
     * @param lClass
     * @param rClass
     * @return
     */
    public abstract<L, R> IBeanConverter<L,R> getBeanConverter(Class<L> lClass,Class<R> rClass);
    /**
     * @param <L> general type
     * @param <R> native type
     * @param lClass
     * @param rClass
     * @param converter
     */
    public abstract <L, R> void setBeanConverter(Class<L> lClass, Class<R> rClass, IBeanConverter<L,R>converter);

}