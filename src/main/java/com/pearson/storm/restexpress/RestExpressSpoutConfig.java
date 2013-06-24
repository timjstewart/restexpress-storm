package com.pearson.storm.restexpress;

import com.strategicgains.restexpress.RestExpress;
import java.io.Serializable;

public final class RestExpressSpoutConfig implements Serializable {

    private static final long serialVersionUID = -65244232323L;

    /** Which port the server should listen on. */
	private int port;

    /** The maximum number of concurrent connections the server should support. */
    private int maxConcurrentConnections;
    
    /** Number of background threads RestExpress should use. */
    private int backgroundThreadCount;

    /** Whether or not to use socket keep alive. */
    private boolean socketKeepAlive;
	
    /**
     * Create a RestExpressSpoutConfig object.
     */
	public RestExpressSpoutConfig(int port,
                                  int maxConcurrentConnections, 
                                  int backgroundThreadCount, 
                                  boolean socketKeepAlive) {
		this.port = port;
        this.maxConcurrentConnections = maxConcurrentConnections;
        this.backgroundThreadCount = backgroundThreadCount;
        this.socketKeepAlive = socketKeepAlive;
	}

    /**
     * Apply the configuration to the RestExpress server.
     */
	public void apply(RestExpress restExpress) {
        restExpress
            .setPort(port)
            .setExecutorThreadCount(backgroundThreadCount)
            .setIoThreadCount(maxConcurrentConnections)
            .setKeepAlive(socketKeepAlive);
	}

}
