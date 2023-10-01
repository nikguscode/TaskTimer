package com.nikguscode.TaskTimer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuBoard {

    private final ReplyKeyboardMarkup mainMenu;

    @Autowired
    public MenuBoard() {
        mainMenu = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Статистика");
        row.add("Управление типами");
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
