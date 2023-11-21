package com.nikguscode.TaskTimer.view.keyboards;

import com.nikguscode.TaskTimer.model.PhraseConstants;
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
        row.add(PhraseConstants.STATISTICS);
        row.add(PhraseConstants.TYPE_MANAGEMENT);

        if (!launch.isStarted()) {
            row.remove(PhraseConstants.STOP_TIMER);
            row.add(PhraseConstants.START_TIMER);
        } else {
            row.remove(PhraseConstants.START_TIMER);
            row.add(PhraseConstants.STOP_TIMER);
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
