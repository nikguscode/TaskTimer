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
        createTaskBoardMenu();
    }

    private void createTaskBoardMenu() {
        taskBoard = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Активная категория");
        row.add("Список категорий");
        row.add("Вернуться в главное меню");

        keyboard.add(row);

        taskBoard.setKeyboard(keyboard);
        taskBoard.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getTaskBoard() {
        createTaskBoardMenu();
        return taskBoard;
    }

}
