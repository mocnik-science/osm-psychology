package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import com.osm.psychology.core.Exporter;
import com.osm.psychology.core.QueryType;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;

public abstract class Strategy {
    public abstract String getName();
    public abstract MapReducer<OSMEntitySnapshot> computeOSMEntitySnapshot(Data data) throws Exception;
    public abstract MapReducer<OSMContribution> computeOSMContribution(Data data) throws Exception;

    public void compute(Exporter exporter, Data data, QueryType queryType) throws Exception {
        if (queryType == QueryType.ENTITY) this.computeOSMEntitySnapshot(data).stream().forEach(exporter::write);
        if (queryType == QueryType.CONTRIBUTION) this.computeOSMContribution(data).stream().forEach(exporter::write);
    };
}
