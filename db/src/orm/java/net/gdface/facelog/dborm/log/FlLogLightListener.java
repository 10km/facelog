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
 * Listener that is notified of fl_log_light table changes.
 * @author sql2java
 */
public interface FlLogLightListener extends TableListener
{
    /**
     * This adapter class provides default implementations for the
     * methods described by the {@link FlLogLightListener} interface.<br>
     * 
     * @see FlLogLightListener
     * @author sql2java
     *
     */
    public static class Adapter implements FlLogLightListener{

        @Override
        public void beforeInsert(FlLogLightBean bean) throws DAOException {}

        @Override
        public void afterInsert(FlLogLightBean bean) throws DAOException {
        }

        @Override
        public void beforeUpdate(FlLogLightBean bean) throws DAOException {}

        @Override
        public void afterUpdate(FlLogLightBean bean) throws DAOException {}

        @Override
        public void beforeDelete(FlLogLightBean bean) throws DAOException {}

        @Override
        public void afterDelete(FlLogLightBean bean) throws DAOException {}
    }
    /**
     * Invoked just before inserting a FlLogLightBean record into the database.
     *
     * @param bean the FlLogLightBean that is about to be inserted
     */
    public void beforeInsert(FlLogLightBean bean) throws DAOException;


    /**
     * Invoked just after a FlLogLightBean record is inserted in the database.
     *
     * @param bean the FlLogLightBean that was just inserted
     */
    public void afterInsert(FlLogLightBean bean) throws DAOException;


    /**
     * Invoked just before updating a FlLogLightBean record in the database.
     *
     * @param bean the FlLogLightBean that is about to be updated
     */
    public void beforeUpdate(FlLogLightBean bean) throws DAOException;


    /**
     * Invoked just after updating a FlLogLightBean record in the database.
     *
     * @param bean the FlLogLightBean that was just updated
     */
    public void afterUpdate(FlLogLightBean bean) throws DAOException;


    /**
     * Invoked just before deleting a FlLogLightBean record in the database.
     *
     * @param bean the FlLogLightBean that is about to be deleted
     */
    public void beforeDelete(FlLogLightBean bean) throws DAOException;


    /**
     * Invoked just after deleting a FlLogLightBean record in the database.
     *
     * @param bean the FlLogLightBean that was just deleted
     */
    public void afterDelete(FlLogLightBean bean) throws DAOException;

}
