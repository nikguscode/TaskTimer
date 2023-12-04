package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Controller
public interface MessageSender {
    SendMessage sendMessage();
}
