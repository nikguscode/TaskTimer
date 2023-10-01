package com.nikguscode.TaskTimer.controller.telegramConnection;

import com.nikguscode.TaskTimer.controller.MenuController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class BotController extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MenuController menuController;

    @Autowired
    public BotController(BotConfig botConfig, MenuController menuController) {
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            menuController.mainRedirecting(update);

            if (menuController.getSendMessage() == null) {
                log.warn("null or undefined message");
            }

            if (menuController.getSendMessage() != null) {
                try {
                    log.info("Отправлено сообщение: " + menuController.getSendMessage().getText());
                    execute(menuController.getSendMessage());
                } catch (TelegramApiException e) {
                    log.error("Telegram exception");
                    throw new RuntimeException(e.getClass().getSimpleName());
                } catch (NullPointerException e) {
                    log.error("Null Exception");
                    throw new RuntimeException(e);
                }
            }

        }
    }
}
