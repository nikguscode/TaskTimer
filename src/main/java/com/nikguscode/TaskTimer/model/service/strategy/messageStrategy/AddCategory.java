package com.nikguscode.TaskTimer.model.service.strategy.messageStrategy;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.crud.Add;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class AddCategory {

    private final BotData botData;
    private final Add add;
    private final SendMessage sendMessage;

    public AddCategory(BotData botData,
                       Add add) {
        this.botData = botData;
        this.add = add;

        sendMessage = new SendMessage();
    }

    public SendMessage transaction() {

        add.addCategory(
                botData.getCategoryName(),
                botData.getCategoryDescription(),
                botData.getChatId()
        );

        sendMessage.setText(PhraseConstants.CREATED_CATEGORY);
        sendMessage.setChatId(botData.getChatId());
        return sendMessage;
    }

}
