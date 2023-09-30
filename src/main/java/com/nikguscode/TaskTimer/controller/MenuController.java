package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.model.service.telegramConnection.BotConnection;
import com.nikguscode.TaskTimer.view.MenuBoard;
import com.nikguscode.TaskTimer.view.TaskBoard;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j2
@Controller
public class MenuController {
    private BotConnection botConnection;
    private final MenuBoard menuBoard;
    private final TaskBoard taskBoard;
    private SendMessage sendMessage;

    @Autowired
    public MenuController(@Lazy BotConnection botConnection, MenuBoard menuBoard, TaskBoard taskBoard) {
        this.botConnection = botConnection;
        this.menuBoard = menuBoard;
        this.taskBoard = taskBoard;
    }

    public void mainRedirecting(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
           sendMessage = new SendMessage();

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            sendMessage.setChatId(chatId);

            switch (messageText) {
                case "/start":
                    sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                    sendMessage.setText("Выберите категорию: ");
                    break;

                case "Статистика":
                    break;

                case "Управление категориями":
                    sendMessage.setReplyMarkup(taskBoard.getTaskBoard());
                    sendMessage.setText("Выбрано: Управление категориями");
                    break;

                case "Начать работу":
                    break;

                case "Завершить работу":
                    break;

                default:
                    System.out.println("Error");
                    break;
            }

        }
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

}
