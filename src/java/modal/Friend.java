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
public class Friend {
    private int FriendID;
    private User UserID1;
    private User UserID2;
    private int status;
    private Date time;

    public Friend() {
    }

    public Friend(int FriendID, User UserID1, User UserID2, int status, Date time) {
        this.FriendID = FriendID;
        this.UserID1 = UserID1;
        this.UserID2 = UserID2;
        this.status = status;
        this.time = time;
    }

    public int getFriendID() {
        return FriendID;
    }

    public void setFriendID(int FriendID) {
        this.FriendID = FriendID;
    }

    public User getUserID1() {
        return UserID1;
    }

    public void setUserID1(User UserID1) {
        this.UserID1 = UserID1;
    }

    public User getUserID2() {
        return UserID2;
    }

    public void setUserID2(User UserID2) {
        this.UserID2 = UserID2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
}
