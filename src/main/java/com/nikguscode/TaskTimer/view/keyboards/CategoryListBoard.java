package com.nikguscode.TaskTimer.view.keyboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryListBoard {
    private final InlineKeyboardMarkup categoryListBoard;

    private final List<String> categoryTexts = List.of(
            "Категория 1",
            "Категория 2",
            "Категория 3",
            "Категория 4",
            "Категория 5",
            "Категория 6"
    );

    private final List<String> categoryCallbackData = List.of(
            "first_category",
            "second_category",
            "third_category",
            "fourth_category",
            "fifth_category",
            "sixth_category"
    );

    @Autowired
    public CategoryListBoard() {
        categoryListBoard = createBoard();
    }

    private InlineKeyboardMarkup createBoard() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (int i = 0; i < categoryTexts.size(); i++) {
            InlineKeyboardButton categoryButton = new InlineKeyboardButton();
            categoryButton.setText(categoryTexts.get(i));
            categoryButton.setCallbackData(categoryCallbackData.get(i));

            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(categoryButton);
            rowList.add(row);
        }

        rowList.add(createNavigationRow());
        rowList.add(createMenuButtonRow());

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(rowList);

        return markup;
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
        return categoryListBoard;
    }
}
