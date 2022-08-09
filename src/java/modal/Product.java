/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author TuanAnh
 */
public class Product {
    private int PurID;
    private int UserID;
    private int gameID;
    private int price;
    private String name;
    private String description;
    private boolean stock;
    private Date time;
    private ArrayList<String> images;
    private String gameName;
    private String imageGame;
    private String displaynameUser;
    public Product() {
    }

    public Product(int PurID, int UserID, int gameID, int price, String name, String description, boolean stock, Date time) {
        this.PurID = PurID;
        this.UserID = UserID;
        this.gameID = gameID;
        this.price = price;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.time = time;
    }

    public int getPurID() {
        return PurID;
    }

    public void setPurID(int PurID) {
        this.PurID = PurID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

   

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getImageGame() {
        return imageGame;
    }

    public void setImageGame(String imageGame) {
        this.imageGame = imageGame;
    }

    public String getDisplaynameUser() {
        return displaynameUser;
    }

    public void setDisplaynameUser(String displaynameUser) {
        this.displaynameUser = displaynameUser;
    }
    
}
