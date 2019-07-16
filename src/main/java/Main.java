import com.osm.psychology.core.BoundingBox;
import com.osm.psychology.core.Col;
import com.osm.psychology.core.Data;
import com.osm.psychology.core.Export;
import com.osm.psychology.strategies.StrategyBuildings;

public class Main {
    public static void main(String[] args) throws Exception {
        // please adapt the name of the OSHDB database file
        Data.load("D:/OSMdata/baden-wuerttemberg.oshdb.mv.db");

        // example of a bounding box (the area to use for the output)
        BoundingBox eppelheim = new BoundingBox("Eppelheim", 8.6159, 49.3868, 8.6555, 49.4153);

        // export changeset data about buildings as a csv file
        Export.csv(new StrategyBuildings(), eppelheim, "2000-01-01", "2019-01-01", Col.ALL);
    }
}
