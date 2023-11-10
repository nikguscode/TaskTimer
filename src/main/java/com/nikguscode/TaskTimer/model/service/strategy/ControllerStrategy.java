package com.nikguscode.TaskTimer.model.service.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;

public interface ControllerStrategy {
    CommandHandler getCommandHandler();
    MessageSender getMessageSender();
}
