package com.osm.psychology.core;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExporterCSV extends Exporter {
    private static final DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    private CSVWriter csvWriter;

    public ExporterCSV(File file, QueryType queryType, Col[] cols) throws IOException {
        file.getParentFile().mkdirs();
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile() + ".csv");
        this.csvWriter = new CSVWriter(fileWriter);
        this.init(cols, queryType);
    }

    protected void writeHeader(List<String> header) {
        this.csvWriter.writeNext(header.toArray(new String[0]));
    }

    protected void writeRow(List<Object> row) {
        List<String> rowString = row.stream().map(o -> {
            if (o == null || o instanceof None) return "";
            if (o instanceof String) return (String) o;
            if (o instanceof Long) return Long.toString((Long) o);
            if (o instanceof Double) return Double.toString((Double) o);
            if (o instanceof Integer) return Integer.toString((Integer) o);
            if (o instanceof Date) {
                try {
                    return this.formatDate.format((Date) o);
                } catch (Exception e) {
                    new Exception("Could not export date " + o.toString()).printStackTrace();
                    return "";
                }
            }
            if (o instanceof Tags) {
                Map<String, String> tags = ((Tags) o).getTagsAsMap();
                return tags
                        .keySet()
                        .stream()
                        .map(key -> key + "=" + tags.get(key))
                        .collect(Collectors.joining(","));
            }
            new Exception("Could not export CSV: unknown type of object - " + o.toString()).printStackTrace();
            return "";
        }).collect(Collectors.toList());
        this.csvWriter.writeNext(rowString.toArray(new String[0]));
    }

    public void close() throws IOException {
        super.close();
        this.csvWriter.close();
    }
}
