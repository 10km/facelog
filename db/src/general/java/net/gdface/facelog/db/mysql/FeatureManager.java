// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________



package net.gdface.facelog.db.mysql;

import java.util.List;
import java.util.Collection;
import java.util.concurrent.Callable;

import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableListener;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.face.FlFeatureManager;
import net.gdface.facelog.dborm.face.FlFeatureBean;
import net.gdface.facelog.dborm.face.FlFeatureListener;

/**
 * Handles database calls (save, load, count, etc...) for the fl_feature table.
 * @author guyadong
 */
public class FeatureManager 
{

    /* set =QUERY for loadUsingTemplate */
    public static final int SEARCH_EXACT = 0;
    /* set %QUERY% for loadLikeTemplate */
    public static final int SEARCH_LIKE = 1;
    /* set %QUERY for loadLikeTemplate */
    public static final int SEARCH_STARTING_LIKE = 2;
    /* set QUERY% for loadLikeTemplate */
    public static final int SEARCH_ENDING_LIKE = 3;

    /**
     * Identify the md5 field.
     */
    public static final int ID_MD5 = 0;

    /**
     * Identify the person_id field.
     */
    public static final int ID_PERSON_ID = 1;

    /**
     * Identify the img_md5 field.
     */
    public static final int ID_IMG_MD5 = 2;

    /**
     * Identify the feature field.
     */
    public static final int ID_FEATURE = 3;

    /**
     * Identify the create_time field.
     */
    public static final int ID_CREATE_TIME = 4;

    /**
     * Tablename.
     */
        public static final String TABLE_NAME="fl_feature";
    /**
     * Contains all the full fields of the fl_feature table.
     */
    public static final String[] FULL_FIELD_NAMES =
    {
        "fl_feature.md5"
        ,"fl_feature.person_id"
        ,"fl_feature.img_md5"
        ,"fl_feature.feature"
        ,"fl_feature.create_time"
    };

    /**
     * Contains all the fields of the fl_feature table.
     */
    public static final String[] FIELD_NAMES =
    {
        "md5"
        ,"person_id"
        ,"img_md5"
        ,"feature"
        ,"create_time"
    };
   /**
     * Contains all the primarykey fields of the fl_feature table.
     */
    public static final String[] PRIMARYKEY_NAMES =
    {
    };
    /**
     * Field that contains the comma separated fields of the fl_feature table.
     */
    public static final String ALL_FULL_FIELDS = "fl_feature.md5"
                            + ",fl_feature.person_id"
                            + ",fl_feature.img_md5"
                            + ",fl_feature.feature"
                            + ",fl_feature.create_time";

    /**
     * Field that contains the comma separated fields of the fl_feature table.
     */
    public static final String ALL_FIELDS = "md5"
                            + ",person_id"
                            + ",img_md5"
                            + ",feature"
                            + ",create_time";

    public static interface Action{
          void call(FeatureBean bean);
          FeatureBean getBean();
     }

    /**
    * @return tableName
    */
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
    * @return fieldNames
    */
    public String[] getFieldNames() {
        return FIELD_NAMES;
    }

    /**
    * @return primarykeyNames
    */
    public String[] getPrimarykeyNames() {
        return PRIMARYKEY_NAMES;
    }
    private FlFeatureManager nativeManager = FlFeatureManager.getInstance();
    private IDbConverter dbConverter = new DbConverter();
    private IBeanConverter<FeatureBean,FlFeatureBean> beanConverter;
    private static FeatureManager singleton = new FeatureManager();

    /**
     * Get the FeatureManager singleton.
     *
     * @return FeatureManager
     */
    public static FeatureManager getInstance()
    {
        return singleton;
    }
    
    public FlFeatureManager getNativeManager() {
        return nativeManager;
    }

    public void setNativeManager(FlFeatureManager nativeManager) {
        this.nativeManager = nativeManager;
    }
    
    public IDbConverter getDbConverter() {
        return dbConverter;
    }

