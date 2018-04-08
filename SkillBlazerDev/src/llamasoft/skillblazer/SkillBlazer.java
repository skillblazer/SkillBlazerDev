package llamasoft.skillblazer;

import java.awt.Color;
import static java.awt.Color.green;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import static javafx.scene.text.FontWeight.BOLD;
import static javafx.scene.text.FontWeight.NORMAL;
// uncomment for Macintosh style
//import com.aquafx_project.*;

public class SkillBlazer extends Application {

    // GUI interface fields
    Button optionsButton;           // options button
    Label appTitle;                 // application title
    Button lifetimeMetricsButton;   // lifetime metrics button

    // setup the main Stage, Scenes and such
    @Override
    public void start(Stage primaryStage) throws Exception {
        // sets title of main window (primaryStage)
        primaryStage.setTitle("Skillblazer Habit Tracker");

        // dropshadow effect for buttons
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        //dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

        // initializes optionsButton
        optionsButton = new Button();
        // sets text of optionsButton
        optionsButton.setText("Options");
        // event handler for 'Options' button
        optionsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // instantiates Options
                Options options = new Options();
            }
        });

        // initializes appTitle
        appTitle = new Label();
        // sets text of appTitle
        appTitle.setText("Skillblazer Habit Tracker");
        // sets font of appTitle
        appTitle.setFont(Font.font("Impact", 30));
        // sets alignment of appTitle to center
        appTitle.setAlignment(Pos.CENTER);

        // initializes lifetimeMetricsButton
        lifetimeMetricsButton = new Button();
        // sets text of lifetimeMetricsButton
        lifetimeMetricsButton.setText("Lifetime Metrics");
        // lambda expression - event handler
        lifetimeMetricsButton.setOnAction(e -> System.out.println("Hello!"));
        // event handler for 'Lifetime Metrics' button
        lifetimeMetricsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // instantiates LifetimeMetrics
                LifetimeMetrics lifetimemetrics = new LifetimeMetrics();
            }
        });

        // sets up layout pane (BorderPane)
        BorderPane borderPane = new BorderPane();

        // hbox layout for top of BorderPane
        HBox hbox = new HBox();
        // necessary to pull css specs from style sheet
        hbox.getStyleClass().add("hbox");
        // sets layout of top of borderPane to hbox 
        borderPane.setTop(hbox);
        // adds optionsButton to hbox
        hbox.getChildren().add(optionsButton);
        // adds appTitle to hbox
        hbox.getChildren().add(appTitle);
        // adds lifetimeMetricsButton to hbox
        hbox.getChildren().add(lifetimeMetricsButton);

        // vbox layout for center of BorderPane
        VBox vbox = new VBox();
        // necessary to pull css specs from style sheet
        vbox.getStyleClass().add("vbox");
        // sets layout of center of borderPane to vbox
        borderPane.setCenter(vbox);

        // adds this pane/layout to the scene
        Scene scene = new Scene(borderPane, 900, 600);

        // adds scene to stage
        primaryStage.setScene(scene);

        // gets css style sheet
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // shows the stage
        primaryStage.show();  // actually displays the scene

        // uncomment for Macintosh style
        //AquaFx.style();
    }

    // inner class for 'Options' menu
    class Options {

        // member fields - GUI elements
        Label habitLabel;
        TextField habitTextField;
        Label goalLabel;
        TextField numTextField;
        ComboBox goalComboBox;
        Label freqLabel;
        RadioButton dailyRB;
        RadioButton weeklyRB;
        RadioButton customRB;
        RadioButton monRB;
        RadioButton tuesRB;
        RadioButton wedRB;
        RadioButton thursRB;
        RadioButton friRB;
        RadioButton satRB;
        RadioButton sunRB;
        RadioButton cumulativeRB;
        Label goalDateLabel;
        TextField dateTextField;
        DatePicker datepicker;
        Label notesLabel;
        TextField notesTextField;
        Button submitButton;

        // constructor
        public Options() {
            // creates new stage
            Stage optionsStage = new Stage();
            // sets title
            optionsStage.setTitle("Options");

            // hbox for 1st vbox row
            HBox hbox1 = new HBox();
            // necessary to pull css specs from style sheet
            hbox1.getStyleClass().add("options");
            // initializes habitLabel
            habitLabel = new Label();
            // sets text of habitLabel
            habitLabel.setText("Habit/Skill:");
            // initializes habitTextField
            habitTextField = new TextField();
            // adds GUI components to hbox1
            hbox1.getChildren().add(habitLabel);
            hbox1.getChildren().add(habitTextField);

            // hbox for 2nd vbox row
            HBox hbox2 = new HBox();
            // necessary to pull css specs from style sheet
            hbox2.getStyleClass().add("options");
            // initializes goalLabel
            goalLabel = new Label();
            // sets text of goalLabel
            goalLabel.setText("Goal to Reach:");
            // initializes numTextField
            numTextField = new TextField();
            // sets max size of numTextField
            numTextField.setMaxSize(80, 80);
            // initializes goalComboBox
            goalComboBox = new ComboBox();
            goalComboBox.getItems().add("minutes");
            goalComboBox.getItems().add("hours");
            goalComboBox.getItems().add("miles");
            // adds GUI components to hbox2
            hbox2.getChildren().add(goalLabel);
            hbox2.getChildren().add(numTextField);
            hbox2.getChildren().add(goalComboBox);

            // hbox for 3rd vbox row
            HBox hbox3 = new HBox();
            // necessary to pull css specs from style sheet
            hbox3.getStyleClass().add("options");
            // initializes freqLabel
            freqLabel = new Label();
            // sets text of freqLabel
            freqLabel.setText("Frequency:");
            // adds GUI components to hbox3
            hbox3.getChildren().add(freqLabel);

            // hbox for 4th vbox row
            HBox hbox4 = new HBox();
            // necessary to pull css specs from style sheet
            hbox4.getStyleClass().add("options");
            // initializes dailyRB
            dailyRB = new RadioButton();
            // sets text of dailyRB
            dailyRB.setText("Daily");
            // adds GUI components to hbox4
            hbox4.getChildren().add(dailyRB);

            // hbox for 5th vbox row
            HBox hbox5 = new HBox();
            // necessary to pull css specs from style sheet
            hbox5.getStyleClass().add("options");
            // initializes weeklyRB
            weeklyRB = new RadioButton();
            // sets text of weeklyRB
            weeklyRB.setText("Weekly");
            // adds GUI components to hbox5
            hbox5.getChildren().add(weeklyRB);

            // hbox for 6th vbox row
            HBox hbox6 = new HBox();
            // necessary to pull css specs from style sheet
            hbox6.getStyleClass().add("options");
            // initializes customRB
            customRB = new RadioButton();
            customRB.setText("Custom");
            // adds GUI components to hbox6
            hbox6.getChildren().add(customRB);

            // hbox for 7th vbox row
            HBox hbox7 = new HBox();
            // necessary to pull css specs from style sheet
            hbox7.getStyleClass().add("hbox7");         
            // initializes monRB
            monRB = new RadioButton();
            monRB.setText("Monday");
            // initializes tuesRB
            tuesRB = new RadioButton();
            tuesRB.setText("Tuesday");
            // initializes wedRB
            wedRB = new RadioButton();
            wedRB.setText("Wednesday");
            // initializes thursRB
            thursRB = new RadioButton();
            thursRB.setText("Thursday");
            // initializes friRB
            friRB = new RadioButton();
            friRB.setText("Friday");
            // initializes satRB
            satRB = new RadioButton();
            satRB.setText("Saturday");
            // initializes sunRB
            sunRB = new RadioButton();
            sunRB.setText("Sunday");
            // adds GUI components to hbox7
            hbox7.getChildren().add(monRB);
            hbox7.getChildren().add(tuesRB);
            hbox7.getChildren().add(wedRB);
            hbox7.getChildren().add(thursRB);
            hbox7.getChildren().add(friRB);
            hbox7.getChildren().add(satRB);
            hbox7.getChildren().add(sunRB);
            
            

            // new vbox layout
            VBox optionsVbox = new VBox();
            // necessary to pull css specs from style sheet
            optionsVbox.getStyleClass().add("secondaryWindow");
            // adds hboxes to vbox layout
            optionsVbox.getChildren().add(hbox1);
            optionsVbox.getChildren().add(hbox2);
            optionsVbox.getChildren().add(hbox3);
            optionsVbox.getChildren().add(hbox4);
            optionsVbox.getChildren().add(hbox5);
            optionsVbox.getChildren().add(hbox6);
            optionsVbox.getChildren().add(hbox7);

            // adds this pane/layout to the scene
            Scene optionsScene = new Scene(optionsVbox, 700, 700);
            // adds scene to stage 
            optionsStage.setScene(optionsScene);
            // gets css style sheet
            optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage
            optionsStage.show();    // actually displays the scene
        }
    }

    // inner class for 'Lifetime Metrics' menu
    class LifetimeMetrics {

        // constructor
        public LifetimeMetrics() {
            // creates new stage
            Stage lifeMetricsStage = new Stage();
            // sets title
            lifeMetricsStage.setTitle("Lifetime Metrics");
            // new vbox layout
            VBox lifeMetricsVbox = new VBox();
            // necessary to pull css specs from style sheet
            lifeMetricsVbox.getStyleClass().add("secondaryWindow");
            // adds this pane/layout to the scene
            Scene lifeMetricsScene = new Scene(lifeMetricsVbox, 600, 600);
            // adds scene to stage 
            lifeMetricsStage.setScene(lifeMetricsScene);
            // gets css style sheet
            lifeMetricsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage
            lifeMetricsStage.show();    // actually displays the scene
        }
    }

    public static void main(String[] args) {
        launch(args);  // opens the JavaFX Stage
    }
}
