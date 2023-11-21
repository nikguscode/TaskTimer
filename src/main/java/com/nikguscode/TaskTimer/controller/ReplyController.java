package com.nikguscode.TaskTimer.controller;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import com.nikguscode.TaskTimer.controller.strategy.CategoryStrategy;
import com.nikguscode.TaskTimer.controller.strategy.interfaces.ReplyStrategy;
import com.nikguscode.TaskTimer.controller.strategy.MenuStrategy;
import com.nikguscode.TaskTimer.controller.strategy.TaskStrategy;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Controller
@Getter
public class ReplyController {

    private final BotData botData;
    private CommandHandler currentCommandHandler;
    private MessageSender currentMessageSender;
    private final Map<String, ReplyStrategy> replyStrategy;


    @Autowired
    public ReplyController(BotData botData,
                           MenuStrategy menuStrategy,
                           TaskStrategy taskStrategy,
                           CategoryStrategy categoryStrategy,
                           Map<String, ReplyStrategy> replyStrategy) {
        this.botData = botData;
        this.replyStrategy = replyStrategy;

        currentCommandHandler = menuStrategy.getCommandHandler();
        currentMessageSender = menuStrategy.getMessageSender();

        // по заданным командам бот переключает контроллер и отправитель сообщения
        // каждый контроллер отвечает за свою клавиатуру
        replyStrategy.put(PhraseConstants.RESTART_BOT_CMD, menuStrategy);
        replyStrategy.put(PhraseConstants.BACK_TO_MENU, menuStrategy);
        replyStrategy.put(PhraseConstants.TYPE_MANAGEMENT, taskStrategy);
        replyStrategy.put(PhraseConstants.ACTIVE_CATEGORY, taskStrategy);
        replyStrategy.put(PhraseConstants.CATEGORY_LIST, categoryStrategy);

        replyStrategy.put(PhraseConstants.CB_BACK_TO_MENU, menuStrategy);
    }

    public void setReplyController(Update update) {
        ReplyStrategy replyStrategy = this.replyStrategy.get(botData.getMessageText());

        // проверяем, содержится ли callback в HashMap
        if (update.hasCallbackQuery()) {
            replyStrategy = this.replyStrategy.get(update.getCallbackQuery().getData());
        }

        // если response или callback содержится в HashMap, выбираем контроллеры
        if (replyStrategy != null) {
            currentCommandHandler = replyStrategy.getCommandHandler();
            currentMessageSender = replyStrategy.getMessageSender();
        }

        // запускаем контроллер, только в случае, если был получен response из HashMap
        // если был получен callback, выполняем только отправку сообщения для соответствующего класса
        if (currentCommandHandler != null && !update.hasCallbackQuery()) {
            currentCommandHandler.handleCommands();
        }

        // перед отправкой любого сообщения устанавливаем chatId
        currentMessageSender.sendMessage().setChatId(botData.getChatId());
    }

    public SendMessage sendMessage() {
        return currentMessageSender.sendMessage();
    }

}
