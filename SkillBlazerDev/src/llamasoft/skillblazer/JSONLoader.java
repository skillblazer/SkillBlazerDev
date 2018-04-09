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


    private JSONObject loadFromJSON(String filename) {
        // loads the JSON file from the disk, populates the ArrayList
        try {
            FileReader fileReader = new FileReader( getJSONFileLocation() );
            jsonUserObject = (JSONObject)(jsonParser.parse(fileReader));

        } catch (FileNotFoundException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nNothing at this location\n");
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nParsing exception thrown\n");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nIOException error thrown\n");
            e.printStackTrace();
        }

        return jsonUserObject;
    }

    // Call the current file contents on disk and return an ArrayList<Task>
    // This method only returns the file contents that were last saved to disk
    public ArrayList<Task> getTaskListFromJSON(JSONObject jsonObject) {

//        while (jsonObject.)
//        parseTask(jsonObject);
//
//        jsonObject.con


        return jsonTaskList;
    }

    private ArrayList<Task> parseCommonTaskFields(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        boolean isCompleted;

        int taskId = (int) jsonObject.get("taskId");
        String startDate = (String) jsonObject.get("startDate");

        String isCompletedString = (String) jsonObject.get("isCompleted");
        if((isCompletedString.equals("false")) ||
           (isCompletedString.equals("FALSE")) ||
           (isCompletedString.equals("False")) ) {
            isCompleted = false;
        } else {
            isCompleted = true;
        }

        String taskName = (String) jsonObject.get("taskName");

        // selection statements to determine if this is a subclass of Task.java

        // instantiate the correct type of Task/DailyTask/WeeklyTask
        return taskArrayList;
    }

    // parse JSONObject fields specific to a Daily Task Object
    private void parseDailyTask(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        parseCommonTaskFields(jsonObject, taskArrayList);

    }

    // parse JSONObject fields specific to a Weekly Task Object
    private void parseWeeklyTask(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        parseCommonTaskFields(jsonObject, taskArrayList);
    }

    // parse JSONObject fields specific to a Cumulative Task object
    private void parseCumulativeTask(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        parseCommonTaskFields(jsonObject, taskArrayList);
    }

    // parse JSONObject fields specific to a Custom Task object
    private void parseCustomTask(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        parseCommonTaskFields(jsonObject, taskArrayList);
    }




    public ArrayList<Task> getJsonDatabase() {
        return jsonTaskList; // May not be complete
    }


    private String getJSONFileLocation() {
        return skillBlazerInit.getLastJSONFilePath();
    }

}
