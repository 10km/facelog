// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: idbconverter.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

/**
 * converter between general type({@code <L>}) and native type ({@code <R>}) for  all Beans
 * @author guyadong
 *
 */
public interface IDbConverter<
                            N_DEVICE,
                            N_DEVICEGROUP,
                            N_FACE,
                            N_FEATURE,
                            N_IMAGE,
                            N_LOG,
                            N_PERMIT,
                            N_PERSON,
                            N_PERSONGROUP,
                            N_STORE,
                            N_LOGLIGHT> {
    /**
     * return a converter {@code DeviceBean -> N_DEVICE}
     * @return
     */
    public abstract IBeanConverter<DeviceBean, N_DEVICE> getDeviceBeanConverter();
    /**
     * return a converter {@code DeviceGroupBean -> N_DEVICEGROUP}
     * @return
     */
    public abstract IBeanConverter<DeviceGroupBean, N_DEVICEGROUP> getDeviceGroupBeanConverter();
    /**
     * return a converter {@code FaceBean -> N_FACE}
     * @return
     */
    public abstract IBeanConverter<FaceBean, N_FACE> getFaceBeanConverter();
    /**
     * return a converter {@code FeatureBean -> N_FEATURE}
     * @return
     */
    public abstract IBeanConverter<FeatureBean, N_FEATURE> getFeatureBeanConverter();
    /**
     * return a converter {@code ImageBean -> N_IMAGE}
     * @return
     */
    public abstract IBeanConverter<ImageBean, N_IMAGE> getImageBeanConverter();
    /**
     * return a converter {@code LogBean -> N_LOG}
     * @return
     */
    public abstract IBeanConverter<LogBean, N_LOG> getLogBeanConverter();
    /**
     * return a converter {@code PermitBean -> N_PERMIT}
     * @return
     */
    public abstract IBeanConverter<PermitBean, N_PERMIT> getPermitBeanConverter();
    /**
     * return a converter {@code PersonBean -> N_PERSON}
     * @return
     */
    public abstract IBeanConverter<PersonBean, N_PERSON> getPersonBeanConverter();
    /**
     * return a converter {@code PersonGroupBean -> N_PERSONGROUP}
     * @return
     */
    public abstract IBeanConverter<PersonGroupBean, N_PERSONGROUP> getPersonGroupBeanConverter();
    /**
     * return a converter {@code StoreBean -> N_STORE}
     * @return
     */
    public abstract IBeanConverter<StoreBean, N_STORE> getStoreBeanConverter();
    /**
     * return a converter {@code LogLightBean -> N_LOGLIGHT}
     * @return
     */
    public abstract IBeanConverter<LogLightBean, N_LOGLIGHT> getLogLightBeanConverter();

    /**
     * return a converter {@code L - > R}
     * @param <L> general type
     * @param <R> native type
     * @param lClass
     * @param rClass
     * @return
     */
    public abstract<L, R> IBeanConverter<L,R> getBeanConverter(Class<L> lClass,Class<R> rClass);
    /**
     * set converter 
     * @param <L> general type
     * @param <R> native type
     * @param lClass
     * @param rClass
     * @param converter
     */
    public abstract <L, R> void setBeanConverter(Class<L> lClass, Class<R> rClass, IBeanConverter<L,R>converter);

}