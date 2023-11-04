package com.nikguscode.TaskTimer.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class TaskDuration {

    private TelegramData telegramData;
    private String formattedTime;
    private Instant instant;

    @Autowired
    public TaskDuration(TelegramData telegramData) {
        this.telegramData = telegramData;
        this.instant = telegramData.getInstant();
    }

    private void getFormattedTime(Instant instant) {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        formattedTime = formatter.format(zonedDateTime);
    }

}
