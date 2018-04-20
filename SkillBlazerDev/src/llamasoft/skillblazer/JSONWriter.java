package llamasoft.skillblazer;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
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
     * FUTURE IMPLEMENTATION
     * */
    public void saveHistoryToFile() {
        // writes task history to file
        // this is for completed and expired tasks only
        // filename format should be [userName]history.json
    }

    /*
    * Ensure that the SBinit.txt file exists and that userProfile.json
    * is listed in SBinit.txt
    *
    * THEN, write the UserProfile object contents
    * to userProfile.json
    * */
    public static void saveUser(UserProfile userProfile) {
        // update the SBinit.txt file (usually only necessary after first run)
        addFileToInit("userProfile.txt");

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
        Calendar cal = userProfile.getUserStartDate();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        // TODO - remove this println() once you know the Calendar is parsing correctly
        System.out.println("year: " + year + " month " + month + " date " + date);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", userProfile.getUserName());
        jsonObject.put("userName", userProfile.getUserName());
        jsonObject.put("month", month);
        jsonObject.put("year", year);
        jsonObject.put("date", date);
        jsonObject.put("taskNumber", userProfile.getTaskNumber());

        writeJSON(jsonObject, fileName);
    } //end method writeUserProfileToDisk()


     static void writeJSON(JSONObject jsonObject, String fileName) {
        try {
            FileWriter jsonOutput = new FileWriter(SkillBlazerInitializer.getUserDataLocation() + fileName);
            jsonOutput.write(jsonObject.toJSONString());
            jsonOutput.flush();
            jsonOutput.close();
        }
        catch (IOException e) {
            System.out.println("Could not find or access " + fileName + "\n");
        }
    } //end method writeJSON()


    /*
     * addFileToInit(String fileName) will be used by all objects
     * that need to persist in .json files to carefully ensure they are
     * listed in SBinit.txt WITHOUT overwriting existing file contents
     *
     * This class will also create SBinit.txt at the location given by
     * SkillBlazerInitializer.getLastJSONFilePath() if is not found!
     *
     * */
    static void addFileToInit(String fileName) {
        boolean isListed = false;
        try {
            java.io.File file = new java.io.File(SkillBlazerInitializer.getLastJSONFilePath());
            Scanner input = new Scanner(file);

            if(!file.exists()) {  // SBinit.txt file does NOT exist
                java.io.PrintWriter output = new java.io.PrintWriter(file);
                output.println(fileName);
                output.close();
            }
            else {  // SBinit.txt file already exists
                while(input.hasNext()) {
                    if(input.next().equals(fileName)) {
                        // found the provided .json file entry
                        isListed = true;
                    }
                } //end while loop
                if (!isListed) {
                    // didn't find the .json file listed in SBinit.txt
                    // Add it to the SBinit.txt file contents
                    java.io.PrintWriter output = new java.io.PrintWriter(file);
                    output.append(fileName);
                    output.close();
                }
            } //end else (file does exist)
        }
        catch (IOException e) {
            System.out.println("SBinit.txt not found when attempting to write" +
                    fileName + " info to disk.");
        }

    } //end method addFileToInit()

}
