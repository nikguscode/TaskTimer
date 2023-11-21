package com.nikguscode.TaskTimer.model.service.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.view.EmojiConstants;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Getter
public class ActiveCategory implements Transaction, MessageSender {

    private final CategoryRepository categoryRepository;
    private SendMessage sendMessage;

    public ActiveCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void transaction(Long userId) {
        String activeCategory = categoryRepository.findCategoryNameByUserIdAndIsActive(userId, true).getCategoryName();
        String botResponse = EmojiConstants.ACTIVE_CTG_ICON + " Активная категория: " + activeCategory;

        sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText(botResponse);
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

}
