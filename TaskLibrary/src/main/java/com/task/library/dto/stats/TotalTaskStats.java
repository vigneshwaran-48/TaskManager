package com.task.library.dto.stats;

public class TotalTaskStats {
    
    private int taskCompleted;
    private int tasksPending;
    private int tasksOverdue;

    public TotalTaskStats() {}
    
    public TotalTaskStats(int taskCompleted, int tasksPending, int tasksOverdue) {
        this.taskCompleted = taskCompleted;
        this.tasksPending = tasksPending;
        this.tasksOverdue = tasksOverdue;
    }

    public int getTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(int taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public int getTasksPending() {
        return tasksPending;
    }

    public void setTasksPending(int tasksPending) {
        this.tasksPending = tasksPending;
    }

    public int getTasksOverdue() {
        return tasksOverdue;
    }

    public void setTasksOverdue(int tasksOverdue) {
        this.tasksOverdue = tasksOverdue;
    }

}
