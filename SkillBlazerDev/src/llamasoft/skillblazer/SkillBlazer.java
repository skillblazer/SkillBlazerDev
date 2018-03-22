package llamasoft.skillblazer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SkillBlazer extends Application {

    @Override
    public void start(Stage primaryStage) {
        // setup the main Stage, Scenes and such

        // instantiate and setup a button
        Button btn = new Button();
        btn.setText("Click So Nothing Happens!!");

        // set up layout pane (BorderPane)
        BorderPane pane = new BorderPane();

        // set placement/alignment for the btn in the pane
        pane.setCenter(btn);

        // add this pane/layout to the scene
        Scene scene = new Scene(pane, 300, 200);

        // add scene to stage
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();  // actually display the scene
    }

    public static void main(String[] args) {
        launch(args);  // open the JavaFX Stage
    }
}
