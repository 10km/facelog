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
 * Listener that is notified of fl_feature table changes.
 * @author sql2java
 */
public interface FlFeatureListener extends TableListener
{
    /**
     * This adapter class provides default implementations for the
     * methods described by the {@link FlFeatureListener} interface.<br>
     * 
     * @see FlFeatureListener
     * @author sql2java
     *
     */
    public static class Adapter implements FlFeatureListener{

        @Override
        public void beforeInsert(FlFeatureBean bean) throws DAOException {}

        @Override
        public void afterInsert(FlFeatureBean bean) throws DAOException {
        }

        @Override
        public void beforeUpdate(FlFeatureBean bean) throws DAOException {}

        @Override
        public void afterUpdate(FlFeatureBean bean) throws DAOException {}

        @Override
        public void beforeDelete(FlFeatureBean bean) throws DAOException {}

        @Override
        public void afterDelete(FlFeatureBean bean) throws DAOException {}
    }
    /**
     * Invoked just before inserting a FlFeatureBean record into the database.
     *
     * @param bean the FlFeatureBean that is about to be inserted
     */
    public void beforeInsert(FlFeatureBean bean) throws DAOException;


    /**
     * Invoked just after a FlFeatureBean record is inserted in the database.
     *
     * @param bean the FlFeatureBean that was just inserted
     */
    public void afterInsert(FlFeatureBean bean) throws DAOException;


    /**
     * Invoked just before updating a FlFeatureBean record in the database.
     *
     * @param bean the FlFeatureBean that is about to be updated
     */
    public void beforeUpdate(FlFeatureBean bean) throws DAOException;


    /**
     * Invoked just after updating a FlFeatureBean record in the database.
     *
     * @param bean the FlFeatureBean that was just updated
     */
    public void afterUpdate(FlFeatureBean bean) throws DAOException;


    /**
     * Invoked just before deleting a FlFeatureBean record in the database.
     *
     * @param bean the FlFeatureBean that is about to be deleted
     */
    public void beforeDelete(FlFeatureBean bean) throws DAOException;


    /**
     * Invoked just after deleting a FlFeatureBean record in the database.
     *
     * @param bean the FlFeatureBean that was just deleted
     */
    public void afterDelete(FlFeatureBean bean) throws DAOException;

}
