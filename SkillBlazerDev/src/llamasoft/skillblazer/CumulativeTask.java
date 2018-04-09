/************************************************************
 * Application Name: skillblazer
 * File Name: CumulativeTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/8/2018
 * 
 * Description:
 * 
 * This child class is one of the kind of tasks that represent 
 * the different types of goals that define these 
 * habits/skills.
 * 
 * A cumulative task is one in which the user sets a deadline 
 * for completing, such as reading 12 books by July 2018.
 *
 * The CumulativeTask class has a field that holds the end 
 * date for a goal asset by the user. There is a method to 
 * set this date, a method to get this date, and a method to 
 * check the percentage that a goal has been reached by
 * the user (e.g. 50% of books read of overall goal).
 ***********************************************************/

package llamasoft.skillblazer;

import java.util.Calendar;

public class CumulativeTask extends Task {

    Calendar endDate; //

    /*
     * 
     */
    public CumulativeTask() {
        super();
    }

    /*
     * 
     */
    public CumulativeTask(String taskName) {
        super(taskName);
        this.taskName = taskName;
    }

    /*
     * 
     */
    public CumulativeTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
    }

    /*
     * 
     */
    public CumulativeTask(String taskName, Calendar startDate, Calendar endDate) {
        super(taskName, startDate);
        this.endDate = endDate;
    }
    
    /*
     * 
     */
    private void setEndDate(Calendar endDate) {
    	this.endDate = endDate;
    }

    /*
     * 
     */
    private Calendar getEndDate() {
        return endDate;
    }

    /*
     * 
     */
    //method to return the percentage the user's goal has been completed
    // (e.g. 50% of books read of overall goal)
    private double checkStatus() {
        int fakeNumber = 20;
        return fakeNumber * 0.10;  // make it a percentage
    }

}
