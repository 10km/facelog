// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.dborm;

import java.util.Map;

/**
 * @author sql2java
 * @version $Revision: 1.3 $
 */
public interface GeneratedBean {
    public boolean isNew();
    public boolean isModified();
    public void resetIsModified();
    public String getValue(String column);
    public Map<String,String> getDictionnary();
    public Map<String,String> getPkDictionnary();
}