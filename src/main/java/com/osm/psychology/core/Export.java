package com.osm.psychology.core;

import com.osm.psychology.strategies.Strategy;

import java.io.File;

public class Export {
    private static final String outputPath = "exported-data";

    private static File file(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd) {
        return new File(outputPath, strategy.getName() + "_" + ((bbox != null) ? bbox.getName() + "_" : "") + ((isoDateStart != null || isoDateEnd != null) ? ((isoDateStart != null) ? isoDateStart : "") + "-" + ((isoDateEnd != null) ? isoDateEnd : "") : ""));
    }

    public static void csv(Strategy strategy, ColContr... cols) throws Exception {
        Export.csv(strategy, null, null, null, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, ColContr... cols) throws Exception {
        Export.csv(strategy, bbox, null, null, cols);
    }

    public static void csv(Strategy strategy, String isoDateStart, String isoDateEnd, ColContr... cols) throws Exception {
        Export.csv(strategy, null, isoDateStart, isoDateEnd, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd, ColContr... cols) throws Exception {
        File file = Export.file(strategy, bbox, isoDateStart, isoDateEnd);
        Exporter exporter = new ExporterCSV(file, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDateStart, isoDateEnd));
        exporter.close();
    }

    public static void json(Strategy strategy, ColContr... cols) throws Exception {
        Export.json(strategy, null, null, null, cols);
    }

    public static void json(Strategy strategy, BoundingBox bbox, ColContr... cols) throws Exception {
        Export.json(strategy, bbox, null, null, cols);
    }

    public static void json(Strategy strategy, String isoDateStart, String isoDateEnd, ColContr... cols) throws Exception {
        Export.json(strategy, null, isoDateStart, isoDateEnd, cols);
    }

    public static void json(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd, ColContr... cols) throws Exception {
        File file = Export.file(strategy, bbox, isoDateStart, isoDateEnd);
        Exporter exporter = new ExporterJSON(file, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDateStart, isoDateEnd));
        exporter.close();
    }
}
