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
    private JSONObject jsonUserObject;

    public JSONLoader() {}


    private JSONObject loadFromJSON() {
        // loads the JSON file from the disk, populates the ArrayList
        try {
            FileReader fileReader = new FileReader( getJSONFileLocation() );
            jsonUserObject = (JSONObject)(jsonParser.parse(fileReader));

        } catch (FileNotFoundException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nnothing at this location\n");
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nParsing exception thrown\n");
            e.printStackTrace();
        } catch (IOException e ) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nIOException error thrown\n");
            e.printStackTrace();
        }

        return jsonUserObject;
    }

    // Call the current file contents on disk and return an ArrayList<Task>
    // This method only returns the file contents that were last saved to disk
    public ArrayList<Task> getTaskListFromJSON(JSONObject jsonObject) {
        parseTask(jsonObject);

        return jsonTaskList;
    }

    private void parseTask(JSONObject jsonObject) {

    }

    // parse JSONObject fields specific to a Daily Task Object
    private void parseDailyTask(JSONObject jasonObject) {

    }

    // parse JSONObject fields specific to a Weekly Task Object
    private void parseWeeklyTask(JSONObject jsonObject) {

    }

    // parse JSONObject fields specific to a Cumulative Task object
    private void parseCumulativeTask(JSONObject jsonObject) {

    }

    // parse JSONObject fields specific to a Custom Task object
    private void parseCustomTask(JSONObject jsonObject) {

    }


    public ArrayList<Task> getJsonDatabase() {
        return jsonTaskList; // May not be complete
    }


    private String getJSONFileLocation() {
        return skillBlazerInit.getLastJSONFileLocation();
    }

}
