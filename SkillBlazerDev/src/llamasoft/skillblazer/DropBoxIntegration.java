package llamasoft.skillblazer;

import javafx.scene.control.Button;
import com.dropbox.core.*;
import static com.dropbox.core.DbxWriteMode.add;
import java.io.*;
import java.util.Locale;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DropBoxIntegration {

    private boolean isEnabled;
/*
    private static final String APP_KEY = "j8zcubrvrth9i1l";
    private static final String APP_SECRET = "2jacqpbzpj5p56f";
    private static final String ACCESS_TOKEN = "Pg2oCQ8Y26AAAAAAAAAAC2Xp1oFMkDCUonjkgRXQ4SwRtlp-LHFeY5JVtjTuXCeB";

    public void syncDBX() {

        
    Stage dbxWindow = new Stage();
    dbxWindow.setTitle("Upload to DropBox");
    
    Button uploadButton;
    Button cancelButton;
    
    HBox dbxInstructions = new HBox();
    Label toDo = new Label();
    toDo.setText("Upload to DropBox");
    
    uploadButton = new Button();
    cancelButton = new Button();
    
    uploadButton.setText("Upload");
    
    cancelButton.setText("Cancel");
    
    cancelButton.SetOnAction(new EventHandler(){
         @Override
	public void handle(Event event) {
            
   }
        stage.close();
    } 
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxRequestConfig config = new DbxRequestConfig("https://www.dropbox.com/home/Apps/skillblazer_destination", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

        //authorize app via sign in
        String authorizeUrl = webAuth.start();
        String code = new BufferedReader(new InputStreamReader(System.in)).readerLine().trim();

        //fail if input auth. is invalid
        DbxAuthFinish authFinish = webAuth.finish(code);
        String accessToken = authFinish.accessToken;

        DbxClient client = new DbxClient(config, ACCESS_TOKEN);

        //allow file input to upload
        File inputFile = new File("/userProfile.json");
        FileInputStream inputStream = new FileInputStream(inputFile);

        try {
            DbxEntry.File uploadFile = client.uploadFile("/userProfile.json", DbxWriteMode.add(), inputFile.lengnth(), inputStream);
            System.out.println("UPloaded " + uploadFile.toString());
        } finally {
            inputStream.close();
        }
    }
*/
    public void enableDropBoxIntegration(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
