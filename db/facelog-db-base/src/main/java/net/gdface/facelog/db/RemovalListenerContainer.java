// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: removal.listener.container.java.vm
// ______________________________________________________
package net.gdface.facelog.db;

import java.util.LinkedHashSet;

import com.google.common.base.Preconditions;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * {@link RemovalListener}管理容器
 * @author guyadong
 *
 * @param <K>
 * @param <V>
 */
public class RemovalListenerContainer<K,V> implements RemovalListener<K, V> {
    LinkedHashSet<RemovalListener<K,V>> listeners = new LinkedHashSet<RemovalListener<K,V>>(); 
    public RemovalListenerContainer() {
    }

    /**
     * @param e
     * @return
     * @see java.util.HashSet#add(java.lang.Object)
     */
    public boolean add(RemovalListener<K, V> e) {
        return listeners.add(Preconditions.checkNotNull(e));
    }

    /**
     * @param o
     * @return
     * @see java.util.HashSet#remove(java.lang.Object)
     */
    public boolean remove(RemovalListener<K, V> o) {
        return listeners.remove(o);
    }

    /**
     * 
     * @see java.util.HashSet#clear()
     */
    public void clear() {
        listeners.clear();
    }

    @Override
    public void onRemoval(RemovalNotification<K, V> notification) {
        for(RemovalListener<K, V> listener:listeners){
            try{
                listener.onRemoval(notification);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}