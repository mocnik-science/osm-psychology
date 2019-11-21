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
    public void close() throws IOException {}

    protected final None none = new None();

    private Set<ColContr> cols;

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

    public void init(ColContr[] cols) {
        this.cols = ColsContr.process(cols);
        List<String> header = new ArrayList<>();
        if (this.cols.contains(ColContr.OSM_ID)) header.add("OsmID");
        if (this.cols.contains(ColContr.CHANGESET_ID)) header.add("ChangesetID");
        if (this.cols.contains(ColContr.CONTRIBUTOR_USER_ID)) header.add("ContributorUserID");
        if (this.cols.contains(ColContr.TIMESTAMP)) header.add("Timestamp");
        if (this.cols.contains(ColContr.CONTRIBUTION_TYPE)) header.addAll(List.of("ContributionTypeCreation", "ContributionTypeDeletion", "ContributionTypeTagChange", "ContributionTypeGeometryChange"));
        if (this.cols.contains(ColContr.GEOMETRY_TYPE_BEFORE)) header.add("GeometryTypeBefore");
        if (this.cols.contains(ColContr.AREA_BEFORE)) header.add("AreaBefore");
        if (this.cols.contains(ColContr.LENGTH_BEFORE)) header.add("LengthBefore");
        if (this.cols.contains(ColContr.NUMBER_OF_POINTS_BEFORE)) header.add("NumberOfPointsBefore");
        if (this.cols.contains(ColContr.CENTROID_BEFORE)) header.addAll(List.of("CentroidLonBefore", "CentroidLatBefore"));
        if (this.cols.contains(ColContr.GEOMETRY_TYPE_AFTER)) header.add("GeometryTypeAfter");
        if (this.cols.contains(ColContr.AREA_AFTER)) header.add("AreaAfter");
        if (this.cols.contains(ColContr.LENGTH_AFTER)) header.add("LengthAfter");
        if (this.cols.contains(ColContr.NUMBER_OF_POINTS_AFTER)) header.add("NumberOfPointsAfter");
        if (this.cols.contains(ColContr.CENTROID_AFTER)) header.addAll(List.of("CentroidLonAfter", "CentroidLatAfter"));
        if (this.cols.contains(ColContr.TAGS_BEFORE)) header.add("TagsBefore");
        if (this.cols.contains(ColContr.NUMBER_OF_TAGS_BEFORE)) header.add("NumberOfTagsBefore");
        if (this.cols.contains(ColContr.TAGS_AFTER)) header.add("TagsAfter");
        if (this.cols.contains(ColContr.NUMBER_OF_TAGS_AFTER)) header.add("NumberOfTagsAfter");
        this.writeHeader(header);
    }

    public void init(ColSnap[] cols) {
        this.cols = ColsSnap.process(cols);
        List<String> header = new ArrayList<>();
        if (this.cols.contains(ColSnap.OSM_ID)) header.add("OsmID");
        if (this.cols.contains(ColSnap.TIMESTAMP)) header.add("Timestamp");
        if (this.cols.contains(ColSnap.NUMBER_OF_CHANGES)) header.add("NumberOfChanges");
        if (this.cols.contains(ColSnap.GEOMETRY_TYPE)) header.add("GeometryType");
        if (this.cols.contains(ColSnap.AREA)) header.add("Area");
        if (this.cols.contains(ColSnap.LENGTH)) header.add("Length");
        if (this.cols.contains(ColSnap.NUMBER_OF_POINTS)) header.add("NumberOfPoints");
        if (this.cols.contains(ColSnap.CENTROID)) header.addAll(List.of("CentroidLon", "CentroidLat"));
        if (this.cols.contains(ColSnap.TAGS)) header.add("Tags");
        if (this.cols.contains(ColSnap.NUMBER_OF_TAGS)) header.add("NumberOfTags");
        this.writeHeader(header);
    }

    public void write(OSMContribution contribution) {
        List<Object> row = new ArrayList<>();
        if (this.cols.contains(ColContr.OSM_ID)) row.add(contribution.getOSHEntity().getId());
        if (this.cols.contains(ColContr.CHANGESET_ID)) row.add(contribution.getChangesetId());
        if (this.cols.contains(ColContr.CONTRIBUTOR_USER_ID)) row.add(contribution.getContributorUserId());
        if (this.cols.contains(ColContr.TIMESTAMP)) row.add(contribution.getTimestamp().toDate());
        if (this.cols.contains(ColContr.CONTRIBUTION_TYPE)) {
            row.add(contribution.is(ContributionType.CREATION) ? 1 : 0);
            row.add(contribution.is(ContributionType.DELETION) ? 1 : 0);
            row.add(contribution.is(ContributionType.TAG_CHANGE) ? 1 : 0);
            row.add(contribution.is(ContributionType.GEOMETRY_CHANGE) ? 1 : 0);
        }
        Geometry geometryBefore = contribution.getGeometryBefore();
        if (geometryBefore != null && !geometryBefore.isEmpty()) {
            if (this.cols.contains(ColContr.GEOMETRY_TYPE_BEFORE)) row.add(geometryBefore.getGeometryType());
            if (this.cols.contains(ColContr.AREA_BEFORE)) row.add(Geo.areaOf(geometryBefore));
            if (this.cols.contains(ColContr.LENGTH_BEFORE)) row.add(Math.max(Geo.lengthOf(geometryBefore), Geo.lengthOf(geometryBefore.getBoundary())));
            if (this.cols.contains(ColContr.NUMBER_OF_POINTS_BEFORE)) row.add(geometryBefore.getNumPoints());
            if (this.cols.contains(ColContr.CENTROID_BEFORE)) {
                row.add(Double.toString(geometryBefore.getCentroid().getX()));
                row.add(Double.toString(geometryBefore.getCentroid().getY()));
            }
        } else {
            if (this.cols.contains(ColContr.GEOMETRY_TYPE_BEFORE)) row.add(none);
            if (this.cols.contains(ColContr.AREA_BEFORE)) row.add(none);
            if (this.cols.contains(ColContr.LENGTH_BEFORE)) row.add(none);
            if (this.cols.contains(ColContr.NUMBER_OF_POINTS_BEFORE)) row.add(none);
            if (this.cols.contains(ColContr.CENTROID_BEFORE)) row.addAll(List.of(none, none));
        }
        Geometry geometryAfter = contribution.getGeometryAfter();
        if (geometryAfter != null && !geometryAfter.isEmpty()) {
            if (this.cols.contains(ColContr.GEOMETRY_TYPE_AFTER)) row.add(geometryAfter.getGeometryType());
            if (this.cols.contains(ColContr.AREA_AFTER)) row.add(Geo.areaOf(geometryAfter));
            if (this.cols.contains(ColContr.LENGTH_AFTER)) row.add(Math.max(Geo.lengthOf(geometryAfter), Geo.lengthOf(geometryAfter.getBoundary())));
            if (this.cols.contains(ColContr.NUMBER_OF_POINTS_AFTER)) row.add(geometryAfter.getNumPoints());
            if (this.cols.contains(ColContr.CENTROID_AFTER)) {
                row.add(Double.toString(geometryAfter.getCentroid().getX()));
                row.add(Double.toString(geometryAfter.getCentroid().getY()));
            }
        } else {
            if (this.cols.contains(ColContr.GEOMETRY_TYPE_AFTER)) row.add(none);
            if (this.cols.contains(ColContr.AREA_AFTER)) row.add(none);
            if (this.cols.contains(ColContr.LENGTH_AFTER)) row.add(none);
            if (this.cols.contains(ColContr.NUMBER_OF_POINTS_AFTER)) row.add(none);
            if (this.cols.contains(ColContr.CENTROID_AFTER)) row.addAll(List.of(none, none));
        }
        if (this.cols.contains(ColContr.TAGS_BEFORE)) {
            if (contribution.getEntityBefore() != null) {
                List<OSMTag> tags = StreamSupport
                        .stream(contribution.getEntityBefore().getTags().spliterator(), true)
                        .map(Data.getTagTranslator()::getOSMTagOf)
                        .collect(Collectors.toList());
                row.add(new Tags(tags));
            } else row.add(none);
        }
        if (this.cols.contains(ColContr.NUMBER_OF_TAGS_BEFORE)) {
            if (contribution.getEntityBefore() != null) row.add(contribution.getEntityBefore().getRawTags().length);
            else row.add(none);
        }
        if (this.cols.contains(ColContr.TAGS_AFTER)) {
            if (contribution.getEntityAfter() != null) {
                List<OSMTag> tags = StreamSupport
                        .stream(contribution.getEntityAfter().getTags().spliterator(), true)
                        .map(Data.getTagTranslator()::getOSMTagOf)
                        .collect(Collectors.toList());
                row.add(new Tags(tags));
            } else row.add(none);
        }
        if (this.cols.contains(ColContr.NUMBER_OF_TAGS_AFTER)) {
            if (contribution.getEntityAfter() != null) row.add(contribution.getEntityAfter().getRawTags().length);
            else row.add(none);
        }
        this.writeRow(row);
    }

    public void write(OSMEntitySnapshot entity) {
        List<Object> row = new ArrayList<>();
        if (this.cols.contains(ColSnap.OSM_ID)) row.add(entity.getOSHEntity().getId());
        if (this.cols.contains(ColSnap.TIMESTAMP)) row.add(entity.getTimestamp().toDate());
        if (this.cols.contains(ColSnap.NUMBER_OF_CHANGES)) row.add(Iterables.size(entity.getOSHEntity().getVersions()));

        Geometry geometry = entity.getGeometry();
        if (geometry != null && !geometry.isEmpty()) {
            if (this.cols.contains(ColSnap.GEOMETRY_TYPE)) row.add(geometry.getGeometryType());
            if (this.cols.contains(ColSnap.AREA)) row.add(Geo.areaOf(geometry));
            if (this.cols.contains(ColSnap.LENGTH)) row.add(Math.max(Geo.lengthOf(geometry), Geo.lengthOf(geometry.getBoundary())));
            if (this.cols.contains(ColSnap.NUMBER_OF_POINTS)) row.add(geometry.getNumPoints());
            if (this.cols.contains(ColSnap.CENTROID)) {
                row.add(Double.toString(geometry.getCentroid().getX()));
                row.add(Double.toString(geometry.getCentroid().getY()));
            }
        } else {
            if (this.cols.contains(ColSnap.GEOMETRY_TYPE)) row.add(none);
            if (this.cols.contains(ColSnap.AREA)) row.add(none);
            if (this.cols.contains(ColSnap.LENGTH)) row.add(none);
            if (this.cols.contains(ColSnap.NUMBER_OF_POINTS)) row.add(none);
            if (this.cols.contains(ColSnap.CENTROID)) row.addAll(List.of(none, none));
        }

        if (this.cols.contains(ColSnap.TAGS){
            if (entity.getEntity() != null) {
                List<OSMTag> tags = StreamSupport
                        .stream(entity.getEntity().getTags().spliterator(), true)
                        .map(Data.getTagTranslator()::getOSMTagOf)
                        .collect(Collectors.toList());
                row.add(new Tags(tags));
            } else row.add(none);
        }

        if (this.cols.contains(ColSnap.NUMBER_OF_TAGS) {
            if (entity.getEntity() != null) row.add(entity.getEntity().getRawTags().length);
            else row.add(none);
        }

        this.writeRow(row);
    }
}
