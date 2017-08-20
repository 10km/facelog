// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm.face;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.TableListener;
/**
 * Listener that is notified of fl_face_light table changes.
 * @author sql2java
 */
public interface FlFaceLightListener extends TableListener
{
    /**
     * This adapter class provides default implementations for the
     * methods described by the {@link FlFaceLightListener} interface.<br>
     * 
     * @see FlFaceLightListener
     * @author sql2java
     *
     */
    public static class Adapter implements FlFaceLightListener{

        @Override
        public void beforeInsert(FlFaceLightBean bean) throws DAOException {}

        @Override
        public void afterInsert(FlFaceLightBean bean) throws DAOException {
        }

        @Override
        public void beforeUpdate(FlFaceLightBean bean) throws DAOException {}

        @Override
        public void afterUpdate(FlFaceLightBean bean) throws DAOException {}

        @Override
        public void beforeDelete(FlFaceLightBean bean) throws DAOException {}

        @Override
        public void afterDelete(FlFaceLightBean bean) throws DAOException {}
    }
    /**
     * Invoked just before inserting a FlFaceLightBean record into the database.
     *
     * @param bean the FlFaceLightBean that is about to be inserted
     */
    public void beforeInsert(FlFaceLightBean bean) throws DAOException;


    /**
     * Invoked just after a FlFaceLightBean record is inserted in the database.
     *
     * @param bean the FlFaceLightBean that was just inserted
     */
    public void afterInsert(FlFaceLightBean bean) throws DAOException;


    /**
     * Invoked just before updating a FlFaceLightBean record in the database.
     *
     * @param bean the FlFaceLightBean that is about to be updated
     */
    public void beforeUpdate(FlFaceLightBean bean) throws DAOException;


    /**
     * Invoked just after updating a FlFaceLightBean record in the database.
     *
     * @param bean the FlFaceLightBean that was just updated
     */
    public void afterUpdate(FlFaceLightBean bean) throws DAOException;


    /**
     * Invoked just before deleting a FlFaceLightBean record in the database.
     *
     * @param bean the FlFaceLightBean that is about to be deleted
     */
    public void beforeDelete(FlFaceLightBean bean) throws DAOException;


    /**
     * Invoked just after deleting a FlFaceLightBean record in the database.
     *
     * @param bean the FlFaceLightBean that was just deleted
     */
    public void afterDelete(FlFaceLightBean bean) throws DAOException;

}