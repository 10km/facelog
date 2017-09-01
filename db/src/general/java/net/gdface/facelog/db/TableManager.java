// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________


package net.gdface.facelog.db;
import java.util.List;
import java.util.LinkedList;
import java.util.Collection;
import java.util.concurrent.Callable;


/**
 * Interface to handle database calls (save, load, count, etc...) for table.
 * @author guyadong
 */
public interface TableManager<B extends BaseBean> {    

    /** set =QUERY for loadUsingTemplate */
    public static final int SEARCH_EXACT = 0;
    /** set %QUERY% for loadLikeTemplate */
    public static final int SEARCH_LIKE = 1;
    /** set %QUERY for loadLikeTemplate */
    public static final int SEARCH_STARTING_LIKE = 2;
    /** set QUERY% for loadLikeTemplate */
    public static final int SEARCH_ENDING_LIKE = 3;
    
    public interface Action<B>{
        public abstract class Adapter<B> implements Action<B>{
            @Override
            public B getBean() {return null;}            
        }
        void call(B bean);
        B getBean();
    }
    public abstract static class Adapter<B extends BaseBean> implements TableManager<B>{
       /**
         * Insert the B bean into the database.
         * 
         * @param bean the B bean to be saved
         * @return the inserted bean
         */
        //13
        protected abstract B insert(B bean);
        /**
         * Update the B bean record in the database according to the changes.
         *
         * @param bean the B bean to be updated
         * @return the updated bean
         */
        //14
        protected abstract B update(B bean);
        
        public class ListAction implements Action<B> {
            final List<B> list;
            public ListAction() {
                list=new LinkedList<B>();
            }

            public List<B> getList() {
                return list;
            }

            @Override
            public void call(B bean) {
                list.add(bean);
            }

            @Override
            public B getBean() {
                return null;
            }
        }
        @Override
        public int countAll(){
            return this.countWhere("");
        }

        @Override
        public int countUsingTemplate(B bean){
            return this.countUsingTemplate(bean, SEARCH_EXACT);
        }

        @Override
        public int deleteAll(){
            return this.deleteByWhere("");
        }


        @Override
        public B[] loadAll(){
            return this.loadUsingTemplate(null);
        }

        @Override
        public int loadAll(Action<B> action){
            return this.loadUsingTemplate(null,action);
        }

        @Override
        public B[] loadAll(int startRow, int numRows){
            return this.loadUsingTemplate(null, startRow, numRows);
        }

        @Override
        public int loadAll(int startRow, int numRows, Action<B> action){
            return this.loadUsingTemplate(null, startRow, numRows,action);
        }

        @Override
        public List<B> loadAllAsList(){
            return this.loadUsingTemplateAsList(null);
        }

        @Override
        public List<B> loadAllAsList(int startRow, int numRows){
            return this.loadUsingTemplateAsList(null, startRow, numRows);
        }

        @Override
        public boolean existsPrimaryKey(B bean){
            return null!=loadByPrimaryKey(bean);
        }
        
        @Override
        public boolean existsPrimaryKey(Object ...keys){
            return null!=loadByPrimaryKey(keys);
        }
        
        @Override
        public B[] loadByWhere(String where){
            return this.loadByWhere(where, (int[])null);
        }

        @Override
        public int loadByWhere(String where, Action<B> action){
            return this.loadByWhere(where, null,action);
        }

        @Override
        public B[] loadByWhere(String where, int[] fieldList){
            return this.loadByWhere(where, fieldList, 1, -1);
        }

        @Override
        public int loadByWhere(String where, int[] fieldList, Action<B> action){
            return this.loadByWhere(where, fieldList, 1, -1,action);
        }

        @SuppressWarnings("unchecked")
        @Override
        public B[] loadByWhere(String where, int[] fieldList, int startRow, int numRows){
            return this.loadByWhereAsList(where, fieldList, startRow, numRows).toArray((B[])new Object[0]);
        }

        @Override
        public int loadByWhere(String where, int[] fieldList, int startRow, int numRows,
                Action<B> action){
            return this.loadByWhereForAction(where, fieldList, startRow, numRows,action);
        }

        @Override
        public List<B> loadByWhereAsList(String where){
            return this.loadByWhereAsList(where, null);
        }

        @Override
        public List<B> loadByWhereAsList(String where, int[] fieldList){
            return this.loadByWhereAsList(where, fieldList, 1, -1);
        }

