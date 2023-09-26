package com.nikguscode.TaskTimer.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotConnection extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Autowired
    public BotConnection(BotConfig botConfig) {
        this.botConfig = botConfig;
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Получаем текст сообщения
            String messageText = update.getMessage().getText();

            // Выводим сообщение в консоль
            System.out.println("Received message: " + messageText);
        }
    }

}
