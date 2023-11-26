package com.nikguscode.TaskTimer.model.service.commands;


import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.service.crud.Add;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.GetActiveCategory;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class contains "/start" and "/stop" commands logic
 */

@Service
@Slf4j
public class Launch {
    private final BotData botData;
    private final GetActiveCategory getActiveCategory;
    //private Map<Long, UserState> userStates = new HashMap<>();
    private String formattedDuration;
    private SendMessage sendMessage;
    private Add add;


    @Autowired
    public Launch(BotData botData,
                  GetActiveCategory getActiveCategory,
                  Add add) {
        this.getActiveCategory = getActiveCategory;
        this.botData = botData;
        this.add = add;

        sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.HTML);
    }

    public void start() {
        getActiveCategory.transaction(botData.getChatId());

        if (getActiveCategory.getActiveCategory() == null) {
            sendMessage.setText("""
                    <strong>Ошибка, кажется у вас нет активной категории.</strong>

                    Для добавления активной категории перейдите в: \s
                    <em>"Управление типами" -> "Список категорий"-> "Список созданных категорий".</em>\s
                    
                    Затем выберите категорию и сделайте её активной.""");
        } else {
            if (botData.getChatId() != null) {
                UserState userState = add.getUserState(botData.getChatId());
                System.out.println(botData.getChatId());
                userState.setStarted(true);
                userState.setStartTime(botData.getInstant());
                LocalDateTime localDate = LocalDateTime.now();

                add.createSession(botData.getChatId(), getActiveCategory.getActiveCategory(), localDate);
                log.info("sessionIdStart: {}", userState.getSessionId());
                log.info("userId: {}", userState.getUserId());
            }
        }

    }

    @Transactional
    public void stop() {
        UserState userState = add.getUserState(botData.getChatId());
        log.info("sessionIdStop1: {}", userState.getSessionId());
        System.out.println(userState);
        userState.setStarted(false);
        userState.setEndTime(botData.getInstant());
        getDuration();
        LocalDateTime localDate = LocalDateTime.now();
        add.endSession(userState, localDate, formattedDuration);
        log.info("sessionIdStop2: {}", userState.getSessionId());
    }

    @Transactional
    private void getDuration() {
        UserState userState = add.getUserState(botData.getChatId());
        Duration duration = Duration.between(userState.getStartTime(), userState.getEndTime());

        if (duration.toDays() == 0) {
            formattedDuration = (duration.toHours() < 10 ? "0" : "") + duration.toHours() % 24 + ":"
                    + (duration.toMinutes() < 10 ? "0" : "") + duration.toMinutes() % 60 + ":"
                    + (duration.toSeconds() < 10 ? "0" : "") + duration.toSeconds() % 60;
        } else {
            formattedDuration = duration.toDays() + "d. | "
                    + (duration.toHours() < 10 ? "0" : "") +duration.toHours() % 24 + ":"
                    + (duration.toMinutes() < 10 ? "0" : "") +duration.toMinutes() % 60 + ":"
                    + (duration.toSeconds() < 10 ? "0" : "") + duration.toSeconds() % 60;
        }

        userState.setFormattedTime(formattedDuration);
    }

    public boolean isStarted() {
        Long chatId = botData.getChatId();
        return chatId != null && add.getUserState(chatId).isStarted();
    }

    public String getFormattedDuration() {
        UserState userState = add.getUserState(botData.getChatId());
        return userState != null ? userState.getFormattedTime() : null;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

}
