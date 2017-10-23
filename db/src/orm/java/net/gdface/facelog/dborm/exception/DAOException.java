// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: dao.exception.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.exception;

import java.sql.SQLException;

/**
 * @author sql2java
 */
public class DAOException extends SQLException
{
    private static final long serialVersionUID = 5165438223391151142L;

    /**
     * contructor
     */
    public DAOException()
    {
        super();
    }

    /**
     * contructor
     */
    public DAOException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public DAOException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public DAOException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
