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
        row.add("\uD83D\uDCCA Статистика");
        row.add("\uD83D\uDCC1 Управление типами");

        if (!launchCommands.isStarted()) {
            row.remove("\uD83C\uDFC1 Завершить работу");
            row.add("\uD83D\uDE80 Начать работу");
        } else {
            row.remove("\uD83D\uDE80 Начать работу");
            row.add("\uD83C\uDFC1 Завершить работу");
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
