// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: base.service.exception.java.vm
// ______________________________________________________
package net.gdface.facelog.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

/**
 * service exception abstract class<br>
 * @author guyadong
 *
 */
@ThriftStruct
public abstract class BaseServiceException extends Exception{
    private static final long serialVersionUID = 1L;
    private String message;
    private String causeClass;
    /** Lazy Initialization field */
    private volatile String serviceStackTraceMessage;
    private String causeFields;

    public BaseServiceException() {
        init();
    }
    public BaseServiceException(String message) {
        super(message);
        this.message = message;
        init();
    }
    public BaseServiceException(String message, Throwable cause) {
        super(message,stripRuntimeShell(cause));
        this.message = message;
        init();
    }
    public BaseServiceException(Throwable cause) {
        super(stripRuntimeShell(cause));
        init();
    }
    private void init(){
        this.message = getMessage();
        this.causeClass = null == getCause()? null : getCause().getClass().getName();
        this.causeFields = null == getCause()? null : getCause().toString();
        if(getCause() instanceof BaseServiceException){
            this.causeFields = ((BaseServiceException)getCause()).jsonOfDeclaredFields();    
        }
    }
    /** return a JSON string of declared fields,subclass override it */
    protected String jsonOfDeclaredFields(){
        return "";
    }
    /**
     * return cause wrapped by {@link RuntimeException}<br>
     * @param e
     * @return
     */
    private static final Throwable stripRuntimeShell(Throwable e){
        if(null != e && null !=e.getCause() && e.getClass() == RuntimeException.class){
            return stripRuntimeShell(e.getCause());
        }
        return e;
    }
    /**
     * save error message to {@link #serviceStackTraceMessage} by calling {@link #printStackTrace(PrintWriter)} 
     * @param cause
     * @see #printStackTrace(PrintWriter)
     */
    private void fillStackTraceMessage(Throwable cause) {
        if (null != cause) {
            StringWriter write = new StringWriter(256);
            PrintWriter pw = new PrintWriter(write);
            cause.printStackTrace(pw);
            serviceStackTraceMessage = write.toString();
        }
    }

    /** return error message from service */
    @Override
    @ThriftField(1)
    public String getMessage() {
        return message;
    }
    @ThriftField
    public void setMessage(String message) {
        this.message = message;
    }
    /** return cause exception class name */
    @ThriftField(2)
    public String getCauseClass() {
        return causeClass;
    }
    @ThriftField
    public void setCauseClass(String causeClass) {
        this.causeClass = causeClass;
    }
    /** return stack trace message from service */
    @ThriftField(3)
    public String getServiceStackTraceMessage() {
        return serviceStackTraceMessage;
    }
    @ThriftField
    public void setServiceStackTraceMessage(String serviceStackTraceMessage) {
        // Double-checked locking
        if(null == serviceStackTraceMessage){
            synchronized(this){
                if(null == serviceStackTraceMessage){
                    fillStackTraceMessage(null == getCause()? this : getCause());
                }
            }
        }
        this.serviceStackTraceMessage = serviceStackTraceMessage;
    }
    /** 
     * return JSON string of declared field values if cause is subclass of this class 
     * and override {@code jsonOfDeclaredFields} method, otherwise return empty string
     * @see {@link #jsonOfDeclaredFields()}
     */
    @ThriftField(4)
    public String getCauseFields() {
        return causeFields;
    }
    @ThriftField
    public void setCauseFields(String causeFields) {
        this.causeFields = causeFields;
    }
}