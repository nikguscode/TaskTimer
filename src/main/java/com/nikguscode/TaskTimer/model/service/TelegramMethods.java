package com.nikguscode.TaskTimer.model.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@Slf4j
public class TelegramMethods {

    private String messageText;
    private Long userId;
    private Long chatId;
    private Instant instant;
    private String formattedTime;
    private String userName;

    public void getMessage(Update update) {
        messageText = update.getMessage().getText();
        userId = update.getMessage().getFrom().getId();
        userName = update.getMessage().getFrom().getUserName();

        chatId = update.getMessage().getChatId();

        instant = Instant.ofEpochSecond(update.getMessage().getDate());
        getData(instant);

        log.info("Получено сообщение: " + messageText);
    }

    private void getData (Instant instant) {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");

        ZonedDateTime zonedDateTime = instant.atZone(zoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        formattedTime = formatter.format(zonedDateTime);
    }

    public String getMessageText() {
        return messageText;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public Instant getInstant() {
        return instant;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public String getUserName() {
        return userName;
    }

}
