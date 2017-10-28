// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: optimistic.locking.exception.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.exception;

/**
 * @author sql2java
 */
public class OptimisticLockingException extends DataAccessException
{
    private static final long serialVersionUID = -1348627690415864134L;

	/**
     * contructor
     */
    public OptimisticLockingException()
    {
        super();
    }

    /**
     * contructor
     */
    public OptimisticLockingException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public OptimisticLockingException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public OptimisticLockingException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