    public void setDbConverter(IDbConverter dbConverter) {
        if( null == dbConverter)
            throw new NullPointerException();
        this.dbConverter = dbConverter;
        this.beanConverter = this.dbConverter.getFeatureBeanConverter();
    }
    public FeatureBean loadByPrimaryKey(FeatureBean bean)
    {
        throw new UnsupportedOperationException();
    }
    public boolean existsPrimaryKey(FeatureBean bean)
    {
        throw new UnsupportedOperationException();
    }
    public int deleteByPrimaryKey(FeatureBean bean)
    {
        throw new UnsupportedOperationException();
    }
 

    //@Override
    public <T> T[] getImportedBeans(FeatureBean bean,String fkName){
        throw new UnsupportedOperationException();
    }
    //@Override
    public <T> List<T> getImportedBeansAsList(FeatureBean bean,String fkName){
        throw new UnsupportedOperationException();
    }
    //@Override
    public <T> T[] setImportedBeans(FeatureBean bean,T[] importedBeans,String fkName){
        throw new UnsupportedOperationException();
    }    
    //@Override
    public <T extends Collection<FeatureBean>> T setImportedBeans(FeatureBean bean,T importedBeans,String fkName){
        throw new UnsupportedOperationException();
    }
 


 
    //@Override
    public <T> T getReferencedBean(FeatureBean bean,String fkName){
        throw new UnsupportedOperationException();
    }
    //@Override
    public <T> T setReferencedBean(FeatureBean bean,T beanToSet,String fkName){
        throw new UnsupportedOperationException();
    }
     

    //////////////////////////////////////
    // LOAD ALL
    //////////////////////////////////////

