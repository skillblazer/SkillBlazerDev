/************************************************************
 * Application Name: skillblazer
 * File Name: CumulativeTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/10/2018
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

import java.text.DecimalFormat;
import java.util.Calendar;

public class CumulativeTask extends Task {

    Calendar endDate; //end date for cumulative task

    /*
     * Default Class Constructor - calls parent constructor
     */
    public CumulativeTask() {
        super();
    } //end CumulativeTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     */
    public CumulativeTask(String taskName) {
        super(taskName);
        this.taskName = taskName;
    } //end CumulativeTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     * and startDate
     */
    public CumulativeTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
    } //end CumulativeTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName, 
     * startDate, and instantiates endDate
     */
    public CumulativeTask(String taskName, Calendar startDate, Calendar endDate) {
        super(taskName, startDate);
        this.endDate = endDate;
    } //end CumulativeTask constructor

    /*
     * Fully qualified constructor
     */
    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String type, Calendar endDate) {
        super(taskName, taskId, startDate, isCompleted, type);
        this.endDate = endDate;
    } //end CumulativeTask constructor
    /*
     * Mutator method - endDate
     */
    public void setEndDate(Calendar endDate) {
    	this.endDate = endDate;
    } //end setEndDate method

    /*
     * Accessor method - endDate
     */
    public Calendar getEndDate() {
        return this.endDate;
    } //end getEndDate method

    /*
     * This method will return the percentage of the user's goal
     * that has been completed (e.g. 50% of books read for
     * overall goal). 
     */
    public String checkStatus(double numCompleted, double goalToReach) {
    	
    	double tempCompleted = numCompleted; //number completed toward goal
    	double tempGoal = goalToReach; //goal to reach for task
    	double percentageDone = 0.0; //percentage completed
    	
    	//if number completed > 0, then calculate percentage
    	if (tempCompleted > 0) {
    		percentageDone = tempCompleted / tempGoal;
    		percentageDone = percentageDone * 100;
    	}
    	
    	DecimalFormat dFormat = new DecimalFormat(".00");
        return dFormat.format(percentageDone);
    } //end checkStatus method
}//end CumulativeTask class
