/** **********************************************************
 * Application Name: skillblazer
 * File Name: CustomTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
<<<<<<< HEAD
 * Date: 4/16/2018
 * 
=======
 * Date: 4/10/2018
 *
>>>>>>> branch 'PhaseII' of https://j3dispartan@github.com/skillblazer/SkillBlazerDev.git
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
 ********************************************************** */
package llamasoft.skillblazer;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomTask extends Task {
	
    private ArrayList<String> actualDaysInTask = new ArrayList<>(); //days picked by user
    private int currentStreak; //current streak of completions
    private int bestStreak; //best streak of completions

    /*
     * Default Class Constructor - calls parent constructor
     */
    public CustomTask() {
        super();
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     */
    public CustomTask(String taskName) {
        super(taskName);
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     * and startDate
     */
    public CustomTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     * and startDate. Initializes arrayOfDays class variable.
     */
    public CustomTask(String taskName, Calendar startDate, ArrayList<String> arrayOfDays) {
        super(taskName, startDate);
        this.actualDaysInTask.addAll(arrayOfDays);
        this.type = "custom";
    } //end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName and 
     * startDate. Initializes all subclass variables.
     */
    public CustomTask(String taskName, Calendar startDate, ArrayList<String> arrayOfDays, int currentStreak,
            int bestStreak) {
        super(taskName, startDate);
        this.actualDaysInTask.addAll(arrayOfDays);
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
        this.type = "custom";
    } //end CustomTask constructor

    /*
     *  Fully qualified constructor (needed for initializing objects stored on disk
     */
    public CustomTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, int currentStreak,
            int bestStreak, ArrayList<String> daysInTask) {
        super(taskName, taskId, startDate, isCompleted, "custom");
        this.actualDaysInTask.addAll(daysInTask);
        this.currentStreak = currentStreak;
        this.bestStreak = bestStreak;
    } //end CustomTask constructor

    /*
     * Accessor Method - actualDaysInTask
     */
    public ArrayList<String> getDaysOfWeek() {
        return this.actualDaysInTask;
    } //end getDaysOfWeek method

    /*
     * Mutator Method - actualDaysInTask
     */
    public void setDaysOfWeek(ArrayList<String> listOfDays) {
        this.actualDaysInTask.addAll(listOfDays);
    } //end setDaysOfWeek method

    /*
     * Accessor Method - currentStreak
     */
    public int getCurrentStreak() {
        return this.currentStreak;
    } //end getCurrentStreak method

    /*
     * Mutator Method - currentStreak
     */
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    } //end setCurrentStreak method

    /*
     * Accessor Method - bestStreak
     */
    public int getBestStreak() {
        return this.bestStreak;
    } //end getBestStreak method

    /*
     * Mutator Method - bestStreak
     */
    public void setBestStreak(int bestStreak) {
        this.bestStreak = bestStreak;
    } //end getBestStreak method
} //end of CustomTask class
