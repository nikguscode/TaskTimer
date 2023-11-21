package com.nikguscode.TaskTimer.model.service.crud;

import com.nikguscode.TaskTimer.model.entity.Category;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import com.nikguscode.TaskTimer.model.service.telegramCore.BotData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Slf4j
public class AddCategory {

    private CategoryRepository categoryRepository;

    @Autowired
    public AddCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(String categoryName, String categoryDescription, Long chatId) {
        if (categoryRepository.existsByCategoryName(categoryName)) {
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
}