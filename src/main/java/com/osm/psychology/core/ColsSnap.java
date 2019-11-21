package com.osm.psychology.core;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColsSnap {
    public static Set<ColSnap> process(ColSnap[] cols) {
        HashSet<ColSnap> cs = Stream.of(cols).collect(Collectors.toCollection(HashSet::new));
        if (cs.isEmpty()) cs.add(ColSnap.ALL);
        if (cs.contains(ColSnap.ALL)) return Set.of(ColSnap.values());
        if (cs.contains(ColSnap.GEOMETRY)) cs.addAll(Set.of(ColSnap.GEOMETRY_TYPE, ColSnap.AREA, ColSnap.LENGTH, ColSnap.NUMBER_OF_POINTS, ColSnap.CENTROID));
        if (cs.contains(ColSnap.TAG_INFORMATION)) cs.addAll(Set.of(ColSnap.NUMBER_OF_TAGS, ColSnap.TAGS));
        if (cs.contains(ColSnap.BASIC_INFORMATION)) cs.addAll(Set.of(ColSnap.OSM_ID, ColSnap.TIMESTAMP, ColSnap.NUMBER_OF_CHANGES));
        return cs;
    }
}
