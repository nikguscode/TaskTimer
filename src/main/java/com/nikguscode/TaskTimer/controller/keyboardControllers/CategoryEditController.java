package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import com.nikguscode.TaskTimer.model.service.Logging;
import com.nikguscode.TaskTimer.model.service.crud.Delete;
import com.nikguscode.TaskTimer.model.service.crud.Update;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.categoryStrategy.ActiveCategorySetter;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotConnection;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotResponse;
import com.nikguscode.TaskTimer.view.keyboards.CategoryEditBoard;
import com.nikguscode.TaskTimer.view.keyboards.CategoryListBoard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

@Controller
@Getter
@Slf4j
public class CategoryEditController implements UCommandHandler, EditMessage {

    private final BotData botData;
    private final BotResponse botResponse;
    private final BotConnection botConnection;
    private final Logging logging;
    private final Update update;
    private final Delete delete;
    private final CategoryFilter categoryFilter;
    private final ActiveCategorySetter activeCategorySetter;
    private final CategoryEditBoard categoryEditBoard;
    private final CategoryListBoard categoryListBoard;
    private EditMessageText editMessage;
    private String lastCategoryCallback;


    public CategoryEditController(BotData botData,
                                  BotResponse botResponse,
                                  BotConnection botConnection,
                                  Logging logging,
                                  Update update,
                                  Delete delete,
                                  CategoryFilter categoryFilter,
                                  ActiveCategorySetter activeCategorySetter,
                                  CategoryEditBoard categoryEditBoard,
                                  CategoryListBoard categoryListBoard) {
        this.botData = botData;
        this.botResponse = botResponse;
        this.botConnection = botConnection;
        this.logging = logging;
        this.update = update;
        this.delete = delete;
        this.categoryFilter = categoryFilter;
        this.activeCategorySetter = activeCategorySetter;
        this.categoryEditBoard = categoryEditBoard;
        this.categoryListBoard = categoryListBoard;
    }

    @Override
    public void handleCommands(org.telegram.telegrambots.meta.api.objects.Update update) {
        editMessage = new EditMessageText();
        botConnection.editMessageConnection(editMessage, update);

        if (categoryFilter.getCurrentCategories() != null) {

            for (String currentCategory : categoryFilter.getCurrentCategories()) {

                if (update.getCallbackQuery().getData().equals(currentCategory)) {
                    botResponse.inlineResponse(
                            editMessage,
                            PhraseConstants.SELECT_WHAT_TO_DO,
                            categoryEditBoard.getSelectingBoard()
                    );

                    lastCategoryCallback = update.getCallbackQuery().getData();
                }

            }

        }

        switch (update.getCallbackQuery().getData()) {
            case (PhraseConstants.CB_EDIT_CATEGORY):
                botResponse.inlineResponse(
                        editMessage,
                        PhraseConstants.SELECT_WHAT_TO_DO,
                        categoryEditBoard.getEditingBoard()
                );
                break;

            case (PhraseConstants.CB_EDIT_ACTIVE_CTG):
                botResponse.inlineResponse(
                        editMessage,
                        PhraseConstants.SELECTED_ACTIVE
                );

                activeCategorySetter.transaction(lastCategoryCallback);
                break;

            case (PhraseConstants.CB_EDIT_CATEGORY_NAME):
                botResponse.inlineResponse(
                        editMessage,
                        PhraseConstants.TYPE_NEW_NAME
                );

                botData.setInputWaiting(true);
                break;

            case (PhraseConstants.CB_EDIT_CATEGORY_DESCRIPTION):
                botResponse.inlineResponse(
                        editMessage,
                        PhraseConstants.TYPE_NEW_DESCRIPTION
                );

                botData.setInputWaiting(true);
                break;

            case (PhraseConstants.CB_DELETE_CATEGORY):
                editMessage = delete.transaction(
                        update,
                        lastCategoryCallback,
                        update.getCallbackQuery().getMessage().getMessageId()
                );
                break;

            case (PhraseConstants.CB_BACK_3):
                botResponse.inlineResponse(
                        editMessage,
                        PhraseConstants.CATEGORY_LIST,
                        categoryEditBoard.getSelectingBoard()
                );
                break;

            default:
                break;
        }

    }

    @Override
    public EditMessageText editMessage() {
        return editMessage;
    }

}
