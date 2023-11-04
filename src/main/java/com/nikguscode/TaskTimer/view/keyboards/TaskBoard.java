package com.nikguscode.TaskTimer.view.keyboards;

import com.nikguscode.TaskTimer.view.EmojiConstants;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskBoard {

    private ReplyKeyboardMarkup taskBoard;

    public TaskBoard() {
        createBoard();
    }

    private void createBoard() {
        taskBoard = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add(EmojiConstants.ACTIVE_CTG_ICON + " Активная категория");
        row.add(EmojiConstants.LIST_ICON + " Список категорий");
        row.add(EmojiConstants.BACK_TO_MENU_ICON + " Вернуться в главное меню");

        keyboard.add(row);

        taskBoard.setKeyboard(keyboard);
        taskBoard.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getBoard() {
        createBoard();
        return taskBoard;
    }

}
