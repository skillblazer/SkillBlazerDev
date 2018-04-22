package llamasoft.skillblazer;

import java.io.FileWriter;
import java.io.IOException;
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
     * listed in SBinit.txt WITHOUT overwriting existing file contents
     *
     * This class will also create SBinit.txt at the location given by
     * SkillBlazerInitializer.getLastJSONFilePath() if is not found!
     *
     * */
    static void addFileToInit(String fileName) {
        boolean isListed = false;
        // get the contents of SBinit.txt, create the file if it doesn't exist
        ArrayList<String> contents = getFileContents();
        Iterator<String> iterator = contents.iterator();

        try {
            java.io.File file = new java.io.File(SkillBlazerInitializer.getLastJSONFilePath() + "SBinit.txt");
            PrintWriter output = new PrintWriter(file);

            // prep the contents arraylist for writing
            // ensure that empty doesn't stay in the arraylist
            // place new fileName into ArrayList
            while(iterator.hasNext()) {
                if (iterator.next().equals("empty")) {
                    contents.remove("empty");
                    contents.add(fileName); // list was empty - add the fileName
                    output.close();
                    break; // exit the loop now that the fileName is listed
                }
                if (iterator.hasNext() && iterator.next().equals(fileName)) {
                    isListed = true;  //method will exit if isListed == true
                    output.close();
                }
            } //end while loop
            if (!isListed) { // fileName not already in SBinit.txt
                contents.add(fileName); // add fileName to contents (ArrayList)

                for (String item : contents) {
                    output.println(item); // write the fileName to SBinit.txt
                }
                output.flush();
                output.close();
            }
        }
        catch (IOException e) {
            System.out.println("SBinit.txt not accessible when attempting to write " + SkillBlazerInitializer.getLastJSONFilePath() +
                    fileName + " info to disk.");
        }
    } //end method addFileToInit()


    static ArrayList<String> getFileContents() {
        ArrayList<String> contents = new ArrayList<>();

        try {
            java.io.File file = new java.io.File(SkillBlazerInitializer.getLastJSONFilePath() + "SBinit.txt");
            Scanner input = new Scanner(file);

            if(!file.exists()) { // SBinit.txt doesn't already exist
                java.io.PrintWriter output = new java.io.PrintWriter(file);  // create the file
                output.println("empty");
                output.flush();
                output.close();
            } else { // file exists, read its contents and add to ArrayList<String>
                while (input.hasNext()) {
                    contents.add(input.next());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error while trying to determine SBinit.txt " +
                    "file contents");
            e.printStackTrace();
        }
        // pass the ArrayList<String> back to caller
        return contents;
    } //end method getFileContents()

}
