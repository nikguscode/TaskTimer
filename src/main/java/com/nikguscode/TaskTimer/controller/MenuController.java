package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.commands.HelloCommand;
import com.nikguscode.TaskTimer.model.service.telegramConnection.BotConnection;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class MenuController {

    private BotConnection botConnection;

    public MenuController(BotConnection botConnection) {
        this.botConnection = botConnection;
    }

    public void mainRedirecting(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

        }
    }

}
