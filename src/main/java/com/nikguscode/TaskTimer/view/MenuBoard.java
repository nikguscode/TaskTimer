package com.nikguscode.TaskTimer.view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

public class MenuBoard {

    ReplyKeyboard menuBoard = new ReplyKeyboard() {

        @Override
        public void validate() throws TelegramApiValidationException {
            ReplyKeyboard.super.validate();

        }
    };

}
