package llamasoft.skillblazer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SkillBlazerCalendar {

    private Calendar currentMonth;
    private int thisMonth;
    private int thisYear;

    private static final String[] dayNamesOfWeek = { "Monday", "Tuesday", "Wednesday",
                                                     "Thursday", "Friday", "Saturday", "Sunday" };

    private static final String[] shortDayNamesOfWeek = { "MON", "TUE", "WED", "THU",
                                                          "FRI", "SAT", "SUN" };

    // NEVER use this directly outside of this class,
    // as IT DOES NOT ACCOUNT FOR LEAP YEARS!
    private final int[] daysOfMonth = { 31, 28, 31, 30,
                                        31, 30, 31, 31,
                                        30, 31, 30, 31 };

    public SkillBlazerCalendar() {
        this.currentMonth = new GregorianCalendar();
        this.thisMonth = currentMonth.get(Calendar.MONTH);
        this.thisYear = currentMonth.get(Calendar.YEAR);
    }

    public int getCurrentMonthInt() {
        return thisMonth;
    }

    /**
    *   Provide the (integer) year, and month as a primitive integer value 0-11 (Normal Java Convention)
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
    protected int getDaysInThisCalendarMonth(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        if ((month == 1) && (isLeapYear( calendar.get(Calendar.YEAR)))) {
            return 29;  // February AND a Leap year
        }
        else
            return daysOfMonth[month];
    } // end method getDaysInThisCalendarMonth()


    // Determine leap year so February is correctly created!
    protected static boolean isLeapYear(int currentYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, currentYear);

        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

} // end class SkillBlazerCalendar