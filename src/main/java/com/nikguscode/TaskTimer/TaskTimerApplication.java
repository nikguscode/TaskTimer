package com.nikguscode.TaskTimer;

import com.nikguscode.TaskTimer.model.service.BotConfig;
import com.nikguscode.TaskTimer.model.service.BotConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication
public class TaskTimerApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(TaskTimerApplication.class, args);

		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		BotConfig botConfig = new BotConfig();
		botsApi.registerBot(new BotConnection(botConfig));

	}

}
