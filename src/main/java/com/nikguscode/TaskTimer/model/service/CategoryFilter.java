package com.nikguscode.TaskTimer.model.service;

import com.nikguscode.TaskTimer.model.dal.GetCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Getter
@Setter
public class CategoryFilter {

    private final GetCategory getCategory;
    private int totalPages = 0;
    private int currentPage = 0;
    private ArrayList<String> currentCategories;
    private ArrayList<String> currentCallback;

    @Autowired
    public CategoryFilter(GetCategory getCategory) {
        this.getCategory = getCategory;
    }

    private void createCategoriesArray() {
        int length = getCategory.getResult().size();
        currentCategories = new ArrayList<>();

        if (length % 6 == 0) {
            totalPages = length / 6;

            for (int i = ((6 * (currentPage - 1))); (i + (6 * (currentPage - 1))) < 6; i++) {
                currentCategories.add(getCategory.getResult().get(i));
            }

        }

        if (length % 6 != 0) {
            totalPages = (currentPage / 6) + 1;

            if (length < 6) {

                for (int j = 0; j < length; j++) {
                    currentCategories.add(getCategory.getResult().get(j));
                }

            } else {

                for (int b = ((6 * (currentPage - 1))); (b + (6 * (currentPage - 1))) < 6; b++) {
                    currentCategories.add(getCategory.getResult().get(b));
                }

                for (int s = (6 * (currentPage - 2)); s < (s + (6 * (currentPage - 1)) + currentPage % 6); s++) {
                    currentCategories.add(getCategory.getResult().get(s));
                }

            }

        }

    }

    public void getCategories() {
        createCategoriesArray();
    }

    public void clearArrays() {
        currentCategories.clear();
        currentCallback.clear();
    }

}
