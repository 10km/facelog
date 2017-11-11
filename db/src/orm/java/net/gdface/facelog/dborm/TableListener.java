// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: tablelistener.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm;

import java.util.LinkedHashSet;
import net.gdface.facelog.dborm.exception.DAOException;

/**
 * Listener that is notified of table changes.
 * @author guyadong
 */
public interface TableListener<B>{
    /**
     * This adapter class provides default implementations for the
     * methods declared by the {@link TableListener} interface.<br>
     * 
     * @author guyadong
     */
    public static class Adapter<B> implements TableListener<B>{

        @Override
        public void beforeInsert(B bean)throws DAOException {}

        @Override
        public void afterInsert(B bean)throws DAOException {}

        @Override
        public void beforeUpdate(B bean)throws DAOException {}

        @Override
        public void afterUpdate(B bean)throws DAOException {}

        @Override
        public void beforeDelete(B bean)throws DAOException {}

        @Override
        public void afterDelete(B bean)throws DAOException {}
    }
    /**
     * Invoked just before inserting a B record into the database.
     *
     * @param bean the B that is about to be inserted
     * @throws DAOException
     */
    public void beforeInsert(B bean)throws DAOException;


    /**
     * Invoked just after a B record is inserted in the database.
     *
     * @param bean the B that was just inserted
     * @throws DAOException
     */
    public void afterInsert(B bean)throws DAOException;


    /**
     * Invoked just before updating a B record in the database.
     *
     * @param bean the B that is about to be updated
     * @throws DAOException
     */
    public void beforeUpdate(B bean)throws DAOException;


    /**
     * Invoked just after updating a B record in the database.
     *
     * @param bean the B that was just updated
     * @throws DAOException
     */
    public void afterUpdate(B bean)throws DAOException;


    /**
     * Invoked just before deleting a B record in the database.
     *
     * @param bean the B that is about to be deleted
     * @throws DAOException
     */
    public void beforeDelete(B bean)throws DAOException;


    /**
     * Invoked just after deleting a B record in the database.
     *
     * @param bean the B that was just deleted
     * @throws DAOException
     */
    public void afterDelete(B bean)throws DAOException;

    /**
     * listener event
     * {@code INSERT} insert a bean<br>
     * {@code UPDATE} update a bean<br>
     * {@code DELETE} delete a bean<br>
     * @author guyadong
     *
     */
    public static enum Event{
        INSERT,UPDATE,DELETE;
        /**
         * fire current event by  {@link ListenerContainer}
         * @param container
         * @param bean
         * @throws DAOException
         */
        public <B> void fire(ListenerContainer<B> container,B bean)throws DAOException {
            if(null == container || null == bean){
                return;
            }
            switch(this){
            case INSERT:
                container.afterInsert(bean);
                break;
            case UPDATE:
                container.afterUpdate(bean);
                break;
            case DELETE:
                container.afterDelete(bean);
                break;
            default:
                break;
            }
        }
        public <B extends BaseBean<B>> void fire(TableManager<B > manager,B bean)throws DAOException {
            if(null == manager || null == bean){
                return;
            }
            manager.fire(this, bean);
        }
    }
    /** container for manager multiple listener */
    public static class ListenerContainer <B> implements TableListener<B> {
        private final LinkedHashSet<TableListener<B>> listeners = new LinkedHashSet<TableListener<B>>();
        public ListenerContainer() {
        }
    
        @Override
        public void beforeInsert(B bean)throws DAOException{
            for(TableListener<B> listener:listeners){
                listener.beforeInsert(bean);
            }
        }
    
        @Override
        public void afterInsert(B bean)throws DAOException{
            for(TableListener<B> listener:listeners){
                listener.afterInsert(bean);
            }
        }
    
        @Override
        public void beforeUpdate(B bean)throws DAOException{
            for(TableListener<B> listener:listeners){
                listener.beforeUpdate(bean);
            }
        }
    
        @Override
        public void afterUpdate(B bean)throws DAOException{
            for(TableListener<B> listener:listeners){
                listener.afterUpdate(bean);
            }
        }
    
        @Override
        public void beforeDelete(B bean)throws DAOException{
            for(TableListener<B> listener:listeners){
                listener.beforeDelete(bean);
            }
        }
    
        @Override
        public void afterDelete(B bean)throws DAOException{
            for(TableListener<B> listener:listeners){
                listener.afterDelete(bean);
            }
        }
    
        public boolean isEmpty() {
            return listeners.isEmpty();
        }
    
        public boolean contains(TableListener<B> o) {
            return listeners.contains(o);
        }
    
        public synchronized boolean add(TableListener<B> e) {
            if(null == e){
                throw new NullPointerException();
            }
            return listeners.add(e);
        }
    
        public synchronized boolean remove(TableListener<B> o) {
            return null == o? false : listeners.remove(o);
        }
    
        public synchronized void clear() {
            listeners.clear();
        }
    }
}

