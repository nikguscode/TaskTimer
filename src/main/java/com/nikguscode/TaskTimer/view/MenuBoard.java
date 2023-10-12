package com.nikguscode.TaskTimer.view;

import com.nikguscode.TaskTimer.model.service.commands.LaunchCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuBoard {

    private final LaunchCommands launchCommands;
    private ReplyKeyboardMarkup mainMenu;

    @Autowired
    public MenuBoard(LaunchCommands launchCommands) {
        this.launchCommands = launchCommands;
        createMainMenu();
    }

    private void createMainMenu() {
        mainMenu = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("Статистика");
        row.add("Управление типами");

        if (!launchCommands.isStarted()) {
            row.remove("Завершить работу");
            row.add("Начать работу");
        } else {
            row.remove("Начать работу");
            row.add("Завершить работу");
        }

        keyboard.add(row);

        mainMenu.setKeyboard(keyboard);
        mainMenu.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getMainMenu() {
        createMainMenu();
        return mainMenu;
    }

}
