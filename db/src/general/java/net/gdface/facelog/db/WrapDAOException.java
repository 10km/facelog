// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// ______________________________________________________

package net.gdface.facelog.db;

/**
 * wrap all DAOException threw bye native code
 *
 * @author guyadong
 */
public class WrapDAOException extends RuntimeException
{
    private static final long serialVersionUID = 2448402307057993837L;

    /**
     * contructor
     */
    public WrapDAOException(Throwable cause)
    {
        super(cause);
    }
}
