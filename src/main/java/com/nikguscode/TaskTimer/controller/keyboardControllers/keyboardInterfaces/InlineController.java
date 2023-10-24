package com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface InlineController {
    void handleCommands(Update update);
    EditMessageText sendEditMessage();
}
