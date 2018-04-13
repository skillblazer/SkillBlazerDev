package llamasoft.skillblazer;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SkillBlazer extends Application {

    JSONLoader jsonLoader = new JSONLoader();
    JSONWriter jsonWriter = new JSONWriter();

    // TODO: This can be removed once testing is completed!
    SkillBlazerInitializer intializer = new SkillBlazerInitializer();

    @Override
    public void start(Stage primaryStage) {
        // setup the main Stage, Scenes and such

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

        launch(args);  // open the JavaFX Stage
    }




}
