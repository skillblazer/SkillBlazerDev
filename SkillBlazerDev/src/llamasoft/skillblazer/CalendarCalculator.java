package llamasoft.skillblazer;

import javafx.scene.Scene;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarCalculator {
    private Calendar currentMonth;
    private int thisMonth;
    private int thisYear;

    protected ArrayList<Day> thisMonthsDayObjects = new ArrayList<>();

    private static final String[] dayNamesOfWeek = { "Monday", "Tuesday", "Wednesday",
                                                     "Thursday", "Friday", "Saturday", "Sunday" };


    private static final String[] shortDayNamesOfWeek = { "MON", "TUE", "WED", "THU",
                                                          "FRI", "SAT", "SUN" };


    // NEVER use this directly outside of this class,
    // as IT DOES NOT ACCOUNT FOR LEAP YEARS!
    private final int[] daysOfMonth = { 31, 28, 31, 30,
                                        31, 30, 31, 31,
                                        30, 31, 30, 31 };


    public CalendarCalculator() {
        this.currentMonth = new GregorianCalendar();
        this.thisMonth = currentMonth.get(Calendar.MONTH);
        this.thisYear = currentMonth.get(Calendar.YEAR);
    }


    public int getCurrentMonthInt() {
        return thisMonth;
    }

    /**
     * This section of the code will deal with creating the GUI elements for
     * the JavaFX calendar scene
     */

    public void instantiateCalendar() {
        Scene calendarScene;
        int daysThisMonth = getDaysInThisCalendarMonth(thisYear, thisMonth);

        TilePane calendarPane = new TilePane();  // shall we do this in FXML???

        for(int dates : daysOfMonth) {

            Day newDay = new Day();  // TODO: finish this constructor call
            // create a Day object
            // add tasks to the day object
            // generate GUI element for the Day object
            addDayToCalendarTile(newDay);
        }
    }


    private void addDayToCalendarTile(Day day) {
        // add the Day object to the GUI TilePane object
    }

    /**
    *   Provide the 4-digit YYYY (integer) year, and month as a primitive integer value 0-11 (Normal Java Convention)
    *   This method rigorously checks for leap years. (To include 100 & 400 modulus divisors)
    *   ----Months are counted from 0, e.g. 0 = January, 11 = December
    */
    protected int getDaysInThisCalendarMonth(int year, int month) {
        if ( (month == 1) && (isLeapYear(year)) ) {
            return 29;  // February AND a Leap year
        }
        else
            return daysOfMonth[month];
    } // end method getDaysInThisCalendarMonth()


    // Overloaded version - just pass in a Calendar object!
    protected int getDaysInThisCalendarMonth(GregorianCalendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        if ((month == 1) && (isLeapYear( calendar.get(Calendar.YEAR)))) {
            return 29;  // February AND a Leap year
        }
        else
            return daysOfMonth[month];
    } // end method getDaysInThisCalendarMonth()


    // Determine leap year so February is correctly created!
    private boolean isLeapYear(int currentYear) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, currentYear);

        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

} // end class SkillBlazerCalendar