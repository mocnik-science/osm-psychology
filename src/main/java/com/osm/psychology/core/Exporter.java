package com.osm.psychology.core;

import com.osm.psychology.tools.GeometryTools;
import org.heigit.bigspatialdata.oshdb.api.object.OSMContribution;
import org.heigit.bigspatialdata.oshdb.api.object.OSMEntitySnapshot;
import org.heigit.bigspatialdata.oshdb.osh.OSHEntity;
import org.heigit.bigspatialdata.oshdb.osm.OSMEntity;
import org.heigit.bigspatialdata.oshdb.osm.OSMRelation;
import org.heigit.bigspatialdata.oshdb.osm.OSMType;
import org.heigit.bigspatialdata.oshdb.util.celliterator.ContributionType;
import org.heigit.bigspatialdata.oshdb.util.geometry.Geo;
import org.heigit.bigspatialdata.oshdb.util.tagtranslator.OSMTag;
import org.locationtech.jts.geom.Geometry;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class Exporter {
    protected abstract void writeHeader(List<String> header);
    protected abstract void writeRow(List<Object> row);

    protected final None none = new None();

    private Set<Col> cols;
    private Set<Col> colsOriginal;
    private Set<Col> colsUnusedWarned = new HashSet();

    private Boolean initCol(Col col) {
        return this.initCol(col, null, null);
    }
    private Boolean initCol(Col col, QueryType queryType, QueryType queryTypeRequired) {
        if (queryTypeRequired != null && queryType != queryTypeRequired) return false;
        Boolean result = this.colsOriginal.contains(col) && !this.cols.contains(col);
        if (result) this.cols.add(col);
        return result;
    }
    private Boolean useCol(Col col) {
        Boolean result = this.cols.contains(col);
        this.cols.remove(col);
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
        this.colsOriginal = Cols.process(cols, queryType);
        this.cols = new HashSet();
        List<String> header = new ArrayList<>();
        if (this.initCol(Col.OSM_ID)) header.add("OsmID");
        if (this.initCol(Col.ELEMENT_ID)) header.add("ElementID");
        if (this.initCol(Col.CHANGESET_ID, queryType, QueryType.CONTRIBUTION)) header.add("ChangesetID");
        if (this.initCol(Col.CONTRIBUTOR_USER_ID, queryType, QueryType.CONTRIBUTION)) header.add("ContributorUserID");
        if (this.initCol(Col.TIMESTAMP)) header.add("Timestamp");
        if (this.initCol(Col.OSM_TYPE)) header.add("OsmType");
        if (this.initCol(Col.NUMBER_OF_CHANGES, queryType, QueryType.ENTITY)) header.add("NumberOfChanges");
        if (this.initCol(Col.NUMBER_OF_CHANGES_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("NumberOfChangesBefore");
        if (this.initCol(Col.NUMBER_OF_CHANGES_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("NumberOfChangesAfter");
        if (this.initCol(Col.CONTRIBUTION_TYPE, queryType, QueryType.CONTRIBUTION)) header.addAll(List.of("ContributionTypeCreation", "ContributionTypeDeletion", "ContributionTypeTagChange", "ContributionTypeGeometryChange"));
        if (this.initCol(Col.GEOMETRY_TYPE, queryType, QueryType.ENTITY)) header.add("GeometryType");
        if (this.initCol(Col.AREA, queryType, QueryType.ENTITY)) header.add("Area");
        if (this.initCol(Col.LENGTH, queryType, QueryType.ENTITY)) header.add("Length");
        if (this.initCol(Col.NUMBER_OF_POINTS, queryType, QueryType.ENTITY)) header.add("NumberOfPoints");
        if (this.initCol(Col.CENTROID, queryType, QueryType.ENTITY)) header.addAll(List.of("CentroidLon", "CentroidLat"));
        if (this.initCol(Col.GEOMETRY_TYPE_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("GeometryTypeBefore");
        if (this.initCol(Col.AREA_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("AreaBefore");
        if (this.initCol(Col.LENGTH_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("LengthBefore");
        if (this.initCol(Col.NUMBER_OF_POINTS_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("NumberOfPointsBefore");
        if (this.initCol(Col.CENTROID_BEFORE, queryType, QueryType.CONTRIBUTION)) header.addAll(List.of("CentroidLonBefore", "CentroidLatBefore"));
        if (this.initCol(Col.GEOMETRY_TYPE_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("GeometryTypeAfter");
        if (this.initCol(Col.AREA_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("AreaAfter");
        if (this.initCol(Col.LENGTH_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("LengthAfter");
        if (this.initCol(Col.NUMBER_OF_POINTS_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("NumberOfPointsAfter");
        if (this.initCol(Col.CENTROID_AFTER, queryType, QueryType.CONTRIBUTION)) header.addAll(List.of("CentroidLonAfter", "CentroidLatAfter"));
        if (this.initCol(Col.TAGS, queryType, QueryType.ENTITY)) header.add("Tags");
        if (this.initCol(Col.NUMBER_OF_TAGS, queryType, QueryType.ENTITY)) header.add("NumberOfTags");
        if (this.initCol(Col.TAGS_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("TagsBefore");
        if (this.initCol(Col.NUMBER_OF_TAGS_BEFORE, queryType, QueryType.CONTRIBUTION)) header.add("NumberOfTagsBefore");
        if (this.initCol(Col.TAGS_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("TagsAfter");
        if (this.initCol(Col.NUMBER_OF_TAGS_AFTER, queryType, QueryType.CONTRIBUTION)) header.add("NumberOfTagsAfter");
        this.writeHeader(header);
        this.colsOriginal = this.cols;
    }

    public void close() throws IOException {
        this.colsUnusedWarned.clear();
    }

    public void write(OSMEntitySnapshot entity) {
        try {
            this.cols = new HashSet(this.colsOriginal);
            List<Object> row = new ArrayList<>();
            OSMEntity object = entity.getEntity();
            if (this.useCol(Col.OSM_ID)) row.add(object.getType().toString().toLowerCase() + "/" + object.getId());
            if (this.useCol(Col.ELEMENT_ID)) row.add(object.getId());
            if (this.useCol(Col.TIMESTAMP)) row.add(object.getTimestamp().toDate());
            if (this.useCol(Col.OSM_TYPE)) row.add(object.getType().name());
            if (this.useCol(Col.NUMBER_OF_CHANGES)) row.add(object.getVersion());

            Geometry geometry = entity.getGeometry();
            if (geometry != null && !geometry.isEmpty()) {
                if (this.useCol(Col.GEOMETRY_TYPE)) row.add(geometry.getGeometryType());
                if (this.useCol(Col.AREA)) row.add(GeometryTools.areaOf(geometry));
                if (this.useCol(Col.LENGTH)) row.add(GeometryTools.lengthOf(geometry));
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
                if (entity.getEntity() != null) row.add(entity.getEntity().getRawTags().length / 2);
                else row.add(none);
            }
            this.writeRow(row);
            for (Col col : this.cols) if (!this.colsUnusedWarned.contains(col)) {
                System.out.println("[WARNING]  Could not output the following column type: " + col);
                this.colsUnusedWarned.add(col);
            }
        } catch (Exception e) {
            System.out.println("[WARNING]  An error occurred while creating the output for the following OSM entity: " + entity.getEntity().getType().name() + " " + entity.getEntity().getId());
        }
    }

    public void write(OSMContribution contribution) {
        try {
            this.cols = new HashSet(this.colsOriginal);
            List<Object> row = new ArrayList<>();
            OSHEntity object = contribution.getOSHEntity();
            if (this.useCol(Col.OSM_ID)) row.add(object.getType().toString().toLowerCase() + "/" + contribution.getOSHEntity().getId());
            if (this.useCol(Col.ELEMENT_ID)) row.add(object.getId());
            if (this.useCol(Col.CHANGESET_ID)) row.add(contribution.getChangesetId());
            if (this.useCol(Col.CONTRIBUTOR_USER_ID)) row.add(contribution.getContributorUserId());
            if (this.useCol(Col.TIMESTAMP)) row.add(contribution.getTimestamp().toDate());
            if (this.useCol(Col.OSM_TYPE)) row.add(object.getType().name());

            if(contribution.getEntityBefore() != null){
                if( this.useCol(Col.NUMBER_OF_CHANGES_BEFORE)) row.add(contribution.getEntityBefore().getVersion());
            } else {
                if( this.useCol(Col.NUMBER_OF_CHANGES_BEFORE)) row.add(none);
            }
            
            if( this.useCol(Col.NUMBER_OF_CHANGES_AFTER)) row.add(contribution.getEntityAfter().getVersion());
            if (this.useCol(Col.CONTRIBUTION_TYPE)) {
                row.add(contribution.is(ContributionType.CREATION) ? 1 : 0);
                row.add(contribution.is(ContributionType.DELETION) ? 1 : 0);
                row.add(contribution.is(ContributionType.TAG_CHANGE) ? 1 : 0);
                row.add(contribution.is(ContributionType.GEOMETRY_CHANGE) ? 1 : 0);
            }
            Geometry geometryBefore = contribution.getGeometryBefore();
            if (geometryBefore != null && !geometryBefore.isEmpty()) {
                if (this.useCol(Col.GEOMETRY_TYPE_BEFORE)) row.add(geometryBefore.getGeometryType());
                if (this.useCol(Col.AREA_BEFORE)) row.add(GeometryTools.areaOf(geometryBefore));
                if (this.useCol(Col.LENGTH_BEFORE)) row.add(GeometryTools.lengthOf(geometryBefore));
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
                if (this.useCol(Col.AREA_AFTER)) row.add(GeometryTools.areaOf(geometryAfter));
                if (this.useCol(Col.LENGTH_AFTER)) row.add(GeometryTools.lengthOf(geometryAfter));
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
                if (contribution.getEntityBefore() != null) row.add(contribution.getEntityBefore().getRawTags().length / 2);
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
                if (contribution.getEntityAfter() != null) row.add(contribution.getEntityAfter().getRawTags().length / 2);
                else row.add(none);
            }
            this.writeRow(row);
            for (Col col : this.cols) if (!this.colsUnusedWarned.contains(col)) {
                System.out.println("[WARNING]  Could not output the following column type: " + col);
                this.colsUnusedWarned.add(col);
            }
        } catch (Exception e) {
            System.out.println("[WARNING]  An error occurred while creating the output for the following changeset: " + contribution.getChangesetId());
        }
    }
}
