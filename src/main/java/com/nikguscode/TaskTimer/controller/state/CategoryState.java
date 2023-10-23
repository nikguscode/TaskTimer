package com.nikguscode.TaskTimer.controller.state;

import com.nikguscode.TaskTimer.controller.CategoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryState implements KeyboardCommandHandler {

    private final CategoryController categoryController;

    @Autowired
    public CategoryState(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @Override
    public void handleCommands() {
        categoryController.handleCommands();
    }

}
