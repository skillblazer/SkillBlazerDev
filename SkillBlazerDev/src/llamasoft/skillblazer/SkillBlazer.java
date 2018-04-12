package llamasoft.skillblazer;

import java.time.LocalDate;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.effect.*;
// uncomment for Macintosh style
//import com.aquafx_project.*;

public class SkillBlazer extends Application {

    // Primary GUI interface fields
    Button optionsButton;           // options button
    Label appTitle;                 // application title
    Button lifetimeMetricsButton;   // lifetime metrics button
    Label currentMonthYearLabel;     // label for current month and year
    Button forwardMonthButton;       // button to move month forward
    Button backMonthButton;          // button to move month back
    CalendarCalculator calCalc;      // CalendarCalculator object
    TilePane calendarPane;           // tilepane object for calendar
    Label monLabel;
    Label tuesLabel;
    Label wedLabel;
    Label thursLabel;
    Label friLabel;
    Label satLabel;
    Label sunLabel;
    VBox[] vboxArray = new VBox[49];
    Button habitCreationButton;

    // TO DO:  Add Calendar GUI features - TilePane (smaller for days of the week, larger for eeach day block)
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
        calCalc = new CalendarCalculator();
//        System.out.println(calCalc.getCurrentMonthString());      // Used for testing display of month
        // initializes optionsButton
        optionsButton = new Button();
        // sets text of optionsButton
        optionsButton.setText("Options");
        optionsButton.setPrefWidth(120);
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
        // pulls css specs from style sheet
        appTitle.getStyleClass().add("appTitle");
        // sets text of appTitle
        appTitle.setText("Skillblazer Habit Tracker");
        Region region1 = new Region();
        //            vboxLabel.setAlignment(Pos.CENTER);
        //            vboxLabel.setTextAlignment(Pos.TOP_RIGHT);
        HBox.setHgrow(region1, Priority.ALWAYS);
        // sets alignment of appTitle to center
        //appTitle.setAlignment(Pos.CENTER);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        // initializes currentMonthYearLabel
        currentMonthYearLabel = new Label();
        // pulls css specs from style sheet
        currentMonthYearLabel.getStyleClass().add("currentMonthYearLabel");
        // sets text of currentMonthYearLabel by calling two methods in CalendarCalculator class
        currentMonthYearLabel.setText(" " + calCalc.getCurrentMonthString() + " " + calCalc.getCurrentYearInt() + " ");
        // initializes forwardMonthButton
        forwardMonthButton = new Button();
        // sets text of forwardMonthButton
        forwardMonthButton.setText(">>");
        // event handler for forwardMonthButton
        forwardMonthButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                calCalc.changeMonthForward();
                // sets text of currentMonthYearLabel by calling two methods in CalendarCalculator class
                currentMonthYearLabel.setText(" " + calCalc.getCurrentMonthString() + " " + calCalc.getCurrentYearInt() + " ");
                drawCalendar();

            }
        }); // end event handler
        // initializes backMonthButton
        backMonthButton = new Button();
        // sets text of backMonthButton
        backMonthButton.setText("<<");
        // event handler for backMonthButton
        backMonthButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                calCalc.changeMonthBackward();
                // sets text of currentMonthYearLabel by calling two methods in CalendarCalculator class
                currentMonthYearLabel.setText(" " + calCalc.getCurrentMonthString() + " " + calCalc.getCurrentYearInt() + " ");
                drawCalendar();
            }
        }); // end event handler

        // initializes lifetimeMetricsButton
        lifetimeMetricsButton = new Button();
        // sets text of lifetimeMetricsButton
        lifetimeMetricsButton.setText("Lifetime Metrics");
        lifetimeMetricsButton.setPrefWidth(120);
        // lambda expression - event handler
        //lifetimeMetricsButton.setOnAction(e -> System.out.println("Hello!"));
        // event handler for 'Lifetime Metrics' button
        lifetimeMetricsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // instantiates LifetimeMetrics
                LifetimeMetrics lifetimemetrics = new LifetimeMetrics();
            }
        }); // end event handler

        BorderPane borderPaneMain = new BorderPane();
        // sets up layout pane (VBox)
        VBox vboxMain = new VBox();

        // hbox layout for top of screen
        HBox hboxTop = new HBox();
        // pulls css specs from style sheet
        hboxTop.getStyleClass().add("vboxMain");
        // adds hbox to vboxMain
        vboxMain.getChildren().add(hboxTop);
        // adds optionsButton to hbox
        hboxTop.getChildren().add(optionsButton);
        hboxTop.getChildren().add(region1);
        // adds appTitle to hbox
        hboxTop.getChildren().add(appTitle);
        hboxTop.getChildren().add(region2);

        // adds lifetimeMetricsButton to hbox
        hboxTop.getChildren().add(lifetimeMetricsButton);

        // hbox layout for current month/year and back/forward buttons
        HBox hboxMonthYear = new HBox();
        // pulls css specs from style sheet
        hboxMonthYear.getStyleClass().add("vboxMain");
        // sets layout of center of borderPane to hbox 
        vboxMain.getChildren().add(hboxMonthYear);
        // sets alignment for hbox2
        hboxMonthYear.setAlignment(Pos.TOP_CENTER);
        // adds backMonthButton to hbox
        hboxMonthYear.getChildren().add(backMonthButton);
        // adds currentMonthLabel to hbox
        hboxMonthYear.getChildren().add(currentMonthYearLabel);
        // adds forwardMonthButton to hbox
        hboxMonthYear.getChildren().add(forwardMonthButton);