        @Override
        public List<B> loadByWhereAsList(String where, int[] fieldList, int startRow, int numRows){
            ListAction action = new ListAction();
            loadByWhereForAction(where,fieldList,startRow,numRows,action);              
            return action.getList();
        }
    
        @Override
        public int loadByWhereForAction(String where, int[] fieldList, int startRow, int numRows,Action<B> action){
            String sql=createSelectSql(fieldList, where);
            // System.out.println("loadByWhere: " + sql);
            return this.loadBySqlForAction(sql, null, fieldList, startRow, numRows, action);
        }
    
        @Override
        public B[] loadUsingTemplate(B bean){
            return this.loadUsingTemplate(bean, 1, -1);
        }

        @Override
        public int loadUsingTemplate(B bean, Action<B> action){
            return this.loadUsingTemplate(bean, 1, -1,action);
        }

        @Override
        public B[] loadUsingTemplate(B bean, int startRow, int numRows){
            return this.loadUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
        }

        @Override
        public int loadUsingTemplate(B bean, int startRow, int numRows,
                Action<B> action){
            return this.loadUsingTemplate(bean, null, startRow, numRows,SEARCH_EXACT, action);
        }

        @SuppressWarnings("unchecked")
        @Override
        public B[] loadUsingTemplate(B bean, int startRow, int numRows, int searchType){
            return this.loadUsingTemplateAsList(bean, startRow, numRows, searchType).toArray((B[])new Object[0]);
        }

        @Override
        public List<B> loadUsingTemplateAsList(B bean){
            return this.loadUsingTemplateAsList(bean, 1, -1);
        }

        @Override
        public List<B> loadUsingTemplateAsList(B bean, int startRow, int numRows){
            return this.loadUsingTemplateAsList(bean, startRow, numRows, SEARCH_EXACT);
        }

        @Override
        public List<B> loadUsingTemplateAsList(B bean, int startRow, int numRows, int searchType){
            ListAction action = new ListAction();
            loadUsingTemplate(bean,null,startRow,numRows,searchType, action);
            return action.getList();
        }

        @Override
        public B save(B bean){
            if(null == bean)return null;
            if (bean.isNew()) {
                return this.insert(bean);
            } else {
                return this.update(bean);
            }
        }
        
        @Override
        public B[] save(B[] beans){
            if(null != beans){
                for (B bean : beans) 
                {
                    this.save(bean);
                }
            }
            return beans;
        }

        @Override
        public <C extends Collection<B>> C save(C beans){
            if(null != beans){
                for (B bean : beans) 
                {
                    this.save(bean);
                }
            }
            return beans;
        }
        
        @Override
        public <C extends Collection<B>> C saveAsTransaction(final C beans){
            return this.runAsTransaction(new Callable<C>(){
                @Override
                public C call() throws Exception {
                    return save(beans);
                }});
        }

        @Override
        public B[] saveAsTransaction(final B[] beans){
            return this.runAsTransaction(new Callable<B[]>(){
                @Override
                public B[] call() throws Exception {
                    return save(beans);
                }});
        }

        @SuppressWarnings("unchecked")
        @Override
        public B[] loadBySql(String sql, Object[] argList, int[] fieldList){
            return loadBySqlAsList(sql, argList, fieldList).toArray((B[])new Object[0]);
        }

        @Override
        public List<B> loadBySqlAsList(String sql, Object[] argList, int[] fieldList){
            ListAction action = new ListAction();
            loadBySqlForAction(sql,argList,fieldList,1,-1,action);
            return action.getList();
        }
        
        @Override
        public String createSelectSql(int[] fieldList,String where){
            StringBuffer sql = new StringBuffer(128);
            if(fieldList == null) {
                sql.append("SELECT ").append(this.getFieldNamesAsString());
            } else{
                sql.append("SELECT ");
                for(int i = 0; i < fieldList.length; ++i){
                    if(i != 0) {
                        sql.append(",");
                    }
                    sql.append(this.getFullFieldNames()[fieldList[i]]);
                }            
            }
            sql.append(" FROM " + this.getTableName() + " ");
            if(null!=where)
                sql.append(where);
            return sql.toString();
        }

        @Override
        public int delete(B bean){
            return deleteByPrimaryKey(bean);
        }
        
        @Override
        public <T extends BaseBean> T getReferencedBean(B bean, String fkName){
            throw new UnsupportedOperationException();
        }

