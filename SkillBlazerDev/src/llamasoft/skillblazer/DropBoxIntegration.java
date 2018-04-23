package llamasoft.skillblazer;
/*
import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.util.List;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
*/
public class DropBoxIntegration {

    private boolean isEnabled;
   /* private static final String APP_KEY= "y5wmn7bg5743dhl";
    private static final String APP_SECRET= "f3llcjq4dy8g4i7";
    private static final String ACCESS_TOKEN="Pg2oCQ8Y26AAAAAAAAAACjqC1eIg9hNMHBhz1hxder9K6SMLUQZVRVuDfhvj1VQ3";
    private DbxRequestConfig config=null;
    DbxClientV2 client = null;
    FullAccount account =null;
    //create dbx client
    DbxRequestConfig config = new DbxRequestConfig("");
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    
    //create new window
    Stage dropboxWindow = new Stage();
    dropboxWindow.setTitle("Dropbox Integration");
    //get account info
    FullAccount account = client.users().getCurrentAccount();
    
    
    
    */
    
    public void enableDropBoxIntegration(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
