package com.nikguscode.TaskTimer.model.service.telegramCore;

import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.service.UserMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class BotConnection {

    private final BotData botData;
    private final UserMap userMap;

    public BotConnection(BotData botData,
                         UserMap userMap) {
        this.botData = botData;
        this.userMap = userMap;
    }

    public void editMessageConnection(EditMessageText editMessage, Update update) {
        editMessage.setChatId(botData.getChatId());

        if (update.hasCallbackQuery()) {
            UserState userState = userMap.getUserState(botData.getChatId());
            editMessage.setMessageId(userState.getLastMessageId());
            log.debug("messageId: {}", userState.getLastMessageId());
        }
    }

}
