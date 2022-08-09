/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.util.Date;

/**
 *
 * @author TuanAnh
 */
public class Notification {
    private int ID;
    private User userR;
    private User userS;
    private Post post;
    private boolean like;
    private Date time;
    private boolean seen;

    public Notification() {
    }
    
    public Notification(int ID, User userR, User userS, Post post, boolean like, Date time, boolean seen) {
        this.ID = ID;
        this.userR = userR;
        this.userS = userS;
        this.post = post;
        this.like = like;
        this.time = time;
        this.seen = seen;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getUserR() {
        return userR;
    }

    public void setUserR(User userR) {
        this.userR = userR;
    }

    public User getUserS() {
        return userS;
    }

    public void setUserS(User userS) {
        this.userS = userS;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
    
//    private int ID;
//    private int UserID;
//    private int PostID;
//    private String typeOfNotification;
//    private String content;
//    private Date time;
//
//    public Notification() {
//    }
//
//    public Notification(int ID, int UserID, int PostID, String typeOfNotification, String content, Date time) {
//        this.ID = ID;
//        this.UserID = UserID;
//        this.PostID = PostID;
//        this.typeOfNotification = typeOfNotification;
//        this.content = content;
//        this.time = time;
//    }
//
//    public int getID() {
//        return ID;
//    }
//
//    public void setID(int ID) {
//        this.ID = ID;
//    }
//
//    public int getUserID() {
//        return UserID;
//    }
//
//    public void setUserID(int UserID) {
//        this.UserID = UserID;
//    }
//
//    public int getPostID() {
//        return PostID;
//    }
//
//    public void setPostID(int PostID) {
//        this.PostID = PostID;
//    }
//
//    public String getTypeOfNotification() {
//        return typeOfNotification;
//    }
//
//    public void setTypeOfNotification(String typeOfNotification) {
//        this.typeOfNotification = typeOfNotification;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public Date getTime() {
//        return time;
//    }
//
//    public void setTime(Date time) {
//        this.time = time;
//    }

}
