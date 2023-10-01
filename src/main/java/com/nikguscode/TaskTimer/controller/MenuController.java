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
            sendMessage = new SendMessage();

            String messageText = update.getMessage().getText();
            log.info("Получено сообщение: " + messageText);
            long chatId = update.getMessage().getChatId();

            sendMessage.setChatId(chatId);

            switch (messageText) {
                case "/start":
                    sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                    log.debug("Вывод клавиатуры меню");
                    sendMessage.setText("Выберите категорию: ");
                    break;

                case "Статистика":
                    break;

                case "Управление типами":
                    sendMessage.setReplyMarkup(taskBoard.getTaskBoard());
                    log.debug("Вывод клавиатуры управления типами");
                    sendMessage.setText("Выбрано: Управление типами");
                    break;

                case "Начать работу":
                    break;

                case "Завершить работу":
                    break;

                default:
                    log.warn("Не найдена команда в MenuController");
                    sendMessage = null;
                    break;
            }
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

}
