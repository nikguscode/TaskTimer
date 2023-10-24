package com.nikguscode.TaskTimer.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        getFormattedTime(instant);
    }

    public void logMessageInfo(Update update) {
        log.info("Получено сообщение: " + messageText);
    }

    public void getCallbackQuery(Update update) {
        callbackQuery = update.getCallbackQuery();
        callbackData = callbackQuery.getData();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
    }

    private void getFormattedTime(Instant instant) {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        formattedTime = formatter.format(zonedDateTime);
    }

    public void getFormattedCategory() {
        String[] formattedText = messageText.split("\\s*\\|\\s*");
        categoryName = formattedText[0];
        categoryDescription = formattedText[1];
    }

}