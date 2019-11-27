package com.osm.psychology.core;

import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;
import org.heigit.bigspatialdata.oshdb.osm.OSMEntity;
import org.heigit.bigspatialdata.oshdb.util.OSHDBTimestamp;

import java.util.stream.StreamSupport;

public class Tools {
    public static OSHDBTimestamp entityLastChange(OSMEntitySnapshot entity) {
        OSHDBTimestamp timestamp = new OSHDBTimestamp(0);
        for (OSMEntity version : entity.getOSHEntity().getVersions()) if (version.getTimestamp().compareTo(entity.getTimestamp()) < 0 && version.getTimestamp().compareTo(timestamp) > 0) timestamp = version.getTimestamp();
        return timestamp;
    }

    public static Long entityNumberOfChanges(OSMEntitySnapshot entity) {
        return StreamSupport.stream(entity.getOSHEntity().getVersions().spliterator(), false).filter(version -> ((OSMEntity) version).getTimestamp().compareTo(entity.getTimestamp()) <= 0).count();
    }
}
