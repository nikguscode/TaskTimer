package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.DatabaseController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.ReplyController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.SendMessageController;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.keyboards.MenuBoard;
import com.nikguscode.TaskTimer.view.keyboards.TaskBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Controller
public class TaskController implements ReplyController, SendMessageController {

    private final TelegramData telegramData;
    private final DatabaseController databaseController;
    private final MenuBoard menuBoard;
    private final TaskBoard taskBoard;
    private SendMessage sendMessage;

    @Autowired
    public TaskController(TelegramData telegramData,
                          DatabaseController databaseController,
                          MenuBoard menuBoard,
                          TaskBoard taskBoard) {
        this.telegramData = telegramData;
        this.databaseController = databaseController;
        this.menuBoard = menuBoard;
        this.taskBoard = taskBoard;
    }

    public void handleCommands() {

        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        switch (telegramData.getMessageText()) {
            case "/start":
                sendMessage.setReplyMarkup(menuBoard.getBoard());
                sendMessage.setText("Выберите категорию: ");
                break;

            case ("\uD83D\uDCCD" + " Активная категория"): // icon = 📍
                databaseController.getInfo();
                break;

            case ("\uD83D\uDCC1" + " Управление типами"): // icon = 📁
                sendMessage.setReplyMarkup(taskBoard.getBoard());
                sendMessage.setText("Выбрана категория: управление типами");
                break;

            default:
                log.warn("Не найдена команда в TaskController");
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
