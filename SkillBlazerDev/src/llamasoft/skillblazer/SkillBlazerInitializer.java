package llamasoft.skillblazer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


class SkillBlazerInitializer {
    private final String initFileLocation = "\\SBinit.txt";

    // ArrayList that will contain the last-known paths of the files
    // used to store the UserProfile and all tasks.
    // Can easily be extended if we want to store more stuff in different
    // json files later on
    private ArrayList<String> listOfJSONFiles = getFileList();

    // TODO: remove this hard coded location
    // using a manually coded location for testing purpose only
    // private final String jsonFilePath = "C:\\$USER\\JSONTestFiles\\AddingFirstGoalTestFile.json";

    protected SkillBlazerInitializer() {}


    protected static ArrayList<String> getFileList() {
        ArrayList<String> listOfJsonFiles = new ArrayList<>();

        // create a File object in the local directory relevant to the current path
        java.io.File file = new java.io.File("\\SBinit.txt");

        try {
            if (!file.exists()) {
                java.io.PrintWriter output = new java.io.PrintWriter(file);
                output.close();  // close the file
            }
            else {
                Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    listOfJsonFiles.add(input.nextLine());
                }
            }
        }
        catch (IOException e) {
            System.out.println("IOException thrown! ");
            e.printStackTrace();
        }
        // pass the ArrayList<String> listOfJsonFiles to the caller
        return listOfJsonFiles;
    }


    public static void writeJsonFileListToInit(ArrayList<String> fileList) {
        // a method will need to be written to handle taking the Task
        // objects and creating a corresponding list of Strings for the
        // filename e.g. d0005.json or userProfile.json

        Iterator<String> fileNamesIterator = fileList.iterator();
        // create a file object to reference SBinit.txt
        java.io.File file = new java.io.File("SBinit.txt");

        try {
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
        } catch (IOException e) {
            System.out.println("IOException thrown! ");
            e.printStackTrace();
        }
    }


    protected String getLastJSONFilePath() {
        // TODO: The preferredUserPath is now a field in UserProfile.java
        // The UserProfile has to be instantiated before this works!

        //bogus return statement, never use absolute paths
        return "C:\\$USER\\Desktop\\JSONTestFiles\\";
    }

}
