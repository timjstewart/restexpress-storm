package com.pearson.storm.restexpress;

import com.strategicgains.restexpress.RestExpress;
import backtype.storm.topology.OutputFieldsDeclarer;
import org.jboss.netty.handler.codec.http.HttpMethod;
import java.util.concurrent.BlockingQueue;
import java.io.Serializable;

public final class Route implements Serializable {

    private static final long serialVersionUID = -16524232323L;

	private final String urlPattern;
	private final String[] methods;
	private final RequestParser parser;
	
	public Route(final String        urlPattern, 
                 final RequestParser parser, 
                 final String...     methods) {

		if((methods == null || methods.length == 0))
			throw new IllegalArgumentException("At least one HttpMethod should be specified when instantiating RouteToStreamDefinition");
		
		this.urlPattern = urlPattern;
		this.parser     = parser;
		this.methods    = methods;
	}
	
    public void register(final RestExpress server, 
                         final BlockingQueue<QueueEntry> queue) {
        for(final String methodString: methods) {

            final HttpMethod method = HttpMethod.valueOf(methodString);
            final InputController inputController = new InputController(queue, parser);

            server.uri(urlPattern, inputController).action("handle", method);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream(parser.getName(), parser.getFields());
    }

}
