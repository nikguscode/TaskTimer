package com.nikguscode.TaskTimer.model.service.strategy.messageStrategy;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.service.crud.Get;
import com.nikguscode.TaskTimer.model.service.crud.Update;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class DescriptionUpdater implements Transaction {

    private final BotData botData;
    private final CategoryRepository categoryRepository;
    private final Update update;
    private final Get get;
    private final SendMessage sendMessage;

    public DescriptionUpdater(CategoryRepository categoryRepository,
                              Update update,
                              Get get,
                              BotData botData) {
        this.categoryRepository = categoryRepository;
        this.update = update;
        this.botData = botData;
        this.get = get;

        sendMessage = new SendMessage();
    }

    public SendMessage transaction(String currentEntryName, String updatedEntry) {
        update.descriptionTransaction(
                currentEntryName,
                botData.getChatId(),
                updatedEntry
        );

        sendMessage.setText(PhraseConstants.CATEGORY_DESCRIPTION_EDITED);
        sendMessage.setChatId(botData.getChatId());
        return sendMessage;
    }





}
