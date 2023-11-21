package com.nikguscode.TaskTimer.view.keyboards;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryListBoard {
    private InlineKeyboardMarkup categoryListBoard;
    private final CategoryFilter categoryFilter;

    @Autowired
    public CategoryListBoard(CategoryFilter categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    private void createBoard() {
        categoryListBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        if (categoryFilter.getCurrentCategories() != null) {
            for (String currentCategory : categoryFilter.getCurrentCategories()) {
                InlineKeyboardButton categoryButton = new InlineKeyboardButton();

                categoryButton.setText(currentCategory);
                categoryButton.setCallbackData(currentCategory);

                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(categoryButton);
                rowList.add(row);
            }
        }

        rowList.add(createNavigationRow());
        rowList.add(createBackButtonRow());

        categoryListBoard.setKeyboard(rowList);
    }


    private List<InlineKeyboardButton> createNavigationRow() {
        InlineKeyboardButton previousPage = new InlineKeyboardButton();
        previousPage.setText(PhraseConstants.PREVIOUS_PAGE);
        previousPage.setCallbackData(PhraseConstants.CB_PREVIOUS_PAGE);

        InlineKeyboardButton nextPage = new InlineKeyboardButton();
        nextPage.setText(PhraseConstants.NEXT_PAGE);
        nextPage.setCallbackData(PhraseConstants.CB_NEXT_PAGE);

        List<InlineKeyboardButton> row = new ArrayList<>();

        if (categoryFilter.getCurrentPage() != 1 && categoryFilter.getTotalPages() != 1) {
            row.add(previousPage);
        }

        if (categoryFilter.getCurrentPage() != categoryFilter.getTotalPages() && categoryFilter.getTotalPages() != 1) {
            row.add(nextPage);
        }

        return row;
    }

    private List<InlineKeyboardButton> createBackButtonRow() {
        InlineKeyboardButton menuButton = new InlineKeyboardButton();
        menuButton.setText(PhraseConstants.BACK);
        menuButton.setCallbackData(PhraseConstants.CB_BACK_1);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(menuButton);

        return row;
    }

    public InlineKeyboardMarkup getBoard() {
        createBoard();
        return categoryListBoard;
    }

}
