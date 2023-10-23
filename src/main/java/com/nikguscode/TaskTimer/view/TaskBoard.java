package com.nikguscode.TaskTimer.view;

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
        row.add("\uD83D\uDCCD Активная категория");
        row.add("\uD83D\uDCC4 Список категорий");
        row.add("\uD83C\uDFE0 Вернуться в главное меню");

        keyboard.add(row);

        taskBoard.setKeyboard(keyboard);
        taskBoard.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getBoard() {
        createBoard();
        return taskBoard;
    }

}
