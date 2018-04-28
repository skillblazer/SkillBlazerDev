package llamasoft.skillblazer;

/**
 *  The child classes listed above represent the different types of goals that
 *  define these habits/skills.
 *
 *  A weekly task is one in which the user has the goal of completing once a
 *  week, such as taking out the trash.
 *
 *The WeeklyTask class is similar to the DailyTask class in that it contains
 * fields for current streak and best streak, as well as methods to get these
 * values. However, for the WeeklyTask class, the current and best streak
 * pertains to the number of weeks in a row (as opposed to days in  row).
 */

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class WeeklyTask extends Task {

    // constructor
    public WeeklyTask() {
        super();
        this.type = "weekly";
    }
    
    // constructor
    public WeeklyTask(String taskName) {
        super(taskName);
        this.type = "weekly";
    }
    
     /*
     *  New Fully qualified constructor (needed for initializing objects stored on disk
     */
    public WeeklyTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes) {
        super(taskName, taskId, startDate, isCompleted, "weekly", notes);
    } //end WeeklyTask constructor

    /*
     *  New Fully qualified constructor (needed for initializing objects stored on disk
     */
    public WeeklyTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, ArrayList<Calendar> datesCompleted) {
        super(taskName, taskId, startDate, isCompleted, "weekly", notes, datesCompleted);
    } //end WeeklyTask constructor

    // method to get user's current streak; utilizes Collections.sort
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
            if (numDaysBetween==7) {
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
        if (numDaysBetween > 7) {
            currentStreak = 0;
        }        
        return currentStreak;
    } // end getCurrentStreak() method
    
    // method to get user's best streak; utilizes Collections.sort
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
            if (numDaysBetween==7) {
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
            // get the day of week of the start date
            int startDayOfWeek = startDate.get(Calendar.DAY_OF_WEEK); 
            
            // create LocalDate objects of todayCopy and startDate
            LocalDate compare1 = LocalDate.of(todayCopy.get(Calendar.YEAR),todayCopy.get(Calendar.MONTH)+1,todayCopy.get(Calendar.DATE));
            LocalDate compare2 = LocalDate.of(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH)+1,startDate.get(Calendar.DATE));
            
            // count the number of saturdays between startDate and todayCopy
            long numSaturdaysBetween = (DAYS.between(compare2,compare1)+(((long)startDayOfWeek)))/7;
            int numSaturdaysInt = (int)numSaturdaysBetween;
            return numSaturdaysInt;
    }
    
    

    // method to write a task to JSON
    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skblw";
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

        JSONWriter.prepareCalendarObjectsForJSONStorage(jsonObject, this.datesCompleted);

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);

    } // end overloaded method writeTaskToJSON(WeeklyTask task)

}