//        HBox hboxDays = new HBox();
//        vboxMain.getChildren().add(hboxDays);
//        monLabel = new Label("Monday");
//        tuesLabel = new Label("Tuesday");
//        wedLabel = new Label("Wednesday");
//        thursLabel = new Label("Thursday");
//        friLabel = new Label("Friday");
//        satLabel = new Label("Saturday");
//        sunLabel = new Label("Sunday");
//        hboxDays.getStylesheets().add("days");
//        hboxDays.getChildren().add(sunLabel);
//        hboxDays.getChildren().add(monLabel);
//        hboxDays.getChildren().add(tuesLabel);
//        hboxDays.getChildren().add(wedLabel);
//        hboxDays.getChildren().add(thursLabel);
//        hboxDays.getChildren().add(friLabel);
//        hboxDays.getChildren().add(satLabel);
//        hboxDays.getStyleClass().add("hbox3");
        // initializes calendarPane
        calendarPane = new TilePane();
        calendarPane.getStyleClass().add("calendarPane");
        calendarPane.setLayoutX(140);
        calendarPane.setLayoutY(50);
        //calendarPane.setTileAlignment(Pos.CENTER);
        //calendarPane.setAlignment(Pos.CENTER);
        calendarPane.setPrefRows(7);
        calendarPane.setPrefColumns(7);

        drawCalendar();

        HBox hboxCalendar = new HBox();
        // necessary to pull css specs from style sheet
        hboxCalendar.getStyleClass().add("vboxMain");
        hboxCalendar.setPrefHeight(500);
        hboxCalendar.getChildren().add(calendarPane);
        hboxCalendar.setAlignment(Pos.CENTER);
        vboxMain.getChildren().add(hboxCalendar);

        // adds this pane/layout to the scene
