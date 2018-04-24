package llamasoft.skillblazer;

/*
import com.dropbox.core.*;
import static com.dropbox.core.DbxWriteMode.add;
import java.io.*;
import java.util.Locale;
*/
public class DropBoxIntegration {

    private boolean isEnabled;
    /*
    private static final String APP_KEY = "j8zcubrvrth9i1l";
    private static final String APP_SECRET = "2jacqpbzpj5p56f";
    private static final String ACCESS_TOKEN = "Pg2oCQ8Y26AAAAAAAAAAC2Xp1oFMkDCUonjkgRXQ4SwRtlp-LHFeY5JVtjTuXCeB";

    DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

    DbxReqsuestConfig config = new DbxRequestConfig("https://www.dropbox.com/home/Apps/skillblazer_destination", Locale.getDefault().toString());
    DbxWebAuthNoREdirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);

    //authorize app via sign in
    String authorizeUrl = webAuth.start();
    String code = new BufferedReader(new InputStreamReader(System.in)).readerLine().trim();
    
    //fail if input auth. is invalid
    DbxAuthFinish authFinish = webAuth.finish(code);
    String accessToken = authFinish.accessToken;

    DbxClient client = new DbxClient(config, ACCESS_TOKEN);

    //allow file input to upload
    File inputFile = new File("test.txt");
    FileInputStream inputStream = new FileInputStream(inputFile);

    try{
    DbxEntry.File uploadFile = client.uploadFile("test.txt"), DbxWriteMode.add(), inputFile.lenght(), inputStream);
    System.out.println("UPloaded " + uploadedFile.toString());
    } finally {
    inputStream.close();
    }
    
   
}
    
    */

    public void enableDropBoxIntegration(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
