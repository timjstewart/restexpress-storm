package com.pearson.storm.restexpress;

import java.util.List;
import java.util.concurrent.BlockingQueue;


import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.RestExpress;
import com.strategicgains.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import com.strategicgains.restexpress.response.DefaultResponseWrapper;
import com.strategicgains.restexpress.response.ResponseProcessor;
import com.strategicgains.restexpress.serialization.text.DefaultTxtProcessor;

public final class RestExpressThread extends Thread {

    private final RestExpressSpoutConfig config;
	private final BlockingQueue<QueueEntry> queue;
	private final List<Route> routes;

	public RestExpressThread(RestExpressSpoutConfig config,
                             BlockingQueue<QueueEntry> queue, 
                             List<Route> routes) {
        this.config = config;
		this.queue  = queue;
		this.routes = routes;
	}

	@Override public void run() {
		RestExpress server = new RestExpress().setName("RestExpressSpout");
        
        config.apply(server);

        registerRoutes(server);

		server.bind();
	}	

    private void registerRoutes(final RestExpress server) {
		for(Route route: routes) {
            route.register(server, queue);
		}
    }
}
