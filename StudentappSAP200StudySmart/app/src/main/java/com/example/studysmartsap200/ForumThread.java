package com.example.studysmartsap200;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ForumThread {
    private int id;
    private String threadTitle;
    private String username;
    private String date;
    private boolean isFavorite;
    private int replies;
    private int categoryRefId;
    private int userRefID;
    public ForumThread(){

    }
    public ForumThread(
            String threadTitle,
            String date,
            boolean isFavorite,
            int replies,
            int categoryRefId,
            String username
    )
    {
        this.threadTitle = threadTitle;
        this.date = date;
        this.isFavorite = isFavorite;
        this.replies = replies;
        this.categoryRefId = categoryRefId;
        this.username = username;
    }
    public ForumThread(
            int id,
            String threadTitle,
            String date,
            boolean isFavorite,
            int replies,
            int categoryRefId,
            String username,
            int userRefID)
    {
        this.threadTitle = threadTitle;
        this.date = date;
        this.isFavorite = isFavorite;
        this.replies = replies;
        this.categoryRefId = categoryRefId;
        this.username = username;
        this.id = id;
        this.userRefID = userRefID;
    }


    public String getThreadTitle() {
        return threadTitle;
    }

    public String getUser() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Boolean getFavorite(){
        return isFavorite;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getCategoryRefId() {
        return categoryRefId;
    }

    public void setCategoryRefId(int categoryRefId) {
        this.categoryRefId = categoryRefId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserRefID() {
        return userRefID;
    }

    public void setUserRefID(int userRefID) {
        this.userRefID = userRefID;
    }

    @Override
    public String toString() {
        return "ForumThread{" +
                "threadTitle='" + threadTitle + '\'' +
                ", username='" + username + '\'' +
                ", date='" + date + '\'' +
                ", isFavorite=" + isFavorite +
                ", replies=" + replies +
                ", categoryRefId=" + categoryRefId +
                '}';
    }
}
