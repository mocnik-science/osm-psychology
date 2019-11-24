[back to readme](../../../)

# ExportContributions.json

## Description

ExportContributions.json exports selected data from the OSHDB into a JSON file. In the exported data set every row represents a change made to an OSM object.

## Usage

```java
ExportContributions.json(new strategy, boundingbox,  startDate, endDate, columns);
```

## Arguments

| arguments    | description                                                  |
| ------------ | ------------------------------------------------------------ |
| strategy     | the strategy used for exporting. StrategyBuildings() exports buildings, StrategyRoads() exports roads, StrategyNodes() exports nodes. Nodes describe places that can be mapped by a single point in space, e.g. trees, traffic lights, or amenities. For further characteristics and a definition of 'node' in OSM see <https://wiki.openstreetmap.org/wiki/Node>.<br />StrategyAll() exports all OSM objects, including buildings, roads, and nodes, as well as further objects such as parks, forests etc. |
| boundingbox  | the bounding box describes the area of interest for which the data will be exported. The bounding box sets the name of the area, as well as minimum latitude, minimum longitude, maximum latitude, and maximum longitude of the area of interest. If no bounding box is defined, the whole OSHDB file will be used in terms of location. |
| isoDateStart | first date an entry in the data can have to be included in the export. If no start **and** end date is specified, the whole OSHDB file will be used in terms of contribution date. |
| isoDateEnd   | last date an entry in the data can have to be included in the export. If no start **and** end date is specified, the whole OSHDB file will be used in terms of contribution date. |
| columns      | the columns to be exported for every entry. A list of all columns and columns combinations can be found [here](cols.md) |

## Details

For extracting tag information, json format is recommended. An R script to read the json file to R is provided [here](scr-r/readJSONtoR.R).

## Examples

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); // loading the OSHDB
ExportContributions.json(new StrategyAll(), "2018-01-01", "2018-12-31", Cols.BASIC_INFORMATION); // exporting a JSON file containing history data of buildings changes in 2018 with basic information
```
```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); 
ExportContributions.json(new StrategyBuildings(), Cols.BASIC_INFORMATION); // exporting a JSON file containing all available data of buildings with basic information
```

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); 
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); 
ExportContributions.json(new StrategyNodes(), eppelheim, Cols.BASIC_INFORMATION, Cols.TAGS); // exporting a JSON file containing data of nodes from Eppelheim with basic information and tag information
```

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db"); 
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); 
ExportContributions.json(new StrategyStreets(), eppelheim,  "2000-01-01", "2018-12-31", Cols.ALL); // exporting a JSON file containing data of streets from Eppelheim between 2000 and 2018 with all available information
```
