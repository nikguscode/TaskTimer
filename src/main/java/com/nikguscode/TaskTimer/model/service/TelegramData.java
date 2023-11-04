package com.nikguscode.TaskTimer.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Service
@Slf4j
@NoArgsConstructor
@Getter
@Setter
public class TelegramData {

    private Long chatId;
    private int messageId;
    private String messageText;
    private String categoryName;
    private String categoryDescription;
    private String userName;
    private CallbackQuery callbackQuery;
    private String callbackData;
    private Instant instant;
    private String formattedTime;

    public void getMessageInfo(Update update) {
        chatId = update.getMessage().getChatId();
        messageText = update.getMessage().getText();
        userName = update.getMessage().getFrom().getUserName();
        instant = Instant.ofEpochSecond(update.getMessage().getDate());
    }

    public void getLogs(Update update) {
        log.info("Получено сообщение: " + messageText);
    }

    public void getCallbackQuery(Update update) {
        callbackQuery = update.getCallbackQuery();
        callbackData = callbackQuery.getData();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
    }

    public void getFormattedCategory() {
        String[] formattedText = messageText.split("\\s*\\|\\s*");

        if (formattedText.length != 0) {
            categoryName = formattedText[0];
            if (formattedText.length >= 1) {
                categoryDescription = formattedText[1];
            }
        }
    }

}