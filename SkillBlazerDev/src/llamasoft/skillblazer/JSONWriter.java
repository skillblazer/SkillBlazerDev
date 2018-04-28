package llamasoft.skillblazer;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.PrintWriter;
import java.util.*;


/**
 * JSONWriter.java
 * @Author Jason Engel
 * 19 APR 2018
 * This class will write the UserProfile and Task objects to JSON files as part
 * of a shutdown routine
 *
 * Calls writeTaskToJSON() methods contained in the Task subclasses, which use
 * 'helper' functions created here
 *
 */

public class JSONWriter {

    public JSONWriter() {}

    public static void saveAllFilesToDisk(UserProfile userProfile, ArrayList<Task> allTasks) {
        // save UserProfile to userProfile.java (will also update SBinit.txt
        saveUser(userProfile);

        // save all tasks to appropriate JSON files
        // the subsequent method calls will also update SBinit.txt
        for (Task task : allTasks) {
            task.writeTaskToJSON();
        } //end for loop
    } //end method saveAllFilesToDisk


    /*
    * Ensure that the SBinit.txt file exists and that userProfile.json
    * is listed in SBinit.txt
    *
    * THEN, write the UserProfile object contents
    * to userProfile.json
    * */
    public static void saveUser(UserProfile userProfile) {
        // update the SBinit.txt file
        addFileToInit("userProfile.json");

        // write the UserProfile object to userProfile.json
        writeUserProfileToDisk(userProfile);
    }


    private static void writeUserProfileToDisk(UserProfile userProfile) {
        // save user profile object contents to file
        // filename should be in format userProfile.json

        // Take the calendar object for userStartDate
        // and convert it to int values for:
        // year e.g. '2017'
        // month 0-11 for jan-dec
        // date 1-31
        String fileName = "userProfile.json";
        int year = userProfile.getUserStartDate().get(Calendar.YEAR);
        int month = userProfile.getUserStartDate().get(Calendar.MONTH);
        int date = userProfile.getUserStartDate().get(Calendar.DATE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", userProfile.getType());
        jsonObject.put("userName", userProfile.getUserName());
        jsonObject.put("month", month);
        jsonObject.put("year", year);
        jsonObject.put("date", date);
        jsonObject.put("taskNumber", userProfile.getTaskNumber());

        writeJSON(jsonObject, fileName);
    } //end method writeUserProfileToDisk()


     static void writeJSON(JSONObject jsonObject, String fileName) {
        try {
            FileWriter jsonOutput = new FileWriter(SkillBlazerInitializer.getLastJSONFilePath() + fileName);
            jsonOutput.write(jsonObject.toJSONString());
            jsonOutput.flush();
            jsonOutput.close();
        }
        catch (IOException e) {
            System.out.println("Could not find or access " + SkillBlazerInitializer.getLastJSONFilePath() + " or " + fileName + "\n");
        }
    } //end method writeJSON()


    /*
     * addFileToInit(String fileName) will be used by all objects
     * that need to persist in .json files to carefully ensure they are
     * listed in SBinit.txt
     *
     * addFileToInit() will get the full list of existing files from SBinit.txt
     * via a call to getFileContents().  If the filename the caller needs to add
     * is not already listed, the string will be added to the same arraylist
     * and an iterator will re-write the entire contents of SBinit.txt
     * so existing filenames not lost
     *
     * */
    static void addFileToInit(String fileName) {
        // getFileContents() will populate the ArrayList from the
        // SBinit.txt file contents on disk,
        // and create the file if it doesn't already exist
        ArrayList<String> contents = getFileContents();

        if(!contents.contains(fileName)) { //if the arraylist doesn't already contain 'fileName'
            contents.add(fileName); // add it to the arraylist

            try {
                java.io.File file = new java.io.File(SkillBlazerInitializer.getAbsoluteInitFilePath());
                PrintWriter output = new PrintWriter(file);
                Iterator<String> contentIterator = contents.iterator();

                // fileName was not already in the ArrayList populated from disk
                // rewrite the SBinit.txt file with the UPDATED ArrayList of filenames
                while(contentIterator.hasNext()) { // if the contents ArrayList has another filename
                    output.println(contentIterator.next()); // add the filename to SBinit.txt
                }
                output.flush();
                output.close();
            }
            catch (IOException e) {
                System.out.println("Could not access SBinit.txt to add filename to disk");
                e.printStackTrace();
            }
        }//end if selection
    } //end method addFileToInit()


    static ArrayList<String> getFileContents() {
        ArrayList<String> listOfFiles = new ArrayList<>();

        try {
            java.io.File file = new java.io.File(SkillBlazerInitializer.getAbsoluteInitFilePath());

            if (file.exists()) { // SBinit.txt is present on disk
                Scanner input = new Scanner(file);

                while (input.hasNext()) {
                    listOfFiles.add(input.next());
                } //end while loop
                input.close();
            }
            else { // SBinit.txt doesn't exist, create it and return an empty list
                java.io.PrintWriter output = new java.io.PrintWriter(file);
                output.flush();
                output.close();
                return listOfFiles;
            }
        }
        catch (IOException e) {
            System.out.println("Could not access SBinit.txt to get file contents!");
            e.printStackTrace();
        }
        return listOfFiles;
    } //end method getFileContents();


    /*
     * CustomTask, DailyTask & WeeklyTask all need to be able to store
     * dates (Calendar) objects that record when a task was completed
     * This method will take an ArrayList<Calendar> and a JSONObject
     * and add a JSONArray completionDate[#][YEAR, MONTH, DATE] to the jsonObject
     * for each recorded completion date
     *
     * This method will also add a (long) completionCount field to JSON files
     *
     * {CumulativeTask will need a similar method with additional info and
     * won't be using this}
     */
    static void prepareCalendarObjectsForJSONStorage(JSONObject jsonCalendarObject, ArrayList<Calendar> datesCompleted) {
        // create and store a value for how many Calendar objects are
        // going to be stored for Completion dates
        // this value will be added to the JSON and then used
        // to determine how many Calendar objects will be parsed out of the JSON file
        // at startup
        long completionCount = datesCompleted.size();
        jsonCalendarObject.put("completionCount", completionCount);

        // for each Calendar Object in ArrayList<Calendar> datesCompleted
        for (int i = 0; i < datesCompleted.size(); i++) {
            Iterator<Calendar> calIterator = datesCompleted.iterator();
            // create a string with completionDate + index of datesCompleted  e.g. completionDate1, completionDate2, ...
            String arrayName = "completionDate" + i;
            // create a new JSONArray to correspond with this Calendar object
            JSONArray datesArray = new JSONArray();

            while (calIterator.hasNext()) {
                // add Year/Month/Date numeric values to THIS JSONArray
                datesArray.add(calIterator.next().get(Calendar.YEAR));
                datesArray.add(calIterator.next().get(Calendar.MONTH));
                datesArray.add(calIterator.next().get(Calendar.DATE));

                // add the JSONArray for one Calendar object to the parent jsonObject
                jsonCalendarObject.put(datesArray, arrayName);
            } //end while loop
        } //end for loop
    }

}
