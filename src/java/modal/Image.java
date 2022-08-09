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
public class Image {
    private int postID;
    private int PurID; 
    private String image;

    public Image() {
    }

    public Image(int postID, int PurID, String image) {
        this.postID = postID;
        this.PurID = PurID;
        this.image = image;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getPurID() {
        return PurID;
    }

    public void setPurID(int PurID) {
        this.PurID = PurID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
