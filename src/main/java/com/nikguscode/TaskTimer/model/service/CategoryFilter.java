package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.model.service.strategy.ListOfCategories;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotConnection;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

@Service
@Getter
@Setter
@Slf4j
public class CategoryFilter {

    private ListOfCategories listOfCategories;
    private EditMessageText editMessage;
    private final BotConnection botConnection;
    private final Logging logging;
    private int totalPages = 0;
    private int currentPage = 1;
    private ArrayList<String> currentCategories;

    @Autowired
    public CategoryFilter(ListOfCategories listOfCategories,
                          Logging logging,
                          BotConnection botConnection) {
        this.listOfCategories = listOfCategories;
        this.logging = logging;
        this.botConnection = botConnection;

        editMessage = new EditMessageText();
    }

    private ArrayList<String> createCategoriesArray() {
        int length = listOfCategories.getCategories().size();

        if (length == 0) {
            if (currentCategories != null && !currentCategories.isEmpty()) {
                currentCategories.clear();
            }
            return  null;
        }

        totalPages = (int) Math.ceil(length / 6.0);

        currentCategories = new ArrayList<>();

        for (int i = ((currentPage - 1) * 6); i < (currentPage * 6) && i < length; i++) {
            if (listOfCategories.getCategories().get(i) != null) {
                currentCategories.add(listOfCategories.getCategories().get(i).getCategoryName());
            } else {
                break;
            }
        }

        return currentCategories;
    }

    public ArrayList<String> getCategories(Update update) {
        botConnection.editMessageConnection(editMessage, update);

        if (createCategoriesArray() == null) {
            editMessage.setText("Вы ещё не создали категорий :)");
        }

        return createCategoriesArray();
    }

    public void clearArrays() {
        currentCategories.clear();
    }

}
