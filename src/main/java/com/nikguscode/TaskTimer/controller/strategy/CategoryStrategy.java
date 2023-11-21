package com.nikguscode.TaskTimer.controller.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryStrategy implements ReplyStrategy, InlineStrategy {

    private final CategoryController categoryController;

    @Autowired
    public CategoryStrategy(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return categoryController;
    }

    @Override
    public MessageSender getMessageSender() {
        return categoryController;
    }

    @Override
    public UCommandHandler getUCommandHandler() {
        return categoryController;
    }

    @Override
    public EditMessage getEditSender() {
        return categoryController;
    }

}
