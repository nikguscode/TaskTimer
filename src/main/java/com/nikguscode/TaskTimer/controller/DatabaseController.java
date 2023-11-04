package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.SendMessageController;
import com.nikguscode.TaskTimer.model.dal.Add;
import com.nikguscode.TaskTimer.model.dal.GetCategory;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DatabaseController implements SendMessageController {

    private TelegramData telegramData;
    private GetCategory getCategory;
    private CategoryController categoryController;
    private Add add;
    private SendMessage sendMessage;

    @Autowired
    public DatabaseController(TelegramData telegramData,
                              GetCategory getCategory,
                              CategoryController categoryController,
                              Add add) {
        this.telegramData = telegramData;
        this.getCategory = getCategory;
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

    public void getInfo() {
        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        getCategory.getActiveCategory();

        if (!getCategory.getResult().isEmpty()) {
            StringBuilder messageText = new StringBuilder("Активная категория: ");
            messageText.append(getCategory.getResult().get(0));
            sendMessage.setText(messageText.toString());
        } else {
            sendMessage.setText("У вас нет активной категории.");
        }
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

}
