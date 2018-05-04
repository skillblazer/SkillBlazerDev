/************************************************************
 * Application Name: skillblazer
 * File Name: CalendarCalculator.java
 * Package: src/llamasoft/skillblazer
 * Team: Team B
 * Date: 4/16/2018
 * 
 * Description:
 * 
 * This class is responsible for creating the basic structure
 * of the calendar interface by populating the months, year, and 
 * days, as well as the 'forward' and 'backward' buttons to change
 * the calendar view. The instantiateCalendar() method creates Day
 * objects for the calendar.
 ***********************************************************/

// package
package llamasoft.skillblazer;

// import
import java.util.*;

public class CalendarCalculator {

    // member fields
    private Calendar currentMonth;                              // Calendar object holding current month
    private int thisMonth;                                      // int holding current month
    private int thisYear;                                       // int holding current year
    private Day[] thisMonthsDayObjects = new Day[31];           // array of Day objects

    // NEVER use this directly outside of this class,
    // as IT DOES NOT ACCOUNT FOR LEAP YEARS!
    private final int[] daysOfMonth = {31, 28, 31, 30,
        31, 30, 31, 31,
        30, 31, 30, 31};

    // default, no-argument constructor
    public CalendarCalculator() {
        this.currentMonth = new GregorianCalendar();
    } // end CalendarCalculator constructor

    // method to get the first day of the week of the current month
    public int getFirstDayOfWeekCurrentMonth() {
        Calendar tempMonth = new GregorianCalendar();
        tempMonth.set(currentMonth.get(Calendar.YEAR), currentMonth.get(Calendar.MONTH), 0);
        return (tempMonth.get(Calendar.DAY_OF_WEEK) % 7);
    } // end getFirstDayOfWeekCurrentMonth() method

    // method to get the number of days in the current month
    public int getDaysInCurrentMonth() {
        return getDaysInThisCalendarMonth((GregorianCalendar) currentMonth);
    } // end getDaysInCurrentMonth() method

    // method to get the integer value for the current month
    public int getCurrentMonthInt() {
        return currentMonth.get(Calendar.MONTH);
    } // end getCurrentMonthInt() method

    // method to get the String of the current month
    public String getCurrentMonthString() {
        return currentMonth.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
    } // end getCurrentMonthString() method

    // method to get the current year
    public int getCurrentYearInt() {
        return currentMonth.get(Calendar.YEAR);
    } // end getCurrentYearInt() method

    // method to move month forward
    public void changeMonthForward() {
        if (currentMonth.get(Calendar.MONTH) == 11) {
            currentMonth.roll(Calendar.YEAR, true);
        }
        currentMonth.roll(Calendar.MONTH, true);
    } // end changeMonthForward() method

    // method to move month backward
    public void changeMonthBackward() {
        if (currentMonth.get(Calendar.MONTH) == 0) {
            currentMonth.roll(Calendar.YEAR, false);
        }
        currentMonth.roll(Calendar.MONTH, false);
    } // end changeMonthBackward() method

    // method to instantiate calendar and create Day objects
    public void instantiateCalendar() {
        // int holding the amount of days for a given month
        int daysThisMonth = getDaysInThisCalendarMonth(getCurrentYearInt(), getCurrentMonthInt());
        /*
        * THE FOLLOWING CODE IS MEANT TO BE REMOVED EVENTUALLY - THIS IS CODE TO TEST THE
        * FUNCTIONALITY OF ADDING TASKS TO THE CALENDAR - When more than 4 tasks are added
        * to a given calendar day, 'up' and 'down' arrows are populated to allow user
        * to view all tasks
        */
        for (int dates = 0; dates < daysThisMonth; dates++) {
            GregorianCalendar thisDate = new GregorianCalendar(getCurrentYearInt(), getCurrentMonthInt(), dates+1);
            thisMonthsDayObjects[dates] = new Day(thisDate);                    // ****TO DO: finish this constructor call (JSON)
        }
    } // end instantiateCalendar() method

    // method to get the current month's Day objects
    public Day getDayObject(int dayOfMonth) {
        return thisMonthsDayObjects[dayOfMonth];
    } // end getDayObject(int dayOfMonth) method

    // method to get the number of days in a calendar month
    protected int getDaysInThisCalendarMonth(int year, int month) {
        if ((month == 1) && (isLeapYear(year))) {
            return 29;  // February AND a Leap year
        } else {
            return daysOfMonth[month];
        }
    } // end getDaysInThisCalendarMonth(int year, int month) method

    // overloaded version - just pass in a Calendar object!
    protected int getDaysInThisCalendarMonth(GregorianCalendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        if ((month == 1) && (isLeapYear(calendar.get(Calendar.YEAR)))) {
            return 29;  // February AND a Leap year
        } else {
            return daysOfMonth[month];
        }
    } // end getDaysInThisCalendarMonth(GregorianCalendar calendar) method

    // method that determines leap year so February is correctly created!
    /*
     * @Author
     * ‘Cletus’ (2009, June 20).  Java code for calculating leap year.
     * Message and source code posted to http://stackoverflow.com.
     */
    private boolean isLeapYear(int currentYear) {
        // Calendar object
        Calendar calendar = Calendar.getInstance();
        // sets Calendar object
        calendar.set(Calendar.YEAR, currentYear);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    } // end isLeapYear(int currentYear) method

} // end class CalendarCalculator
