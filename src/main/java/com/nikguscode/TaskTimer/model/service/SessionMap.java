package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.model.entity.SessionState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionMap {
    private final Map<Long, SessionState> sessionStates = new HashMap<>();

    public SessionState getSessionState(long userId) {
        SessionState sessionState = sessionStates.get(userId);

        if (sessionState == null) {
            sessionState = new SessionState();
            sessionState.setUserId(userId);
            sessionStates.put(userId, sessionState);
        }

        return sessionState;
    }

    public void putSessionState(long userId, SessionState sessionState) {
        sessionStates.put(userId, sessionState);
    }

}
