package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import com.osm.psychology.core.Exporter;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;

public abstract class StrategyOSMEntitySnapshot extends Strategy {
    public abstract MapReducer<OSMEntitySnapshot> computeOSMEntitySnapshot(Data data) throws Exception;

    public void compute(Exporter exporter, Data data) throws Exception {
        this.computeOSMEntitySnapshot(data).stream().forEach(exporter::write);
    }
}
