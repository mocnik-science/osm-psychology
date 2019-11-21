import com.osm.psychology.core.*;
import com.osm.psychology.strategies.StrategyBuildings;

public class Main {
    public static void main(String[] args) throws Exception {
        // please adapt the name of the OSHDB database file
        Data.load("D:/OSMdata/baden-wuerttemberg.oshdb.mv.db");

        // example of a bounding box (the area to use for the output)
        BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);

        // export changeset data about buildings as a csv file
        ExportEntities.csv(new StrategyBuildings(), eppelheim, "2019-01-01", Col.LENGTH_AFTER, Col.GEOMETRY_BEFORE, Col.TAGS_AFTER, Col.TIMESTAMP);
        ExportContributions.csv(new StrategyBuildings(), eppelheim, "2000-01-01", "2019-01-01", Col.LENGTH_AFTER, Col.GEOMETRY_BEFORE, Col.TAGS_AFTER, Col.TIMESTAMP);
    }
}
