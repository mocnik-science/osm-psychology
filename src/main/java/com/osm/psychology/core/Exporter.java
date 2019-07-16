package com.osm.psychology.core;

import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.util.celliterator.ContributionType;
import org.heigit.bigspatialdata.oshdb.util.geometry.Geo;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Exporter {
    protected abstract void writeHeader(List<String> header);
    protected abstract void writeRow(List<String> row);
    public void close() throws IOException {}

    private Set<Col> cols;

    public void init(Col[] cols) {
        this.cols = Cols.process(cols);
        List<String> header = new ArrayList<>();
        if (this.cols.contains(Col.OSM_ID)) header.add("OsmID");
        if (this.cols.contains(Col.CHANGESET_ID)) header.add("ChangesetID");
        if (this.cols.contains(Col.CONTRIBUTOR_USER_ID)) header.add("ContributorUserID");
        if (this.cols.contains(Col.TIMESTAMP)) header.add("Timestamp");
        if (this.cols.contains(Col.CONTRIBUTION_TYPE)) header.addAll(List.of("ContributionTypeCreation", "ContributionTypeDeletion", "ContributionTypeTagChange", "ContributionTypeGeometryChange"));
        if (this.cols.contains(Col.GEOMETRY_TYPE_BEFORE)) header.add("GeometryTypeBefore");
        if (this.cols.contains(Col.AREA_BEFORE)) header.add("AreaBefore");
        if (this.cols.contains(Col.LENGTH_BEFORE)) header.add("LengthBefore");
        if (this.cols.contains(Col.NUMBER_OF_POINTS_BEFORE)) header.add("NumberOfPointsBefore");
        if (this.cols.contains(Col.CENTROID_BEFORE)) header.addAll(List.of("CentroidLonBefore", "CentroidLatBefore"));
        if (this.cols.contains(Col.GEOMETRY_TYPE_AFTER)) header.add("GeometryTypeAfter");
        if (this.cols.contains(Col.AREA_AFTER)) header.add("AreaAfter");
        if (this.cols.contains(Col.LENGTH_AFTER)) header.add("LengthAfter");
        if (this.cols.contains(Col.NUMBER_OF_POINTS_AFTER)) header.add("NumberOfPointsAfter");
        if (this.cols.contains(Col.CENTROID_AFTER)) header.addAll(List.of("CentroidLonAfter", "CentroidLatAfter"));
        if (this.cols.contains(Col.NUMBER_OF_TAGS_BEFORE)) header.add("NumberOfTagsBefore");
        if (this.cols.contains(Col.NUMBER_OF_TAGS_AFTER)) header.add("NumberOfTagsAfter");
        this.writeHeader(header);
    }

    public void write(OSMContribution contribution) {
        List<String> row = new ArrayList<>();
        if (this.cols.contains(Col.OSM_ID)) row.add(Long.toString(contribution.getOSHEntity().getId()));
        if (this.cols.contains(Col.CHANGESET_ID)) row.add(Long.toString(contribution.getChangesetId()));
        if (this.cols.contains(Col.CONTRIBUTOR_USER_ID)) row.add(Long.toString(contribution.getContributorUserId()));
        if (this.cols.contains(Col.TIMESTAMP)) row.add(contribution.getTimestamp().toString());
        if (this.cols.contains(Col.CONTRIBUTION_TYPE)) {
            row.add(contribution.is(ContributionType.CREATION) ? "1" : "0");
            row.add(contribution.is(ContributionType.DELETION) ? "1" : "0");
            row.add(contribution.is(ContributionType.TAG_CHANGE) ? "1" : "0");
            row.add(contribution.is(ContributionType.GEOMETRY_CHANGE) ? "1" : "0");
        }
        Geometry geometryBefore = contribution.getGeometryBefore();
        if (geometryBefore != null && !geometryBefore.isEmpty()) {
            if (this.cols.contains(Col.GEOMETRY_TYPE_BEFORE)) row.add(geometryBefore.getGeometryType());
            if (this.cols.contains(Col.AREA_BEFORE)) row.add(Double.toString(Geo.areaOf(geometryBefore)));
            if (this.cols.contains(Col.LENGTH_BEFORE)) row.add(Double.toString(Math.max(Geo.lengthOf(geometryBefore), Geo.lengthOf(geometryBefore.getBoundary()))));
            if (this.cols.contains(Col.NUMBER_OF_POINTS_BEFORE)) row.add(Integer.toString(geometryBefore.getNumPoints()));
            if (this.cols.contains(Col.CENTROID_BEFORE)) {
                row.add(Double.toString(geometryBefore.getCentroid().getX()));
                row.add(Double.toString(geometryBefore.getCentroid().getY()));
            }
        }
        Geometry geometryAfter = contribution.getGeometryBefore();
        if (geometryAfter != null && !geometryAfter.isEmpty()) {
            if (this.cols.contains(Col.GEOMETRY_TYPE_AFTER)) row.add(geometryAfter.getGeometryType());
            if (this.cols.contains(Col.AREA_AFTER)) row.add(Double.toString(Geo.areaOf(geometryAfter)));
            if (this.cols.contains(Col.LENGTH_AFTER)) row.add(Double.toString(Math.max(Geo.lengthOf(geometryAfter), Geo.lengthOf(geometryAfter.getBoundary()))));
            if (this.cols.contains(Col.NUMBER_OF_POINTS_AFTER)) row.add(Integer.toString(geometryAfter.getNumPoints()));
            if (this.cols.contains(Col.CENTROID_AFTER)) {
                row.add(Double.toString(geometryAfter.getCentroid().getX()));
                row.add(Double.toString(geometryAfter.getCentroid().getY()));
            };
        }
        if (this.cols.contains(Col.NUMBER_OF_TAGS_BEFORE) && contribution.getEntityBefore() != null) row.add(Integer.toString(contribution.getEntityBefore().getRawTags().length));
        if (this.cols.contains(Col.NUMBER_OF_TAGS_AFTER) && contribution.getEntityAfter() != null) row.add(Integer.toString(contribution.getEntityAfter().getRawTags().length));
        this.writeRow(row);
    };
}
