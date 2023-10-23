package com.nikguscode.TaskTimer.controller.state;

import com.nikguscode.TaskTimer.controller.MenuController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

@Controller
@Primary
public class MenuState implements KeyboardCommandHandler {
    private final MenuController menuController;

    @Autowired
    public MenuState(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public void handleCommands() {
        menuController.handleCommands();
    }

}
