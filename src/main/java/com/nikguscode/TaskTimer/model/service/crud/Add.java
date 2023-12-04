package com.nikguscode.TaskTimer.model.service.crud;

import com.nikguscode.TaskTimer.model.entity.Category;
import com.nikguscode.TaskTimer.model.entity.SessionState;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.repository.SessionRepository;
import com.nikguscode.TaskTimer.model.service.SessionMap;
import com.nikguscode.TaskTimer.model.service.UserMap;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
@Slf4j
public class Add {

    private CategoryRepository categoryRepository;
    private SessionRepository sessionRepository;
    private UserMap userMap;
    private SessionMap sessionMap;

    @Autowired
    public Add(CategoryRepository categoryRepository,
               SessionRepository sessionRepository,
               UserMap userMap,
               SessionMap sessionMap) {
        this.categoryRepository = categoryRepository;
        this.sessionRepository = sessionRepository;
        this.userMap = userMap;
        this.sessionMap = sessionMap;
    }

    public void addCategory(String categoryName, String categoryDescription, Long chatId) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryDescription);
            category.setUserId(chatId);
            category.setActive(false);
            categoryRepository.save(category);
    }

    @Transactional
    public void createSession(Long userId, String activeCategoryName, LocalDateTime startDate) {
        SessionState sessionState = new SessionState();
        sessionState.setUserId(userId);
        sessionState.setActiveCategoryName(activeCategoryName);
        sessionState.setStartDate(startDate);
        sessionRepository.save(sessionState);

        log.debug("[CREATE SESSION] sessionId: {}", sessionState.getSessionId());
        log.debug("[CREATE SESSION] sessionObject: {}", sessionState);

        sessionMap.putSessionState(userId, sessionState);
    }

    @Transactional
    public void endSession(SessionState sessionState, LocalDateTime endDate, long duration){
        sessionRepository.finishSession(
                sessionState.getSessionId(),
                endDate,
                duration
        );
    }

}