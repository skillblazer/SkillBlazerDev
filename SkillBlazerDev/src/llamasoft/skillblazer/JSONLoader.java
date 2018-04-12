package llamasoft.skillblazer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONLoader {
    protected SkillBlazerInitializer skillBlazerInit = new SkillBlazerInitializer();
    private JSONParser jsonParser = new JSONParser();
    private ArrayList<Task> jsonTaskList = new ArrayList<>();
    protected JSONArray calendarOfTasks;
    private JSONObject jsonUserObject;

    public JSONLoader() {}


    private JSONObject loadFromJSON(String filename) {
        // loads the JSON file from the disk, populates the ArrayList
        try {
            // TODO: Make sure this reference to a file is correct
            FileReader fileReader = new FileReader( skillBlazerInit.getLastJSONFilePath() + filename );
            jsonUserObject = (JSONObject)(jsonParser.parse(fileReader));

        } catch (FileNotFoundException e) {
            System.out.println("\nNothing at this location\n");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("\nParsing exception thrown\n");
            e.printStackTrace();
        } catch (IOException e) {
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

    private void parseAndCreateTask(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        boolean isCompleted;
        int currentStreak = 0;
        int bestStreak = 0;

        String type = (String) jsonObject.get("type");
        int taskId = (int) jsonObject.get("taskId");
        String isCompletedString = (String) jsonObject.get("isCompleted");

        String startDate = (String) jsonObject.get("startDate");

        if(isCompletedString.equals("false")) {
            isCompleted = false;
        } else {
            isCompleted = true;
        }

        String taskName = (String) jsonObject.get("taskName");

        // Parse the remaining subclass-specific (unique) fields and
        // instantiate the correct Task subclass, add it to the
        // ArrayList<Task> taskArrayList
        switch (type) {
            case "daily":
                // parse remaining fields specific to a DailyTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a DailyTask object and add to ArrayList
                taskArrayList.add(new DailyTask());
                break;

            case "weekly":
                // parse remaining fields specific to a WeeklyTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a WeeklyTask object and add to ArrayList
                taskArrayList.add(new WeeklyTask());
                break;

            case "custom":
                // parse remaining fields specific to a CustomTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a CustomTask object and add to ArrayList
                taskArrayList.add(new CustomTask());
                break;

            case "cumulative":
                // parse remaining fields specific to a CumulativeTask object
                Calendar endDate = (Calendar) jsonObject.get("endDate");

                // instantiate a CumulativeTask object and add to ArrayList
                taskArrayList.add(new CumulativeTask());
                break;
        }
    }


    public ArrayList<Task> getJsonDatabase() {
        return jsonTaskList; // May not be complete
    }

}
