package com.nikguscode.TaskTimer.model.service.telegramCore;

import com.nikguscode.TaskTimer.model.service.Logging;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Service
@Slf4j
@Getter
@Setter
public class BotData {

    private Long chatId;
    private int messageId;
    private String messageText;
    private String tempMessage;
    private String categoryName;
    private String categoryDescription;
    private String userName;
    private String lastCallback;
    private Instant instant;
    private final Logging logging;

    private boolean isInputWaiting = false;

    public BotData(Logging logging) {
        this.logging = logging;
    }

    public void getMessageInfo(Update update) {
        chatId = update.getMessage().getChatId();
        messageText = update.getMessage().getText();
        userName = update.getMessage().getFrom().getUserName();
        instant = Instant.ofEpochSecond(update.getMessage().getDate());

        logging.getMessage(messageText);
    }

    public void getCallbackQuery(Update update) {
        lastCallback = update.getCallbackQuery().getData();
        messageId = update.getCallbackQuery().getMessage().getMessageId();
    }

    public void getFormattedCategory(String message) {
        String[] formattedText = message.split("\\s*\\|\\s*");

        if (formattedText.length != 0) {
            categoryName = formattedText[0];
            if (formattedText.length > 1) {
                categoryDescription = formattedText[1];
            }
        }

    }

}