// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: manager.java.vm
// ______________________________________________________
package net.gdface.facelog.dborm;

import java.sql.Connection;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Callable;
import javax.sql.DataSource;
//guyadong change datasource pool to c3p0 2014/10/09
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import net.gdface.facelog.dborm.exception.DAOException;

/**
 * The Manager provides connections and manages transactions transparently.
 * <br>
 * It is a singleton, you get its instance with the getInstance() method.
 * All of the XxxxManager classes use the Manager to get database connections.
 * Before doing any operation, you must pass either a
 * datasource or a jdbc driver/url/username/password.
 * You may extend it and use setInstance() method to make sure your
 * implementation is used as a singleton.
 * @author sql2java
 * @version $Revision: 1.5 $
 */
public final class Manager
{
    private static Manager instance = new Manager();
    private static InheritableThreadLocal<Connection> transactionConnection = new InheritableThreadLocal<Connection>();
    
    private PrintWriter pw = new PrintWriter(System.out);
    private DataSource ds = null;
    private String jdbcDriver = null;
    private String jdbcUrl = null;
    private String jdbcUsername = null;
    private String jdbcPassword = null;
    private static String maxIdleTime;
    private static String idleConnectionTestPeriod;
    private static String maxPoolSize;
    private static String minPoolSize;
    
    /**
     * Returns the manager singleton instance.
     */
    private Manager()
    {
        String envVar="config_folder";
        String propFile="database.properties";
        String confFolder="conf";
        Properties properties = ConfigUtils.loadAllProperties(propFile, confFolder, envVar, Manager.class, false);
        loadProperties(properties);
    }

    /**
     * Returns the manager singleton instance.
     */
    public static Manager getInstance()
    {
        return instance;
    }
    
    //dispose pool
    public void disposePool(){
        try{
            DataSources.destroy(ds);
        }catch (Exception e) {
            log("dispose pool wrong ..." + e);
        }
    }
    
    /**configure with the parameters given in the properties object
     * @param properties the properties object to be used
     */
    public void loadProperties(Properties properties){
        boolean isDebug = properties.getProperty("isDebug").trim().equalsIgnoreCase("true");
        String prefix=isDebug?"debug":"work";            
        jdbcDriver = properties.getProperty(prefix+".jdbc.driver").trim();
        jdbcUrl = properties.getProperty(prefix+".jdbc.url").trim();
        jdbcUsername = properties.getProperty(prefix+".jdbc.username").trim();
        jdbcPassword = properties.getProperty(prefix+".jdbc.password").trim();
        maxPoolSize = properties.getProperty(prefix+".c3p0.maxPoolSize").trim();
        minPoolSize = properties.getProperty(prefix+".c3p0.minPoolSize").trim();
        maxIdleTime = properties.getProperty(prefix+".c3p0.maxIdleTime").trim();
        idleConnectionTestPeriod = properties.getProperty(prefix+".c3p0.idleConnectionTestPeriod").trim();
        
        log(String.format("database using %s environment parameter: ",prefix));
        log("jdbcUrl = " +jdbcUrl);
        log("jdbcUsername = " +jdbcUsername);
        log("jdbcPassword = " +jdbcPassword);
        log("maxPoolSize = " +maxPoolSize);
        log("minPoolSize = " +minPoolSize);
        log("maxIdleTime = " +maxIdleTime);
        log("idleConnectionTestPeriod = " +idleConnectionTestPeriod);
    }
    
    /**
     * use key synchronized to be sure the ds created once 
     */
    private synchronized void initDataSource(){
        try{
            if (ds == null){
                //set C3P0 properties
                ComboPooledDataSource cpds = new ComboPooledDataSource();                
                cpds.setDriverClass(jdbcDriver);
                cpds.setUser(jdbcUsername);
                cpds.setPassword(jdbcPassword);
                cpds.setJdbcUrl(jdbcUrl);
                cpds.setMaxPoolSize(Integer.parseInt(maxPoolSize));
                cpds.setMinPoolSize(Integer.parseInt(minPoolSize));
                cpds.setMaxIdleTime(Integer.parseInt(maxIdleTime));
                cpds.setIdleConnectionTestPeriod(Integer.parseInt(idleConnectionTestPeriod));
                ds = cpds; 
            }
        }catch (Exception e){
            throw new IllegalArgumentException(String.format("can't get connection by argument...driver/url/username/password[%s/%s/%s/%s]",jdbcDriver,jdbcUrl,jdbcUsername,jdbcPassword),e);
        }
    }
    
