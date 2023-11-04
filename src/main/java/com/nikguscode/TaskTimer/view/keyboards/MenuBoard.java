package com.nikguscode.TaskTimer.view.keyboards;

import com.nikguscode.TaskTimer.model.service.commands.Launch;
import com.nikguscode.TaskTimer.view.EmojiConstants;
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
        row.add(EmojiConstants.STATS_ICON + " Статистика");
        row.add(EmojiConstants.FOLDER_ICON + " Управление типами");

        if (!launch.isStarted()) {
            row.remove(EmojiConstants.END_ICON + " Завершить работу");
            row.add(EmojiConstants.START_ICON + " Начать работу");
        } else {
            row.remove(EmojiConstants.START_ICON + " Начать работу");
            row.add(EmojiConstants.END_ICON + " Завершить работу");
        }

        keyboard.add(row);

        mainMenu.setKeyboard(keyboard);
        mainMenu.setResizeKeyboard(true);
    }

    public ReplyKeyboardMarkup getBoard() {
        createBoard(); // update keyboard
        return mainMenu;
    }

}
