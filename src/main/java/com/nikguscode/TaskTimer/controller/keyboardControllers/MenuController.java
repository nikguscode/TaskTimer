package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.model.service.commands.Launch;
import com.nikguscode.TaskTimer.view.MenuBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Controller
public class MenuController implements com.nikguscode.TaskTimer.controller.keyboardControllers.Controller {
    private final TelegramData telegramData;
    private final MenuBoard menuBoard;
    private final Launch launch;
    private SendMessage sendMessage;

    @Autowired
    public MenuController(TelegramData telegramData,
                          MenuBoard menuBoard,
                          Launch launch) {
        this.telegramData = telegramData;
        this.menuBoard = menuBoard;
        this.launch = launch;
    }

    public void handleCommands() {

        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        switch (telegramData.getMessageText()) {
            case "/start":
                sendMessage.setReplyMarkup(menuBoard.getBoard());
                log.debug("Вывод клавиатуры меню");
                sendMessage.setText("Выберите категорию: ");
                break;

            case "\uD83D\uDCCA Статистика":
                break;

            case "\uD83D\uDE80 Начать работу":
                if (!launch.isStarted()) {
                    launch.start();
                    sendMessage.setText("✅ Таймер запущен.");
                    sendMessage.setReplyMarkup(menuBoard.getBoard());
                } else {
                    sendMessage.setText("❌ Ошибка. Кажется, таймер уже запущен.");
                }
                break;

            case "\uD83C\uDFC1 Завершить работу":
                if (launch.isStarted()) {
                    launch.stop();
                    sendMessage.setText("✅ Таймер остановлен, время работы: "
                            + launch.getFormattedDuration());
                    sendMessage.setReplyMarkup(menuBoard.getBoard());
                } else {
                    sendMessage.setText("❌ Ошибка. Кажется, Вы ещё не запускали таймер.");
                }
                break;

            case ("\uD83C\uDFE0 Вернуться в главное меню"):
                sendMessage.setReplyMarkup(menuBoard.getBoard());
                sendMessage.setText("Успешно");
                break;

            default:
                log.warn("Не найдена команда в MenuController, либо не доступна в текущем сценарии");
                sendMessage.setText("""
                        ❌ Кажется, указанная команда не найдена.\s
                        ❓ Используйте "/start\"""");
                break;
        }

    }

    public void handleCommands(Update update) {
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }
    public EditMessageText sendEditMessage() {
        return null;
    }

}
