package com.nikguscode.TaskTimer.controller.telegramConnection;

import com.nikguscode.TaskTimer.controller.MasterController;
import com.nikguscode.TaskTimer.model.service.TelegramData;
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
    private final TelegramData telegramData;
    private final MasterController masterController;

    @Autowired
    public BotController(BotConfig botConfig,
                         TelegramData telegramData,
                         MasterController masterController) {
        this.botConfig = botConfig;
        this.telegramData = telegramData;
        this.masterController = masterController;
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
            telegramData.getMessage(update);
            masterController.setController();

            if (masterController.getSendMessage() != null) {
                try {
                    log.info("Отправлено сообщение: " + masterController.getSendMessage().getText());
                    execute(masterController.getSendMessage());
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
