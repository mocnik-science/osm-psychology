import com.osm.psychology.core.*;
import com.osm.psychology.strategies.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // please adapt the name of the OSHDB database file
        Data.load("/path/to/database/baden-wuerttemberg.oshdb.mv.db");

        // example of a bounding box (the area to use for the output)
        BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);

        // export elements that are buildings as a CSV file
        ExportElements.csv(new StrategyBuildings(), eppelheim, "2019-01-01", Col.NUMBER_OF_CHANGES, Col.LENGTH_AFTER, Col.GEOMETRY_BEFORE, Col.TAGS_AFTER, Col.TIMESTAMP);

        // export changes of elements that are buildings as a JSON file
        ExportContributions.json(new StrategyBuildings(), eppelheim, "2000-01-01", "2019-01-01", Col.LENGTH_AFTER, Col.GEOMETRY_BEFORE, Col.TAGS_AFTER, Col.TIMESTAMP);
    }
}
