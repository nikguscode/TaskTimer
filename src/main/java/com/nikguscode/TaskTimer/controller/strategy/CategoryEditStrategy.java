package com.nikguscode.TaskTimer.controller.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryEditController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryEditStrategy implements InlineStrategy {

    private final CategoryEditController categoryEditController;

    public CategoryEditStrategy(CategoryEditController categoryEditController) {
        this.categoryEditController = categoryEditController;
    }

    @Override
    public UCommandHandler getUCommandHandler() {
        return categoryEditController;
    }

    @Override
    public EditMessage getEditSender() {
        return categoryEditController;
    }

}
