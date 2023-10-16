package com.nikguscode.TaskTimer.model.service.commands;


import com.nikguscode.TaskTimer.model.service.TelegramData;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * This class contains "/start" and "/stop" commands logic
 */

@Service
public class LaunchCommands {

    private final TelegramData telegramData;
    private Instant startTime;
    private Instant endTime;
    private String formattedDuration;
    private boolean isStarted;

    public LaunchCommands(TelegramData telegramData) {
        this.telegramData = telegramData;
    }

    public void start() {
        isStarted = true;
        startTime = telegramData.getInstant();
    }

    public void stop() {
        isStarted = false;

        endTime = telegramData.getInstant();
        getDuration();
    }

    private void getDuration() {
        Duration duration = Duration.between(startTime, endTime);

        if (duration.toDays() == 0) {
            formattedDuration = (duration.toHours() < 10 ? "0" : "") + duration.toHours() % 24 + ":"
                    + (duration.toMinutes() < 10 ? "0" : "") + duration.toMinutes() % 60 + ":"
                    + (duration.toSeconds() < 10 ? "0" : "") + duration.toSeconds() % 60;
        } else {
            formattedDuration = duration.toDays() + "d. | "
                    + (duration.toHours() < 10 ? "0" : "") +duration.toHours() % 24 + ":"
                    + (duration.toMinutes() < 10 ? "0" : "") +duration.toMinutes() % 60 + ":"
                    + (duration.toSeconds() < 10 ? "0" : "") + duration.toSeconds() % 60;
        }

    }

    public boolean isStarted() {
        return isStarted;
    }

    public String getFormattedDuration() {
        return formattedDuration;
    }

}
