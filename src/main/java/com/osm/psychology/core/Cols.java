package com.osm.psychology.core;

import java.util.Set;

public class Cols {
    public static Set<Col> process(Col[] cols) {
        Set<Col> cs = Set.of(cols);
        if (cs.isEmpty()) cs.add(Col.ALL);
        if (cs.contains(Col.ALL)) return Set.of(Col.values());
        if (cs.contains(Col.BEFORE)) cs.addAll(Set.of(Col.GEOMETRY_BEFORE, Col.NUMBER_OF_TAGS_BEFORE));
        if (cs.contains(Col.GEOMETRY_BEFORE)) cs.addAll(Set.of(Col.GEOMETRY_TYPE_BEFORE, Col.AREA_BEFORE, Col.LENGTH_BEFORE, Col.NUMBER_OF_POINTS_BEFORE, Col.CENTROID_BEFORE));
        if (cs.contains(Col.AFTER)) cs.addAll(Set.of(Col.GEOMETRY_AFTER, Col.NUMBER_OF_TAGS_AFTER));
        if (cs.contains(Col.GEOMETRY_AFTER)) cs.addAll(Set.of(Col.GEOMETRY_TYPE_AFTER, Col.AREA_AFTER, Col.LENGTH_AFTER, Col.NUMBER_OF_POINTS_AFTER, Col.CENTROID_AFTER));
        return cs;
    }
}
