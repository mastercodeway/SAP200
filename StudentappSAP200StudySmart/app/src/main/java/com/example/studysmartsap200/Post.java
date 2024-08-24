package com.example.studysmartsap200;

public class Post {
    private String postTitle;
    private String postContent;
    private String userName;
    private int userID;
    private int postID;
    private String date;
    private int threadRef;
    private int replies;
    private int likes;
    private boolean favorite;

    public Post()
    {
    }
    public Post( String content, String userName, String date,int likes, int threadId){
        this.postContent = content;
        this.userName = userName;
        this.date = date;
        this.likes = likes;
        this.threadRef = threadId;
    }
    public Post(String title, String content, String userName, String date, int replies, int likes, int threadRef){
        this.postTitle = title;
        this.postContent = content;
        this.userName = userName;
        this.date = date;
        this.replies = replies;
        this.likes = likes;
        this.threadRef = threadRef;
    }
    public Post(String postTitle, String postContent, String userName, int userID, int postID, int threadRef, int replies, int likes, String date, boolean favorite) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.userName = userName;
        this.userID = userID;
        this.postID = postID;
        this.threadRef = threadRef;
        this.replies = replies;
        this.date = date;
        this.likes = likes;
        this.favorite = favorite;
    }
    public Post(String postContent, String userName, int userID, int postID, int threadRef, int likes, String date, boolean favorite) {
        this.postContent = postContent;
        this.userName = userName;
        this.userID = userID;
        this.postID = postID;
        this.threadRef = threadRef;
        this.date = date;
        this.likes = likes;
        this.favorite = favorite;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getThreadRef() {
        return threadRef;
    }

    public void setThreadRef(int threadRef) {
        this.threadRef = threadRef;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", userName='" + userName + '\'' +
                ", userID=" + userID +
                ", postID=" + postID +
                ", date='" + date + '\'' +
                ", threadRef=" + threadRef +
                '}';
    }
}
