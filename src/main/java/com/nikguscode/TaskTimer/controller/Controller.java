package com.nikguscode.TaskTimer.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Controller {
    void handleCommands();
    void handleCommands(Update update);
    SendMessage sendMessage();
    EditMessageText sendEditMessage();

}
