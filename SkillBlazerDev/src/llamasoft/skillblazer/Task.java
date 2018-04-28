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
    protected Calendar startDate;                                       // calendar object holding start date
    protected boolean isCompleted;                                      // boolean holding whether task is completed
    protected String type;                                              // string holidng task type
    protected ArrayList<Calendar> datesCompleted = new ArrayList<>();     // arrayList to hold the dates a particular habit/task has been completed
    protected Calendar endDate;                                           // calendar object representing end date for cumulative task
    protected String notes;                                             // string field to hold contents in notes text area
    
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
    public Task(String taskName, long taskId, Calendar startDate, boolean isCompleted, String type, String notes) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.startDate = startDate;
        this.isCompleted = isCompleted;
        this.type = type;
        this.endDate = null;
        this.notes = notes;
    } // end constructor

    // fully-qualified constructor with ArrayList<Calendar> datesCompleted
    public Task(String taskName, long taskId, Calendar startDate, boolean isCompleted, String type, String notes, ArrayList<Calendar> datesCompleted) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.startDate = startDate;
        this.isCompleted = isCompleted;
        this.type = type;
        this.endDate = null;
        this.notes = notes;
        this.datesCompleted = datesCompleted;
    } // end constructor

    // method to get task ID
    long getTaskId() {
        return this.taskId;
    } // end getTaskId() method
    
    // toString printing only name so object can be placed in label
    public String toString()
    {
        return taskName;
    }
    // printString() method
    
    // replaces old toString() method
    public String infoString() {
        return "Taskname is: " + taskName + "TaskID is: " + taskId
                + "StartDate is: " + startDate + "isCompleted is: "
                + isCompleted + "Type is: " + type;
    } // end infoString() method

    // method to get start date
    Calendar getStartDate() {
        return startDate;
    } // end getStartDate() method

    // method to get task name
    public String getTaskName() {
        return this.taskName;
    } // end getTaskName() method
    
        // method to set task name
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    } // end setTaskName() method
    
    
    // method to return notes
    public String getNotes() {
        return this.notes;
    } // end getNotes() method
    
// method to set notes
    public void setNotes(String notes) {
        this.notes = notes;
    } // end setNotes() method
    
    
    // method to end a task
    public void endTask(Calendar endDate) {
        this.isCompleted = true;
        this.endDate = endDate;
    } // end endTask() method

    // method to return whether task is completed
    public boolean getIsCompleted() {
        return this.isCompleted;
    } // end getIsCompleted() method
    
     /*
     * Accessor method - endDate
     */
    public Calendar getEndDate() {
        return this.endDate;
    } // end getEndDate() method

        
    /*
     * Mutator method - endDate
     */
    public void setEndDate(Calendar endDate) {
    	this.endDate = endDate;
    } //end setEndDate method
    
    
    
    // method to get the total amount completed
    public int getNumCompleted() {
        return datesCompleted.size();
    }
    // method to delete a record
    protected void deleteRecord() {

    } // end deleteRecord() method

    public void writeTaskToJSON() {
        // placeholder so the individual task subclasses can have their
        // writeTaskToJSON() methods referenced by external classes
    } // end writeTaskToJSON() method

    // method to add to the ArrayList datesCompleted
    public void setDateCompleted(Calendar dateCompleted) {
        if (!datesCompleted.contains(dateCompleted)) {
            datesCompleted.add(dateCompleted);
        }
    } // end setDateCompleted() method
    
    // method to remove from the ArrayList datesCompleted
    public void removeDateCompleted(Calendar dateRemove) {
        if (datesCompleted.contains(dateRemove)) {
            datesCompleted.remove(dateRemove);
        }
    } // end setDateCompleted() method
    
    
    // method to check if a date was completed
    public boolean checkDateCompleted(Calendar dateCheck) {
        return datesCompleted.contains(dateCheck);
    } // end checkDateCompleted() method
    
    
    // method get the ArrayList datesCompleted
    public ArrayList<Calendar> getDatesCompleted() {
        return datesCompleted;
    } // end getDatesCompleted() method

} // end class Task
