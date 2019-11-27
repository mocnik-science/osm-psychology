package com.osm.psychology.core;

import com.google.common.collect.Iterables;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;
import org.heigit.bigspatialdata.oshdb.util.celliterator.ContributionType;
import org.heigit.bigspatialdata.oshdb.util.geometry.Geo;
import org.heigit.bigspatialdata.oshdb.util.tagtranslator.OSMTag;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class Exporter {
    protected abstract void writeHeader(List<String> header);
    protected abstract void writeRow(List<Object> row);

    protected final None none = new None();

    private Set<Col> cols;
    private Set<Col> colsUnusedWarned;

    private Boolean useCol(Col col) {
        Boolean result = this.cols.contains(col);
        this.cols.remove(cols);
        return result;
    }

    protected class Tags {
        private List<OSMTag> tags;

        public Tags(List<OSMTag> tags) {
            this.tags = tags;
        }

        public Map<String, String> getTagsAsMap() {
            return this.tags.stream().collect(Collectors.toMap(OSMTag::getKey, OSMTag::getValue));
        }
    }
    protected class None {}

    public void init(Col[] cols, QueryType queryType) {
        this.cols = Cols.process(cols, queryType);
        List<String> header = new ArrayList<>();
        if (this.useCol(Col.OSM_ID)) header.add("OsmID");
        if (this.useCol(Col.CHANGESET_ID)) header.add("ChangesetID");
        if (this.useCol(Col.CONTRIBUTOR_USER_ID)) header.add("ContributorUserID");
        if (this.useCol(Col.TIMESTAMP)) header.add("Timestamp");
        if (this.useCol(Col.CONTRIBUTION_TYPE)) header.addAll(List.of("ContributionTypeCreation", "ContributionTypeDeletion", "ContributionTypeTagChange", "ContributionTypeGeometryChange"));
        if (this.useCol(Col.GEOMETRY_TYPE)) header.add("GeometryType");
        if (this.useCol(Col.AREA)) header.add("Area");
        if (this.useCol(Col.LENGTH)) header.add("Length");
        if (this.useCol(Col.NUMBER_OF_POINTS)) header.add("NumberOfPoints");
        if (this.useCol(Col.CENTROID)) header.addAll(List.of("CentroidLon", "CentroidLat"));
        if (this.useCol(Col.GEOMETRY_TYPE_BEFORE)) header.add("GeometryTypeBefore");
        if (this.useCol(Col.AREA_BEFORE)) header.add("AreaBefore");
        if (this.useCol(Col.LENGTH_BEFORE)) header.add("LengthBefore");
        if (this.useCol(Col.NUMBER_OF_POINTS_BEFORE)) header.add("NumberOfPointsBefore");
        if (this.useCol(Col.CENTROID_BEFORE)) header.addAll(List.of("CentroidLonBefore", "CentroidLatBefore"));
        if (this.useCol(Col.GEOMETRY_TYPE_AFTER)) header.add("GeometryTypeAfter");
        if (this.useCol(Col.AREA_AFTER)) header.add("AreaAfter");
        if (this.useCol(Col.LENGTH_AFTER)) header.add("LengthAfter");
        if (this.useCol(Col.NUMBER_OF_POINTS_AFTER)) header.add("NumberOfPointsAfter");
        if (this.useCol(Col.CENTROID_AFTER)) header.addAll(List.of("CentroidLonAfter", "CentroidLatAfter"));
        if (this.useCol(Col.TAGS)) header.add("Tags");
        if (this.useCol(Col.NUMBER_OF_TAGS)) header.add("NumberOfTags");
        if (this.useCol(Col.TAGS_BEFORE)) header.add("TagsBefore");
        if (this.useCol(Col.NUMBER_OF_TAGS_BEFORE)) header.add("NumberOfTagsBefore");
        if (this.useCol(Col.TAGS_AFTER)) header.add("TagsAfter");
        if (this.useCol(Col.NUMBER_OF_TAGS_AFTER)) header.add("NumberOfTagsAfter");

        if (this.useCol(Col.NUMBER_OF_CHANGES)) header.add("NumberOfChanges");

        this.writeHeader(header);
    }

    public void close() throws IOException {
        this.colsUnusedWarned.clear();
    }

    public void write(OSMContribution contribution) {
        List<Object> row = new ArrayList<>();
        if (this.useCol(Col.OSM_ID)) row.add(contribution.getOSHEntity().getId());
        if (this.useCol(Col.CHANGESET_ID)) row.add(contribution.getChangesetId());
        if (this.useCol(Col.CONTRIBUTOR_USER_ID)) row.add(contribution.getContributorUserId());
        if (this.useCol(Col.TIMESTAMP)) row.add(contribution.getTimestamp().toDate());
        if (this.useCol(Col.CONTRIBUTION_TYPE)) {
            row.add(contribution.is(ContributionType.CREATION) ? 1 : 0);
            row.add(contribution.is(ContributionType.DELETION) ? 1 : 0);
            row.add(contribution.is(ContributionType.TAG_CHANGE) ? 1 : 0);
            row.add(contribution.is(ContributionType.GEOMETRY_CHANGE) ? 1 : 0);
        }
        Geometry geometryBefore = contribution.getGeometryBefore();
        if (geometryBefore != null && !geometryBefore.isEmpty()) {
            if (this.useCol(Col.GEOMETRY_TYPE_BEFORE)) row.add(geometryBefore.getGeometryType());
            if (this.useCol(Col.AREA_BEFORE)) row.add(Geo.areaOf(geometryBefore));
            if (this.useCol(Col.LENGTH_BEFORE)) row.add(Math.max(Geo.lengthOf(geometryBefore), Geo.lengthOf(geometryBefore.getBoundary())));
            if (this.useCol(Col.NUMBER_OF_POINTS_BEFORE)) row.add(geometryBefore.getNumPoints());
            if (this.useCol(Col.CENTROID_BEFORE)) {
                row.add(Double.toString(geometryBefore.getCentroid().getX()));
                row.add(Double.toString(geometryBefore.getCentroid().getY()));
            }
        } else {
            if (this.useCol(Col.GEOMETRY_TYPE_BEFORE)) row.add(none);
            if (this.useCol(Col.AREA_BEFORE)) row.add(none);
            if (this.useCol(Col.LENGTH_BEFORE)) row.add(none);
            if (this.useCol(Col.NUMBER_OF_POINTS_BEFORE)) row.add(none);
            if (this.useCol(Col.CENTROID_BEFORE)) row.addAll(List.of(none, none));
        }
        Geometry geometryAfter = contribution.getGeometryAfter();
        if (geometryAfter != null && !geometryAfter.isEmpty()) {
            if (this.useCol(Col.GEOMETRY_TYPE_AFTER)) row.add(geometryAfter.getGeometryType());
            if (this.useCol(Col.AREA_AFTER)) row.add(Geo.areaOf(geometryAfter));
            if (this.useCol(Col.LENGTH_AFTER)) row.add(Math.max(Geo.lengthOf(geometryAfter), Geo.lengthOf(geometryAfter.getBoundary())));
            if (this.useCol(Col.NUMBER_OF_POINTS_AFTER)) row.add(geometryAfter.getNumPoints());
            if (this.useCol(Col.CENTROID_AFTER)) {
                row.add(Double.toString(geometryAfter.getCentroid().getX()));
                row.add(Double.toString(geometryAfter.getCentroid().getY()));
            }
        } else {
            if (this.useCol(Col.GEOMETRY_TYPE_AFTER)) row.add(none);
            if (this.useCol(Col.AREA_AFTER)) row.add(none);
            if (this.useCol(Col.LENGTH_AFTER)) row.add(none);
            if (this.useCol(Col.NUMBER_OF_POINTS_AFTER)) row.add(none);
            if (this.useCol(Col.CENTROID_AFTER)) row.addAll(List.of(none, none));
        }
        if (this.useCol(Col.TAGS_BEFORE)) {
            if (contribution.getEntityBefore() != null) {
                List<OSMTag> tags = StreamSupport
                        .stream(contribution.getEntityBefore().getTags().spliterator(), true)
                        .map(Data.getTagTranslator()::getOSMTagOf)
                        .collect(Collectors.toList());
                row.add(new Tags(tags));
            } else row.add(none);
        }
        if (this.useCol(Col.NUMBER_OF_TAGS_BEFORE)) {
            if (contribution.getEntityBefore() != null) row.add(contribution.getEntityBefore().getRawTags().length);
            else row.add(none);
        }
        if (this.useCol(Col.TAGS_AFTER)) {
            if (contribution.getEntityAfter() != null) {
                List<OSMTag> tags = StreamSupport
                        .stream(contribution.getEntityAfter().getTags().spliterator(), true)
                        .map(Data.getTagTranslator()::getOSMTagOf)
                        .collect(Collectors.toList());
                row.add(new Tags(tags));
            } else row.add(none);
        }
        if (this.useCol(Col.NUMBER_OF_TAGS_AFTER)) {
            if (contribution.getEntityAfter() != null) row.add(contribution.getEntityAfter().getRawTags().length);
            else row.add(none);
        }
        this.writeRow(row);
        for (Col col : this.cols) if (!this.colsUnusedWarned.contains(col)) {
            System.out.println("WARNING  Could not output the following column type: " + col);
            this.colsUnusedWarned.add(col);
        }
    }

    public void write(OSMEntitySnapshot entity) {
        List<Object> row = new ArrayList<>();
        if (this.useCol(Col.OSM_ID)) row.add(entity.getOSHEntity().getId());
        if (this.useCol(Col.TIMESTAMP)) row.add(entity.getTimestamp().toDate());
        if (this.useCol(Col.NUMBER_OF_CHANGES)) row.add(Iterables.size(entity.getOSHEntity().getVersions()));
        Geometry geometry = entity.getGeometry();
        if (geometry != null && !geometry.isEmpty()) {
            if (this.useCol(Col.GEOMETRY_TYPE)) row.add(geometry.getGeometryType());
            if (this.useCol(Col.AREA)) row.add(Geo.areaOf(geometry));
            if (this.useCol(Col.LENGTH)) row.add(Math.max(Geo.lengthOf(geometry), Geo.lengthOf(geometry.getBoundary())));
            if (this.useCol(Col.NUMBER_OF_POINTS)) row.add(geometry.getNumPoints());
            if (this.useCol(Col.CENTROID)) {
                row.add(Double.toString(geometry.getCentroid().getX()));
                row.add(Double.toString(geometry.getCentroid().getY()));
            }
        } else {
            if (this.useCol(Col.GEOMETRY_TYPE)) row.add(none);
            if (this.useCol(Col.AREA)) row.add(none);
            if (this.useCol(Col.LENGTH)) row.add(none);
            if (this.useCol(Col.NUMBER_OF_POINTS)) row.add(none);
            if (this.useCol(Col.CENTROID)) row.addAll(List.of(none, none));
        }
        if (this.useCol(Col.TAGS)) {
            if (entity.getEntity() != null) {
                List<OSMTag> tags = StreamSupport
                        .stream(entity.getEntity().getTags().spliterator(), true)
                        .map(Data.getTagTranslator()::getOSMTagOf)
                        .collect(Collectors.toList());
                row.add(new Tags(tags));
            } else row.add(none);
        }
        if (this.useCol(Col.NUMBER_OF_TAGS)) {
            if (entity.getEntity() != null) row.add(entity.getEntity().getRawTags().length);
            else row.add(none);
        }
        this.writeRow(row);
        for (Col col : this.cols) if (!this.colsUnusedWarned.contains(col)) {
            System.out.println("WARNING  Could not output the following column type: " + col);
            this.colsUnusedWarned.add(col);
        }
    }
}
