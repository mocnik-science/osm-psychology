package com.osm.psychology.core;

import com.osm.psychology.strategies.Strategy;

public class Export {
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
        Exporter exporter = new ExporterCSV(strategy.getName() + "_" + bbox.getName() + "_" + isoDateStart + "-" + isoDateEnd, cols);
        strategy.compute(exporter, Data.getInstance().with(bbox, isoDateStart, isoDateEnd));
        exporter.close();
    }
}
