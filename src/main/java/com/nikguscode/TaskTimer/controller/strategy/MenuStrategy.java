package com.nikguscode.TaskTimer.controller.strategy;

import com.nikguscode.TaskTimer.controller.keyboardControllers.MenuController;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.InlineStrategy;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuStrategy implements ReplyStrategy, InlineStrategy {

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

    @Override
    public UCommandHandler getUCommandHandler() {
        return menuController;
    }

    @Override
    public EditMessage getEditSender() {
        return menuController;
    }

}
