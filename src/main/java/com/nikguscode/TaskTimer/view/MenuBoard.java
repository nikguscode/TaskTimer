package com.nikguscode.TaskTimer.view;

import com.nikguscode.TaskTimer.model.service.commands.Launch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuBoard {

    private final Launch launch;
    private ReplyKeyboardMarkup mainMenu;

    @Autowired
    public MenuBoard(Launch launch) {
        this.launch = launch;
        createBoard();
    }

    private void createBoard() {
        mainMenu = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDCCA Статистика");
        row.add("\uD83D\uDCC1 Управление типами");

        if (!launch.isStarted()) {
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

    public ReplyKeyboardMarkup getBoard() {
        createBoard();
        return mainMenu;
    }

}
