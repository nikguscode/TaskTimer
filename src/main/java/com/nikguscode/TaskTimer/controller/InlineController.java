package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.controller.strategy.CategoryEditStrategy;
import com.nikguscode.TaskTimer.controller.strategy.CategoryListStrategy;
import com.nikguscode.TaskTimer.controller.strategy.CategoryStrategy;
import com.nikguscode.TaskTimer.controller.strategy.MenuStrategy;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Controller
public class InlineController {

    private UCommandHandler currentCommandHandler;
    private EditMessage currentMessageEditor;
    private final Map<String, InlineStrategy> controllerStrategyMap;
    private final CategoryFilter categoryFilter;
    private final CategoryEditStrategy categoryEditStrategy;

    public InlineController(MenuStrategy menuStrategy,
                            CategoryStrategy categoryStrategy,
                            CategoryListStrategy categoryListStrategy,
                            CategoryEditStrategy categoryEditStrategy,
                            Map<String, InlineStrategy> controllerStrategyMap,
                            CategoryFilter categoryFilter) {
        this.controllerStrategyMap = controllerStrategyMap;
        this.categoryFilter = categoryFilter;
        this.categoryEditStrategy = categoryEditStrategy;

        currentCommandHandler = menuStrategy.getUCommandHandler();
        currentMessageEditor = menuStrategy.getEditSender();

        // выбирает контроллер в зависимости от callback
        controllerStrategyMap.put(PhraseConstants.CB_BACK_TO_MENU, menuStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_ADD_CATEGORY, categoryStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_CATEGORY_LIST, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_BACK_1, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_BACK_2, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_BACK_3, categoryEditStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_NEXT_PAGE, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_PREVIOUS_PAGE, categoryListStrategy);
    }

    // добавляет callback в HashMap
    public void initialize() {
        if (categoryFilter.getCurrentCategories() != null && !categoryFilter.getCurrentCategories().isEmpty()) {
            for (String currentCategory : categoryFilter.getCurrentCategories()) {
                controllerStrategyMap.put(currentCategory, categoryEditStrategy);
            }
        }
    }

    public void setInlineController(Update update) {
        initialize();
        InlineStrategy inlineStrategy = controllerStrategyMap.get(update.getCallbackQuery().getData());

        if (inlineStrategy != null) {
            currentCommandHandler = inlineStrategy.getUCommandHandler();
            currentMessageEditor = inlineStrategy.getEditSender();
        }

        currentCommandHandler.handleCommands(update);
    }

    public EditMessageText editMessage() {
        return currentMessageEditor.editMessage();
    }

}
