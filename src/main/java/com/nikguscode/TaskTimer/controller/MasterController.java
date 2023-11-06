package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.InlineController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.ReplyController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.SendMessageController;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.EmojiConstants;
import com.nikguscode.TaskTimer.view.keyboards.CategoryEditBoard;
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
    private CategoryFilter categoryFilter;
    private CategoryEditBoard categoryEditBoard;

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
                            DatabaseController databaseController,
                            CategoryFilter categoryFilter) {
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
        this.categoryFilter = categoryFilter;
    }

    public void setReplyController() {

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

            if (update.getCallbackQuery().getData().equals("menu_btn")) {
                currentReplyController = menuReplyController;
            }

            if (update.getCallbackQuery().getData().equals("list_of_ctg")) {
                currentMessage = databaseMessage;
                currentInlineController = databaseController;
                categoryController.handleCommands(update);
                databaseController.getCategory();
            }

            if (categoryFilter.getCurrentCategories() != null) {
                int length = categoryFilter.getCurrentCategories().size(); // get size of current page

                for (int currentIndex = 1; currentIndex < length; currentIndex++) {

                    if (update.getCallbackQuery().getData().equals("category_btn_" + currentIndex)) {
                        currentInlineController = categoryInlineController;
                        categoryController.sendSelectedCategory();
                    }

                    currentIndex++;
                }
            }

            if (update.getCallbackQuery().getData().equals("add_ctg")) {
                categoryController.handleCommands(update);
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
