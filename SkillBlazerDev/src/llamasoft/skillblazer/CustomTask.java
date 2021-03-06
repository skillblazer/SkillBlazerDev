/** **********************************************************
 * Application Name: skillblazer
 * File Name: CustomTask.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
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
// package
package llamasoft.skillblazer;

// imports
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

public class CustomTask extends Task {

    // member fields
    private ArrayList<String> actualDaysInTask = new ArrayList<>();         //days picked by user

    /*
     * Default Class Constructor - calls parent constructor
     */
    public CustomTask() {
        super();
        this.type = "custom";
    } // end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     */
    public CustomTask(String taskName) {
        super(taskName);
        this.type = "custom";
    } // end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     * and startDate
     */
    public CustomTask(String taskName, Calendar startDate) {
        super(taskName, startDate);
        this.type = "custom";
    } // end CustomTask constructor

    /*
     * Overloaded Class Constructor - calls parent constructor with taskName
     * and startDate. Initializes arrayOfDays class variable.
     */
    public CustomTask(String taskName, Calendar startDate, ArrayList<String> arrayOfDays) {
        super(taskName, startDate);
        this.actualDaysInTask.addAll(arrayOfDays);
        this.type = "custom";
    } // end CustomTask constructor


    /*
     *  constructor
     */
    public CustomTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, ArrayList<String> daysInTask) {
        super(taskName, taskId, startDate, isCompleted, "custom", notes);
        this.actualDaysInTask.addAll(daysInTask);
    } // end CustomTask constructor


    /*
     *  The constructor formerly known as fully qualified
     */
    public CustomTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, Calendar endDate, ArrayList<String> daysInTask) {
        super(taskName, taskId, startDate, isCompleted, "custom", notes, endDate);
        this.actualDaysInTask.addAll(daysInTask);
    } // end CustomTask constructor


    /*
     *  Constructor with endDate (needed for initializing objects stored on disk)
     */
    public CustomTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, ArrayList<String> daysInTask, Calendar endDate) {
        super(taskName, taskId, startDate, isCompleted, "custom", notes, endDate);
        this.actualDaysInTask.addAll(daysInTask);
    } // end CustomTask constructor


    /*
     *  Fully qualified constructor with endDate and Completion Dates (Calendar objects) (needed for initializing objects stored on disk)
     */
    public CustomTask(String taskName, long taskId, Calendar startDate, boolean isCompleted, String notes, ArrayList<String> daysInTask, Calendar endDate, ArrayList<Calendar> datesCompleted) {
        super(taskName, taskId, startDate, isCompleted, "custom", notes, datesCompleted, endDate);
        this.actualDaysInTask.addAll(daysInTask);
    } // end CustomTask constructor

    /*
     * Accessor Method - actualDaysInTask
     */
    public ArrayList<String> getDaysOfWeek() {
        return this.actualDaysInTask;
    } // end getDaysOfWeek method

    /*
     * Mutator Method - actualDaysInTask
     */
    public void setDaysOfWeek(ArrayList<String> listOfDays) {
        this.actualDaysInTask.addAll(listOfDays);
    } // end setDaysOfWeek method

    // method to get the maximum possible completions up to a given date
    public int getMaxPossible(Calendar todaysDate) {
        Calendar todayCopy = (Calendar) todaysDate.clone();
        // check if endDate was set
        if (endDate != null) {
            // set todayCopy to end date if endDate before todaysDate
            if (todaysDate.compareTo(endDate) > 0) {
                todayCopy = (Calendar) endDate.clone();
            }
        }

        // get the days of week of the start date and todays date
        int todayDayOfWeek = todayCopy.get(Calendar.DAY_OF_WEEK);
        int startDayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
        // create an adjustment factor that equals the number of days in addition to 
        // the quantity of full weeks
        int daysOfWeekAdj = (todayDayOfWeek - startDayOfWeek + 1) % 7;
        // create LocalDate objects of todayCopy and startDate
        LocalDate compare1 = LocalDate.of(todayCopy.get(Calendar.YEAR), todayCopy.get(Calendar.MONTH) + 1, todayCopy.get(Calendar.DATE));
        LocalDate compare2 = LocalDate.of(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH) + 1, startDate.get(Calendar.DATE));

        // compute the number of full weeks between todayCopy and startDate
        long numFullWeeksBetween = (DAYS.between(compare2, compare1) + 1) / 7;
        // create array to hold integer representation of actualDaysInTask
        int[] daysInTaskInt = new int[actualDaysInTask.size()];
        int i = 0;
        // fill the integer array
        for (String md : actualDaysInTask) {
            if (md.equalsIgnoreCase("Sunday")) {
                daysInTaskInt[i] = 1;
            } else if (md.equalsIgnoreCase("Monday")) {
                daysInTaskInt[i] = 2;
            } else if (md.equalsIgnoreCase("Tuesday")) {
                daysInTaskInt[i] = 3;
            } else if (md.equalsIgnoreCase("Wednesday")) {
                daysInTaskInt[i] = 4;
            } else if (md.equalsIgnoreCase("Thursday")) {
                daysInTaskInt[i] = 5;
            } else if (md.equalsIgnoreCase("Friday")) {
                daysInTaskInt[i] = 6;
            } else if (md.equalsIgnoreCase("Saturday")) {
                daysInTaskInt[i] = 7;
            }
            i++;
        }

        // adjust todayDayOfWeek to be greater than startDayOfWeek
        if (todayDayOfWeek < startDayOfWeek) {
            todayDayOfWeek += 7;
        }
        // count number of additional days in final partial week
        int numAdditionalDays = 0;
        if (daysOfWeekAdj != 0) {
            for (int j = startDayOfWeek; j <= todayDayOfWeek; j++) {
                int dayOfWeekMod = ((j - 1) % 7) + 1;
                for (int k = 0; k < daysInTaskInt.length; k++) {
                    if (dayOfWeekMod == daysInTaskInt[k]) {
                        numAdditionalDays++;
                    }
                }
            }
        }
        // add partial week number to full week number
        int numTaskDays = ((int) numFullWeeksBetween) * daysInTaskInt.length + numAdditionalDays;
        return numTaskDays;
    } // end getMaxPossible() method

    // method to get user's current streak; utilizes Collections.sort
    public int getCurrentStreak(Calendar todaysDate) {
        int currentStreak = 0;
        if (datesCompleted.size() > 0) {
            currentStreak = 1;
        } else {
            return 0;
        }
        // create array to hold integer representation of actualDaysInTask
        int[] daysInTaskInt = new int[actualDaysInTask.size()];
        int i = 0;
        // fill the integer array
        for (String md : actualDaysInTask) {
            if (md.equalsIgnoreCase("Sunday")) {
                daysInTaskInt[i] = 1;
            } else if (md.equalsIgnoreCase("Monday")) {
                daysInTaskInt[i] = 2;
            } else if (md.equalsIgnoreCase("Tuesday")) {
                daysInTaskInt[i] = 3;
            } else if (md.equalsIgnoreCase("Wednesday")) {
                daysInTaskInt[i] = 4;
            } else if (md.equalsIgnoreCase("Thursday")) {
                daysInTaskInt[i] = 5;
            } else if (md.equalsIgnoreCase("Friday")) {
                daysInTaskInt[i] = 6;
            } else if (md.equalsIgnoreCase("Saturday")) {
                daysInTaskInt[i] = 7;
            }
            i++;
        }
        int[] dayDiffTasks = new int[7];
        for (i = 0; i < 7; i++) {
            dayDiffTasks[i] = 7;
        }
        int j;
        Arrays.sort(daysInTaskInt);
        if (daysInTaskInt.length > 1) {
            for (i = 0; i < daysInTaskInt.length; i++) {
                int index1 = i % (daysInTaskInt.length);
                int index2 = ((i - 1) % (daysInTaskInt.length) + daysInTaskInt.length) % daysInTaskInt.length;

                dayDiffTasks[daysInTaskInt[i] - 1] = ((daysInTaskInt[index1] - daysInTaskInt[index2]) % 7 + 7) % 7;

            }
        }
        Collections.sort(datesCompleted);
        for (i = 1; i < datesCompleted.size(); i++) {
            LocalDate compare1 = LocalDate.of(datesCompleted.get(i).get(Calendar.YEAR), datesCompleted.get(i).get(Calendar.MONTH) + 1, datesCompleted.get(i).get(Calendar.DATE));
            LocalDate compare2 = LocalDate.of(datesCompleted.get(i - 1).get(Calendar.YEAR), datesCompleted.get(i - 1).get(Calendar.MONTH) + 1, datesCompleted.get(i - 1).get(Calendar.DATE));
            long numDaysBetween = DAYS.between(compare2, compare1);
            int DayOfWeek = datesCompleted.get(i).get(Calendar.DAY_OF_WEEK);

            if (numDaysBetween == dayDiffTasks[DayOfWeek - 1]) {
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
        if (i > (datesCompleted.size() - 1)) {
            i = datesCompleted.size() - 1;
        }
        // Catch for current streak not making it to today or yesterday
        LocalDate compare1 = LocalDate.of(todaysDate.get(Calendar.YEAR), todaysDate.get(Calendar.MONTH) + 1, todaysDate.get(Calendar.DATE));
        LocalDate compare2 = LocalDate.of(datesCompleted.get(i).get(Calendar.YEAR), datesCompleted.get(i).get(Calendar.MONTH) + 1, datesCompleted.get(i).get(Calendar.DATE));
        long numDaysBetween = DAYS.between(compare2, compare1);
        if (numDaysBetween > 7) {
            currentStreak = 0;
        }
        return currentStreak;
    } // end getCurrentStreak() method

    // method to get user's best streak; utilizes Collections.sort
    public int getBestStreak() {
        int bestStreak = 0;
        int currentStreak = 0;
        if (datesCompleted.size() > 0) {
            currentStreak = 1;
            bestStreak = 1;
        } else {
            return bestStreak;
        }

        // create array to hold integer representation of actualDaysInTask
        int[] daysInTaskInt = new int[actualDaysInTask.size()];
        int i = 0;
        // fill the integer array
        for (String md : actualDaysInTask) {
            if (md.equalsIgnoreCase("Sunday")) {
                daysInTaskInt[i] = 1;
            } else if (md.equalsIgnoreCase("Monday")) {
                daysInTaskInt[i] = 2;
            } else if (md.equalsIgnoreCase("Tuesday")) {
                daysInTaskInt[i] = 3;
            } else if (md.equalsIgnoreCase("Wednesday")) {
                daysInTaskInt[i] = 4;
            } else if (md.equalsIgnoreCase("Thursday")) {
                daysInTaskInt[i] = 5;
            } else if (md.equalsIgnoreCase("Friday")) {
                daysInTaskInt[i] = 6;
            } else if (md.equalsIgnoreCase("Saturday")) {
                daysInTaskInt[i] = 7;
            }
            i++;
        }

        int[] dayDiffTasks = new int[7];
        for (i = 0; i < 7; i++) {
            dayDiffTasks[i] = 7;
        }
        int j;
        Arrays.sort(daysInTaskInt);
        if (daysInTaskInt.length > 1) {
            for (i = 0; i < daysInTaskInt.length; i++) {
                int index1 = i % (daysInTaskInt.length);
                int index2 = ((i - 1) % (daysInTaskInt.length) + daysInTaskInt.length) % daysInTaskInt.length;

                dayDiffTasks[daysInTaskInt[i] - 1] = ((daysInTaskInt[index1] - daysInTaskInt[index2]) % 7 + 7) % 7;

            }
        }

        Collections.sort(datesCompleted);
        for (i = 1; i < datesCompleted.size(); i++) {
            LocalDate compare1 = LocalDate.of(datesCompleted.get(i).get(Calendar.YEAR), datesCompleted.get(i).get(Calendar.MONTH) + 1, datesCompleted.get(i).get(Calendar.DATE));
            LocalDate compare2 = LocalDate.of(datesCompleted.get(i - 1).get(Calendar.YEAR), datesCompleted.get(i - 1).get(Calendar.MONTH) + 1, datesCompleted.get(i - 1).get(Calendar.DATE));
            long numDaysBetween = DAYS.between(compare2, compare1);
            int DayOfWeek = datesCompleted.get(i).get(Calendar.DAY_OF_WEEK);

            if (numDaysBetween == dayDiffTasks[DayOfWeek - 1]) {
                currentStreak += 1;
            } else {
                currentStreak = 1;
            }
            if (currentStreak > bestStreak) {
                bestStreak = currentStreak;
            }
        }
        return bestStreak;
    } // end getBestStreak() method

    /*
     * method to write the CustomTask info to JSON
     */
    @Override
    public void writeTaskToJSON() {
        String taskSuffixNumber = String.valueOf(this.getTaskId());
        String filePrefix = "skblc";
        String fileName = filePrefix + taskSuffixNumber + ".json";

        Calendar cal = this.getStartDate();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        int[] endValues = this.getEndValues();
        int endYear = endValues[0];
        int endMonth = endValues[1];
        int endDate = endValues[2];

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("taskId", this.getTaskId());
        // start date values
        jsonObject.put("year", year);
        jsonObject.put("month", month);
        jsonObject.put("date", date);
        // end date values
        jsonObject.put("endYear", endYear);
        jsonObject.put("endMonth", endMonth);
        jsonObject.put("endDate", endDate);
        // remaining fields
        jsonObject.put("isCompleted", this.getIsCompleted());
        jsonObject.put("taskName", this.getTaskName());
        jsonObject.put("notes", this.notes);

        JSONWriter.prepareCalendarObjectsForJSONStorage(jsonObject, this.datesCompleted);

        JSONArray jsonArray = new JSONArray();

        // add all the days listed in task.getDaysOfWeek() to a JSONArray
        for (String s : this.getDaysOfWeek()) {
            jsonArray.add(s);
        }

        // add the JSONArray to the JSONObject, name the array "days"
        jsonObject.put("days", jsonArray);

        JSONWriter.writeJSON(jsonObject, fileName);
        JSONWriter.addFileToInit(fileName);

    } // end method writeTaskToJSON()
    
} //end of CustomTask class
