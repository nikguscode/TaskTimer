package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.Logging;
import com.nikguscode.TaskTimer.model.service.crud.Add;
import com.nikguscode.TaskTimer.model.service.crud.Get;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.ListOfCategories;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotConnection;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotResponse;
import com.nikguscode.TaskTimer.view.keyboards.CategoryBoard;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * This class contains a keyboard controller, where the user can choose to add a category or view created
 * categories.
 */

@Controller
@Getter
@Setter
@Slf4j
public class CategoryController implements CommandHandler, MessageSender, UCommandHandler, EditMessage {

    private final BotData botData;
    private final BotResponse botResponse;
    private final BotConnection botConnection;
    private final Logging logging;
    private final Get get;
    private final Add add;
    private final ListOfCategories listOfCategories;
    private final CategoryBoard categoryBoard;
    private SendMessage sendMessage;
    private EditMessageText editMessage;

    @Autowired
    public CategoryController(BotData botData,
                              BotResponse botResponse,
                              BotConnection botConnection,
                              Logging logging,
                              Get get,
                              Add add,
                              ListOfCategories listOfCategories,
                              CategoryBoard categoryBoard) {
        this.botData = botData;
        this.botResponse = botResponse;
        this.botConnection = botConnection;
        this.logging = logging;
        this.get = get;
        this.add = add;
        this.listOfCategories = listOfCategories;
        this.categoryBoard = categoryBoard;

        sendMessage = new SendMessage();
    }

    @Override
    public void handleCommands() {
        if (botData.getMessageText().equals(PhraseConstants.CATEGORY_LIST)) {
            botResponse.inlineResponse(
                    sendMessage,
                    PhraseConstants.SELECTED_LIST_OF_CATEGORY,
                    categoryBoard.getBoard()
            );
        } else {
            logging.receivedUndefinedCommand(this.getClass());
            sendMessage.setText(Logging.notFoundedCommand);
        }
    }

    @Override
    public void handleCommands(Update update) {
        if (update.hasCallbackQuery() && update.getCallbackQuery() != null) {
            editMessage = new EditMessageText();
            botConnection.editMessageConnection(editMessage, update);

            if (update.getCallbackQuery().getData().equals(PhraseConstants.CB_ADD_CATEGORY)) {
                editMessage.setText(PhraseConstants.ADD_CATEGORY_RESPONSE);
                botData.setInputWaiting(true);
            } else {
                logging.receivedUndefinedCommand(this.getClass());
                sendMessage.setText(Logging.notFoundedCommand);
            }

        }
    }

    @Override
    public SendMessage sendMessage() {
        return sendMessage;
    }

    @Override
    public EditMessageText editMessage() {
        return editMessage;
    }

}
