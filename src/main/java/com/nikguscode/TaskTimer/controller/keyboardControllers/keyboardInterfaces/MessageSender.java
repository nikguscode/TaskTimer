package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Configuration
public interface MessageSender {
    SendMessage sendMessage();
}
