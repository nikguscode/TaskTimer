package com.nikguscode.TaskTimer.model.service.strategy;

import com.nikguscode.TaskTimer.controller.MasterController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.TaskController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStrategy implements ControllerStrategy {

    private MasterController masterController;
    private final TaskController taskController;

    @Autowired
    public TaskStrategy(TaskController taskController) {
        this.taskController = taskController;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return taskController;
    }

    @Override
    public MessageSender getMessageSender() {
        return taskController;
    }

}
