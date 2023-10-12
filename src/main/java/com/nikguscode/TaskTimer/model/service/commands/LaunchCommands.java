package com.nikguscode.TaskTimer.model.service.commands;


import com.nikguscode.TaskTimer.model.service.TelegramMethods;
import com.nikguscode.TaskTimer.view.MenuBoard;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * This class contains "/start" and "/stop" command logic
 */

@Service
public class LaunchCommands {

    private final TelegramMethods telegramMethods;
    private Instant startTime;
    private Instant endTime;
    private String formattedDuration;
    private boolean isStarted;

    public LaunchCommands(TelegramMethods telegramMethods) {
        this.telegramMethods = telegramMethods;
    }

    public void start() {
        isStarted = true;
        startTime = telegramMethods.getInstant();
    }

    public void stop() {
        isStarted = false;

        endTime = telegramMethods.getInstant();
        getDuration();
    }

    private void getDuration() {
        Duration duration = Duration.between(startTime, endTime);

        if (duration.toDays() == 0) {
            formattedDuration = duration.toHours() % 24
                    + ":" + duration.toMinutes() % 60
                    + ":" + duration.toSeconds() % 60;
        } else {
            formattedDuration = duration.toDays() + ":"
                    + duration.toHours() % 24 + ":"
                    + duration.toMinutes() % 60 + ":"
                    + duration.toSeconds() % 60;
        }

    }

    public boolean isStarted() {
        return isStarted;
    }

    public String getFormattedDuration() {
        return formattedDuration;
    }

}
