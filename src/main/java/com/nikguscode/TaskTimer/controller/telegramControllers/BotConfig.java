package com.nikguscode.TaskTimer.controller.telegramControllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class BotConfig {

    @Value("${telegram.bot.name}")
    private String botName;

    public String getBotName() {
        return botName;
    }

    @Value("${telegram.bot.token}")
    private String botToken;

    public String getBotToken() {
        return botToken;
    }

}
