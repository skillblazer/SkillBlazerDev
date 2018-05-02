/** **********************************************************
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
 ********************************************************** */
// package
package llamasoft.skillblazer;

// imports
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;

import twitter4j.TwitterException;

// main class; extends Application
public class SkillBlazer extends Application {

    // primary GUI interface fields
    private Stage window;                                                                               // stage set equal to primaryStage
    private Button optionsButton;                                                                       // options button
    private Label appTitle;                                                                             // application title
    private Label usernameLabel;                                                                        // username label
    private Button lifetimeMetricsButton;                                                               // lifetime metrics button
    private Label currentMonthYearLabel;                                                                // label for current month and year
    private Button forwardMonthButton;                                                                  // button to move month forward
    private Button backMonthButton;                                                                     // button to move month back
    private CalendarCalculator calCalc;                                                                 // CalendarCalculator object
    private TilePane calendarPane;                                                                      // tilepane object for calendar
    private VBox[] vboxArray = new VBox[49];                                                            // vbox array for main calendar interface
    private Button habitCreationButton;                                                                 // button for habit creation
    private static ArrayList<Task> taskList;                                                            // arraylist holding tasks
    private Options optionsMenu;                                                                        // Options object
    private LifetimeMetrics lifetimeMetrics;                                                            // LifetimeMetrics object
    private HabitCreationButton habitCreationMenu;                                                      // HabitCreationButton object
    private static final String[] dayNamesOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday",         // String array holding names for days of week
        "Thursday", "Friday", "Saturday"};                                                              

    // these objects will conduct the startup routine
    static JSONLoader jsonLoader = new JSONLoader();     // also provides an instance of SkillBlazerInitializer skillBlazerInit
    static UserProfile skbUserProfile;          

    // sets up the main stage, scenes and such
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // window = primaryStage
        window = primaryStage;
        // sets title of main window (primaryStage)

        window.setTitle("Skillblazer Habit Tracker");
        // adds skillblazer icon to primaryStage
        window.getIcons().add(new Image("/llama.jpg"));

        // dropshadow effect for buttons
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);

        // instantiates CalendarCalculator
        calCalc = new CalendarCalculator();
        // instantiates optionsMenu
        optionsMenu = new Options();
        // initializes optionsButton
        optionsButton = new Button();
        // sets text of optionsButton
        optionsButton.setText("Options");
        // tooltip
        optionsButton.setTooltip(new Tooltip("Set your preferred options"));
        // sets preferred width of optionsButton
        optionsButton.setPrefWidth(120);
        // pulls css specs from style sheet
        optionsButton.getStyleClass().add("button1");
        // event handler for 'Options' button
        optionsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // showOptions() method called
                optionsMenu.showOptions();
            }
        }); // end event handler

        // initializes appTitle
        appTitle = new Label();
        // pulls css specs from style sheet
        appTitle.getStyleClass().add("appTitle");
        // sets text of appTitle
        appTitle.setText("Skillblazer Habit Tracker");
        // instantiates usernameLabel
        usernameLabel = new Label();
        // sets text of usernameLabel
        usernameLabel.setText("Hello, " + skbUserProfile.getUserName() + "!");
        // pulls css specs from style sheet
        usernameLabel.getStyleClass().add("usernameLabel");
        
        // creates new region (for layout/alignment purposes)
        Region region1 = new Region();
        // for layout/alignment purposes
        HBox.setHgrow(region1, Priority.ALWAYS);
        // creates new region (for layout/alignment purposes)
        Region region2 = new Region();
        // for layout/alignment purposes
        HBox.setHgrow(region2, Priority.ALWAYS);
        // creates new region (for layout/alignment purposes)
        Region region3 = new Region();
        // for layout/alignment purposes
        HBox.setHgrow(region3, Priority.ALWAYS);
        // creates new region (for layout/alignment purposes)
        Region region4 = new Region();
        // for layout/alignment purposes
        HBox.setHgrow(region4, Priority.ALWAYS);
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
        // tooltip for forwardMonthButton
        forwardMonthButton.setTooltip(new Tooltip("Move forward in time"));
        // pulls css specs from style sheet
        forwardMonthButton.getStyleClass().add("button1");
        // event handler for forwardMonthButton
        forwardMonthButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // calls changeMonthForward() method from CalendarCalculator class
                calCalc.changeMonthForward();
                // sets text of currentMonthYearLabel by calling two methods in CalendarCalculator class
                currentMonthYearLabel.setText(" " + calCalc.getCurrentMonthString() + " " + calCalc.getCurrentYearInt() + " ");
                // calls drawCalendar() method, which is responsible for creating calendar
                drawCalendar();
            }
        }); // end event handler

        // initializes backMonthButton
        backMonthButton = new Button();
        // sets text of backMonthButton
        backMonthButton.setText("<<");
        // tooltip for backMonthButton
        backMonthButton.setTooltip(new Tooltip("Move backward in time"));
        // pulls css specs from style sheet
        backMonthButton.getStyleClass().add("button1");
        // event handler for backMonthButton
        backMonthButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // calls changeMonthBackward() method from CalendarCalculator class
                calCalc.changeMonthBackward();
                // sets text of currentMonthYearLabel by calling two methods in CalendarCalculator class
                currentMonthYearLabel.setText(" " + calCalc.getCurrentMonthString() + " " + calCalc.getCurrentYearInt() + " ");
                // calls drawCalendar() method, which is responsible for creating calendar
                drawCalendar();
            }
        }); // end event handler
        
        // instantiates lifetimeMetrics
        lifetimeMetrics = new LifetimeMetrics();
        // initializes lifetimeMetricsButton
        lifetimeMetricsButton = new Button();
        // sets text of lifetimeMetricsButton
        lifetimeMetricsButton.setText("Lifetime Metrics");
        // tooltip for lifetimeMetricsButton
        lifetimeMetricsButton.setTooltip(new Tooltip("Check out metrics for one of your tracked habits!"));
        // sets preferred width of lifetimeMetricsButton
        lifetimeMetricsButton.setPrefWidth(120);
        // pulls css specs from style sheet
        lifetimeMetricsButton.getStyleClass().add("button1");
        // event handler for 'Lifetime Metrics' button
        lifetimeMetricsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                // showLifetimeMetrics() method called
                lifetimeMetrics.showLifetimeMetrics();   
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
        // adds region2 to hboxTop
        hboxTop.getChildren().add(region2);
        // adds lifetimeMetricsButton to hbox
        hboxTop.getChildren().add(lifetimeMetricsButton);
        
        // hbox layout for username area of GUI
        HBox hboxUsername = new HBox();
        // pulls css specs from style sheet
        hboxUsername.getStyleClass().add("vboxMain");
        // adds region3 to hboxUsername
        hboxUsername.getChildren().add(region3);
        // adds usernameLabel to hboxUsername
        hboxUsername.getChildren().add(usernameLabel);
        // adds region4 to hboxUsername
        hboxUsername.getChildren().add(region4);

        // adds hbox to vboxMain
        vboxMain.getChildren().add(hboxUsername);

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
        // instantiates habitCreationMenu
        habitCreationMenu = new HabitCreationButton();
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
        // event handler for big red 'X' to close program; calls method closeProgram()
        window.setOnCloseRequest(e -> closeProgram());
        // adds scene to stage
        window.setScene(scene);
        // pulls css style sheet
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        // shows the stage; actually displays the scen
        window.show();

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
            // for a Task object in taskList
            for (Task mt : taskList) {
															 
                // Calendar object representing task start date
                Calendar taskStartDate = mt.getStartDate();
                // Calendar object representing task end date
                Calendar taskEndDate = mt.getEndDate();
                if (taskEndDate!=null) {
                    // if end date is passed, skip (continue)
                    if (calCalc.getDayObject(i).getTaskDate().compareTo(taskEndDate) > 0) {
                        continue;
                    }
                }
                // if start date has not been reached, skip (continue)
                if (calCalc.getDayObject(i).getTaskDate().compareTo(taskStartDate) < 0) {
                    continue;
                }
                // if Task object is an instance of DailyTask
                if (mt instanceof DailyTask) {
                    // adds Task object to Day object of calCalc
                    calCalc.getDayObject(i).addTask(mt);
                // else if Task object is an instance of WeeklyTask
                } else if (mt instanceof WeeklyTask) {
                    if (dayOfWeek == 6) {
                        // adds Task object to Day object of calCalc
                        calCalc.getDayObject(i).addTask(mt);
                    }
                // else if Task object is an instance of CustomTask
                } else if (mt instanceof CustomTask) {
                    // arraylist of type string representing the days of the week
                    ArrayList<String> daysOfWeek = ((CustomTask) mt).getDaysOfWeek();
                    // boolean that gets set to true if the days of the week show up in the arraylist for the days of the week the task has
                    boolean todayActive = false;
                    for (String md : daysOfWeek) {
                        todayActive |= md.equalsIgnoreCase(dayNamesOfWeek[dayOfWeek]);
                    }
                    if (todayActive) {
                        // adds Task object to Day object of calCalc
                        calCalc.getDayObject(i).addTask(mt);
                    }
                // else if Task object is an instance of CumulativeTask
                } else if (mt instanceof CumulativeTask) {
                    // adds Task object to Day object of calCalc
                    calCalc.getDayObject(i).addTask(mt);
                }
            }
            // week day update
            dayOfWeek = (dayOfWeek + 1) % 7;
        }
    } // end populateDays() method

    // drawCalendar() method; responsible for creating the calendar
    private void drawCalendar() {
        // generates Day objects for selected calendar month
        calCalc.instantiateCalendar();
        // populates calendar days with task information
        populateDays();
        // int that holds 1st day of week
        int firstDayOfWeekCurrentMonth = calCalc.getFirstDayOfWeekCurrentMonth();
        // int that holds # of days
        int numberDaysCurrentMonth = calCalc.getDaysInCurrentMonth();
        // iterator
        int j = 0;

        // clears calendar interface
        calendarPane.getChildren().clear();

        // 'for' loop used to populate days of the week in top of calendar interface
        for (int i = 0; i < 7; i++) {
            // initializes vboxArray
            vboxArray[i] = new VBox();
            // creates and initializes hboxCal
            HBox hboxCal = new HBox();
            // creates and initializes; fills it with string value from dayNamesOfWeek
            Label vboxLabel = new Label(dayNamesOfWeek[i]);
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
                // tooltip for vboxButton
                vboxButton.setTooltip(new Tooltip("Add a habit/skill record!"));
                // pulls css style sheet
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
                        ProgressButton progressButton = new ProgressButton(todayDayOb);
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
                // adds Tasks to calendar day - 'for' loop
                for (Task mt : todayDayOb.tasksThisDay) {
                    // label for each Task
                    Label taskLabel = new Label(mt.getTaskName());
                    // if Task object is an instance of CumulativeTask
                    if (mt instanceof CumulativeTask) {
                        // tool tip for Cumulative Task
                        taskLabel.setTooltip(new Tooltip("Cumulative Task"));
                        // if there is no difference between the dates
                        if (todayDayOb.getTaskDate().compareTo(((CumulativeTask)mt).getEndDate())==0) {
                            // if cumulative task is completed
                            if (((CumulativeTask)mt).checkCompleted()) {
                                // marks completed task with checkmark
                                taskLabel.setText(mt.getTaskName()+ "   ✔");
                                // pulls css style sheet
                                taskLabel.getStyleClass().add("labelFinalCumulativeCompleted");
                            } else {
                                // sets text of taskLabel with cumulative task name and exclamation point
                                taskLabel.setText(mt.getTaskName()+"(!)");
                                // pulls css style sheet
                                taskLabel.getStyleClass().add("labelFinalCumulativeUncompleted");
                            }
                        } else {
                            // pulls css style sheet
                            taskLabel.getStyleClass().add("labelDefaultTask");
                        }
                    } else {
                        // if task is completed
                        if (mt.checkDateCompleted(todayDayOb.getTaskDate())) {
                            // marks completed task with checkmark
                            taskLabel.setText(mt.getTaskName()+ "   ✔");  
                            // pulls css style sheet
                            taskLabel.getStyleClass().add("labelCompletedTask");
                        } else {
                            // pulls css style sheet
                            taskLabel.getStyleClass().add("labelDefaultTask");
                        }             
                        // if Task object is an instance of DailyTask
                        if (mt instanceof DailyTask) {
                            // tool tip for Daily Task
                            taskLabel.setTooltip(new Tooltip("Daily Task"));
                            // else if Task object is an instance of WeeklyTask
                        } else if (mt instanceof WeeklyTask) {
                            // tool tip for Weekly Task
                            taskLabel.setTooltip(new Tooltip("Weekly Task"));
                        } else {
                            // tool tip for Custom Task
                            taskLabel.setTooltip(new Tooltip("Custom Task"));
                        }
                    }

                    // hbox array
                    taskHboxArray[k] = new HBox();
                    // adds taskHboxArray to vboxArray
                    vboxArray[i].getChildren().add(taskHboxArray[k]);
                    // adds taskLabel to taskHboxArray
                    taskHboxArray[k].getChildren().add(taskLabel);
                    // iterator
                    k++;
                } // end 'for' loop
                
                // if there are more than 3 tasks for a given calendar day
                if (todayDayOb.tasksThisDay.size() > 4) {
                    // 'for' loop
                    for (k = 3; k < todayDayOb.tasksThisDay.size(); k++) {
                        // set visibility to false
                        taskHboxArray[k].setVisible(false);
                        // sets managed to false
                        taskHboxArray[k].setManaged(false);
                    } // end 'for' loop
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
                            // int representing last visible task
                            int lastVisible = -1;
                            // 'for' loop
                            for (int m = 0; m < todayDayOb.tasksThisDay.size(); m++) {
                                if (taskHboxArray[m].isVisible()) {
                                    lastVisible = m;
                                }
                                // sets visibility to false
                                taskHboxArray[m].setVisible(false);
                                // sets managed to false
                                taskHboxArray[m].setManaged(false);
                            } // end 'for' loop
                            // int representing the new first visible task
                            int newFirstVisible = lastVisible + 1;
                            if ((newFirstVisible + 3) > todayDayOb.tasksThisDay.size()) {
                                newFirstVisible = todayDayOb.tasksThisDay.size() - 3;
                            }
                            // 'for' loop
                            for (int m = newFirstVisible; m < (newFirstVisible + 3); m++) {
                                // sets visibility to true
                                taskHboxArray[m].setVisible(true);
                                // sets managed to true
                                taskHboxArray[m].setManaged(true);
                            } // end 'for' loop
                        }
                    }); // end event handler
                    
                    // event handler for upArrowButton
                    upArrowButton.setOnAction(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            // int representing first visible task
                            int firstVisible = -1;
                            // 'for' loop
                            for (int m = todayDayOb.tasksThisDay.size() - 1; m >= 0; m--) {
                                if (taskHboxArray[m].isVisible()) {
                                    firstVisible = m;
                                }
                                // sets visibility to false
                                taskHboxArray[m].setVisible(false);
                                // sets managed to false
                                taskHboxArray[m].setManaged(false);
                            } // end 'for' loop
                            // int representing new first visible task
                            int newFirstVisible = firstVisible - 3;
                            if (newFirstVisible < 0) {
                                newFirstVisible = 0;
                            }
                            // 'for' loop
                            for (int m = newFirstVisible; m < (newFirstVisible + 3); m++) {
                                taskHboxArray[m].setVisible(true);
                                taskHboxArray[m].setManaged(true);
                            } // 'for' loop
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
                // tooltip for habitCreationButton
                habitCreationButton.setTooltip(new Tooltip("Set up a new habit to track!"));
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
                        // showHabitEntry() method called
                        habitCreationMenu.showHabitEntry();
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
        private Button notificationsButton;                     // button for notifications screen
        private Button editUsernameButton;                      // button for notifications screen
        private Button deleteGoalButton;                        // button for deleting goal from calendar screen
        private Button twitterButton;                           // button for Twitter option
        private Stage optionsStage;                             // stage for Options
        private Button dbxButton;                               // button for Dropbox

        private Stage editUsernameStage;                        // stage for screen to edit username
        private Label editUsernameLabel;                        // label for username
        private TextField editUsernameTextField;                // textfield for entering a username
        private Button editUsernameApplyButton;                 // button to apply edited username
        private Button editUsernameCloseButton;                 // button to close screen

        private Label editHabitLabel;                           // label for editHabitComboBox
        private Stage editTasksStage;                           // stage for editing tasks
        private ComboBox editHabitComboBox;                     // comboBox for list of populated habits/skills of user
        private Label editTaskNameLabel;                        // label for editing task name
        private TextField editTaskNameTextField;                // textfield for editing task name
        private Label editGoalLabel;                            // label for editing goal
        private TextField editGoalTextField;                    // textfield for editing goal
        private Label editGoalUnitsLabel;                       // label for editing goal units
        private TextField editGoalUnitsTextField;               // textfield for editing goal units
        private Label editEndDateLabel;                         // label for editing end date
        private DatePicker editEndDate;                         // datepicker for selecting new end date

        private Label editNotesLabel;                           // label for editing notes
        private TextArea editNotesTextArea;                     // textarea for editing notes
        private Button editTaskApplyButton;                     // button for applying task edits
        private Button editTaskCloseButton;                     // button for closing edit task screen
        private Button editTaskEndTaskButton;                   // button for ending a task
        private Button editTaskDeleteButton;                    // button for deleting a task

        private HBox editTasksHbox1;                            // HBoxes for layout
        private HBox editTasksHbox2;
        private HBox editTasksHbox3;
        private HBox editTasksHbox4;
        private HBox editTasksHbox5;
        private HBox editTasksHbox6;
        private HBox editTasksHbox7;

        // constructor
					  
        public Options() {
            // creates new stage
            optionsStage = new Stage();
            // creates window for editing tasks
            createEditTasksWindow();

            // sets title of optionsStage
            optionsStage.setTitle("Options");
            // adds skillblazer icon to optionsStage
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
            // tooltip for notificationsButton
            notificationsButton.setTooltip(new Tooltip("Turn notifications on or off"));
            // event handler for notificationsButton
            notificationsButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // creates a new stage
                    Stage notificationsStage = new Stage();
                    // add skillblazer icon to notificationsStage
                    notificationsStage.getIcons().add(new Image("/llama.jpg"));
                    // label for enable notifications message
                    Label enableNotifications = new Label("Enable Notifications?");
                    // button for user to select yes
                    Button yesButton = new Button("Yes");
                    // tooltip for yesButton
                    yesButton.setTooltip(new Tooltip("Go for it!"));
                    // button for user to select no
                    Button noButton = new Button("No");
                    // tooltip for noButton
                    noButton.setTooltip(new Tooltip("No thanks"));
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
                    // adds all notificationsHbox1 to notificationsVBox
                    notificationsVBox.getChildren().add(notificationsHbox1);
                    // adds all notificationsHbox2 to notificationsVBox
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

            // hbox for 3rd vbox row
            HBox optionsButtonHbox2 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox2.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox2.getStyleClass().add("optionsButtonHboxes");
            // initializes editUsernameButton
            editUsernameButton = new Button();
            // sets text for editUsernameButton
            editUsernameButton.setText("Edit Username");
            // tooltip for editUsernameButton
            editUsernameButton.setTooltip(new Tooltip("Edit your username"));
            // sets alignment for editUsernameButton
            editUsernameButton.setAlignment(Pos.CENTER);
            // event handler for editUsernameButton
            editUsernameButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // method to show screen to edit username
                    showEditUsername();
                }
            }); // end event handler
            // adds editUsernameButton to optionsButtonHbox2
            optionsButtonHbox2.getChildren().add(editUsernameButton);
            
            // hbox for 3rd vbox row
            HBox optionsButtonHbox3 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox3.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox3.getStyleClass().add("optionsButtonHboxes");
            // initializes deleteGoalButton
            deleteGoalButton = new Button();
            // sets text for deleteGoalButton
            deleteGoalButton.setText("Edit, Delete, or End a Habit");
            // tooltip for deleteGoalButton
            deleteGoalButton.setTooltip(new Tooltip("Edit, Delete, or End a Habit"));
            // sets alignment for deleteGoalButton
            deleteGoalButton.setAlignment(Pos.CENTER);
            // event handler for deleteGoalButton
            deleteGoalButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // method to show screen to edit tasks
                    showEditTasks();
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
            // sets style of twitterButton
            twitterButton.setStyle("-fx-background-color: #00bfff;");
            // sets text for twitterButton
            twitterButton.setText("Send Tweet");
            // tooltip for twitterButton
            twitterButton.setTooltip(new Tooltip("Send a tweet!"));
            // sets alignment for twitterButton
            twitterButton.setAlignment(Pos.CENTER);
            // event handler for twitterButton
            twitterButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // instantiates TwitterIntegration
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
/*
            //create upload to dropbox 
            // hbox for 5th vbox row
            HBox optionsButtonHbox5 = new HBox();
            // sets alignment for hbox
            optionsButtonHbox5.setAlignment(Pos.CENTER);
            // pulls css specs from style sheet
            optionsButtonHbox5.getStyleClass().add("optionsButtonHboxes");
            // initializes dbxButton
            dbxButton = new Button();
            // sets text for dbxButton
            dbxButton.setText("Upload to Dropbox");
            // tooltip for dbxButton
            dbxButton.setTooltip(new Tooltip("Upload task file to Dropbox!"));
            // sets alignment for dbxButton
            dbxButton.setAlignment(Pos.CENTER);
            // event handler for twitterButton
            /*dbxButton.setOnAction(new EventHandler() {
               @Override
                public void handle(Event event) {
                   if (enableDropBoxIntegration !=true){
                   System.out.println("please enable dropbox");
            }
            
            else{
            DropBoxIntegration dbxIntegration = new DropBoxIntegration();
                    dbxIntegration.syncDBX();
					 
            }
            }); 
            // end event handler
            // adds dbxButton to optionsButtonHbox5
            optionsButtonHbox5.getChildren().add(dbxButton);
            */
            // new vbox layout
            VBox optionsVBox = new VBox();
            // pulls css specs from style sheet
            optionsVBox.getStyleClass().add("secondaryWindow");

            // creates empty regions (for layout/alignment purposes)
            Region emptyRegion1 = new Region();
            Region emptyRegion2 = new Region();
            Region emptyRegion3 = new Region();
            Region emptyRegion4 = new Region();
            // adds all hboxes and regions to optionsVBox
            optionsVBox.getChildren().add(optionsButtonHbox1);
            optionsVBox.getChildren().add(emptyRegion1);
            optionsVBox.getChildren().add(optionsButtonHbox2);
            optionsVBox.getChildren().add(emptyRegion2);
            optionsVBox.getChildren().add(optionsButtonHbox3);
            optionsVBox.getChildren().add(emptyRegion3);
            optionsVBox.getChildren().add(optionsButtonHbox4);
            optionsVBox.getChildren().add(emptyRegion4);
            //optionsVBox.getChildren().add(optionsButtonHbox5);
            
            // adds this pane/layout to the scene
            Scene optionsScene = new Scene(optionsVBox, 350, 350);
            // adds optionsScene to optionsStage 
            optionsStage.setScene(optionsScene);
            // gets css style sheet
            optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // event handler for optionsStage; hideOptions() method called
            optionsStage.setOnCloseRequest(e -> hideOptions());
            
            // instantiates editUsernameStage
            editUsernameStage = new Stage();
            // sets title for editUsernameStage
            editUsernameStage.setTitle("Edit Username");
            // add skillblazer icon to editUsernameStage
            editUsernameStage.getIcons().add(new Image("/llama.jpg"));
            // instantiates editUsernameLabel
            editUsernameLabel = new Label("Username:");
            // instantiates editUsernameTextField
            editUsernameTextField = new TextField();
            // instantiates editUsernameApplyButton
            editUsernameApplyButton = new Button("Apply");
            // instantiates editUsernameCloseButton
            editUsernameCloseButton = new Button("Close");
        
            // event handler for editUsernameCloseButton
            editUsernameCloseButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    editUsernameStage.hide();
                }
            }); // end event handler
            
            // event handler for editUsernameApplyButton
            editUsernameApplyButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // new alert box - confirmation
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to change your username?");
                    Optional<ButtonType> result = alert.showAndWait();
                    // if user clicks "OK"
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // sets new username
                        skbUserProfile.setUserName(editUsernameTextField.getText());
                        // sets text of usernameLabel with new username
                        usernameLabel.setText("Hello, " + skbUserProfile.getUserName() + "!");
                        // hides editUsernameStage
                        editUsernameStage.hide();
                    }                
                }
            }); // end event handler
        
            // new empty region (for layout purposes)
            Region emptyRegion5 = new Region();
            // for layout purposes
            HBox.setHgrow(emptyRegion5, Priority.ALWAYS);
            // new empty region (for layout purposes)
            Region emptyRegion6 = new Region();
            // for layout purposes
            HBox.setHgrow(emptyRegion6, Priority.ALWAYS);
            // hbox for editing username screen
            HBox editUsernameHbox1 = new HBox();
            // hbox for editing username screen
            HBox editUsernameHbox2 = new HBox();
            // adds editUsernameLabel to editUsernameHbox1
            editUsernameHbox1.getChildren().add(editUsernameLabel);
            // adds emptyRegion5 to editUsernameHbox1
            editUsernameHbox1.getChildren().add(emptyRegion5);
            // adds editUsernameTextField to editUsernameHbox1
            editUsernameHbox1.getChildren().add(editUsernameTextField);
            // adds editUsernameApplyButton to editUsernameHbox2
            editUsernameHbox2.getChildren().add(editUsernameApplyButton);
            // adds emptyRegion6 to editUsernameHbox2
            editUsernameHbox2.getChildren().add(emptyRegion6);
            // adds editUsernameCloseButton to editUsernameHbox2
            editUsernameHbox2.getChildren().add(editUsernameCloseButton);
             
            // new vbox layout
            VBox editUsernameVbox = new VBox();
            // pulls css specs from style sheet
            editUsernameVbox.getStyleClass().add("secondaryWindow");
            // adds editUsernameHbox1 to editUsernameVbox
            editUsernameVbox.getChildren().add(editUsernameHbox1);
            // adds editUsernameHbox2 to editUsernameVbox
            editUsernameVbox.getChildren().add(editUsernameHbox2);
            
            // new scene for editing username screen; adds editUsernameVbox to the scene
            Scene editUsernameScene = new Scene(editUsernameVbox, 600, 300);
             // adds editUsernameScene to editUsernameStage 
            editUsernameStage.setScene(editUsernameScene);
            // gets css style sheet
            editUsernameScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // event handler for editUsernameStage; hide() method called
            editUsernameStage.setOnCloseRequest(e -> {editUsernameStage.hide();});
            } // end constructor
        
		 

															 
        // method to show optionsStage and bring to the front
        public void showOptions() {
            // shows optionsStage
            optionsStage.show();
            // brings optionsStage to the front
            optionsStage.toFront();
        } // end showOptions() method
        
        // method to hide optionsStage
        public void hideOptions() {
            // hides optionsStage
            optionsStage.hide();
        } // end hideOptions() method
        
        // method to close optionsStage
        public void closeOptions() {
            // closes optionsStage
            optionsStage.close();
        } // end closeOptions() method
        
        // method to include all of the implementation for the edit/delete/end task screen within Options
        public void createEditTasksWindow() {
            // creates new stage
            editTasksStage = new Stage();
            // sets title of editTasksStage
            editTasksStage.setTitle("Edit, Delete, or End Tasks");
            // add skillblazer icon to editTasksStage
            editTasksStage.getIcons().add(new Image("/llama.jpg"));
            
             // initializes editHabitLabel
            editHabitLabel = new Label();
            // sets text for editHabitLabel
            editHabitLabel.setText("Habit/Skill:");
            // initializes editHabitComboBox
            editHabitComboBox = new ComboBox();
            // tooltip for editHabitComboBox
            editHabitComboBox.setTooltip(new Tooltip("Select a habit/skill"));
            // for each Task object of taskList
            for (Task mt : taskList) {
                // adds object to combobox; displayed string is the object's toString() Method
                editHabitComboBox.getItems().add(mt);
            } // end 'for' loop
            
            // hbox
            editTasksHbox1 = new HBox();
            // pulls css styling information
            editTasksHbox1.getStyleClass().add("progressButtonHboxes");
             // hbox
            editTasksHbox2 = new HBox();
            // pulls css styling information
            editTasksHbox2.getStyleClass().add("progressButtonHboxes");
             // hbox
            editTasksHbox3 = new HBox();
            // pulls css styling information
            editTasksHbox3.getStyleClass().add("progressButtonHboxes");
             // hbox
            editTasksHbox4 = new HBox();
            // pulls css styling information
            editTasksHbox4.getStyleClass().add("progressButtonHboxes");
            // hbox
            editTasksHbox5 = new HBox();
            // pulls css styling information
            editTasksHbox5.getStyleClass().add("progressButtonHboxes");
            // hbox
            editTasksHbox6 = new HBox();
            // pulls css styling information
            editTasksHbox6.getStyleClass().add("progressButtonHboxes");
            // hbox
            editTasksHbox7 = new HBox();
            // pulls css styling information
            editTasksHbox7.getStyleClass().add("progressButtonHboxes");            
											
            // initializes editTaskNameLabel
            editTaskNameLabel  = new Label("Task Name:");
            // initializes editTaskNameTextField
            editTaskNameTextField  = new TextField();
            // initializes editGoalLabel
            editGoalLabel = new Label("Goal:");
            // initializes editGoalTextField
            editGoalTextField = new TextField();
											 
            // initializes editGoalUnitsLabel
            editGoalUnitsLabel = new Label("Goal Units:");
            // initializes editGoalUnitsTextField
            editGoalUnitsTextField  = new TextField();
            // initializes editEndDateLabel
            editEndDateLabel = new Label("End Date:");
            // initializes editEndDate
            editEndDate = new DatePicker();
            // initializes editNotesLabel
            editNotesLabel = new Label("Notes:");
            // initializes editNotesTextArea
            editNotesTextArea = new TextArea();
            // sets preferred size of editNotesTextArea
            editNotesTextArea.setPrefSize(350, 100);
            // initializes editTaskApplyButton
            editTaskApplyButton = new Button("Apply Edits");
            // initializes editTaskCloseButton
            editTaskCloseButton = new Button("Close");
            // initializes editTaskDeleteButton
            editTaskDeleteButton = new Button("Delete");
            // initializes editTaskEndTaskButton
            editTaskEndTaskButton = new Button("End");
            
            // event handler for editTaskApplyButton
            editTaskApplyButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // task object
                    Task mt = (Task)editHabitComboBox.getSelectionModel().getSelectedItem();
                    // new Alert - confirmation for editing a task
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to edit " + mt.getTaskName() + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (editTaskApplyEdits()) {
                            // draws new calendar
                            drawCalendar();
                            // closes window to edit tasks
                            closeEditTasks();
                        }
                    }
                }
            }); // end event handler
            
            // event handler for editTaskCloseButton
            editTaskCloseButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // closes window to edit tasks
                    closeEditTasks();
                }
            }); // end event handler
            
            // event handler for editTaskDeleteButton
            editTaskDeleteButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // task object
                    Task mt = (Task)editHabitComboBox.getSelectionModel().getSelectedItem();
                    // new alert - confirmation to user for deleting a task
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete " + mt.getTaskName() + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // removes Task object from taskList
                        taskList.remove(mt);
                        // removes JSON file for Task
                        JSONWriter.removeFileFromDisk(mt);
                        // draws new calendar
                        drawCalendar();
                        // closes window to edit tasks
                        closeEditTasks();
                    }                
                }
            }); // end event handler
            
            // event handler for editTaskEndTaskButton
            editTaskEndTaskButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    // task object
                    Task mt = (Task)editHabitComboBox.getSelectionModel().getSelectedItem();
                    // new alert - confirmation for ending a task
                    Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to end " + mt.getTaskName() + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    // if user clicks "OK" button
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (editTaskApplyEdits()) {
                            // new GregorianCalendar object
                            GregorianCalendar todaysDate = new GregorianCalendar();
                            // sets end date and isCompleted in task object
                            mt.endTask(todaysDate);
                            // draws new calendar
                            drawCalendar();
                            // closes window for editing tasks
                            closeEditTasks();
                        }
                    }
				 
                }
            }); // end event handler
            
            // creates some empty regions - for layout purposes
            Region emptyRegion1 = new Region();
            HBox.setHgrow(emptyRegion1, Priority.ALWAYS);
            Region emptyRegion2 = new Region();
            HBox.setHgrow(emptyRegion2, Priority.ALWAYS);
            Region emptyRegion3 = new Region();
            HBox.setHgrow(emptyRegion3, Priority.ALWAYS);
            Region emptyRegion4 = new Region();
            HBox.setHgrow(emptyRegion4, Priority.ALWAYS);
            Region emptyRegion5 = new Region();
            HBox.setHgrow(emptyRegion5, Priority.ALWAYS);
            Region emptyRegion6 = new Region();
            HBox.setHgrow(emptyRegion6, Priority.ALWAYS);
            Region emptyRegion7 = new Region();
            HBox.setHgrow(emptyRegion7, Priority.ALWAYS);
            Region emptyRegion8 = new Region();
            HBox.setHgrow(emptyRegion8, Priority.ALWAYS);
            // adds editHabitLabel to editTasksHbox1
            editTasksHbox1.getChildren().add(editHabitLabel);
            // adds editHabitComboBox to editTasksHbox1
            editTasksHbox1.getChildren().add(editHabitComboBox);
            // adds editTaskNameLabel to editTasksHbox2
            editTasksHbox2.getChildren().add(editTaskNameLabel);
            // adds emptyRegion1 to editTasksHbox2
            editTasksHbox2.getChildren().add(emptyRegion1);
            // adds editTaskNameTextField to editTasksHbox2
            editTasksHbox2.getChildren().add(editTaskNameTextField);
            // adds editGoalLabel to editTasksHbox3
            editTasksHbox3.getChildren().add(editGoalLabel);
            // adds emptyRegion2 to editTasksHbox3
            editTasksHbox3.getChildren().add(emptyRegion2);
            // adds editGoalTextField to editTasksHbox3
            editTasksHbox3.getChildren().add(editGoalTextField);
            // adds editGoalUnitsLabel to editTasksHbox4
            editTasksHbox4.getChildren().add(editGoalUnitsLabel);
            // adds emptyRegion3 to editTasksHbox4
            editTasksHbox4.getChildren().add(emptyRegion3);
            // adds editGoalUnitsTextField to editTasksHbox4
            editTasksHbox4.getChildren().add(editGoalUnitsTextField);
            // adds editEndDateLabel to editTasksHbox5
            editTasksHbox5.getChildren().add(editEndDateLabel);
            // adds emptyRegion4 to editTasksHbox5
            editTasksHbox5.getChildren().add(emptyRegion4);
            // adds editEndDate to editTasksHbox5
            editTasksHbox5.getChildren().add(editEndDate);
            // adds editNotesLabel to editTasksHbox6
            editTasksHbox6.getChildren().add(editNotesLabel);
            // adds emptyRegion5 to editTasksHbox6
            editTasksHbox6.getChildren().add(emptyRegion5);
            // adds editNotesTextArea to editTasksHbox6
            editTasksHbox6.getChildren().add(editNotesTextArea);
            // adds editTaskApplyButton to editTasksHbox7
            editTasksHbox7.getChildren().add(editTaskApplyButton);
            // adds emptyRegion6 to editTasksHbox7
            editTasksHbox7.getChildren().add(emptyRegion6);
            // adds editTaskDeleteButton to editTasksHbox7
            editTasksHbox7.getChildren().add(editTaskDeleteButton);
            // adds emptyRegion7 to editTasksHbox7
            editTasksHbox7.getChildren().add(emptyRegion7);
            // adds editTaskEndTaskButton to editTasksHbox7
            editTasksHbox7.getChildren().add(editTaskEndTaskButton);
            // adds emptyRegion8 to editTasksHbox7
            editTasksHbox7.getChildren().add(emptyRegion8);
            // adds editTaskCloseButton to editTasksHbox7
            editTasksHbox7.getChildren().add(editTaskCloseButton);
            
            // sets editHabitComboBox action handler
            editHabitComboBox.setOnAction(e -> {updateEditTaskFields();});
            // disable all hboxes for now
										 
											 
            editTasksHbox2.setVisible(false);
            editTasksHbox2.setManaged(false);
            editTasksHbox3.setVisible(false);
            editTasksHbox3.setManaged(false);
            editTasksHbox4.setVisible(false);
            editTasksHbox4.setManaged(false);
            editTasksHbox5.setVisible(false);
            editTasksHbox5.setManaged(false);
            editTasksHbox6.setVisible(false);
            editTasksHbox6.setManaged(false);
            editTasksHbox7.setVisible(false);
            editTasksHbox7.setManaged(false);

            // if habitComboBox is not empty
            if (!editHabitComboBox.getItems().isEmpty()) {
                editHabitComboBox.getSelectionModel().select(0);
                // updates  the fields based on what is selected in the combobox
                updateEditTaskFields();
            }
            
            // new vbox layout
            VBox editTasksVbox = new VBox();
            // pulls css specs from style sheet
            editTasksVbox.getStyleClass().add("secondaryWindow");
            // adds editTasksHbox1 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox1);
            // adds editTasksHbox2 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox2);
            // adds editTasksHbox3 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox3);
            // adds editTasksHbox4 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox4);
            // adds editTasksHbox5 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox5);
            // adds editTasksHbox6 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox6);
            // adds editTasksHbox7 to editTasksVbox
            editTasksVbox.getChildren().add(editTasksHbox7);
            // adds this pane/layout to the scene
            Scene editTasksScene = new Scene(editTasksVbox, 600, 600);
            // adds editTasksScene to editTasksStage 
            editTasksStage.setScene(editTasksScene);
            // gets css style sheet
            editTasksScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // event handler for editTasksStage; hides the screen for editing tasks
            editTasksStage.setOnCloseRequest(e -> hideEditTasks());
        } // createEditTasksWindow() method
        
        // method to apply the edits indicated by the user from the edit/delete task screen
        public boolean editTaskApplyEdits() {
            // task object
            Task mt = (Task)editHabitComboBox.getSelectionModel().getSelectedItem();
            // string holding new task name
            String taskName = editTaskNameTextField.getText();
            if (taskName.trim().isEmpty()) {
                // new alert - warning to enter a habit name
                Alert alert = new Alert(AlertType.WARNING, "Please enter a habit name!");
                // shows alert
                alert.show();
                return false;
            }
            // string holding notes
            String notes = editNotesTextArea.getText();
            // if Task object is an instance of CumulativeTask
            if (mt instanceof CumulativeTask) {
                // double holding goal value
                double goalValue;
                // 'try & catch' block
                try{
                    goalValue = Double.parseDouble(editGoalTextField.getText());
                    // catches NumberFormatException
                } catch (NumberFormatException e) {
                    // new alert - warning to enter a numeric value for goal
                    Alert alert = new Alert(AlertType.WARNING, "Please enter a numeric value for your goal!");
                    // shows alert
                    alert.show();
                    return false;
                }
                if (goalValue<=0) {
                    // new alert - warning to enter a positive goal amount
                    Alert alert = new Alert(AlertType.WARNING, "Please enter a positive goal!");
                    // shows alert
                    alert.show();
                    return false;
                }
                // GregorianCalendar object
                GregorianCalendar endDate = new GregorianCalendar();
                if (editEndDate.getValue() == null) {
                    // new alert - warning to select an end date
                    Alert alert = new Alert(AlertType.WARNING, "Please select an end date!");
                    // shows alert
                    alert.show();
                    return false;
                }
                endDate.setTime(Date.from(editEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                if (mt.getStartDate().compareTo(endDate)>=0) {
                    // new alert - warning to enter an ende date after the start date
                    Alert alert = new Alert(AlertType.WARNING, "Please enter an end date after your start date!");
                    // shows alert
                    alert.show();
                    return false;
                }
                String goalUnits = editGoalUnitsTextField.getText();
                ((CumulativeTask)mt).setEndDate(endDate);
                ((CumulativeTask)mt).setTaskUnits(goalUnits);
                ((CumulativeTask)mt).setGoalToReach(goalValue);
            }
            mt.setTaskName(taskName);
            mt.setNotes(notes);   

            return true;
        }
        
               // method to show showEditUsername
        public void showEditUsername() {
            editUsernameTextField.setText(skbUserProfile.getUserName());
            editUsernameStage.show();
            editUsernameStage.toFront();
        } // end showEditTasks() method 
        
        
        

        // method to show editTasksStage
        public void showEditTasks() {
            editTasksStage.show();
            // remove everything in habitComboBox
            editHabitComboBox.getItems().clear();
            // add tasks in taskList to habitComboBox
            for (Task mt : taskList) {
                // add object to combo box displayed string is objects toString Method
                editHabitComboBox.getItems().add(mt);
            }
            // if habitComboBox is not empty
            if (!editHabitComboBox.getItems().isEmpty()) {
                // select the first combo box entry
                editHabitComboBox.getSelectionModel().select(0);
                updateEditTaskFields();
            }
            editTasksStage.toFront();
        } // end showEditTasks() method
        
        // method to hide editTasksStage
        public void hideEditTasks() {
            editTasksStage.hide();
        } // end hideEditTasks() method
        
        // method to close editTasksStage
        public void closeEditTasks() {
            editTasksStage.close();
        } // end closeEditTasks() method
        
        
         // update the fields for the selected task
        public void updateEditTaskFields() {
            Task mt = (Task)editHabitComboBox.getSelectionModel().getSelectedItem();
            if (mt != null) {
                editTaskNameTextField.setText(mt.getTaskName());
                editNotesTextArea.setText(mt.getNotes());
                editTasksHbox2.setVisible(true);
                editTasksHbox2.setManaged(true);
                editTasksHbox6.setVisible(true);
                editTasksHbox6.setManaged(true);
                editTasksHbox7.setVisible(true);
                editTasksHbox7.setManaged(true);
                
                if (mt instanceof CumulativeTask) {
                    editGoalTextField.setText(""+((CumulativeTask)mt).getGoalToReach());
                    editGoalUnitsTextField.setText(((CumulativeTask)mt).getTaskUnits());
                    Calendar endDate = ((CumulativeTask)mt).getEndDate();
                    editEndDate.setValue(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    editTaskEndTaskButton.setDisable(true);
                    editTasksHbox3.setVisible(true);
                    editTasksHbox3.setManaged(true);
                    editTasksHbox4.setVisible(true);
                    editTasksHbox4.setManaged(true);
                    editTasksHbox5.setVisible(true);
                    editTasksHbox5.setManaged(true);
                 } else {
                    editTaskEndTaskButton.setDisable(false);
                    editTasksHbox3.setVisible(false);
                    editTasksHbox3.setManaged(false);
                    editTasksHbox4.setVisible(false);
                    editTasksHbox4.setManaged(false);
                    editTasksHbox5.setVisible(false);
                    editTasksHbox5.setManaged(false);
                }
            }
        }

       
    }// end class Options


    // inner class for 'Lifetime Metrics' menu
    class LifetimeMetrics {
        
        
         // member fields - GUI elements
        private Label habitLabel;                       // label for habitComboBox
        private Stage lifetimeMetricsStage;                     // Stage for LifetimeMetrics
        private ComboBox habitComboBox;                 // comboBox for list of populated habits/skills of user
        private Label percentCompletedLabel;
        private TextField percentCompletedTextField;
        private Label totalCompletedLabel;
        private TextField totalCompletedTextField;
        private Label goalLabel;
        private TextField goalTextField;
        private Label maxPossibleLabel;
        private TextField maxPossibleTextField;
        
        private Label currentStreakLabel;
        private TextField currentStreakTextField;
        private Label bestStreakLabel;
        private TextField bestStreakTextField;
        private HBox lifetimeMetricsHbox1;
        private HBox lifetimeMetricsHbox2;
        private HBox lifetimeMetricsHbox3;
        private HBox lifetimeMetricsHbox4;
        private HBox lifetimeMetricsHbox5;
        private HBox lifetimeMetricsHbox6;
        private HBox lifetimeMetricsHbox7;
        // constructor
        public LifetimeMetrics() {
            // creates new stage
            lifetimeMetricsStage = new Stage();
            // sets title
            lifetimeMetricsStage.setTitle("Lifetime Metrics");
            // add skillblazer icon
            lifetimeMetricsStage.getIcons().add(new Image("/llama.jpg"));
            
             // initializes habitLabel
            habitLabel = new Label();
            // sets text for habitLabel
            habitLabel.setText("Habit/Skill:");
            // tooltip for habitLabel
            habitLabel.setTooltip(new Tooltip("Habit name"));
            // initializes habitComboBox
            habitComboBox = new ComboBox();
            // tooltip for habitComboBox
            habitComboBox.setTooltip(new Tooltip("Select a habit/skill"));
            for (Task mt : taskList) {
                // add object to combo box displayed string is objects toString Method
                habitComboBox.getItems().add(mt);
            }
            
            // hbox for 1st metrics vbox row
            lifetimeMetricsHbox1 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox1.getStyleClass().add("progressButtonHboxes");
             // hbox for 2nd metrics vbox row
            lifetimeMetricsHbox2 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox2.getStyleClass().add("progressButtonHboxes");
             // hbox for 3rd metrics vbox row
            lifetimeMetricsHbox3 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox3.getStyleClass().add("progressButtonHboxes");
             // hbox for 4th metrics vbox row
            lifetimeMetricsHbox4 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox4.getStyleClass().add("progressButtonHboxes");
             // hbox for 5th metrics vbox row
            lifetimeMetricsHbox5 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox5.getStyleClass().add("progressButtonHboxes");
             // hbox for 6th metrics vbox row
            lifetimeMetricsHbox6 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox6.getStyleClass().add("progressButtonHboxes");
             // hbox for 6th metrics vbox row
            lifetimeMetricsHbox7 = new HBox();
            // pulls css styling information
            lifetimeMetricsHbox7.getStyleClass().add("progressButtonHboxes");
            // initializes percentCompletedLabel
            percentCompletedLabel = new Label("Percent Completed:");
            // initializes percentCompletedTextField
            percentCompletedTextField  = new TextField();
            percentCompletedTextField.setTooltip(new Tooltip("% that a task has been completed"));
            // sets editable to false
            percentCompletedTextField.setEditable(false);
            // initializes totalCompletedLabel
            totalCompletedLabel = new Label("Total Completed:");
            // initializes totalCompletedTextField
            totalCompletedTextField  = new TextField();
            // tooltip for totalCompletedTextField
            totalCompletedTextField.setTooltip(new Tooltip("Total # days completed"));
            // sets editable to false
            totalCompletedTextField.setEditable(false);
            // initializes goalLabel
            goalLabel  = new Label("Goal:");
            // initializes goalTextField
            goalTextField  = new TextField();
            // tooltip for goalTextField
            goalTextField.setTooltip(new Tooltip("This is your goal"));
            // sets editable to false
            goalTextField.setEditable(false);
            // initializes maxPossibleLabel
            maxPossibleLabel  = new Label("Max Possible:");
            // initializes maxPossibleTextField
            maxPossibleTextField  = new TextField();
            // tooltip for maxPossibleTextField
            maxPossibleTextField.setTooltip(new Tooltip("This is your maximum possible number of completions"));
            // sets editable to false
            maxPossibleTextField.setEditable(false);
            // initializes currentStreakLabel
            currentStreakLabel  = new Label("Current Streak:");
            // initializes currentStreakTextField
            currentStreakTextField  = new TextField();
            // tooltip for currentStreakTextField
            currentStreakTextField.setTooltip(new Tooltip("This is your current streak"));
            // sets editable to false
            currentStreakTextField.setEditable(false);
            // initializes bestStreakLabel
            bestStreakLabel  = new Label("Best Streak:");
            // initializes bestStreakTextField
            bestStreakTextField  = new TextField();
            // tooltip for bestStreakTextField
            bestStreakTextField.setTooltip(new Tooltip("This is your best streak"));
            // sets editable to false
            bestStreakTextField.setEditable(false);
            // create some empty regions
            Region emptyRegion1 = new Region();
            HBox.setHgrow(emptyRegion1, Priority.ALWAYS);
            Region emptyRegion2 = new Region();
            HBox.setHgrow(emptyRegion2, Priority.ALWAYS);
            Region emptyRegion3 = new Region();
            HBox.setHgrow(emptyRegion3, Priority.ALWAYS);
            Region emptyRegion4 = new Region();
            HBox.setHgrow(emptyRegion4, Priority.ALWAYS);
            Region emptyRegion5 = new Region();
            HBox.setHgrow(emptyRegion5, Priority.ALWAYS);
            Region emptyRegion6 = new Region();
            HBox.setHgrow(emptyRegion6, Priority.ALWAYS);
            // add fields to hboxes
            lifetimeMetricsHbox1.getChildren().add(habitLabel);
            lifetimeMetricsHbox1.getChildren().add(habitComboBox);
            lifetimeMetricsHbox2.getChildren().add(percentCompletedLabel);
            lifetimeMetricsHbox2.getChildren().add(emptyRegion1);
            lifetimeMetricsHbox2.getChildren().add(percentCompletedTextField);
            lifetimeMetricsHbox3.getChildren().add(totalCompletedLabel);
            lifetimeMetricsHbox3.getChildren().add(emptyRegion2);
            lifetimeMetricsHbox3.getChildren().add(totalCompletedTextField);
            lifetimeMetricsHbox4.getChildren().add(goalLabel);
            lifetimeMetricsHbox4.getChildren().add(emptyRegion3);
            lifetimeMetricsHbox4.getChildren().add(goalTextField);
            lifetimeMetricsHbox5.getChildren().add(maxPossibleLabel);
            lifetimeMetricsHbox5.getChildren().add(emptyRegion4);
            lifetimeMetricsHbox5.getChildren().add(maxPossibleTextField);
            lifetimeMetricsHbox6.getChildren().add(currentStreakLabel);
            lifetimeMetricsHbox6.getChildren().add(emptyRegion5);
            lifetimeMetricsHbox6.getChildren().add(currentStreakTextField);
            lifetimeMetricsHbox7.getChildren().add(bestStreakLabel);
            lifetimeMetricsHbox7.getChildren().add(emptyRegion6);
            lifetimeMetricsHbox7.getChildren().add(bestStreakTextField);
            
            // set habitComboBox action handler
            habitComboBox.setOnAction(e -> {updateLifetimeMetricFields();});
            // disables all hboxes for now
										 
												   
            lifetimeMetricsHbox2.setVisible(false);
            lifetimeMetricsHbox2.setManaged(false);
            lifetimeMetricsHbox3.setVisible(false);
            lifetimeMetricsHbox3.setManaged(false);
            lifetimeMetricsHbox4.setVisible(false);
            lifetimeMetricsHbox4.setManaged(false);
            lifetimeMetricsHbox5.setVisible(false);
            lifetimeMetricsHbox5.setManaged(false);
            lifetimeMetricsHbox6.setVisible(false);
            lifetimeMetricsHbox6.setManaged(false);
            lifetimeMetricsHbox7.setVisible(false);
            lifetimeMetricsHbox7.setManaged(false);
            // if habitComboBox is not empty
            if (!habitComboBox.getItems().isEmpty()) {
                habitComboBox.getSelectionModel().select(0);
                updateLifetimeMetricFields();
            }
            

            // new vbox layout
            VBox lifeMetricsVbox = new VBox();
            // necessary to pull css specs from style sheet
            lifeMetricsVbox.getStyleClass().add("secondaryWindow");
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox1);
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox2);
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox3);
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox4);
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox5);
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox6);
            lifeMetricsVbox.getChildren().add(lifetimeMetricsHbox7);
            // adds this pane/layout to the scene
            Scene lifeMetricsScene = new Scene(lifeMetricsVbox, 600, 600);
            // adds scene to stage 
            lifetimeMetricsStage.setScene(lifeMetricsScene);
            // gets css style sheet
            lifeMetricsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // shows the stage
            lifetimeMetricsStage.setOnCloseRequest(e -> hideLifetimeMetrics());
        }
        
        // method to show lifetimeMetricsStage
        public void showLifetimeMetrics() {
            lifetimeMetricsStage.show();
            // remove everything in habitComboBox
            habitComboBox.getItems().clear();
            // add tasks in taskList to habitComboBox
            for (Task mt : taskList) {
                // add object to combo box displayed string is objects toString Method
                habitComboBox.getItems().add(mt);
            }
            // if habitComboBox is not empty
            if (!habitComboBox.getItems().isEmpty()) {
                // select the first combo box entry
                habitComboBox.getSelectionModel().select(0);
                updateLifetimeMetricFields();
            }
            lifetimeMetricsStage.toFront();
        } // end showLifetimeMetrics() method
        
        // method to hide lifetimeMetricsStage
        public void hideLifetimeMetrics() {
            lifetimeMetricsStage.hide();
        } // end hideLifetimeMetrics() method
        
        // method to close lifetimeMetricsStage
        public void closeLifetimeMetrics() {
            lifetimeMetricsStage.close();
        } // end closeLifetimeMetrics() method
        
        // updates the fields for the selected task
        public void updateLifetimeMetricFields() {
            Task mt = (Task)habitComboBox.getSelectionModel().getSelectedItem();
                GregorianCalendar todayDate = new GregorianCalendar();
                if (mt instanceof CumulativeTask) {
                    double totalCompleted = ((CumulativeTask)mt).getTotalProgress();
                    double goal = ((CumulativeTask)mt).getGoalToReach();
                    String percentString = String.format("%.2f",totalCompleted/goal*100.0);
                    percentCompletedTextField.setText(percentString + "%");
                    totalCompletedTextField.setText(totalCompleted + " " + ((CumulativeTask)mt).getTaskUnits());
                    goalTextField.setText(goal + " " + ((CumulativeTask)mt).getTaskUnits());
                    lifetimeMetricsHbox2.setVisible(true);
                    lifetimeMetricsHbox2.setManaged(true);
                    lifetimeMetricsHbox3.setVisible(true);
                    lifetimeMetricsHbox3.setManaged(true);
                    lifetimeMetricsHbox4.setVisible(true);
                    lifetimeMetricsHbox4.setManaged(true);
                    lifetimeMetricsHbox5.setVisible(false);
                    lifetimeMetricsHbox5.setManaged(false);
                    lifetimeMetricsHbox6.setVisible(false);
                    lifetimeMetricsHbox6.setManaged(false);
                    lifetimeMetricsHbox7.setVisible(false);
                    lifetimeMetricsHbox7.setManaged(false);
                 } else if  (mt instanceof CustomTask){
                    double totalCompleted = ((CustomTask)mt).getNumCompleted();
                    double maxPossible = ((CustomTask)mt).getMaxPossible(todayDate);
                    if (maxPossible > 0) {
                        String percentString = String.format("%.2f",totalCompleted/maxPossible*100.0);
                        percentCompletedTextField.setText(percentString + "%");
                    } else {
                        percentCompletedTextField.setText("N/A");
                    }
                    totalCompletedTextField.setText(""+totalCompleted);
                    maxPossibleTextField.setText(""+maxPossible);
                    currentStreakTextField.setText(""+((CustomTask)mt).getCurrentStreak(todayDate));
                    bestStreakTextField.setText(""+((CustomTask)mt).getBestStreak());
                    lifetimeMetricsHbox2.setVisible(true);
                    lifetimeMetricsHbox2.setManaged(true);
                    lifetimeMetricsHbox3.setVisible(true);
                    lifetimeMetricsHbox3.setManaged(true);
                    lifetimeMetricsHbox4.setVisible(false);
                    lifetimeMetricsHbox4.setManaged(false);
                    lifetimeMetricsHbox5.setVisible(true);
                    lifetimeMetricsHbox5.setManaged(true);
                    lifetimeMetricsHbox6.setVisible(true);
                    lifetimeMetricsHbox6.setManaged(true);
                    lifetimeMetricsHbox7.setVisible(true);
                    lifetimeMetricsHbox7.setManaged(true);
                } else if  (mt instanceof DailyTask){
                    double totalCompleted = ((DailyTask)mt).getNumCompleted();
                    double maxPossible = ((DailyTask)mt).getMaxPossible(todayDate);
                    if (maxPossible > 0) {
                        String percentString = String.format("%.2f",totalCompleted/maxPossible*100.0);
                        percentCompletedTextField.setText(percentString + "%");
                    } else {
                        percentCompletedTextField.setText("N/A");
                    }
                    totalCompletedTextField.setText(""+totalCompleted);
                    maxPossibleTextField.setText(""+maxPossible);
                    currentStreakTextField.setText(""+((DailyTask)mt).getCurrentStreak(todayDate));
                    bestStreakTextField.setText(""+((DailyTask)mt).getBestStreak());
                    lifetimeMetricsHbox2.setVisible(true);
                    lifetimeMetricsHbox2.setManaged(true);
                    lifetimeMetricsHbox3.setVisible(true);
                    lifetimeMetricsHbox3.setManaged(true);
                    lifetimeMetricsHbox4.setVisible(false);
                    lifetimeMetricsHbox4.setManaged(false);
                    lifetimeMetricsHbox5.setVisible(true);
                    lifetimeMetricsHbox5.setManaged(true);
                    lifetimeMetricsHbox6.setVisible(true);
                    lifetimeMetricsHbox6.setManaged(true);
                    lifetimeMetricsHbox7.setVisible(true);
                    lifetimeMetricsHbox7.setManaged(true);
                } else if  (mt instanceof WeeklyTask){
                    double totalCompleted = ((WeeklyTask)mt).getNumCompleted();
                    double maxPossible = ((WeeklyTask)mt).getMaxPossible(todayDate);
                    if (maxPossible > 0) {
                        String percentString = String.format("%.2f",totalCompleted/maxPossible*100.0);
                        percentCompletedTextField.setText(percentString + "%");
                    } else {
                        percentCompletedTextField.setText("N/A");
                    }
                    totalCompletedTextField.setText(""+totalCompleted);
                    maxPossibleTextField.setText(""+maxPossible);
                    currentStreakTextField.setText(""+((WeeklyTask)mt).getCurrentStreak(todayDate));
                    bestStreakTextField.setText(""+((WeeklyTask)mt).getBestStreak());
                    lifetimeMetricsHbox2.setVisible(true);
                    lifetimeMetricsHbox2.setManaged(true);
                    lifetimeMetricsHbox3.setVisible(true);
                    lifetimeMetricsHbox3.setManaged(true);
                    lifetimeMetricsHbox4.setVisible(false);
                    lifetimeMetricsHbox4.setManaged(false);
                    lifetimeMetricsHbox5.setVisible(true);
                    lifetimeMetricsHbox5.setManaged(true);
                    lifetimeMetricsHbox6.setVisible(true);
                    lifetimeMetricsHbox6.setManaged(true);
                }
    }
        
    } // end class LifetimeMetrics

    // inner class for handling when user clicks on a calendar button
    class HabitCreationButton {

        // member fields - GUI elements
        private Label startDateLabel;                           // label for 'Date to Start Goal'
        private DatePicker startDatePicker;                     // datepicker for user to enter start date for goal
        private Label habitLabel;                               // label for 'Habit Name'
        private TextField habitTextField;                       // textfield for habit name
        private Label goalLabel;                                // label for 'Goal'
        private TextField numTextField;                         // textfield for goal number
        private TextField goalUnitsField;                       // textfield for goal units
        private Label freqLabel;                                // label for 'Frequency'
        private ToggleGroup rbGroup = new ToggleGroup();        // togglegroup for radio button group
        private RadioButton dailyRB;                            // radio button for 'Daily' option
        private RadioButton weeklyRB;                           // radio button for 'Weekly' option
        private RadioButton customRB;                           // radio button for 'Custom' option
        private RadioButton monRB;                              // radio button for 'Monday' option
        private RadioButton tuesRB;                             // radio button for 'Tuesday' option
        private RadioButton wedRB;                              // radio button for 'Wednesday' option
        private RadioButton thursRB;                            // radio button for 'Thursday' option
        private RadioButton friRB;                              // radio button for 'Friday' option
        private RadioButton satRB;                              // radio button for 'Saturday' option
        private RadioButton sunRB;                              // radio button for 'Sunday' option
        private RadioButton cumulativeRB;                       // radio button for 'Cumulative' option
        private Label goalDateLabel;                            // label for 'Date to Complete Goal'
        private DatePicker datePicker;                          // datepicker for user to enter date to complete goal
        private Label notesLabel;                               // label for 'Notes'
        private TextArea notesTextArea;                         // textarea for notes section   
        private Button submitButton;                            // button for submitting informatione entered by user
        private Stage habitEntryStage;                          // Stage for HabitCreationButton
        
        // constructor
        public HabitCreationButton() {

            // instantiates habitEntryStage
            habitEntryStage = new Stage();
            // sets title for habitEntryStage
            habitEntryStage.setTitle("Habit/Skill Creation");
            // adds skillblazer icon
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
            // Tooltip
            startDatePicker.setTooltip(new Tooltip("Select habit/skill start date"));
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
            // Tooltip
            habitTextField.setTooltip(new Tooltip("Choose a name for your habit/skill"));
            // adds habitLabel to habitCreationButtonHbox2
            habitCreationButtonHbox2.getChildren().add(habitLabel);
            // adds habitTextField to habitCreationButtonHbox2
            habitCreationButtonHbox2.getChildren().add(habitTextField);

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
            // Tooltip
            dailyRB.setTooltip(new Tooltip("A daily goal is expected to be completed each day"));
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
            // Tooltip
            weeklyRB.setTooltip(new Tooltip("A weekly goal is expected to be completed once a week"));
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
            // Tooltip
            customRB.setTooltip(new Tooltip("A custom goal is to be completed on the day(s) in which you set"));
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
            // Tooltip
            cumulativeRB.setTooltip(new Tooltip("A cumulative goal is to be completed by a specific end date"));
            // adds cumulativeRB to toggle group rbGroup
            cumulativeRB.setToggleGroup(rbGroup);
            // adds cumulativeRB to habitCreationButtonHbox9
            habitCreationButtonHbox9.getChildren().add(cumulativeRB);

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
            // Tooltip
            numTextField.setTooltip(new Tooltip("Enter a numeric goal to reach (e.g. 5 miles to run)"));
            // sets max size of numTextField
            numTextField.setMaxSize(80, 80);
            // initializes goalUnitsField
            goalUnitsField = new TextField();
            // sets text for goalUnitsField
            goalUnitsField.setText("units");
            // sets max size of goalUnitsField
            goalUnitsField.setMaxSize(80, 80);
            // sets tooltip for goalUnitsField
            goalUnitsField.setTooltip(new Tooltip("Enter the units for the goal (e.g. miles)"));
            // adds goalLabel to habitCreationButtonHbox3
            habitCreationButtonHbox3.getChildren().add(goalLabel);
            // adds numTextField to habitCreationButtonHbox3
            habitCreationButtonHbox3.getChildren().add(numTextField);
            // adds goalUnitsField to habitCreationButtonHbox3
            habitCreationButtonHbox3.getChildren().add(goalUnitsField);
            // sets numTextField as disabled 
            numTextField.setDisable(true);
            // sets goalUnitsField as disabled
            goalUnitsField.setDisable(true);
            
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
            // Tooltip
            datePicker.setTooltip(new Tooltip("Select a deadline for reaching your cumulative goal"));

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
                numTextField.setDisable(true);
                goalUnitsField.setDisable(true);
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
                numTextField.setDisable(true);
                goalUnitsField.setDisable(true);
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
                numTextField.setDisable(true);
                goalUnitsField.setDisable(true);
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
                numTextField.setDisable(false);
                goalUnitsField.setDisable(false);
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
            // Tooltip
            notesTextArea.setTooltip(new Tooltip("Add any notes you would like to record pertaining to your goal"));
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
                    
                                                                        
                    if (createTaskObject()) {
                        habitEntryStage.hide();    // hides window
                        resetHabitEntry();
                        drawCalendar();
                    }
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
            habitCreationVbox.getChildren().add(habitCreationButtonHbox4);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox5);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox6);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox7);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox8);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox9);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox3);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox10);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox11);
            habitCreationVbox.getChildren().add(habitCreationButtonHbox12);

            // adds habitCreationVbox to optionsScene
            Scene optionsScene = new Scene(habitCreationVbox, 800, 800);
            // adds optionsScene to habitEntryStage 
            habitEntryStage.setScene(optionsScene);
            // gets css style sheet
            optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // event handler for habitEntryStage
            habitEntryStage.setOnCloseRequest(e -> hideHabitEntry());
        }
        
        // method to reset the Habit Entry window fields
        public void resetHabitEntry() {
            startDatePicker.setValue(LocalDate.now());
            dailyRB.fire();
            habitTextField.setText("");
            numTextField.setText("");
            notesTextArea.setText("");
            monRB.setSelected(false);
            tuesRB.setSelected(false);
            wedRB.setSelected(false);
            thursRB.setSelected(false);
            friRB.setSelected(false);
            satRB.setSelected(false);
            sunRB.setSelected(false);
            datePicker.setValue(null);   
            // sets text for goalUnitsField
            goalUnitsField.setText("units");
        } // end resetHabitEntry() method
        
        // method to show habitEntryStage and bring to front
        public void showHabitEntry() {
            habitEntryStage.show();
            habitEntryStage.toFront();
        } // end showHabitEntry() method
        
        // method to hide habitEntryStage
        public void hideHabitEntry() {
            habitEntryStage.hide();
        } // end hideHabitEntry() method
        
        // method to close habitEntryStage
        public void closeHabitEntry() {
            habitEntryStage.close();
        } // end closeHabitEntry() method
        
        // method createTaskObject - to create a Task object
        public boolean createTaskObject() {
            String taskName = habitTextField.getText();
            if (taskName.trim().isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING, "Please enter a habit name!");
                alert.show();
                return false;
            }
            String notes = notesTextArea.getText();
            GregorianCalendar startDate = new GregorianCalendar();
            startDate.setTime(Date.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            long taskId = skbUserProfile.incrementTaskNumber();
            if (dailyRB.isSelected()) {
                // Create DailyTask object
                DailyTask newTask = new DailyTask(taskName,taskId,startDate,false,notes);
                taskList.add(newTask);
            } else if (weeklyRB.isSelected()) {
                // Create WeeklyTask object
                // Jason added a WeeklyTask constructor with no endDate parameter so this won't break on 29APR2018
                WeeklyTask newTask = new WeeklyTask(taskName,taskId,startDate,false,notes);
                taskList.add(newTask);
            } else if (customRB.isSelected()) {
                ArrayList<String> dateList = new ArrayList<>();
                // populate array list of days of week
                if (monRB.isSelected()) {
                    dateList.add("Monday");
                }
                if (tuesRB.isSelected()) {
                    dateList.add("Tuesday");
                }
                if (wedRB.isSelected()) {
                    dateList.add("Wednesday");
                }
                if (thursRB.isSelected()) {
                    dateList.add("Thursday");
                }
                if (friRB.isSelected()) {
                    dateList.add("Friday");
                }
                if (satRB.isSelected()) {
                    dateList.add("Saturday");
                }
                if (sunRB.isSelected()) {
                    dateList.add("Sunday");
                }
                if (dateList.isEmpty()) {
                    Alert alert = new Alert(AlertType.WARNING, "Please select at least one day!");
                    alert.show();
                    return false;
                }
                // create CustomTask object
                CustomTask newTask = new CustomTask(taskName,taskId,startDate,false,notes,dateList);
                taskList.add(newTask);
            } else {
                double goalValue;
                try{
                    goalValue = Double.parseDouble(numTextField.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.WARNING, "Please enter a numeric value for your goal!");
                    alert.show();
                    return false;
                }
                if (goalValue<=0) {
                    Alert alert = new Alert(AlertType.WARNING, "Please enter a positive goal!");
                    alert.show();
                    return false;
                }
                GregorianCalendar endDate = new GregorianCalendar();
                if (datePicker.getValue() == null) {
                    Alert alert = new Alert(AlertType.WARNING, "Please select an end date!");
                    alert.show();
                    return false;
                }
                endDate.setTime(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                if (startDate.compareTo(endDate)>=0) {
                    Alert alert = new Alert(AlertType.WARNING, "Please enter an end date after your start date!");
                    alert.show();
                    return false;
                }
                String goalUnits = goalUnitsField.getText();
                // create CumulativeTask object
                CumulativeTask newTask = new CumulativeTask(taskName,taskId,startDate,false,notes,endDate,goalValue,goalUnits);
                taskList.add(newTask);
            }
            // return success
            return true;
                  
        } // end createTaskObject() method
        
    } // end class HabitCreationButton

    // inner class for handling when user clicks on the "+" button to add progress
    class ProgressButton {

        // member fields - GUI elements
        private Label habitLabel;                       // label for habitComboBox
        private ComboBox habitComboBox;                 // comboBox for list of populated habits/skills of user
        private CheckBox completedCheckBox;             // check box to mark completion
        private Label completedLabel;                   // label for "Completion" TextBox
        private Label progressMadeLabel;                // label for "Progress Made" TextField
        private TextField progressMadeTextField;        // textField for "Progress Made"; user can enter progress metrics  
        private Label unitsLabel;                       // label for units
        private Button submitButton;                    // button for user to submit progress information
        private TextArea notesTextArea;                 // textArea to display notes that were entered by user
        
        // constructor
        ProgressButton(Day progressDay) {

            // creates new stage
            Stage progressStage = new Stage();
            // add skillblazer icon
            progressStage.getIcons().add(new Image("/llama.jpg"));
            // sets title for progressStage
            progressStage.setTitle("Habit Progress");
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
            // Tooltip
            habitComboBox.setTooltip(new Tooltip("Select a habit/skill"));
            for (Task mt : progressDay.tasksThisDay) {
                // add object to combo box displayed string is objects toString Method
                habitComboBox.getItems().add(mt);
            }
                  
            // adds habitLabel to progressButtonHbox1
            progressButtonHbox1.getChildren().add(habitLabel);
            // adds habitComboBox to progressButtonHbox1
            progressButtonHbox1.getChildren().add(habitComboBox);

            // hbox for 3rd vbox row
            HBox progressButtonHboxCumulative = new HBox();
            // pulls css styling information
            progressButtonHboxCumulative.getStyleClass().add("progressButtonHboxes");
            // initializes progressMadeLabel
            progressMadeLabel = new Label();
            // sets text for progressMadeLabel
            progressMadeLabel.setText("Progress Made:");
            // initializes progressMadeTextField
            progressMadeTextField = new TextField();
            // Tooltip
            progressMadeTextField.setTooltip(new Tooltip("Enter a numeric value representing progress made towards habit/skill (e.g. 10 books read)"));
            // sets max size of progressMadeTextField
            progressMadeTextField.setMaxSize(80, 80);
            // initializes progressMetrics
            unitsLabel  = new Label();
            completedCheckBox = new CheckBox();
            completedLabel = new Label("Completed");

            // adds components to progressButtonHbox3                                    
            progressButtonHboxCumulative.getChildren().add(progressMadeLabel);
            progressButtonHboxCumulative.getChildren().add(progressMadeTextField);
            progressButtonHboxCumulative.getChildren().add(unitsLabel);
            
            HBox progressButtonHboxOtherTasks = new HBox();
            progressButtonHboxOtherTasks.getStyleClass().add("progressButtonHboxes");
            progressButtonHboxOtherTasks.getChildren().add(completedLabel);
            progressButtonHboxOtherTasks.getChildren().add(completedCheckBox);
            // hbox for 4th vbox row
            HBox progressButtonHbox4 = new HBox();
            // pulls css styling information
            progressButtonHbox4.getStyleClass().add("progressButtonHboxes");
            // initializes notesTextField
            notesTextArea = new TextArea();
            notesTextArea.setPrefSize(350, 100);
            // Disable user entry
            notesTextArea.setEditable(false);
            // add component to hbox
            progressButtonHbox4.getChildren().add(notesTextArea);
            
            habitComboBox.setOnAction(e -> {
                Task mt = (Task)habitComboBox.getSelectionModel().getSelectedItem();
                // set notes text area to task notes
                notesTextArea.setText(mt.getNotes());
                if (mt instanceof CumulativeTask) {
                    unitsLabel.setText(((CumulativeTask)mt).getTaskUnits());
                    double progressCurr = ((CumulativeTask)mt).getProgress(progressDay.getTaskDate());
                    if (progressCurr >0.0) {
                        progressMadeTextField.setText(Double.toString(progressCurr));
                    } else {
                        progressMadeTextField.setText("");
                    }
                    progressButtonHboxCumulative.setVisible(true);
                    progressButtonHboxCumulative.setManaged(true);
                    progressButtonHboxOtherTasks.setVisible(false);
                    progressButtonHboxOtherTasks.setManaged(false);
                } else {
                    boolean dateCompleted = mt.checkDateCompleted(progressDay.getTaskDate());
                    completedCheckBox.setSelected(dateCompleted);
                    progressButtonHboxOtherTasks.setVisible(true);
                    progressButtonHboxOtherTasks.setManaged(true);
                    progressButtonHboxCumulative.setVisible(false);
                    progressButtonHboxCumulative.setManaged(false);
                }
            });
            if (!habitComboBox.getItems().isEmpty()) {
                habitComboBox.getSelectionModel().select(0);
                Task mt = (Task)habitComboBox.getSelectionModel().getSelectedItem();
                // set notes text area to task notes
                notesTextArea.setText(mt.getNotes());
                if (mt instanceof CumulativeTask) {
                    unitsLabel.setText(((CumulativeTask)mt).getTaskUnits());
                    double progressCurr = ((CumulativeTask)mt).getProgress(progressDay.getTaskDate());
                    if (progressCurr >0.0) {
                        progressMadeTextField.setText(Double.toString(progressCurr));
                    } else {
                        progressMadeTextField.setText("");
                    }
                    progressButtonHboxCumulative.setVisible(true);
                    progressButtonHboxCumulative.setManaged(true);
                    progressButtonHboxOtherTasks.setVisible(false);
                    progressButtonHboxOtherTasks.setManaged(false);
                } else {
                    boolean dateCompleted = mt.checkDateCompleted(progressDay.getTaskDate());
                    completedCheckBox.setSelected(dateCompleted);
                    progressButtonHboxOtherTasks.setVisible(true);
                    progressButtonHboxOtherTasks.setManaged(true);
                    progressButtonHboxCumulative.setVisible(false);
                    progressButtonHboxCumulative.setManaged(false);
                }
            } else {
                progressButtonHboxCumulative.setVisible(false);
                progressButtonHboxCumulative.setManaged(false);
                progressButtonHboxOtherTasks.setVisible(false);
                progressButtonHboxOtherTasks.setManaged(false);
            }

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
                    if (addProgress(progressDay.getTaskDate())) {
                        progressStage.close();      // closes window
                        drawCalendar();
                    }
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
            progressVbox.getChildren().add(progressButtonHboxCumulative);
            progressVbox.getChildren().add(progressButtonHboxOtherTasks);
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
        
        // method to add progress for a task
        public boolean addProgress(Calendar dateProgress) {
            if (!habitComboBox.getItems().isEmpty()) {
                Task mt = (Task)habitComboBox.getSelectionModel().getSelectedItem();
                if (mt instanceof CumulativeTask) {
                    double progressValue;
                    try{
                        progressValue = Double.parseDouble(progressMadeTextField.getText());
                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(AlertType.WARNING, "Please enter a numeric value for your progress!");
                        alert.show();
                        return false;
                    }
                    if (progressValue < 0) {
                        Alert alert = new Alert(AlertType.WARNING, "Please enter a positive value for your progress!");
                        alert.show();
                        return false;
                    } 
                    boolean previousState = ((CumulativeTask)mt).checkCompleted();
                    ((CumulativeTask)mt).addProgress(dateProgress, progressValue);
                    boolean newState = ((CumulativeTask)mt).checkCompleted();
                    if (newState&&(!previousState)) {
                        Alert alert = new Alert(AlertType.INFORMATION, "Great job! You have completed your goal!");
                        alert.show();
                    } else if (progressValue > 0) {
                        Alert alert = new Alert(AlertType.INFORMATION, "Great job! You have made progress on your goal!");
                        alert.show();
                    }
                } else {
                    if (completedCheckBox.isSelected()) {
                        mt.setDateCompleted(dateProgress);
                        Alert alert = new Alert(AlertType.INFORMATION, "Great job! You completed your task!");
                        alert.show();
                    } else {
                        mt.removeDateCompleted(dateProgress);
                    }                               
                }
            }
            return true;
        }
    } // end addProgress() method
    
    // method to close the program
    private void closeProgram() {
        // call to JSON method to save information to file
        JSONWriter.saveAllFilesToDisk(skbUserProfile, taskList);
        // ensures windows are closed
        habitCreationMenu.closeHabitEntry();
        lifetimeMetrics.closeLifetimeMetrics();                                        
        optionsMenu.closeOptions();
        window.close();
    } // end closeProgram() method

    // main method
    public static void main(String[] args) {
        /*
         * These two objects need to be invoked in main so the rest of the
         * application can access the UserProfile and the list of Task objects
         * that were loaded from disk.
         */
        // instantiates taskList
        taskList = new ArrayList<Task>();
        taskList = jsonLoader.loadTasksFromJSON();
        skbUserProfile = jsonLoader.parseAndReturnUserProfile();
        launch(args);               // opens the JavaFX Stage
    } // end main method

} // end class Skillblazer
