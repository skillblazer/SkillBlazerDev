package llamasoft.skillblazer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
// uncomment for Macintosh style
//import com.aquafx_project.*;


public class SkillBlazer extends Application {

    JSONLoader jsonLoader = new JSONLoader();
    JSONWriter jsonWriter = new JSONWriter();

    // TODO: This can be removed once testing is completed!
    SkillBlazerInitializer intializer = new SkillBlazerInitializer();

    @Override
    public void start(Stage primaryStage) {
        // setup the main Stage, Scenes and such


        // uncomment for Macintosh style
        //AquaFx.style();

        // set up layout pane (BorderPane)
        BorderPane pane = new BorderPane();

        // add this pane/layout to the scene
        Scene scene = new Scene(pane, 900, 600);

        // add scene to stage
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();  // actually display the scene
    }


    public static void main(String[] args) {
        // probably need to call something from SkillBlazerInitializer
        // to instantiate the Tasks and UserProfile objects and make them
        // available to the GUI

        sbInitTester();

        launch(args);  // open the JavaFX Stage
    }


    public static void sbInitTester() {
        ArrayList<String> fileListTest = SkillBlazerInitializer.getFileList();
        //fileListTest = SkillBlazerInitializer.getFileList();

        if (fileListTest.isEmpty()) {
            fileListTest.add("d0005.json");
            fileListTest.add("userProfile.json");
            fileListTest.add("w0002.json");
            fileListTest.add("c0003.json");
        }

        SkillBlazerInitializer.writeJsonFileListToInit(fileListTest);

        ArrayList<String> notEmptyTest = SkillBlazerInitializer.getFileList();

        Iterator<String> iterator = notEmptyTest.iterator();
        String path = SkillBlazerInitializer.getUserDataLocation();
        while (iterator.hasNext()) {
            System.out.println(path + "\\" + iterator.next());
        }
    } // end method sbinitTester()

}
