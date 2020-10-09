
[back to readme](../../../)

# Overview over the column options

| code                         | availability                 | description                                                  |
| ---------------------------- | ---------------------------- | :----------------------------------------------------------- |
| Col.OSM_ID                   | entities<br />contributions  | Unique ID, composed from OSM_TYPE and OBJECT_ID*             |
| Col.OBJECT_ID                | entities<br />contributions  | Object ID, unique per OSM_TYPE                               |
| Col.CHANGESET_ID             | contributions                | ID of the session in which it was changed                    |
| Col.CONTRIBUTOR_USER_ID      | contributions                | ID of the contributor contributing the change                |
| Col.TIMESTAMP                | entities<br />contributions  | Entities: Date and time when the last change (current entry) was made<br />Contributions: date and time when the change was made |
| Col.OSM_TYPE                 | entities<br />contributions  | OSM type of the object (node, way, or relation)              |
| Col.CONTRIBUTION_TYPE        | contributions                | Type of the contribution, i.e. Creation, Deletion, Tag Change and / or geometry change |
| Col.NUMBER_OF_CHANGES        | entities                     | Number of changes made to the object until the date of the entity snapshot based on the OpenStreetMap version number** |
| Col.NUMBER_OF_CHANGES_BEFORE | contributions                | Number of changes made to the object before the change based on the OpenStreetMap version number** |
| Col.NUMBER_OF_CHANGES_AFTER  | contributions                | Number of changes made to the object after the change based on the OpenStreetMap version number** |
| Col.GEOMETRY_TYPE_BEFORE     | contributions                | Geometry type of the object before the change                |
| Col.AREA_BEFORE              | contributions                | Area of the object before the change                         |
| Col.LENGTH_BEFORE            | contributions                | Length of the object before the change***                    |
| Col.NUMBER_OF_POINTS_BEFORE  | contributions                | Number of edges in the geometric figure before the change    |
| Col.CENTROID_BEFORE          | contributions                | Longitude and latitude of the object's Euclidean centroid before the change |
| Col.TAGS_BEFORE              | contributions                | List of object tags before the change                        |
| Col.NUMBER_OF_TAGS_BEFORE    | contributions                | Number of tags before the change                             |
| Col.GEOMETRY_TYPE_AFTER      | contributions                | Geometry type of the object after the change                 |
| Col.AREA_AFTER               | contributions                | Area of the object after the change                          |
| Col.LENGTH_AFTER             | contributions                | Length of the object after the change***                     |
| Col.NUMBER_OF_POINTS_AFTER   | contributions                | Number of edges in the geometric figure before the change    |
| Col.CENTROID_AFTER           | contributions                | Longitude and latitude of the object's Euclidean centroid after the change |
| Col.TAGS_AFTER               | contributions                | List of object tags after the change                         |
| Col.NUMBER_OF_TAGS_AFTER     | contributions                | Number of tags after the change                              |
| Col.AREA                     | entities<br />contributions  | Entities: Area of the object<br />Contributions: Col.AREA_BEFORE, and Col.AREA_AFTER |
| Col.LENGTH                   | entities<br />contributions  | Entities: Length of the object<br />Contributions: Col.LENGTH_BEFORE, and Col.LENGTH_AFTER |
| Col.NUMBER_OF_POINTS         | entities<br />contributions  | Entities: Number of edges in the geometric figure<br />Contributions: Col.NUMBER_OF_POINTS_BEFORE, and Col.NUMBER_OF_POINTS_AFTER |
| Col.CENTROID                 | entities<br />contribution   | Entities: Longitude and latitude of the object's Euclidean centroid<br />Contributions: Col.CENTROID_BEFORE, and Col.CENTROID_AFTER |
| Col.TAGS                     | entities<br />contributions  | Entities: List of object tags<br />Contributions: Col.TAGS_BEFORE, and Col.TAGS_AFTER |
| Col.NUMBER_OF_TAGS           | entities<br />contributions  | Entities: Number of tags<br />Contributions: Col.NUMBER_OF_TAGS_BEFORE, and Col.NUMBER_OF_TAGS_AFTER |
| Col.ALL                      | entities <br />contributions | All columns available for either entities or contributions   |
| Col.BASIC_INFORMATION        | entities<br />contributions  | Entities: OSM_ID, OBJECT_ID, TIMESTAMP, OSM_TYPE, and NUMBER_OF_CHANGES<br />Contributions: OSM_ID, OBJECT_ID, CHANGESET_ID, CONTRIBUTOR_USER_ID, TIMESTAMP, OSM_TYPE, and CONTRIBUTION_TYPE |
| Col.BEFORE                   | contributions                | All information of the object before the change              |
| Col.AFTER                    | contributions                | All information of the object after the change               |
| Col.GEOMETRY_INFORMATION     | entities<br />contributions  | Entities: all geometric information<br />Contributions: all geometric information before and after the change |
| Col.GEOMETRY_BEFORE          | contributions                | All geometric information of the object before the change    |
| Col.GEOMETRY_AFTER           | contributions                | All geometric information of the object after the change     |
| Col.TAG_INFORMATION          | entities<br />contributions  | Entities: all tag information<br />Contributions: all tag information before and after the change |

*IDs are only unique per OSM type. Thus, the OSM_ID reflects an unique ID, while OBJECT_ID can occur several times for objects of different OSM_TYPE. Objects can be traced in OpenStreetMap using the OSM_ID with openstreetmap.org/OSM_ID.

**the Number of Changes reflects the object version as given in the OpenStreetMap data base. This may not reflect the number of entries for an object in the contribution view since contributions are counted differently.

***for buildings and other polygonal objects the length describes the circumference of the object, for roads the length is exported