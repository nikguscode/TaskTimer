package com.nikguscode.TaskTimer.controller.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryListController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryListStrategy implements InlineStrategy {

    private final CategoryListController categoryListController;

    @Autowired
    public CategoryListStrategy(CategoryListController categoryListController) {
        this.categoryListController = categoryListController;
    }

    @Override
    public UCommandHandler getUCommandHandler() {
        return categoryListController;
    }

    @Override
    public EditMessage getEditSender() {
        return categoryListController;
    }

}
