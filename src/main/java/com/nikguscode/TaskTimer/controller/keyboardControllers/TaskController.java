package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.MenuBoard;
import com.nikguscode.TaskTimer.view.TaskBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Controller
public class TaskController implements com.nikguscode.TaskTimer.controller.keyboardControllers.Controller {

    private final TelegramData telegramData;
    private final MenuBoard menuBoard;
    private final TaskBoard taskBoard;
    private SendMessage sendMessage;

    @Autowired
    public TaskController(TelegramData telegramData,
                          MenuBoard menuBoard,
                          TaskBoard taskBoard) {
        this.telegramData = telegramData;
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

            case ("\uD83D\uDCCD Активная категория"):
                sendMessage.setText("Выбрана категория: активная категория");
                break;

            case ("\uD83D\uDCC1 Управление типами"):
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
