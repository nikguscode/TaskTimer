package com.nikguscode.TaskTimer.model.service.commands;

import com.nikguscode.TaskTimer.model.entity.SessionState;
import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.service.SessionMap;
import com.nikguscode.TaskTimer.model.service.UserMap;
import com.nikguscode.TaskTimer.model.service.crud.Add;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.sessionStrategy.LastSessionGetter;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.sessionStrategy.SessionGetter;
import com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.sessionStrategy.TimeGetter;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@Getter
public class Statistics {

    private final SessionGetter sessionGetter;
    private final LastSessionGetter lastSessionGetter;
    private final TimeGetter timeGetter;
    private final Add add;
    private final BotData botData;
    private final UserMap userMap;
    private final SessionMap sessionMap;
    private final String statistics = """
            Статистика:
            Количество выполненных задач: undefined
            Среднее время выполнения задачи: undefined
            Последняя выполненная задача: undefined""";

    @Autowired
    public Statistics(Add add,
                      BotData botData,
                      TimeGetter timeGetter,
                      SessionGetter sessionGetter,
                      LastSessionGetter lastSessionGetter,
                      UserMap userMap,
                      SessionMap sessionMap) {
        this.add = add;
        this.botData = botData;
        this.sessionGetter = sessionGetter;
        this.lastSessionGetter = lastSessionGetter;
        this.userMap = userMap;
        this.sessionMap = sessionMap;
        this.timeGetter = timeGetter;
    }

    public int getCompletedTask() {
        sessionGetter.transaction();
        UserState userState = userMap.getUserState(botData.getChatId());

        return userState.getSessionCounter();
    }

    public double getAverageTime() {
        ArrayList<SessionState> taskDurationList = timeGetter.transaction(botData.getChatId());

        int listSize = taskDurationList.size();
        int sumOfDuration = 0;
        for (SessionState sessionState : taskDurationList) {
            sumOfDuration += (int) sessionState.getDuration();
        }

        return (double) sumOfDuration / listSize;
    }

    public String getLastCompletedTask() {
        lastSessionGetter.transaction();
        UserState userState = userMap.getUserState(botData.getChatId());

        log.debug("lastActiveCategoryName: {}", userState.getLastActiveCategoryName());
        return userState.getLastActiveCategoryName();
    }

    public String getStat() {
        String updatedTask;
        updatedTask = "Статистика: " + "\n" + "\n"
                + "Количество выполненных задач: "
                + getCompletedTask() + "\n"
                + "Среднее время выполнения задачи: " +
                (!Double.isNaN(getAverageTime()) ? (getAverageTime() + " сек." + "\n") : ("отсутствует" + "\n"))
                + "Последняя выполненная задача: " +
                (getLastCompletedTask() != null ? getLastCompletedTask() : "отсутствует");

        return updatedTask;
    }

}