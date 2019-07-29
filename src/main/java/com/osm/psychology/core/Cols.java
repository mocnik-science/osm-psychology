package com.osm.psychology.core;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cols {
    public static Set<Col> process(Col[] cols) {
        HashSet<Col> cs = Stream.of(cols).collect(Collectors.toCollection(HashSet::new));
        if (cs.isEmpty()) cs.add(Col.ALL);
        if (cs.contains(Col.ALL)) return Set.of(Col.values());
        if (cs.contains(Col.BEFORE)) cs.addAll(Set.of(Col.GEOMETRY_BEFORE, Col.NUMBER_OF_TAGS_BEFORE, Col.TAGS_BEFORE));
        if (cs.contains(Col.GEOMETRY_BEFORE)) cs.addAll(Set.of(Col.GEOMETRY_TYPE_BEFORE, Col.AREA_BEFORE, Col.LENGTH_BEFORE, Col.NUMBER_OF_POINTS_BEFORE, Col.CENTROID_BEFORE));
        if (cs.contains(Col.AFTER)) cs.addAll(Set.of(Col.GEOMETRY_AFTER, Col.NUMBER_OF_TAGS_AFTER, Col.TAGS_AFTER));
        if (cs.contains(Col.GEOMETRY_AFTER)) cs.addAll(Set.of(Col.GEOMETRY_TYPE_AFTER, Col.AREA_AFTER, Col.LENGTH_AFTER, Col.NUMBER_OF_POINTS_AFTER, Col.CENTROID_AFTER));
        if (cs.contains(Col.GEOMETRY)) cs.addAll(Set.of(Col.GEOMETRY_BEFORE, Col.GEOMETRY_AFTER));
        if (cs.contains(Col.TAGS)) cs.addAll(Set.of(Col.NUMBER_OF_TAGS_BEFORE, Col.TAGS_BEFORE, Col.NUMBER_OF_TAGS_AFTER, Col.TAGS_AFTER));
        if (cs.contains(Col.BASIC_INFORMATION)) cs.addAll(Set.of(Col.OSM_ID, Col.CHANGESET_ID, Col.CONTRIBUTOR_USER_ID, Col.TIMESTAMP, Col.CONTRIBUTION_TYPE));
        return cs;
    }
}
