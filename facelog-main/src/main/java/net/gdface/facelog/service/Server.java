package net.gdface.facelog.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.google.common.collect.ImmutableList;

/**
 * @author guyadong
 *
 */
public class Server {
	public static class Builder {
	    private ExecutorService executor;
	    private int serverPort=DEFAULT_PORT;
	    private List<?> services = ImmutableList.of();
		public Builder() {
		}
	    public Builder withServices(Object ...services){
	    	return withServices(ImmutableList.copyOf(services));
	    }
	    public Builder withServices(List<?>services){
	    	checkArgument(null != services && !services.isEmpty());
	    	this.services = services;
			return this;    	
	    }
	    public Builder setServerPort(int serverPort) {
	    	checkArgument(serverPort>0);
			this.serverPort = serverPort;
			return this;
		}
		public Builder setExecutor(ExecutorService executor) {
			this.executor = checkNotNull(executor);
			return this;
		}
		public Server build(){
			return new Server(services, serverPort, executor);
		}
	}
	public static final Builder bulider(){
		return new Builder();
	}
	public static final int DEFAULT_PORT = 26411;
    private final ExecutorService executor;
    private final ThriftServer server;
    private final int serverPort;
    private final ThriftServiceProcessor processor;
    public Server(List<?> services, int serverPort, ExecutorService executor) {
		checkArgument(null != services && !services.isEmpty());
		this.serverPort = serverPort>0? serverPort : DEFAULT_PORT;
		this.executor = checkNotNull(executor);
		processor = new ThriftServiceProcessor(
    			new ThriftCodecManager(),
    			ImmutableList.<ThriftEventHandler>of(),
    			services
    			);
        ThriftServerDef serverDef = ThriftServerDef.newBuilder()
                .listen(serverPort)
                .withProcessor(processor)
                .using(this.executor)
                .build();
        NettyServerConfig serverConfig = NettyServerConfig.newBuilder()
                .setBossThreadExecutor(this.executor)
                .setWorkerThreadExecutor(this.executor)
                .build();
        server = new ThriftServer(serverConfig, serverDef);
        // Arrange to stop the server at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                server.close();
            }
        });
   }

    public void stop() {
        server.close();
    }
	public ThriftServer getServer() {
    return server;
}
	public ExecutorService getExecutor() {
		return executor;
	}

	public int getServerPort() {
		return serverPort;
	}
}
