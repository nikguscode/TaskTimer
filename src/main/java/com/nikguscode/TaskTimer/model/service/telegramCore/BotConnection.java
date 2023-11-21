package com.nikguscode.TaskTimer.model.service.telegramCore;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class BotConnection {

    private final BotData botData;

    public BotConnection(BotData botData) {
        this.botData = botData;
    }

    public void editMessageConnection(EditMessageText editMessage, Update update) {
        editMessage.setChatId(botData.getChatId());

        if (update.hasCallbackQuery()) {
            editMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        }
    }

}
