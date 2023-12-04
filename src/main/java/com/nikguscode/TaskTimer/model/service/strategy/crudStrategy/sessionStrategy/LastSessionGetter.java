package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.sessionStrategy;

import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.repository.SessionRepository;
import com.nikguscode.TaskTimer.model.service.SessionMap;
import com.nikguscode.TaskTimer.model.service.UserMap;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LastSessionGetter {

    private final BotData botData;
    private final SessionRepository sessionRepository;
    private final UserMap userMap;

    public LastSessionGetter(BotData botData,
                             SessionRepository sessionRepository,
                             UserMap userMap) {
        this.botData = botData;
        this.sessionRepository = sessionRepository;
        this.userMap = userMap;
    }

    public void transaction() {
        String lastActiveCategoryName = sessionRepository.getLastSessionCategoryName(botData.getChatId());

        UserState userState = userMap.getUserState(botData.getChatId());
        userState.setLastActiveCategoryName(lastActiveCategoryName);

        log.debug("lastActiveCategoryName: {}", lastActiveCategoryName);
    }

}
