package com.nikguscode.TaskTimer.model.entity;

import java.time.LocalDateTime;

//@AutoValue
public class TaskFinisher {

    private String taskName;
    private LocalDateTime finishDate;

    public TaskFinisher() {
        this("");
    }

    public TaskFinisher(String taskName) {
        this(taskName, null);
    }

    public TaskFinisher(String taskName, LocalDateTime finishDate) {
        this.taskName = taskName;
        this.finishDate = finishDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

}
