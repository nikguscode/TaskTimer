package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.view.EmojiConstants;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Getter
public class GetActiveCategory implements Transaction, MessageSender {

    private final CategoryRepository categoryRepository;
    private SendMessage sendMessage;
    private String activeCategory;

    public GetActiveCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

        sendMessage = new SendMessage();
    }

    @Override
    public void transaction(Long userId) {
        if (categoryRepository.findCategoryNameByUserIdAndIsActive(userId, true) != null) {
            activeCategory = categoryRepository.findCategoryNameByUserIdAndIsActive(userId, true).getCategoryName();
            String botResponse = EmojiConstants.ACTIVE_CTG_ICON + " Активная категория: " + activeCategory;
            sendMessage.setText(botResponse);
        } else {
            activeCategory = null;
            String botResponse = "Активная категория не выбрана";
            sendMessage.setText(botResponse);
            System.out.println("GetActiveCategory, активной категории не существует");
        }
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

}
