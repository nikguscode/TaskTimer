package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.state.MessageHandler;
import com.nikguscode.TaskTimer.model.dal.AddCategory;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.CategoryBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

/**
 * This class contains a keyboard controller, where the user can choose to add a category or view created
 * categories.
 */

@Controller
@Getter
@Setter
@Slf4j
public class CategoryController implements MessageHandler {

    private final TelegramData telegramData;
    private final AddCategory addCategory;
    private final CategoryBoard categoryBoard;
    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private boolean isAddCtgSelected;

    @Autowired
    public CategoryController(TelegramData telegramData,
                              AddCategory addCategory,
                              CategoryBoard categoryBoard) {
        this.telegramData = telegramData;
        this.addCategory = addCategory;
        this.categoryBoard = categoryBoard;
    }

    public void handleCommands() {
        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        if (telegramData.getMessageText().equals("\uD83D\uDCC4 Список категорий")) {
            sendMessage.setText("Выбрана категория: список категорий");
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
            sendMessage.setReplyMarkup(categoryBoard.getBoard());
        }

    }

    public void handleCommands(Update update) {

        if (update.hasCallbackQuery() && telegramData.getCallbackData() != null) {
            editMessageText = new EditMessageText();
            editMessageText.setChatId(telegramData.getChatId());
            editMessageText.setMessageId(telegramData.getMessageId());

            switch (telegramData.getCallbackData()) {
                case "add_ctg":
                    editMessageText.setText("Введите: \"Название категории | описание категории\" через \" | \" \n" +
                            "Пример: Написание кода | Отсчитывает время написания калькулятора");
                    isAddCtgSelected = true;
                    break;

                case "list_of_ctg":
                    editMessageText.setText("Список категорий: ");
                    break;

                default:
                    log.warn("Не найдена команда в TaskController");
                    sendMessage.setText("""
                            ❌ Кажется, указанная команда не найдена.\s
                            ❓ Используйте "/start\"""");
                    break;
            }

        }
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

    public EditMessageText sendEditMessage() {
        return editMessageText;
    }

}
