package com.nikguscode.TaskTimer.model.service.strategy.crudStrategy;

import com.nikguscode.TaskTimer.model.entity.Category;
import com.nikguscode.TaskTimer.model.repository.CategoryRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Getter
@Setter
public class ListOfCategories implements Transaction {

    private final CategoryRepository categoryRepository;
    private ArrayList<Category> categories;

    public ListOfCategories(CategoryRepository categoryRepository,
                            ArrayList<Category> categories) {
        this.categoryRepository = categoryRepository;
        this.categories = categories;
    }

    @Override
    public void transaction(Long userId) {
        categories = categoryRepository.findCategoryNameByUserId(userId);
    }
}
