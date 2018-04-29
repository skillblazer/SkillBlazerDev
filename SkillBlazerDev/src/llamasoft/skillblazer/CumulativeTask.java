/************************************************************
 * Application Name: skillblazer
 * File Name: CumulativeTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
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

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class CumulativeTask extends Task {

    private String goalUnits;                                           // string field for goal units (e.g. miles, hours, books, etc.) to be entered by user
    private double goalToReach;                                         // double field to represent numeric amount for goal to reach (e.g. 5 miles); entered by user
    private ArrayList<CumulativeHistoryStruct> cumulativeHistory;       // ArrayList of CumulativeHistoryStruct inner class type - custom data structure
    
    /*
     * Default Class Constructor - calls parent constructor
     */
    public CumulativeTask() {
        super();
        this.type = "cumulative";
    } //end CumulativeTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     */
    public CumulativeTask(String taskName) {
        super(taskName);
        this.taskName = taskName;
        this.type = "cumulative";
    } //end CumulativeTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     * and startDate
     */
    public CumulativeTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
        this.type = "cumulative";
        this.goalToReach = 0.0;
        this.cumulativeHistory = new ArrayList<>();
    } //end CumulativeTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName, 
     * startDate, and instantiates endDate
     */
    public CumulativeTask(String taskName, Calendar startDate, Calendar endDate) {
        super(taskName, startDate);
        this.endDate = endDate;
        this.type = "cumulative";
        this.goalToReach = 0.0;
        this.cumulativeHistory = new ArrayList<>();
    } //end CumulativeTask constructor
    
    /*
     * New Fully qualified constructor
     */
    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, Calendar endDate, double goalToReach, String goalUnits) {
        super(taskName, taskId, startDate, isCompleted, "cumulative", notes);
        this.endDate = endDate;
        this.goalToReach = goalToReach;
        this.goalUnits = goalUnits;
        this.cumulativeHistory = new ArrayList<>();
    } //end CumulativeTask constructor


    /*
     * Fully Qualified constructor which includes the ArrayList<CumulativeHistoryStruct> (inner class member) cumulativeHistory as a parameter
     * This is useful for instantiation of CumulativeTask objects that have been stored in JSON files
     */
    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, Calendar endDate, double goalToReach, String goalUnits, ArrayList<CumulativeHistoryStruct> cumulativeHistory) {
        super(taskName, taskId, startDate, isCompleted, "cumulative", notes);
        this.endDate = endDate;
        this.goalToReach = goalToReach;
        this.goalUnits = goalUnits;
        this.cumulativeHistory = cumulativeHistory;
    } //end CumulativeTask constructor
    
       @Override
    public String infoString() {
        return super.infoString() + "EndDate is: " + endDate;
    }

    
    public String getTaskUnits () {
        return goalUnits;
    }
    
    public void setTaskUnits (String goalUnits) {
        this.goalUnits = goalUnits;
    }
    

        // method to add to the ArrayList cumulativeHistory
    public void addProgress(Calendar dateCompleted, double progress) {
        CumulativeHistoryStruct newEntry = new CumulativeHistoryStruct(dateCompleted,progress);
        if (cumulativeHistory.contains(newEntry)) {
            int oldIndex = cumulativeHistory.indexOf(newEntry);
            cumulativeHistory.get(oldIndex).progress = progress;
        } else {
            cumulativeHistory.add(new CumulativeHistoryStruct(dateCompleted,progress));
        }
    } // end addProgress() method
    
    // method to get progress from the ArrayList cumulativeHistory
    public double getProgress(Calendar dateProgress) {
        CumulativeHistoryStruct checkEntry = new CumulativeHistoryStruct(dateProgress,0.0);
        if (cumulativeHistory.contains(checkEntry)) {
            int checkIndex = cumulativeHistory.indexOf(checkEntry);
            return cumulativeHistory.get(checkIndex).progress;
        } else {
            return 0.0;
        }
    } // end getProgress() method
    
    // method that checks whether a task has been completed (compares total progress with goal to reach)
    public boolean checkCompleted() {
        if (getTotalProgress()>=goalToReach) {
            return true;
        } else {
            return false;
        }  
    } // end checkCompleted() method
    
    //  method that returns the total progress a user has made towards a goal
    public double getTotalProgress() {
        double progressSum = 0.0;
        for (CumulativeHistoryStruct mh : cumulativeHistory) {
            progressSum += mh.progress;
        }
        return progressSum;
    } // end getTotalProgress() method
    
    // method to get a goal to reach defined by user
    public double getGoalToReach() {
        return this.goalToReach;
    } // end getGoalToReach() method
    
    // method to set a goal to reach defined by user
    public void setGoalToReach (double goalToReach) {
        this.goalToReach = goalToReach;
    }// end setGoalToReach() method
    
    
    // method to write a task to JSON
    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skblv";
        String fileName = filePrefix + taskSuffixNumber + ".json";

        Calendar taskEndDate = this.getEndDate();
        int endYear = taskEndDate.get(Calendar.YEAR);
        int endMonth = taskEndDate.get(Calendar.MONTH);
        int endDate = taskEndDate.get(Calendar.DATE);

        Calendar startDate = this.getStartDate();
        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH);
        int date = startDate.get(Calendar.DATE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("taskId", this.getTaskId());
        // endDate fields - more simplistic than its Weekly/Daily/Custom siblings
        jsonObject.put("endYear", endYear);
        jsonObject.put("endMonth", endMonth);
        jsonObject.put("endDate", endDate);
        // startDate fields
        jsonObject.put("year", year);
        jsonObject.put("month", month);
        jsonObject.put("date", date);
        // remaining fields
        jsonObject.put("isCompleted", this.getIsCompleted());
        jsonObject.put("taskName", this.getTaskName());
        jsonObject.put("notes", this.notes);
        jsonObject.put("goalToReach", this.goalToReach);
        jsonObject.put("goalUnits", this.goalUnits);

        // pass the jsonObject and the inner class object ArrayList<CumulativeHistoryStruct> cumulativeHistory to
        // a method in JSONWriter which will prep the Calendar objects and corresponding progress values
        // to be added to the JSON file
        JSONWriter.prepareCumulativeHistoryStructForJSONStorage(jsonObject, cumulativeHistory);

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);
    } // end writeTaskToJSON() method

}//end CumulativeTask class
