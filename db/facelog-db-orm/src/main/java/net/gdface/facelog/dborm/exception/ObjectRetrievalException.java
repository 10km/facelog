// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: object.retrieval.exception.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm.exception;

/**
 * @author sql2java
 */
public class ObjectRetrievalException extends DataRetrievalException
{
    private static final long serialVersionUID = -3197505872331833160L;

	/**
     * contructor
     */
    public ObjectRetrievalException()
    {
        super();
    }

    /**
     * contructor
     */
    public ObjectRetrievalException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public ObjectRetrievalException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public ObjectRetrievalException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
