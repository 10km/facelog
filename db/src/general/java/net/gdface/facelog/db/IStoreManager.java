// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db;

/**
 * Interface to handle database calls (save, load, count, etc...) for the fl_store table.<br>
 * @author guyadong
 */
public interface IStoreManager extends TableManager<StoreBean>
{  
    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a {@link StoreBean} from the fl_store using primary key fields.
     *
     * @param md5 String - PK# 1
     * @return a unique StoreBean or {@code null} if not found
     */
    //1
    public StoreBean loadByPrimaryKey(String md5);

    
    /**
     * Returns true if this fl_store contains row with primary key fields.
     * @param md5 String - PK# 1
     * @see #loadByPrimaryKey($keys)
     */
    //1.4
    public boolean existsPrimaryKey(String md5);
    /**
     * Check duplicated row by primary keys,if row exists throw exception
     * @param md5 String
     */
    //1.4.1
    public String checkDuplicate(String md5);
    /**
     * Loads {@link StoreBean} from the fl_store using primary key fields.
     *
     * @param keys primary keys array
     * @return list of StoreBean
     */
    //1.8
    public java.util.List<StoreBean> loadByPrimaryKey(String... keys);
    /**
     * Loads {@link StoreBean} from the fl_store using primary key fields.
     *
     * @param keys primary keys collection
     * @return list of StoreBean
     */
    //1.9
    public java.util.List<StoreBean> loadByPrimaryKey(java.util.Collection<String> keys);
    /**
     * Delete row according to its primary keys.<br>
     * all keys must not be null
     *
     * @param md5 String - PK# 1
     * @return the number of deleted rows
     */
    //2
    public int deleteByPrimaryKey(String md5);
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys array
     * @return the number of deleted rows
     */
    //2.2
    public int deleteByPrimaryKey(String... keys);
    /**
     * Delete rows according to primary key.<br>
     *
     * @param keys primary keys collection
     * @return the number of deleted rows
     */
    //2.3
    public int deleteByPrimaryKey(java.util.Collection<String> keys);
    /**
     * Delete beans.<br>
     *
     * @param beans StoreBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.4
    public int delete(StoreBean... beans);
    /**
     * Delete beans.<br>
     *
     * @param beans StoreBean collection wille be deleted
     * @return the number of deleted rows
     */
    //2.5
    public int delete(java.util.Collection<StoreBean> beans);
 
 }
