package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;

public class StrategyAll extends Strategy {
    public String getName() {
        return "Entities";
    }

    @Override
    public MapReducer<OSMEntitySnapshot> computeOSMEntitySnapshot(Data data) throws Exception {
        return data.getOSMEntityView();
    }

    @Override
    public MapReducer<OSMContribution> computeOSMContribution(Data data) throws Exception {
        return data.getOSMContributionView();
    }
}
