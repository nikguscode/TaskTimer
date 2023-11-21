package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.*;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.*;
import com.nikguscode.TaskTimer.model.service.crud.AddCategory;
import com.nikguscode.TaskTimer.model.service.crud.GetCategory;
import com.nikguscode.TaskTimer.model.service.strategy.ListOfCategories;
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
    private final MessageInterceptor messageInterceptor;
    private final GetCategory getCategory;
    private final AddCategory addCategory;
    private final ListOfCategories listOfCategories;
    private final CategoryBoard categoryBoard;
    private SendMessage sendMessage;
    private EditMessageText editMessage;

    @Autowired
    public CategoryController(BotData botData,
                              BotResponse botResponse,
                              BotConnection botConnection,
                              Logging logging,
                              MessageInterceptor messageInterceptor,
                              GetCategory getCategory,
                              AddCategory addCategory,
                              ListOfCategories listOfCategories,
                              CategoryBoard categoryBoard) {
        this.botData = botData;
        this.botResponse = botResponse;
        this.botConnection = botConnection;
        this.logging = logging;
        this.messageInterceptor = messageInterceptor;
        this.getCategory = getCategory;
        this.addCategory = addCategory;
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
                sendMessage = messageInterceptor.getSendMessage();
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
