package com.osm.psychology.tools;

import org.heigit.bigspatialdata.oshdb.util.geometry.Geo;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;

public class GeometryTools {
    public static double areaOf(Geometry g) {
        return Geo.areaOf(g);
    }

    public static double lengthOf(Geometry g) {
        if (g instanceof GeometryCollection) {
            double l = 0.0;
            for (int i = 0; i < g.getNumGeometries(); i++) l += GeometryTools.lengthOf(g.getGeometryN(i));
            return l;
        } else return Math.max(Geo.lengthOf(g), Geo.lengthOf(g.getBoundary()));
    }
}
