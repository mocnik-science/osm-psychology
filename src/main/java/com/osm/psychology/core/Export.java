package com.osm.psychology.core;

import com.osm.psychology.strategies.Strategy;

import java.io.File;

public class Export {
    private static final String outputPath = "exported-data";

    public static void csv(Strategy strategy, Col ... cols) throws Exception {
        Export.csv(strategy, null, null, null, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, Col ... cols) throws Exception {
        Export.csv(strategy, bbox, null, null, cols);
    }

    public static void csv(Strategy strategy, String isoDateStart, String isoDateEnd, Col ... cols) throws Exception {
        Export.csv(strategy, null, isoDateStart, isoDateEnd, cols);
    }

    public static void csv(Strategy strategy, BoundingBox bbox, String isoDateStart, String isoDateEnd, Col ... cols) throws Exception {
        File file = new File(outputPath, strategy.getName() + "_" + bbox.getName() + "_" + isoDateStart + "-" + isoDateEnd);
        Exporter exporter = new ExporterCSV(file, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDateStart, isoDateEnd));
        exporter.close();
    }
}
