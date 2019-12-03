# OSM-Psychology

The application `OSM-Psychology` provides a simple tool to generate csv and json files about the OpenStreetMap data (map snapshot and history) that can be used for psychological research.

## Installation and preparation

For accessing the OpenStreetMap data, you will require the current [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/index.html), and an Integrated Development Environment (IDE) for Java with [Maven Integration](https://maven.apache.org/) installed. Science the IntelliJ IDEA IDE (as recommended below) has Maven included, no separate installation of Maven is necessary. 


For Java beginners, we recommend to follow these instructions: 

1. Download `OSM-Psychology` by clicking on "Clone or Download" and select "Download ZIP".
2. Unzip the archive in a directory of your choice. This will be the location where the program is stored and exported files are saved to.
3. Download the OpenStreetMap history data from [ohsome Data Repository](http://downloads.ohsome.org/v0.5/) and save it to a directory of your choice. Due to file size, we recommend to download the smallest possible file for your research. In this documentation, the OSHDB file for Heidelberg is used in all examples.
4. Download and install [IntelliJ IDEA IDE](https://www.jetbrains.com/idea/download/). During the installation settings, tick the checkbox for .java to be associated with IntelliJ. 
5. Run IntelliJ and click "Skip All and Set Default" in the lower right corner. You can previously set an UI theme.
6. Now the start screen of IntelliJ should appear. Click "Open" and navigate to the directory where you previously exported the program files.
7. Follow the path structure on the right `/src/main/java/com.osm.psychology` and open the file `Main`. You only need to edit this file to extract the data!

For specifying an area of interest in case not the whole data is used, a bounding box is needed, which can be obtained [here](http://norbertrenner.de/osm/bbox.html) or can be exported from [OpenStreetMap](https://www.openstreetmap.org/).

## Example 1

Get entities in the data (i.e., in Heidelberg) at 2019-01-01 and export those with all available information to csv.

```java
Data.load("C:/path/to/your/OSMdata/heidelberg.oshdb.mv.db");
ExportEntities.csv(new StrategyAll(), "2019-01-01", Col.ALL);
```


## Example 2

Get all changes made to roads in Heidelberg in 2008 and export those with basic information to a csv-formatted file.

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db");
ExportContributions.csv(new StrategyRoads(), "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION);
```

## Example 3

Get all changes made to nodes in Eppelheim, a town near Heidelberg, and export them with basic information and tag information to a json-formatted file.

```java
Data.load("C:/path/to/your/OSMdata/Heidelberg.oshdb.mv.db");
BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);
ExportContributions.json(new StrategyNodes(), eppelheim, "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION, Col.TAG_INFORMATION);
```
A R script to read the JSON file into R is provided [here](src-r/readJSONtoR.R).

## Usage

Further documentation for the functions can be found on these pages.
* [Data.load](docs/load.md) - Setting the path to the OSHDB file
* [BoundingBox](docs/bbox.md) - Setting a bounding box
* [Export**Entities.csv**](docs/EntitiesCsv.MD) - Exporting snapshot data in a CSV file
* [Export**Contributions.csv**](docs/ContributionsCsv.MD) - Exporting contribution data in a CSV file
* [Export**Entities.json**](docs/EntitiesJson.md) - Exporting snapshot data in a JSON file
* [Export**Contributions.json**](docs/ContributionsJson.md) - Exporting contribution data in a JSON file

## Related Publications

* todo

## Author

This application is written and maintained by Maren Mayer, <maren.mayer@students.uni-mannheim.de>, Franz-Benjamin Mocnik, <mail@mocnik-science.net>, and Daniel W Heck <heck@uni-mannheim.de>.

(c) by Maren Mayer and Franz-Benjamin Mocnik, 2019.

The code is licensed under the [MIT license](https://github.com/mocnik-science/osm-psychology/blob/master/LICENSE).
