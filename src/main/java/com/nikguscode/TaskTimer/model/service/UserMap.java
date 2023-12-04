package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.model.entity.UserState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserMap {
    private final Map<Long, UserState> userStates = new HashMap<>();

    public UserState getUserState(long userId) {
        UserState userState = userStates.get(userId);

        if (userState == null) {
            userState = new UserState();
            userState.setUserId(userId);
            userStates.put(userId, userState);
        }

        return userState;
    }

}
