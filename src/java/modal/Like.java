/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

/**
 *
 * @author TuanAnh
 */
public class Like {
    private int UserID;
    private int PostID;

    public Like() {
    }

    public Like(int UserID, int PostID) {
        this.UserID = UserID;
        this.PostID = PostID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int PostID) {
        this.PostID = PostID;
    }
    
}
