package com.osm.psychology.core;

import com.osm.psychology.strategies.Strategy;

import java.io.File;

public class ExportEntities {
    private static final String outputPath = "exported-data";

    private static File file(Strategy strategy, BoundingBox bbox, String isoDate) {
        return new File(outputPath, "Entities_" + strategy.getName() + "_" + ((bbox != null) ? bbox.getName() + "_" : "") + (isoDate != null ? isoDate : ""));
    }

    public static void csv(Strategy strategy, Col... cols) throws Exception {
        ExportEntities.csv(strategy, null, null, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, Col... cols) throws Exception {
        ExportEntities.csv(strategy, bbox, null, cols);
    }

    public static void csv(Strategy strategy, String isoDate, Col... cols) throws Exception {
        ExportEntities.csv(strategy, isoDate, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, String isoDate, Col... cols) throws Exception {
        File file = ExportEntities.file(strategy, bbox, isoDate);
        Exporter exporter = new ExporterCSV(file, QueryType.ENTITY, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDate), QueryType.ENTITY);
        exporter.close();
    }

    public static void json(Strategy strategy, Col... cols) throws Exception {
        ExportEntities.json(strategy, null, null, cols);
    }

    public static void json(Strategy strategy, BoundingBox bbox, Col... cols) throws Exception {
        ExportEntities.json(strategy, bbox, null, cols);
    }

    public static void json(Strategy strategy, String isoDate, Col... cols) throws Exception {
        ExportEntities.json(strategy, isoDate, cols);
    }

    public static void json(Strategy strategy, BoundingBox bbox, String isoDate, Col... cols) throws Exception {
        File file = ExportEntities.file(strategy, bbox, isoDate);
        Exporter exporter = new ExporterJSON(file, QueryType.ENTITY, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDate), QueryType.ENTITY);
        exporter.close();
    }
}
