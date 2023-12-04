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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Controller
@Slf4j
public class InlineController {

    private final Map<String, InlineStrategy> controllerStrategyMap;
    private final CategoryFilter categoryFilter;
    private final CategoryEditStrategy categoryEditStrategy;
    private final MenuStrategy menuStrategy;
    private final CategoryStrategy categoryStrategy;
    private final CategoryListStrategy categoryListStrategy;

    @Autowired
    public InlineController(MenuStrategy menuStrategy,
                            CategoryStrategy categoryStrategy,
                            CategoryListStrategy categoryListStrategy,
                            CategoryEditStrategy categoryEditStrategy,
                            Map<String, InlineStrategy> controllerStrategyMap,
                            CategoryFilter categoryFilter) {
        this.categoryFilter = categoryFilter;
        this.menuStrategy = menuStrategy;
        this.categoryStrategy = categoryStrategy;
        this.categoryListStrategy = categoryListStrategy;
        this.categoryEditStrategy = categoryEditStrategy;
        this.controllerStrategyMap = controllerStrategyMap;
    }

    // добавляет callback (название категории-кнопка) в HashMap
    private synchronized void initializeCategories() {
        if (categoryFilter.getCurrentCategories() != null) {
            categoryFilter.getCurrentCategories().forEach(currentCategory -> controllerStrategyMap.put(currentCategory, categoryEditStrategy));
            log.debug("categories: {}", categoryFilter.getCurrentCategories());
        }
    }

    private synchronized void initializeStates(MenuStrategy menuStrategy,
                                               CategoryStrategy categoryStrategy,
                                               CategoryListStrategy categoryListStrategy,
                                               CategoryEditStrategy categoryEditStrategy) {
        controllerStrategyMap.put(PhraseConstants.CB_BACK_TO_MENU, menuStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_ADD_CATEGORY, categoryStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_CATEGORY_LIST, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_BACK_1, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_BACK_2, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_BACK_3, categoryEditStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_NEXT_PAGE, categoryListStrategy);
        controllerStrategyMap.put(PhraseConstants.CB_PREVIOUS_PAGE, categoryListStrategy);
    }

    public synchronized EditMessage setInlineController(Update update) {
        initializeCategories();
        initializeStates(menuStrategy, categoryStrategy, categoryListStrategy, categoryEditStrategy);

        String callBackData = update.getCallbackQuery().getData();
        InlineStrategy inlineStrategy = controllerStrategyMap.get(callBackData);

        if (inlineStrategy == null) {
            throw new IllegalArgumentException("Invalid callback data: " + callBackData);
        }

        return selectHandlerAndEditor(inlineStrategy, update);
    }

    public EditMessageText editMessage(Update update) {
        EditMessageText editedMessage = setInlineController(update).editMessage();
        log.debug("editMessage: {}", editedMessage);
        return editedMessage;
    }

    private synchronized EditMessage selectHandlerAndEditor(InlineStrategy inlineStrategy, Update update) {
        UCommandHandler commandHandler = inlineStrategy.getUCommandHandler();
        EditMessage editMessage = inlineStrategy.getEditSender();
        log.debug("commandHandler: {}", commandHandler);
        log.debug("editMessage: {}", editMessage);

        commandHandler.handleCommands(update);
        return editMessage;
    }

}
