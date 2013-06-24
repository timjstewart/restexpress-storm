package com.pearson.storm.restexpress;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.utils.Utils;

public final class RestExpressSpout extends BaseRichSpout {

    private static final long serialVersionUID = -65286323L;

	private SpoutOutputCollector collector;

	private final LinkedBlockingQueue<QueueEntry> queue;

	private final List<Route> routes;

	private final RestExpressSpoutConfig config;
    
	public RestExpressSpout(final RestExpressSpoutConfig config, 
                            final List<Route> routes) {
		this.config = config;
		this.routes = routes;

        this.queue  = new LinkedBlockingQueue<QueueEntry>();
	}

    @SuppressWarnings("rawtypes")
	@Override public void open(Map conf, 
                               TopologyContext context, 
                               SpoutOutputCollector collector) {
		this.collector = collector;

		RestExpressThread thread = new RestExpressThread(config, queue, routes);

		thread.run();
	}

	@Override public void nextTuple() {
		QueueEntry entry = queue.poll();

        if(entry == null) {
            Utils.sleep(1);
        } else {
            entry.dispatch(collector);
        }
	}

	@Override public void declareOutputFields(OutputFieldsDeclarer declarer) {
		for(Route route: routes) {
            route.declareOutputFields(declarer);
		}
	}
}
