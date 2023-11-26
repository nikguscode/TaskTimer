package com.nikguscode.TaskTimer.model.service.telegramCore;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
public class BotResponse {

    // response without keyboard
    public void replyResponse(SendMessage sendMessage, String responseText) {
        sendMessage.setText(responseText);
    }

    public void replyResponse(SendMessage sendMessage, String responseText, ReplyKeyboardMarkup boardName) {
        sendMessage.setText(responseText);
        sendMessage.setReplyMarkup(boardName);
    }

    public void replyResponse(EditMessageText editMessage,
                              SendMessage sendMessage,
                              String responseEditText,
                              String responseText,
                              ReplyKeyboardMarkup boardName) {
        editMessage.setText(responseEditText);
        sendMessage.setText(responseText);
        sendMessage.setReplyMarkup(boardName);
    }

    public void inlineResponse(EditMessageText editMessage, String responseText) {
        editMessage.setText(responseText);
    }

    public void inlineResponse(SendMessage sendMessage, String responseText, InlineKeyboardMarkup boardName) {
        sendMessage.setText(responseText);
        sendMessage.setReplyMarkup(boardName);
    }

    public void inlineResponse(EditMessageText editMessage, String responseText, InlineKeyboardMarkup boardName) {
        editMessage.setText(responseText);
        editMessage.setReplyMarkup(boardName);
    }

}
