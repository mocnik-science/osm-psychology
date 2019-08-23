# OSM-Psychology

The application `OSM-Psychology` provides a simple tool to generate csv and json files about the OpenStreetMap history that can be used for psychological research.

## Installation and preparation

For accessing the OpenStreetMap history data, you will require the current [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/index.html), and an Integrated Development Environment (IDE) for Java with [Maven Integration](https://maven.apache.org/) installed.
For Java beginners, we recommend to follow these instructions: 

1. Download `OSM-Psychology` by clicking on 'Clone or Download' and select 'Download ZIP'.
2. Unzip the arcive in a directory of your choice. This will be the location where the program is stored and exported files are saved to.
3. Download the OpenStreetMap history data from [ohsome](http://downloads.ohsome.org/v0.5/) and save it to a directory of your choice. Due to file size, we recommend to download the smallest possible file for your research. In this documentation, the OSHDB file for Baden-Württemberg is used in all examples.
4. Download and install [IntelliJ IDEA IDE](https://www.jetbrains.com/idea/download/). During the installation settings, tick the checkbox for .java to be associated with IntelliJ. 
5. Run IntelliJ and click "Skip All and Set Default" in the lower right corner. You can previously set an UI theme.
6. Now the start screen of IntelliJ should appear. Click "Open" and navigate to the directory where yout previously exported the program files.
7. Follow the path structure on the right src > main > java > com.osm.psychology and open the file Main. You only need to edit this file to extract the data!

For specifying an area of interest in case not the whole data is used, a bounding box is needed, which can be obtained [here](http://norbertrenner.de/osm/bbox.html) or can be exported from [OpenStreetMap](https://www.openstreetmap.org/).

## Example 1

Get all changes ever made to buildings in the data (i.e. in Baden-Württemberg) and export those with all available information to csv.

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
Export.csv(new StrategyBuildings(), Col.ALL);
```


## Example 2

Get all changes made to roads in Baden-Württemberg in 2008 and export those with basic information to csv.

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
Export.csv(new StrategyRoads(), "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION);
```

## Example 3

Get all changes made to nodes in Heidelberg Eppelheim and export them with basic information and tag information to json.

```java
Data.load("C:/path/to/your/OSMdata/baden-wuerttemberg.oshdb.mv.db");
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);
Export.json(new StrategyBuildings(), eppelheim, "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION, Col.TAGS);
```
A R script to read the JSON file into R is provided [here](src-r/readJSONtoR.R)

## Usage

Further documentation for the functions can be found on these pages.
* [Data.load](docs/load.md) - Setting the path to the OSHDB file
* [BoundingBox](docs/bbox.md) - Setting a bounding box
* [Export.**csv**](docs/csv.MD) - Exporting data in a CSV file
* [Export.**json**](docs/json.md) - Exporting data in a JSON file

## Related Publications

* todo

## Author

This application is written and maintained by Maren Mayer, <maren.mayer@students.uni-mannheim.de>, Franz-Benjamin Mocnik, <mail@mocnik-science.net>, and Daniel W Heck <heck@uni-mannheim.de>.

(c) by Maren Mayer and Franz-Benjamin Mocnik, 2019.

The code is licensed under the [MIT license](https://github.com/mocnik-science/osm-psychology/blob/master/LICENSE).
