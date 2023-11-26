package com.nikguscode.TaskTimer.controller.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.TaskController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TaskStrategy implements ReplyStrategy {

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
