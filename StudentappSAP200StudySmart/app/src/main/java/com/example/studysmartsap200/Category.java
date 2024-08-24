package com.example.studysmartsap200;

import java.util.List;
// Klass för att spara kategorier i ett object
public class Category {
    private String categoryName;
    private int categoryID;

    private List<ForumThread> forumsThreadList;

    public Category(String categoryName) {
        this.categoryName = categoryName;


    }

    public int getCategoryID() {
        return categoryID;
    }

    public List<ForumThread> getForumsThreadList() {
        return forumsThreadList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setForumsThreadList(List<ForumThread> forumsThreadList) {
        this.forumsThreadList = forumsThreadList;
    }
}
