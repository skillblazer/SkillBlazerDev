/************************************************************
 * Application Name: skillblazer
 * File Name: Skillblazer.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
 * 
 * Description:
 * 
 * This class holds all of the field and methods related to the
 * primary GUI and secondary windows. It includes the necessary
 * JavaFX start() method, as well as the main method to run
 * the application. Other methods exist in this class to assist
 * in adding habit/task information to the calendar interface.
 ***********************************************************/

// package
package llamasoft.skillblazer;

// imports
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import twitter4j.TwitterException;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import java.util.GregorianCalendar;
// uncomment for Macintosh style
//import com.aquafx_project.*;

// main class; extends Application
public class SkillBlazer extends Application {

    // primary GUI interface fields
    private Button optionsButton;                                                                       // options button
    private Label appTitle;                                                                             // application title
    private Button lifetimeMetricsButton;                                                               // lifetime metrics button
    private Label currentMonthYearLabel;                                                                // label for current month and year
    private Button forwardMonthButton;                                                                  // button to move month forward
    private Button backMonthButton;                                                                     // button to move month back
    private CalendarCalculator calCalc;                                                                 // CalendarCalculator object
    private TilePane calendarPane;                                                                      // tilepane object for calendar
    private VBox[] vboxArray = new VBox[49];                                                            // vbox array for main calendar interface
    private Button habitCreationButton;                                                                 // button for habit creation
    private ArrayList<Task> taskList;                                                                   // ArrayList holding tasks
    private static final String[] dayNamesOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"};                                                          // String array holding names for days of week

    // These objects will conduct the startup routine
    static JSONLoader jsonLoader = new JSONLoader(); // also provides an instance of SkillBlazerInitializer skillBlazerInit
    static JSONWriter jsonWriter = new JSONWriter(); // may not be necessary

    // sets up the main stage, scenes and such
    @Override
    public void start(Stage primaryStage) throws Exception {
        // instantiates taskList
        taskList = new ArrayList<Task>();
        // 'for' loop
        for (int i = 0; i < 5; i++) {
            // GregorianCalendar object
            GregorianCalendar testStartDate = new GregorianCalendar(2018, 4, 1 + 2 * i);
            // DailyTask object
            DailyTask testTask = new DailyTask("Test " + i, i, testStartDate, false, 0, 0);
            // adds testTask to taskList
            taskList.add(testTask);
        } // end 'for' loop

        // sets title of main window (primaryStage)
        primaryStage.setTitle("Skillblazer Habit Tracker");
        // adds Icon to primaryStage
        primaryStage.getIcons().add(new Image("/llama.jpg"));

        // dropshadow effect for buttons
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);

        // instantiates CalendarCalculator
        calCalc = new CalendarCalculator();

