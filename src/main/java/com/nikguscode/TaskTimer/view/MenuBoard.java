package com.nikguscode.TaskTimer.view;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuBoard {

    private ReplyKeyboardMarkup mainMenu;

    public MenuBoard() {
        mainMenu = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Управление категориями");
        row.add("Начать работу");
        row.add("Завершить работу");

        keyboard.add(row);

        mainMenu.setKeyboard(keyboard);
        mainMenu.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getMainMenu() {
        return mainMenu;
    }

}