        @Override
        public <T extends BaseBean> T setReferencedBean(B bean, T beanToSet, String fkName){
            throw new UnsupportedOperationException();
        }

        @Override
        public <T extends BaseBean> T[] getImportedBeans(B bean, String fkName){
            throw new UnsupportedOperationException();
        }

        @Override
        public <T extends BaseBean> List<T> getImportedBeansAsList(B bean, String fkName){
            throw new UnsupportedOperationException();
        }

        @Override
        public <T extends BaseBean> T[] setImportedBeans(B bean, T[] importedBeans, String fkName){
            throw new UnsupportedOperationException();
        }

        @Override
        public <T extends BaseBean, C extends Collection<T>> C setImportedBeans(B bean, C importedBeans,
                String fkName){
            throw new UnsupportedOperationException();
        }

        @Override
        public B loadByPrimaryKey(B bean){
            throw new UnsupportedOperationException();
        }

        @Override
        public B loadByPrimaryKey(Object ...keys){
            throw new UnsupportedOperationException();
        }

        @Override
        public int deleteByPrimaryKey(Object ...keys){
            throw new UnsupportedOperationException();
        }

    }

    public abstract String[] getFieldNames();

    public abstract String[] getPrimarykeyNames();

    public abstract String getTableName();
    
    public abstract String getFieldNamesAsString();
    
    public abstract String[] getFullFieldNames();
    
    /**
     * return true if @{code column}(case insensitive)is primary key,otherwise return false <br>
     * return false if @{code column} is null or empty 
     * @param column
     * @return
     */
    //43
    public abstract boolean isPrimaryKey(String column);
    
    //_____________________________________________________________________
    //
    // COUNT
    //_____________________________________________________________________
    /**
     * Retrieves the number of rows of the table.
     *
     * @return the number of rows returned
     */
    //24
    public abstract int countAll();
    
    /**
     * count the number of elements of a specific bean
     *
     * @param bean the bean to look for ant count
     * @return the number of rows returned
     */
    //27
    public abstract int countUsingTemplate( B bean);
  
    /**
     * count the number of elements of a specific bean given the search type
     *
     * @param bean the template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ? ending link ? <br>
     *                {@value #SEARCH_EXACT}   {@link #SEARCH_EXACT} <br>
     *                {@value #SEARCH_LIKE}   {@link #SEARCH_LIKE} <br>
     *                {@value #SEARCH_STARTING_LIKE}   {@link #SEARCH_STARTING_LIKE} <br>
     *                {@value #SEARCH_ENDING_LIKE}   {@link #SEARCH_ENDING_LIKE} <br>  
     * @return the number of rows returned
     */
    //20
    public abstract int countUsingTemplate(B bean, int searchType);

    /**
     * Retrieves the number of rows of the table with a 'where' clause.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the restriction clause
     * @return the number of rows returned
     */
    //25
    public abstract int countWhere(String where);

    /**
     * Deletes all rows from table.
     * @return the number of deleted rows.
     */
    //10
    public abstract int deleteAll();

    /**
     * Deletes rows from the table using a 'where' clause.
     * It is up to you to pass the 'WHERE' in your where clausis.
     * <br>Attention, if 'WHERE' is omitted it will delete all records.
     *
     * @param where the sql 'where' clause
     * @return the number of deleted rows
     */
    //11
    public abstract int deleteByWhere(String where);

    /**
     * Deletes rows using a template.
     *
     * @param bean the template object(s) to be deleted
     * @return the number of deleted objects
     */
    //21
    public abstract int deleteUsingTemplate(B bean);

    /**
     * Delete row according to its primary keys.
     *
     * @param keys primary keys value
     * @return the number of deleted rows
     */   
    //2.1
    public abstract int deleteByPrimaryKey(Object ...keys);

    /**
     * Delete row according to primary keys of bean.<br>
     * 
     * @param bean will be deleted ,all keys must not be null
     * @return the number of deleted rows,0 returned if bean is null
     */
    //2.2
    public abstract int delete(B bean);


    //////////////////////////////////////
    // LOAD ALL
    //////////////////////////////////////

    /**
     * Loads all the rows from table.
     *
     * @return an array of B bean
     */
    //5
    public abstract B[] loadAll();

