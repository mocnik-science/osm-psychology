# OSM-Psychology

The application `OSM-Psychology` provides a simple tool to generate csv and json files about the OpenStreetMap history that can be used for psychological research.

## Installation

For accessing the OpenStreetMap history data, you will require the current [Java Development Kit] (https://www.oracle.com/technetwork/java/javase/downloads/index.html), and an Integrated Development Environment (IDE) for Java with (Maven Integration) [https://maven.apache.org/] installed.
For Java-beginners (IntelliJ IDEA IDE)[https://www.jetbrains.com/idea/] is recommended as Maven can be retrieved during the program installation and no separate installation process is necessary. For adding OSM psychology you can either follow this [tutorial] (https://www.jetbrains.com/help/idea/manage-projects-hosted-on-github.html) or download as ZIP, extract at a directory of your choice and add manually to your IDE. 

Lastly, the OpenStreetMap history data can be retrieved from [ohsome] (http://downloads.ohsome.org/v0.5/).

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

This application is written and maintained by Maren Mayer, <maren.mayer@students.uni-mannheim.de>, Franz-Benjamin Mocnik, <mail@mocnik-science.net>, and Daniel W Heck <heck@uni-mannheim.de>.

(c) by Maren Mayer and Franz-Benjamin Mocnik, 2019.

The code is licensed under the [MIT license](https://github.com/mocnik-science/osm-psychology/blob/master/LICENSE).