        // initializes optionsButton
        optionsButton = new Button();
        // sets text of optionsButton
        optionsButton.setText("Options");
        // sets preferred width of optionsButton
        optionsButton.setPrefWidth(120);
        optionsButton.getStyleClass().add("button1");
        // event handler for 'Options' button
        optionsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // instantiates Options
                Options options = new Options();
            }
        }); // end event handler

        // initializes appTitle
        appTitle = new Label();
        // pulls css specs from style sheet
        appTitle.getStyleClass().add("appTitle");
        // sets text of appTitle
        appTitle.setText("Skillblazer Habit Tracker");
        // creates new region (for layout/alignment purposes)
        Region region1 = new Region();
        // for layout/alignment purposes
        HBox.setHgrow(region1, Priority.ALWAYS);
        // creates new region (for layout/alignment purposes)
        Region region2 = new Region();
        // for layout/alignment purposes
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
        forwardMonthButton.getStyleClass().add("button1");
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
        backMonthButton.getStyleClass().add("button1");
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
        // sets preferred width of lifetimeMetricsButton
        lifetimeMetricsButton.setPrefWidth(120);
        lifetimeMetricsButton.getStyleClass().add("button1");
        // event handler for 'Lifetime Metrics' button
        lifetimeMetricsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // instantiates LifetimeMetrics
                LifetimeMetrics lifetimemetrics = new LifetimeMetrics();
            }
        }); // end event handler

        // creation of BorderPane for the overall layout
        BorderPane borderPaneMain = new BorderPane();
        // sets up layout pane (VBox); will be placed in borderPaneMain
        VBox vboxMain = new VBox();

        // hbox layout for top of screen
        HBox hboxTop = new HBox();
        // pulls css specs from style sheet
        hboxTop.getStyleClass().add("vboxMain");
        // adds hbox to vboxMain
        vboxMain.getChildren().add(hboxTop);
        // adds optionsButton to hboxTop
        hboxTop.getChildren().add(optionsButton);
        // adds region1 to hboxTop
        hboxTop.getChildren().add(region1);
        // adds appTitle to hboxTop
        hboxTop.getChildren().add(appTitle);
        // adds appTitle to region2
        hboxTop.getChildren().add(region2);
        // adds lifetimeMetricsButton to hbox
        hboxTop.getChildren().add(lifetimeMetricsButton);

        // hbox layout for current month/year and back/forward buttons
        HBox hboxMonthYear = new HBox();
        // pulls css specs from style sheet
        hboxMonthYear.getStyleClass().add("vboxMain");
        // sets layout of center of borderPane to hbox 
        vboxMain.getChildren().add(hboxMonthYear);
        // sets alignment for hboxMonthYear
        hboxMonthYear.setAlignment(Pos.TOP_CENTER);
        // adds backMonthButton to hboxMonthYear
        hboxMonthYear.getChildren().add(backMonthButton);
        // adds currentMonthLabel to hboxMonthYear
        hboxMonthYear.getChildren().add(currentMonthYearLabel);
        // adds forwardMonthButton to hboxMonthYear
        hboxMonthYear.getChildren().add(forwardMonthButton);

        // initializes calendarPane
        calendarPane = new TilePane();
        // pulls css specs from style sheet
        calendarPane.getStyleClass().add("calendarPane");
        // sets layoutX for calendarPane
        calendarPane.setLayoutX(140);
        // sets layoutY for calendarPane
        calendarPane.setLayoutY(50);
        // sets preferred rows for calendarPane
        calendarPane.setPrefRows(7);
        // sets preferred columns for calendarPane
        calendarPane.setPrefColumns(7);

        // call to drawCalendar() method, which is responsible for creating calendar
        drawCalendar();

        // hbox layout for calendarPane
        HBox hboxCalendar = new HBox();
        // pulls css specs from style sheet
        hboxCalendar.getStyleClass().add("vboxMain");
        // sets preferred height of hboxCalendar
        hboxCalendar.setPrefHeight(500);
        // adds calendarPane to hboxCalendar
        hboxCalendar.getChildren().add(calendarPane);
        // sets alignment of hboxCalendar
        hboxCalendar.setAlignment(Pos.CENTER);
        // adds hboxCalendar to vboxMain
        vboxMain.getChildren().add(hboxCalendar);

        // adds vboxMain to center region of borderPaneMain
        borderPaneMain.setCenter(vboxMain);
        // pulls css specs from style sheet
        borderPaneMain.getStyleClass().add("vboxMain");
        // creates a new scene and adds borderPaneMain
        Scene scene = new Scene(borderPaneMain, 1000, 800);
        // adds scene to stage
        primaryStage.setScene(scene);

        // pulls css style sheet
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        // shows the stage; actually displays the scen
        primaryStage.show();

    } // end start() method

    // method to populate calendar days with task information; called in drawCalendar() method
    private void populateDays() {
        // int that holds 1st day of week
        int firstDayOfWeekCurrentMonth = calCalc.getFirstDayOfWeekCurrentMonth();
        // int that holds # of days
        int numberDaysCurrentMonth = calCalc.getDaysInCurrentMonth();
        // int to hold day of week
        int dayOfWeek = firstDayOfWeekCurrentMonth;
        // nested 'for' loop
        for (int i = 0; i < numberDaysCurrentMonth; i++) {
            for (Task mt : taskList) {
                // if Task object is an instance of DailyTask
                if (mt instanceof DailyTask) {
                    // Calendar object representing task start date
                    Calendar taskStartDate = ((DailyTask) mt).getStartDate();
                    if (calCalc.getDayObject(i).getTaskDate().compareTo(taskStartDate) >= 0) {
                        // adds Task objects to Day objects of calCalc
                        calCalc.getDayObject(i).addTask(mt);
                    }
                    // else if Task object is an instance of WeeklyTask
                } else if (mt instanceof WeeklyTask) {
                    // Calendar object representing task start date
                    Calendar taskStartDate = ((WeeklyTask) mt).getStartDate();
                    if (calCalc.getDayObject(i).getTaskDate().compareTo(taskStartDate) >= 0) {
                        if (dayOfWeek == 6) {
                            // adds Task objects to Day objects of calCalc
                            calCalc.getDayObject(i).addTask(mt);
                        }
                    }
                    // else if Task object is an instance of CustomTask
                } else if (mt instanceof CustomTask) {
                    // Calendar object representing task start date
                    Calendar taskStartDate = ((CustomTask) mt).getStartDate();
                    if (calCalc.getDayObject(i).getTaskDate().compareTo(taskStartDate) >= 0) {
                        ArrayList<String> daysOfWeek = ((CustomTask) mt).getDaysOfWeek();
                        // boolean that gets set to true if the days of the week shows up in the ArrayList for the days of the week the task has
                        boolean todayActive = false;
                        for (String md : daysOfWeek) {
                            todayActive |= md.equalsIgnoreCase(dayNamesOfWeek[dayOfWeek]);
                        }
                        if (todayActive) {
                            // adds Task objects to Day objects of calCalc
                            calCalc.getDayObject(i).addTask(mt);
                        }
                    }
                    // else if Task object is an instance of CumulativeTask
                } else if (mt instanceof CumulativeTask) {
                    // Calendar object representing task end date
                    Calendar taskEndDate = ((CumulativeTask) mt).getEndDate();
                    if (calCalc.getDayObject(i).getTaskDate().compareTo(taskEndDate) == 0) {
                        // adds Task objects to Day objects of calCalc
                        calCalc.getDayObject(i).addTask(mt);
                    }
                }
            }
            // week day update
            dayOfWeek = (dayOfWeek + 1) % 7;
        }
    } // end populateDays() method

    // drawCalendar() method; responsible for creating the calendar
    private void drawCalendar() {
        // generates day objects for selected calendar month
        calCalc.instantiateCalendar();
        // populates calendar days with task information
        populateDays();
        // int that holds 1st day of week
        int firstDayOfWeekCurrentMonth = calCalc.getFirstDayOfWeekCurrentMonth();
        // int that holds # of days
        int numberDaysCurrentMonth = calCalc.getDaysInCurrentMonth();
        // iterator
        int j = 0;
        // string array holding days
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"};

        // clears calendar interface
        calendarPane.getChildren().clear();

        // 'for' loop used to populate days of the week in top of calendar interface
        for (int i = 0; i < 7; i++) {
            // initializes vboxArray
            vboxArray[i] = new VBox();
            // creates and initializes hboxCal
            HBox hboxCal = new HBox();
            // creates and initializes; fills it with string value from daysOfWeek
            Label vboxLabel = new Label(daysOfWeek[i]);
            // pulls css specs from style sheet
            vboxLabel.getStyleClass().add("dayOfWeekLabels");
            // creates new region (for layout/alignment purposes)
            Region emptyRegion1 = new Region();
            // creates new region (for layout/alignment purposes)
            Region emptyRegion2 = new Region();
            // creates new region (for layout/alignment purposes)
            Region emptyRegion3 = new Region();
            // for layout/alignment purposes
            HBox.setHgrow(emptyRegion3, Priority.ALWAYS);
            // for layout/alignment purposes
            HBox.setHgrow(emptyRegion2, Priority.ALWAYS);
            // for layout/alignment purposes
            VBox.setVgrow(emptyRegion1, Priority.ALWAYS);
            // adds emptyRegion3 to hboxCal
            hboxCal.getChildren().add(emptyRegion3);
            // adds vboxLabel to hboxCal
            hboxCal.getChildren().add(vboxLabel);
            // adds emptyRegion2 to hboxCal
            hboxCal.getChildren().add(emptyRegion2);
            // adds emptyRegion1 to vboxArray
            vboxArray[i].getChildren().add(emptyRegion1);
            // adds hboxCal to vboxArray
            vboxArray[i].getChildren().add(hboxCal);
            // adds vboxArray[i] to calendarPane
            calendarPane.getChildren().add(vboxArray[i]);
        } // end 'for' loop

        // 'for' loop used to populate actual calendar boxes of calendar interface
        for (int i = 7; i < vboxArray.length; i++) {
            // initializes vboxArray
            vboxArray[i] = new VBox();
            // sets preferred size of vboxArray
            vboxArray[i].setPrefSize(120, 85);
            // conditional statement
            if (((i - 7) >= firstDayOfWeekCurrentMonth) && j < numberDaysCurrentMonth) {
                // Day object; getDayObject() called
                Day todayDayOb = calCalc.getDayObject(j);
                // pulls css specs from style sheet
                vboxArray[i].getStyleClass().add("vboxCalendar");
                // creates and initializes hboxCal
                HBox hboxCal = new HBox();
                // int j iterates
                j++;
                // creates and initializes vboxLabel
                Label vboxLabel = new Label("" + j);
                // creates new region (for layout/alignment purposes)
                Region emptyRegion = new Region();
                // creates and initializes vboxButton
                Button vboxButton = new Button("+");
                vboxButton.getStyleClass().add("button2");
                // sets preferred size of vboxButton
                vboxButton.setPrefSize(15, 15);
                // for layout/alignment purposes
                HBox.setHgrow(emptyRegion, Priority.ALWAYS);
                // event handler for vboxButton
                vboxButton.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        // instantiates progressButton
                        ProgressButton progressButton = new ProgressButton();
                    }
                }); // end event handler
                // adds vboxButton to hboxCal
                hboxCal.getChildren().add(vboxButton);
                // adds emptyRegion to hboxCal
                hboxCal.getChildren().add(emptyRegion);
                // adds vboxLabel to hboxCal
                hboxCal.getChildren().add(vboxLabel);
                // adds hboxCal to vboxArray[i]
                vboxArray[i].getChildren().add(hboxCal);
                // HBox array which is the size of tasksThisDay
                HBox[] taskHboxArray = new HBox[todayDayOb.tasksThisDay.size()];
                int k = 0;
                // adds Tasks to calendar day
                for (Task mt : todayDayOb.tasksThisDay) {
                    // label for each Task
                    Label taskLabel = new Label(mt.getTaskName());
                    // hbox for each task
                    taskHboxArray[k] = new HBox();
                    // adds taskHbox to vboxArray
                    vboxArray[i].getChildren().add(taskHboxArray[k]);
                    // adds taskLabel to taskHbox
                    taskHboxArray[k].getChildren().add(taskLabel);
                    k++;
                }
                // if there are more than 3 tasks for a given calendar day
                if (todayDayOb.tasksThisDay.size() > 4) {
                    for (k = 3; k < todayDayOb.tasksThisDay.size(); k++) {
                        taskHboxArray[k].setVisible(false);
                        taskHboxArray[k].setManaged(false);
                    }
                    // creates hbox for button
                    HBox buttonHbox = new HBox();
                    // creates new region (for layout/alignment purposes)
                    Region emptyRegionButton1 = new Region();
                    // for layout/alignment purposes
                    HBox.setHgrow(emptyRegionButton1, Priority.ALWAYS);
                    // creates new region (for layout/alignment purposes)
                    Region emptyRegionButton2 = new Region();
                    // for layout/alignment purposes
                    HBox.setHgrow(emptyRegionButton2, Priority.ALWAYS);
                    // creates new region (for layout/alignment purposes)
                    Region emptyRegionButton3 = new Region();
                    // for layout/alignment purposes
                    HBox.setHgrow(emptyRegionButton3, Priority.ALWAYS);
                    // down arrow button
                    Button downArrowButton = new Button("▼");
                    // pulls css specs from style sheet
                    downArrowButton.getStyleClass().add("button2");
                    // up arrow button
                    Button upArrowButton = new Button("▲");
                    // pulls css specs from style sheet
                    upArrowButton.getStyleClass().add("button2");
                    // event handler for downArrowButton
                    downArrowButton.setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            int lastVisible = -1;
                            for (int m = 0; m < todayDayOb.tasksThisDay.size(); m++) {
                                if (taskHboxArray[m].isVisible()) {
                                    lastVisible = m;
                                }
                                taskHboxArray[m].setVisible(false);
                                taskHboxArray[m].setManaged(false);
                            }
                            int newFirstVisible = lastVisible + 1;
                            if ((newFirstVisible + 3) > todayDayOb.tasksThisDay.size()) {
                                newFirstVisible = todayDayOb.tasksThisDay.size() - 3;
                            }
                            for (int m = newFirstVisible; m < (newFirstVisible + 3); m++) {
                                taskHboxArray[m].setVisible(true);
                                taskHboxArray[m].setManaged(true);
                            }
                        }
                    }); // end event handler
                    // event handler for upArrowButton
                    upArrowButton.setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            int firstVisible = -1;
                            for (int m = todayDayOb.tasksThisDay.size() - 1; m >= 0; m--) {
                                if (taskHboxArray[m].isVisible()) {
                                    firstVisible = m;
                                }
                                taskHboxArray[m].setVisible(false);
                                taskHboxArray[m].setManaged(false);
                            }
                            int newFirstVisible = firstVisible - 3;
                            if (newFirstVisible < 0) {
                                newFirstVisible = 0;
                            }
                            for (int m = newFirstVisible; m < (newFirstVisible + 3); m++) {
                                taskHboxArray[m].setVisible(true);
                                taskHboxArray[m].setManaged(true);
                            }
                        }
                    }); // end event handler

                    // sets alignment for buttonHbox
                    buttonHbox.setAlignment(Pos.CENTER);
                    // adds buttonHbox to vboxArray
                    vboxArray[i].getChildren().add(buttonHbox);
                    // adds emptyRegionButton1 to buttonHbox
                    buttonHbox.getChildren().add(emptyRegionButton1);
                    // adds downArrowButton to buttonHbox
                    buttonHbox.getChildren().add(downArrowButton);
                    // adds emptyRegionButton2 to buttonHbox
                    buttonHbox.getChildren().add(emptyRegionButton2);
                    // adds upArrowButton to buttonHbox
                    buttonHbox.getChildren().add(upArrowButton);
                    // adds emptyRegionButton3 to buttonHbox
                    buttonHbox.getChildren().add(emptyRegionButton3);
                }
            }

            // "Create Habit/Skill Button"
            if (i == 47) {
                // initializes habitCreationButton
                habitCreationButton = new Button("Create Habit/Skill");
                // pulls css specs from style sheet
                habitCreationButton.getStyleClass().add("button1");
                // creates new region (for layout/alignment purposes)
                Region emptyRegion1 = new Region();
                // creates new region (for layout/alignment purposes)
                Region emptyRegion2 = new Region();
                // for layout/alignment purposes
                VBox.setVgrow(emptyRegion1, Priority.ALWAYS);
                // for layout/alignment purposes
                VBox.setVgrow(emptyRegion2, Priority.ALWAYS);
                // adds emptyRegion1 to vboxArray
                vboxArray[i].getChildren().add(emptyRegion1);
                // adds habitCreationButton to vboxArray
                vboxArray[i].getChildren().add(habitCreationButton);
                // adds emptyRegion2 to vboxArray
                vboxArray[i].getChildren().add(emptyRegion2);
                // event handler for habitCreationButton
                habitCreationButton.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        // instantiates Options
                        HabitCreationButton habitCreationButton = new HabitCreationButton();
                    }
                }); // end event handler
            }
            // adds vboxArray[i] to calendarPane
            calendarPane.getChildren().add(vboxArray[i]);
        } // end 'for' loop
    } // end drawCalendar() method

    // inner class for 'Options' menu
    class Options {

        // member fields - GUI elements
        Button notificationsButton;                     // button for notifications screen
        Button deleteSkillHistoryButton;                // button for deleting skill history screen
        Button deleteGoalButton;                        // button for deleting goal from calendar screen
        Button twitterButton;

        // constructor
        public Options() {

            // creates new stage
            Stage optionsStage = new Stage();
            // sets title
            optionsStage.setTitle("Options");
            optionsStage.getIcons().add(new Image("/llama.jpg"));

            // hbox for 1st vbox row
            HBox optionsButtonHbox1 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox1.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox1.getStyleClass().add("optionsButtonHboxes");
            // initializes notificationsButton
            notificationsButton = new Button();
            // sets text for notificationsButton
            notificationsButton.setText("Notifications: On/Off");
            // event handler for notificationsButton
            notificationsButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // creates a new stage
                    Stage notificationsStage = new Stage();
                    // label for enable notifications message
                    Label enableNotifications = new Label("Enable Notifications?");
                    // button for user to select yes
                    Button yesButton = new Button("Yes");
                    // button for user to select no
                    Button noButton = new Button("No");

                    // hbox for 1st vbox row
                    HBox notificationsHbox1 = new HBox();
                    // sets alignment of hbox to center
                    notificationsHbox1.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    notificationsHbox1.getStyleClass().add("optionsButtonHboxes");
                    // adds enableNotifications to notificationsHbox1
                    notificationsHbox1.getChildren().add(enableNotifications);

                    // hbox for 2nd vbox row
                    HBox notificationsHbox2 = new HBox();
                    // sets alignment of hbox to center
                    notificationsHbox2.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    notificationsHbox2.getStyleClass().add("optionsButtonHboxes");
                    // adds yesButton to notificationsHbox2
                    notificationsHbox2.getChildren().add(yesButton);
                    // adds noButton to notificationsHbox2
                    notificationsHbox2.getChildren().add(noButton);

                    // new vbox layout
                    VBox notificationsVBox = new VBox();
                    // pulls css specs from style sheet
                    notificationsVBox.getStyleClass().add("secondaryWindow");
                    // adds all hboxes to optionsVBox
                    notificationsVBox.getChildren().add(notificationsHbox1);
                    notificationsVBox.getChildren().add(notificationsHbox2);

                    // adds notificationsVBox to the notificationsScene
                    Scene notificationsScene = new Scene(notificationsVBox, 250, 250);
                    // adds notificationsScene to notificationsStage 
                    notificationsStage.setScene(notificationsScene);
                    // gets css style sheet
                    notificationsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                    // shows the stage; actually displays the scene
                    notificationsStage.show();

                }
            }); // end event handler
            // adds notificationsButton to optionsButtonHbox1
            optionsButtonHbox1.getChildren().add(notificationsButton);

            // hbox for 2nd vbox row
            HBox optionsButtonHbox2 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox2.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox2.getStyleClass().add("optionsButtonHboxes");
            // initializes deleteSkillHistoryButton
            deleteSkillHistoryButton = new Button();
            // sets text for deleteSkillHistoryButton
            deleteSkillHistoryButton.setText("Delete Skill History");
            // event handler for deleteSkillHistoryButton
            deleteSkillHistoryButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // creates a new stage
                    Stage deleteSkillStage = new Stage();
                    // label for enable notifications message
                    Label skillLabel = new Label("Choose which skill you would like to delete:");
                    // combobox for user to select skill from list
                    ComboBox skillsComboBox = new ComboBox();                   // ****TO DO: populate user's skills
                    // button for user to delete skill history
                    Button deleteButton = new Button("Delete");

                    // hbox for 1st vbox row
                    HBox deleteSkillHbox1 = new HBox();
                    // sets alignment of hbox to center
                    deleteSkillHbox1.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteSkillHbox1.getStyleClass().add("optionsButtonHboxes");
                    // adds skillLabel to deleteSkillsHbox1
                    deleteSkillHbox1.getChildren().add(skillLabel);

                    // hbox for 2nd vbox row
                    HBox deleteSkillHbox2 = new HBox();
                    // sets alignment of hbox to center
                    deleteSkillHbox2.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteSkillHbox2.getStyleClass().add("optionsButtonHboxes");
                    // adds skillsComboBox to deleteSkillsHbox2
                    deleteSkillHbox2.getChildren().add(skillsComboBox);

                    // hbox for 3rd vbox row
                    HBox deleteSkillHbox3 = new HBox();
                    // sets alignment of hbox to center
                    deleteSkillHbox3.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteSkillHbox3.getStyleClass().add("optionsButtonHboxes");
                    // adds deleteButton to deleteSkillsHbox3
                    deleteSkillHbox3.getChildren().add(deleteButton);

                    // new vbox layout
                    VBox deleteSkillVBox = new VBox();
                    // pulls css specs from style sheet
                    deleteSkillVBox.getStyleClass().add("secondaryWindow");
                    // adds all hboxes to deleteSkillVBox
                    deleteSkillVBox.getChildren().add(deleteSkillHbox1);
                    deleteSkillVBox.getChildren().add(deleteSkillHbox2);
                    deleteSkillVBox.getChildren().add(deleteSkillHbox3);

                    // adds deleteSkillsVBox to the deleteSkillScene
                    Scene deleteSkillScene = new Scene(deleteSkillVBox, 450, 450);
                    // adds deleteSkillScene to deleteSkillStage 
                    deleteSkillStage.setScene(deleteSkillScene);
                    // gets css style sheet
                    deleteSkillScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                    // shows the stage; actually displays the scene
                    deleteSkillStage.show();

                }
            }); // end event handler
            // adds deleteSkillHistoryButton to optionsButtonHbox2
            optionsButtonHbox2.getChildren().add(deleteSkillHistoryButton);

            // hbox for 3rd vbox row
            HBox optionsButtonHbox3 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox3.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox3.getStyleClass().add("optionsButtonHboxes");
            // initializes deleteGoalButton
            deleteGoalButton = new Button();
            // sets text for deleteGoalButton
            deleteGoalButton.setText("Delete Goal from Calendar");
            // sets alignment for both
            deleteGoalButton.setAlignment(Pos.CENTER);
            // event handler for deleteGoalButton
            deleteGoalButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // creates a new stage
                    Stage deleteGoalStage = new Stage();
                    // label for enable notifications message
                    Label skillLabel = new Label("Choose a skill:");
                    // combobox for user to select skill from list
                    ComboBox skillsComboBox = new ComboBox();                   // ****TO DO: populate user's skills
                    // label for delete goal message
                    Label goalLabel = new Label("Choose which goal you would like to delete:");
                    // combobox for user to select goal from list
                    ComboBox goalsComboBox = new ComboBox();                    // ****TO DO: populate user's goals
                    // button for user to delete skill history
                    Button deleteButton = new Button("Delete");

                    // hbox for 1st vbox row
                    HBox deleteGoalHbox1 = new HBox();
                    // sets alignment for hbox
                    deleteGoalHbox1.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteGoalHbox1.getStyleClass().add("optionsButtonHboxes");
                    // adds skillLabel to deleteGoalHbox1
                    deleteGoalHbox1.getChildren().add(skillLabel);

                    // hbox for 2nd vbox row
                    HBox deleteGoalHbox2 = new HBox();
                    // sets alignment of hbox to center
                    deleteGoalHbox2.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteGoalHbox2.getStyleClass().add("optionsButtonHboxes");
                    // adds skillsComboBox to deleteGoalHbox2
                    deleteGoalHbox2.getChildren().add(skillsComboBox);

                    // hbox for 3rd vbox row
                    HBox deleteGoalHbox3 = new HBox();
                    // sets alignment of hbox to center
                    deleteGoalHbox3.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteGoalHbox3.getStyleClass().add("optionsButtonHboxes");
                    // adds goalLabel to deleteGoalHbox3
                    deleteGoalHbox3.getChildren().add(goalLabel);

                    // hbox for 4th vbox row
                    HBox deleteGoalHbox4 = new HBox();
                    // sets alignment of hbox to center
                    deleteGoalHbox4.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteGoalHbox4.getStyleClass().add("optionsButtonHboxes");
                    // adds goalsComboBox to deleteGoalHbox4
                    deleteGoalHbox4.getChildren().add(goalsComboBox);

                    // hbox for 5th vbox row
                    HBox deleteGoalHbox5 = new HBox();
                    // sets alignment of hbox to center
                    deleteGoalHbox5.setAlignment(Pos.CENTER);
                    // pulls css specs from style sheet
                    deleteGoalHbox5.getStyleClass().add("optionsButtonHboxes");
                    // adds deleteButton to deleteGoalHbox5
                    deleteGoalHbox5.getChildren().add(deleteButton);

                    // new vbox layout
                    VBox deleteGoalVBox = new VBox();
                    // pulls css specs from style sheet
                    deleteGoalVBox.getStyleClass().add("secondaryWindow");
                    // adds all hboxes to deleteGoalVBox
                    deleteGoalVBox.getChildren().add(deleteGoalHbox1);
                    deleteGoalVBox.getChildren().add(deleteGoalHbox2);
                    deleteGoalVBox.getChildren().add(deleteGoalHbox3);
                    deleteGoalVBox.getChildren().add(deleteGoalHbox4);
                    deleteGoalVBox.getChildren().add(deleteGoalHbox5);

                    // adds deleteGoalVBox to the deleteGoalScene
                    Scene deleteGoalScene = new Scene(deleteGoalVBox, 450, 450);
                    // adds deleteGoalScene to deleteGoalStage 
                    deleteGoalStage.setScene(deleteGoalScene);
                    // gets css style sheet
                    deleteGoalScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                    // shows the stage; actually displays the scene
                    deleteGoalStage.show();
                }
            }); // end event handler
            // adds deleteGoalButton to optionsButtonHbox3
            optionsButtonHbox3.getChildren().add(deleteGoalButton);

            // hbox for 4th vbox row
            HBox optionsButtonHbox4 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox4.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox4.getStyleClass().add("optionsButtonHboxes");
            // initializes twitterButton
            twitterButton = new Button();
            twitterButton.setStyle("-fx-background-color: #00bfff;");
            // sets text for twitterButton
            twitterButton.setText("Send Tweet");
            // sets alignment for both
            twitterButton.setAlignment(Pos.CENTER);
            // event handler for twitterButton
            twitterButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    TwitterIntegration twitterApp = new TwitterIntegration();
                    try {
                        twitterApp.display();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }); // end event handler
            // adds twitterButton to optionsButtonHbox4
            optionsButtonHbox4.getChildren().add(twitterButton);

            // new vbox layout
            VBox optionsVBox = new VBox();
            // necessary to pull css specs from style sheet
            optionsVBox.getStyleClass().add("secondaryWindow");

            // creates new regions (for layout/alignment purposes)
            Region emptyRegion1 = new Region();
            Region emptyRegion2 = new Region();
            Region emptyRegion3 = new Region();

            // adds all hboxes and regions to optionsVBox
            optionsVBox.getChildren().add(optionsButtonHbox1);
            optionsVBox.getChildren().add(emptyRegion1);
            optionsVBox.getChildren().add(optionsButtonHbox2);
            optionsVBox.getChildren().add(emptyRegion2);
            optionsVBox.getChildren().add(optionsButtonHbox3);
            optionsVBox.getChildren().add(emptyRegion3);
            optionsVBox.getChildren().add(optionsButtonHbox4);

            // adds this pane/layout to the scene
            Scene optionsScene = new Scene(optionsVBox, 350, 350);
            // adds scene to stage 
            optionsStage.setScene(optionsScene);
            // gets css style sheet
            optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage; actually displays the scene
            optionsStage.show();

        }

    } // end class Options

    // inner class for 'Lifetime Metrics' menu
    class LifetimeMetrics {

        // constructor
        public LifetimeMetrics() {
            // creates new stage
            Stage lifeMetricsStage = new Stage();
            // sets title
            lifeMetricsStage.setTitle("Lifetime Metrics");
            lifeMetricsStage.getIcons().add(new Image("/llama.jpg"));
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
    } // end class LifetimeMetrics

    // inner class for handling when user clicks on a calendar button
    class HabitCreationButton {

        // member fields - GUI elements
        Label startDateLabel;                           // label for 'Date to Start Goal'
        DatePicker startDatePicker;                     // datepicker for user to enter start date for goal
        Label habitLabel;                               // label for 'Habit Name'
        TextField habitTextField;                       // textfield for habit name
        Label goalLabel;                                // label for 'Goal'
        TextField numTextField;                         // textfield for goal number
        ComboBox goalComboBox;                          // combobox for goal metric
        Label freqLabel;                                // label for 'Frequency'
        ToggleGroup rbGroup = new ToggleGroup();        // togglegroup for radio button group
        RadioButton dailyRB;                            // radio button for 'Daily' option
        RadioButton weeklyRB;                           // radio button for 'Weekly' option
        RadioButton customRB;                           // radio button for 'Custom' option
        RadioButton monRB;                              // radio button for 'Monday' option
        RadioButton tuesRB;                             // radio button for 'Tuesday' option
        RadioButton wedRB;                              // radio button for 'Wednesday' option
        RadioButton thursRB;                            // radio button for 'Thursday' option
        RadioButton friRB;                              // radio button for 'Friday' option
        RadioButton satRB;                              // radio button for 'Saturday' option
        RadioButton sunRB;                              // radio button for 'Sunday' option
        RadioButton cumulativeRB;                       // radio button for 'Cumulative' option
        Label goalDateLabel;                            // label for 'Date to Complete Goal'
        DatePicker datePicker;                          // datepicker for user to enter date to complete goal
        Label notesLabel;                               // label for 'Notes'
        TextArea notesTextArea;                         // textarea for notes section   
        Button submitButton;                            // button for submitting informatione entered by user

        // constructor
        public HabitCreationButton() {

            // creates new stage
            Stage habitEntryStage = new Stage();
            // sets title for habitEntryStage
            habitEntryStage.setTitle("Habit/Skill Creation");
            habitEntryStage.getIcons().add(new Image("/llama.jpg"));
            // hbox for 1st vbox row
            HBox habitCreationButtonHbox1 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox1.getStyleClass().add("habitCreationButtonHboxes");
            // initializes startDateLabel
            startDateLabel = new Label();
            // sets text for startDateLabel
            startDateLabel.setText("Date to Start Goal:");
            // initializes startDatePicker
            startDatePicker = new DatePicker();
            // sets startDatePicker to the current date by default
            startDatePicker.setValue(LocalDate.now());
            // adds startDateLabel to habitCreationButtonHbox1
            habitCreationButtonHbox1.getChildren().add(startDateLabel);
            // adds startDatePicker to habitCreationButtonHbox1
            habitCreationButtonHbox1.getChildren().add(startDatePicker);

            // hbox for 2nd vbox row
            HBox habitCreationButtonHbox2 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox2.getStyleClass().add("habitCreationButtonHboxes");
            // initializes habitLabel
            habitLabel = new Label();
            // sets text of habitLabel
            habitLabel.setText("Habit/Skill:");
            // initializes habitTextField
            habitTextField = new TextField();
            // adds habitLabel to habitCreationButtonHbox2
            habitCreationButtonHbox2.getChildren().add(habitLabel);
            // adds habitTextField to habitCreationButtonHbox2
            habitCreationButtonHbox2.getChildren().add(habitTextField);

            // hbox for 2nd vbox row
            HBox habitCreationButtonHbox3 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox3.getStyleClass().add("habitCreationButtonHboxes");
            // initializes goalLabel
            goalLabel = new Label();
            // sets text of goalLabel
            goalLabel.setText("Goal to Reach:");
            // initializes numTextField
            numTextField = new TextField();
            // sets max size of numTextField
            numTextField.setMaxSize(80, 80);
            // initializes goalComboBox
            // initializes goalComboBox
            goalComboBox = new ComboBox();
            // adds metrics to goalComboBox
            goalComboBox.getItems().add("minutes");
            goalComboBox.getItems().add("hours");
            goalComboBox.getItems().add("miles");
            // adds goalLabel to habitCreationButtonHbox3
            habitCreationButtonHbox3.getChildren().add(goalLabel);
            // adds numTextField to habitCreationButtonHbox3
            habitCreationButtonHbox3.getChildren().add(numTextField);
            // adds goalComboBox to habitCreationButtonHbox3
            habitCreationButtonHbox3.getChildren().add(goalComboBox);

            // hbox for 3rd vbox row
            HBox habitCreationButtonHbox4 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox4.getStyleClass().add("habitCreationButtonHboxes");
            // initializes freqLabel
            freqLabel = new Label();
            // sets text of freqLabel
            freqLabel.setText("Frequency:");
            // adds freqLabel to habitCreationButtonHbox4
            habitCreationButtonHbox4.getChildren().add(freqLabel);

            // hbox for 4th vbox row
            HBox habitCreationButtonHbox5 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox5.getStyleClass().add("habitCreationButtonHboxes");
            // initializes dailyRB
            dailyRB = new RadioButton();
            // sets text of dailyRB
            dailyRB.setText("Daily");
            // adds dailyRB to toggle group rbGroup
            dailyRB.setToggleGroup(rbGroup);
            // sets 'Daily' option as selected by default
            dailyRB.setSelected(true);
            // adds dailyRB to habitCreationButtonHbox5
            habitCreationButtonHbox5.getChildren().add(dailyRB);

            // hbox for 5th vbox row
            HBox habitCreationButtonHbox6 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox6.getStyleClass().add("habitCreationButtonHboxes");
            // initializes weeklyRB
            weeklyRB = new RadioButton();
            // sets text of weeklyRB
            weeklyRB.setText("Weekly");
            // adds weeklyRB to toggle group rbGroup
            weeklyRB.setToggleGroup(rbGroup);
            // adds weeklyRB to habitCreationButtonHbox6
            habitCreationButtonHbox6.getChildren().add(weeklyRB);

            // hbox for 6th vbox row
            HBox habitCreationButtonHbox7 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox7.getStyleClass().add("habitCreationButtonHboxes");
            // initializes customRB
            customRB = new RadioButton();
            // sets text for customRB
            customRB.setText("Custom");
            // adds customRB to toggle group rbGroup
            customRB.setToggleGroup(rbGroup);
            // adds customRB to habitCreationButtonHbox7
            habitCreationButtonHbox7.getChildren().add(customRB);

            // hbox for 7th vbox row
            HBox habitCreationButtonHbox8 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox8.getStyleClass().add("habitCreationButtonHboxes");
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
            // adds monRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(monRB);
            // adds tuesRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(tuesRB);
            // adds wedRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(wedRB);
            // adds thursRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(thursRB);
            // adds friRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(friRB);
            // adds satRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(satRB);
            // adds sunRB to habitCreationButtonHbox8
            habitCreationButtonHbox8.getChildren().add(sunRB);

            // hbox for 8th vbox row
            HBox habitCreationButtonHbox9 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox9.getStyleClass().add("habitCreationButtonHboxes");
            // initializes cumulativeRB
            cumulativeRB = new RadioButton();
            // sets text for cumulativeRB
            cumulativeRB.setText("Cumulative");
            // adds cumulativeRB to toggle group rbGroup
            cumulativeRB.setToggleGroup(rbGroup);
            // adds cumulativeRB to habitCreationButtonHbox9
            habitCreationButtonHbox9.getChildren().add(cumulativeRB);

            // hbox for 9th vbox row
            HBox habitCreationButtonHbox10 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox10.getStyleClass().add("habitCreationButtonHboxes");
            // initializes goalDateLabel
            goalDateLabel = new Label();
            // sets text for goalDateLabel
            goalDateLabel.setText("Date to Complete Goal:");
            // initializes datePicker
            datePicker = new DatePicker();

            // adds goalDateLabel to habitCreationButtonHbox10
            habitCreationButtonHbox10.getChildren().add(goalDateLabel);
            // adds datePicker to habitCreationButtonHbox10
            habitCreationButtonHbox10.getChildren().add(datePicker);
            // sets datePicker as disabled
            datePicker.setDisable(true);

            // event handler for customRB
            customRB.setOnAction(e -> {
                // sets all radio buttons to enabled
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
                // sets all radio buttons to disabled
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
                // sets all radio buttons to disabled
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
                // sets all radio buttons to disabled
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
            HBox habitCreationButtonHbox11 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox11.getStyleClass().add("habitCreationButtonHboxes");
            // initializes notesLabel
            notesLabel = new Label();
            // sets text for notesLabel
            notesLabel.setText("Notes:");
            // initializes notesTextArea
            notesTextArea = new TextArea();
            // sets preferred size of notesTextArea
            notesTextArea.setPrefSize(350, 350);
            // adds notesLabel to habitCreationButtonHbox11
            habitCreationButtonHbox11.getChildren().add(notesLabel);
            // adds notesTextArea to habitCreationButtonHbox11
            habitCreationButtonHbox11.getChildren().add(notesTextArea);

            // hbox for 11th vbox row
            HBox habitCreationButtonHbox12 = new HBox();
            // pulls css specs from style sheet
            habitCreationButtonHbox12.getStyleClass().add("habitCreationButtonHboxes");
            // initializes submitButton
            submitButton = new Button();
            // sets text for submitButton
            submitButton.setText("Submit");
            // event handler for submitButton
            submitButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // ****TO DO: Save to JSON file
                    habitEntryStage.close();    // closes window
                }
            }); // end event handler

            // centers the hbox that submitButton is placed in
            habitCreationButtonHbox12.setAlignment(Pos.CENTER);
            // adds submitButton to habitCreationButtonHbox12
            habitCreationButtonHbox12.getChildren().add(submitButton);

            // new vbox layout
            VBox habitCreationVbox = new VBox();
            // pulls css specs from style sheet
            habitCreationVbox.getStyleClass().add("secondaryWindow");
            // adds all hboxes to habitCreationVbox
            habitCreationVbox.getChildren().add(habitCreationButtonHbox1);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox2);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox3);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox4);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox5);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox6);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox7);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox8);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox9);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox10);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox11);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox12);

            // adds habitCreationVbox to optionsScene
            Scene optionsScene = new Scene(habitCreationVbox, 800, 800);
            // adds optionsScene to habitEntryStage 
            habitEntryStage.setScene(optionsScene);
            // gets css style sheet
            optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage; actually displays the scene
            habitEntryStage.show();
        } // end constructor

    } // end class HabitCreationButton

    // inner class for handling when user clicks on the "+" button to add progress
    class ProgressButton {

        // member fields - GUI elements
        Label habitLabel;                       // label for habitComboBox
        ComboBox habitComboBox;                 // comboBox for list of populated habits/skills of user
        Label goalLabel;                        // label for goalComboBox
        ComboBox goalComboBox;                  // comboBox for list of goals pertaining to chosen habit/skill
        Label progressMadeLabel;                // label for "Progress Made" TextField
        TextField progressMadeTextField;        // textField for "Progress Made"; user can enter progress metrics  
        ComboBox progressMetricsComboBox;       // comboBox for list of metrics when user updates progress
        Label notesLabel;                       // label for "Notes" area
        TextArea notesTextArea;                 // textArea for "Notes" area
        Button submitButton;                    // button for user to submit progress information

        // constructor
        ProgressButton() {

            // creates new stage
            Stage progressStage = new Stage();
            // sets title for progressStage
            progressStage.setTitle("Habit Progress");
            progressStage.getIcons().add(new Image("/llama.jpg"));
            // hbox for 1st vbox row
            HBox progressButtonHbox1 = new HBox();
            // pulls css styling information
            progressButtonHbox1.getStyleClass().add("progressButtonHboxes");
            // initializes habitLabel
            habitLabel = new Label();
            // sets text for habitLabel
            habitLabel.setText("Habit/Skill:");
            // initializes habitComboBox
            habitComboBox = new ComboBox();
            // adds items to habitComboBox
            habitComboBox.getItems().add("minutes");
            habitComboBox.getItems().add("hours");
            habitComboBox.getItems().add("miles");
            // adds habitLabel to progressButtonHbox1
            progressButtonHbox1.getChildren().add(habitLabel);
            // adds habitComboBox to progressButtonHbox1
            progressButtonHbox1.getChildren().add(habitComboBox);

            // hbox for 2nd vbox row
            HBox progressButtonHbox2 = new HBox();
            // pulls css styling information
            progressButtonHbox2.getStyleClass().add("progressButtonHboxes");
            // initializes goalLabel
            goalLabel = new Label();
            // sets text for goalLabel
            goalLabel.setText("Goal:");
            // initializes goalComboBox
            goalComboBox = new ComboBox();
            // ****TO DO: populate goals in goalComboBox
            // adds goalLabel to progressButtonHbox2                                
            progressButtonHbox2.getChildren().add(goalLabel);
            // adds goalComboBox to progressButtonHbox2     
            progressButtonHbox2.getChildren().add(goalComboBox);

            // hbox for 3rd vbox row
            HBox progressButtonHbox3 = new HBox();
            // pulls css styling information
            progressButtonHbox3.getStyleClass().add("progressButtonHboxes");
            // initializes progressMadeLabel
            progressMadeLabel = new Label();
            // sets text for progressMadeLabel
            progressMadeLabel.setText("Progress Made:");
            // initializes progressMadeTextField
            progressMadeTextField = new TextField();
            // sets max size of progressMadeTextField
            progressMadeTextField.setMaxSize(80, 80);
            // initializes progressMetrics
            progressMetricsComboBox = new ComboBox();
            // ****TO DO: populate goals in progressMetricsComboBox
            // adds components to progressButtonHbox3                                    
            progressButtonHbox3.getChildren().add(progressMadeLabel);
            progressButtonHbox3.getChildren().add(progressMadeTextField);
            progressButtonHbox3.getChildren().add(progressMetricsComboBox);

            // hbox for 4th vbox row
            HBox progressButtonHbox4 = new HBox();
            // pulls css styling information
            progressButtonHbox4.getStyleClass().add("progressButtonHboxes");
            // initializes notesLabel
            notesLabel = new Label();
            // sets text for notesLabel
            notesLabel.setText("Notes:");
            // initializes notesTextArea
            notesTextArea = new TextArea();
            // sets preferred size of notesTextArea
            notesTextArea.setPrefSize(350, 350);
            // adds notesLabel to progressButtonHbox4                                    
            progressButtonHbox4.getChildren().add(notesLabel);
            // adds notesTextArea to progressButtonHbox4    
            progressButtonHbox4.getChildren().add(notesTextArea);

            // hbox for 5th vbox row
            HBox progressButtonHbox5 = new HBox();
            // pulls css styling information
            progressButtonHbox5.getStyleClass().add("progressButtonHboxes");
            // initializes submitButton
            submitButton = new Button();
            // sets text for submitButton
            submitButton.setText("Submit");
            // event handler for submitButton
            submitButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {

                    // ****TO DO: Save to JSON file
                    progressStage.close();      // closes window

                }
            }); // end event handler

            // centers hbox that submitButton is placed in
            progressButtonHbox5.setAlignment(Pos.CENTER);
            // adds submitButton to progressButtonHbox5                                    
            progressButtonHbox5.getChildren().add(submitButton);

            // new vbox layout
            VBox progressVbox = new VBox();
            // pulls css specs from style sheet
            progressVbox.getStyleClass().add("secondaryWindow");
            // adds all of the hboxes to progressVbox
            progressVbox.getChildren().add(progressButtonHbox1);
            progressVbox.getChildren().add(progressButtonHbox2);
            progressVbox.getChildren().add(progressButtonHbox3);
            progressVbox.getChildren().add(progressButtonHbox4);
            progressVbox.getChildren().add(progressButtonHbox5);

            // adds progressVbox to progressScene
            Scene progressScene = new Scene(progressVbox, 700, 700);
            // adds progressScene to progressStage 
            progressStage.setScene(progressScene);
            // gets css style sheet
            progressScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage; actually displays the scene
            progressStage.show();
        } // end constructor

    } // end class ProgressButton

    // main method
    public static void main(String[] args) {
        /*
         * These three objects need to be invoked in main so the rest of the
         * application can access the UserProfile and the list of Task objects
         * that were loaded from disk.
         */
        ArrayList<Task> arrayOfTasks = jsonLoader.loadFromJSON();
        Iterator<Task> taskIterator = arrayOfTasks.iterator();
        UserProfile skbUserProfile = jsonLoader.getProfileFromLoader();

        launch(args);               // opens the JavaFX Stage
    } // end main method

} // end class Skillblazer