//        Scene scene = new Scene(vboxMain, 900, 800);
        borderPaneMain.setCenter(vboxMain);
        borderPaneMain.getStyleClass().add("vboxMain");
        Scene scene = new Scene(borderPaneMain, 1000, 800);
        // adds scene to stage
        primaryStage.setScene(scene);

        // gets css style sheet
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // shows the stage
        primaryStage.show();  // actually displays the scene

        // uncomment for Macintosh style
        //AquaFx.style();
    } // end start() method

    private void drawCalendar() {
        int firstWeekdayMonth = calCalc.getFirstDayOfWeekCurrentMonth();
        int numberDaysCurrentMonth = calCalc.getDaysInCurrentMonth();
        int j = 0;
        calendarPane.getChildren().clear();

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < 7; i++) {
            vboxArray[i] = new VBox();
            HBox hboxCal = new HBox();
            Label vboxLabel = new Label(daysOfWeek[i]);
            vboxLabel.getStyleClass().add("dayOfWeekLabels");

            Region emptyRegion1 = new Region();
            Region emptyRegion2 = new Region();
            Region emptyRegion3 = new Region();
            HBox.setHgrow(emptyRegion3, Priority.ALWAYS);
            HBox.setHgrow(emptyRegion2, Priority.ALWAYS);
            VBox.setVgrow(emptyRegion1, Priority.ALWAYS);
            hboxCal.getChildren().add(emptyRegion3);
            hboxCal.getChildren().add(vboxLabel);
            hboxCal.getChildren().add(emptyRegion2);
            vboxArray[i].getChildren().add(emptyRegion1);
            vboxArray[i].getChildren().add(hboxCal);
            calendarPane.getChildren().add(vboxArray[i]);
        }
    
        for (int i = 7; i < vboxArray.length; i++) {

            vboxArray[i] = new VBox();
            vboxArray[i].setPrefSize(120, 85);
            if (((i - 7) >= firstWeekdayMonth) && j < numberDaysCurrentMonth) {
                vboxArray[i].getStyleClass().add("vboxCalendar");
                HBox hboxCal = new HBox();
                j++;
                Label vboxLabel = new Label("" + j);
                Region emptyRegion = new Region();
                Button vboxButton = new Button("+");
                vboxButton.setPrefSize(20, 20);
                HBox.setHgrow(emptyRegion, Priority.ALWAYS);    
                vboxButton.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        // instantiates Options
                        CalendarButton calButton = new CalendarButton();
                    }

                }); // end event handler
                hboxCal.getChildren().add(vboxButton);
                hboxCal.getChildren().add(emptyRegion);
                hboxCal.getChildren().add(vboxLabel);

                vboxArray[i].getChildren().add(hboxCal);
            }
            // "Habit Creation Button"
            if(i==47) {
                habitCreationButton = new Button("Create Habit");
                habitCreationButton.getStyleClass().add("habitCreationButton");
                Region emptyRegion1 = new Region();
                Region emptyRegion2 = new Region();
                VBox.setVgrow(emptyRegion1, Priority.ALWAYS);
                VBox.setVgrow(emptyRegion2, Priority.ALWAYS);
                vboxArray[i].getChildren().add(emptyRegion1);
                vboxArray[i].getChildren().add(habitCreationButton);
                vboxArray[i].getChildren().add(emptyRegion2);
                habitCreationButton.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        // instantiates Options
                        CalendarButton calButton = new CalendarButton();
                    }

                }); // end event handler
            }
            
            
            calendarPane.getChildren().add(vboxArray[i]);
        }

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
            Stage habitEntryStage = new Stage();
            // sets title
            habitEntryStage.setTitle("Options");
            // new vbox layout
            VBox habitEntryVBox = new VBox();
            // necessary to pull css specs from style sheet
            habitEntryVBox.getStyleClass().add("secondaryWindow");
            // adds this pane/layout to the scene
            Scene habitEntryScene = new Scene(habitEntryVBox, 600, 680);
            // adds scene to stage 
            habitEntryStage.setScene(habitEntryScene);
            // gets css style sheet
            habitEntryScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage
            habitEntryStage.show();    // actually displays the scene
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

    // inner class for handling when user clicks on a calendar button
    class CalendarButton {

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
        Label startDateLabel;
        DatePicker startDatePicker;
        Label notesLabel;
        TextArea notesTextArea;
        Button submitButton;

        // constructor
        public CalendarButton() {

            // creates new stage
            Stage optionsStage = new Stage();
            // sets title for optionsStage
            optionsStage.setTitle("Habit Entry");

             // hbox for 1st vbox row
            HBox hbox1 = new HBox();
            hbox1.getStyleClass().add("options");
            // initializes startDateLabel
            startDateLabel = new Label();
            // sets text for goalDateLabel
            startDateLabel.setText("Date to Start Goal:");
            startDatePicker = new DatePicker();
            startDatePicker.setValue(LocalDate.now());
            hbox1.getChildren().add(startDateLabel);
            hbox1.getChildren().add(startDatePicker);
            
            
            // hbox for 2nd vbox row
            HBox hbox2 = new HBox();
            // necessary to pull css specs from style sheet
            hbox2.getStyleClass().add("options");
            // initializes habitLabel
            habitLabel = new Label();
            // sets text of habitLabel
            habitLabel.setText("Habit/Skill:");
            // initializes habitTextField
            habitTextField = new TextField();
            // adds GUI components to hbox1
            hbox2.getChildren().add(habitLabel);
            hbox2.getChildren().add(habitTextField);
            
            
            

            // hbox for 2nd vbox row
            HBox hbox3 = new HBox();
            // necessary to pull css specs from style sheet
            hbox3.getStyleClass().add("options");
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
            hbox3.getChildren().add(goalLabel);
            hbox3.getChildren().add(numTextField);
            hbox3.getChildren().add(goalComboBox);

           
            
            
            // hbox for 3rd vbox row
            HBox hbox4 = new HBox();
            // necessary to pull css specs from style sheet
            hbox4.getStyleClass().add("options");
            // initializes freqLabel
            freqLabel = new Label();
            // sets text of freqLabel
            freqLabel.setText("Frequency:");
            // adds GUI components to hbox3
            hbox4.getChildren().add(freqLabel);

            // hbox for 4th vbox row
            HBox hbox5 = new HBox();
            // necessary to pull css specs from style sheet
            hbox5.getStyleClass().add("options");
            // initializes dailyRB
            dailyRB = new RadioButton();
            // sets text of dailyRB
            dailyRB.setText("Daily");
            // adds dailyRB to toggle group
            dailyRB.setToggleGroup(rbGroup);
            // sets 'Daily' option as selected by default
            dailyRB.setSelected(true);
            // adds GUI components to hbox4
            hbox5.getChildren().add(dailyRB);

            // hbox for 5th vbox row
            HBox hbox6 = new HBox();
            // necessary to pull css specs from style sheet
            hbox6.getStyleClass().add("options");
            // initializes weeklyRB
            weeklyRB = new RadioButton();
            // sets text of weeklyRB
            weeklyRB.setText("Weekly");
            // adds weeklyRB to toggle group
            weeklyRB.setToggleGroup(rbGroup);
            // adds GUI components to hbox5
            hbox6.getChildren().add(weeklyRB);

            // hbox for 6th vbox row
            HBox hbox7 = new HBox();
            // necessary to pull css specs from style sheet
            hbox7.getStyleClass().add("options");
            // initializes customRB
            customRB = new RadioButton();
            // sets text for customRB
            customRB.setText("Custom");
            // adds customRB to toggle group
            customRB.setToggleGroup(rbGroup);
            // adds GUI components to hbox6
            hbox7.getChildren().add(customRB);

            // hbox for 7th vbox row
            HBox hbox8 = new HBox();
            // necessary to pull css specs from style sheet
            hbox8.getStyleClass().add("hbox8");
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
            hbox8.getChildren().add(monRB);
            hbox8.getChildren().add(tuesRB);
            hbox8.getChildren().add(wedRB);
            hbox8.getChildren().add(thursRB);
            hbox8.getChildren().add(friRB);
            hbox8.getChildren().add(satRB);
            hbox8.getChildren().add(sunRB);

            // hbox for 8th vbox row
            HBox hbox9 = new HBox();
            // necessary to pull css specs from style sheet
            hbox9.getStyleClass().add("options");
            // initializes cumulativeRB
            cumulativeRB = new RadioButton();
            // sets text for cumulativeRB
            cumulativeRB.setText("Cumulative");
            // adds cumulativeRB to toggle group
            cumulativeRB.setToggleGroup(rbGroup);
            // adds GUI components to hbox8
            hbox9.getChildren().add(cumulativeRB);

            // hbox for 9th vbox row
            HBox hbox10 = new HBox();
            // necessary to pull css specs from style sheet
            hbox10.getStyleClass().add("options");
            // initializes goalDateLabel
            goalDateLabel = new Label();
            // sets text for goalDateLabel
            goalDateLabel.setText("Date to Complete Goal:");
            // initializes datePicker
            datePicker = new DatePicker();
            
            
            // adds GUI components to hbox9
            hbox10.getChildren().add(goalDateLabel);
            hbox10.getChildren().add(datePicker);
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
            HBox hbox11 = new HBox();
            // necessary to pull css specs from style sheet
            hbox11.getStyleClass().add("options");
            // initializes notesLabel
            notesLabel = new Label();
            // sets text for notesLabel
            notesLabel.setText("Notes:");
            // initializes notesTextArea
            notesTextArea = new TextArea();
            notesTextArea.setPrefSize(350, 350);
            // adds GUI components to hbox9
            hbox11.getChildren().add(notesLabel);
            hbox11.getChildren().add(notesTextArea);

            // hbox for 11th vbox row
            HBox hbox12 = new HBox();
            // necessary to pull css specs from style sheet
            hbox12.getStyleClass().add("options");
            // initializes submitButton
            submitButton = new Button();
            // sets text for submitButton
            submitButton.setText("Submit");
            
            submitButton.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        
                        // TO DO: Save to JSON file
                        
                        optionsStage.close();
                     
                    }});
            
            
            // centers hbox that submitButton is placed in
            hbox12.setAlignment(Pos.CENTER);
            // adds GUI components to hbox11
            hbox12.getChildren().add(submitButton);

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
            optionsVbox.getChildren().add(hbox12);
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

    // main method
    public static void main(String[] args) {
        launch(args);  // opens the JavaFX Stage
    } // end main method

} // end Skillblazer class
