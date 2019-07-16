package com.osm.psychology.core;

import org.heigit.bigspatialdata.oshdb.api.db.OSHDBDatabase;
import org.heigit.bigspatialdata.oshdb.api.db.OSHDBH2;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.MapReducer;
import org.heigit.bigspatialdata.oshdb.api.mapreducer.OSMContributionView;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.util.tagtranslator.TagTranslator;

public class Data {
    private static Data dataInstance = null;
    private Data() {}

    public static Data getInstance() {
        if (Data.dataInstance == null) Data.dataInstance = new Data();
        return Data.dataInstance;
    }

    private OSHDBDatabase oshdb = null;
    private TagTranslator tagTranslator = null;
    private BoundingBox bbox = null;
    private String isoDateStart = null;
    private String isoDateEnd = null;

    public static void load(String filename) throws Exception {
        Data data = Data.getInstance();
        OSHDBH2 oshdbH2 = new OSHDBH2(filename.replaceAll("(\\.mv)?\\.db$", ""));
        data.oshdb = oshdbH2.multithreading(true);
        data.tagTranslator = new TagTranslator(oshdbH2.getConnection());
    }

    public static TagTranslator getTagTranslator() {
        return Data.getInstance().tagTranslator;
    }

    public Data with(BoundingBox bbox, String isoDateStart, String isoDateEnd) {
        Data data = new Data();
        data.oshdb = this.oshdb;
        data.bbox = bbox;
        data.isoDateStart = isoDateStart;
        data.isoDateEnd = isoDateEnd;
        return data;
    }

    public MapReducer<OSMContribution> getOSMContributionView() {
        MapReducer<OSMContribution> mapReducer = OSMContributionView.on(this.oshdb);
        if (this.bbox != null) mapReducer = mapReducer.areaOfInterest(bbox.getOshdbBoundingBox());
        if (this.isoDateStart != null && this.isoDateEnd != null) mapReducer = mapReducer.timestamps(this.isoDateStart, this.isoDateEnd);
        return mapReducer;
    }

}
