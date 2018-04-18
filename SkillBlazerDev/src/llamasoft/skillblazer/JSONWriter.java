package llamasoft.skillblazer;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    public void saveTaskToFile(Task task) {

    }

    public void saveUserProfileToFile(UserProfile userProfile) {
        // save user profile to file
        // filename should be in format userProfile.json
    }
}
