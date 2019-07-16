package com.osm.psychology.strategies;

import com.osm.psychology.core.Data;
import com.osm.psychology.core.Exporter;

public abstract class Strategy {
    public abstract String getName();
    public abstract void compute(Exporter exporter, Data data) throws Exception;
}
