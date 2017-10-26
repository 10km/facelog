// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: tablelistener.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

import java.util.LinkedHashSet;

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
        public void beforeInsert(B bean) {}

        @Override
        public void afterInsert(B bean) {}

        @Override
        public void beforeUpdate(B bean) {}

        @Override
        public void afterUpdate(B bean) {}

        @Override
        public void beforeDelete(B bean) {}

        @Override
        public void afterDelete(B bean) {}
    }
    /**
     * Invoked just before inserting a B record into the database.
     *
     * @param bean the B that is about to be inserted
     */
    public void beforeInsert(B bean);


    /**
     * Invoked just after a B record is inserted in the database.
     *
     * @param bean the B that was just inserted
     */
    public void afterInsert(B bean);


    /**
     * Invoked just before updating a B record in the database.
     *
     * @param bean the B that is about to be updated
     */
    public void beforeUpdate(B bean);


    /**
     * Invoked just after updating a B record in the database.
     *
     * @param bean the B that was just updated
     */
    public void afterUpdate(B bean);


    /**
     * Invoked just before deleting a B record in the database.
     *
     * @param bean the B that is about to be deleted
     */
    public void beforeDelete(B bean);


    /**
     * Invoked just after deleting a B record in the database.
     *
     * @param bean the B that was just deleted
     */
    public void afterDelete(B bean);

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
        public <B> void fire(ListenerContainer<B> container,B bean) {
            if(null == container || null == bean)return;
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
            }
        }
        public <B extends BaseBean<B>> void fire(TableManager<B > manager,B bean) {
            if(null == manager || null == bean)return;
            manager.fire(this, bean);
        }
    }
    /** container for manager multiple listener */
    public static class ListenerContainer <B> implements TableListener<B> {
        private final LinkedHashSet<TableListener<B>> listeners = new LinkedHashSet<TableListener<B>>();
        public ListenerContainer() {
        }
    
        @Override
        public void beforeInsert(B bean)  {
            for(TableListener<B> listener:listeners){
                listener.beforeInsert(bean);
            }
        }
    
        @Override
        public void afterInsert(B bean)  {
            for(TableListener<B> listener:listeners){
                listener.afterInsert(bean);
            }
        }
    
        @Override
        public void beforeUpdate(B bean)  {
            for(TableListener<B> listener:listeners){
                listener.beforeUpdate(bean);
            }
        }
    
        @Override
        public void afterUpdate(B bean)  {
            for(TableListener<B> listener:listeners){
                listener.afterUpdate(bean);
            }
        }
    
        @Override
        public void beforeDelete(B bean)  {
            for(TableListener<B> listener:listeners){
                listener.beforeDelete(bean);
            }
        }
    
        @Override
        public void afterDelete(B bean)  {
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
            if(null == e)
                throw new NullPointerException();
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

