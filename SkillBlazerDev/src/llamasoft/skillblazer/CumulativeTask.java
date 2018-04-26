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

    private Calendar endDate;                                           // calendar object representing end date for cumulative task
    private String goalUnits;                                           // string field for goal units (e.g. miles, hours, books, etc.) to be entered by user
    private double goalToReach;                                         // double field to represent numeric amount for goal to reach (e.g. 5 miles); entered by user
    private ArrayList<CumulativeHistoryStruct> cumulativeHistory;       // arraylist of CumulativeHistoryStruct inner class type - custom data structure
    
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
    
      /*
     * Old Fully qualified constructor
     */
    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, Calendar endDate) {
        super(taskName, taskId, startDate, isCompleted, "cumulative","");
        this.endDate = endDate;
        this.goalToReach = 0.0;
        this.cumulativeHistory = new ArrayList(); 
    } //end CumulativeTask constructor
    
    /*
     * New Fully qualified constructor
     */
    public CumulativeTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, Calendar endDate, double goalToReach, String goalUnits) {
        super(taskName, taskId, startDate, isCompleted, "cumulative", notes);
        this.endDate = endDate;
        this.goalToReach = goalToReach;
        this.goalUnits = goalUnits;
        this.cumulativeHistory = new ArrayList(); 
    } //end CumulativeTask constructor
    
       @Override
    public String infoString() {
        return super.infoString() + "EndDate is: " + endDate;
    }

    
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
    
    
    public boolean checkCompleted() {
        if (getTotalProgress()>=goalToReach) {
            return true;
        } else {
            return false;
        }  
    }
    
    public double getTotalProgress() {
        double progressSum = 0.0;
        for (CumulativeHistoryStruct mh : cumulativeHistory) {
            progressSum += mh.progress;
        }
        return progressSum;
    }
    
    public double getGoalToReach() {
        return this.goalToReach;
    }


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
    
    // inner class; custom data structure
    class CumulativeHistoryStruct {
        public Calendar date;               // calendar object holding the date
        public double progress;             // double field holding progress made toward goal
        
        // constructor
        CumulativeHistoryStruct() {
            date = null;
            progress = 0.0;
        }
        
        // constructor
        CumulativeHistoryStruct(Calendar date,double progress) {
            this.date = date;
            this.progress = progress;
        }
        
        // Overriding equals() method to compare dates
        @Override
        public boolean equals(Object o) {
            if (o instanceof CumulativeHistoryStruct) {
                return date.equals(((CumulativeHistoryStruct)o).date);
            } else {
                return false;
            }
        } // end equals() method
    }

}//end CumulativeTask class
