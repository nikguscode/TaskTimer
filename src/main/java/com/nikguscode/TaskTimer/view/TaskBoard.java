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
        taskBoard = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Список категорий");
        row.add("Добавить категорию");

        taskBoard.setKeyboard(keyboard);
        taskBoard.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getTaskBoard() {
        return taskBoard;
    }

}
