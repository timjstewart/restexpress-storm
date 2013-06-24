package com.pearson.storm.restexpress;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

public final class ParserResult implements Iterable<ParserRecord> {

    private final List<ParserRecord> records;

    public static ParserResult fromSingleRecord(final List<Object> values) {
        List<ParserRecord> records = new ArrayList<ParserRecord>(1);
        records.add(new ParserRecord(values));
        return new ParserResult(records);
    }

    public static ParserResult fromMultipleRecords(List<ParserRecord> records) {
        return new ParserResult(records);
    }

    public static ParserResult fromSingleRecord(Object... values) {
        return fromSingleRecord(Arrays.asList(values));
    }

    private ParserResult(final List<ParserRecord> records) {
        this.records = records;
    }

    public Iterator<ParserRecord> iterator() {
        return records.iterator();
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder("ParserResult {\n");

        for (ParserRecord rec: records) {
            sb.append("  ");
            sb.append(rec.toString());
            sb.append("\n");
        }

        sb.append("}");

        return sb.toString();
    }

}
