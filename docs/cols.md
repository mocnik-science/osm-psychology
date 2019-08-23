
[back to readme](../../../)

# Overview over the column options

| Code        			     | Description                                                                          |
| -------------------------- |:-------------------------------------------------------------------------------------|                                                             |
| ALL						 | All tags available                                                                   |
| BASIC_INFORMATION 		 | Includes OSM_ID, CHANGESET_ID, CONTRIBUTOR_USER_ID, TIMESTAMP, and CONTRIBUTION_TYPE |
| OSM_ID                     | ID of the object, e.g. building or street                                            |
| CHANGESET_ID               | ID of the session in which it was changed                                            |
| CONTRIBUTOR_USER_ID        | ID of the contributor contributing the change                                        |
| TIMESTAMP                  | Date and time when the change was made                                               |
| CONTRIBUTION_TYPE          | Type of the contribution, Creation, Deletion, Tag Change and / or geometry change    |
| BEFORE                     | All information of the object before the change                                      |
| GEOMETRY_BEFORE            | All gemotric information of the object before the change                             |
| GEOMETRY_TYPE_BEFORE       | Geometry type of the object before the change                                        |
| AREA_BEFORE                | Area of the object before the change                                                 |
| LENGTH_BEFORE              | Length of the object before the change*                                              |
| NUMBER_OF_POINTS_BEFORE    | Number of edges in the geometric figure before the change                            |
| CENTROID_BEFORE            | Longitude and latitude of the object's euklidean centroid before the change          |
| TAGS_BEFORE                | List of object tags before the change                                                |
| NUMBER_OF_TAGS_BEFORE      | Number of tags before the change                                                     |
| AFTER                      | All information of the object after the change                                       |
| GEOMETRY_AFTER       		 | All gemotric information of the object after the change                              |
| GEOMETRY_TYPE_AFTER        | Geometry type of the object after the change                                         |
| AREA_AFTER                 | Area of the object after the change                                                  |
| LENGTH_AFTER               | Length of the object after the change                                                |
| NUMBER_OF_POINTS_AFTER     | Number of edges in the geometric figure before the change                            |
| CENTROID_AFTER             | Longitude and latitude of the object's euklidean centroid after the change           |
| TAGS_AFTER                 | List of object tags after the change                                                 |
| NUMBER_OF_TAGS_AFTER       | Number of tags after the change                                                      |
| GEOMETRY                   | All gemotric information before and after the change                                 |
| TAGS                       | All tag information before and after the change                                      |

*for buildings the length describes the circumfence of the object, for roads the length is exported
