package com.nikguscode.TaskTimer.model.service.strategy.messageStrategy;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public interface Transaction {
    SendMessage transaction(String currentCategoryName, String updatedCategoryName);
}
