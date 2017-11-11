// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: manager.java.vm
// ______________________________________________________
package net.gdface.facelog.db.mysql;

import java.util.concurrent.Callable;

import net.gdface.facelog.db.Constant;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.ILogLightManager;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.WrapDAOException;
import net.gdface.facelog.db.exception.ObjectRetrievalException;

import net.gdface.facelog.dborm.exception.DAOException;

/**
 * Handles database calls (save, load, count, etc...) for the fl_log_light table.<br>
 * all {@link DAOException} be wrapped as {@link WrapDAOException} to throw.<br>
 * Remarks: VIEW<br>
 * @author guyadong
 */
public class LogLightManager extends TableManager.BaseAdapter<LogLightBean> implements ILogLightManager
{
    private net.gdface.facelog.dborm.log.FlLogLightManager nativeManager = net.gdface.facelog.dborm.log.FlLogLightManager.getInstance();
    private IDbConverter<
                        net.gdface.facelog.dborm.device.FlDeviceBean,
                        net.gdface.facelog.dborm.device.FlDeviceGroupBean,
                        net.gdface.facelog.dborm.face.FlFaceBean,
                        net.gdface.facelog.dborm.face.FlFeatureBean,
                        net.gdface.facelog.dborm.image.FlImageBean,
                        net.gdface.facelog.dborm.log.FlLogBean,
                        net.gdface.facelog.dborm.permit.FlPermitBean,
                        net.gdface.facelog.dborm.person.FlPersonBean,
                        net.gdface.facelog.dborm.person.FlPersonGroupBean,
                        net.gdface.facelog.dborm.image.FlStoreBean,
                        net.gdface.facelog.dborm.log.FlLogLightBean> dbConverter = DbConverter.INSTANCE;
    private IBeanConverter<LogLightBean,net.gdface.facelog.dborm.log.FlLogLightBean> beanConverter = dbConverter.getLogLightBeanConverter();
    private static LogLightManager singleton = new LogLightManager();
    protected LogLightManager(){}
    
    /**
     * @return table name
     */
    @Override
    public String getTableName() {
        return this.nativeManager.getTableName();
    }

    /**
     * @return field names of table
     */
    @Override
    public String getFields() {
        return this.nativeManager.getFields();
    }

    @Override
    public String getFullFields() {
        return this.nativeManager.getFullFields();
    }
    
    /**
     * @return primarykeyNames
     */
    @Override
    public String[] getPrimarykeyNames() {
        return this.nativeManager.getPrimarykeyNames();
    }
    
    /**
     * Get the {@link LogLightManager} singleton.
     *
     * @return {@link LogLightManager}
     */
    public static LogLightManager getInstance()
    {
        return singleton;
    }
   
    @Override
    protected Class<LogLightBean> beanType(){
        return LogLightBean.class;
    }
    
    public IDbConverter<net.gdface.facelog.dborm.device.FlDeviceBean,net.gdface.facelog.dborm.device.FlDeviceGroupBean,net.gdface.facelog.dborm.face.FlFaceBean,net.gdface.facelog.dborm.face.FlFeatureBean,net.gdface.facelog.dborm.image.FlImageBean,net.gdface.facelog.dborm.log.FlLogBean,net.gdface.facelog.dborm.permit.FlPermitBean,net.gdface.facelog.dborm.person.FlPersonBean,net.gdface.facelog.dborm.person.FlPersonGroupBean,net.gdface.facelog.dborm.image.FlStoreBean,net.gdface.facelog.dborm.log.FlLogLightBean> getDbConverter() {
        return dbConverter;
    }

