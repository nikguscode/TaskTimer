package com.nikguscode.TaskTimer.view.keyboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryEditBoard {

    private InlineKeyboardMarkup categoryEditBoard;

    @Autowired
    public CategoryEditBoard() {
        createBoard();
    }

    private void createBoard() {
        categoryEditBoard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        InlineKeyboardButton addCategoryButton = new InlineKeyboardButton();
        addCategoryButton.setText("Редактировать категорию");
        addCategoryButton.setCallbackData("test_1");

        InlineKeyboardButton showCategoriesButton = new InlineKeyboardButton();
        showCategoriesButton.setText("Удалить категорию");
        showCategoriesButton.setCallbackData("test_2");

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Вернуться назад");
        backButton.setCallbackData("back_btn_1");

        keyboardButtonsRow1.add(addCategoryButton);
        keyboardButtonsRow1.add(showCategoriesButton);
        keyboardButtonsRow2.add(backButton);

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        categoryEditBoard.setKeyboard(rowList);
    }

    public InlineKeyboardMarkup getBoard() {
        createBoard();
        return categoryEditBoard;
    }

}
