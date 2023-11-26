package com.nikguscode.TaskTimer.view.keyboards;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryEditBoard {

    private InlineKeyboardMarkup categorySelectBoard;
    private InlineKeyboardMarkup categoryEditBoard;

    @Autowired
    public CategoryEditBoard() {
        createSelectBoard();
        createEditBoard();
    }

    private void createSelectBoard() {
        categorySelectBoard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        InlineKeyboardButton addCategoryButton = new InlineKeyboardButton();
        addCategoryButton.setText(PhraseConstants.EDIT_CATEGORY);
        addCategoryButton.setCallbackData(PhraseConstants.CB_EDIT_CATEGORY);

        InlineKeyboardButton showCategoriesButton = new InlineKeyboardButton();
        showCategoriesButton.setText(PhraseConstants.DELETE_CATEGORY);
        showCategoriesButton.setCallbackData(PhraseConstants.CB_DELETE_CATEGORY);

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText(PhraseConstants.BACK);
        backButton.setCallbackData(PhraseConstants.CB_BACK_2);

        keyboardButtonsRow1.add(addCategoryButton);
        keyboardButtonsRow1.add(showCategoriesButton);
        keyboardButtonsRow2.add(backButton);

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        categorySelectBoard.setKeyboard(rowList);
    }

    private void createEditBoard() {
        categoryEditBoard = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        InlineKeyboardButton editActiveCategory = new InlineKeyboardButton();
        editActiveCategory.setText(PhraseConstants.EDIT_ACTIVE_CATEGORY);
        editActiveCategory.setCallbackData(PhraseConstants.CB_EDIT_ACTIVE_CTG);

        InlineKeyboardButton editCategoryName = new InlineKeyboardButton();
        editCategoryName.setText(PhraseConstants.EDIT_CATEGORY_NAME);
        editCategoryName.setCallbackData(PhraseConstants.CB_EDIT_CATEGORY_NAME);

        InlineKeyboardButton editCategoryDescription = new InlineKeyboardButton();
        editCategoryDescription.setText(PhraseConstants.EDIT_CATEGORY_DESCRIPTION);
        editCategoryDescription.setCallbackData(PhraseConstants.CB_EDIT_CATEGORY_DESCRIPTION);

        InlineKeyboardButton backButton = new InlineKeyboardButton();
        backButton.setText(PhraseConstants.BACK);
        backButton.setCallbackData(PhraseConstants.CB_BACK_3);

        keyboardButtonsRow1.add(editCategoryName);
        keyboardButtonsRow1.add(editCategoryDescription);
        keyboardButtonsRow2.add(editActiveCategory);
        keyboardButtonsRow3.add(backButton);

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        categoryEditBoard.setKeyboard(rowList);
    }

    public InlineKeyboardMarkup getSelectingBoard() {
        createSelectBoard();
        return categorySelectBoard;
    }

    public InlineKeyboardMarkup getEditingBoard() {
        createEditBoard();
        return categoryEditBoard;
    }

}
