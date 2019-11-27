package com.osm.psychology.core;

import com.osm.psychology.strategies.Strategy;

import java.io.File;

public class ExportContributions {
    private static final String outputPath = "exported-data";

    private static File file(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd) {
        return new File(outputPath, "Contributions_" + strategy.getName() + "_" + ((bbox != null) ? bbox.getName() + "_" : "") + ((isoDateStart != null || isoDateEnd != null) ? ((isoDateStart != null) ? isoDateStart : "") + "-" + ((isoDateEnd != null) ? isoDateEnd : "") : ""));
    }

    public static void csv(Strategy strategy, Col... cols) throws Exception {
        ExportContributions.csv(strategy, null, null, null, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, Col... cols) throws Exception {
        ExportContributions.csv(strategy, bbox, null, null, cols);
    }

    public static void csv(Strategy strategy, String isoDateStart, String isoDateEnd, Col... cols) throws Exception {
        ExportContributions.csv(strategy, null, isoDateStart, isoDateEnd, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd, Col... cols) throws Exception {
        File file = ExportContributions.file(strategy, bbox, isoDateStart, isoDateEnd);
        Exporter exporter = new ExporterCSV(file, QueryType.CONTRIBUTION, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDateStart, isoDateEnd), QueryType.CONTRIBUTION);
        exporter.close();
    }

    public static void json(Strategy strategy, Col... cols) throws Exception {
        ExportContributions.json(strategy, null, null, null, cols);
    }

    public static void json(Strategy strategy, BoundingBox bbox, Col... cols) throws Exception {
        ExportContributions.json(strategy, bbox, null, null, cols);
    }

    public static void json(Strategy strategy, String isoDateStart, String isoDateEnd, Col... cols) throws Exception {
        ExportContributions.json(strategy, null, isoDateStart, isoDateEnd, cols);
    }

    public static void json(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd, Col... cols) throws Exception {
        File file = ExportContributions.file(strategy, bbox, isoDateStart, isoDateEnd);
        Exporter exporter = new ExporterJSON(file, QueryType.CONTRIBUTION, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDateStart, isoDateEnd), QueryType.CONTRIBUTION);
        exporter.close();
    }
}
