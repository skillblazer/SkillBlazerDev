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
    private ArrayList<Task> userTasks = new ArrayList<>();
    private JSONObject jsonUserObject;

    public JSONLoader() {}

    // Only use this getter during runtime
    // At startup you should be calling loadFromJSON()
    public ArrayList<Task> getTasks() { return this.userTasks; }


    protected ArrayList<Task> loadFromJSON() {
        // call a method to get all the file names from the SBinit.txt
        // file in SkillBlazerInitializer.java and
        // return a Iterator<String> object that has ONLY the useful filenames
        ArrayList<String> actualFilenames = addActualFileNames();

        Iterator<String> userFileIterator = actualFilenames.iterator();

        // go through all the files that were confirmed to have skillblazer
        // filenames.  For each filename, create a FileReader Object and use it
        // to instantiate a JSONObject
        // pass the JSONObject to the appropriate method to create the task
        // to an ArrayList<Task>
        while (userFileIterator.hasNext()) {
            try {
                FileReader fileReader = new FileReader(userFileIterator.next());
                jsonUserObject = (JSONObject)(jsonParser.parse(fileReader));
            }
            catch (FileNotFoundException e) {
                System.out.println("\nNothing at this location\n");
                e.printStackTrace();
            } catch (ParseException e) {
                System.out.println("\nParsing exception thrown\n");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("\nIOException error thrown\n");
                e.printStackTrace();
            }

            if (jsonUserObject.get("type") == "userProfile") {
                // call the method to instantiate the UserProfile object
                parseAndReturnUserProfile(jsonUserObject);
            } else {
                // call the method to instantiate a Task-subclass object and
                // add it to the arraylist of tasks
                parseCreateAndAddTaskToList(jsonUserObject, userTasks);
            }

        } //end while loop
        return userTasks;
    }

    /*
     * Method to screen for files with skillblazer filenames and place them in
     * an iterator for later use
     */
    private ArrayList<String> addActualFileNames() {
        // get the list of all files in the "user.home" + "\\SkillBlazer\\"
        // home directory
        ArrayList<String> allFileNames = skillBlazerInit.getFileList();
        // generate Iterator for all the (un filtered) files in the directory
        Iterator<String> allFileIterator = allFileNames.iterator();
        // generate an Iterator that will reference the ArrayList<String>
        // that contains files we want to work with
        ArrayList<String> actualFilenames = new ArrayList<>();
        /*
         * look for SkillBlazer files in the directory
         */
        while (allFileIterator.hasNext()) {
            if (allFileIterator.next().contains("skblw") ||  // weekly task
                    allFileIterator.next().contains("skbld") ||  // daily task
                    allFileIterator.next().contains("skblc") ||  // custom task
                    allFileIterator.next().contains("skblv") ||  // cumulative task
                    // not a task file?  is it the UserProfile data? :
                    allFileIterator.next().contains("userProfile")) {

                // if the filename has a skillblazer format add it to the list
                actualFilenames.add(allFileIterator.next());
            }
        } //end while loop

        // return an Iterator<String> instantiated on
        // ArrayList<String> actualFilenames
        return actualFilenames;
    }


    /*
     * Given a JSONObject created from a json file: parse, instantiate and return
     * a UserProfile object
     * @return UserProfile object
     */
    private UserProfile parseAndReturnUserProfile(JSONObject jsonObject) {
        String type = "userProfile";

        int year = (int) jsonObject.get("year");
        int month = (int) jsonObject.get("month");
        int day = (int) jsonObject.get("date");

        Calendar userStartDate = new GregorianCalendar();
        userStartDate.set(year, month, day);

        String userName = (String) jsonObject.get("userName");
        long taskNumber = (long) jsonObject.get("taskNumber");

        return (new UserProfile(userName, userStartDate, taskNumber));
    }


    /*
     * Given a jsonObject <Task> and an ArrayList<Task> this method
     * will instantiate the appropriate Task subclass, and add it to the
     * class member ArrayList that is passed as a parameter
     */
    private void parseCreateAndAddTaskToList(JSONObject jsonObject, ArrayList<Task> userTasks) {
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
        // ArrayList<Task> userTaskList
        switch (type) {
            case "daily":
                // parse remaining fields specific to a DailyTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a DailyTask object and add to ArrayList
                userTasks.add(new DailyTask(taskName, taskId, startDate,
                        isCompleted, currentStreak, bestStreak));
                break;

            case "weekly":
                // parse remaining fields specific to a WeeklyTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                // instantiate a WeeklyTask object and add to ArrayList
                userTasks.add(new WeeklyTask(taskName, taskId, startDate,
                        isCompleted, currentStreak, bestStreak));
                break;

            case "custom":
                // parse remaining fields specific to a CustomTask object
                currentStreak = (int) jsonObject.get("currentStreak");
                bestStreak = (int) jsonObject.get("bestStreak");

                JSONArray days = (JSONArray) jsonObject.get("days");
                ArrayList<String> dayListing = new ArrayList<>();
                // copy the contents of the JSONArray into an ArrayList
                dayListing.addAll(days);

                // instantiate a CustomTask object and add to ArrayList
                userTasks.add(new CustomTask(taskName, taskId, startDate,
                        isCompleted, currentStreak, bestStreak, dayListing));
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
                userTasks.add(new CumulativeTask(taskName, taskId, startDate,
                        isCompleted, endDate));
                break;
        } //end switch statement

    } //end method parseCreateAndAddTaskToList()

}
