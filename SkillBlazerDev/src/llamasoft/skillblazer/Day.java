package llamasoft.skillblazer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Day {

    // this class will contain a Calendar date
    // user task schedule for that day
    private boolean hasTranspired = false;
    private Calendar taskDate = new GregorianCalendar();

    public boolean getHasTranspired() {
        return hasTranspired;
    }

    public Calendar getTaskDate() {
        return taskDate;
    }

    // use this at the end of a calendar day
    protected void setHasTranspired() {
        hasTranspired = true;  // can only do this once!
    }

    // Constructor that will accept a date from the calling method
    // This is important for creating FUTURE dates that will go
    // into a monthly calendar
    public Day(Calendar taskDate) {
        this.taskDate = taskDate;
    }

    // Constructor that will create its own date upon creation
    // not sure if a use case exists for 'today's date'
    // since we should be looking at a month at a time
    public Day() {
        this.taskDate = new GregorianCalendar();
    }

}
