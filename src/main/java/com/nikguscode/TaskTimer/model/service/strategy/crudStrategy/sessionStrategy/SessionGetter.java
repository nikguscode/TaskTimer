package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.sessionStrategy;

import com.nikguscode.TaskTimer.model.entity.SessionState;
import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.repository.SessionRepository;
import com.nikguscode.TaskTimer.model.service.UserMap;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class SessionGetter {

    private final SessionRepository sessionRepository;
    private final BotData botData;
    private final UserMap userMap;

    @Autowired
    public SessionGetter(SessionRepository sessionRepository,
                         BotData botData,
                         UserMap userMap) {
        this.sessionRepository = sessionRepository;
        this.botData = botData;
        this.userMap = userMap;
    }

    public void transaction() {
        ArrayList<SessionState> sessionsId = sessionRepository.findSessionIdByUserId(botData.getChatId());
        log.debug("[ARRAYLIST] sessionId: {}", sessionsId);

        UserState userState = userMap.getUserState(botData.getChatId());
        userState.setSessionCounter(sessionsId.size());
    }

}
