package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.model.dal.GetCategory;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Getter
public class CategoryTransformation {

    private final GetCategory getCategory;
    private ArrayList<String> categoryList;

    public CategoryTransformation(GetCategory getCategory) {
        this.getCategory = getCategory;
    }

    public void transformToString() {
        categoryList = new ArrayList<>();

        int length = getCategory.getResult().size();
        for (int currentIndex = 0; currentIndex < length; currentIndex++) {
            String tempName = getCategory.getResult().get(currentIndex).toString();
            categoryList.add(tempName);
        }

    }

}
