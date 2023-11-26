package com.nikguscode.TaskTimer.model.service.strategy.messageStrategy;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.service.crud.Update;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class UpdateName implements Transaction {

    private final BotData botData;
    private final CategoryRepository categoryRepository;
    private final Update update;
    private final SendMessage sendMessage;

    public UpdateName(CategoryRepository categoryRepository,
                      Update update,
                      BotData botData) {
        this.categoryRepository = categoryRepository;
        this.update = update;
        this.botData = botData;

        sendMessage = new SendMessage();
    }

    public SendMessage transaction(String currentEntry, String updatedEntry) {
        update.nameTransaction(
                currentEntry,
                botData.getChatId(),
                updatedEntry
        );

        sendMessage.setText(PhraseConstants.CATEGORY_NAME_EDITED);
        sendMessage.setChatId(botData.getChatId());
        return sendMessage;
    }

}
