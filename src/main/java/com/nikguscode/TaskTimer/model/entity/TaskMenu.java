package com.nikguscode.TaskTimer.model.entity;

import java.util.Map;

//@AutoValue
public class TaskMenu {

    private Map<Integer, String> categoryList;

    public TaskMenu() {
    }

    public TaskMenu(Map<Integer, String> categoryList) {
        this.categoryList = categoryList;
    }

    public Map<Integer, String> getCategoryList() {
        return getCategoryList();
    }

    public void setCategoryList(Map<Integer, String> categoryList) {
        this.categoryList = categoryList;
    }

}
