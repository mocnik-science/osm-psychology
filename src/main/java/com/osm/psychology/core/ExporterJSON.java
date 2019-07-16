package com.osm.psychology.core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class ExporterJSON extends Exporter {
    private String filename;
    private JSONArray jsonRows = new JSONArray();
    private List<String> header;

    public ExporterJSON(File file, Col[] cols) throws IOException {
        this.filename = file.getAbsoluteFile() + ".json";
        file.getParentFile().mkdirs();
        this.init(cols);
    }

    protected void writeHeader(List<String> header) {
        this.header = header;
    }

    protected void writeRow(List<Object> row) {
        ListIterator<Object> it = row.listIterator();
        JSONObject jsonRow = new JSONObject();
        while (it.hasNext()) jsonRow.put(header.get(it.nextIndex()), it.next());
        this.jsonRows.add(jsonRow);
    }

    public void close() throws IOException {
        Writer fileWriter = new FileWriter(this.filename);
        JSONObject jsonObject = new JSONObject();
        JSONObject metadata = new JSONObject();
        metadata.put("creation software", "created by OSM-Psychology, by Maren Mayer <>, Franz-Benjamin Mocnik <mail@mocnik-science.net>, and Daniel W Heck <>");
        metadata.put("creation date", new Date());
        metadata.put("url", "https://github.com/mocnik-science/osm-psychology");
        jsonObject.put("metadata", metadata);
        jsonObject.put("data", this.jsonRows);
        jsonObject.writeJSONString(fileWriter);
        fileWriter.close();
    }
}
