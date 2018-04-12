package llamasoft.skillblazer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    /**
     * Given a jsonObject <Task> and an ArrayList<Task> this method
     * will instantiate the appropriate Task subclass, and add it to the
     * ArrayList that is passed as a parameter
     */
    private void parseAndCreateTask(JSONObject jsonObject, ArrayList<Task> taskArrayList) {
        boolean isCompleted;
        int currentStreak;
        int bestStreak;

        String taskName = (String) jsonObject.get("taskName");
        int taskId = (int) jsonObject.get("taskId");

        int year = (int) jsonObject.get("year");
        int month = (int) jsonObject.get("month");
        int day = (int) jsonObject.get("date");

        Calendar startDate = new GregorianCalendar();
        startDate.set(year, month, day);

        String isCompletedString = (String) jsonObject.get("isCompleted");
        if(isCompletedString.equals("false")) {
            isCompleted = false;
        } else {
            isCompleted = true;
        }
        String type = (String) jsonObject.get("type");


        // Parse the remaining subclass-specific (unique) fields and
        // instantiate the correct Task subclass, add it to the
        // ArrayList<Task> taskArrayList
        switch (type) {
            case "daily":
                // parse remaining fields specific to a DailyTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a DailyTask object and add to ArrayList
                taskArrayList.add(new DailyTask(taskName, taskId, startDate, isCompleted, type, currentStreak, bestStreak));
                break;

            case "weekly":
                // parse remaining fields specific to a WeeklyTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a WeeklyTask object and add to ArrayList
                taskArrayList.add(new WeeklyTask(taskName, taskId, startDate, isCompleted, type, currentStreak, bestStreak));
                break;

            case "custom":
                // parse remaining fields specific to a CustomTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                JSONArray days = (JSONArray) jsonObject.get("days");

                ArrayList<String> dayListing = new ArrayList<>();

                // copy the contents of the JSONArray into an ArrayList
                Iterator<String> iterator = days.iterator();
                while (iterator.hasNext()) {
                    dayListing.add(iterator.next());
                }

                String[] daysOfWeekArray = new String[7];
                // copy ArrayList contents into a primitive String[] array
                for (int i = 0; i < dayListing.size(); i++) {
                    daysOfWeekArray[i] = dayListing.get(i);
                }

                // instantiate a CustomTask object and add to ArrayList
                taskArrayList.add(new CustomTask(taskName, taskId, startDate, isCompleted, type, currentStreak, bestStreak, daysOfWeekArray));
                break;

            case "cumulative":
                // parse remaining fields specific to a CumulativeTask object
                //Calendar endDate = (Calendar) jsonObject.get("endDate");
                int endYear = (int) jsonObject.get("endYear");
                int endMonth = (int) jsonObject.get("endMonth");
                int endDay = (int) jsonObject.get("endDate");

                Calendar endDate = new GregorianCalendar();
                endDate.set(endYear, endMonth, endDay);

                // instantiate a CumulativeTask object and add to ArrayList
                taskArrayList.add(new CumulativeTask(taskName, taskId, startDate, isCompleted, type, endDate));
                break;
        }
    }


    public ArrayList<Task> getJsonDatabase() {
        return jsonTaskList; // May not be complete
    }

}
