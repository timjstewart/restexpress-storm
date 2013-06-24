package com.pearson.storm.restexpress;

import backtype.storm.spout.SpoutOutputCollector;

public final class QueueEntry {
    
    private final String streamId;
    private final ParserRecord record;

    public QueueEntry(final String streamId,
                      final ParserRecord record) {
        this.streamId = streamId;
        this.record = record;
    }

    public void dispatch(SpoutOutputCollector collector) {
        System.out.println(record.toString());
        collector.emit(streamId, record.getValues());
    }
}
