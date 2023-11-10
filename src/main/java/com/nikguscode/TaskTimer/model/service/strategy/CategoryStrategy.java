package com.nikguscode.TaskTimer.model.service.strategy;

import com.nikguscode.TaskTimer.controller.MasterController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.CategoryController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryStrategy implements ControllerStrategy {

    private MasterController masterController;
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

}
