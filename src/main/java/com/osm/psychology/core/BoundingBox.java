package com.osm.psychology.core;

import org.heigit.bigspatialdata.oshdb.util.OSHDBBoundingBox;

public class BoundingBox {
    private String name;
    private OSHDBBoundingBox oshdbBoundingBox;

    public BoundingBox(String name, double minLon, double minLat, double maxLon, double maxLat) {
        this.name = name;
        this.oshdbBoundingBox = new OSHDBBoundingBox(minLon, minLat, maxLon, maxLat);
    }

    public String getName() {
        return this.name;
    }

    public OSHDBBoundingBox getOshdbBoundingBox() {
        return this.oshdbBoundingBox;
    }
}
