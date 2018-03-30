package llamasoft.skillblazer;

public class DailyTask extends Task {

    private boolean isCompleted = false;
    private String taskName;

    public boolean getIsCompleted() {
        return isCompleted;
    }

    protected void setCompleted() {
        isCompleted = true;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public DailyTask(String taskName) {
        this.taskName = taskName;
        this.isCompleted = false;
    }

}