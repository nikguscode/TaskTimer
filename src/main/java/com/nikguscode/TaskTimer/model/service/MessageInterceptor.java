package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryEditController;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.strategy.messageStrategy.AddCategory;
import com.nikguscode.TaskTimer.model.service.strategy.messageStrategy.Transaction;
import com.nikguscode.TaskTimer.model.service.strategy.messageStrategy.UpdateDescription;
import com.nikguscode.TaskTimer.model.service.strategy.messageStrategy.UpdateName;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Service
@Getter
public class MessageInterceptor {

    private final BotData botData;
    private final Transaction transaction;
    private final AddCategory addCategory;
    private final Logging logging;
    private final SendMessage sendMessage;
    private final HashMap<String, Transaction> transactionHashMap;
    private final CategoryEditController categoryEditController;
    private final UpdateName updateName;
    private String interceptedMessage;
    private final CategoryController categoryController;

    @Autowired
    public MessageInterceptor(BotData botData,
                              CategoryController categoryController,
                              UpdateName updateName,
                              AddCategory addCategory,
                              CategoryEditController categoryEditController,
                              Logging logging,
                              HashMap<String, Transaction> transactionHashMap,
                              UpdateDescription updateDescription) {
        this.botData = botData;
        this.logging = logging;
        this.transaction = updateName;
        this.transactionHashMap = transactionHashMap;
        this.categoryEditController = categoryEditController;
        this.updateName = updateName;
        this.addCategory = addCategory;
        this.categoryController = categoryController;

        transactionHashMap.put(PhraseConstants.CB_EDIT_CATEGORY_NAME, updateName);
        transactionHashMap.put(PhraseConstants.CB_EDIT_CATEGORY_DESCRIPTION, updateDescription);

        sendMessage = new SendMessage();
    }

    public void interceptMessage(Update update) {

        if (update.getMessage() != null && update.getMessage().getText() != null) {
            interceptedMessage = update.getMessage().getText();
            botData.getFormattedCategory(interceptedMessage);
            logging.debugMessage(interceptedMessage);
        }

        Transaction transaction = transactionHashMap.get(botData.getLastCallback());

        switch (botData.getLastCallback()) {
            case (PhraseConstants.CB_ADD_CATEGORY):
                addTransaction();
                break;
            case (PhraseConstants.CB_EDIT_CATEGORY_NAME), (PhraseConstants.CB_EDIT_CATEGORY_DESCRIPTION):
                editTransaction(transaction);
                break;
            default:
                System.out.println("ERROR IN MessageInterceptor switch");
                break;
        }

    }

    public void addTransaction() {
        categoryController.setSendMessage(addCategory.transaction());
    }

    public void editTransaction(Transaction transaction) {
        categoryController.setSendMessage(
                transaction.transaction(
                        categoryEditController.getLastCategoryCallback(),
                        interceptedMessage
                )
        );
    }

}
