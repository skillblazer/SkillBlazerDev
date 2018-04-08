package llamasoft.skillblazer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


class SkillBlazerInitializer {
    private final String initFileLocation = "\\init.txt";

    // TODO: remove this hard coded location
    // using a manually coded location for testing purpose only
    // private final String jsonFilePath = "C:\\$USER\\JSONTestFiles\\AddingFirstGoalTestFile.json";


    protected String getLastJSONFileLocation() {
        // TODO: This should not be a hard coded reference - the init.txt file should hold the last used location
        // TODO: that location should be ready to be passed to any part of the program that wants to write to disk!
        return "C:\\$USER\\Desktop\\JSONTestFiles\\MainTestFile.json";
    }

    protected SkillBlazerInitializer() {}
}
