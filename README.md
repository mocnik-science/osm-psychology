# OSM-Psychology

The application `OSM-Psychology` provides a simple tool to generate csv files about the OpenStreetMap history that can be used for psychological research.

## Installation

todo

## Example 1

todo
```java
Data.load("D:/OSMdata/baden-wuerttemberg.oshdb.mv.db");
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);
Export.csv(new StrategyBuildings(), eppelheim, "2000-01-01", "2019-01-01", Col.ALL);
```

## Example 2

todo

## Related Publications

* todo

## Author

This application is written and maintained by Maren Mayer, <>, Franz-Benjamin Mocnik, <mail@mocnik-science.net>, and Daniel W Heck <>.

(c) by Maren Mayer and Franz-Benjamin Mocnik, 2019.

The code is licensed under the [MIT license](https://github.com/mocnik-science/osm-psychology/blob/master/LICENSE).
