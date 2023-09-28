package com.nikguscode.TaskTimer.model.service.telegramConnection;

import com.nikguscode.TaskTimer.controller.MenuController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotConnection extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MenuController menuController;

    @Autowired
    public BotConnection(BotConfig botConfig, MenuController menuController) {
        this.botConfig = botConfig;
        this.menuController = menuController;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        menuController.mainRedirecting(update);
    }

}
