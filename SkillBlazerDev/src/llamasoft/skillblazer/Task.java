/** **********************************************************
 * Application Name: skillblazer
 * File Name: Task.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
 *
 * Description:
 *
 * The Task class contains the methods and fields that will
 * apply to all of the types of tasks. Included in the Task class
 * will be methods to initialize the task, edit the task, delete
 * the task, get task ID, get start date, and get task name.
 ********************************************************** */
// package
package llamasoft.skillblazer;

// imports
import java.util.ArrayList;
import java.util.Calendar;

public class Task {

    // member fields
    protected String taskName;                                          // string holding task name
    protected long taskId;                                              // long holding task ID
    protected Calendar startDate;                                       // Calendar object holding start date
    protected boolean isCompleted;                                      // boolean holding whether task is completed
    protected String type;                                              // string holidng task type
    protected ArrayList<Calendar> datesCompleted = new ArrayList();     // arrayList to hold the dates a particular habit/task has been completed

    // default, no-argument constructor
    public Task() {
    } // end constructor

    // constructor taking taskName as input parameter
    public Task(String taskName) {
        this.taskName = taskName;
    } // end constructor

    // constructor taking taskName and startDate as input parameter
    public Task(String taskName, Calendar startDate) {
        this.taskName = taskName;
        this.startDate = startDate;
    } // end constructor

    // fully-qualified constructor
    public Task(String taskName, long taskId, Calendar startDate, boolean isCompleted, String type) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.startDate = startDate;
        this.isCompleted = isCompleted;
        this.type = type;
    } // end constructor

    // method to get task ID
    long getTaskId() {
        return this.taskId;
    } // end getTaskId() method

    // toString() method
    @Override
    public String toString() {
        return "Taskname is: " + taskName + "TaskID is: " + taskId
                + "StartDate is: " + startDate + "isCompleted is: "
                + isCompleted + "Type is: " + type;
    } // end toString() method

    // method to get start date
    Calendar getStartDate() {
        return startDate;
    } // end getStartDate() method

    // method to get task name
    public String getTaskName() {
        return this.taskName;
    } // end getTaskName() method

    // method to initialize task after user input
    public void startTask() {

    } // end startTask() method

    // method to return whether task is completed
    public boolean getIsCompleted() {
        return this.isCompleted;
    } // end getIsCompleted() method

    // method to set a task as completed
    protected void setCompleted() {
        this.isCompleted = true;
    } // end setCompleted() method

    // method to make changes to an existing task
    public void editTask() {

    } // end editTask() method

    // method to delete the task specified by the caller
    protected void deleteTask(Task task) {

    } // end deleteTask() method

    // method to delete a record
    protected void deleteRecord() {

    } // end deleteRecord() method

    public void writeTaskToJSON() {
        // placeholder so the individual task subclasses can have their
        // writeTaskToJSON() methods referenced by external classes
    } // end writeTaskToJSON() method

    // method to add to the ArrayList datesCompleted
    public void setDateCompleted(Calendar dateCompleted) {
        datesCompleted.add(dateCompleted);
    } // end setDateCompleted() method

    // method get the ArrayList datesCompleted
    public ArrayList<Calendar> getDatesCompleted() {
        return datesCompleted;
    } // end getDatesCompleted() method

} // end class Task
