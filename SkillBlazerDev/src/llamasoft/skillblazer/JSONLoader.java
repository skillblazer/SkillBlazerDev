package llamasoft.skillblazer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONLoader {
    private SkillBlazerInitializer skillBlazerInit = new SkillBlazerInitializer();
    private JSONParser jsonParser = new JSONParser();
    private ArrayList<Task> jsonTaskList = new ArrayList<>();
    protected JSONArray calendarOfTasks;


    // TODO: remove this hard coded location
    // using a manually coded location for testing purpose only
    // private final String jsonFilePath = "C:\\$USER\\JSONTestFiles\\AddingFirstGoalTestFile.json";



    public JSONLoader() {}

    public void loadFromJSON() {
        // loads the JSON file from the disk, populates the ArrayList
        try {
            FileReader fileReader = new FileReader( getJSONFileLocation() );

            JSONObject jsonUserObject = new JSONObject();

        }
        catch (FileNotFoundException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nnothing at this location\n");
            e.printStackTrace();
        }
        catch (ParseException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nParsing exception thrown\n");
            e.printStackTrace();
        }
//        catch (IOException e ) {
//            // TODO: Pop a JavaFX prompt to find the File!
//            System.out.println("\nIOException error thrown\n");
//            e.printStackTrace();
//        }
    }

    public ArrayList<Task> getJsonDatabase() {
        return jsonTaskList; // May not be complete
    }

    private String getJSONFileLocation() {
        return skillBlazerInit.getLastFileLocation();
    }

}
