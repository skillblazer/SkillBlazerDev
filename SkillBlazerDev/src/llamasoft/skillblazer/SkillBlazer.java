package llamasoft.skillblazer;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.effect.*;
import javafx.scene.text.*;
// uncomment for Macintosh style
//import com.aquafx_project.*;

public class SkillBlazer extends Application {

    // Primary GUI interface fields
    Button optionsButton;           // options button
    Label appTitle;                 // application title
    Button lifetimeMetricsButton;   // lifetime metrics button

    // sets up the main stage, scenes and such
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
        }); // end event handler

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
        
    } // end start() method

    // inner class for 'Options' menu
    class Options {

        // member fields - GUI elements
        Label habitLabel;
        TextField habitTextField;
        Label goalLabel;
        TextField numTextField;
        ComboBox goalComboBox;
        Label freqLabel;
        ToggleGroup rbGroup = new ToggleGroup();
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
        DatePicker datePicker;
        Label notesLabel;
        TextArea notesTextArea;
        Button submitButton;

        // constructor
        public Options() {
            
            // creates new stage
            Stage optionsStage = new Stage();
            // sets title for optionsStage
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
            // adds dailyRB to toggle group
            dailyRB.setToggleGroup(rbGroup);
            // sets 'Daily' option as selected by default
            dailyRB.setSelected(true);
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
            // adds weeklyRB to toggle group
            weeklyRB.setToggleGroup(rbGroup);
            // adds GUI components to hbox5
            hbox5.getChildren().add(weeklyRB);

            // hbox for 6th vbox row
            HBox hbox6 = new HBox();
            // necessary to pull css specs from style sheet
            hbox6.getStyleClass().add("options");
            // initializes customRB
            customRB = new RadioButton();
            // sets text for customRB
            customRB.setText("Custom");
            // adds customRB to toggle group
            customRB.setToggleGroup(rbGroup);
            // adds GUI components to hbox6
            hbox6.getChildren().add(customRB);

            // hbox for 7th vbox row
            HBox hbox7 = new HBox();
            // necessary to pull css specs from style sheet
            hbox7.getStyleClass().add("hbox7");
            // initializes monRB
            monRB = new RadioButton();
            // sets text of monRB
            monRB.setText("Monday");
            // initializes tuesRB
            tuesRB = new RadioButton();
            // sets text of tuesRB
            tuesRB.setText("Tuesday");
            // initializes wedRB
            wedRB = new RadioButton();
            // sets text of wedRB
            wedRB.setText("Wednesday");
            // initializes thursRB
            thursRB = new RadioButton();
            // sets text of thursRB
            thursRB.setText("Thursday");
            // initializes friRB
            friRB = new RadioButton();
            // sets text of friRB
            friRB.setText("Friday");
            // initializes satRB
            satRB = new RadioButton();
            // sets text of satRB
            satRB.setText("Saturday");
            // initializes sunRB
            sunRB = new RadioButton();
            // sets text of sunRB
            sunRB.setText("Sunday");
            // sets these radio buttons as disabled by default
            monRB.setDisable(true);
            tuesRB.setDisable(true);
            wedRB.setDisable(true);
            thursRB.setDisable(true);
            friRB.setDisable(true);
            satRB.setDisable(true);
            sunRB.setDisable(true);
            // adds GUI components to hbox7
            hbox7.getChildren().add(monRB);
            hbox7.getChildren().add(tuesRB);
            hbox7.getChildren().add(wedRB);
            hbox7.getChildren().add(thursRB);
            hbox7.getChildren().add(friRB);
            hbox7.getChildren().add(satRB);
            hbox7.getChildren().add(sunRB);

            // hbox for 8th vbox row
            HBox hbox8 = new HBox();
            // necessary to pull css specs from style sheet
            hbox8.getStyleClass().add("options");
            // initializes cumulativeRB
            cumulativeRB = new RadioButton();
            // sets text for cumulativeRB
            cumulativeRB.setText("Cumulative");
            // adds cumulativeRB to toggle group
            cumulativeRB.setToggleGroup(rbGroup);
            // adds GUI components to hbox8
            hbox8.getChildren().add(cumulativeRB);

            // hbox for 9th vbox row
            HBox hbox9 = new HBox();
            // necessary to pull css specs from style sheet
            hbox9.getStyleClass().add("options");
            // initializes goalDateLabel
            goalDateLabel = new Label();
            // sets text for goalDateLabel
            goalDateLabel.setText("Date to Complete Goal:");  
            // initializes datePicker
            datePicker = new DatePicker();
            // adds GUI components to hbox9
            hbox9.getChildren().add(goalDateLabel);
            hbox9.getChildren().add(datePicker);
            // sets datePicker as disabled
            datePicker.setDisable(true);
                   
            // event handler for customRB
            customRB.setOnAction(e -> {
                monRB.setDisable(false);
                tuesRB.setDisable(false);
                wedRB.setDisable(false);
                thursRB.setDisable(false);
                friRB.setDisable(false);
                satRB.setDisable(false);
                sunRB.setDisable(false);
                datePicker.setDisable(true);
            }); // end event handler
            
            // event handler for weeklyRB
            weeklyRB.setOnAction(e -> {
                monRB.setDisable(true);
                tuesRB.setDisable(true);
                wedRB.setDisable(true);
                thursRB.setDisable(true);
                friRB.setDisable(true);
                satRB.setDisable(true);
                sunRB.setDisable(true);
                datePicker.setDisable(true);
            }); // end event handler
            
            // event handler for dailyRB
            dailyRB.setOnAction(e -> {
                monRB.setDisable(true);
                tuesRB.setDisable(true);
                wedRB.setDisable(true);
                thursRB.setDisable(true);
                friRB.setDisable(true);
                satRB.setDisable(true);
                sunRB.setDisable(true);
                datePicker.setDisable(true);
            }); // end event handler
            
            // event handler for cumulativeRB
            cumulativeRB.setOnAction(e -> {
                    monRB.setDisable(true);
                   tuesRB.setDisable(true);
                    wedRB.setDisable(true);
                    thursRB.setDisable(true);
                   friRB.setDisable(true);
                   satRB.setDisable(true);
                   sunRB.setDisable(true);
                   datePicker.setDisable(false);
                }); // end event handler
            
            // hbox for 10th vbox row
            HBox hbox10 = new HBox();
            // necessary to pull css specs from style sheet
            hbox10.getStyleClass().add("options");
            // initializes notesLabel
            notesLabel = new Label();
            // sets text for notesLabel
            notesLabel.setText("Notes:");
            // initializes notesTextArea
            notesTextArea = new TextArea();
            notesTextArea.setPrefSize(350, 350);
            // adds GUI components to hbox9
            hbox10.getChildren().add(notesLabel);
            hbox10.getChildren().add(notesTextArea);

            // hbox for 11th vbox row
            HBox hbox11 = new HBox();
            // necessary to pull css specs from style sheet
            hbox11.getStyleClass().add("options");
            // initializes submitButton
            submitButton = new Button();
            // sets text for submitButton
            submitButton.setText("Submit");
            // centers hbox that submitButton is placed in
            hbox11.setAlignment(Pos.CENTER);
            // adds GUI components to hbox11
            hbox11.getChildren().add(submitButton);

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
            optionsVbox.getChildren().add(hbox8);
            optionsVbox.getChildren().add(hbox9);
            optionsVbox.getChildren().add(hbox10);
            optionsVbox.getChildren().add(hbox11);

            // adds this pane/layout to the scene
            Scene optionsScene = new Scene(optionsVbox, 700, 700);
            // adds scene to stage 
            optionsStage.setScene(optionsScene);
            // gets css style sheet
            optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage
            optionsStage.show();    // actually displays the scene
        }
    } // end Options class

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
    } // end LifetimeMetrics class
    
    // main method
    public static void main(String[] args) {
        launch(args);  // opens the JavaFX Stage
    } // end main method
    
} // end Skillblazer class
