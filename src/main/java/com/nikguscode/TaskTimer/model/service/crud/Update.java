package com.nikguscode.TaskTimer.model.service.crud;

import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Update {

    private final CategoryRepository categoryRepository;

    @Autowired
    public Update(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void nameTransaction(String currentEntry, Long userId, String updatedEntry) {
        categoryRepository.updateCategoryNameByCategoryNameAndUserId(
                currentEntry,
                userId,
                updatedEntry
        );
    }

    public void descriptionTransaction(String currentEntryName, Long userId, String updatedEntry) {
        categoryRepository.updateCategoryDescriptionByCategoryName(
                currentEntryName,
                userId,
                updatedEntry
        );
    }

    public void setActive(String categoryName, Long userId, Boolean isActive) {
        categoryRepository.setActive(
                categoryName,
                userId,
                isActive
        );
    }

}
