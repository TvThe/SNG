/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

/**
 *
 * @author LENNOVO
 */
public class Report {

    private int purID;
    private int postID;
    private int userID;
    private String reportMessage;

    public Report() {
    }

    public Report(int purID, int postID, int userID, String reportMessage) {
        this.purID = purID;
        this.postID = postID;
        this.userID = userID;
        this.reportMessage = reportMessage;
    }

    public int getPurID() {
        return purID;
    }

    public void setPurID(int purID) {
        this.purID = purID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

}
