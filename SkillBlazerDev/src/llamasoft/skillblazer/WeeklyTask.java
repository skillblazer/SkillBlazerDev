package llamasoft.skillblazer;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeeklyTask extends Task {

    private long taskId;
    private final Calendar startDate = new GregorianCalendar();

    public long getTaskId() {
        return this.taskId;
    }

    public Calendar getStartDate() {
        return this.startDate;
    }
}
