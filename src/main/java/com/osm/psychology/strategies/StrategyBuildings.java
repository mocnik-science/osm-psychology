package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import com.osm.psychology.core.Exporter;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.osm.OSMType;

public class StrategyBuildings extends StrategyOSMContribution {
    public String getName() {
        return "Buildings";
    }

    public MapReducer<OSMContribution> computeOSMContribution(Data data) throws Exception {
        return data.getOSMContributionView()
                .osmType(OSMType.WAY)
                .osmTag("building");
    }
}
