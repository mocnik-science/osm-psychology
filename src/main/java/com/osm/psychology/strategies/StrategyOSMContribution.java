package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import com.osm.psychology.core.Exporter;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;

public abstract class StrategyOSMContribution extends Strategy {
    public abstract MapReducer<OSMContribution> computeOSMContribution(Data data) throws Exception;

    public void compute(Exporter exporter, Data data) throws Exception {
        this.computeOSMContribution(data).stream().forEach(exporter::write);
    }
}
