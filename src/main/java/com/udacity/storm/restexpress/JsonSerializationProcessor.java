package com.udacity.storm.restexpress;

import java.util.Date;

import com.strategicgains.restexpress.serialization.json.DefaultJsonProcessor;
import com.strategicgains.restexpress.serialization.json.JacksonTimepointSerializer;
import com.strategicgains.util.date.DateAdapterConstants;

public class JsonSerializationProcessor extends DefaultJsonProcessor
{
	public JsonSerializationProcessor()
    {
	    super();
    }
}
