/**
 * This class is responsible for managing the user's home directory files so
 * that they are available for JSONWriter and JSONLoader when the application
 * starts up or when other parts of the application need to save additions to or
 * changes to the overall set of Tasks created by the user.
 *
 * Note on design philosophy -> This class is responsible for providing info on
 * the location of all user files, and should be handling any direct references
 *  or changes to SBinit.txt file!
 *
 *  It will handle accessing SBinit.txt as required by JSONLoader and JSONWriter
 *  and provide location data for the files these classes need to read/write
 *  to JSON files
 *
 * */
// package
package llamasoft.skillblazer;

// imports
import java.io.File;
import java.io.IOException;
import java.util.*;

class SkillBlazerInitializer {
    
    // member fields
    private static final String userHome = System.getProperty("user.home");
    private static final String userFileLocation = determineOSFilePath();
    protected static final String initFile = "SBinit.txt";
    
    // default, no-argument constructor
    SkillBlazerInitializer() {}
    
    // method to get user data location
    static String getUserDataLocation() { 
        return userHome; 
    } // end method getUserDataLocation()


    /*
     * Determines if the folder for the skillblazer app has been created
     * and records the folder path for where to create new JSON files
     * for persistence of data.
     */
    public static String determineOSFilePath() {

        String storageFolderPath = ""; //default initialization - path to send json data
        File file = new File(""); //default initialization - will become directory to store data

        String osName = System.getProperty("os.name"); //pulls OS name
        String homePath = System.getProperty("user.home"); //pulls home directory

        if (osName.contains("Windows")) {

            file = new File(userHome + "\\SkillBlazer\\");
            //if folder doesn't exist, create skillblazerApp directory
            if (!file.exists()) {
                file.mkdir();
            }

            storageFolderPath = (userHome + "\\SkillBlazer\\");
        }
        else if (osName.contains("Mac") || osName.contains("Linux") ||
                osName.contains("Unix")) {
            file = new File(userHome + "/SkillBlazer/");
            //if folder doesn't exist, create skillblazerApp directory
            if (!file.exists()) {
                file.mkdir();
            }

            storageFolderPath = (userHome + "/SkillBlazer/");
        }

        //if path returns empty, then window will need to appear stating that
        //the OS they are using cannot use this application
        return storageFolderPath;
    } // end determineOSFilePath() method


    // method to get last JSON file path
    static String getLastJSONFilePath() {
        return userFileLocation;
    } // end method getLastJSONFilePath()

    // method to return a String of form "user.home" + \\SkillBlazer\\SBinit.txt"
    static String getAbsoluteInitFilePath() {
        return userFileLocation + initFile;
    } // end method getAbsoluteFilePath()

    // method to create skillblazer directory
    private static void createSkillBlazerDirectory() {
        boolean newDirectory = new File(userFileLocation).mkdirs();
    } // end method createSkilBlazerDirectory()

    /*
     * Read the contents of the SBinit.txt file from the disk
     * Return the contents as an ArrayList<String>
     * NOTE: This will return a raw list of the contents of \\SkillBlazer\\
     */
    ArrayList<String> getFileList() {
        ArrayList<String> listOfJsonFiles = new ArrayList<>();

        // create a File object in the local directory relevant to the current path
        java.io.File directory = new java.io.File(userFileLocation);
        java.io.File file = new java.io.File(userFileLocation + initFile);

        try {
            if (file.exists()) {  // SBinit.txt already exists on disk
                Scanner input = new Scanner(file);

                while (input.hasNext()) {
                    java.io.File tempFile = new java.io.File(userFileLocation + input.next());

                    if(tempFile.exists()) {
                        listOfJsonFiles.add(tempFile.toString());
                    }
                }
                input.close();
            }
            else if (!directory.exists()) {
                // create the directory if it doesn't already exists or was
                // deleted
                createSkillBlazerDirectory();  // create the directory on disk
                java.io.PrintWriter output = new java.io.PrintWriter(file);  // create the file
                output.flush();
                output.close();
            }
            else if (!file.exists()) {
                // if the SBinit.txt file doesn't exist, go ahead and create it for later
                // (usually occurs when the user runs the program for the very
                // first time).
                java.io.PrintWriter output = new java.io.PrintWriter(file);
                output.flush();
                output.close();  // close the file
            }
        }
        catch (IOException e) {
            System.out.println("Error while READING data from: " + initFile +
                    "  It's quite possible that the file has been set to read " +
                    "only, or the program cannot access the folder specified!" +
                    "\n1.  Check Permissions for folder: " + userFileLocation +
                    "\n2.  Check Permissions for file: " + userFileLocation + initFile);
            System.out.println("\n\n");
            e.printStackTrace();
        }
        // pass the ArrayList<String> listOfJsonFiles to the caller
        return listOfJsonFiles;
    } // end method getFileList()

} // end class SkillBlazerInitializer
