package llamasoft.skillblazer;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import org.json.simple.JSONObject;

import java.util.Calendar;
import java.util.Collections;

/**
 * The child classes listed above represent the different types of goals that
 * define these habits/skills.
 *
 * A daily task is one in which the user has the goal of completing every day,
 * such as exercising for 1 hour.
 *
 * The DailyTask class contains fields and methods associated with the
 * completion of a task on a daily basis. It includes fields for current streak
 * and best streak and methods to set and get these values. It also contains a
 * method to set a task as being completed for a given day and a method to get
 * that value.
 */
public class DailyTask extends Task {

    
    public DailyTask() {
        super();
        this.type = "daily";
    }

    public DailyTask(String taskName) {
        super(taskName);
        this.type = "daily";
    }
   
     /*
     *  New Fully qualified constructor (needed for initializing objects stored on disk
     */
    public DailyTask(String taskName, long taskId, Calendar startDate,
                     boolean isCompleted, String notes) {
        super(taskName, taskId, startDate, isCompleted, "daily", notes);

    } //end DailyTask constructor
    
    // method to get the user's current streak; utilizes Collections.sort
    public int getCurrentStreak(Calendar todaysDate) {
        int i;
        int currentStreak=0;
        if (datesCompleted.size() > 0) {
            currentStreak = 1;
        } else {
            return 0;
        }
        Collections.sort(datesCompleted);
        for (i=1;i<datesCompleted.size();i++) {
            LocalDate compare1 = LocalDate.of(datesCompleted.get(i).get(Calendar.YEAR),datesCompleted.get(i).get(Calendar.MONTH)+1,datesCompleted.get(i).get(Calendar.DATE));
            LocalDate compare2 = LocalDate.of(datesCompleted.get(i-1).get(Calendar.YEAR),datesCompleted.get(i-1).get(Calendar.MONTH)+1,datesCompleted.get(i-1).get(Calendar.DATE));
            long numDaysBetween = DAYS.between(compare2,compare1);
            if (numDaysBetween==1) {
                currentStreak += 1;
            } else {
                currentStreak = 1;
            }  
            // stop once reaching todays date
            if (todaysDate.compareTo(datesCompleted.get(i)) == 0) {
                break;
            } else if (todaysDate.compareTo(datesCompleted.get(i)) < 0) {
                // passed date without hitting date -- no streak
                currentStreak = 0;
            }
        }
        if (i>(datesCompleted.size()-1)) {
            i=datesCompleted.size()-1;
        }  
        // Catch for current streak not making it to today or yesterday
        LocalDate compare1 = LocalDate.of(todaysDate.get(Calendar.YEAR),todaysDate.get(Calendar.MONTH)+1,todaysDate.get(Calendar.DATE));
        LocalDate compare2 = LocalDate.of(datesCompleted.get(i).get(Calendar.YEAR),datesCompleted.get(i).get(Calendar.MONTH)+1,datesCompleted.get(i).get(Calendar.DATE));
        long numDaysBetween = DAYS.between(compare2,compare1);
        if (numDaysBetween > 1) {
            currentStreak = 0;
        }       
        return currentStreak;
    } // end getCurrentStreak() method

    // method to get the user's best streak; utilizes Collections.sort
    public int getBestStreak() {
        Collections.sort(datesCompleted);
        int bestStreak = 0;
        int i;
        int currentStreak=0;
        if (datesCompleted.size() > 0) {
            currentStreak = 1;
            bestStreak = 1;
        } else {
            return bestStreak;
        }
        Collections.sort(datesCompleted);
        for (i=1;i<datesCompleted.size();i++) {
            LocalDate compare1 = LocalDate.of(datesCompleted.get(i).get(Calendar.YEAR),datesCompleted.get(i).get(Calendar.MONTH)+1,datesCompleted.get(i).get(Calendar.DATE));
            LocalDate compare2 = LocalDate.of(datesCompleted.get(i-1).get(Calendar.YEAR),datesCompleted.get(i-1).get(Calendar.MONTH)+1,datesCompleted.get(i-1).get(Calendar.DATE));
            long numDaysBetween = DAYS.between(compare2,compare1);
            if (numDaysBetween==1) {
                currentStreak += 1;
            } else {
                currentStreak = 1;
            }  
            if (currentStreak>bestStreak) {
                bestStreak = currentStreak;
            }
        }
        return bestStreak;
    } // end getBestStreak() method
    
        
    // method to get the maximum possible completions up to a given date
    public int getMaxPossible(Calendar todaysDate) {
        // create a copy of date passed in
        Calendar todayCopy = (Calendar) todaysDate.clone();
        // check if endDate was set
        if (endDate != null) {
            // set todayCopy to end date if endDate before todaysDate
            if (todaysDate.compareTo(endDate)>0) {
                 todayCopy = (Calendar) endDate.clone();
            }
        }

        // create LocalDate objects of todayCopy and startDate
        LocalDate compare1 = LocalDate.of(todayCopy.get(Calendar.YEAR),todayCopy.get(Calendar.MONTH)+1,todayCopy.get(Calendar.DATE));
        LocalDate compare2 = LocalDate.of(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH)+1,startDate.get(Calendar.DATE));
        // get total number of days between todayCopy and startDate plus 1
        long numDaysBetween = DAYS.between(compare2,compare1)+1;
        int numDaysInt = (int)numDaysBetween;
        return numDaysInt;
    }
    
    // method to write a task to JSON
    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skbld";
        String fileName = filePrefix + taskSuffixNumber + ".json";

        Calendar cal = this.getStartDate();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("taskId", this.getTaskId());
        jsonObject.put("year", year);
        jsonObject.put("month", month);
        jsonObject.put("date", date);
        jsonObject.put("isCompleted", this.getIsCompleted());
        jsonObject.put("taskName", this.getTaskName());
        jsonObject.put("notes", this.notes);

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);

    } // end method writeTaskToJSON()

    /* DO YOU NEED TO OVERRIDE the Task.getCurrentStreak() or
     * Task.getBestStreak() methods?  */
}
