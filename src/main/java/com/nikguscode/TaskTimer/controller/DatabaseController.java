package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.InlineController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.SendMessageController;
import com.nikguscode.TaskTimer.model.dal.Add;
import com.nikguscode.TaskTimer.model.dal.GetCategory;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.keyboards.CategoryListBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatabaseController implements SendMessageController, InlineController {

    private TelegramData telegramData;
    private GetCategory getCategory;
    private CategoryFilter categoryFilter;
    private CategoryController categoryController;
    private CategoryListBoard categoryListBoard;
    private Add add;
    private SendMessage sendMessage;
    private EditMessageText editMessageText;

    @Autowired
    public DatabaseController(TelegramData telegramData,
                              GetCategory getCategory,
                              CategoryFilter categoryFilter,
                              CategoryController categoryController,
                              CategoryListBoard categoryListBoard,
                              Add add) {
        this.telegramData = telegramData;
        this.categoryFilter = categoryFilter;
        this.getCategory = getCategory;
        this.categoryListBoard = categoryListBoard;
        this.categoryController = categoryController;
        this.add = add;
    }

    public void addCategory(Update update) {
        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        if (categoryController.isAddTransaction()) {
            add.addCategory(update);
            categoryController.setAddTransaction(false);

            if (add.isTransacted()) {
                sendMessage.setText("Успешно");
                add.setTransacted(false); // указывает на проверку повторяющегося category_name
            } else {
                sendMessage.setText("Данная категория уже создана, попробуйте указать другое имя");
            }

        }

    }

    public void getCategory() {
        editMessageText = new EditMessageText();
        editMessageText.setChatId(telegramData.getChatId());
        editMessageText.setMessageId(telegramData.getMessageId());

        if (categoryController.isListTransaction()) {
            getCategory.getAllCategories();
            categoryFilter.getCategories();
            categoryController.setListTransaction(false);

            if (getCategory.isTransacted()) {
                editMessageText.setText("Список и управление категориями: ");
                editMessageText.setReplyMarkup(categoryListBoard.getBoard());
                getCategory.setTransacted(false); // указывает на проверку повторяющегося category_name
            } else {
                editMessageText.setText("Данная категория уже создана, попробуйте указать другое имя");
            }

        }

    }

    public void getInfo() {
        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        getCategory.getActiveCategory();

        if (!getCategory.getResult().isEmpty()) {
            String messageText = "Активная категория: " + getCategory.getResult().get(0);
            sendMessage.setText(messageText);
        } else {
            sendMessage.setText("У вас нет активной категории.");
        }
    }

    @Override
    public void handleCommands(Update update) {
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

    @Override
    public EditMessageText sendEditMessage() {
        return editMessageText;
    }

}
