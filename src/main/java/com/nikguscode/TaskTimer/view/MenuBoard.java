package com.nikguscode.TaskTimer.view;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuBoard {

    public ReplyKeyboardMarkup mainMenu = new ReplyKeyboardMarkup();

    public MenuBoard() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Управление категориями");
        row.add("Начать работу");
        row.add("Завершить работу");

        keyboard.add(row);

        mainMenu.setKeyboard(keyboard);
        mainMenu.setResizeKeyboard(true);
    }

}
