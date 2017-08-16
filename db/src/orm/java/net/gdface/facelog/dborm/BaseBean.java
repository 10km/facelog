// ______________________________________________________
// Generated by sql2java - http://sql2java.sourceforge.net/
// jdbc driver used at code generation time: $db.Driver
//
// Please help us improve this tool by reporting:
// - problems and suggestions to
//   http://sourceforge.net/tracker/?group_id=54687
// - feedbacks and ideas on
//   http://sourceforge.net/forum/forum.php?forum_id=182208
// ______________________________________________________

package net.gdface.facelog.dborm;

import java.util.Map;

/**
 * @author guyadong
 */
public interface BaseBean<B> {
    public abstract void copy(B bean);
    public abstract void copy(B bean, String[] fieldList);
    public abstract void copyIfNotNull(B bean);
    public abstract<T>T getObject(String column);
    public abstract String getValue(String column);
    public abstract boolean isNew();
    public abstract void isNew(boolean isNew);
    public abstract Map<String,String> readDictionnary();
    public abstract Map<String,String> readPkDictionnary();
    public abstract<T>void setObject(String column,T object);
    public abstract <T>T toFullBean();
}
