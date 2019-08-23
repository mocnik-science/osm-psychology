
[back to readme](../../../)

# BoundingBox

## Description

BoundingBox sets a bounding box to a given longitude and latitude and naming the selected area.

## Usage

```java
BoundingBox objectName = new BoundingBox(name, minLon, minLat, maxLon, maxLat);
```

## Arguments

name			name given to the respective area. The name indicated in the bounding box is used to name the exported CSV or JSON file.

minLon			minimum longitude of the bounding box.

minLat			minimum latitude of the bounding box.

maxLon			maximum longitude of the bounding box.

maxLat			maximum latitude of the bounding box.

## Examples

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153); // creating an bounding box object, in which name and location are set
Export.json(new StrategyBuildings(), eppelheim, "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION, Col.TAGS);
```