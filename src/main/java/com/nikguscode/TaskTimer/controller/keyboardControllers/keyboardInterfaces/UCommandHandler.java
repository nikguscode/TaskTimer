package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UCommandHandler {
    void handleCommands(Update update);
}
