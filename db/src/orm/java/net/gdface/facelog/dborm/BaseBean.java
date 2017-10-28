// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: basebean.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm;

/**
 * @author guyadong
 */
public interface BaseBean <B> {
    public boolean isNew();
    public void isNew(boolean isNew);
    public boolean isModified();
    public void resetIsModified();
    public void resetPrimaryKeysModified();
    public boolean isInitialized(int columnID);
    public boolean isModified(int columnID);
    public boolean isInitialized(String column);
    public boolean isModified(String column);
    public void copy(B bean, String... fieldList);
    public void copy(B bean, int... fieldList);
    public <T> T getValue(int columnID);
    public <T> void setValue(int columnID,T value);
    public <T> T getValue(String column);
    public <T> void setValue(String column,T value);
}
