// ______________________________________________________
// Generated by sql2java - http://sql2java.sourceforge.net/
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
//
// Please help us improve this tool by reporting:
// - problems and suggestions to
//   http://sourceforge.net/tracker/?group_id=54687
// - feedbacks and ideas on
//   http://sourceforge.net/forum/forum.php?forum_id=182208
// ______________________________________________________

package net.gdface.facelog.dborm.face;

import net.gdface.facelog.dborm.exception.DAOException;
import net.gdface.facelog.dborm.TableListener;
/**
 * Listener that is notified of fl_face table changes.
 * @author sql2java
 */
public interface FlFaceListener extends TableListener
{
    /**
     * This adapter class provides default implementations for the
     * methods described by the {@link FlFaceListener} interface.<br>
     * 
     * @see FlFaceListener
     * @author sql2java
     *
     */
    public static class Adapter implements FlFaceListener{

        @Override
        public void beforeInsert(FlFaceBean bean) throws DAOException {}

        @Override
        public void afterInsert(FlFaceBean bean) throws DAOException {
        }

        @Override
        public void beforeUpdate(FlFaceBean bean) throws DAOException {}

        @Override
        public void afterUpdate(FlFaceBean bean) throws DAOException {}

        @Override
        public void beforeDelete(FlFaceBean bean) throws DAOException {}

        @Override
        public void afterDelete(FlFaceBean bean) throws DAOException {}
    }
    /**
     * Invoked just before inserting a FlFaceBean record into the database.
     *
     * @param bean the FlFaceBean that is about to be inserted
     */
    public void beforeInsert(FlFaceBean bean) throws DAOException;


    /**
     * Invoked just after a FlFaceBean record is inserted in the database.
     *
     * @param bean the FlFaceBean that was just inserted
     */
    public void afterInsert(FlFaceBean bean) throws DAOException;


    /**
     * Invoked just before updating a FlFaceBean record in the database.
     *
     * @param bean the FlFaceBean that is about to be updated
     */
    public void beforeUpdate(FlFaceBean bean) throws DAOException;


    /**
     * Invoked just after updating a FlFaceBean record in the database.
     *
     * @param bean the FlFaceBean that was just updated
     */
    public void afterUpdate(FlFaceBean bean) throws DAOException;


    /**
     * Invoked just before deleting a FlFaceBean record in the database.
     *
     * @param bean the FlFaceBean that is about to be deleted
     */
    public void beforeDelete(FlFaceBean bean) throws DAOException;


    /**
     * Invoked just after deleting a FlFaceBean record in the database.
     *
     * @param bean the FlFaceBean that was just deleted
     */
    public void afterDelete(FlFaceBean bean) throws DAOException;

}
