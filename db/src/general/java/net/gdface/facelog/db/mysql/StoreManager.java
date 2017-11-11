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
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.IStoreManager;
import net.gdface.facelog.db.TableListener;
import net.gdface.facelog.db.exception.WrapDAOException;
import net.gdface.facelog.db.exception.ObjectRetrievalException;

import net.gdface.facelog.dborm.exception.DAOException;

/**
 * Handles database calls (save, load, count, etc...) for the fl_store table.<br>
 * all {@link DAOException} be wrapped as {@link WrapDAOException} to throw.<br>
 * Remarks: 二进制数据存储表<br>
 * @author guyadong
 */
public class StoreManager extends TableManager.BaseAdapter<StoreBean> implements IStoreManager
{
    private net.gdface.facelog.dborm.image.FlStoreManager nativeManager = net.gdface.facelog.dborm.image.FlStoreManager.getInstance();
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
    private IBeanConverter<StoreBean,net.gdface.facelog.dborm.image.FlStoreBean> beanConverter = dbConverter.getStoreBeanConverter();
    private static StoreManager singleton = new StoreManager();
    protected StoreManager(){}
    
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
     * Get the {@link StoreManager} singleton.
     *
     * @return {@link StoreManager}
     */
    public static StoreManager getInstance()
    {
        return singleton;
    }
   
    @Override
    protected Class<StoreBean> beanType(){
        return StoreBean.class;
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
        this.beanConverter = this.dbConverter.getStoreBeanConverter();
    }
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    //1 override IStoreManager
    @Override 
    public StoreBean loadByPrimaryKey(String md5)
    {
        try{
            return loadByPrimaryKeyChecked(md5);
        }catch(ObjectRetrievalException e){
            // not found
            return null;
        }
    }
    //1.1 override IStoreManager
    @Override
    public StoreBean loadByPrimaryKeyChecked(String md5) throws ObjectRetrievalException
    {
        try{
            return this.beanConverter.fromRight(nativeManager.loadByPrimaryKeyChecked(md5));
        }catch(net.gdface.facelog.dborm.exception.ObjectRetrievalException e){
            throw new ObjectRetrievalException();
        }catch(DAOException e){
            throw new WrapDAOException(e);
        }
    }
    //1.2
    @Override
    public StoreBean loadByPrimaryKey(StoreBean bean)
    {
        return bean==null?null:loadByPrimaryKey(bean.getMd5());
    }

    //1.2.2
    @Override
    public StoreBean loadByPrimaryKeyChecked(StoreBean bean) throws ObjectRetrievalException
    {
        if(null == bean){
            throw new NullPointerException();
        }
        return loadByPrimaryKeyChecked(bean.getMd5());
    }
    
    //1.3
    @Override
    public StoreBean loadByPrimaryKey(Object ...keys){
        try{
            return loadByPrimaryKeyChecked(keys);
        }catch(ObjectRetrievalException e){
            // not found
            return null;
        }
    }
    
    //1.3.2
    @Override
    public StoreBean loadByPrimaryKeyChecked(Object ...keys) throws ObjectRetrievalException{
        if(null == keys){
            throw new NullPointerException();
        }
        if(keys.length != 1){
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        }
        
        if(! (keys[0] instanceof String)){
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:String");
        }
        return loadByPrimaryKeyChecked((String)keys[0]);
    }

