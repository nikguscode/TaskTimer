package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public interface UCommandHandler {
    void handleCommands(Update update);
}
