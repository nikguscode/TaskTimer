package com.nikguscode.TaskTimer.model.service.crud;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.GetActiveCategory;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.ListOfCategories;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.Transaction;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Getter
@Setter
@Slf4j
public class Get implements MessageSender {

    private final BotData botData;
    private Transaction transaction;
    private SendMessage sendMessage;
    private GetActiveCategory getActiveCategory;
    private MessageSender messageSender;
    private ListOfCategories listOfCategories;

    @Autowired
    public Get(BotData botData,
               GetActiveCategory getActiveCategory,
               ListOfCategories listOfCategories) {
        this.botData = botData;
        this.transaction = getActiveCategory;
        this.messageSender = getActiveCategory;
        this.listOfCategories = listOfCategories;
    }

    public void transaction(Transaction transaction) {
        transaction.transaction(botData.getChatId());
    }

    @Override
    public SendMessage sendMessage() {
        return messageSender.sendMessage();
    }

}
