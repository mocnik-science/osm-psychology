package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;
import org.heigit.bigspatialdata.oshdb.osm.OSMType;

public class StrategyRoads extends Strategy {
    public String getName() {
        return "Roads";
    }

    @Override
    public MapReducer<OSMEntitySnapshot> computeOSMEntitySnapshot(Data data) throws Exception {
        return data.getOSMEntityView()
                .osmType(OSMType.WAY)
                .osmTag("highway");
    }

    @Override
    public MapReducer<OSMContribution> computeOSMContribution(Data data) throws Exception {
        return data.getOSMContributionView()
                .osmType(OSMType.WAY)
                .osmTag("highway");
    }
}
