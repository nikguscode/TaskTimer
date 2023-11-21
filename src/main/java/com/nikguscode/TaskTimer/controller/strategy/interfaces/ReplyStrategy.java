package com.nikguscode.TaskTimer.controller.strategy.interfaces;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;

public interface ReplyStrategy {
    CommandHandler getCommandHandler();
    MessageSender getMessageSender();
}