    /**
     * Loads each row from table and dealt with action.
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //5-1    
    public abstract int loadAll(Action<B> action);

    /**
     * Loads the given number of rows from table, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return an array of B bean
     */
    //6
    public abstract B[] loadAll(int startRow, int numRows);

    /**
     *  Loads the given number of rows from table, given the start row and dealt with action.
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //6-1    
    public abstract int loadAll(int startRow, int numRows,Action<B> action);

    /**
     * Loads all the rows from table.
     *
     * @return a list of B bean
     */
    //5-2
    public abstract List<B> loadAllAsList();

    /**
     * Loads the given number of rows from table, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return a list of B bean
     */
    //6-2
    public abstract List<B> loadAllAsList(int startRow, int numRows);

    /**
     * Loads a B bean from the table using primary key fields of {@code bean}.
     * when you don't know which is primary key of table,you can use the method.
     * @param bean the B bean with primary key fields
     * @return a unique B or {@code null} if not found or bean is null
     */
    //1.2
    public abstract B loadByPrimaryKey(B bean);
    
    /**
     * Loads a B bean from the table using primary key fields.
     * when you don't know which is primary key of table,you can use the method.
     * @param keys primary keys value:<br> 
     * @return a unique B or {@code null} if not found
     */
    //1.3
    public abstract B loadByPrimaryKey(Object ...keys);
    
    /**
     * Returns true if this table contains row with primary key fields.
     * @param keys primary keys value:<br>
     * @see #loadByPrimaryKey(Object...)
     */
    //1.5
    public abstract boolean existsPrimaryKey(Object ...keys);
    
    /**
     * Returns true if this table contains row specified by primary key fields of B.<br>
     * when you don't know which is primary key of table,you can use the method.
     * @param bean the B bean with primary key fields
     * @return 
     * @see {@link #loadByPrimaryKey(B bean)}
     */
    //1.6
    public abstract boolean existsPrimaryKey(B bean);
   
    //////////////////////////////////////
    // SQL 'WHERE' METHOD
    //////////////////////////////////////
    /**
     * Retrieves an array of B given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return 
     */
    //7 
    public abstract B[] loadByWhere(String where);
    
    /**
     * Retrieves each row of B bean given a sql 'where' clause and dealt with action.
     * @param where the sql 'where' clause
     * @param action  Action object for do something(not null)
     * @return the count dealt by action
     */
    //7-1
    public abstract int loadByWhere(String where,Action<B> action);

    /**
     * Retrieves an array of B bean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return 
     */
    //8
    public abstract B[] loadByWhere(String where, int[] fieldList);
   
    /**
     * Retrieves each row of B bean given a sql where clause, and a list of fields,
     * and dealt with action.
     * It is up to you to pass the 'WHERE' in your where clausis.
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //8-1 
    public abstract int loadByWhere(String where, int[] fieldList,Action<B> action);

    /**
     * Retrieves an array of B bean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return 
     */
    //9
    public abstract B[] loadByWhere(String where, int[] fieldList, int startRow, int numRows);

    /**
     * Retrieves each row of B bean given a sql where clause and a list of fields, and startRow and numRows,
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
    public abstract int loadByWhere(String where, int[] fieldList, int startRow, int numRows,Action<B> action);
    /**
     * Retrieves a list of B bean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return
     */
    //7
    public abstract List<B> loadByWhereAsList(String where);

    /**
     * Retrieves a list of B bean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return
     */
    //8
    public abstract List<B> loadByWhereAsList(String where, int[] fieldList);
    
    /**
     * Retrieves a list of B bean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return
     */
    //9-2
    public abstract List<B> loadByWhereAsList(String where, int[] fieldList, int startRow, int numRows);

    /**
     * Retrieves each row of B bean given a sql where clause and a list of fields, and startRow and numRows,
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
    public abstract int loadByWhereForAction(String where, int[] fieldList, int startRow, int numRows,Action<B> action);

    //_____________________________________________________________________
    //
    // USING TEMPLATE
    //_____________________________________________________________________
    /**
     * Loads a unique B bean from a template one giving a c
     *
     * @param bean the B bean to look for
     * @return the bean matching the template
     */
    //18   
    public abstract B loadUniqueUsingTemplate(B bean);

    /**
     * Loads an array of B from a template one.
     *
     * @param bean the B bean template to look for
     * @return all the B beans matching the template
     */
    //19
    public abstract B[] loadUsingTemplate(B bean);
    
