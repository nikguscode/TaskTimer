package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.state.*;
import com.nikguscode.TaskTimer.model.dal.AddCategory;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MasterController {

    private final TelegramData telegramData;
    private final MenuController menuController;
    private final CategoryController categoryController;
    private final TaskController taskController;
    private final AddCategory addCategory;
    private KeyboardCommandHandler currentBoard;
    private MessageHandler currentMessage;
    private EditMessageText editMessageText;
    private SendMessage sendMessage;

    @Autowired
    public MasterController(TelegramData telegramData,
                            MenuController menuController,
                            TaskController taskController,
                            CategoryController categoryController,
                            AddCategory addCategory) {
        this.telegramData = telegramData;
        this.taskController = taskController;
        this.menuController = menuController;
        this.categoryController = categoryController;
        this.currentMessage = menuController;
        this.currentBoard = new MenuState(menuController);
        this.addCategory = addCategory;
    }

    public void setController() {

        // TaskController
        if (telegramData.getMessageText().equals("\uD83D\uDCC1 Управление типами")) {
            currentBoard = new TaskState(taskController);
            currentMessage = taskController;
        }

        // MenuController
        if (telegramData.getMessageText().equals("\uD83C\uDFE0 Вернуться в главное меню")) {
            currentBoard = new MenuState(menuController);
            currentMessage = menuController;
        }

        // CategoryController
        if (telegramData.getMessageText().equals("\uD83D\uDCC4 Список категорий")) {
            currentBoard = new CategoryState(categoryController);
            currentMessage = categoryController;
        }

        currentBoard.handleCommands();

    }

    public void setCallbackController(Update update) {
        if (update.hasCallbackQuery()) {

            if (currentMessage == categoryController) {
                categoryController.handleCommands(update);
                editMessageText = categoryController.sendEditMessage();
            }

        }
    }

    public void setDatabaseController(Update update) {

        if (update.hasMessage()) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(telegramData.getChatId());

            // AddCategory
            if(categoryController.isAddCtgSelected()) {
                addCategory.transaction();
                categoryController.setAddCtgSelected(false); // указывает на add_ctg callback

                if (addCategory.isTransacted()) {
                    sendMessage.setText("Успешно");
                    currentBoard = new MenuState(menuController);
                    currentMessage = menuController;
                    addCategory.setTransacted(false); // указывает на проверку повторяющегося category_name
                } else {
                    sendMessage.setText("Данная категория уже создана, попробуйте указать другое имя");
                    categoryController.setAddCtgSelected(true); // повторный запуск сценария для создания категории
                }

            }

        }

    }

    public SendMessage getSendMessage() {
        return currentMessage.sendMessage();
    }

    public EditMessageText editMessageText() {
        return editMessageText;
    }

    public SendMessage sendMessage() {
        return sendMessage;
    }

}
