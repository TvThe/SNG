/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.User;

/**
 *
 * @author TuanAnh
 */
public class LikeDao {

    private DBContext db = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;
    private Connection connection = null;

    public LikeDao() {
        db = new DBContext();
    }

    public void likePost(int postID, int userID) {
        try {
            connection = db.getConnection();
            String sql = "DECLARE @postID INT = ?\n"
                    + "DECLARE @userID INT = ?\n"
                    + "\n"
                    + "IF EXISTS (SELECT * from [Like] WHERE [PostID]= @postID AND [UserID]= @userID)\n"
                    + "  BEGIN\n"
                    + "   	DELETE FROM [Like]\n"
                    + "      WHERE [PostID]= @postID AND [UserID]= @userID\n"
                    + "  END\n"
                    + "ELSE\n"
                    + "  BEGIN\n"
                    + "   	INSERT INTO [Like]\n"
                    + "               ([PostID]\n"
                    + "               ,[UserID])\n"
                    + "           VALUES\n"
                    + "               (@postID\n"
                    + "               ,@userID)\n"
                    + "	SELECT displayname, avatar FROM [User] WHERE [ID] = @userID\n"
                    + "  END";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            stm.setInt(2, userID);
            stm.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int amountLikeOfPost(int postID, int userID) {
        int amount = 0;
        try {
            connection = db.getConnection();
            String sql = "SELECT COUNT([UserID]) AS 'Amount' FROM [Like] WHERE PostID = ? GROUP BY PostID";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            rs = stm.executeQuery();
            while (rs.next()) {
                amount = rs.getInt("Amount");
            }
        } catch (Exception ex) {
            Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        // last digit = 1 user liked that post
        if (checkLike(postID, userID)) {
//            System.out.println(postID+", "+userID+", true");
            return amount * 10 + 1;
        } else {
//            System.out.println(postID+", "+userID+", false");
            return amount * 10 + 2;
        }
    }

    public boolean checkLike(int postID, int userID) {
        boolean check = false;
        try {
            connection = db.getConnection();
            String sql = "SELECT * FROM [Like] WHERE [PostID] = ? AND [UserID] = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            stm.setInt(2, userID);
            rs = stm.executeQuery();
            while (rs.next()) {
                check = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LikeDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return check;
    }
}
