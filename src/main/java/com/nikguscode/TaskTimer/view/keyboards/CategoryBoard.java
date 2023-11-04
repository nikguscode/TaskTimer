package com.nikguscode.TaskTimer.view.keyboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryBoard {

    private InlineKeyboardMarkup categoryBoard;

    @Autowired
    public CategoryBoard() {
        createBoard();
    }

    private void createBoard() {
        categoryBoard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        InlineKeyboardButton addCategoryButton = new InlineKeyboardButton();
        addCategoryButton.setText("Добавить категорию");
        addCategoryButton.setCallbackData("add_ctg");

        InlineKeyboardButton showCategoriesButton = new InlineKeyboardButton();
        showCategoriesButton.setText("Список созданных категорий");
        showCategoriesButton.setCallbackData("list_of_ctg");

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText("Вернуться назад");
        backButton.setCallbackData("back_btn");

        keyboardButtonsRow1.add(addCategoryButton);
        keyboardButtonsRow1.add(showCategoriesButton);
        keyboardButtonsRow2.add(backButton);

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        categoryBoard.setKeyboard(rowList);
    }

    public InlineKeyboardMarkup getBoard() {
        createBoard();
        return categoryBoard;
    }


}
