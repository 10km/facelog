package com.facebook.swift.service;

import com.facebook.nifty.core.RequestContext;
import com.facebook.nifty.core.RequestContexts;
import com.facebook.swift.codec.ThriftCodec;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.codec.internal.TProtocolReader;
import com.facebook.swift.codec.internal.TProtocolWriter;
import com.facebook.swift.codec.metadata.ThriftFieldMetadata;
import com.facebook.swift.codec.metadata.ThriftType;
import com.facebook.swift.service.ContextChain;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.facebook.swift.service.metadata.ThriftMethodMetadata;
import com.google.common.base.Defaults;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.weakref.jmx.Managed;
import org.weakref.jmx.com.google.common.base.Preconditions;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import static org.apache.thrift.TApplicationException.INTERNAL_ERROR;

/**
 * 定制{@link ThriftMethodProcessor},收到的primitive封装类型(Boolean,Integer...)如果是{@code null}不会被转成default 0.
 * 通过调用{@link #setUseDefaultValueIfPrimitiveWrap(boolean)}方法设置为{@value true}可以禁用该特性
 * @author guyadong
 *
 */
@ThreadSafe
public class ThriftMethodProcessorCustom extends ThriftMethodProcessor
{
    private final String name;
    private final String serviceName;
    private final String qualifiedName;
    private final Object service;
    private final Method method;
    private final String resultStructName;
    private final boolean oneway;
    private final ImmutableList<ThriftFieldMetadata> parameters;
    private final Map<Short, ThriftCodec<?>> parameterCodecs;
    private final Map<Short, Short> thriftParameterIdToJavaArgumentListPositionMap;
    private final ThriftCodec<Object> successCodec;
    private final Map<Class<?>, ExceptionProcessor> exceptionCodecs;
    /** 对于primitive wrap类型是否将{@link null}转为default value */
    private static Boolean useDefaultValueIfPrimitiveWrap= false;
    @SuppressWarnings("unchecked")
	public ThriftMethodProcessorCustom(
            Object service,
            String serviceName,
            ThriftMethodMetadata methodMetadata,
            ThriftCodecManager codecManager
    )
    {
    	super(service, serviceName, methodMetadata, codecManager);
        this.service = service;
        this.serviceName = serviceName;

        name = methodMetadata.getName();
        qualifiedName = serviceName + "." + name;
        resultStructName = name + "_result";

        method = methodMetadata.getMethod();
        oneway = methodMetadata.getOneway();

        parameters = ImmutableList.copyOf(methodMetadata.getParameters());

        ImmutableMap.Builder<Short, ThriftCodec<?>> builder = ImmutableMap.builder();
        for (ThriftFieldMetadata fieldMetadata : methodMetadata.getParameters()) {
            builder.put(fieldMetadata.getId(), codecManager.getCodec(fieldMetadata.getThriftType()));
        }
        parameterCodecs = builder.build();

        // Build a mapping from thrift parameter ID to a position in the formal argument list
        ImmutableMap.Builder<Short, Short> parameterOrderingBuilder = ImmutableMap.builder();
        short javaArgumentPosition = 0;
        for (ThriftFieldMetadata fieldMetadata : methodMetadata.getParameters()) {
            parameterOrderingBuilder.put(fieldMetadata.getId(), javaArgumentPosition++);
        }
        thriftParameterIdToJavaArgumentListPositionMap = parameterOrderingBuilder.build();

        ImmutableMap.Builder<Class<?>, ExceptionProcessor> exceptions = ImmutableMap.builder();
        for (Map.Entry<Short, ThriftType> entry : methodMetadata.getExceptions().entrySet()) {
            Class<?> type = TypeToken.of(entry.getValue().getJavaType()).getRawType();
            ExceptionProcessor processor = new ExceptionProcessor(entry.getKey(), codecManager.getCodec(entry.getValue()));
            exceptions.put(type, processor);
        }
        exceptionCodecs = exceptions.build();

        successCodec = (ThriftCodec<Object>) codecManager.getCodec(methodMetadata.getReturnType());
    }