    /**
     * Loads each row from a template one and dealt with action.
     *
     * @param bean the B bean template to look for
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //19-1
    public abstract int loadUsingTemplate(B bean,Action<B> action);

    /**
     * Loads an array of B bean from a template one, given the start row and number of rows.
     *
     * @param bean the B bean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the B matching the template
     */
    //20
    public abstract B[] loadUsingTemplate(B bean, int startRow, int numRows);
    
    /**
     * Loads each row from a template one, given the start row and number of rows and dealt with action.
     *
     * @param bean the B bean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //20-1
    public abstract int loadUsingTemplate(B bean, int startRow, int numRows,Action<B> action);

    /**
     * Loads each row from a template one, given the start row and number of rows and dealt with action.
     *
     * @param bean the B template to look for
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ? ending link ? <br>
     *                {@value #SEARCH_EXACT}   {@link #SEARCH_EXACT} <br>
     *                {@value #SEARCH_LIKE}   {@link #SEARCH_LIKE} <br>
     *                {@value #SEARCH_STARTING_LIKE}   {@link #SEARCH_STARTING_LIKE} <br>
     *                {@value #SEARCH_ENDING_LIKE}   {@link #SEARCH_ENDING_LIKE} <br>  
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    //20-5
    public abstract int loadUsingTemplate(B bean, int[] fieldList, int startRow, int numRows,int searchType, Action<B> action);
    /**
     * Loads a list of B bean from a template one, given the start row and number of rows.
     *
     * @param bean the B bean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ? ending link ? <br>
     *                {@value #SEARCH_EXACT}   {@link #SEARCH_EXACT} <br>
     *                {@value #SEARCH_LIKE}   {@link #SEARCH_LIKE} <br>
     *                {@value #SEARCH_STARTING_LIKE}   {@link #SEARCH_STARTING_LIKE} <br>
     *                {@value #SEARCH_ENDING_LIKE}   {@link #SEARCH_ENDING_LIKE} <br>  
     * @return all the B bean matching the template
     */
    //20-4
    public abstract B[] loadUsingTemplate(B bean, int startRow, int numRows, int searchType);

    /**
     * Loads a list of B bean from a template one.
     *
     * @param bean the B bean template to look for
     * @return all the B beans matching the template
     */
    //19-2
    public abstract List<B> loadUsingTemplateAsList(B bean);

    /**
     * Loads a list of B bean from a template one, given the start row and number of rows.
     *
     * @param bean the B bean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the B bean matching the template
     */
    //20-2
    public abstract List<B> loadUsingTemplateAsList(B bean, int startRow, int numRows);

    /**
     * Loads an array of B bean from a template one, given the start row and number of rows.
     *
     * @param bean the B bean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ? ending link? <br>
     *                {@value #SEARCH_EXACT}   {@link #SEARCH_EXACT} <br>
     *                {@value #SEARCH_LIKE}   {@link #SEARCH_LIKE} <br>
     *                {@value #SEARCH_STARTING_LIKE}   {@link #SEARCH_STARTING_LIKE} <br>
     *                {@value #SEARCH_ENDING_LIKE}   {@link #SEARCH_ENDING_LIKE} <br>  
     * @return all the B beans matching the template
     */
    //20-3
    public abstract List<B> loadUsingTemplateAsList(B bean, int startRow, int numRows, int searchType);
    
    //_____________________________________________________________________
    //
    // LISTENER
    //_____________________________________________________________________

    /**
     * Registers a unique {@link TableListener} listener.
     */
    //35
    public abstract void registerListener(TableListener<B> listener);

    /**
     * remove listener.
     */
    //36
    public abstract void unregisterListener(TableListener<B> listener);

    //_____________________________________________________________________
    //
    // SAVE
    //_____________________________________________________________________
    /**
     * Saves the B bean into the database.
     *
     * @param bean the B bean to be saved
     * @return the inserted or updated bean,or null if bean is null
     */
    //12
    public abstract B save(B bean);

    /**
     * Saves an array of B bean into the database.
     *
     * @param beans the array of  B bean to be saved
     * @return alwarys beans saved
     */
    //15
    public abstract B[] save(B[] beans);
    
    /**
     * Saves a collection of B bean into the database.
     *
     * @param beans the B bean table to be saved
     * @return alwarys beans saved
     */
    //15-2
    public abstract <C extends Collection<B>> C saveAsTransaction(C beans);
    
