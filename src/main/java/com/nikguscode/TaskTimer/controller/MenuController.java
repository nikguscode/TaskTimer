package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.model.service.TelegramMethods;
import com.nikguscode.TaskTimer.model.service.commands.LaunchCommands;
import com.nikguscode.TaskTimer.view.MenuBoard;
import com.nikguscode.TaskTimer.view.TaskBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Controller
public class MenuController {
    private final TelegramMethods telegramMethods;
    private final MenuBoard menuBoard;
    private final TaskBoard taskBoard;
    private final LaunchCommands launchCommands;
    private SendMessage sendMessage;

    @Autowired
    public MenuController(TelegramMethods telegramMethods,
                          TaskBoard taskBoard,
                          MenuBoard menuBoard,
                          LaunchCommands launchCommands) {
        this.telegramMethods = telegramMethods;
        this.menuBoard = menuBoard;
        this.taskBoard = taskBoard;
        this.launchCommands = launchCommands;
    }

    public void mainRedirecting() {
        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramMethods.getChatId());

        switch (telegramMethods.getMessageText()) {
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

                if (!launchCommands.isStarted()) {
                    launchCommands.start();
                    sendMessage.setText("✅ Таймер запущен.");
                    sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                } else {
                    sendMessage.setText("❌ Ошибка. Кажется, таймер уже запущен.");
                }

                break;

            case "Завершить работу":

                if (launchCommands.isStarted()) {
                    launchCommands.stop();
                    sendMessage.setText("✅ Таймер остановлен, время работы: "
                            + launchCommands.getFormattedDuration());
                    sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                } else {
                    sendMessage.setText("❌ Ошибка. Кажется, Вы ещё не запускали таймер.");
                }

                break;

            default:
                log.warn("Не найдена команда в MenuController");
                sendMessage.setText("❌ Кажется, указанная команда не найдена. " +
                        "\n       Используйте \"/start\"");
                break;
        }

    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

}
