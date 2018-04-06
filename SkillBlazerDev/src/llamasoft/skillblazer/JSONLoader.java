package llamasoft.skillblazer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.*;

public class JSONLoader {
    protected ArrayList<Task> jsonDatabase = new ArrayList<>();
    protected JSONArray calendarOfTasks;

    public JSONLoader() {}

    public void jsonLoad() {
        // loads the JSON file from the disk, populates the ArrayList
    }

    public ArrayList<Task> getJsonDatabase() {
        return jsonDatabase; // May not be complete
    }


}
