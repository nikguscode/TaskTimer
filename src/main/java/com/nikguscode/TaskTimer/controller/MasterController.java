package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.Controller;
import com.nikguscode.TaskTimer.model.dal.AddCategory;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MasterController {

    private final TelegramData telegramData;
    private final Controller menuController;
    private final Controller categoryController;
    private final Controller taskController;
    private final AddCategory addCategory;
    private Controller currentController;
    private SendMessage sendMessage;

    @Autowired
    public MasterController(TelegramData telegramData,
                            @Qualifier("menuController") Controller menuController,
                            @Qualifier("categoryController") Controller categoryController,
                            @Qualifier("taskController") Controller taskController,
                            AddCategory addCategory) {
        this.telegramData = telegramData;
        this.menuController = menuController;
        this.currentController = menuController;
        this.taskController = taskController;
        this.categoryController = categoryController;
        this.addCategory = addCategory;
    }

    public void setController() {

        if (telegramData.getMessageText().equals("\uD83D\uDCC1 Управление типами")) {
            currentController = taskController;
        }

        if (telegramData.getMessageText().equals("\uD83C\uDFE0 Вернуться в главное меню")) {
            currentController = menuController;
        }

        if (telegramData.getMessageText().equals("\uD83D\uDCC4 Список категорий")) {
            currentController = categoryController;
        }

        currentController.handleCommands();
    }

    public void setCallbackController(Update update) {
        if (update.hasCallbackQuery()) {

            if (currentController == categoryController) {
                currentController.handleCommands(update);
            }

        }
    }

    public void setDatabaseController(Update update) {

        if (update.hasMessage()) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(telegramData.getChatId());

            if (currentController instanceof CategoryController) {
                CategoryController categoryController = (CategoryController) currentController;
                if (categoryController.isAddCtgSelected()) {
                    addCategory.transaction();
                    categoryController.setAddCtgSelected(false); // указывает на add_ctg callback

                    if (addCategory.isTransacted()) {
                        currentController = menuController;
                        sendMessage.setText("Успешно");
                        addCategory.setTransacted(false); // указывает на проверку повторяющегося category_name
                    } else {
                        sendMessage.setText("Данная категория уже создана, попробуйте указать другое имя");
                        categoryController.setAddCtgSelected(true); // повторный запуск сценария для создания категории
                    }

                }
            }

        }

    }

    public SendMessage getSendMessage() {
        return currentController.sendMessage();
    }

    public EditMessageText editMessageText() {
        return currentController.sendEditMessage();
    }

    public SendMessage sendMessage() {
        return sendMessage;
    }

}
