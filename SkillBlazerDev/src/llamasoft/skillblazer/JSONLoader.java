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
    private JSONParser jsonParser = new JSONParser();
    private ArrayList<Task> userTasks = new ArrayList<>();
    private JSONObject jsonUserObject;
    static SkillBlazerInitializer skillBlazerInit = new SkillBlazerInitializer();
    public static UserProfile userProfile;

    JSONLoader() {}

    // Only use this getter during runtime
    // At startup you should be calling loadTasksFromJSON()
    public ArrayList<Task> getTasks() { return this.userTasks; }

    public UserProfile getUserProfile() { return userProfile; }

    protected ArrayList<Task> loadTasksFromJSON() {
        String temp;

        // Caution: This ArrayList and its Iterator may contain ABSOLUTE PATHS
        ArrayList<String> actualFilenames = addActualFileNames();

        // create an Iterator to reference the VALID filenames
        Iterator<String> userFileIterator = actualFilenames.iterator();

        // go through all the files that were confirmed to have skillblazer
        // filenames.  For each filename, create a FileReader Object and use it
        // to instantiate a JSONObject
        // pass the JSONObject to the appropriate method to create the task
        // to an ArrayList<Task>
        while (userFileIterator.hasNext()) {
            temp = userFileIterator.next();

            try {
                FileReader fileReader = new FileReader(temp);
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
            if (temp.contains("skbl")) {
                // call the method to instantiate a Task-subclass object and
                // add it to the arraylist of tasks
                parseCreateAndAddTaskToList(jsonUserObject, userTasks);
            }
        } //end while loop
        return userTasks;
    }


    /*
     * Method to screen for files with skillblazer filenames and place them in
     * an ArrayList<String> for later use
     */
    private ArrayList<String> addActualFileNames() {
        String tempFileName;
        // get the list of all files in the "user.home" + "\\SkillBlazer\\"
        // home directory
        ArrayList<String> allFileNames = skillBlazerInit.getFileList();
        // generate Iterator for all the (un-filtered) files in the directory
        Iterator<String> allFileIterator = allFileNames.iterator();
        // generate an Iterator that will reference the ArrayList<String>
        // that contains files we want to work with
        ArrayList<String> validatedFileNames = new ArrayList<>();
        /*
         * look for SkillBlazer files in the directory
         */
        while (allFileIterator.hasNext()) {
            tempFileName = allFileIterator.next();
            if (tempFileName.contains("skblw") ||  // weekly task
                tempFileName.contains("skbld") ||  // daily task
                tempFileName.contains("skblc") ||  // custom task
                tempFileName.contains("skblv") ||  // cumulative task
                    // not a task file?  is it the UserProfile data? :
                tempFileName.contains("userProfile.json")) {
                // if the filename has a skillblazer format add it to the list
                validatedFileNames.add(tempFileName);
            }
        } //end while loop

        // return ArrayList<String> validatedFileNames
        return validatedFileNames;
    }


    /*
     * Given a JSONObject created from a json file: parse, instantiate and return
     * a UserProfile object
     * @return UserProfile object
     */
    public UserProfile parseAndReturnUserProfile() {
        try {
            FileReader fileReader = new FileReader(SkillBlazerInitializer.getLastJSONFilePath() +
                                                            "userProfile.json");

            JSONObject jsonObject = (JSONObject)(jsonParser.parse(fileReader));

            java.io.File profileFile = new java.io.File(SkillBlazerInitializer.getLastJSONFilePath() +
                                                            "userProfile.json");

            if (profileFile.exists()) {
                String userName = (String) jsonObject.get("userName");
                long taskNumber = (long) jsonObject.get("taskNumber");
                int year = convertInt((long) (jsonObject.get("year")));
                int month = convertInt((long) jsonObject.get("month"));
                int day = convertInt((long) jsonObject.get("date"));

                Calendar userStartDate = new GregorianCalendar();
                userStartDate.set(year, month, day);

                userProfile = (new UserProfile(userName, userStartDate, taskNumber));
            }
            else if (!profileFile.exists()) { // if the userprofile doesn't exist, create a default one
                return (new UserProfile(getDefaultUsername("New User"), (new GregorianCalendar()), 0));
            }
        } //end try block
        catch (IOException e) {
            System.out.println("Unable to ACCESS userProfile from: " +
                    SkillBlazerInitializer.getLastJSONFilePath() +
                    " \nPlease check your file and folder permissions!");
            return (new UserProfile(getDefaultUsername("New User"), (new GregorianCalendar()), 0));
        }
        catch (ParseException e) {
            System.out.println("Unable to PARSE json file: " +
                    SkillBlazerInitializer.getLastJSONFilePath() + " userProfile.json");
            return (new UserProfile(getDefaultUsername("New User"), (new GregorianCalendar()), 0));
        }

        // ensure that the userName is not set to a meaningless Default value
        userProfile.setUserName( getDefaultUsername(userProfile.getUserName()) );

        return userProfile;
    }


    // If the user is running the program for the first time, or the program had to
    // recover from a lost userProfile.json file, the selection statement below
    // will use the username found in the OS file structure to populate
    // the username
    // Borrowing Code from Mark or Rebecca
    // How dare you implement regex[!|?]
    private String getDefaultUsername(String userNameFromJSON) {
        if (userNameFromJSON.equals(("New User"))) {
            String homePath = System.getProperty("user.home"); //pulls home directory
            String[] arOfKeys = homePath.split(":?\\\\"); //parses folder path
            return arOfKeys[2]; //returns username from desktop path
        }
        return userNameFromJSON;
    }


    /*
     * Given a jsonObject <Task> and an ArrayList<Task> this method
     * will instantiate the appropriate Task subclass, and add it to the
     * class member ArrayList that is passed as a parameter
     */
    private void parseCreateAndAddTaskToList(JSONObject jsonObject, ArrayList<Task> userTasks) {
        boolean isCompleted;

        String taskName = (String) jsonObject.get("taskName");
        long taskId = (long) jsonObject.get("taskId");
        String notes = (String) jsonObject.get("notes");

        int year = convertInt((long) jsonObject.get("year"));
        int month = convertInt((long) jsonObject.get("month"));
        int day = convertInt( (long) jsonObject.get("date"));

        Calendar startDate = new GregorianCalendar();
        startDate.set(year, month, day);

        isCompleted = (boolean)  jsonObject.get("isCompleted");
        String type = (String) jsonObject.get("type");

        ArrayList<Calendar> jsonDatesCompleted = new ArrayList<>();
        long completionCount;

        // Parse the remaining subclass-specific (unique) fields and
        // instantiate the correct Task subclass, add it to the
        // ArrayList<Task> userTaskList
        switch (type) {
            case "daily":
                completionCount = (long) jsonObject.get("completionCount"); // should we try to parse completionDates (Calendar objects)
                if (completionCount > 0) { // there are completion dates stored in this task if completionCount is greater than zero
                    // parse out the completion date fields and instantiate them for this task
                    jsonDatesCompleted = parseCompletionDates(jsonObject, jsonDatesCompleted, completionCount);
                    // instantiate a DailyTask object and add to ArrayList - includes completionDates
                    userTasks.add(new DailyTask(taskName, taskId, startDate, isCompleted, notes, jsonDatesCompleted));
                } else {
                    // instantiate a DailyTask object and add to ArrayList (no completions yet)
                    userTasks.add(new DailyTask(taskName, taskId, startDate,
                            isCompleted, notes));
                }
                break;

            case "weekly":
                completionCount = (long) jsonObject.get("completionCount"); // should we try to parse completionDates (Calendar objects)
                if (completionCount > 0) { // there are completion dates stored in this task if completionCount is greater than zero
                    // parse out the completion date fields and instantiate them for this task
                    jsonDatesCompleted = parseCompletionDates(jsonObject, jsonDatesCompleted, completionCount);
                    // instantiate a WeeklyTask object and add to ArrayList - includes completionDates
                    userTasks.add(new WeeklyTask(taskName, taskId, startDate, isCompleted, notes, jsonDatesCompleted));
                } else {
                    // instantiate a WeeklyTask object and add to ArrayList (no completions yet)
                    userTasks.add(new WeeklyTask(taskName, taskId, startDate,
                            isCompleted, notes));
                }
                break;

            case "custom":
                JSONArray days = (JSONArray) jsonObject.get("days");
                ArrayList<String> dayListing = new ArrayList<>();
                // copy the contents of the JSONArray into an ArrayList
                Iterator<String> dayIterator = days.iterator();
                while (dayIterator.hasNext()) {
                    dayListing.add(dayIterator.next());
                }

                completionCount = (long) jsonObject.get("completionCount"); // should we try to parse completionDates (Calendar objects)
                if (completionCount > 0) { // there are completion dates stored in this task if completionCount is greater than zero
                    // parse out the completion date fields and instantiate them for this task
                    jsonDatesCompleted = parseCompletionDates(jsonObject, jsonDatesCompleted, completionCount);
                    // instantiate a CustomTask object and add to ArrayList - includes completionDates
                    userTasks.add(new CustomTask(taskName, taskId, startDate, isCompleted, notes, dayListing, jsonDatesCompleted));
                } else {
                    // instantiate a CustomTask object and add to ArrayList (no completions yet)
                    userTasks.add(new CustomTask(taskName, taskId, startDate,
                            isCompleted, notes, dayListing));
                }
                break;

            case "cumulative":
                // parse remaining fields specific to a CumulativeTask object
                //Calendar endDate = (Calendar) jsonObject.get("endDate");
                int endYear = convertInt((long) jsonObject.get("endYear"));
                int endMonth = convertInt((long) jsonObject.get("endMonth"));
                int endDay = convertInt((long) jsonObject.get("endDate"));
                double goalToReach = (double) jsonObject.get("goalToReach");
                String goalUnits = (String) jsonObject.get("goalUnits");

                Calendar endDate = new GregorianCalendar();
                endDate.set(endYear, endMonth, endDay);

                completionCount = (long) jsonObject.get("completionCount"); // should we try to parse completionDates (Calendar objects)

                ArrayList<CumulativeHistoryStruct> loveToWatchHerStruct = parseHistoryStruct(jsonObject, completionCount);

                if (completionCount > 0) { // there are completion dates stored in this task if completionCount is greater than zero
                    // parse out the completion date fields and instantiate them for this CumulativeHistoryStruct


                    // instantiate a CumulativeTask object and add to ArrayList
                    userTasks.add(new CumulativeTask(taskName, taskId, startDate,
                            isCompleted, notes, endDate, goalToReach, goalUnits, loveToWatchHerStruct));
                } else {
                    // instantiate a CumulativeTask object and add to ArrayList
                    userTasks.add(new CumulativeTask(taskName, taskId, startDate,
                            isCompleted, notes, endDate, goalToReach, goalUnits));
                }
                // instantiate a CumulativeTask object and add to ArrayList
                userTasks.add(new CumulativeTask(taskName, taskId, startDate,
                        isCompleted, notes, endDate, goalToReach, goalUnits));
                break;
        } //end switch statement
    } //end method parseCreateAndAddTaskToList()


    /*
     * Parse completionDates (Calendar objects) for the Daily/Weekly/Custom Task subclasses
     * {{{{This method CANNOT be used for CumulativeTask objects}}}}
     */
    private ArrayList<Calendar> parseCompletionDates(JSONObject jsonObject, ArrayList<Calendar> jsonDatesCompleted, long completionCount) {
        int completionYear, completionMonth, completionDate;

        for (int i = 1; i <= completionCount; i++) { // completionCount should be in range 1, 2, 3,...N  (zeros are not desirable)
            // for each completionDate (JSONArray) labeled as "completionDate" + i e.g. completionDate1, completionDate2, ...
            String jsonArrayName = "completionDate" + i; // determine the next Array name in the JSON file

            JSONArray completionDateFields = (JSONArray)(jsonObject.get(jsonArrayName));
            Iterator<Long> dateFieldsIterator = completionDateFields.iterator();

            completionYear = convertInt(dateFieldsIterator.next());
            completionMonth = convertInt(dateFieldsIterator.next());
            completionDate = convertInt(dateFieldsIterator.next());

            // create the new Calendar object to represent the completion date
            // that was stored in JSON, with the correct Year, Month, Day
            jsonDatesCompleted.add(new GregorianCalendar(completionYear, completionMonth, completionDate));
        } //end for loop

        return jsonDatesCompleted;
    }


    private ArrayList<CumulativeHistoryStruct> parseHistoryStruct(JSONObject jsonObject, long completionCount) {
        ArrayList<CumulativeHistoryStruct> cumulativeHistoryStructArrayList = new ArrayList<>();
        String jsonStructName;
        int completionYear, completionMonth, completionDate;
        double progress;

        for (int i = 1; i <= completionCount; i++) {
            jsonStructName = "completionDate" + i;

            JSONArray structFields = (JSONArray)(jsonObject.get(jsonStructName));
            Iterator<String> structFieldIterator = structFields.iterator(); // using String since a struct has int and double values

            completionYear = Integer.parseInt(structFieldIterator.next());
            completionMonth = Integer.parseInt(structFieldIterator.next());
            completionDate = Integer.parseInt(structFieldIterator.next());
            progress = Double.parseDouble(structFieldIterator.next());

            Calendar nextCalendar = new GregorianCalendar(completionYear, completionMonth, completionDate);
            CumulativeHistoryStruct newStruct = new CumulativeHistoryStruct(nextCalendar, progress);
            cumulativeHistoryStructArrayList.add( newStruct);
        }
        return cumulativeHistoryStructArrayList;
    }


    /*
     * Legal JSON places numeric values into the file without quotes
     * As a result Java interprets the value returned by JSONParse.get()
     * methods as a long value.
     * This method (casts to primitive int values) long values returned by JSON Object 'get' method
     * to simplify code throughout the parse methods and significantly reduce
     * the number of variables that must be created
     */
    private static int convertInt(long numeric) {
        return (int) numeric;
    } //end method convertInt()

}