    /**
     * Saves an array of B bean into the database as transaction.
     *
     * @param beans the B bean table to be saved
     * @return alwarys beans saved
     * @see #save(B[])
     */
    //15-3
    public abstract B[] saveAsTransaction(B[] beans);

    /**
     * Saves a collection of B bean into the database as transaction.
     *
     * @param beans the B bean table to be saved
     * @return alwarys beans saved
     */
    //15-4
    public abstract <C extends Collection<B>> C save(C beans);

    /**
     * Load all the elements using a SQL statement specifying a list of fields to be retrieved.
     * @param sql the SQL statement for retrieving
     * @param argList the arguments to use fill given prepared statement,may be null
     * @param fieldList table of the field's associated constants
     * @return an array of B bean
     */
    public abstract B[] loadBySql(String sql, Object[] argList, int[] fieldList);
    
    /**
     * Load all elements using a SQL statement specifying a list of fields to be retrieved.
     * @param sql the SQL statement for retrieving
     * @param argList the arguments to use fill given prepared statement,may be null
     * @param fieldList table of the field's associated constants
     * @return an list of B bean
     */
    public abstract List<B> loadBySqlAsList(String sql, Object[] argList, int[] fieldList);
    
    /**
     * Load each the elements using a SQL statement specifying a list of fields to be retrieved and dealt by action.
     * @param sql the SQL statement for retrieving
     * @param argList the arguments to use fill given prepared statement,may be null
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param action Action object for do something(not null)
     * @return the count dealt by action
     */
    public int loadBySqlForAction(String sql, Object[] argList, int[] fieldList,int startRow, int numRows,Action<B> action);
    /**
     * Run {@code Callable<T>} as a transaction.<br>
     * all exceptions but {@code SQLException} threw by {@code Callable<T>} is warpped into {@code RuntimeException}<br>
     * throw {@code NullPointerException} if {@code fun} be {@code null}<br>
     * @param <T>  type of return result
     * @param fun
     * @return
     */
    public abstract<T> T runAsTransaction(Callable<T> fun);
    /**
     * Run {@code Runnable} as a transaction.no return
     * @param fun
     * @
     * @see #runAsTransaction(Runnable)
     */
    public abstract void runAsTransaction(final Runnable fun);
    
    /**
     * Retrieves the T object referenced by fkName.<br>
     * @param bean the B object to use
     * @param fkName foreign key name. for detail see implementation class
     * @return the associated <T> bean or {@code null} if {@code bean}  is {@code null}
     */
    public abstract <T extends BaseBean> T getReferencedBean(B bean,String fkName);
    
    /**
     * Associates the B object to the T object by fkName field.<br>
     * @param bean the B object to use
     * @param beanToSet the T object to associate to the B bean
     * @param fkName
     * @return always beanToSet saved
     */
    public abstract <T extends BaseBean> T setReferencedBean(B bean,T beanToSet,String fkName);
    
    /**
     * Retrieves imported T objects by fkName.<br>
     * @param bean the B object to use
     * @param fkName foreign key name. for detail see implementation class
     * @return the associated T beans or {@code null} if {@code bean} is {@code null}
     */
    public <T extends BaseBean> T[] getImportedBeans(B bean,String fkName);
    
    /**
     * Retrieves imported T objects by fkName.<br>
     * @param bean the B object to use
     * @param fkName foreign key name. for detail see implementation class
     * @return the associated T beans or {@code null} if {@code bean} is {@code null}
     */
    public <T extends BaseBean> List<T> getImportedBeansAsList(B bean,String fkName);
    
    /**
     * Set the importedBeans associates to the bean by fkName<br>
     * 
     * @param bean the bean object to use
     * @param importedBeans the T object to associate to bean
     * @param fkName foreign key name. for detail see implementation class
     * @return importedBeans always
     */
    public <T extends BaseBean> T[] setImportedBeans(B bean,T[] importedBeans,String fkName);
    
    /**
     * Set the importedBeans associates to the bean by fkName<br>
     * 
     * @param bean the bean object to use
     * @param importedBeans the T object to associate to bean
     * @param fkName foreign key name. for detail see implementation class
     * @return importedBeans always
     */
    public <T extends BaseBean,C extends Collection<T>> C setImportedBeans(B bean,C importedBeans,String fkName);
    
    public String createSelectSql(int[] fieldList,String where);
}
