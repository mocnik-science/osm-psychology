package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;

public class StrategyEntities extends StrategyOSMEntitySnapshot {
    public String getName() {
        return "Entities";
    }

    public MapReducer<OSMEntitySnapshot> computeOSMEntitySnapshot(Data data) throws Exception {
        return data.getOSMEntityView();
    }
}
