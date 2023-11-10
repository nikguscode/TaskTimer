package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.model.service.strategy.CategoryStrategy;
import com.nikguscode.TaskTimer.model.service.strategy.ControllerStrategy;
import com.nikguscode.TaskTimer.model.service.strategy.MenuStrategy;
import com.nikguscode.TaskTimer.model.service.strategy.TaskStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

@Controller
public class ReplyController {

    private final TelegramData telegramData;
    private CommandHandler currentCommandHandler;
    private MessageSender currentMessageSender;
    private final Map<String, ControllerStrategy> controllerStrategyMap;

    @Autowired
    public ReplyController(TelegramData telegramData,
                           MenuStrategy menuStrategy,
                           TaskStrategy taskStrategy,
                           CategoryStrategy categoryStrategy,
                           Map<String, ControllerStrategy> controllerStrategyMap) {
        this.telegramData = telegramData;
        this.controllerStrategyMap = controllerStrategyMap;

        controllerStrategyMap.put(PhraseConstants.RESTART_BOT_CMD, menuStrategy);
        controllerStrategyMap.put(PhraseConstants.BACK_TO_MENU, menuStrategy);
        controllerStrategyMap.put(PhraseConstants.TYPE_MANAGEMENT, taskStrategy);
        controllerStrategyMap.put(PhraseConstants.CATEGORY_LIST, categoryStrategy);
    }

    public void setController() {
        ControllerStrategy controllerStrategy = controllerStrategyMap.get(telegramData.getMessageText());

        if (controllerStrategy != null) {
            currentCommandHandler = controllerStrategy.getCommandHandler();
            currentMessageSender = controllerStrategy.getMessageSender();
        }

        currentCommandHandler.handleCommands();
    }

    public SendMessage sendMessage() {
        return currentMessageSender.sendMessage();
    }

}
