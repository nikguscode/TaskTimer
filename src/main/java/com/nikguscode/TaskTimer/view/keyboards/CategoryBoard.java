package com.nikguscode.TaskTimer.view.keyboards;

import com.nikguscode.TaskTimer.model.PhraseConstants;
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
        addCategoryButton.setText(PhraseConstants.ADD_CATEGORY);
        addCategoryButton.setCallbackData(PhraseConstants.CB_ADD_CATEGORY);

        InlineKeyboardButton showCategoriesButton = new InlineKeyboardButton();
        showCategoriesButton.setText(PhraseConstants.LIST_OF_CATEGORIES);
        showCategoriesButton.setCallbackData(PhraseConstants.CB_CATEGORY_LIST);

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText(PhraseConstants.BACK_TO_MENU);
        backButton.setCallbackData(PhraseConstants.CB_BACK_TO_MENU);

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
