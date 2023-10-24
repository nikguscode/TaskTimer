package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ReplyController {
    void handleCommands();
    SendMessage sendMessage();
}
