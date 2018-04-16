// package
package llamasoft.skillblazer;

// imports
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import java.util.*;

// main class
public class CalendarCalculator {
    
    // member fields
    private Calendar currentMonth;      // Calendar object holding current month
    private int thisMonth;              // int holding current month
    private int thisYear;               // int holding current year

    //protected TilePane calendarPane = new TilePane();      // remove????

    //protected ArrayList<Day> thisMonthsDayObjects = new ArrayList<>();

//   private static final String[] dayNamesOfWeek = { "Monday", "Tuesday", "Wednesday",
//                                                     "Thursday", "Friday", "Saturday", "Sunday" };


//    private static final String[] shortDayNamesOfWeek = { "MON", "TUE", "WED", "THU",
//                                                          "FRI", "SAT", "SUN" };


    // NEVER use this directly outside of this class,
    // as IT DOES NOT ACCOUNT FOR LEAP YEARS!
    private final int[] daysOfMonth = { 31, 28, 31, 30,
                                        31, 30, 31, 31,
                                        30, 31, 30, 31 };

    // constructor
    public CalendarCalculator() {
        this.currentMonth = new GregorianCalendar();
    } // end constructor
    
    // method to get the first day of the week of the current month
    public int getFirstDayOfWeekCurrentMonth() {
        Calendar tempMonth = new GregorianCalendar();
        tempMonth.set(currentMonth.get(Calendar.YEAR), currentMonth.get(Calendar.MONTH), 0);
        return (tempMonth.get(Calendar.DAY_OF_WEEK)%7);
    } // end getFirstDayOfWeekCurrentMonth() method
    
    // method to get the number of days in the current month
    public int getDaysInCurrentMonth() {
        return getDaysInThisCalendarMonth((GregorianCalendar)currentMonth);
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
    
    // method to instantiate calendar
    public void instantiateCalendar() {
        Scene calendarScene;
        int daysThisMonth = getDaysInThisCalendarMonth(getCurrentYearInt(), getCurrentMonthInt());


        for(int dates = 0; dates<daysThisMonth; dates++) {
            Day newDay = new Day();                 // ****TO DO: finish this constructor call
            // create a Day object
            // add tasks to the day object
            // generate GUI element for the Day object
            addDayToCalendarTile(newDay);
        }
    } // end instantiateCalendar() method

    // method to add the Day object to calendar
    private void addDayToCalendarTile(Day day) {
    }

    // method to get the number of days in a calendar month
    protected int getDaysInThisCalendarMonth(int year, int month) {
        if ( (month == 1) && (isLeapYear(year)) ) {
            return 29;  // February AND a Leap year
        }
        else
            return daysOfMonth[month];
    } // end method getDaysInThisCalendarMonth()

    // overloaded version - just pass in a Calendar object!
    protected int getDaysInThisCalendarMonth(GregorianCalendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        if ((month == 1) && (isLeapYear( calendar.get(Calendar.YEAR)))) {
            return 29;  // February AND a Leap year
        }
        else
            return daysOfMonth[month];
    } // end method getDaysInThisCalendarMonth()


    // method that determines leap year so February is correctly created!
    /*
     * @Author
     * ‘Cletus’ (2009, June 20).  Java code for calculating leap year.
     * Message and source code posted to http://stackoverflow.com.
     */
    private boolean isLeapYear(int currentYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    } // end isLeapYear() method

} // end class CalendarCalculator