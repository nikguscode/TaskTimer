package com.nikguscode.TaskTimer.controller.telegramControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class BotInitializer  {

    private BotController botController;

    @Autowired
    public void setBotConnection(BotController botController) {
        this.botController = botController;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException{
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(botController);
    }

}