    @Managed
    @Override
    public String getName()
    {
        return name;
    }
    @Override
    public Class<?> getServiceClass() {
        return service.getClass();
    }
    @Override
    public String getServiceName()
    {
        return serviceName;
    }
    @Override
    public String getQualifiedName()
    {
        return qualifiedName;
    }
    @Override
    public ListenableFuture<Boolean> process(TProtocol in, final TProtocol out, final int sequenceId, final ContextChain contextChain)
            throws Exception
    {
        // read args
        contextChain.preRead();
        Object[] args = readArguments(in);
        contextChain.postRead(args);
        final RequestContext requestContext = RequestContexts.getCurrentContext();

        in.readMessageEnd();

        // invoke method
        final ListenableFuture<?> invokeFuture = invokeMethod(args);
        final SettableFuture<Boolean> resultFuture = SettableFuture.create();

        Futures.addCallback(invokeFuture, new FutureCallback<Object>()
        {
            @Override
            public void onSuccess(Object result)
            {
                if (oneway) {
                    resultFuture.set(true);
                }
                else {
                    RequestContext oldRequestContext = RequestContexts.getCurrentContext();
                    RequestContexts.setCurrentContext(requestContext);

                    // write success reply
                    try {
                        contextChain.preWrite(result);

                        writeResponse(out,
                                      sequenceId,
                                      TMessageType.REPLY,
                                      "success",
                                      (short) 0,
                                      successCodec,
                                      result);

                        contextChain.postWrite(result);

                        resultFuture.set(true);
                    }
                    catch (Exception e) {
                        // An exception occurred trying to serialize a return value onto the output protocol
                        resultFuture.setException(e);
                    }
                    finally {
                        RequestContexts.setCurrentContext(oldRequestContext);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                RequestContext oldRequestContext = RequestContexts.getCurrentContext();
                RequestContexts.setCurrentContext(requestContext);

                try {
                    contextChain.preWriteException(t);
                    if (!oneway) {
                        ExceptionProcessor exceptionCodec = exceptionCodecs.get(t.getClass());
                        if (exceptionCodec != null) {
                            // write expected exception response
                            writeResponse(
                                    out,
                                    sequenceId,
                                    TMessageType.REPLY,
                                    "exception",
                                    exceptionCodec.getId(),
                                    exceptionCodec.getCodec(),
                                    t);
                            contextChain.postWriteException(t);
                        } else {
                            // unexpected exception
                            TApplicationException applicationException =
                                    ThriftServiceProcessor.writeApplicationException(
                                            out,
                                            method.getName(),
                                            sequenceId,
                                            INTERNAL_ERROR,
                                            "Internal error processing " + method.getName(),
                                            t);

                            contextChain.postWriteException(applicationException);
                        }
                    }

                    resultFuture.set(true);
                }
                catch (Exception e) {
                    // An exception occurred trying to serialize an exception onto the output protocol
                    resultFuture.setException(e);
                }
                finally {
                    RequestContexts.setCurrentContext(oldRequestContext);
                }
            }
        });

        return resultFuture;
    }

    private ListenableFuture<?> invokeMethod(Object[] args)
    {
        try {
            Object response = method.invoke(service, args);
            if (response instanceof ListenableFuture) {
                return (ListenableFuture<?>) response;
            }
            return Futures.immediateFuture(response);
        }
        catch (IllegalAccessException | IllegalArgumentException e) {
            // These really should never happen, since the method metadata should have prevented it
            return Futures.immediateFailedFuture(e);
        }
        catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                return Futures.immediateFailedFuture(cause);
            }

            return Futures.immediateFailedFuture(e);
        }
    }

    private Object[] readArguments(TProtocol in)
            throws Exception
    {
        try {
            int numArgs = method.getParameterTypes().length;
            Object[] args = new Object[numArgs];
            TProtocolReader reader = new TProtocolReader(in);

            // Map incoming arguments from the ID passed in on the wire to the position in the
            // java argument list we expect to see a parameter with that ID.
            reader.readStructBegin();
            while (reader.nextField()) {
                short fieldId = reader.getFieldId();

                ThriftCodec<?> codec = parameterCodecs.get(fieldId);
                if (codec == null) {
                    // unknown field
                    reader.skipFieldData();
                }
                else {
                    // Map the incoming arguments to an array of arguments ordered as the java
                    // code for the handler method expects to see them
                    args[thriftParameterIdToJavaArgumentListPositionMap.get(fieldId)] = reader.readField(codec);
                }
            }
            reader.readStructEnd();

            // Walk through our list of expected parameters and if no incoming parameters were
            // mapped to a particular expected parameter, fill the expected parameter slow with
            // the default for the parameter type.
            if(Boolean.TRUE.equals(useDefaultValueIfPrimitiveWrap)){
            	int argumentPosition = 0;
            	for (ThriftFieldMetadata argument : parameters) {
            		if (args[argumentPosition] == null) {
            			Type argumentType = argument.getThriftType().getJavaType();

            			if (argumentType instanceof Class) {
            				Class<?> argumentClass = (Class<?>) argumentType;
            				argumentClass = Primitives.unwrap(argumentClass);
            				args[argumentPosition] = Defaults.defaultValue(argumentClass);
            			}
            		}
            		argumentPosition++;
            	}
            }
            return args;
        }
        catch (TProtocolException e) {
            // TProtocolException is the only recoverable exception
            // Other exceptions may have left the input stream in corrupted state so we must
            // tear down the socket.
            throw new TApplicationException(TApplicationException.PROTOCOL_ERROR, e.getMessage());
        }
    }

    private <T> void writeResponse(TProtocol out,
                                   int sequenceId,
                                   byte responseType,
                                   String responseFieldName,
                                   short responseFieldId,
                                   ThriftCodec<T> responseCodec,
                                   T result) throws Exception
    {
        out.writeMessageBegin(new TMessage(name, responseType, sequenceId));

        TProtocolWriter writer = new TProtocolWriter(out);
        writer.writeStructBegin(resultStructName);
        writer.writeField(responseFieldName, (short) responseFieldId, responseCodec, result);
        writer.writeStructEnd();

        out.writeMessageEnd();
        out.getTransport().flush();
    }

    private static final class ExceptionProcessor
    {
        private final short id;
        private final ThriftCodec<Object> codec;

        @SuppressWarnings("unchecked")
		private ExceptionProcessor(short id, ThriftCodec<?> coded)
        {
            this.id = id;
            this.codec = (ThriftCodec<Object>) coded;
        }

        public short getId()
        {
            return id;
        }

        public ThriftCodec<Object> getCodec()
        {
            return codec;
        }
    }

	/**
	 * 设置对于primitive wrap类型是否将{@link null}转为default value，默认为{@link false} <br>
	 * 该方法只能被调用一次
	 * @param useDefaultValueIfPrimitiveWrap
	 * @throws IllegalStateException 该方法已经被调用过
	 */
	public static synchronized void setUseDefaultValueIfPrimitiveWrap(boolean useDefaultValueIfPrimitiveWrap) {
		Preconditions.checkState(null == ThriftMethodProcessorCustom.useDefaultValueIfPrimitiveWrap,
				"useDefaultValueIfPrimitiveWrap can be initialized only once");
		ThriftMethodProcessorCustom.useDefaultValueIfPrimitiveWrap = useDefaultValueIfPrimitiveWrap;
	}
}