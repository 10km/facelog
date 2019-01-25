package net.gdface.facelog.thrift;
import net.gdface.thrift.exception.client.BaseServiceRuntimeException;
import static com.google.common.base.Preconditions.*;

/**
 * 服务端抛出的 runtime 异常
 * @author guyadong
 *
 */
public class ServiceRuntimeException extends BaseServiceRuntimeException{
    private static final long serialVersionUID = 1L;

    ServiceRuntimeException(net.gdface.facelog.client.thrift.ServiceRuntimeException cause) {
        super(checkNotNull(cause,"cause is null"));
        type = cause.type;
        message = cause.message;
        causeClass = cause.causeClass;
        causeFields = cause.causeFields;
        serviceStackTraceMessage = cause.serviceStackTraceMessage;
    }        
}