    /**
     * set  {@link IDbConverter} as converter used by manager.<br>
     * throw {@link NullPointerException} if {@code dbConverter} is null
     * @param dbConverter
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public synchronized void setDbConverter(IDbConverter dbConverter) {
        if( null == dbConverter){
            throw new NullPointerException();
        }
        this.dbConverter = dbConverter;
        this.beanConverter = this.dbConverter.getLogLightBeanConverter();
    }
 
 


     

    //////////////////////////////////////
    // SQL 'WHERE' METHOD
    //////////////////////////////////////

    //11
    @Override
    public int deleteByWhere(String where)
    {
        try{
            return this.nativeManager.deleteByWhere(where);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //_____________________________________________________________________
    //
    // SAVE
    //_____________________________________________________________________

    //13
    @Override
    protected LogLightBean insert(LogLightBean bean)
    {
        try{
            return this.beanConverter.fromRight(bean,this.nativeManager.insert(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //14
    @Override
    protected LogLightBean update(LogLightBean bean)
    {
        try{
            return this.beanConverter.fromRight(bean,this.nativeManager.update(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //_____________________________________________________________________
    //
    // USING TEMPLATE
    //_____________________________________________________________________
    //18
    @Override
    public LogLightBean loadUniqueUsingTemplate(LogLightBean bean)
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadUniqueUsingTemplate(this.beanConverter.toRight(bean)));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
     }
    //18-1
    @Override
    public LogLightBean loadUniqueUsingTemplateChecked(LogLightBean bean) throws ObjectRetrievalException
    {
        try{
            return this.beanConverter.fromRight(this.nativeManager.loadUniqueUsingTemplateChecked(this.beanConverter.toRight(bean)));
        }
        catch(net.gdface.facelog.dborm.exception.ObjectRetrievalException e)
        {
            throw new ObjectRetrievalException();
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
     }
    //20-5
    @Override
    public int loadUsingTemplate(LogLightBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<LogLightBean> action)
    {
        try {
            return this.nativeManager.loadUsingTemplate(this.beanConverter.toRight(bean),fieldList,startRow,numRows,searchType,this.toNative(action));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //21
    @Override
    public int deleteUsingTemplate(LogLightBean bean)
    {
        try{
            return this.nativeManager.deleteUsingTemplate(this.beanConverter.toRight(bean));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }


    //_____________________________________________________________________
    //
    // COUNT
    //_____________________________________________________________________
    //25
    @Override
    public int countWhere(String where)
    {
        try{
            return this.nativeManager.countWhere(where);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }

    //20
    @Override
    public int countUsingTemplate(LogLightBean bean, int searchType)
    {
        try{
            return this.nativeManager.countUsingTemplate(this.beanConverter.toRight(bean),searchType);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }


    //_____________________________________________________________________
    //
    // LISTENER
    //_____________________________________________________________________

    /**
     * @return {@link WrapListener} instance
     */
    //35
    @Override
    public TableListener<LogLightBean> registerListener(TableListener<LogLightBean> listener)
    {
        WrapListener wrapListener;
        if(listener instanceof WrapListener){
            wrapListener = (WrapListener)listener;
            this.nativeManager.registerListener(wrapListener.nativeListener);
        }else{
            wrapListener = new WrapListener(listener);
            this.nativeManager.registerListener(wrapListener.nativeListener);
        }
        return wrapListener;
    }

    //36
    @Override
    public void unregisterListener(TableListener<LogLightBean> listener)
    {
        if(!(listener instanceof WrapListener)){
            throw new IllegalArgumentException("invalid listener type: " + WrapListener.class.getName() +" required");
        }
        this.nativeManager.unregisterListener(((WrapListener)listener).nativeListener);
    }
    
    //37
    @Override
    public void fire(TableListener.Event event, LogLightBean bean){
        fire(event.ordinal(), bean);
    }
    
