package com.pearson.storm.restexpress;

import java.util.List;

import com.strategicgains.restexpress.Request;
import backtype.storm.tuple.Fields;
import java.io.Serializable;

public interface RequestParser {

    public String getName();

    public Fields getFields();

	public ParserResult parse(Request request);

}
