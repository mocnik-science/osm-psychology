[back to readme](../../../)

# Export.csv

## Description

Export.csv exports selected data from the OSHDB into a CSV file.

## Usage

```java
Export.csv(new strategy, boundingbox,  startDate, endDate, columns);
```

## Arguments

| arguments   | description                                                  |
| ----------- | ------------------------------------------------------------ |
| strategy    | the strategy used for exporting. StrategyBuildings() exports buildings, StrategyRoads() exports roads, StrategyNodes() exports nodes. Nodes describe places that can be mapped by a single point in space, e.g. trees, traffic lights, or amenities. For further characteristics and a definition of 'node' in OSM see <https://wiki.openstreetmap.org/wiki/Node>. |
| boundingbox | the bounding box describes the area of interest for which the data will be exported. The bounding box sets the name of the area, as well as minimum latitude, minimum longitude, maximum latitude, and maximum longitude of the area of interest. If no bounding box is defined, the whole OSHDB file will be used in terms of location. |
| startDate   | first date an entry in the data can have to be included in the export. If no start **and** end date is specified, the whole OSHDB file will be used in terms of contribution date. |
| endDate     | last date an entry in the data can have to be included in the export. If no start **and** end date is specified, the whole OSHDB file will be used in terms of contribution date. |
| columns     | the columns to be exported for every entry. A list of all columns and columns combinations can be found [here](cols.md) |



## Examples

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db"); // loading the OSHDB
Export.csv(new StrategyBuildings(), Cols.BASIC_INFORMATION); // exporting a CSV file containing all available data of buildings with basic information
```

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db"); // loading the OSHDB
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); // naming and setting the bouding box
Export.csv(new StrategyNodes(), eppelheim, Cols.BASIC_INFORMATION, Cols.GEOMETRY); // exporting a CSV file containing data of nodes from Eppelheim with basic information and geometric information
```

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db"); // loading the OSHDB
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); // naming and setting the bouding box
Export.csv(new StrategyStreets(), eppelheim,  "2000-01-01", "2018-12-31", Cols.BASIC_INFORMATION, Cols.GEOMETRY_BEFORE); // exporting a CSV file containing data of streets from Eppelheim between 2000 and 2018 with basic information and geometric information before the change
```