    //37-1
    @Override
    public void fire(int event, LogLightBean bean){
        try{
            this.nativeManager.fire(event, this.beanConverter.toRight(bean));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    /**
     * bind foreign key listener to foreign table for DELETE RULE
     */
    //37-2
    void bindForeignKeyListenerForDeleteRule(){
        this.nativeManager.bindForeignKeyListenerForDeleteRule();
    }
    /**
     * unbind foreign key listener from all of foreign tables <br>
     * @see #bindForeignKeyListenerForDeleteRule()
     */
    //37-3
    void unbindForeignKeyListenerForDeleteRule(){
        this.nativeManager.unbindForeignKeyListenerForDeleteRule();

    }
    /**
     * wrap {@code TableListener<LogLightBean>} as native listener
     *
     */
    public class WrapListener implements TableListener<LogLightBean>{
        private final TableListener<LogLightBean> listener;
        private final net.gdface.facelog.dborm.TableListener<net.gdface.facelog.dborm.log.FlLogLightBean> nativeListener;
        private WrapListener(final TableListener<LogLightBean> listener) {
            if(null == listener){
                throw new NullPointerException();
            }
            this.listener = listener;
            this.nativeListener = new net.gdface.facelog.dborm.TableListener<net.gdface.facelog.dborm.log.FlLogLightBean> (){

                @Override
                public void beforeInsert(net.gdface.facelog.dborm.log.FlLogLightBean bean) throws DAOException {
                    listener.beforeInsert(LogLightManager.this.beanConverter.fromRight(bean));                
                }

                @Override
                public void afterInsert(net.gdface.facelog.dborm.log.FlLogLightBean bean) throws DAOException {
                    listener.afterInsert(LogLightManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void beforeUpdate(net.gdface.facelog.dborm.log.FlLogLightBean bean) throws DAOException {
                    listener.beforeUpdate(LogLightManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void afterUpdate(net.gdface.facelog.dborm.log.FlLogLightBean bean) throws DAOException {
                    listener.afterUpdate(LogLightManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void beforeDelete(net.gdface.facelog.dborm.log.FlLogLightBean bean) throws DAOException {
                    listener.beforeDelete(LogLightManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void afterDelete(net.gdface.facelog.dborm.log.FlLogLightBean bean) throws DAOException {
                    listener.afterDelete(LogLightManager.this.beanConverter.fromRight(bean));
                }};
        }

        @Override
        public void beforeInsert(LogLightBean bean) {
            listener.beforeInsert(bean);
        }

        @Override
        public void afterInsert(LogLightBean bean) {
            listener.afterInsert(bean);
        }

        @Override
        public void beforeUpdate(LogLightBean bean) {
            listener.beforeUpdate(bean);
        }

        @Override
        public void afterUpdate(LogLightBean bean) {
            listener.afterUpdate(bean);
        }

        @Override
        public void beforeDelete(LogLightBean bean) {
            listener.beforeDelete(bean);
        }

        @Override
        public void afterDelete(LogLightBean bean) {
            listener.afterDelete(bean);
        }        
    }

    //_____________________________________________________________________
    //
    // UTILS
    //_____________________________________________________________________

    //43
    @Override
    public boolean isPrimaryKey(String column){
        return this.nativeManager.isPrimaryKey(column);
    }
    
    @Override
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<LogLightBean> action){
        try{
            return this.nativeManager.loadBySqlForAction(sql,argList,fieldList,startRow,numRows,this.toNative(action));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    @Override
    public <T>T runAsTransaction(Callable<T> fun) {
        try{
            return this.nativeManager.runAsTransaction(fun);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    
    private net.gdface.facelog.dborm.TableManager.Action<net.gdface.facelog.dborm.log.FlLogLightBean> toNative(final Action<LogLightBean> action){
        if(null == action){
            throw new NullPointerException();
        }
        return new net.gdface.facelog.dborm.TableManager.Action<net.gdface.facelog.dborm.log.FlLogLightBean>(){

            @Override
            public void call(net.gdface.facelog.dborm.log.FlLogLightBean bean) {
                action.call(LogLightManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public net.gdface.facelog.dborm.log.FlLogLightBean getBean() {
                return  LogLightManager.this.beanConverter.toRight(action.getBean());
            }};
    }
    
}
