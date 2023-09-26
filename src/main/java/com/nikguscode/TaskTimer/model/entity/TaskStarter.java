package com.nikguscode.TaskTimer.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//@AutoValue
@Getter
@Setter
public class TaskStarter {

    private int categoryId;
    private String categoryType;
    private LocalDateTime startDate;
    private String taskName;
    private String taskDescription;

    public TaskStarter(int categoryId,
                       String categoryType,
                       LocalDateTime startDate,
                       String taskName) {
        this(categoryId, categoryType, startDate, taskName, "");
    }

    public TaskStarter(int categoryId,
                       String categoryType,
                       LocalDateTime startDate,
                       String taskName,
                       String taskDescription) {
        this.categoryId = categoryId;
        this.categoryType = categoryType;
        this.startDate = startDate;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }



}
