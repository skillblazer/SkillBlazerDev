package llamasoft.skillblazer;

/**
 * The Task class contains the methods and fields that will apply to all of
 * the types of tasks. Included in the Task class will be methods to initialize
 * the task, edit the task, delete the task, get task ID, get start date, and
 * get task name.  
 */

import java.util.Calendar;

public class Task {
    protected String taskName;
    protected long taskId;
    protected Calendar startDate;
    protected boolean isCompleted;
    protected String type;

    long getTaskId() {
        return 0;  // placeholder return statement -> needs real code!
    }

    Calendar getStartDate() {
        return null;  // placeholder return statement -> needs real code!
    }

    public Task() {}

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public Task(String taskName, Calendar startDate) {
        this.taskName = taskName;
        this.startDate = startDate;
    }

    public Task(String taskName, long taskId, Calendar startDate, boolean isCompleted, String type) {
        this.taskName = taskName;
        this.taskId = taskId;
        this.startDate = startDate;
        this.isCompleted = isCompleted;
        this.type = type;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void startTask() {
        // initialize task after user input
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    protected void setCompleted() {
        this.isCompleted = true;
    }

    public void editTask() {
        // make changes to an existing task
    }

    protected void deleteTask(Task task) {
        // kill the task specified by the caller
    }

    protected void deleteRecord() {
        // delete a record
    }

}
