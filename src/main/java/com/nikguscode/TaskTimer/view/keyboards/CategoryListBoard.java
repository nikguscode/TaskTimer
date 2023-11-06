package com.nikguscode.TaskTimer.view.keyboards;

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
        createBoard();
    }

    private void createBoard() {
        categoryListBoard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        if (categoryFilter.getCurrentCategories() != null) {
            int count = 1;

            for (String currentCategory : categoryFilter.getCurrentCategories()) {
                InlineKeyboardButton categoryButton = new InlineKeyboardButton();

                categoryButton.setText(currentCategory);
                categoryButton.setCallbackData("category_btn_" + count);

                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(categoryButton);
                rowList.add(row);

                count ++;
            }
        }

        rowList.add(createNavigationRow());
        rowList.add(createMenuButtonRow());

        categoryListBoard.setKeyboard(rowList);
    }


    private List<InlineKeyboardButton> createNavigationRow() {
        InlineKeyboardButton previousPage = new InlineKeyboardButton();
        previousPage.setText("Назад");
        previousPage.setCallbackData("previous_page");

        InlineKeyboardButton nextPage = new InlineKeyboardButton();
        nextPage.setText("Вперёд");
        nextPage.setCallbackData("next_page");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(previousPage);
        row.add(nextPage);

        return row;
    }

    private List<InlineKeyboardButton> createMenuButtonRow() {
        InlineKeyboardButton menuButton = new InlineKeyboardButton();
        menuButton.setText("Вернуться в главное меню");
        menuButton.setCallbackData("menu_btn");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(menuButton);

        return row;
    }

    public InlineKeyboardMarkup getBoard() {
        createBoard(); // update board
        return categoryListBoard;
    }

}