    /**
     * Gets an auto commit connection.
     * <br>
     * Normally you do not need this method that much ;-)
     *
     * @return an auto commit connection
     */
    public Connection getConnection() throws SQLException
    {
        Connection tc = transactionConnection.get();
        if (tc != null) {
            return tc;
        }
        if (ds == null){
            initDataSource();
        }
        return ds.getConnection();
    }

    /**
     * Releases the database connection.
     * <br>
     * Normally you should not need this method ;-)
     */
    public void releaseConnection(Connection c)
    {
        Connection tc = transactionConnection.get();
        if (tc != null){
            return;
        }
        try{
            if (c != null){
                c.close();
            }
        }catch (SQLException x){
            log("Could not release the connection: "+x.toString());
        }
    }

    /**
     * Initiates a database transaction.
     * <br>
     * When working within a transaction, you should invoke this method first.
     * The connection is returned just in case you need to set the isolation level.
     *
     * @return a non-auto commit connection with the default transaction isolation level
     */
    public Connection beginTransaction() throws SQLException
    {
        Connection c = this.getConnection();
        c.setAutoCommit(false);
        transactionConnection.set(c);
        return c;
    }

    /**
     * Releases connection used for the transaction and performs a commit or rollback.
     *
     * @param commit tells whether this connection should be committed
     *        true for commit(), false for rollback()
     */
    public void endTransaction(boolean commit) throws SQLException
    {
        Connection c = transactionConnection.get();
        if (c == null)
        {
            return;
        }

        try
        {
            if (commit)
            {
                c.commit();
            }
            else
            {
                c.rollback();
            }
        }
        finally
        {
            c.setAutoCommit(true);
            transactionConnection.set(null);
            releaseConnection(c);
        }
    }
    /**
     * Run {@code Callable<T>} as a transaction.<br>
     * all exceptions but {@code SQLException} threw by {@code Callable<T>} is warpped into {@code RuntimeException}<br>
     * throw {@code NullPointerException} if {@code fun} be {@code null}<br>
     * @param <T>  type of return result
     * @param fun
     * @return
     * @throws DAOException
     * @see #beginTransaction()
     * @see #endTransaction(boolean)
     */
    public <T>T runAsTransaction(Callable<T> fun) throws DAOException{
        if(null==fun) 
            throw new NullPointerException();
        try {
            Manager.getInstance().beginTransaction();
            boolean commit = false;
            try {
                T result = fun.call();
                commit = true;
                return result;
            } catch (SQLException e) {
                throw e;
            } catch (RuntimeException e) {
                throw e;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                Manager.getInstance().endTransaction(commit);
            }
        } catch (DAOException e) {
            throw e;
        }catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    /**
     * Run {@code Runnable} as a transaction.no return
     * @param fun
     * @throws DAOException
     * @see #runAsTransaction(Callable)
     */
    public void runAsTransaction(final Runnable fun) throws DAOException{
        if(null==fun) 
            throw new NullPointerException();
        runAsTransaction(new Callable<Object>(){

            @Override
            public Object call() throws Exception {
                fun.run();
                return null;
            }});
    }
    /**
     * Sets the PrintWriter where logs are printed.
     * <br>
     * You may pass 'null' to disable logging.
     *
     * @param pw the PrintWriter for log messages
     */
    public void setLogWriter(PrintWriter pw)
    {
        this.pw = pw;
    }

////////////////////////////////////////////////////
// cleaning method
////////////////////////////////////////////////////

    /**
     * Logs a message using the underlying logwriter, if not null.
     */
    public void log(String message)
    {
        if (pw != null) {
            pw.println(message);
        }
    }

    /**
     * Logs a message using the underlying logwriter, if not null.
     */
    public void log(Throwable cause)
    {    
        if(pw != null && null != cause){
            cause.printStackTrace(pw);
        }
    }
    
    /**
     * Logs a message using the underlying logwriter, if not null.
     */
    public void log(String message,Throwable cause)
    {    
        log(message);
        log(cause);
    }
    
    /**
     * Closes the passed Statement.
     */
    public void close(Statement s)
    {
        try
        {
            if (s != null) {
                s.close();
            }
        }
        catch (SQLException x)
        {
            log("Could not close statement!: " + x.toString());
        }
    }

    /**
     * Closes the passed ResultSet.
     */
    public void close(ResultSet rs)
    {
        try
        {
            if (rs != null) {
                rs.close();
            }
        }
        catch (SQLException x)
        {
            log("Could not close result set!: " + x.toString());
        }
    }

    /**
     * Closes the passed Statement and ResultSet.
     */
    public void close(Statement s, ResultSet rs)
    {
        close(rs);
        close(s);
    }

////////////////////////////////////////////////////
// Helper methods for fetching numbers using IDs or names
////////////////////////////////////////////////////

    /**
     * Retrieves an int value from the passed result set as an Integer object.
     */
    public static Integer getInteger(ResultSet rs, int pos) throws SQLException
    {
        int i = rs.getInt(pos);
        return rs.wasNull() ? (Integer)null : new Integer(i);
    }

    /**
     * Retrieves an int value from the passed result set as an Integer object.
     */
    public static Integer getInteger(ResultSet rs, String column) throws SQLException
    {
        int i = rs.getInt(column);
        return rs.wasNull() ? (Integer)null : new Integer(i);
    }

    /**
     * Set an Integer object to the passed prepared statement as an int or as null.
     */
    public static void  setInteger(PreparedStatement ps, int pos, Integer i) throws SQLException
    {
        if (i==null)
        {
            ps.setNull(pos, Types.INTEGER);
        }
        else
        {
            ps.setInt(pos, i.intValue());
        }
    }

    /**
     * Retrieves a float value from the passed result set as a Float object.
     */
    public static Float getFloat(ResultSet rs, int pos) throws SQLException
    {
        float f = rs.getFloat(pos);
        return rs.wasNull() ? (Float)null : new Float(f);
    }

    /**
     * Retrieves a float value from the passed result set as a Float object.
     */
    public static Float getFloat(ResultSet rs, String column) throws SQLException
    {
        float f = rs.getFloat(column);
        return rs.wasNull() ? (Float)null : new Float(f);
    }

    /**
     * Set a Float object to the passed prepared statement as a float or as null.
     */
    public static void  setFloat(PreparedStatement ps, int pos, Float f) throws SQLException
    {
        if (f==null)
        {
            ps.setNull(pos, Types.FLOAT);
        }
        else
        {
            ps.setFloat(pos, f.floatValue());
        }
    }

    /**
     * Retrieves a double value from the passed result set as a Double object.
     */
    public static Double getDouble(ResultSet rs, int pos) throws SQLException
    {
        double d = rs.getDouble(pos);
        return rs.wasNull() ? (Double)null : new Double(d);
    }

    /**
     * Retrieves a double value from the passed result set as a Double object.
     */
    public static Double getDouble(ResultSet rs, String column) throws SQLException
    {
        double d = rs.getDouble(column);
        return rs.wasNull() ? (Double)null : new Double(d);
    }

    /**
     * Set a Double object to the passed prepared statement as a double or as null.
     */
    public static void  setDouble(PreparedStatement ps, int pos, Double d) throws SQLException
    {
        if (d==null)
        {
            ps.setNull(pos, Types.DOUBLE);
        }
        else
        {
            ps.setDouble(pos, d.doubleValue());
        }
    }

    /**
     * Retrieves a long value from the passed result set as a Long object.
     */
    public static Long getLong(ResultSet rs, int pos) throws SQLException
    {
        long l = rs.getLong(pos);
        return rs.wasNull() ? (Long)null : new Long(l);
    }

    /**
     * Retrieves a long value from the passed result set as a Long object.
     */
    public static Long getLong(ResultSet rs, String column) throws SQLException
    {
        long l = rs.getLong(column);
        return rs.wasNull() ? (Long)null : new Long(l);
    }

    /**
     * Set a Long object to the passed prepared statement as a long or as null.
     */
    public static void  setLong(PreparedStatement ps, int pos, Long l) throws SQLException
    {
        if (l==null)
        {
            ps.setNull(pos, Types.BIGINT);
        }
        else
        {
            ps.setLong(pos, l.longValue());
        }
    }

    /**
     * Retrieves a java.sql.Blob object from the passed result set as a byte array.
     */
    public static java.nio.ByteBuffer getBlob(ResultSet rs, int pos) throws SQLException
    {
        java.sql.Blob blob = rs.getBlob(pos);
        return rs.wasNull()?(java.nio.ByteBuffer)null:java.nio.ByteBuffer.wrap(blob.getBytes(1L,(int)blob.length()));
    }

    /**
     * Retrieves a java.sql.Blob object from the passed result set as a byte array.
     */
    public static java.nio.ByteBuffer getBlob(ResultSet rs, String column) throws SQLException
    {
        java.sql.Blob blob = rs.getBlob(column);
        return rs.wasNull()?(java.nio.ByteBuffer)null:java.nio.ByteBuffer.wrap(blob.getBytes(1L,(int)blob.length()));
    }
    /**
     * return all bytes in buffer (position~limit),no change status of buffer
     * @param buffer
     * @return
     */
    private static final byte[] getBytesInBuffer(java.nio.ByteBuffer buffer){
        int pos = buffer.position();
        try{
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            return bytes;
        }finally{
            buffer.position(pos);
        }
    }
    /**
     * Set a byte array to the passed prepared statement as a java.sql.Blob or as null.
     */
    public static void  setBlob(PreparedStatement ps, int pos, java.nio.ByteBuffer blob) throws SQLException
    {
        if (blob == null){
            ps.setNull(pos, Types.BLOB);
        }else{
            ps.setBlob(pos, new java.io.ByteArrayInputStream(getBytesInBuffer(blob)));
        }
    }

    /**
     * Retrieves a java.sql.Clob object from the passed result set as a String.
     */
    public static String getClob(ResultSet rs, int pos) throws SQLException
    {
        java.sql.Clob clob = rs.getClob(pos);
        return rs.wasNull()?(String)null:clob.getSubString(1L,(int)clob.length());
    }

    /**
     * Retrieves a java.sql.Clob object from the passed result set as a String.
     */
    public static String getClob(ResultSet rs, String column) throws SQLException
    {
        java.sql.Clob clob = rs.getClob(column);
        return rs.wasNull()?(String)null:clob.getSubString(1L,(int)clob.length());
    }

    /**
     * Set a String to the passed prepared statement as a java.sql.Clob or as null.
     */
    public static void  setClob(PreparedStatement ps, int pos, String clob) throws SQLException
    {
        if (clob==null){
            ps.setNull(pos, Types.CLOB);
        }else{
            ps.setClob(pos, new java.io.StringReader(clob));
        }
    }

    /**
     * Retrieves a binary object from the passed result set as a byte array.
     */
    public static java.nio.ByteBuffer getBytes(ResultSet rs, int pos) throws SQLException
    {
        return rs.wasNull()?(java.nio.ByteBuffer)null:java.nio.ByteBuffer.wrap(rs.getBytes(pos));
    }

    /**
     * Retrieves a byte array from the passed result set as java.nio.ByteBuffer.
     */
    public static java.nio.ByteBuffer getBytes(ResultSet rs, String column) throws SQLException
    {
        return rs.wasNull()?(java.nio.ByteBuffer)null:java.nio.ByteBuffer.wrap(rs.getBytes(column));
    }

    /**
     * Set a byte array to the passed prepared statement as a java.nio.ByteBuffer or as null.
     */
    public static void  setBytes(int sqlType,PreparedStatement ps, int pos, java.nio.ByteBuffer bytes) throws SQLException
    {
        if (null == bytes){
            ps.setNull(pos, sqlType);
        }else{
            ps.setBytes(pos, getBytesInBuffer(bytes));
        }
    }
    
    /**
     * Retrieves a boolean value from the passed result set as a Boolean object.
     */
    public static Boolean getBoolean(ResultSet rs, int pos) throws SQLException
    {
        boolean b = rs.getBoolean(pos);
        return rs.wasNull() ? (Boolean)null : new Boolean(b);
    }

    /**
     * Retrieves a boolean value from the passed result set as a Boolean object.
     */
    public static Boolean getBoolean(ResultSet rs, String column) throws SQLException
    {
        boolean b = rs.getBoolean(column);
        return rs.wasNull() ? (Boolean)null : new Boolean(b);
    }

    /**
     * Set a Boolean object to the passed prepared statement as a boolean or as null.
     */
    public static void  setBoolean(PreparedStatement ps, int pos, Boolean b) throws SQLException
    {
        if (b==null)
        {
            ps.setNull(pos, Types.BOOLEAN);
        }
        else
        {
            ps.setBoolean(pos, b.booleanValue());
        }
    }

    /**
     * Retrieves a date value from the passed result set as a Calendar object.
     */
    public static Calendar getCalendar(ResultSet rs, int pos) throws SQLException
    {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(rs.getDate(pos));
            if (rs.wasNull()) {
                setValueRepresentingNull(calendar);
            }
        } catch (SQLException se) {
            setValueRepresentingNull(calendar);
        }
        return calendar;
    }

