package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.CategoryFilter;
import com.nikguscode.TaskTimer.model.service.Logging;
import com.nikguscode.TaskTimer.model.service.crud.Get;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.ListOfCategories;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotConnection;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotResponse;
import com.nikguscode.TaskTimer.view.keyboards.CategoryBoard;
import com.nikguscode.TaskTimer.view.keyboards.CategoryListBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class CategoryListController implements UCommandHandler, EditMessage {

    private final BotResponse botResponse;
    private final BotConnection botConnection;
    private final Logging logging;
    private final CategoryFilter categoryFilter;
    private final Get get;
    private final ListOfCategories listOfCategories;
    private final CategoryListBoard categoryListBoard;
    private final CategoryBoard categoryBoard;
    private EditMessageText editMessage;

    @Autowired
    public CategoryListController(BotResponse botResponse,
                                  BotConnection botConnection,
                                  Logging logging,
                                  CategoryFilter categoryFilter,
                                  Get get,
                                  ListOfCategories listOfCategories,
                                  CategoryListBoard categoryListBoard,
                                  CategoryBoard categoryBoard) {
        this.botResponse = botResponse;
        this.botConnection = botConnection;
        this.logging = logging;
        this.categoryFilter = categoryFilter;
        this.get = get;
        this.listOfCategories = listOfCategories;
        this.categoryListBoard = categoryListBoard;
        this.categoryBoard = categoryBoard;
    }

    @Override
    public void handleCommands(Update update) {
        if (update.hasCallbackQuery()) {
            int currentPage;
            int totalPages;

            editMessage = new EditMessageText();
            botConnection.editMessageConnection(editMessage, update);

            switch (update.getCallbackQuery().getData()) {
                case (PhraseConstants.CB_CATEGORY_LIST):
                    // получаем все категории для пользователя, после чего вносим их в ArrayList
                    get.transaction(listOfCategories);
                    categoryFilter.getCategories(update);

                    currentPage = categoryFilter.getCurrentPage();
                    totalPages = categoryFilter.getTotalPages();

                    if (categoryFilter.getCategories(update) == null) {
                        editMessage = categoryFilter.getEditMessage();
                    } else {
                        botResponse.inlineResponse(
                                editMessage,
                                PhraseConstants.CURRENT_PAGE + currentPage + "/" + totalPages,
                                categoryListBoard.getBoard()
                        );
                    }
                    break;

                case (PhraseConstants.CB_NEXT_PAGE):
                    currentPage = categoryFilter.getCurrentPage();
                    totalPages = categoryFilter.getTotalPages();

                    if (currentPage < totalPages) {
                        categoryFilter.setCurrentPage(currentPage += 1);
                        categoryFilter.clearArrays();
                        categoryFilter.getCategories(update);

                        botResponse.inlineResponse(
                                editMessage,
                                PhraseConstants.CURRENT_PAGE + currentPage + "/" + totalPages,
                                categoryListBoard.getBoard()
                        );
                    }
                    break;

                case (PhraseConstants.CB_PREVIOUS_PAGE):
                    currentPage = categoryFilter.getCurrentPage();
                    totalPages = categoryFilter.getTotalPages();

                    if (currentPage != 1) {
                        categoryFilter.setCurrentPage(currentPage -= 1);
                        categoryFilter.clearArrays();
                        categoryFilter.getCategories(update);

                        botResponse.inlineResponse(
                                editMessage,
                                PhraseConstants.CURRENT_PAGE + currentPage + "/" + totalPages,
                                categoryListBoard.getBoard()
                        );
                    }
                    break;

                case (PhraseConstants.CB_BACK_1):
                    botResponse.inlineResponse(
                            editMessage,
                            PhraseConstants.SELECTED_LIST_OF_CATEGORY,
                            categoryBoard.getBoard()
                    );
                    break;

                case (PhraseConstants.CB_BACK_2):
                    currentPage = categoryFilter.getCurrentPage();
                    totalPages = categoryFilter.getTotalPages();

                    botResponse.inlineResponse(
                            editMessage,
                            PhraseConstants.CURRENT_PAGE + currentPage + "/" + totalPages,
                            categoryListBoard.getBoard()
                    );
                    break;

                default:
                    logging.receivedUndefinedCommand(this.getClass());
                    break;
            }

        }
    }

    @Override
    public EditMessageText editMessage() {
        return editMessage;
    }

}
