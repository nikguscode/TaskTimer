package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.state.KeyboardCommandHandler;
import com.nikguscode.TaskTimer.controller.state.MenuState;
import com.nikguscode.TaskTimer.controller.state.MessageHandler;
import com.nikguscode.TaskTimer.controller.state.TaskState;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Service
public class MasterController {

    private TelegramData telegramData;
    private MenuController menuController;
    private MenuState menuState;
    private TaskController taskController;
    private TaskState taskState;
    private KeyboardCommandHandler currentBoard;
    private SendMessage sendMessage;
    private MessageHandler currentMessage;

    public MasterController(TelegramData telegramData,
                            MenuController menuController,
                            TaskController taskController,
                            MenuState menuState,
                            TaskState taskState) {
        this.telegramData = telegramData;
        this.taskController = taskController;
        this.menuController = menuController;
        this.currentMessage = menuController;
        this.currentBoard = new MenuState(menuController);
    }

    public void setController() {

        if (telegramData.getMessageText().equals("\uD83D\uDCC1 Управление типами")) {
            currentBoard = new TaskState(taskController);
            currentMessage = taskController;
        }

        if (telegramData.getMessageText().equals("Вернуться в главное меню")) {
            currentBoard = new MenuState(menuController);
            currentMessage = menuController;
        }

        currentBoard.handleCommands();

    }

    public SendMessage getSendMessage() {
        return currentMessage.sendMessage();
    }

}
