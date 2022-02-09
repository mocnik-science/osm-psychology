# OSM-Psychology

The application `OSM-Psychology` provides a simple tool to generate CSV and JSON files about the OpenStreetMap data (map snapshot and history) that can be used for psychological research.

## Installation and First Steps

To prepare for running the software, some preparations need to be made:

1. Download and install the current [Java Development Kit](https://www.oracle.com/technetwork/java/javase/downloads/index.html).
2. Download and install [IntelliJ IDEA IDE](https://www.jetbrains.com/idea/download/).
  * During the installation settings, tick the checkbox for .java to be associated with IntelliJ.
  * When running IntelliJ the first time, a message might appear in the lower right corner. Click "Skip All and Set Default".
3. Download a OpenStreetMap database from [ohsome Data Repository](https://downloads.ohsome.org/) and save it to a directory of your choice. Due to file size, it is recommended to download a file that covers your area of interest.

Then, you can start setting up your project.  

4. Download the file [myOSMPsychology.zip](https://minhaskamal.github.io/DownGit/#/home?url=https://github.com/mocnik-science/osm-psychology/tree/master/myOSMPsychology) and extract it to a directory of your choice. This will be the location where the files are exported and saved to.
5. Start IntelliJ, click "Open", and navigate to the directory where you previously exported the program files.
6. Follow the path structure on the right `/src/main/java/com.osm.myPsychology` and edit the file `Main`.
   * Open the file by clicking it.
   * Adapt the content of this file by replacing `/path/to/database/baden-wuerttemberg.oshdb.mv.db` by the path of the database you downloaded in Step 3.
   * Adapt the commands for extracting the data according to your needs.
7. Run the file `Main` by clicking `run` in its context menu (right click).

Below, you will find several examples and further documentation.

To identify the coordinates for a bounding box – this is needed to specify an area of interest – you can use the [the BoundingBox tool](https://boundingbox.klokantech.com) or export the coordinates from [OpenStreetMap](https://www.openstreetmap.org).

## Example 1

Get entities in the data (i.e., in Heidelberg) at 2019-01-01 and export those with all available information to CSV.

```java
Data.load("/path/to/your/OSMdata/heidelberg.oshdb.mv.db");
ExportEntities.csv(new StrategyAll(), "2019-01-01", Col.ALL);
```


## Example 2

Get all changes made to roads in Heidelberg in 2008 and export those with basic information to a CSV-formatted file.

```java
Data.load("/path/to/your/OSMdata/Heidelberg.oshdb.mv.db");
ExportContributions.csv(new StrategyRoads(), "2008-01-01", "2008-12-31", Col.BASIC_INFORMATION);
```

## Example 3

Get all changes made to nodes in Eppelheim, a town near Heidelberg, and export them with basic information and tag information to a JSON-formatted file.

```java
Data.load("/path/to/your/OSMdata/Heidelberg.oshdb.mv.db");
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

* tba

## Author

This application is written and maintained by Franz-Benjamin Mocnik <mail@mocnik-science.net>, Maren Mayer <maren.mayer@students.uni-mannheim.de>, and Daniel W Heck <heck@uni-mannheim.de>.

For questions, please approach Maren Mayer <maren.mayer@students.uni-mannheim.de>.

(c) by Franz-Benjamin Mocnik, Maren Mayer, and Daniel W Heck, 2019–2021.

The code is licensed under the [MIT license](https://github.com/mocnik-science/osm-psychology/blob/master/LICENSE).