    //1.4 override IStoreManager
    @Override 
    public boolean existsPrimaryKey(String md5)
    {
        try{
            return nativeManager.existsPrimaryKey(md5);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //1.6
    @Override
    public boolean existsByPrimaryKey(StoreBean bean)
    {
        return null == bean ? false : existsPrimaryKey(bean.getMd5());
    }
    //1.7
    @Override
    public StoreBean checkDuplicate(StoreBean bean)throws ObjectRetrievalException{
        if(null != bean){
            checkDuplicate(bean.getMd5());
        }
        return bean;   
    }
    //1.4.1 override IStoreManager
    @Override 
    public String checkDuplicate(String md5)throws ObjectRetrievalException{
        try{
            return this.nativeManager.checkDuplicate(md5);
        }catch(net.gdface.facelog.dborm.exception.ObjectRetrievalException e){
        	throw new ObjectRetrievalException(e);
        }catch(DAOException e){
            throw new WrapDAOException(e);
        }
    }
    //1.8 override IStoreManager
    @Override 
    public java.util.List<StoreBean> loadByPrimaryKey(String... keys){
        if(null == keys){
            return new java.util.ArrayList<StoreBean>();
        }
        java.util.ArrayList<StoreBean> list = new java.util.ArrayList<StoreBean>(keys.length);
        for(int i = 0 ;i< keys.length;++i){
            list.add(loadByPrimaryKey(keys[i]));
        }
        return list;
    }
    //1.9 override IStoreManager
    @Override 
    public java.util.List<StoreBean> loadByPrimaryKey(java.util.Collection<String> keys){
        if(null == keys ){
            return new java.util.ArrayList<StoreBean>();
        }
        java.util.ArrayList<StoreBean> list = new java.util.ArrayList<StoreBean>(keys.size());
        if(keys instanceof java.util.List){
            for(String key: keys){
                list.add(loadByPrimaryKey(key));
            }
        }else{
            StoreBean bean;
            for(String key: keys){
                if(null != (bean = loadByPrimaryKey(key))){
                    list.add(bean);
                }
            }
        }
        return list;
    }
    //2 override IStoreManager
    @Override 
    public int deleteByPrimaryKey(String md5)
    {
        try
        {
            return nativeManager.deleteByPrimaryKey(md5);
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }
    }
    //2
    @Override
    public int delete(StoreBean bean){
        try
        {
            return nativeManager.delete(this.beanConverter.toRight(bean));
        }
        catch(DAOException e)
        {
            throw new WrapDAOException(e);
        }   
    }
    //2.1
    @Override
    public int deleteByPrimaryKey(Object ...keys){
        if(null == keys){
            throw new NullPointerException();
        }
        if(keys.length != 1){
            throw new IllegalArgumentException("argument number mismatch with primary key number");
        }
        if(! (keys[0] instanceof String)){
            throw new IllegalArgumentException("invalid type for the No.1 argument,expected type:String");
        }
        return deleteByPrimaryKey((String)keys[0]);
    }
    //2.2 override IStoreManager
    @Override 
    public int deleteByPrimaryKey(String... keys){
        int count = 0;
        if(null != keys){        
            for(String key:keys){
                count += deleteByPrimaryKey(key);
            }
        }
        return count;
    }
    //2.3 override IStoreManager
    @Override 
    public int deleteByPrimaryKey(java.util.Collection<String> keys){
        int count = 0;
        if(null != keys){        
            for(String key :keys){
                count += deleteByPrimaryKey(key);
            }
        }
        return count;
    }
    //2.4 override IStoreManager
    @Override 
    public int delete(StoreBean... beans){
        int count = 0;
        if(null != beans){
            for(StoreBean bean :beans){
                count += delete(bean);
            }
        }
        return count;
    }
    //2.5 override IStoreManager
    @Override 
    public int delete(java.util.Collection<StoreBean> beans){
        int count = 0;
        if(null != beans){
            for(StoreBean bean :beans){
                count += delete(bean);
            }
        }
        return count;
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
    protected StoreBean insert(StoreBean bean)
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
    protected StoreBean update(StoreBean bean)
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
    public StoreBean loadUniqueUsingTemplate(StoreBean bean)
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
    public StoreBean loadUniqueUsingTemplateChecked(StoreBean bean) throws ObjectRetrievalException
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
    public int loadUsingTemplate(StoreBean bean, int[] fieldList, int startRow, int numRows,int searchType, Action<StoreBean> action)
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
    public int deleteUsingTemplate(StoreBean bean)
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
    public int countUsingTemplate(StoreBean bean, int searchType)
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
    public TableListener<StoreBean> registerListener(TableListener<StoreBean> listener)
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
    public void unregisterListener(TableListener<StoreBean> listener)
    {
        if(!(listener instanceof WrapListener)){
            throw new IllegalArgumentException("invalid listener type: " + WrapListener.class.getName() +" required");
        }
        this.nativeManager.unregisterListener(((WrapListener)listener).nativeListener);
    }
    
    //37
    @Override
    public void fire(TableListener.Event event, StoreBean bean){
        fire(event.ordinal(), bean);
    }
    
    //37-1
    @Override
    public void fire(int event, StoreBean bean){
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
     * wrap {@code TableListener<StoreBean>} as native listener
     *
     */
    public class WrapListener implements TableListener<StoreBean>{
        private final TableListener<StoreBean> listener;
        private final net.gdface.facelog.dborm.TableListener<net.gdface.facelog.dborm.image.FlStoreBean> nativeListener;
        private WrapListener(final TableListener<StoreBean> listener) {
            if(null == listener){
                throw new NullPointerException();
            }
            this.listener = listener;
            this.nativeListener = new net.gdface.facelog.dborm.TableListener<net.gdface.facelog.dborm.image.FlStoreBean> (){

                @Override
                public void beforeInsert(net.gdface.facelog.dborm.image.FlStoreBean bean) throws DAOException {
                    listener.beforeInsert(StoreManager.this.beanConverter.fromRight(bean));                
                }

                @Override
                public void afterInsert(net.gdface.facelog.dborm.image.FlStoreBean bean) throws DAOException {
                    listener.afterInsert(StoreManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void beforeUpdate(net.gdface.facelog.dborm.image.FlStoreBean bean) throws DAOException {
                    listener.beforeUpdate(StoreManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void afterUpdate(net.gdface.facelog.dborm.image.FlStoreBean bean) throws DAOException {
                    listener.afterUpdate(StoreManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void beforeDelete(net.gdface.facelog.dborm.image.FlStoreBean bean) throws DAOException {
                    listener.beforeDelete(StoreManager.this.beanConverter.fromRight(bean));
                }

                @Override
                public void afterDelete(net.gdface.facelog.dborm.image.FlStoreBean bean) throws DAOException {
                    listener.afterDelete(StoreManager.this.beanConverter.fromRight(bean));
                }};
        }

        @Override
        public void beforeInsert(StoreBean bean) {
            listener.beforeInsert(bean);
        }

        @Override
        public void afterInsert(StoreBean bean) {
            listener.afterInsert(bean);
        }

        @Override
        public void beforeUpdate(StoreBean bean) {
            listener.beforeUpdate(bean);
        }

        @Override
        public void afterUpdate(StoreBean bean) {
            listener.afterUpdate(bean);
        }

        @Override
        public void beforeDelete(StoreBean bean) {
            listener.beforeDelete(bean);
        }

        @Override
        public void afterDelete(StoreBean bean) {
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
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<StoreBean> action){
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
    
    private net.gdface.facelog.dborm.TableManager.Action<net.gdface.facelog.dborm.image.FlStoreBean> toNative(final Action<StoreBean> action){
        if(null == action){
            throw new NullPointerException();
        }
        return new net.gdface.facelog.dborm.TableManager.Action<net.gdface.facelog.dborm.image.FlStoreBean>(){

            @Override
            public void call(net.gdface.facelog.dborm.image.FlStoreBean bean) {
                action.call(StoreManager.this.beanConverter.fromRight(bean));
            }

            @Override
            public net.gdface.facelog.dborm.image.FlStoreBean getBean() {
                return  StoreManager.this.beanConverter.toRight(action.getBean());
            }};
    }
    
    //45 override IStoreManager
    @Override 
    public java.util.List<String> toPrimaryKeyList(StoreBean... array){        
        if(null == array){
            return new java.util.ArrayList<String>();
        }
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(array.length);
        for(StoreBean bean:array){
            list.add(null == bean ? null : bean.getMd5());
        }
        return list;
    }
    //46 override IStoreManager
    @Override 
    public java.util.List<String> toPrimaryKeyList(java.util.Collection<StoreBean> collection){        
        if(null == collection){
            return new java.util.ArrayList<String>();
        }
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(collection.size());
        for(StoreBean bean:collection){
            list.add(null == bean ? null : bean.getMd5());
        }
        return list;
    }
}
