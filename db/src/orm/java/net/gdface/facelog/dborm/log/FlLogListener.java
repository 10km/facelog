// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.log;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.TableListener;
/**
 * Listener that is notified of fl_log table changes.
 * @author sql2java
 */
public interface FlLogListener extends TableListener
{
    /**
     * This adapter class provides default implementations for the
     * methods described by the {@link FlLogListener} interface.<br>
     * 
     * @see FlLogListener
     * @author sql2java
     *
     */
    public static class Adapter implements FlLogListener{

        @Override
        public void beforeInsert(FlLogBean bean) throws DAOException {}

        @Override
        public void afterInsert(FlLogBean bean) throws DAOException {
        }

        @Override
        public void beforeUpdate(FlLogBean bean) throws DAOException {}

        @Override
        public void afterUpdate(FlLogBean bean) throws DAOException {}

        @Override
        public void beforeDelete(FlLogBean bean) throws DAOException {}

        @Override
        public void afterDelete(FlLogBean bean) throws DAOException {}
    }
    /**
     * Invoked just before inserting a FlLogBean record into the database.
     *
     * @param bean the FlLogBean that is about to be inserted
     */
    public void beforeInsert(FlLogBean bean) throws DAOException;


    /**
     * Invoked just after a FlLogBean record is inserted in the database.
     *
     * @param bean the FlLogBean that was just inserted
     */
    public void afterInsert(FlLogBean bean) throws DAOException;


    /**
     * Invoked just before updating a FlLogBean record in the database.
     *
     * @param bean the FlLogBean that is about to be updated
     */
    public void beforeUpdate(FlLogBean bean) throws DAOException;


    /**
     * Invoked just after updating a FlLogBean record in the database.
     *
     * @param bean the FlLogBean that was just updated
     */
    public void afterUpdate(FlLogBean bean) throws DAOException;


    /**
     * Invoked just before deleting a FlLogBean record in the database.
     *
     * @param bean the FlLogBean that is about to be deleted
     */
    public void beforeDelete(FlLogBean bean) throws DAOException;


    /**
     * Invoked just after deleting a FlLogBean record in the database.
     *
     * @param bean the FlLogBean that was just deleted
     */
    public void afterDelete(FlLogBean bean) throws DAOException;

}
