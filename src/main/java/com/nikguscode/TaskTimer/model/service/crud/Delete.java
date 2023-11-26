package com.nikguscode.TaskTimer.model.service.crud;

import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.ListOfCategories;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import com.nikguscode.TaskTimer.view.EmojiConstants;
import com.nikguscode.TaskTimer.view.keyboards.CategoryListBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class Delete {

    private final BotData botData;
    private final CategoryRepository categoryRepository;
    private final CategoryListBoard categoryListBoard;
    private final CategoryFilter categoryFilter;
    private final ListOfCategories listOfCategories;

    @Autowired
    public Delete(CategoryRepository categoryRepository,
                  CategoryListBoard categoryListBoard,
                  CategoryFilter categoryFilter,
                  ListOfCategories listOfCategories,
                  BotData botData) {
        this.categoryRepository = categoryRepository;
        this.botData = botData;
        this.categoryListBoard = categoryListBoard;
        this.categoryFilter = categoryFilter;
        this.listOfCategories = listOfCategories;
    }

    public EditMessageText transaction(Update update, String categoryName, int messageId) {

        categoryRepository.deleteByCategoryNameAndUserId(categoryName, botData.getChatId());

        listOfCategories.transaction(botData.getChatId());
        categoryFilter.clearArrays();
        categoryFilter.getCategories(update);

        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(botData.getChatId());
        editMessage.setMessageId(messageId);

        editMessage.setText(EmojiConstants.SUCCESFULLY_ICON + " Категория: " + categoryName + " успешно удалена");
        editMessage.setReplyMarkup(categoryListBoard.getBoard());
        return editMessage;
    }


}
