package com.osm.psychology.core;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExporterCSV extends Exporter {
    private CSVWriter csvWriter;

    public ExporterCSV(String filename, Col[] cols) throws IOException {
        FileWriter file = new FileWriter(filename + ".csv");
        this.csvWriter = new CSVWriter(file);
        this.init(cols);
    }

    protected void writeHeader(List<String> header) {
        this.csvWriter.writeNext(header.toArray(new String[0]));
    }

    protected void writeRow(List<String> row) {
        this.csvWriter.writeNext(row.toArray(new String[0]));
    }

    public void close() throws IOException {
        this.csvWriter.close();
    }
}
