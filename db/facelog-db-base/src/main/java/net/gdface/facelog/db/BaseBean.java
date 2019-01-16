// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: base.bean.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

/**
 * @author guyadong
 *
 * @param <B>
 *
 */
public interface BaseBean <B> {
    /**
     * Determines if the current object is new.
     *
     * @return true if the current object is new, false if the object is not new
     */
    public boolean isNew();
    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    public void isNew(boolean isNew);

    /**
     * Determines if the object has been modified since the last time this method was called.<br>
     * We can also determine if this object has ever been modified since its creation.
     *
     * @return true if the object has been modified, false if the object has not been modified
     */
    public boolean isModified();
    /**
     * Resets the object modification status to 'not modified'.
     */
    public void resetIsModified();
    /**
     * Resets the primary keys modification status to 'not modified'.
     */
    public void resetPrimaryKeysModified();
    /**
     * Determines if the {@code columnID} has been initialized.<br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     * @param columnID column id
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isInitialized(int columnID);
    /**
     * Determines if the {@code column} has been modified.
     * @param columnID column id
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isModified(int columnID);
    /**
     * Determines if the {@code column} has been initialized.<br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     * @param column column name
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isInitialized(String column);
    /**
     * Determines if the {@code column} has been modified.
     * @param column column name
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isModified(String column);
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @return always {@code bean}
     */
    public B copy(B bean);
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column id list to copy into the current bean
     * @return always {@code bean}
     */
    public B copy(B bean, int... fieldList);
    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     * @param fieldList the column name list to copy into the current bean
     * @return always {@code bean}
     */
    public B copy(B bean, String... fieldList);
    /**
     * 
     * @param columnID column id
     * @return return a object representation of the given column id
     */
    public <T> T getValue(int columnID);
    /**
     * set a value representation of the given column id
     * @param columnID column id
     * @param value
     */
    public <T> void setValue(int columnID,T value);
    /**
     * 
     * @param column column name
     * @return return a object representation of the given field
     */
    public <T> T getValue(String column);
    /**
     * set a value representation of the given field
     * @param column column name
     * @param value
     */
    public <T> void setValue(String column,T value);
    /**
     * @param notNull output not null field only if {@code true}
     * @param fullIfStringOrBytes for string or bytes field,output full content if {@code true},otherwise output length.
     * @return Returns a string representation of the object
     */
    public String toString(boolean notNull, boolean fullIfStringOrBytes);
}