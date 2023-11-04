package com.nikguscode.TaskTimer.controller.telegramControllers;

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
            telegramData.getMessageInfo(update);
            telegramData.logMessageInfo(update);
            masterController.setController();
            masterController.setDatabaseController(update);

            if (masterController.getSendMessage() != null || masterController.sendMessage() != null) {
                try {

                    if (masterController.sendMessage().getText() != null) {
                        log.info("Отправлено сообщение: " + masterController.sendMessage().getText());
                        execute(masterController.sendMessage());
                    }

                    if (masterController.getSendMessage().getText() != null) {
                        log.info("Отправлено сообщение: " + masterController.getSendMessage().getText());
                        execute(masterController.getSendMessage());
                    }

                } catch (TelegramApiException e) {
                    log.error("Ошибка Telegram API при отправке сообщения", e);
                    throw new RuntimeException("Ошибка Telegram: " + e.getMessage(), e);
                } catch (NullPointerException e) {
                    log.error("Ошибка NullPointerException при отправке сообщения", e);
                    throw new RuntimeException("Ошибка NullPointerException: " + e.getMessage(), e);
                } catch (Exception e) {
                    log.error("Непредвиденная ошибка при отправке сообщения", e);
                    throw new RuntimeException("Непредвиденная ошибка: " + e.getMessage(), e);
                }
            }

        }

        if (update.hasCallbackQuery()) {
            telegramData.getCallbackQuery(update);
            masterController.setCallbackController(update);

            try {
                log.info("[Callback] Отправлено сообщение: " + masterController.editMessageText().getText());
                execute(masterController.editMessageText());
            } catch (TelegramApiException e) {
                log.error("[Callback] Ошибка Telegram API при отправке сообщения", e);
                throw new RuntimeException("Ошибка Telegram: " + e.getMessage(), e);
            } catch (NullPointerException e) {
                log.error("[Callback] Ошибка NullPointerException при отправке сообщения", e);
                throw new RuntimeException("Ошибка NullPointerException: " + e.getMessage(), e);
            } catch (Exception e) {
                log.error("[Callback] Непредвиденная ошибка при отправке сообщения", e);
                throw new RuntimeException("Непредвиденная ошибка: " + e.getMessage(), e);
            }

        }

    }
}