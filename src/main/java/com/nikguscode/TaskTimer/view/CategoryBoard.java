package com.nikguscode.TaskTimer.view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class CategoryBoard {

    private ReplyKeyboardMarkup replyCategoryKeyboard;
    private InlineKeyboardButton inlineCategoryKeyboard;


    public CategoryBoard() {
        createReplyCategoryBoard();
        createInlineCategoryBoard();
    }

    private void createReplyCategoryBoard() {

        replyCategoryKeyboard = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("Добавить категорию");
        row.add("Просмотреть категории");
        row.add("В главное меню");

        keyboard.add(row);

        replyCategoryKeyboard.setKeyboard(keyboard);
        replyCategoryKeyboard.setResizeKeyboard(true);
    }

    private void createInlineCategoryBoard() {

//        InlineKeyboardMarkup categoryBoard = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton addCategoryButton = new InlineKeyboardButton();
//        addCategoryButton.setText("Добавить категорию");
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(categoryBoard);


    }




}