    /**
     * Retrieves a date value from the passed result set as a Calendar object.
     */
    public static Calendar getCalendar(ResultSet rs, String column) throws SQLException
    {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(rs.getDate(column));
            if (rs.wasNull()) {
                setValueRepresentingNull(calendar);
            }
        } catch (SQLException se) {
            setValueRepresentingNull(calendar);
        }
        return calendar;
    }

    /**
     * Set a Calendar object to the passed prepared statement as a date or as null.
     */
    public static void  setCalendar(PreparedStatement ps, int pos, Calendar calendar) throws SQLException
    {
        if ((calendar == null) || (isValueRepresentingNull(calendar)))
        {
            ps.setNull(pos, Types.TIMESTAMP);
        }
        else
        {
            ps.setDate(pos, new java.sql.Date(calendar.getTimeInMillis()));
        }
    }

    private static void setValueRepresentingNull(Calendar calendar) {
        calendar.set(Calendar.YEAR, DATE_REPRESENTING_NULL_YEAR);
        calendar.set(Calendar.MONTH, DATE_REPRESENTING_NULL_MONTH);
        calendar.set(Calendar.DATE, DATE_REPRESENTING_NULL_DATE);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.ZONE_OFFSET, 
            (calendar.getTimeZone().getRawOffset() + calendar.getTimeZone().getDSTSavings()) / 60000);
    } 

    public static boolean isValueRepresentingNull(Calendar calendar) {
        return (calendar.get(Calendar.YEAR) == DATE_REPRESENTING_NULL_YEAR) &&
                (calendar.get(Calendar.MONTH) == DATE_REPRESENTING_NULL_MONTH) &&
                (calendar.get(Calendar.DATE) == DATE_REPRESENTING_NULL_DATE);
    } 
 
    private static final int DATE_REPRESENTING_NULL_YEAR = 1899; 
    private static final int DATE_REPRESENTING_NULL_MONTH = Calendar.JANUARY; 
    private static final int DATE_REPRESENTING_NULL_DATE = 1; 

