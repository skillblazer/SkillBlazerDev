/************************************************************
 * Application Name: skillblazer
 * File Name: CumulativeTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/9/2018
 * 
 * Description:
 * 
 * This child class is one of the kind of tasks that represent 
 * the different types of goals that define these 
 * habits/skills.
 *
 * A custom task is one in which the user defines how often 
 * to complete, such as reading 3 times a week (e.g. Monday, 
 * Tuesday, and Wednesday).
 *
 * The CustomTask class contains a boolean array that holds 
 * which days of the week the user selects to complete a task 
 * (e.g. every Monday, Wednesday, and Friday). There are 
 * methods to set and get these days of the week.
 ***********************************************************/

package llamasoft.skillblazer;

import java.util.Calendar;

public class CustomTask extends Task {

    private boolean[] daysOfWeek; //WILL CHANGE THIS TO STRING
    private int currentStreak; //current streak of completions
    private int bestStreak; //best streak of completions
    
    /*
     * 
     */
    public CustomTask() {
        super();
    }

    /*
     * 
     */
    public CustomTask(String taskName) {
        super(taskName);
    }

    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
    }

    /*
     * 
     */
    public void setDaysOfWeek() {
    	
    }

    /*
     * 
     */
    public boolean[] getDaysOfWeek() {
        return this.daysOfWeek;
    }

    /*
     * 
     */
    public int getCurrentStreak() {
        return this.currentStreak;
    }

    /*
     * 
     */
    public int getBestStreak() {
        return this.bestStreak;
    }

}
