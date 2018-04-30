/** **********************************************************
 * Application Name: skillblazer
 * File Name: Day.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
 *
 * Description:
 *
 * The Day class contains the methods and fields that will
 * apply to a given day in the calendar interface. It contains
 * a Calendar object holding the Task date (taskDate), an ArrayList 
 * of type Task holding the tasks for a given day (tasksThisDay), a 
 * method to get the task date and a method to add to the ArrayList
 * tasksThisDay.
 ********************************************************** */
// package
package llamasoft.skillblazer;

// imports
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Day {

    // member fields										  
    private Calendar taskDate = new GregorianCalendar();    // calendar object representing date of task
    ArrayList<Task> tasksThisDay = new ArrayList<>();       // arraylist containing tasks for a given day
    
    /* Constructor that will accept a date from the calling method
    * This is important for creating FUTURE dates that will go
    * into a monthly calendar
    */
    public Day(Calendar taskDate) {
        this.taskDate = taskDate;
    } // end constructor

    /* Constructor that will create its own date upon creation
    * not sure if a use case exists for 'today's date'
    * since we should be looking at a month at a time
    */
    public Day() {
        this.taskDate = new GregorianCalendar();
    } // end constructor
    
    // method to get task date
    public Calendar getTaskDate() {
        return taskDate;
    } // end getTaskDate() method
    
    // method to add a Task to the ArrayList tasksThisDay
    public void addTask(Task newTask) {
        tasksThisDay.add(newTask);
    } // end addTask() method
    
} // end class Day
 
