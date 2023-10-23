package com.nikguscode.TaskTimer.controller.state;

import com.nikguscode.TaskTimer.controller.TaskController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TaskState implements KeyboardCommandHandler {
    private final TaskController taskController;

    @Autowired
    public TaskState(TaskController taskController) {
        this.taskController = taskController;
    }

    public void handleCommands() {
        taskController.handleCommands();
    }

}
