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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CumulativeTask extends Task {

    private Calendar endDate; //end date for cumulative task
    private String goalUnits;
    private double goalToReach;
    private ArrayList<CumulativeHistoryStruct> cumulativeHistory;
    /*
     * Default Class Constructor - calls parent constructor
     */
    public CumulativeTask() {
        super();
        this.type = "cumulative";
    } //end CumulativeTask constructor

    @Override
    public String infoString() {
        return super.infoString() + "EndDate is: " + endDate;
    }

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
        this.cumulativeHistory = new ArrayList(); 
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
        this.cumulativeHistory = new ArrayList(); 
    } //end CumulativeTask constructor

    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, Calendar endDate) {
        super(taskName, taskId, startDate, isCompleted, "cumulative","");
        this.endDate = endDate;
        this.goalToReach = 0.0;
        this.cumulativeHistory = new ArrayList(); 
    } //end CumulativeTask constructor
    /*
     * Fully qualified constructor
     */
    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, Calendar endDate, double goalToReach, String goalUnits) {
        super(taskName, taskId, startDate, isCompleted, "cumulative", notes);
        this.endDate = endDate;
        this.goalToReach = goalToReach;
        this.goalUnits = goalUnits;
        this.cumulativeHistory = new ArrayList(); 
    } //end CumulativeTask constructor
    
    
    public String getTaskUnits () {
        return goalUnits;
    }
    
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

        // method to add to the ArrayList CumulativeHistory
    public void addProgress(Calendar dateCompleted, double progress) {
        CumulativeHistoryStruct newEntry = new CumulativeHistoryStruct(dateCompleted,progress);
        if (cumulativeHistory.contains(newEntry)) {
            int oldIndex = cumulativeHistory.indexOf(newEntry);
            cumulativeHistory.get(oldIndex).progress = progress;
        } else {
            cumulativeHistory.add(new CumulativeHistoryStruct(dateCompleted,progress));
        }
    } // end addProgress() method
    
            // method to add to the ArrayList CumulativeHistory
    public double getProgress(Calendar dateProgress) {
        CumulativeHistoryStruct checkEntry = new CumulativeHistoryStruct(dateProgress,0.0);
        if (cumulativeHistory.contains(checkEntry)) {
            int checkIndex = cumulativeHistory.indexOf(checkEntry);
            return cumulativeHistory.get(checkIndex).progress;
        } else {
            return 0.0;
        }
    } // end addProgress() method
    
    /*
     * This method will return the percentage of the user's goal
     * that has been completed (e.g. 50% of books read for
     * overall goal). 
     */
    public String checkStatus(double numCompleted) {
    	
    	double tempCompleted = numCompleted; //number completed toward goal
        double tempGoal = goalToReach;
    	double percentageDone = 0.0; //percentage completed
    	
    	//if number completed > 0, then calculate percentage
    	if (tempCompleted > 0) {
    		percentageDone = tempCompleted / tempGoal;
    		percentageDone = percentageDone * 100;
    	}
    	
    	DecimalFormat dFormat = new DecimalFormat(".00");
        return dFormat.format(percentageDone);
    } //end checkStatus method


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
        // endDate fields
        jsonObject.put("endYear", endYear);
        jsonObject.put("endMonth", endMonth);
        jsonObject.put("endDate", endDate);
        // startDate fields
        jsonObject.put("year", year);
        jsonObject.put("month", month);
        jsonObject.put("date", date);

        jsonObject.put("isCompleted", this.getIsCompleted());
        jsonObject.put("taskName", this.getTaskName());

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);

    }
    
    class CumulativeHistoryStruct {
        public Calendar date;
        public double progress;
        
        CumulativeHistoryStruct() {
            date = null;
            progress = 0.0;
        }
        CumulativeHistoryStruct(Calendar date,double progress) {
            this.date = date;
            this.progress = progress;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CumulativeHistoryStruct) {
                return date.equals(((CumulativeHistoryStruct)o).date);
            } else {
                return false;
            }
        }
    }

}//end CumulativeTask class
