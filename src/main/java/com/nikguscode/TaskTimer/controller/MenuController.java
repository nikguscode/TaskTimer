package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.view.MenuBoard;
import com.nikguscode.TaskTimer.view.TaskBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Controller
public class MenuController {
    private final MenuBoard menuBoard;
    private final TaskBoard taskBoard;
    private SendMessage sendMessage;

    @Autowired
    public MenuController(MenuBoard menuBoard, TaskBoard taskBoard) {
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

                case "Управление типами":
                    sendMessage.setReplyMarkup(taskBoard.getTaskBoard());
                    sendMessage.setText("Выбрано: Управление типами");
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
