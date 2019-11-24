
[back to readme](../../../)

# Overview over the column options

| code                        | availability                 | description                                                  |
| --------------------------- | ---------------------------- | :----------------------------------------------------------- |
| Col.OSM_ID                  | entities<br />contributions  | ID of the object, e.g. building or street                    |
| Col.CHANGESET_ID            | contributions                | ID of the session in which it was changed                    |
| Col.CONTRIBUTOR_USER_ID     | contributions                | ID of the contributor contributing the change                |
| Col.TIMESTAMP               | entities<br />contributions  | Entities: Date and time when the last change (current entry) was made<br />Contributions: date and time when the change was made |
| Col.CONTRIBUTION_TYPE       | contributions                | Type of the contribution, i.e. Creation, Deletion, Tag Change and / or geometry change |
| Col.NUMBER_OF_CHANGES       | entities                     | Number of changes made to the object until the date of the entity snapshot |
| Col.GEOMETRY_TYPE_BEFORE    | contributions                | Geometry type of the object before the change                |
| Col.AREA_BEFORE             | contributions                | Area of the object before the change                         |
| Col.LENGTH_BEFORE           | contributions                | Length of the object before the change*                      |
| Col.NUMBER_OF_POINTS_BEFORE | contributions                | Number of edges in the geometric figure before the change    |
| Col.CENTROID_BEFORE         | contributions                | Longitude and latitude of the object's Euclidean centroid before the change |
| Col.TAGS_BEFORE             | contributions                | List of object tags before the change                        |
| Col.NUMBER_OF_TAGS_BEFORE   | contributions                | Number of tags before the change                             |
| Col.GEOMETRY_TYPE_AFTER     | contributions                | Geometry type of the object after the change                 |
| Col.AREA_AFTER              | contributions                | Area of the object after the change                          |
| Col.LENGTH_AFTER            | contributions                | Length of the object after the change                        |
| Col.NUMBER_OF_POINTS_AFTER  | contributions                | Number of edges in the geometric figure before the change    |
| Col.CENTROID_AFTER          | contributions                | Longitude and latitude of the object's Euclidean centroid after the change |
| Col.TAGS_AFTER              | contributions                | List of object tags after the change                         |
| Col.NUMBER_OF_TAGS_AFTER    | contributions                | Number of tags after the change                              |
| Col.AREA                    | entities<br />contributions  | Entities: Area of the object<br />Contributions: Col.AREA_BEFORE, and Col.AREA_AFTER |
| Col.LENGTH                  | entities<br />contributions  | Entities: Length of the object<br />Contributions: Col.LENGTH_BEFORE, and Col.LENGTH_AFTER |
| Col.NUMBER_OF_POINTS        | entities<br />contributions  | Entities: Number of edges in the geometric figure<br />Contributions: Col.NUMBER_OF_POINTS_BEFORE, and Col.NUMBER_OF_POINTS_AFTER |
| Col.CENTROID                | entities<br />contribution   | Entities: Longitude and latitude of the object's Euclidean centroid<br />Contributions: Col.CENTROID_BEFORE, and Col.CENTROID_AFTER |
| Col.TAGS                    | entities<br />contributions  | Entities: List of object tags<br />Contributions: Col.TAGS_BEFORE, and Col.TAGS_AFTER |
| Col.NUMBER_OF_TAGS          | entities<br />contributions  | Entities: Number of tags<br />Contributions: Col.NUMBER_OF_TAGS_BEFORE, and Col.NUMBER_OF_TAGS_AFTER |
| Col.ALL                     | entities <br />contributions | All columns available for either entities or contributions   |
| Col.BASIC_INFORMATION       | entities<br />contributions  | Entities: OSM_ID, TIMESTAMP, and NUMBER_OF_CHANGES<br />Contributions: OSM_ID, CHANGESET_ID, CONTRIBUTOR_USER_ID, TIMESTAMP, and CONTRIBUTION_TYPE |
| Col.BEFORE                  | contributions                | All information of the object before the change              |
| Col.AFTER                   | contributions                | All information of the object after the change               |
| Col.GEOMETRY_INFORMATION    | entities<br />contributions  | Entities: all geometric information<br />Contributions: all geometric information before and after the change |
| Col.GEOMETRY_BEFORE         | contributions                | All geometric information of the object before the change    |
| Col.GEOMETRY_AFTER          | contributions                | All geometric information of the object after the change     |
| Col.TAG_INFORMATION         | entities<br />contributions  | Entities: all tag information<br />Contributions: all tag information before and after the change |

*for buildings and other polygonal objects the length describes the circumference of the object, for roads the length is exported