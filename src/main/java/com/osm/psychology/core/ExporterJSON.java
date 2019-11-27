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
import java.util.Map;

public class ExporterJSON extends Exporter {
    private static final DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    private String filename;
    private JSONArray jsonRows = new JSONArray();
    private List<String> header;

    public ExporterJSON(File file, QueryType queryType, Col[] cols) throws IOException {
        this.filename = file.getAbsoluteFile() + ".json";
        file.getParentFile().mkdirs();
        this.init(cols, queryType);
    }

    protected void writeHeader(List<String> header) {
        this.header = header;
    }

    protected void writeRow(List<Object> row) {
        ListIterator<Object> it = row.listIterator();
        JSONObject jsonRow = new JSONObject();
        while (it.hasNext()) {
            int i = it.nextIndex();
            Object o = it.next();
            if (o == null || o instanceof None) jsonRow.put(header.get(i), null);
            else if (o instanceof Tags) {
                JSONObject tagObject = new JSONObject();
                for (Map.Entry<String, String> entry : ((Tags) o).getTagsAsMap().entrySet()) tagObject.put(entry.getKey(), entry.getValue());
                jsonRow.put(header.get(i), tagObject);
            } else if (o instanceof Date) {
                try {
                    jsonRow.put(header.get(i), this.formatDate.format((Date) o));
                } catch (Exception e) {
                    new Exception("Could not export date " + o.toString()).printStackTrace();
                    jsonRow.put(header.get(i), null);
                }
            }
            else jsonRow.put(header.get(i), o);
        }
        this.jsonRows.add(jsonRow);
    }

    public void close() throws IOException {
        super.close();
        Writer fileWriter = new FileWriter(this.filename);
        JSONObject jsonObject = new JSONObject();
        JSONObject metadata = new JSONObject();
        metadata.put("creation software", "created by OSM-Psychology, by Maren Mayer <maren.mayer@students.uni-mannheim.de>, Franz-Benjamin Mocnik <mail@mocnik-science.net>, and Daniel W Heck <heck@uni-mannheim.de>");
        metadata.put("creation date", this.formatDate.format(new Date()));
        metadata.put("url", "https://github.com/mocnik-science/osm-psychology");
        jsonObject.put("metadata", metadata);
        jsonObject.put("data", this.jsonRows);
        jsonObject.writeJSONString(fileWriter);
        fileWriter.close();
    }
}
