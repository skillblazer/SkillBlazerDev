/************************************************************
 * Application Name: skillblazer
 * File Name: CustomTask.java
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
 * A custom task is one in which the user defines how often 
 * to complete, such as reading 3 times a week (e.g. Monday, 
 * Tuesday, and Wednesday).
 *
 * The CustomTask class contains a String array that holds 
 * which days of the week the user selects to complete a task 
 * (e.g. every Monday, Wednesday, and Friday). There are 
 * methods to set and get these days of the week.
 ***********************************************************/

package llamasoft.skillblazer;

import java.util.Calendar;

public class CustomTask extends Task {

    private String[] daysOfWeek; //days of the week the user has selected
    private int currentStreak; //current streak of completions
    private int bestStreak; //best streak of completions
    
    /*
     * 
     */
    public CustomTask() {
        super();
    } //end CustomTask constructor

    /*
     * 
     */
    public CustomTask(String taskName) {
        super(taskName);
    } //end CustomTask constructor

    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
    } //end CustomTask constructor
    
    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate, String[] arrayOfDays) {
        super(taskName, startDate);
        daysOfWeek = arrayOfDays.clone();
    } //end CustomTask constructor
    
    /*
     * 
     */
    public CustomTask(String taskName, Calendar startDate, String[] arrayOfDays, int currentStreak,
    		int bestStreak) {
        super(taskName, startDate);
        daysOfWeek = arrayOfDays.clone();
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
    } //end CustomTask constructor

    /*
     * 
     */
    public String[] getDaysOfWeek() {
        return this.daysOfWeek;
    } //end getDaysOfWeek method
    
    /*
     * 
     */
    public void setDaysOfWeek(String[] arrayOfDays) {
    	daysOfWeek = arrayOfDays.clone();
    } //end setDaysOfWeek method

    /*
     * 
     */
    public int getCurrentStreak() {
        return this.currentStreak;
    } //end getCurrentStreak method
    
    /*
     * 
     */
    public void setCurrentStreak(int currentStreak) {
    	this.currentStreak = currentStreak;
    } //end setCurrentStreak method

    /*
     * 
     */
    public int getBestStreak() {
        return this.bestStreak;
    } //end getBestStreak method
    
    /*
     * 
     */
    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    } //end getBestStreak method
}
