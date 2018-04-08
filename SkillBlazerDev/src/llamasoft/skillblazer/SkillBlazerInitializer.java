package llamasoft.skillblazer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class SkillBlazerInitializer {
    private final String initFileLocation = "\\SBinit.txt";

    // ArrayList that will contain the last-known paths of the files
    // used to store the UserProfile and all tasks.
    // Can easily be extended if we want to store more stuff in different
    // json files later on
    private ArrayList<String> listOfJSONFiles = new ArrayList<>();

    // TODO: remove this hard coded location
    // using a manually coded location for testing purpose only
    // private final String jsonFilePath = "C:\\$USER\\JSONTestFiles\\AddingFirstGoalTestFile.json";

    protected SkillBlazerInitializer() {}

    protected ArrayList<String> createListOfFiles() {
        try {
            java.io.File file = new File(getLastJSONFilePath());
            FileReader fileReader = new FileReader( "SBinit.txt" );
            while ()
            listOfJSONFiles.add(fileReader.);

        } catch (FileNotFoundException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nNothing at this location\n");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: Pop a JavaFX prompt to find the File!
            System.out.println("\nIOException error thrown\n");
            e.printStackTrace();
        }

        return listOfJSONFiles;
    }


    protected String getLastJSONFilePath() {
        // TODO: This should not be a hard coded reference - the init.txt file should hold the last used location
        // TODO: that location should be ready to be passed to any part of the program that wants to write to disk!
        return "C:\\$USER\\Desktop\\JSONTestFiles\\";
    }


}
