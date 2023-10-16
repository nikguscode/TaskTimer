package com.nikguscode.TaskTimer.controller.state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageHandler {
    SendMessage sendMessage();
}
