package com.nikguscode.TaskTimer.controller.state;

import com.nikguscode.TaskTimer.controller.TaskController;
import org.springframework.stereotype.Controller;

@Controller
public class TaskState implements KeyboardCommandHandler {
    private final TaskController taskController;

    public TaskState(TaskController taskController) {
        this.taskController = taskController;
    }

    @Override
    public void handleCommands() {
        taskController.handleCommands();
    }
}