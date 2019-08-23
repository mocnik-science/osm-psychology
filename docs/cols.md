
[back to readme](../../../)

# Overview over the column options

| Code                         | Description                                                  |
| ---------------------------- | :----------------------------------------------------------- |
| Cols.ALL                     | All tags available                                           |
| Cols.BASIC_INFORMATION       | Includes OSM_ID, CHANGESET_ID, CONTRIBUTOR_USER_ID, TIMESTAMP, and CONTRIBUTION_TYPE |
| Cols.OSM_ID                  | ID of the object, e.g. building or street                    |
| Cols.CHANGESET_ID            | ID of the session in which it was changed                    |
| Cols.CONTRIBUTOR_USER_ID     | ID of the contributor contributing the change                |
| Cols.TIMESTAMP               | Date and time when the change was made                       |
| Cols.CONTRIBUTION_TYPE       | Type of the contribution, Creation, Deletion, Tag Change and / or geometry change |
| Cols.BEFORE                  | All information of the object before the change              |
| Cols.GEOMETRY_BEFORE         | All geometric information of the object before the change    |
| Cols.GEOMETRY_TYPE_BEFORE    | Geometry type of the object before the change                |
| Cols.AREA_BEFORE             | Area of the object before the change                         |
| Cols.LENGTH_BEFORE           | Length of the object before the change*                      |
| Cols.NUMBER_OF_POINTS_BEFORE | Number of edges in the geometric figure before the change    |
| Cols.CENTROID_BEFORE         | Longitude and latitude of the object's Euclidean centroid before the change |
| Cols.TAGS_BEFORE             | List of object tags before the change                        |
| Cols.NUMBER_OF_TAGS_BEFORE   | Number of tags before the change                             |
| Cols.AFTER                   | All information of the object after the change               |
| Cols.GEOMETRY_AFTER          | All geometric information of the object after the change     |
| Cols.GEOMETRY_TYPE_AFTER     | Geometry type of the object after the change                 |
| Cols.AREA_AFTER              | Area of the object after the change                          |
| Cols.LENGTH_AFTER            | Length of the object after the change                        |
| Cols.NUMBER_OF_POINTS_AFTER  | Number of edges in the geometric figure before the change    |
| Cols.CENTROID_AFTER          | Longitude and latitude of the object's Euclidean centroid after the change |
| Cols.TAGS_AFTER              | List of object tags after the change                         |
| Cols.NUMBER_OF_TAGS_AFTER    | Number of tags after the change                              |
| Cols.GEOMETRY                | All geometric information before and after the change        |
| Cols.TAGS                    | All tag information before and after the change              |

*for buildings the length describes the circumference of the object, for roads the length is exported