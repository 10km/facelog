// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm;

/**
 * @author guyadong
 */
public interface FullBean <B> extends BaseBean<B>{
    public abstract boolean isModified();
    public abstract void resetIsModified();
    public abstract boolean isInitialized(String column);
    public abstract boolean isModified(String column);

}
