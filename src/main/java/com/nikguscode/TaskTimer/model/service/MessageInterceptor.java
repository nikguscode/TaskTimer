package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.crud.AddCategory;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Getter
public class MessageInterceptor {

    private final BotData botData;
    private final AddCategory addCategory;
    private final Logging logging;
    private final SendMessage sendMessage;

    public MessageInterceptor(BotData botData,
                              AddCategory addCategory,
                              Logging logging) {
        this.botData = botData;
        this.addCategory = addCategory;
        this.logging = logging;

        sendMessage = new SendMessage();
    }

    public void interceptMessage(Update update) {

        if (update.getMessage() != null && update.getMessage().getText() != null) {
            String interceptedMessage = update.getMessage().getText();
            botData.getFormattedCategory(interceptedMessage);
            logging.debugMessage(interceptedMessage);
        }

        addCategoryTransaction();
        sendMessage.setText(PhraseConstants.CREATED_CATEGORY);
    }

    private void addCategoryTransaction() {
        addCategory.addCategory(
                botData.getCategoryName(),
                botData.getCategoryDescription(),
                botData.getChatId()
        );
    }
}
