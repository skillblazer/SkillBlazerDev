package llamasoft.skillblazer;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

class SkillBlazerInitializer {
    private static final String userHome = System.getProperty("user.home");
    private static final String userFileLocation = userHome + "\\SkillBlazer\\";
    protected static final String initFile = "SBinit.txt";

    SkillBlazerInitializer() {}

    static String getUserDataLocation() { return userHome; }

    static String getLastJSONFilePath() {
        return userFileLocation;
    }

    static String getAbsoluteInitFilePath() {
        // will return a String of form "user.home" + \\SkillBlazer\\SBinit.txt"
        return userFileLocation + initFile;
    }

    private static void createSkillBlazerDirectory() {
        boolean newDirectory = new File(userFileLocation).mkdirs();
    }

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
    }


    static void writeJsonFileListToInit(ArrayList<String> fileList) {
        // a method will need to be written to handle taking the Task
        // objects and creating a corresponding list of Strings for the
        // filename e.g. d0005.json or userProfile.json

        Iterator<String> fileNamesIterator = fileList.iterator();
        // create a file object to reference SBinit.txt
        java.io.File directory = new java.io.File(userFileLocation);
        java.io.File file = new java.io.File(userFileLocation + initFile);

        try {
            if (!directory.exists()) {
                createSkillBlazerDirectory();
            }
            if (!file.exists()) {
                // create the file on disk
                java.io.PrintWriter output = new java.io.PrintWriter(file);
                while (fileNamesIterator.hasNext()) {
                    // add contents of the ArrayList of fileNames to SBinit.txt
                    output.println(fileNamesIterator.next());
                }
                output.close(); // close the file
            } else {
                // open the file on the disk
                java.io.PrintWriter output = new java.io.PrintWriter(file);
                // (Overwriting previous contents) add filenames to disk
                // one per line
                while (fileNamesIterator.hasNext()) {
                    // add contents of the ArrayList of fileNames to SBinit.txt
                    output.println(fileNamesIterator.next());
                }
                output.close();
            }
        }
        catch (IOException e) {
            System.out.println(" Error while WRITING data to " + initFile +
                "  It's quite possible that the file has been set to read " +
                "only, or the program cannot access the folder specified!" +
                "\n1.  Check Permissions for folder: " + userFileLocation +
                "\n2.  Check Permissions for file: " + userFileLocation + initFile);
            System.out.println("\n\n");
            e.printStackTrace();
        }
    }

}
