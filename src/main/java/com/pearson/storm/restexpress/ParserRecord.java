package com.pearson.storm.restexpress;

import java.util.List;

public final class ParserRecord {

    private List<Object> values;

    ParserRecord(final List<Object> values) {
        this.values = values;
    }

    public List<Object> getValues() {
        return values;
    }

    @Override public String toString() { 
        StringBuilder sb = new StringBuilder("ParserRecord { ");

        for (Object value : values) {
            sb.append(" ");
            sb.append(value.toString());
        }

        sb.append("}");

        return sb.toString();
    }
}
