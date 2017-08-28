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

import net.gdface.facelog.db.FaceLightBean;
import net.gdface.facelog.db.IBeanConverter;
import net.gdface.facelog.db.IDbConverter;
import net.gdface.facelog.db.TableListener;

import net.gdface.facelog.dborm.exception.DAOException;

import net.gdface.facelog.dborm.face.FlFaceLightManager;
import net.gdface.facelog.dborm.face.FlFaceLightBean;
import net.gdface.facelog.dborm.face.FlFaceLightListener;

/**
 * Handles database calls (save, load, count, etc...) for the fl_face_light table.
 * @author guyadong
 */
public class FaceLightManager 
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
     * Identify the face_left field.
     */
    public static final int ID_FACE_LEFT = 3;

    /**
     * Identify the face_top field.
     */
    public static final int ID_FACE_TOP = 4;

    /**
     * Identify the face_width field.
     */
    public static final int ID_FACE_WIDTH = 5;

    /**
     * Identify the face_height field.
     */
    public static final int ID_FACE_HEIGHT = 6;

    /**
     * Identify the eye_leftx field.
     */
    public static final int ID_EYE_LEFTX = 7;

    /**
     * Identify the eye_lefty field.
     */
    public static final int ID_EYE_LEFTY = 8;

    /**
     * Identify the eye_rightx field.
     */
    public static final int ID_EYE_RIGHTX = 9;

    /**
     * Identify the eye_righty field.
     */
    public static final int ID_EYE_RIGHTY = 10;

    /**
     * Identify the mouth_x field.
     */
    public static final int ID_MOUTH_X = 11;

    /**
     * Identify the mouth_y field.
     */
    public static final int ID_MOUTH_Y = 12;

    /**
     * Identify the nose_x field.
     */
    public static final int ID_NOSE_X = 13;

    /**
     * Identify the nose_y field.
     */
    public static final int ID_NOSE_Y = 14;

    /**
     * Identify the angle_yaw field.
     */
    public static final int ID_ANGLE_YAW = 15;

    /**
     * Identify the angle_pitch field.
     */
    public static final int ID_ANGLE_PITCH = 16;

    /**
     * Identify the angle_roll field.
     */
    public static final int ID_ANGLE_ROLL = 17;

    /**
     * Identify the ext_info field.
     */
    public static final int ID_EXT_INFO = 18;

    /**
     * Identify the create_time field.
     */
    public static final int ID_CREATE_TIME = 19;

    /**
     * Tablename.
     */
		public static final String TABLE_NAME="fl_face_light";
    /**
     * Contains all the full fields of the fl_face_light table.
     */
    public static final String[] FULL_FIELD_NAMES =
    {
        "fl_face_light.md5"
        ,"fl_face_light.person_id"
        ,"fl_face_light.img_md5"
        ,"fl_face_light.face_left"
        ,"fl_face_light.face_top"
        ,"fl_face_light.face_width"
        ,"fl_face_light.face_height"
        ,"fl_face_light.eye_leftx"
        ,"fl_face_light.eye_lefty"
        ,"fl_face_light.eye_rightx"
        ,"fl_face_light.eye_righty"
        ,"fl_face_light.mouth_x"
        ,"fl_face_light.mouth_y"
        ,"fl_face_light.nose_x"
        ,"fl_face_light.nose_y"
        ,"fl_face_light.angle_yaw"
        ,"fl_face_light.angle_pitch"
        ,"fl_face_light.angle_roll"
        ,"fl_face_light.ext_info"
        ,"fl_face_light.create_time"
    };

    /**
     * Contains all the fields of the fl_face_light table.
     */
    public static final String[] FIELD_NAMES =
    {
        "md5"
        ,"person_id"
        ,"img_md5"
        ,"face_left"
        ,"face_top"
        ,"face_width"
        ,"face_height"
        ,"eye_leftx"
        ,"eye_lefty"
        ,"eye_rightx"
        ,"eye_righty"
        ,"mouth_x"
        ,"mouth_y"
        ,"nose_x"
        ,"nose_y"
        ,"angle_yaw"
        ,"angle_pitch"
        ,"angle_roll"
        ,"ext_info"
        ,"create_time"
    };
   /**
     * Contains all the primarykey fields of the fl_face_light table.
     */
    public static final String[] PRIMARYKEY_NAMES =
    {
    };
    /**
     * Field that contains the comma separated fields of the fl_face_light table.
     */
    public static final String ALL_FULL_FIELDS = "fl_face_light.md5"
                            + ",fl_face_light.person_id"
                            + ",fl_face_light.img_md5"
                            + ",fl_face_light.face_left"
                            + ",fl_face_light.face_top"
                            + ",fl_face_light.face_width"
                            + ",fl_face_light.face_height"
                            + ",fl_face_light.eye_leftx"
                            + ",fl_face_light.eye_lefty"
                            + ",fl_face_light.eye_rightx"
                            + ",fl_face_light.eye_righty"
                            + ",fl_face_light.mouth_x"
                            + ",fl_face_light.mouth_y"
                            + ",fl_face_light.nose_x"
                            + ",fl_face_light.nose_y"
                            + ",fl_face_light.angle_yaw"
                            + ",fl_face_light.angle_pitch"
                            + ",fl_face_light.angle_roll"
                            + ",fl_face_light.ext_info"
                            + ",fl_face_light.create_time";

    /**
     * Field that contains the comma separated fields of the fl_face_light table.
     */
    public static final String ALL_FIELDS = "md5"
                            + ",person_id"
                            + ",img_md5"
                            + ",face_left"
                            + ",face_top"
                            + ",face_width"
                            + ",face_height"
                            + ",eye_leftx"
                            + ",eye_lefty"
                            + ",eye_rightx"
                            + ",eye_righty"
                            + ",mouth_x"
                            + ",mouth_y"
                            + ",nose_x"
                            + ",nose_y"
                            + ",angle_yaw"
                            + ",angle_pitch"
                            + ",angle_roll"
                            + ",ext_info"
                            + ",create_time";

    public static interface Action{
          void call(FaceLightBean bean);
          FaceLightBean getBean();
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
    private FlFaceLightManager nativeManager = FlFaceLightManager.getInstance();
    private IDbConverter dbConverter = new DbConverter();
    private IBeanConverter<FaceLightBean,FlFaceLightBean> beanConverter;
    private static FaceLightManager singleton = new FaceLightManager();

    /**
     * Get the FaceLightManager singleton.
     *
     * @return FaceLightManager
     */
    public static FaceLightManager getInstance()
    {
        return singleton;
    }
    
    public FlFaceLightManager getNativeManager() {
        return nativeManager;
    }

    public void setNativeManager(FlFaceLightManager nativeManager) {
        this.nativeManager = nativeManager;
    }
    
    public IDbConverter getDbConverter() {
        return dbConverter;
    }

    public void setDbConverter(IDbConverter dbConverter) {
        if( null == dbConverter)
            throw new NullPointerException();
        this.dbConverter = dbConverter;
        this.beanConverter = this.dbConverter.getFaceLightBeanConverter();
    }
    public FaceLightBean loadByPrimaryKey(FaceLightBean bean)
    {
        throw new UnsupportedOperationException();
    }
    public boolean existsPrimaryKey(FaceLightBean bean)
    {
        throw new UnsupportedOperationException();
    }
    public int deleteByPrimaryKey(FaceLightBean bean)
    {
        throw new UnsupportedOperationException();
    }
 

    //@Override
    public <T> T[] getImportedBeans(FaceLightBean bean,String fkName){
        throw new UnsupportedOperationException();
    }
    //@Override
    public <T> List<T> getImportedBeansAsList(FaceLightBean bean,String fkName){
        throw new UnsupportedOperationException();
    }
    //@Override
    public <T> T[] setImportedBeans(FaceLightBean bean,T[] importedBeans,String fkName){
        throw new UnsupportedOperationException();
    }    
    //@Override
    public <T extends Collection<FaceLightBean>> T setImportedBeans(FaceLightBean bean,T importedBeans,String fkName){
        throw new UnsupportedOperationException();
    }
 


 
    //@Override
    public <T> T getReferencedBean(FaceLightBean bean,String fkName){
        throw new UnsupportedOperationException();
    }
    //@Override
    public <T> T setReferencedBean(FaceLightBean bean,T beanToSet,String fkName){
        throw new UnsupportedOperationException();
    }
     

    //////////////////////////////////////
    // LOAD ALL
    //////////////////////////////////////

    /**
     * Loads all the rows from fl_face_light.
     *
     * @return an array of FlFaceLightManager bean
     */
    //5
    public FaceLightBean[] loadAll()
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
     * Loads each row from fl_face_light and dealt with action.
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //5-1
    public int loadAll(Action action)
    {
        return this.loadUsingTemplate(null,action);
    }
    /**
     * Loads all the rows from fl_face_light.
     *
     * @return a list of FaceLightBean bean
     */
    //5-2
    public List<FaceLightBean> loadAllAsList()
    {
        return this.loadUsingTemplateAsList(null);
    }


    /**
     * Loads the given number of rows from fl_face_light, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return an array of FlFaceLightManager bean
     */
    //6
    public FaceLightBean[] loadAll(int startRow, int numRows)
    {
        return this.loadUsingTemplate(null, startRow, numRows);
    }
    /**
     *  Loads the given number of rows from fl_face_light, given the start row and dealt with action.
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
     * Loads the given number of rows from fl_face_light, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return a list of FlFaceLightManager bean
     */
    //6-2
    public List<FaceLightBean> loadAllAsList(int startRow, int numRows)
    {
        return this.loadUsingTemplateAsList(null, startRow, numRows);
    }

    //////////////////////////////////////
    // SQL 'WHERE' METHOD
    //////////////////////////////////////
    /**
     * Retrieves an array of FaceLightBean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return the resulting FaceLightBean table
     */
    //7
    public FaceLightBean[] loadByWhere(String where)
    {
        return this.loadByWhere(where, (int[])null);
    }
    /**
     * Retrieves a list of FaceLightBean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return the resulting FaceLightBean table
     */
    //7
    public List<FaceLightBean> loadByWhereAsList(String where)
    {
        return this.loadByWhereAsList(where, null);
    }
    /**
     * Retrieves each row of FaceLightBean given a sql 'where' clause and dealt with action.
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
     * Retrieves an array of FaceLightBean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return the resulting FaceLightBean table
     */
    //8
    public FaceLightBean[] loadByWhere(String where, int[] fieldList)
    {
        return this.loadByWhere(where, fieldList, 1, -1);
    }


    /**
     * Retrieves a list of FaceLightBean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return the resulting FaceLightBean table
     */
    //8
    public List<FaceLightBean> loadByWhereAsList(String where, int[] fieldList)
    {
        return this.loadByWhereAsList(where, fieldList, 1, -1);
    }
    /**
     * Retrieves each row of FaceLightBean given a sql where clause, and a list of fields,
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
     * Retrieves an array of FaceLightBean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the resulting FaceLightBean table
     */
    //9
    public FaceLightBean[] loadByWhere(String where, int[] fieldList, int startRow, int numRows)
    {
        return (FaceLightBean[]) this.loadByWhereAsList(where, fieldList, startRow, numRows).toArray(new FaceLightBean[0]);
    }
    /**
     * Retrieves each row of  FaceLightBean given a sql where clause and a list of fields, and startRow and numRows,
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
     * Retrieves a list of FaceLightBean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the resulting FaceLightBean table
     */
    //9-2
    public List<FaceLightBean> loadByWhereAsList(String where, int[] fieldList, int startRow, int numRows)
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
     * Retrieves each row of FaceLightBean given a sql where clause and a list of fields, and startRow and numRows,
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
     * Deletes all rows from fl_face_light table.
     * @return the number of deleted rows.
     */
    //10
    public int deleteAll()
    {
        return this.deleteByWhere("");
    }

    /**
     * Deletes rows from the fl_face_light table using a 'where' clause.
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
     * Saves the FaceLightBean bean into the database.
     *
     * @param bean the FaceLightBean bean to be saved
     * @return the inserted or updated bean
     */
    //12
    public FaceLightBean save(FaceLightBean bean)
    {
        if (bean.isNew()) {
            return this.insert(bean);
        } else {
            return this.update(bean);
        }
    }

    /**
     * Insert the FaceLightBean bean into the database.
     *
     * @param bean the FaceLightBean bean to be saved
     * @return the inserted bean
     */
    //13
    public FaceLightBean insert(FaceLightBean bean)
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
     * Update the FaceLightBean bean record in the database according to the changes.
     *
     * @param bean the FaceLightBean bean to be updated
     * @return the updated bean
     */
    //14
    public FaceLightBean update(FaceLightBean bean)
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
     * Saves an array of FaceLightBean beans into the database.
     *
     * @param beans the FaceLightBean bean table to be saved
     * @return the saved FaceLightBean array.
     */
    //15
    public FaceLightBean[] save(FaceLightBean[] beans)
    {
        for (FaceLightBean bean : beans) 
        {
            this.save(bean);
        }
        return beans;
    }

    /**
     * Saves a list of FaceLightBean beans into the database.
     *
     * @param beans the FaceLightBean bean table to be saved
     * @return the saved FaceLightBean array.
     */
    //15-2
    public <T extends Collection<FaceLightBean>>T save(T beans)
    {
        for (FaceLightBean bean : beans) 
        {
            this.save(bean);
        }
        return beans;
    }
    /**
     * Saves an array of FaceLightBean beans as transaction into the database.
     *
     * @param beans the FaceLightBean bean table to be saved
     * @return the saved FaceLightBean array.
     * @see #save(FaceLightBean[])
     */
    //15-3
    public FaceLightBean[] saveAsTransaction(final FaceLightBean[] beans) {
        return this.runAsTransaction(new Callable<FaceLightBean[]>(){
            @Override
            public FaceLightBean[] call() throws Exception {
                return save(beans);
            }});
    }
    /**
     * Saves a list of FaceLightBean beans as transaction into the database.
     *
     * @param beans the FaceLightBean bean table to be saved
     * @return the saved FaceLightBean array.
     * @see #save(List)
     */
    //15-4
    public <T extends Collection<FaceLightBean>> T saveAsTransaction(final T beans){
        return this.runAsTransaction(new Callable<T>(){
            @Override
            public T call() throws Exception {
                return save(beans);
            }});
    }
    /**
     * Insert an array of FaceLightBean beans into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     */
    //16
    public FaceLightBean[] insert(FaceLightBean[] beans)
    {
        return this.save(beans);
    }

    /**
     * Insert a list of FaceLightBean beans into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     */
    //16-2
    public <T extends Collection<FaceLightBean>> T insert(T beans)
    {
        return this.save(beans);
    }
    
    /**
     * Insert an array of FaceLightBean beans as transaction into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     * @see #saveAsTransaction(FaceLightBean[])
     */
    //16-3
    public FaceLightBean[] insertAsTransaction(FaceLightBean[] beans)
    {
        return this.saveAsTransaction(beans);
    }

    /**
     * Insert a list of FaceLightBean beans as transaction into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     * @see #saveAsTransaction(List)
     */
    //16-4
    public <T extends Collection<FaceLightBean>> T insertAsTransaction(T beans)
    {
        return this.saveAsTransaction(beans);
    }


    /**
     * Updates an array of FaceLightBean beans into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     */
    //17
    public FaceLightBean[] update(FaceLightBean[] beans)
    {
        return this.save(beans);
    }

    /**
     * Updates a list of FaceLightBean beans into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     */
    //17-2
    public <T extends Collection<FaceLightBean>> T update(T beans)
    {
        return this.save(beans);
    }
    
    /**
     * Updates an array of FaceLightBean beans as transaction into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     * @see #saveAsTransaction(FaceLightBean[])
     */
    //17-3
    public FaceLightBean[] updateAsTransaction(FaceLightBean[] beans)
    {
        return this.saveAsTransaction(beans);
    }

    /**
     * Updates a list of FaceLightBean beans as transaction into the database.
     *
     * @param beans the FaceLightBean bean table to be inserted
     * @return the saved FaceLightBean array.
     * @see #saveAsTransaction(List)
     */
    //17-4
    public <T extends Collection<FaceLightBean>> T updateAsTransaction(T beans)
    {
        return this.saveAsTransaction(beans);
    }
    
    //_____________________________________________________________________
    //
    // USING TEMPLATE
    //_____________________________________________________________________
    /**
     * Loads a unique FaceLightBean bean from a template one giving a c
     *
     * @param bean the FaceLightBean bean to look for
     * @return the bean matching the template
     */
    //18
    public FaceLightBean loadUniqueUsingTemplate(FaceLightBean bean)
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
     * Loads an array of FaceLightBean from a template one.
     *
     * @param bean the FaceLightBean template to look for
     * @return all the FaceLightBean matching the template
     */
    //19
    public FaceLightBean[] loadUsingTemplate(FaceLightBean bean)
    {
        return this.loadUsingTemplate(bean, 1, -1);
    }
    /**
     * Loads each row from a template one and dealt with action.
     *
     * @param bean the FaceLightBean template to look for
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //19-1
    public int loadUsingTemplate(FaceLightBean bean,Action action)
    {
        return this.loadUsingTemplate(bean, 1, -1,action);
    }

    /**
     * Loads a list of FaceLightBean from a template one.
     *
     * @param bean the FaceLightBean template to look for
     * @return all the FaceLightBean matching the template
     */
    //19-2
    public List<FaceLightBean> loadUsingTemplateAsList(FaceLightBean bean)
    {
        return this.loadUsingTemplateAsList(bean, 1, -1);
    }

    /**
     * Loads an array of FaceLightBean from a template one, given the start row and number of rows.
     *
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the FaceLightBean matching the template
     */
    //20
    public FaceLightBean[] loadUsingTemplate(FaceLightBean bean, int startRow, int numRows)
    {
        return this.loadUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
    }
    /**
     * Loads each row from a template one, given the start row and number of rows and dealt with action.
     *
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //20-1
    public int loadUsingTemplate(FaceLightBean bean, int startRow, int numRows,Action action)
    {
        return this.loadUsingTemplate(bean, null, startRow, numRows,SEARCH_EXACT, action);
    }
    /**
     * Loads a list of FaceLightBean from a template one, given the start row and number of rows.
     *
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the FaceLightBean matching the template
     */
    //20-2
    public List<FaceLightBean> loadUsingTemplateAsList(FaceLightBean bean, int startRow, int numRows)
    {
        return this.loadUsingTemplateAsList(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * Loads an array of FaceLightBean from a template one, given the start row and number of rows.
     *
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return all the FaceLightBean matching the template
     */
    //20-3
    public FaceLightBean[] loadUsingTemplate(FaceLightBean bean, int startRow, int numRows, int searchType)
    {
    	return this.loadUsingTemplateAsList(bean, startRow, numRows, searchType).toArray(new FaceLightBean[0]);
    }

    /**
     * Loads a list of FaceLightBean from a template one, given the start row and number of rows.
     *
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return all the FaceLightBean matching the template
     */
    //20-4
    public List<FaceLightBean> loadUsingTemplateAsList(FaceLightBean beanBase, int startRow, int numRows, int searchType)
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
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //20-5
    public int loadUsingTemplate(FaceLightBean beanBase, int[] fieldList, int startRow, int numRows,int searchType, Action action)
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
     * Deletes rows using a FaceLightBean template.
     *
     * @param bean the FaceLightBean object(s) to be deleted
     * @return the number of deleted objects
     */
    //21
    public int deleteUsingTemplate(FaceLightBean beanBase)
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
     * Retrieves the number of rows of the table fl_face_light.
     *
     * @return the number of rows returned
     */
    //24
    public int countAll() 
    {
        return this.countWhere("");
    }

    /**
     * Retrieves the number of rows of the table fl_face_light with a 'where' clause.
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
     * count the number of elements of a specific FaceLightBean bean
     *
     * @param bean the FaceLightBean bean to look for ant count
     * @return the number of rows returned
     */
    //27
    public int countUsingTemplate(FaceLightBean bean)
    {
        return this.countUsingTemplate(bean, -1, -1);
    }

    /**
     * count the number of elements of a specific FaceLightBean bean , given the start row and number of rows.
     *
     * @param bean the FaceLightBean template to look for and count
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the number of rows returned
     */
    //20
    public int countUsingTemplate(FaceLightBean bean, int startRow, int numRows)
    {
        return this.countUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * count the number of elements of a specific FaceLightBean bean given the start row and number of rows and the search type
     *
     * @param bean the FaceLightBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return the number of rows returned
     */
    //20
    public int countUsingTemplate(FaceLightBean beanBase, int startRow, int numRows, int searchType)
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
     * Registers a unique FaceLightListener listener.
     */
    //35
    public void registerListener(TableListener listener)
    {
        this.nativeManager.registerListener(this.toNative((FaceLightListener)listener));
    }

    private FlFaceLightListener toNative(final FaceLightListener listener) {
		return null == listener ?null:new FlFaceLightListener (){

			@Override
			public void beforeInsert(FlFaceLightBean bean) throws DAOException {
				listener.beforeInsert(FaceLightManager.this.beanConverter.fromNative(bean));				
			}

			@Override
			public void afterInsert(FlFaceLightBean bean) throws DAOException {
				listener.afterInsert(FaceLightManager.this.beanConverter.fromNative(bean));
				
			}

			@Override
			public void beforeUpdate(FlFaceLightBean bean) throws DAOException {
				listener.beforeUpdate(FaceLightManager.this.beanConverter.fromNative(bean));
				
			}

			@Override
			public void afterUpdate(FlFaceLightBean bean) throws DAOException {
				listener.afterUpdate(FaceLightManager.this.beanConverter.fromNative(bean));
			}

			@Override
			public void beforeDelete(FlFaceLightBean bean) throws DAOException {
				listener.beforeDelete(FaceLightManager.this.beanConverter.fromNative(bean));
			}

			@Override
			public void afterDelete(FlFaceLightBean bean) throws DAOException {
				listener.afterDelete(FaceLightManager.this.beanConverter.fromNative(bean));
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
     * @return an array of FaceLightBean
     */
    public FaceLightBean[] loadBySql(String sql, Object[] argList, int[] fieldList) {
        return loadBySqlAsList(sql, argList, fieldList).toArray(new FaceLightBean[0]);
    }
    /**
     * Load all elements using a SQL statement specifying a list of fields to be retrieved.
     * @param sql the SQL statement for retrieving
     * @param argList the arguments to use fill given prepared statement,may be null
     * @param fieldList table of the field's associated constants
     * @return an list of FaceLightBean
     */
    public List<FaceLightBean> loadBySqlAsList(String sql, Object[] argList, int[] fieldList){
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
    private FlFaceLightManager.Action toNative(final Action action){
        return new FlFaceLightManager.Action(){

            @Override
            public void call(FlFaceLightBean bean) {
                action.call(FaceLightManager.this.beanConverter.fromNative(bean));
            }

            @Override
            public FlFaceLightBean getBean() {
                return  FaceLightManager.this.beanConverter.toNative(action.getBean());
            }};
    }
}