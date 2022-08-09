/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author TuanAnh
 */
public class Message {
    private Integer ID;
    private Integer senderID;
    private Integer receiveID;
    private String content;
    private boolean status;
    private String image;
    private Date time;
    private Set<Integer> onlineList = new HashSet<Integer>();
    public Message() {
    }

    public Message(Integer ID, Integer senderID, Integer receiveID, String content, String image, Date time) {
        this.ID = ID;
        this.senderID = senderID;
        this.receiveID = receiveID;
        this.content = content;
        this.image = image;
        this.time = time;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getSenderID() {
        return senderID;
    }

    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }

    public Integer getReceiveID() {
        return receiveID;
    }

    public void setReceiveID(Integer receiveID) {
        this.receiveID = receiveID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<Integer> getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(Set<Integer> onlineList) {
        this.onlineList = onlineList;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
    
    
}
