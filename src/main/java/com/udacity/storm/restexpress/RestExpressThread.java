package com.udacity.storm.restexpress;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.RestExpress;
import com.strategicgains.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import com.strategicgains.restexpress.response.DefaultResponseWrapper;
import com.strategicgains.restexpress.response.ResponseProcessor;
import com.strategicgains.restexpress.serialization.json.DefaultJsonProcessor;

public class RestExpressThread extends Thread {

	private final int workerCount;
	private final int executorThreadCount;
	private final int port;
	private final BlockingQueue<Emission> queue;
	private final List<RouteToStreamDefinition<?>> rtsDefs;

	public RestExpressThread(int workerCount, int executorThreadCount, int port, BlockingQueue<Emission> queue, List<RouteToStreamDefinition<?>> rtsDefs) {
		super();
		this.workerCount = workerCount;
		this.executorThreadCount = executorThreadCount;
		this.port = port;
		this.queue = queue;
		this.rtsDefs = rtsDefs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		
		RestExpress server = new RestExpress()
			.setName("RestExpressSpout")
			.setPort(port)
			.setDefaultFormat(Format.JSON)
			.putResponseProcessor(Format.JSON, new ResponseProcessor(new DefaultJsonProcessor(), new DefaultResponseWrapper()))
			.addMessageObserver(new SimpleConsoleLogMessageObserver());
		
		for(RouteToStreamDefinition<?> def: rtsDefs) {
			for(String methodString: def.getMethods()) {
				HttpMethod method = HttpMethod.valueOf(methodString);
				server.uri(def.getUrlPattern(), new InputController(queue, def)).action("handle", method);			
			}
		}

		if (workerCount > 0)
		{
			server.setExecutorThreadCount(workerCount);
		}

		if (executorThreadCount > 0)
		{
			server.setExecutorThreadCount(executorThreadCount);
		}

		server.bind();
	}	
}
