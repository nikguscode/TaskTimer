package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy.sessionStrategy;

import com.nikguscode.TaskTimer.model.entity.SessionState;
import com.nikguscode.TaskTimer.model.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TimeGetter {

    private final SessionRepository sessionRepository;

    public TimeGetter(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public ArrayList<SessionState> transaction(long userId) {
        return sessionRepository.getDurationByUserId(userId);
    }

}
