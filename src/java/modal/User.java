/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.sql.Date;
import java.util.List;


/**
 *
 * @author TuanAnh
 */
public class User {
    private int ID;
//typeOfAccount = 1: SNG account; 2 facebook account
    private int typeOfAccount;
    private String username;
    private String password;
    private String displayname;
    private String avatar;
    private Date dob;
    private String gender;
    private boolean admin;
    private int QuestionID;
    private String answer;
    private List<Game> gameList;

    public User() {
    }

    public User(int ID, int typeOfAccount, String username, String password, String displayname, String avatar, Date dob, String gender, boolean admin, int QuestionID, String answer, List<Game> gameList) {
        this.ID = ID;
        this.typeOfAccount = typeOfAccount;
        this.username = username;
        this.password = password;
        this.displayname = displayname;
        this.avatar = avatar;
        this.dob = dob;
        this.gender = gender;
        this.admin = admin;
        this.QuestionID = QuestionID;
        this.answer = answer;
        this.gameList = gameList;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(int typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int QuestionID) {
        this.QuestionID = QuestionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Game> getgameList() {
        return gameList;
    }

    public void setgameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    @Override
    public String toString() {
        return "User{" + "displayname=" + displayname + ", avatar=" + avatar + '}';
    }

    
    
}
