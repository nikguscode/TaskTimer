package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.Logging;
import com.nikguscode.TaskTimer.model.service.crud.Get;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.GetActiveCategory;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotResponse;
import com.nikguscode.TaskTimer.view.keyboards.TaskBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Controller
public class TaskController implements CommandHandler, MessageSender {

    private final BotData botData;
    private final BotResponse botResponse;
    private final Logging logging;
    private final Get get;
    private final GetActiveCategory getActiveCategory;
    private final TaskBoard taskBoard;
    private final SendMessage sendMessage;

    @Autowired
    public TaskController(BotData botData,
                          BotResponse botResponse,
                          Logging logging,
                          Get get,
                          GetActiveCategory getActiveCategory,
                          TaskBoard taskBoard) {
        this.botData = botData;
        this.botResponse = botResponse;
        this.logging = logging;
        this.get = get;
        this.getActiveCategory = getActiveCategory;
        this.taskBoard = taskBoard;

        sendMessage = new SendMessage();
    }

    @Override
    public void handleCommands() {
        switch (botData.getMessageText()) {
            case (PhraseConstants.ACTIVE_CATEGORY):
                get.transaction(getActiveCategory);
                botResponse.replyResponse(sendMessage, get.sendMessage().getText());
                break;

            case (PhraseConstants.TYPE_MANAGEMENT):
                botResponse.replyResponse(sendMessage, PhraseConstants.SELECTED_TYPE_MANAGEMENT, taskBoard.getBoard());
                break;

            default:
                logging.receivedUndefinedCommand(this.getClass());
                sendMessage.setText(Logging.notFoundedCommand);
                break;
        }
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

}
