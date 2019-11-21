package com.osm.psychology.core;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColsContr {
    public static Set<ColContr> process(ColContr[] cols) {
        HashSet<ColContr> cs = Stream.of(cols).collect(Collectors.toCollection(HashSet::new));
        if (cs.isEmpty()) cs.add(ColContr.ALL);
        if (cs.contains(ColContr.ALL)) return Set.of(ColContr.values());
        if (cs.contains(ColContr.BEFORE)) cs.addAll(Set.of(ColContr.GEOMETRY_BEFORE, ColContr.NUMBER_OF_TAGS_BEFORE, ColContr.TAGS_BEFORE));
        if (cs.contains(ColContr.GEOMETRY_BEFORE)) cs.addAll(Set.of(ColContr.GEOMETRY_TYPE_BEFORE, ColContr.AREA_BEFORE, ColContr.LENGTH_BEFORE, ColContr.NUMBER_OF_POINTS_BEFORE, ColContr.CENTROID_BEFORE));
        if (cs.contains(ColContr.AFTER)) cs.addAll(Set.of(ColContr.GEOMETRY_AFTER, ColContr.NUMBER_OF_TAGS_AFTER, ColContr.TAGS_AFTER));
        if (cs.contains(ColContr.GEOMETRY_AFTER)) cs.addAll(Set.of(ColContr.GEOMETRY_TYPE_AFTER, ColContr.AREA_AFTER, ColContr.LENGTH_AFTER, ColContr.NUMBER_OF_POINTS_AFTER, ColContr.CENTROID_AFTER));
        if (cs.contains(ColContr.GEOMETRY)) cs.addAll(Set.of(ColContr.GEOMETRY_BEFORE, ColContr.GEOMETRY_AFTER));
        if (cs.contains(ColContr.TAGS)) cs.addAll(Set.of(ColContr.NUMBER_OF_TAGS_BEFORE, ColContr.TAGS_BEFORE, ColContr.NUMBER_OF_TAGS_AFTER, ColContr.TAGS_AFTER));
        if (cs.contains(ColContr.BASIC_INFORMATION)) cs.addAll(Set.of(ColContr.OSM_ID, ColContr.TIMESTAMP, ColContr.CONTRIBUTION_TYPE));
        return cs;
    }
}
