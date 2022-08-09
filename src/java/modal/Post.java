/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.util.Date;
import java.util.List;

/**
 *
 * @author TuanAnh
 */
public class Post {
    private int ID;
    private User user;
    private Game game;
    private String content;
    private int like;
    private Date time;
    private String image;

    public Post() {
    }

//    public Post(int ID, User user, Game game, String content, int like, Date time) {
//        this.ID = ID;
//        this.user = user;
//        this.game = game;
//        this.content = content;
//        this.like = like;
//        this.time = time;
//    }

    public Post(int ID, User user, Game game, String content, int like, Date time, String image) {
        this.ID = ID;
        this.user = user;
        this.game = game;
        this.content = content;
        this.like = like;
        this.time = time;
        this.image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
