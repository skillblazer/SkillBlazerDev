package llamasoft.skillblazer;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Day {

    // member fields
    private boolean hasTranspired = false;                  // boolean to represent if day has transpired
										  
    private Calendar taskDate = new GregorianCalendar();    // calendar object representing date of task

    VBox currentDayView;                                    // vbox

    ArrayList<Task> tasksThisDay = new ArrayList<>();       // arraylist containing tasks for a given day
    
    // Constructor that will accept a date from the calling method
    // This is important for creating FUTURE dates that will go
    // into a monthly calendar
    public Day(Calendar taskDate) {
        this.taskDate = taskDate;
    } // end constructor

    // Constructor that will create its own date upon creation
    // not sure if a use case exists for 'today's date'
    // since we should be looking at a month at a time
    public Day() {
        this.taskDate = new GregorianCalendar();
    } // end constructor
    
    // method returning whether a day has transpired
    public boolean getHasTranspired() {
        return hasTranspired;
    } // end getHasTranspired() method
    
    // method to get task date
    public Calendar getTaskDate() {
        return taskDate;
    } // end getTaskDate() method

    // use this at the end of a calendar day
    protected void setHasTranspired() {
        hasTranspired = true;  // can only do this once!
    } // end setHasTranspired() method
    
    void createDay() {
        // instantiate a Day object, populate the Day object with tasks
    }
    
    // method to add a Task to the ArrayList tasksThisDay
    public void addTask(Task newTask) {
        tasksThisDay.add(newTask);
    } // end addTask() method
    
} // end class Day
 
