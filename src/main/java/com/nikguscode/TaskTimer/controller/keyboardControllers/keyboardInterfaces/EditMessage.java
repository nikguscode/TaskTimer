package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Controller
public interface EditMessage {
    EditMessageText editMessage();
}
