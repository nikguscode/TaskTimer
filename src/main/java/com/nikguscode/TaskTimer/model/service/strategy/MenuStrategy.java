package com.nikguscode.TaskTimer.model.service.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.MenuController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuStrategy implements ControllerStrategy {

    private final MenuController menuController;

    @Autowired
    public MenuStrategy(MenuController menuController) {
        this.menuController = menuController;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return menuController;
    }

    @Override
    public MessageSender getMessageSender() {
        return menuController;
    }

}
