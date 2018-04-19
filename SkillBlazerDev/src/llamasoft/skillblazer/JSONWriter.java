package llamasoft.skillblazer;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.io.*;

/**
 * JSONWriter.java
 * @Author Jason Engel
 * 19 APR 2018
 * This class will write the UserProfile and Task objects to JSON files
 *
 */

public class JSONWriter {

    public JSONWriter() {}

    public void convertArrayListToJSONArray() {
        // converts ArrayList to JSONArray of associated values
    }

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

    public static void saveTaskToFile(Task task) {

    }

    public static void writeDailyTaskToJSON(DailyTask task) {

    }

    public static void writeWeeklyTaskToJSON(DailyTask task) {

    }

    public static void writeCustomTaskToJSON(DailyTask task) {

    }

    public static void writeCumulativeTaskToJSON(DailyTask task) {

    }


    private static void writeUserProfileToDisk(UserProfile userProfile) {
        // save user profile object contents to file
        // filename should be in format userProfile.json
    }

    /*
     * addFileToInit(String fileName) will be used by all objects
     * that need to persist in .json files to carefully ensure they are
     * listed in SBinit.txt WITHOUT overwriting existing file contents
     *
     *  */
    private static void addFileToInit(String fileName) {
        boolean isListed = false;
        try {
            java.io.File file = new java.io.File(SkillBlazerInitializer.getAbsoluteInitFilePath());
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
