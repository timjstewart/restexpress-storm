package com.udacity.storm.restexpress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Values;
import backtype.storm.tuple.Fields;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.utils.Utils;

public class RestExpressSpout extends BaseRichSpout {
	private static final long serialVersionUID = -1150677524672544330L;

	private SpoutOutputCollector collector;

	private final LinkedBlockingQueue<Emission> queue = new LinkedBlockingQueue<Emission>();
	private final List<RouteToStreamDefinition<?>> rtsDefs;
	private final RestExpressSpoutConfig env;
    
	public RestExpressSpout(RestExpressSpoutConfig environment, List<RouteToStreamDefinition<?>> rtsDefs) {
		super();
		this.env = environment;
		this.rtsDefs = rtsDefs;
	}

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;

		RestExpressThread thread = new RestExpressThread(
				env.getWorkerCount(),
				env.getExecutorThreadCount(),
				env.getPort(),
				queue,
				rtsDefs
		);

		thread.run();
	}

	@Override
	public void nextTuple() {
		Emission ret = queue.poll();

        if(ret == null) {
            Utils.sleep(env.getSpoutPollingDelayMillis());
        } else {
            collector.emit(ret.getMessage());
        }
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		for(RouteToStreamDefinition<?> rtsd: rtsDefs) {
			rtsd.declareFields(declarer);
		}
	}
}