    /**
     * Loads all the rows from fl_feature.
     *
     * @return an array of FlFeatureManager bean
     */
    //5
    public FeatureBean[] loadAll()
    {
        try{
            return this.beanConverter.fromNative(this.nativeManager.loadUsingTemplate(null));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Loads each row from fl_feature and dealt with action.
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //5-1
    public int loadAll(Action action)
    {
        return this.loadUsingTemplate(null,action);
    }
    /**
     * Loads all the rows from fl_feature.
     *
     * @return a list of FeatureBean bean
     */
    //5-2
    public List<FeatureBean> loadAllAsList()
    {
        return this.loadUsingTemplateAsList(null);
    }


    /**
     * Loads the given number of rows from fl_feature, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return an array of FlFeatureManager bean
     */
    //6
    public FeatureBean[] loadAll(int startRow, int numRows)
    {
        return this.loadUsingTemplate(null, startRow, numRows);
    }
    /**
     *  Loads the given number of rows from fl_feature, given the start row and dealt with action.
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //6-1
    public int loadAll(int startRow, int numRows,Action action)
    {
        return this.loadUsingTemplate(null, startRow, numRows,action);
    }
    /**
     * Loads the given number of rows from fl_feature, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return a list of FlFeatureManager bean
     */
    //6-2
    public List<FeatureBean> loadAllAsList(int startRow, int numRows)
    {
        return this.loadUsingTemplateAsList(null, startRow, numRows);
    }

    //////////////////////////////////////
    // SQL 'WHERE' METHOD
    //////////////////////////////////////
    /**
     * Retrieves an array of FeatureBean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return the resulting FeatureBean table
     */
    //7
    public FeatureBean[] loadByWhere(String where)
    {
        return this.loadByWhere(where, (int[])null);
    }
    /**
     * Retrieves a list of FeatureBean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return the resulting FeatureBean table
     */
    //7
    public List<FeatureBean> loadByWhereAsList(String where)
    {
        return this.loadByWhereAsList(where, null);
    }
    /**
     * Retrieves each row of FeatureBean given a sql 'where' clause and dealt with action.
     * @param where the sql 'where' clause
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //7-1
    public int loadByWhere(String where,Action action)
    {
        return this.loadByWhere(where, null,action);
    }
    /**
     * Retrieves an array of FeatureBean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return the resulting FeatureBean table
     */
    //8
    public FeatureBean[] loadByWhere(String where, int[] fieldList)
    {
        return this.loadByWhere(where, fieldList, 1, -1);
    }


    /**
     * Retrieves a list of FeatureBean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return the resulting FeatureBean table
     */
    //8
    public List<FeatureBean> loadByWhereAsList(String where, int[] fieldList)
    {
        return this.loadByWhereAsList(where, fieldList, 1, -1);
    }
    /**
     * Retrieves each row of FeatureBean given a sql where clause, and a list of fields,
     * and dealt with action.
     * It is up to you to pass the 'WHERE' in your where clausis.
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //8-1
    public int loadByWhere(String where, int[] fieldList,Action action)
    {
        return this.loadByWhere(where, fieldList, 1, -1,action);
    }

    /**
     * Retrieves an array of FeatureBean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the resulting FeatureBean table
     */
    //9
    public FeatureBean[] loadByWhere(String where, int[] fieldList, int startRow, int numRows)
    {
        return (FeatureBean[]) this.loadByWhereAsList(where, fieldList, startRow, numRows).toArray(new FeatureBean[0]);
    }
    /**
     * Retrieves each row of  FeatureBean given a sql where clause and a list of fields, and startRow and numRows,
     * and dealt wity action.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //9-1
    public int loadByWhere(String where, int[] fieldList, int startRow, int numRows,Action action)
    {
        return this.loadByWhereForAction(where, fieldList, startRow, numRows,action);
    }

    /**
     * Retrieves a list of FeatureBean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the resulting FeatureBean table
     */
    //9-2
    public List<FeatureBean> loadByWhereAsList(String where, int[] fieldList, int startRow, int numRows)
    {
        try{
            return this.beanConverter.fromNative(this.nativeManager.loadByWhereAsList(where,fieldList,startRow,numRows));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Retrieves each row of FeatureBean given a sql where clause and a list of fields, and startRow and numRows,
     * and dealt wity action
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //9-3
    public int loadByWhereForAction(String where, int[] fieldList, int startRow, int numRows,Action action)
    {
        try{
            return this.nativeManager.loadByWhereForAction(where,fieldList,startRow,numRows,this.toNative(action));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes all rows from fl_feature table.
     * @return the number of deleted rows.
     */
    //10
    public int deleteAll()
    {
        return this.deleteByWhere("");
    }

    /**
     * Deletes rows from the fl_feature table using a 'where' clause.
     * It is up to you to pass the 'WHERE' in your where clausis.
     * <br>Attention, if 'WHERE' is omitted it will delete all records.
     *
     * @param where the sql 'where' clause
     * @return the number of deleted rows
     */
    //11
    public int deleteByWhere(String where)
    {
        try{
            return this.nativeManager.deleteByWhere(where);
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //_____________________________________________________________________
    //
    // SAVE
    //_____________________________________________________________________
    /**
     * Saves the FeatureBean bean into the database.
     *
     * @param bean the FeatureBean bean to be saved
     * @return the inserted or updated bean
     */
    //12
    public FeatureBean save(FeatureBean bean)
    {
        if (bean.isNew()) {
            return this.insert(bean);
        } else {
            return this.update(bean);
        }
    }

    /**
     * Insert the FeatureBean bean into the database.
     *
     * @param bean the FeatureBean bean to be saved
     * @return the inserted bean
     */
    //13
    public FeatureBean insert(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromNative(this.nativeManager.insert(this.beanConverter.toNative(bean)));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the FeatureBean bean record in the database according to the changes.
     *
     * @param bean the FeatureBean bean to be updated
     * @return the updated bean
     */
    //14
    public FeatureBean update(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromNative(this.nativeManager.update(this.beanConverter.toNative(bean)));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves an array of FeatureBean beans into the database.
     *
     * @param beans the FeatureBean bean table to be saved
     * @return the saved FeatureBean array.
     */
    //15
    public FeatureBean[] save(FeatureBean[] beans)
    {
        for (FeatureBean bean : beans) 
        {
            this.save(bean);
        }
        return beans;
    }

    /**
     * Saves a list of FeatureBean beans into the database.
     *
     * @param beans the FeatureBean bean table to be saved
     * @return the saved FeatureBean array.
     */
    //15-2
    public <T extends Collection<FeatureBean>>T save(T beans)
    {
        for (FeatureBean bean : beans) 
        {
            this.save(bean);
        }
        return beans;
    }
    /**
     * Saves an array of FeatureBean beans as transaction into the database.
     *
     * @param beans the FeatureBean bean table to be saved
     * @return the saved FeatureBean array.
     * @see #save(FeatureBean[])
     */
    //15-3
    public FeatureBean[] saveAsTransaction(final FeatureBean[] beans) {
        return this.runAsTransaction(new Callable<FeatureBean[]>(){
            @Override
            public FeatureBean[] call() throws Exception {
                return save(beans);
            }});
    }
    /**
     * Saves a list of FeatureBean beans as transaction into the database.
     *
     * @param beans the FeatureBean bean table to be saved
     * @return the saved FeatureBean array.
     * @see #save(List)
     */
    //15-4
    public <T extends Collection<FeatureBean>> T saveAsTransaction(final T beans){
        return this.runAsTransaction(new Callable<T>(){
            @Override
            public T call() throws Exception {
                return save(beans);
            }});
    }
    /**
     * Insert an array of FeatureBean beans into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     */
    //16
    public FeatureBean[] insert(FeatureBean[] beans)
    {
        return this.save(beans);
    }

    /**
     * Insert a list of FeatureBean beans into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     */
    //16-2
    public <T extends Collection<FeatureBean>> T insert(T beans)
    {
        return this.save(beans);
    }
    
    /**
     * Insert an array of FeatureBean beans as transaction into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     * @see #saveAsTransaction(FeatureBean[])
     */
    //16-3
    public FeatureBean[] insertAsTransaction(FeatureBean[] beans)
    {
        return this.saveAsTransaction(beans);
    }

    /**
     * Insert a list of FeatureBean beans as transaction into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     * @see #saveAsTransaction(List)
     */
    //16-4
    public <T extends Collection<FeatureBean>> T insertAsTransaction(T beans)
    {
        return this.saveAsTransaction(beans);
    }


    /**
     * Updates an array of FeatureBean beans into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     */
    //17
    public FeatureBean[] update(FeatureBean[] beans)
    {
        return this.save(beans);
    }

    /**
     * Updates a list of FeatureBean beans into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     */
    //17-2
    public <T extends Collection<FeatureBean>> T update(T beans)
    {
        return this.save(beans);
    }
    
    /**
     * Updates an array of FeatureBean beans as transaction into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     * @see #saveAsTransaction(FeatureBean[])
     */
    //17-3
    public FeatureBean[] updateAsTransaction(FeatureBean[] beans)
    {
        return this.saveAsTransaction(beans);
    }

    /**
     * Updates a list of FeatureBean beans as transaction into the database.
     *
     * @param beans the FeatureBean bean table to be inserted
     * @return the saved FeatureBean array.
     * @see #saveAsTransaction(List)
     */
    //17-4
    public <T extends Collection<FeatureBean>> T updateAsTransaction(T beans)
    {
        return this.saveAsTransaction(beans);
    }
    
    //_____________________________________________________________________
    //
    // USING TEMPLATE
    //_____________________________________________________________________
    /**
     * Loads a unique FeatureBean bean from a template one giving a c
     *
     * @param bean the FeatureBean bean to look for
     * @return the bean matching the template
     */
    //18
    public FeatureBean loadUniqueUsingTemplate(FeatureBean bean)
    {
        try{
            return this.beanConverter.fromNative(this.nativeManager.loadUniqueUsingTemplate(this.beanConverter.toNative(bean)));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
     }

    /**
     * Loads an array of FeatureBean from a template one.
     *
     * @param bean the FeatureBean template to look for
     * @return all the FeatureBean matching the template
     */
    //19
    public FeatureBean[] loadUsingTemplate(FeatureBean bean)
    {
        return this.loadUsingTemplate(bean, 1, -1);
    }
    /**
     * Loads each row from a template one and dealt with action.
     *
     * @param bean the FeatureBean template to look for
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //19-1
    public int loadUsingTemplate(FeatureBean bean,Action action)
    {
        return this.loadUsingTemplate(bean, 1, -1,action);
    }

    /**
     * Loads a list of FeatureBean from a template one.
     *
     * @param bean the FeatureBean template to look for
     * @return all the FeatureBean matching the template
     */
    //19-2
    public List<FeatureBean> loadUsingTemplateAsList(FeatureBean bean)
    {
        return this.loadUsingTemplateAsList(bean, 1, -1);
    }

    /**
     * Loads an array of FeatureBean from a template one, given the start row and number of rows.
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the FeatureBean matching the template
     */
    //20
    public FeatureBean[] loadUsingTemplate(FeatureBean bean, int startRow, int numRows)
    {
        return this.loadUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
    }
    /**
     * Loads each row from a template one, given the start row and number of rows and dealt with action.
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //20-1
    public int loadUsingTemplate(FeatureBean bean, int startRow, int numRows,Action action)
    {
        return this.loadUsingTemplate(bean, null, startRow, numRows,SEARCH_EXACT, action);
    }
    /**
     * Loads a list of FeatureBean from a template one, given the start row and number of rows.
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the FeatureBean matching the template
     */
    //20-2
    public List<FeatureBean> loadUsingTemplateAsList(FeatureBean bean, int startRow, int numRows)
    {
        return this.loadUsingTemplateAsList(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * Loads an array of FeatureBean from a template one, given the start row and number of rows.
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return all the FeatureBean matching the template
     */
    //20-3
    public FeatureBean[] loadUsingTemplate(FeatureBean bean, int startRow, int numRows, int searchType)
    {
        return this.loadUsingTemplateAsList(bean, startRow, numRows, searchType).toArray(new FeatureBean[0]);
    }

    /**
     * Loads a list of FeatureBean from a template one, given the start row and number of rows.
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return all the FeatureBean matching the template
     */
    //20-4
    public List<FeatureBean> loadUsingTemplateAsList(FeatureBean beanBase, int startRow, int numRows, int searchType)
    {
        try{
            return this.beanConverter.fromNative(this.nativeManager.loadUsingTemplateAsList(this.beanConverter.toNative(beanBase),startRow,numRows,searchType));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }        
    }
    /**
     * Loads each row from a template one, given the start row and number of rows and dealt with action.
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //20-5
    public int loadUsingTemplate(FeatureBean beanBase, int[] fieldList, int startRow, int numRows,int searchType, Action action)
    {
        try {
            return this.nativeManager.loadUsingTemplate(this.beanConverter.toNative(beanBase),fieldList,startRow,numRows,searchType,this.toNative(action));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Deletes rows using a FeatureBean template.
     *
     * @param bean the FeatureBean object(s) to be deleted
     * @return the number of deleted objects
     */
    //21
    public int deleteUsingTemplate(FeatureBean beanBase)
    {
        try{
            return this.nativeManager.deleteUsingTemplate(this.beanConverter.toNative(beanBase));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }



    //_____________________________________________________________________
    //
    // COUNT
    //_____________________________________________________________________

    /**
     * Retrieves the number of rows of the table fl_feature.
     *
     * @return the number of rows returned
     */
    //24
    public int countAll() 
    {
        return this.countWhere("");
    }

    /**
     * Retrieves the number of rows of the table fl_feature with a 'where' clause.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the restriction clause
     * @return the number of rows returned
     */
    //25
    public int countWhere(String where)
    {
        try{
            return this.nativeManager.countWhere(where);
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * count the number of elements of a specific FeatureBean bean
     *
     * @param bean the FeatureBean bean to look for ant count
     * @return the number of rows returned
     */
    //27
    public int countUsingTemplate(FeatureBean bean)
    {
        return this.countUsingTemplate(bean, -1, -1);
    }

    /**
     * count the number of elements of a specific FeatureBean bean , given the start row and number of rows.
     *
     * @param bean the FeatureBean template to look for and count
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the number of rows returned
     */
    //20
    public int countUsingTemplate(FeatureBean bean, int startRow, int numRows)
    {
        return this.countUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * count the number of elements of a specific FeatureBean bean given the start row and number of rows and the search type
     *
     * @param bean the FeatureBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return the number of rows returned
     */
    //20
    public int countUsingTemplate(FeatureBean beanBase, int startRow, int numRows, int searchType)
    {
        try{
            return this.nativeManager.countUsingTemplate(this.beanConverter.toNative(beanBase),startRow,numRows,searchType);
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }


    //_____________________________________________________________________
    //
    // LISTENER
    //_____________________________________________________________________

    /**
     * Registers a unique FeatureListener listener.
     */
    //35
    public void registerListener(TableListener listener)
    {
        this.nativeManager.registerListener(this.toNative((FeatureListener)listener));
    }

    private FlFeatureListener toNative(final FeatureListener listener) {
        return null == listener ?null:new FlFeatureListener (){

            @Override
            public void beforeInsert(FlFeatureBean bean) throws DAOException {
                listener.beforeInsert(FeatureManager.this.beanConverter.fromNative(bean));                
            }

            @Override
            public void afterInsert(FlFeatureBean bean) throws DAOException {
                listener.afterInsert(FeatureManager.this.beanConverter.fromNative(bean));
                
            }

            @Override
            public void beforeUpdate(FlFeatureBean bean) throws DAOException {
                listener.beforeUpdate(FeatureManager.this.beanConverter.fromNative(bean));
                
            }

            @Override
            public void afterUpdate(FlFeatureBean bean) throws DAOException {
                listener.afterUpdate(FeatureManager.this.beanConverter.fromNative(bean));
            }

            @Override
            public void beforeDelete(FlFeatureBean bean) throws DAOException {
                listener.beforeDelete(FeatureManager.this.beanConverter.fromNative(bean));
            }

            @Override
            public void afterDelete(FlFeatureBean bean) throws DAOException {
                listener.afterDelete(FeatureManager.this.beanConverter.fromNative(bean));
            }};
    }

    //_____________________________________________________________________
    //
    // UTILS
    //_____________________________________________________________________


    /**
     * return true if @{code column}(case insensitive)is primary key,otherwise return false <br>
     * return false if @{code column} is null or empty 
     * @param column
     * @return
     * @author guyadong
     */
    //43
    public static boolean isPrimaryKey(String column){
        for(String c:PRIMARYKEY_NAMES)if(c.equalsIgnoreCase(column))return true;
        return false;
    }
    
    /**
     * Load all the elements using a SQL statement specifying a list of fields to be retrieved.
     * @param sql the SQL statement for retrieving
     * @param argList the arguments to use fill given prepared statement,may be null
     * @param fieldList table of the field's associated constants
     * @return an array of FeatureBean
     */
    public FeatureBean[] loadBySql(String sql, Object[] argList, int[] fieldList) {
        return loadBySqlAsList(sql, argList, fieldList).toArray(new FeatureBean[0]);
    }
    /**
     * Load all elements using a SQL statement specifying a list of fields to be retrieved.
     * @param sql the SQL statement for retrieving
     * @param argList the arguments to use fill given prepared statement,may be null
     * @param fieldList table of the field's associated constants
     * @return an list of FeatureBean
     */
    public List<FeatureBean> loadBySqlAsList(String sql, Object[] argList, int[] fieldList){
        try{
            return this.beanConverter.fromNative(this.nativeManager.loadBySqlAsList(sql,argList,fieldList));
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }

    
    //@Override
    public <T>T runAsTransaction(Callable<T> fun) {
        try{
            return this.nativeManager.runAsTransaction(fun);
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    //@Override
    public void runAsTransaction(final Runnable fun){
        try{
            this.nativeManager.runAsTransaction(fun);
        }
        catch(DAOException e)
        {
            throw new RuntimeException(e);
        }
    }
    private FlFeatureManager.Action toNative(final Action action){
        if(null == action)
            throw new NullPointerException();
        return new FlFeatureManager.Action(){

            @Override
            public void call(FlFeatureBean bean) {
                action.call(FeatureManager.this.beanConverter.fromNative(bean));
            }

            @Override
            public FlFeatureBean getBean() {
                return  FeatureManager.this.beanConverter.toNative(action.getBean());
            }};
    }
}
