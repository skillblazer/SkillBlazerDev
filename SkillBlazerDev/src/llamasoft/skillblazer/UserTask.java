package llamasoft.skillblazer;

import java.util.Date;

public class UserTask implements Task {

    private long taskId;
    private final Date startDate = new Date();

    public long getTaskId() {
        return this.taskId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

}
