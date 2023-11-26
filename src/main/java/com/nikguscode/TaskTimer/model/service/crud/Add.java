package com.nikguscode.TaskTimer.model.service.crud;

import com.nikguscode.TaskTimer.model.entity.Category;
import com.nikguscode.TaskTimer.model.entity.UserState;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.repository.SessionRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
@Setter
@Slf4j
public class Add {

    private CategoryRepository categoryRepository;
    private SessionRepository sessionRepository;
    private Map<Long, UserState> userStates = new HashMap<>();

    @Autowired
    public Add(CategoryRepository categoryRepository,
               SessionRepository sessionRepository) {
        this.categoryRepository = categoryRepository;
        this.sessionRepository = sessionRepository;
    }

    public void addCategory(String categoryName, String categoryDescription, Long chatId) {
        if (categoryRepository.existsByCategoryNameAndUserId(categoryName, chatId)) {
            log.warn("[Adding Category] Данная категория уже создана");
        } else {
            Category category = new Category();
            category.setCategoryName(categoryName);
            category.setCategoryDescription(categoryDescription);
            category.setUserId(chatId);
            category.setActive(false);
            categoryRepository.save(category);
        }
    }

    @Transactional
    public UserState createSession(Long chatId, String activeCategoryName, LocalDateTime startDate) {
        UserState userState = getUserState(chatId);

        userState.setActiveCategoryName(activeCategoryName);
        userState.setStartDate(startDate);
        userState = sessionRepository.save(userState);

        return userState;
    }

    @Transactional
    public void endSession(UserState userState, LocalDateTime endDate, String duration){
        sessionRepository.updateEndDateAndDurationBySessionId(
                userState.getSessionId(),
                endDate,
                duration
        );
    }

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