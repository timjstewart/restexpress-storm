package com.udacity.storm.restexpress;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

public class InputController<BodyType> {
	final BlockingQueue<Emission> queue;
	final RouteToStreamDefinition<BodyType> rtsDef;
	final Class<BodyType> clazz;
	
	public InputController(final BlockingQueue<Emission> queue, final RouteToStreamDefinition<BodyType> rtsDef) {
		this.queue  = queue;
		this.rtsDef = rtsDef;
		this.clazz  = rtsDef.getBodyType();
	}
	
	public void handle(Request request, Response response) {
		BodyType input = request.getBodyAs(clazz);
		
		List<Emission> emissions = rtsDef.handle(input);
		
		queue.addAll(emissions);

		response.setResponseCreated();
	}
}
