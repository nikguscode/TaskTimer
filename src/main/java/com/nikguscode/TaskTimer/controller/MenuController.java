package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.state.MessageHandler;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.model.service.commands.LaunchCommands;
import com.nikguscode.TaskTimer.view.MenuBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Controller
public class MenuController implements MessageHandler {
    private final TelegramData telegramData;
    private final MenuBoard menuBoard;
    private final LaunchCommands launchCommands;
    private SendMessage sendMessage;

    @Autowired
    public MenuController(TelegramData telegramData,
                          MenuBoard menuBoard,
                          LaunchCommands launchCommands) {
        this.telegramData = telegramData;
        this.menuBoard = menuBoard;
        this.launchCommands = launchCommands;
    }

    public void handleCommands() {

        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        switch (telegramData.getMessageText()) {
            case "/start":
                sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                log.debug("Вывод клавиатуры меню");
                sendMessage.setText("Выберите категорию: ");
                break;

            case "\uD83D\uDCCA Статистика":
                break;

            case "\uD83D\uDE80 Начать работу":
                if (!launchCommands.isStarted()) {
                    launchCommands.start();
                    sendMessage.setText("✅ Таймер запущен.");
                    sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                } else {
                    sendMessage.setText("❌ Ошибка. Кажется, таймер уже запущен.");
                }
                break;

            case "\uD83C\uDFC1 Завершить работу":
                if (launchCommands.isStarted()) {
                    launchCommands.stop();
                    sendMessage.setText("✅ Таймер остановлен, время работы: "
                            + launchCommands.getFormattedDuration());
                    sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                } else {
                    sendMessage.setText("❌ Ошибка. Кажется, Вы ещё не запускали таймер.");
                }
                break;

            case ("Вернуться в главное меню"):
                sendMessage.setReplyMarkup(menuBoard.getMainMenu());
                sendMessage.setText("Успешно");
                break;

            default:
                log.warn("Не найдена команда в MenuController");
                sendMessage.setText("""
                        ❌ Кажется, указанная команда не найдена.\s

                        ❓ Используйте "/start\"""");
                break;
        }

    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

}
