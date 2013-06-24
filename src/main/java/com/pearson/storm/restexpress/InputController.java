package com.pearson.storm.restexpress;

import java.util.concurrent.BlockingQueue;

import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

/**
 * A RestExpress Controller class that handles HTTP requests.
 *
 * An InputController should use its RequestParser to parse out one or
 * more ParserRecords from the Request.  Those ParserRecords are then
 * added to a shared queue so that the RestExpressSpout can pull a
 * ParserRecord off the queue whenever the RestExpressSpout is asked
 * for a tuple.
 */
public final class InputController {

	final BlockingQueue<QueueEntry> queue;
	final RequestParser requestParser;
	
	public InputController(final BlockingQueue<QueueEntry> queue, 
                           final RequestParser requestParser) {
		this.queue  = queue;
		this.requestParser = requestParser;
	}
	
	public void handle(Request request, Response response) {
        ParserResult records = requestParser.parse(request);

        for (ParserRecord record : records) {
            queue.add(new QueueEntry(requestParser.getName(), record));
        }

		response.setResponseCreated();
	}
}
