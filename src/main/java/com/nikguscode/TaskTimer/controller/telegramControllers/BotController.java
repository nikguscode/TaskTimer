package com.nikguscode.TaskTimer.controller.telegramControllers;

import com.nikguscode.TaskTimer.controller.InlineController;
import com.nikguscode.TaskTimer.controller.ReplyController;
import com.nikguscode.TaskTimer.model.service.MessageInterceptor;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
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
    private final BotData botData;
    private final ReplyController replyController;
    private final MessageInterceptor messageInterceptor;
    private final InlineController inlineController;

    @Autowired
    public BotController(BotConfig botConfig,
                         BotData botData,
                         ReplyController replyController,
                         InlineController inlineController,
                         MessageInterceptor messageInterceptor) {
        this.botConfig = botConfig;
        this.botData = botData;
        this.replyController = replyController;
        this.messageInterceptor = messageInterceptor;
        this.inlineController = inlineController;
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
            botData.getMessageInfo(update);
            replyController.setReplyController(update);

            if (botData.isInputWaiting()) {
                messageInterceptor.interceptMessage(update);
                botData.setInputWaiting(false);
            }

                try {
                    if (replyController.sendMessage() != null) {
                        if (replyController.sendMessage().getText() != null && !update.hasCallbackQuery()) {
                            log.info("Отправлено сообщение: " + replyController.sendMessage().getText());
                            execute(replyController.sendMessage());
                        }
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

        if (update.hasCallbackQuery()) {
            botData.getCallbackInfo(update);
            inlineController.setInlineController(update);
            replyController.setReplyController(update);

            try {

                if (inlineController.editMessage(update) != null) {
                    if (inlineController.editMessage(update).getText() != null) {
                        log.info("[Callback] Отправлено сообщение: " + inlineController.editMessage(update).getText());
                        execute(inlineController.editMessage(update));
                    }
                }

                if (replyController.sendMessage() != null) {
                    if (replyController.sendMessage().getText() != null &&
                            (update.getCallbackQuery().getData().equals("menu_btn"))) {
                        log.info("[CallbackToReply] Отправлено сообщение: " + replyController.sendMessage().getText());
                        execute(replyController.sendMessage());
                    }
                }

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