////////////////////////////////////////////////////
// Date helper methods
////////////////////////////////////////////////////

    /**
     * pattern for received date processing.
     */
    private static final String[] PATTERNS = new String[]
    {
        "EEE, dd MMM yyyy HH:mm:ss '-'S '('z')'",
        "EEE, dd MMM yyyy HH:mm:ss '+'S '('z')'",
        "EEE, dd MMM yyyy HH:mm:ss '-'S",
        "EEE, dd MMM yyyy HH:mm:ss '+'S",
        "EEE, dd MMM yyyy HH:mm:ss z",
        "EEE, dd MMM yyyy HH:mm:ss Z",
        "EEE, dd MMM yyyy HH:mm:ss",
        "EEE, d MMM yyyy HH:mm:ss '-'S '('z')'",
        "EEE, d MMM yyyy HH:mm:ss '+'S '('z')'",
        "EEE, d MMM yyyy HH:mm:ss '-'S",
        "EEE, d MMM yyyy HH:mm:ss '+'S",
        "EEE, d MMM yyyy HH:mm:ss z",
        "EEE, d MMM yyyy HH:mm:ss Z",
        "EEE, d MMM yyyy HH:mm:ss",

        "EEE, dd MMM yy HH:mm:ss '-'S '('z')'",
        "EEE, dd MMM yy HH:mm:ss '+'S '('z')'",
        "EEE, dd MMM yy HH:mm:ss '-'S",
        "EEE, dd MMM yy HH:mm:ss '+'S",
        "EEE, dd MMM yy HH:mm:ss z",
        "EEE, dd MMM yy HH:mm:ss Z",
        "EEE, dd MMM yy HH:mm:ss",
        "EEE, d MMM yy HH:mm:ss '-'S '('z')'",
        "EEE, d MMM yy HH:mm:ss '+'S '('z')'",
        "EEE, d MMM yy HH:mm:ss '-'S",
        "EEE, d MMM yy HH:mm:ss '+'S",
        "EEE, d MMM yy HH:mm:ss z",
        "EEE, d MMM yy HH:mm:ss Z",
        "EEE, d MMM yy HH:mm:ss",

        "dd MMM yyyy HH:mm:ss '-'S",
        "dd MMM yyyy HH:mm:ss '+'S",
        "dd MMM yyyy HH:mm:ss '-'S '('z')'",
        "dd MMM yyyy HH:mm:ss '+'S '('z')'",
        "dd MMM yyyy HH:mm:ss z",
        "dd MMM yyyy HH:mm:ss Z",
        "dd MMM yyyy HH:mm:ss",

        "dd MMM yyy HH:mm:ss '-'S",
        "dd MMM yyy HH:mm:ss '+'S",
        "dd MMM yyy HH:mm:ss '-'S '('z')'",
        "dd MMM yyy HH:mm:ss '+'S '('z')'",
        "dd MMM yyy HH:mm:ss z",
        "dd MMM yyy HH:mm:ss Z",
        "dd MMM yyy HH:mm:ss",

        "yyyy.MM.dd HH:mm:ss z",
        "yyyy.MM.dd HH:mm:ss Z",
        "yyyy.MM.d HH:mm:ss z",
        "yyyy.MM.d HH:mm:ss Z",
        "yyyy.MM.dd HH:mm:ss",
        "yyyy.MM.d HH:mm:ss",

        "yy.MM.dd HH:mm:ss z",
        "yy.MM.dd HH:mm:ss Z",
        "yy.MM.d HH:mm:ss z",
        "yy.MM.d HH:mm:ss Z",
        "yy.MM.dd HH:mm:ss",
        "yy.MM.d HH:mm:ss",

        "yyyy MM dd HH:mm:ss",
        "yyyy MM d HH:mm:ss",
        "yyyy MM dd HH:mm:ss z",
        "yyyy MM dd HH:mm:ss Z",
        "yyyy MM d HH:mm:ss z",
        "yyyy MM d HH:mm:ss Z",

        "yy MM dd HH:mm:ss",
        "yy MM d HH:mm:ss",
        "yy MM dd HH:mm:ss z",
        "yy MM dd HH:mm:ss Z",
        "yy MM d HH:mm:ss z",
        "yy MM d HH:mm:ss Z",

        "yyyy-MM-dd HH:mm:ss z",
        "yyyy-MM-dd HH:mm:ss Z",
        "yyyy-MM-d HH:mm:ss z",
        "yyyy-MM-d HH:mm:ss Z",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-d HH:mm:ss",

        "yy-MM-dd HH:mm:ss z",
        "yy-MM-dd HH:mm:ss Z",
        "yy-MM-d HH:mm:ss z",
        "yy-MM-d HH:mm:ss Z",
        "yy-MM-dd HH:mm:ss",
        "yy-MM-d HH:mm:ss",

        "dd MMM yyyy",
        "d MMM yyyy",

        "dd.MMM.yyyy",
        "d.MMM.yyyy",

        "dd-MMM-yyyy",
        "d-MMM-yyyy",

        "dd MM yyyy",
        "d MM yyyy",

        "dd.MM.yyyy",
        "d.MM.yyyy",

        "dd-MM-yyyy",
        "d-MM-yyyy",

        "yyyy MM dd",
        "yyyy MM d",

        "yyyy.MM.dd",
        "yyyy.MM.d",

        "yyyy-MM-dd",
        "yyyy-MM-d",

        "dd MMM yy",
        "d MMM yy",

        "dd.MMM.yy",
        "d.MMM.yy",

        "dd-MMM-yy",
        "d-MMM-yy",

        "dd MM yy",
        "d MM yy",

        "dd.MM.yy",
        "d.MM.yy",

        "dd-MM-yy",
        "d-MM-yy",

        "yy MMM dd",
        "yy MMM d",

        "yy.MMM.dd",
        "yy.MMM.d",

        "yy-MMM-dd",
        "yy-MMM-d",

        "yy MMM dd",
        "yy MMM d",

        "yy.MMM.dd",
        "yy.MMM.d",

        "yy-MMM-dd",
        "yy-MMM-d",

        "EEE dd, MMM yyyy", // ex: Wed 19, Feb 2003

        "EEE dd, MMM yy" // ex: Wed 19, Feb 03
    };


    /**
     * get a date from a date string representation in one of the registered formats
     * @param strDate the date as string. If (null or empty) or correct pattern was not found
     * @return Date object
     */
    public static java.util.Date getDateFromString(String strDate)
    {
        java.util.Date dReceivedDate = Calendar.getInstance().getTime();
        if (strDate == null) {
            return dReceivedDate;
        } else {
            strDate = strDate.trim();
        }

        SimpleDateFormat pSimpleDateFormat = new SimpleDateFormat("");
        if (!"".equals(strDate))
        {
            for (int i=0; i<PATTERNS.length; i++)
            {
                try
                {
                    pSimpleDateFormat.applyPattern(PATTERNS[i]);
                    dReceivedDate = pSimpleDateFormat.parse(strDate);
                    if (dReceivedDate == null)
                    {
                        continue;
                    }
                    return dReceivedDate;
                }
                catch (ParseException pe)
                {
                    ; // ignore this format try the next one
                }
            }
        }
        return dReceivedDate;
    }

    /**
     * Verify that the string represantes the date with one of the registered formats
     * @param strDate the date as string.
     * @return boolean "true" if the string represantes the date in one of the registed formats.
     */
    public static boolean isDate(String strDate)
    {
        if (strDate == null) {
            return false;
        } else {
            strDate = strDate.trim();
        }

        SimpleDateFormat pSimpleDateFormat = new SimpleDateFormat("");
        if (!"".equals(strDate))
        {
            for (String pattern : PATTERNS)
            {
                try
                {
                    pSimpleDateFormat.applyPattern(pattern);
                    java.util.Date dReceivedDate = pSimpleDateFormat.parse(strDate);
                    if (dReceivedDate == null) {
                        continue;
                    }
                    return true;
                }
                catch (ParseException pe)
                {
                    ; // ignore as it is reported below
                }
            }
        }
        return false;
    }
    
    public static String buildProcedureCall(String packageName, String procedureName, int paramCount) {
        return buildProcedureCall(packageName + "." + procedureName, paramCount);
    }
    
    public static String buildProcedureCall(String procedureName, int paramCount) {
        StringBuilder sb = new StringBuilder("{call ").append(procedureName).append("(");
        for (int n = 1; n <= paramCount; n++) {
            sb.append("?,");
        }
        if (paramCount > 0) {
            sb.setLength(sb.length()-1);
        }
        return sb.append(")}").toString();
    }
    /**
     * 用指定的SQL语句查询数据库
     * 
     * @param sql
     *            sql语句字符串
     * @param argList
     *            sql语句中？代表的参数数组，顺序必须与sql语句中?出现的位置顺序保持一致
     * @return 包含查询结果的对象数组
     * @throws SQLException 
     */
    public boolean runSql(String sql, Object[] argList) throws SQLException {
        PreparedStatement ps = null;
        Connection c = null;
        if (sql == null)
            return false;
        // log("sql string:\n" + sql + "\n");
        try {
            c = getConnection();
            ps = c.prepareStatement(sql);
            fillPrepareStatement(ps, argList);
            return ps.execute();
        } finally {
            if(null != ps)
                close(ps);
            if(null != c)
                releaseConnection(c);
        }
    }
    /**
     * 填充PreparedStatement中的参数对象
     * 
     * @param ps
     * @param argList
     * @return 出错则返回null;
     * @throws SQLException
     */
    private void fillPrepareStatement(PreparedStatement ps, Object[] argList) throws SQLException {
        if (!(argList == null || ps == null)) {
            for (int i = 0; i < argList.length; i++) {
                if (argList[i].getClass().equals(byte[].class)) {
                    ps.setBytes(i + 1, (byte[]) argList[i]);
                } else
                    ps.setObject(i + 1, argList[i]);
            }
        }
    }
}
