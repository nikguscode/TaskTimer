package com.nikguscode.TaskTimer.model.service.commands;


import com.nikguscode.TaskTimer.model.entity.SessionState;
import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.service.SessionMap;
import com.nikguscode.TaskTimer.model.service.UserMap;
import com.nikguscode.TaskTimer.model.service.crud.Add;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.categoryStrategy.ActiveCategoryGetter;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class Launch {
    private final BotData botData;
    private final ActiveCategoryGetter activeCategoryGetter;
    private SendMessage sendMessage;
    private final Add add;
    private final UserMap userMap;
    private final SessionMap sessionMap;


    @Autowired
    public Launch(BotData botData,
                  ActiveCategoryGetter activeCategoryGetter,
                  Add add,
                  UserMap userMap,
                  SessionMap sessionMap) {
        this.activeCategoryGetter = activeCategoryGetter;
        this.botData = botData;
        this.add = add;
        this.userMap = userMap;
        this.sessionMap = sessionMap;

        sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.HTML);
    }

    public void start() {
        activeCategoryGetter.transaction(botData.getChatId());

        if (activeCategoryGetter.getActiveCategory() == null) {
            sendMessage.setText("""
                    <strong>Ошибка, кажется у вас нет активной категории.</strong>

                    Для добавления активной категории перейдите в: \s
                    <em>"Управление типами" -> "Список категорий"-> "Список созданных категорий".</em>\s
                                        
                    Затем выберите категорию и сделайте её активной.""");
        } else {
            if (botData.getChatId() != null) {
                UserState userState = userMap.getUserState(botData.getChatId());
                userState.setStarted(true);

                LocalDateTime localDate = LocalDateTime.now();
                add.createSession(botData.getChatId(), activeCategoryGetter.getActiveCategory(), localDate);

                SessionState sessionState = sessionMap.getSessionState(botData.getChatId());
                sessionState.setStartDate(localDate);

                log.debug("[START TIMER] sessionObject: {}", sessionState);
                log.debug("[START TIMER] sessionId: {}", sessionState.getSessionId());
                log.debug("[START TIMER] userId: {}", sessionState.getUserId());
                log.debug("[START TIMER] startDate: {}", sessionState.getStartDate());
            }
        }

    }

    @Transactional
    public void stop() {
        UserState userState = userMap.getUserState(botData.getChatId());
        userState.setStarted(false);

        LocalDateTime localDate = LocalDateTime.now();

        SessionState sessionState = sessionMap.getSessionState(botData.getChatId());
        sessionState.setEndDate(localDate);

        getDuration();
        add.endSession(sessionState, localDate, sessionState.getDuration());

        log.debug("[FINISH TIMER] sessionId: {}", sessionState.getSessionId());
        log.debug("[FINISH TIMER] userId: {}", sessionState.getUserId());
        log.debug("[FINISH TIMER] endDate: {}", sessionState.getEndDate());
        log.debug("[FINISH TIMER] formattedTime: {}", sessionState.getDuration());
    }

    @Transactional
    private void getDuration() {
        SessionState sessionState = sessionMap.getSessionState(botData.getChatId());
        Duration duration = Duration.between(sessionState.getStartDate(), sessionState.getEndDate());

        String formattedDuration;
        if (duration.toDays() == 0) {
            formattedDuration = (duration.toHours() < 10 ? "0" : "") + duration.toHours() % 24 + ":"
                    + (duration.toMinutes() < 10 ? "0" : "") + duration.toMinutes() % 60 + ":"
                    + (duration.toSeconds() < 10 ? "0" : "") + duration.toSeconds() % 60;
        } else {
            formattedDuration = duration.toDays() + "d. | "
                    + (duration.toHours() < 10 ? "0" : "") + duration.toHours() % 24 + ":"
                    + (duration.toMinutes() < 10 ? "0" : "") + duration.toMinutes() % 60 + ":"
                    + (duration.toSeconds() < 10 ? "0" : "") + duration.toSeconds() % 60;
        }

        sessionState.setDuration(duration.getSeconds());
        sessionState.setFormattedTime(formattedDuration);
    }

    public boolean isStarted() {

        if (botData.getChatId() != null) {
            UserState userState = userMap.getUserState(botData.getChatId());
            return userState != null && userState.isStarted();
        }

        return false;
    }

    public String getFormattedDuration() {
        SessionState sessionState = sessionMap.getSessionState(botData.getChatId());

        return sessionState != null ? sessionState.getFormattedTime() : null;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

}
