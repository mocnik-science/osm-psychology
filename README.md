# OSM-Psychology

The application `OSM-Psychology` provides a simple tool to generate csv and json files about the OpenStreetMap history that can be used for psychological research.

## Installation

For accessing the OpenStreetMap history data, you will require the current [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/index.html), and an Integrated Development Environment (IDE) for Java with [Maven Integration](https://maven.apache.org/) installed.
For Java-beginners, we recommend [IntelliJ IDEA IDE](https://www.jetbrains.com/idea/) as Maven can be installed during the program installation. 
You can add OSM-psychology to IntelliJ IDE by either following this [tutorial](https://www.jetbrains.com/help/idea/manage-projects-hosted-on-github.html) or downloading the project as ZIP, extracting it at a directory of your choice and adding it to your IDE manually. 

Lastly, the OpenStreetMap history data can be retrieved from [ohsome](http://downloads.ohsome.org/v0.5/). Due to file size, we recommend to download the smallest possible file for your research. In this documentation, the OSHDB file for Baden-Württemberg is used in all examples.

## Preparation

Open the class Main by following the folder structre of src within the Java project. To generate csv and json files, you will only have to edit this Java class.

For specifying an area of interest, a bounding box is needed, which can be obtained [here](http://norbertrenner.de/osm/bbox.html) or can be exported from [OpenStreetMap](https://www.openstreetmap.org/).

## Example 1

Get all changes made to buildings in the area of Heidelberg Eppelheim from 2010 to 2018 and export those with basic and geometric information to csv.

We set the path to the previously downloaded OSHDB file and name and set the bounding box accordingly. Lastly, the export statement is specified, indicating that a csv file with change information on buildings is requested. In this statement, the bounding box, the start and end date as well as the desired information about the changes is specified.

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);
Export.csv(new StrategyBuildings(), eppelheim, "2010-01-01", "2018-12-31", Col.BASIC_INFORMATION, Col.GEOMETRY);
;
```
Basic information contains the ID of the OSM object, the ID of the changeset, i.e. the session in which the change was contributed, the ID of the contributor who made the change, a timestamp indicating the time of the contribution, and the type of contribution (creation, deletion, tag change, geometry change. We recommend to always export at least basic information to identify each change unambigiously.

## Example 2

Get all changes made to roads in Baden-Württemberg in 2008 and export them with all information available to json.
We set the path to the previously downloaded OSHDB file. Since no bounding box is specified, the statement is applied to the whole OSHDB file. 

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
Export.json(new StrategyRoads(), "2008-01-01", "2008-12-31", Col.ALL);
;
```
For extracting tag information, json format is recommended. An R script to read the json file to R is provided.

## Example 3

Get all changes ever made to nodes, i.e. single points in space representing e.g. trees, trees, or amenities, in Heidelberg Eppelheim and export them with basic information, all tag information and the geometric information of the nodes after the change to json.
We set the path to the previously downloaded OSHDB file. 

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);
Export.json(new StrategyNodes(), "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION, Col.TAGS, Col.GEOMETRY_AFTER);
;
```


## Related Publications

* todo

## Author

This application is written and maintained by Maren Mayer, <maren.mayer@students.uni-mannheim.de>, Franz-Benjamin Mocnik, <mail@mocnik-science.net>, and Daniel W Heck <heck@uni-mannheim.de>.

(c) by Maren Mayer and Franz-Benjamin Mocnik, 2019.

The code is licensed under the [MIT license](https://github.com/mocnik-science/osm-psychology/blob/master/LICENSE).
