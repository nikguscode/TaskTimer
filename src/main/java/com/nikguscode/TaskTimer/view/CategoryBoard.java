package com.nikguscode.TaskTimer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Controller
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

        InlineKeyboardButton addCategoryButton = new InlineKeyboardButton();
        addCategoryButton.setText("Добавить категорию");
        addCategoryButton.setCallbackData("add_ctg");

        InlineKeyboardButton showCategoriesButton = new InlineKeyboardButton();
        showCategoriesButton.setText("Список созданных категорий");
        showCategoriesButton.setCallbackData("list_of_ctg");

        keyboardButtonsRow1.add(addCategoryButton);
        keyboardButtonsRow1.add(showCategoriesButton);

        rowList.add(keyboardButtonsRow1);

        categoryBoard.setKeyboard(rowList);
    }

    public InlineKeyboardMarkup getBoard() {
        createBoard();
        return categoryBoard;
    }


}
