package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.InlineController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.ReplyController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.SendMessageController;
import com.nikguscode.TaskTimer.model.dal.Add;
import com.nikguscode.TaskTimer.model.dal.GetCategory;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.view.EmojiConstants;
import com.nikguscode.TaskTimer.view.keyboards.CategoryBoard;
import com.nikguscode.TaskTimer.view.keyboards.CategoryEditBoard;
import com.nikguscode.TaskTimer.view.keyboards.CategoryListBoard;
import com.nikguscode.TaskTimer.view.keyboards.MenuBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * This class contains a keyboard controller, where the user can choose to add a category or view created
 * categories.
 */

@Controller
@Getter
@Setter
@Slf4j
public class CategoryController implements ReplyController, InlineController, SendMessageController {

    private final TelegramData telegramData;
    private final Add add;
    private final CategoryBoard categoryBoard;
    private final CategoryListBoard categoryListBoard;
    private final GetCategory getCategory;
    private final MenuBoard menuBoard;
    private CategoryFilter categoryFilter;
    private boolean addTransaction;
    private boolean listTransaction;
    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private CategoryEditBoard categoryEditBoard;

    @Autowired
    public CategoryController(TelegramData telegramData,
                              GetCategory getCategory,
                              CategoryFilter categoryFilter,
                              CategoryEditBoard categoryEditBoard,
                              Add add,
                              CategoryBoard categoryBoard,
                              CategoryListBoard categoryListBoard,
                              MenuBoard menuBoard) {
        this.telegramData = telegramData;
        this.categoryEditBoard = categoryEditBoard;
        this.getCategory = getCategory;
        this.categoryFilter = categoryFilter;
        this.add = add;
        this.categoryBoard = categoryBoard;
        this.categoryListBoard = categoryListBoard;
        this.menuBoard = menuBoard;
    }

    @Override
    public void handleCommands() {
        sendMessage = new SendMessage();
        sendMessage.setChatId(telegramData.getChatId());

        if (telegramData.getMessageText().equals(EmojiConstants.LIST_ICON + " Список категорий")) { // icon = 📄
            sendMessage.setText("Выбрана категория: список категорий");
            sendMessage.setReplyMarkup(categoryBoard.getBoard());
        }

    }

    public void sendSelectedCategory() {
        editMessageText = new EditMessageText();
        editMessageText.setChatId(telegramData.getChatId());
        editMessageText.setMessageId(telegramData.getMessageId());

        editMessageText.setText("Выберите операцию:");
        editMessageText.setReplyMarkup(categoryEditBoard.getBoard());
    }

    @Override
    public void handleCommands(Update update) {

        if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            editMessageText = new EditMessageText();
            editMessageText.setChatId(telegramData.getChatId());
            editMessageText.setMessageId(telegramData.getMessageId());

            switch (update.getCallbackQuery().getData()) {
                case "add_ctg":
                    editMessageText.setText("Введите: \"Название категории | описание категории\" через \" | \" \n" +
                            "Пример: Написание кода | Отсчитывает время написания калькулятора");
                    addTransaction = true;
                    break;

                case "list_of_ctg":
                    listTransaction = true;
                    break;

                case "next_page":

                    break;

                case "previous_page":

                    break;

                case "menu_btn":
                    editMessageText.setText("Успешно");
                    sendMessage.setReplyMarkup(menuBoard.getBoard());
                    break;

                default:
                    log.warn("Не найдена команда в CategoryController");
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

    @Override
    public EditMessageText sendEditMessage() {
        return editMessageText;
    }

}
