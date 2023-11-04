package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.InlineController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.ReplyController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.SendMessageController;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.EmojiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MasterController {

    private final TelegramData telegramData;
    private final ReplyController menuReplyController;
    private final ReplyController categoryReplyController;
    private final ReplyController taskReplyController;
    private ReplyController currentReplyController;
    private final InlineController categoryInlineController;
    private InlineController currentInlineController;
    private final SendMessageController menuMessage;
    private final SendMessageController taskMessage;
    private final SendMessageController categoryMessage;
    private final SendMessageController databaseMessage;
    private SendMessageController currentMessage;
    private final DatabaseController databaseController;
    private final CategoryController categoryController;

    @Autowired
    public MasterController(TelegramData telegramData,
                            @Qualifier("menuController") ReplyController menuReplyController,
                            @Qualifier("menuController") SendMessageController menuMessage,
                            @Qualifier("categoryController") ReplyController categoryReplyController,
                            @Qualifier("categoryController") SendMessageController categoryMessage,
                            CategoryController categoryController,
                            @Qualifier("categoryController") InlineController categoryInlineController,
                            @Qualifier("taskController") ReplyController taskReplyController,
                            @Qualifier("taskController") SendMessageController taskMessage,
                            @Qualifier("databaseController") SendMessageController databaseMessage,
                            DatabaseController databaseController) {
        this.telegramData = telegramData;
        this.currentReplyController = menuReplyController;
        this.menuReplyController = menuReplyController;
        this.taskReplyController = taskReplyController;
        this.categoryReplyController = categoryReplyController;
        this.currentInlineController = categoryInlineController;
        this.categoryInlineController = categoryInlineController;
        this.currentMessage = menuMessage;
        this.menuMessage = menuMessage;
        this.categoryMessage = categoryMessage;
        this.taskMessage = taskMessage;
        this.databaseMessage = databaseMessage;
        this.databaseController = databaseController;
        this.categoryController = categoryController;
    }

    public void setReplyController(Update update) {

        if (telegramData.getMessageText().equals(EmojiConstants.FOLDER_ICON + " Управление типами")) {
            currentReplyController = taskReplyController;
            currentMessage = taskMessage;
        }

        if (telegramData.getMessageText().equals(EmojiConstants.BACK_TO_MENU_ICON +
                " Вернуться в главное меню")) {
            currentReplyController = menuReplyController;
            currentMessage = menuMessage;
        }

        if (telegramData.getMessageText().equals(EmojiConstants.LIST_ICON + " Список категорий")) {
            currentReplyController = categoryReplyController;
            currentMessage = categoryMessage;
        }

        if (telegramData.getMessageText().equals(EmojiConstants.ACTIVE_CTG_ICON + " Активная категория")) {
            currentMessage = databaseMessage;
        }

        currentReplyController.handleCommands();
    }

    public void setCallbackController(Update update) {
        if (update.hasCallbackQuery()) {

            if (currentReplyController == categoryReplyController) {
                currentInlineController = categoryInlineController;
                currentInlineController.handleCommands(update);

                if (update.getCallbackQuery().getData().equals("menu_btn")) {
                    currentReplyController = menuReplyController;
                }

                if (update.getCallbackQuery().getData().equals("list_of_ctg")) {
                    databaseController.getCategory(update);
                }

            }

            if (update.getCallbackQuery().getData().equals("add_ctg")) {
                currentMessage = databaseMessage;
            }

        }
    }

    public void setDatabaseController(Update update) {
        if (categoryController.isAddTransaction() && update.hasMessage()) {
            databaseController.addCategory(update);
        }
    }

    public SendMessage sendMessage() {
        return currentMessage.sendMessage();
    }

    public EditMessageText editMessage() {
        return currentInlineController.sendEditMessage();
    }

}
