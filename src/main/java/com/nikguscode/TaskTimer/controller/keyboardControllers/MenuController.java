package com.nikguscode.TaskTimer.controller.keyboardControllers;

import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.CommandHandler;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.EditMessage;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.MessageSender;
import com.nikguscode.TaskTimer.controller.keyboardControllers.keyboardInterfaces.UCommandHandler;
import com.nikguscode.TaskTimer.model.PhraseConstants;
import com.nikguscode.TaskTimer.model.service.Logging;
import com.nikguscode.TaskTimer.model.service.commands.Launch;
import com.nikguscode.TaskTimer.model.service.commands.Statistics;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotConnection;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotResponse;
import com.nikguscode.TaskTimer.view.keyboards.MenuBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Controller
public class MenuController implements CommandHandler, MessageSender, UCommandHandler, EditMessage {

    private final BotData botData;
    private final BotResponse botResponse;
    private final BotConnection botConnection;
    private final Logging logging;
    private final Launch launch;
    private final Statistics statistics;
    private final MenuBoard menuBoard;
    private SendMessage sendMessage;
    private EditMessageText editMessage;

    public MenuController(BotData botData,
                          BotResponse botResponse,
                          BotConnection botConnection,
                          Logging logging,
                          Launch launch,
                          Statistics statistics,
                          MenuBoard menuBoard) {
        this.botData = botData;
        this.botResponse = botResponse;
        this.botConnection = botConnection;
        this.logging = logging;
        this.launch = launch;
        this.statistics = statistics;
        this.menuBoard = menuBoard;

        sendMessage = new SendMessage();
    }

    @Override
    public void handleCommands() {
        switch (botData.getMessageText()) {
            case (PhraseConstants.RESTART_BOT_CMD):
                botResponse.replyResponse(sendMessage, PhraseConstants.SELECT_CATEGORY, menuBoard.getBoard());
                break;

            case (PhraseConstants.STATISTICS):
                botResponse.replyResponse(sendMessage, statistics.getStat());
                break;

            case (PhraseConstants.START_TIMER):
                if (!launch.isStarted()) {
                    launch.start();

                    // проверяем, есть ли у пользователя активные категории
                    if (launch.getSendMessage().getText() == null) {
                        botResponse.replyResponse(sendMessage, PhraseConstants.STARTED_TIMER, menuBoard.getBoard());
                    } else {
                        launch.getSendMessage().getText();
                        sendMessage = launch.getSendMessage();
                        launch.setSendMessage(null);
                    }

                } else {
                    botResponse.replyResponse(sendMessage, PhraseConstants.ERROR_STARTED_TIMER);
                }
                break;

            case (PhraseConstants.STOP_TIMER):
                if (launch.isStarted()) {
                    launch.stop();
                    botResponse.replyResponse(
                            sendMessage,
                            PhraseConstants.STOPPED_TIMER + launch.getFormattedDuration(),
                            menuBoard.getBoard()
                    );
                } else {
                    botResponse.replyResponse(sendMessage, PhraseConstants.ERROR_STOPPED_TIMER);
                }
                break;

            case (PhraseConstants.BACK_TO_MENU):
                botResponse.replyResponse(sendMessage, PhraseConstants.SUCCESSFULLY, menuBoard.getBoard());
                break;

            default:
                logging.receivedUndefinedCommand(this.getClass());
                sendMessage.setText(Logging.notFoundedCommand);
                break;
        }
    }

    @Override
    public void handleCommands(Update update) {
        if (update.hasCallbackQuery()) {
            editMessage = new EditMessageText();
            botConnection.editMessageConnection(editMessage, update);

            if (update.getCallbackQuery().getData().equals(PhraseConstants.CB_BACK_TO_MENU)) {
                botResponse.replyResponse(
                        editMessage,
                        sendMessage,
                        PhraseConstants.SUCCESSFULLY,
                        PhraseConstants.SELECT_CATEGORY,
                        menuBoard.getBoard()
                );
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
