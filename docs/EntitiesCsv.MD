[back to readme](../../../)

# ExportEntities.csv

## Description

ExportEntities.csv exports selected snapshot data from the OSHDB into a CSV file. In the exported data set every row represents an object (i.e. an OSM entity), its information represents the information this object contained at a given point in time.

## Usage

```java
ExportEntities.csv(new strategy, boundingbox, isoDate, columns);
```

## Arguments

| arguments   | description                                                  |
| ----------- | ------------------------------------------------------------ |
| strategy    | the strategy used for exporting. StrategyBuildings() exports buildings, StrategyRoads() exports roads, StrategyNodes() exports nodes. Nodes describe places that can be mapped by a single point in space, e.g. trees, traffic lights, or amenities. For further characteristics and a definition of 'node' in OSM see <https://wiki.openstreetmap.org/wiki/Node>. <br />StrategyAll() exports all OSM objects, including buildings, roads, and nodes, as well as further objects such as parks, forests etc. |
| boundingbox | the bounding box describes the area of interest for which the data will be exported. The bounding box sets the name of the area, as well as minimum latitude, minimum longitude, maximum latitude, and maximum longitude of the area of interest. If no bounding box is defined, the whole OSHDB file will be used in terms of location. |
| isoDate     | date representing the point in time of which the snapshot should be made. For every object / entity the latest version until that date is represented in the data. |
| columns     | the columns to be exported for every entry. A list of all columns and column combinations can be found [here](cols.md). |



## Examples

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); // loading the OSHDB
ExportEntities.csv(new StrategyBuildings(), "2018-01-01", Cols.BASIC_INFORMATION); // exporting a CSV file containing snapshot data of building at 2018-01-01 with basic information
```

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); // loading the OSHDB
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); // naming and setting the bouding box
ExportEntities.csv(new StrategyNodes(), eppelheim, Cols.BASIC_INFORMATION, Cols.GEOMETRY); // exporting a CSV file containing snapshot data of nodes from Eppelheim at 2018-01-01 with basic information and geometric information
```

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); // loading the OSHDB
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); // naming and setting the bouding box
ExportEntities.csv(new StrategyStreets(), eppelheim,  "2000-01-01", "2018-12-31", Cols.BASIC_INFORMATION, Cols.AREA); // exporting a CSV file containing snapshot data of streets from Eppelheim at 2018-12-31 with basic information and area
```