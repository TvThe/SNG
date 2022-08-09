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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Notification;
import modal.Post;
import modal.User;

/**
 *
 * @author TuanAnh
 */
public class NotificationDao {

    private DBContext db = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;
    private Connection connection = null;

    public NotificationDao() {
        db = new DBContext();
    }

    public void addNotification(int postID, int userIDs, int type) {
//        String sql = "DECLARE @CursorUserIDr INT;\n"
//                + "/* post is interacted */\n"
//                + "DECLARE @PostID INT = ?;\n"
//                + "/* User interact with post */\n"
//                + "DECLARE @UserIDs INT = ?;\n"
//                + "/* Like = 0; comment = 1 */\n"
//                + "DECLARE @Type BIT = ?;\n"
//                + "\n"
//                + "DECLARE CUR_TEST CURSOR FAST_FORWARD FOR\n"
//                + "	/* user comment in post is interacted */\n"
//                + "	SELECT c.[UserID]\n"
//                + "	  FROM [Comment] c\n"
//                + "	  WHERE c.[PostID] = @PostID AND c.[UserID] != @UserIDs\n"
//                + "	union\n"
//                + "	/* user like in post is interacted */\n"
//                + "	SELECT l.[UserID]\n"
//                + "	  FROM [Like] l\n"
//                + "	  WHERE l.[PostID] = @PostID AND l.[UserID] != @UserIDs\n"
//                + "	union\n"
//                + "	/* user who created that post */\n"
//                + "	SELECT [UserID] FROM [Post]\n"
//                + "	  WHERE [ID] = @PostID AND [UserID] != @UserIDs\n"
//                + "OPEN CUR_TEST\n"
//                + "FETCH NEXT FROM CUR_TEST INTO @CursorUserIDr\n"
//                + "\n"
//                + "WHILE @@FETCH_STATUS = 0\n"
//                + "BEGIN\n"
//                + "	INSERT INTO [Notification]\n"
//                + "           ([UserIDr]\n"
//                + "           ,[UserIDs]\n"
//                + "           ,[PostID]\n"
//                + "           ,[type])\n"
//                + "     VALUES\n"
//                + "           (@CursorUserIDr\n"
//                + "           ,@UserIDs\n"
//                + "           ,@PostID\n"
//                + "           ,@Type)\n"
//                + "	FETCH NEXT FROM CUR_TEST INTO @CursorUserIDr\n"
//                + "END\n"
//                + "\n"
//                + "CLOSE CUR_TEST\n"
//                + "DEALLOCATE CUR_TEST";
        String sql = "/*insert notification with (userID tuong tac) and (postID duoc tuong tac)*/\n"
                + "DECLARE @CursorUserIDr INT;\n"
                + "/* post is interacted */\n"
                + "DECLARE @PostID INT = ?;\n"
                + "/* User interact with post */\n"
                + "DECLARE @UserIDs INT = ?;\n"
                + "/* Like = 0; comment = 1 */\n"
                + "DECLARE @Type BIT = ?;\n"
                + "\n"
                + "DECLARE CUR_TEST CURSOR FAST_FORWARD FOR\n"
                + "	/* user comment in post is interacted */\n"
                + "	SELECT c.[UserID]\n"
                + "	  FROM [Comment] c\n"
                + "	  WHERE c.[PostID] = @PostID AND c.[UserID] != @UserIDs\n"
                + "	union\n"
                + "	/* user like in post is interacted */\n"
                + "	SELECT l.[UserID]\n"
                + "	  FROM [Like] l\n"
                + "	  WHERE l.[PostID] = @PostID AND l.[UserID] != @UserIDs\n"
                + "	union\n"
                + "	/* user who created that post */\n"
                + "	SELECT [UserID] FROM [Post]\n"
                + "	  WHERE [ID] = @PostID AND [UserID] != @UserIDs\n"
                + "OPEN CUR_TEST\n"
                + "FETCH NEXT FROM CUR_TEST INTO @CursorUserIDr\n"
                + "\n"
                + "WHILE @@FETCH_STATUS = 0\n"
                + "BEGIN\n"
                + "	/* notification not exist -> add new */\n"
                + "	IF NOT EXISTS (SELECT * FROM [Notification] WHERE [UserIDr] = @CursorUserIDr AND [UserIDs] = @UserIDs AND [PostID] = @PostID AND [type] = @Type)\n"
                + "		BEGIN\n"
                + "			INSERT INTO [Notification]\n"
                + "				([UserIDr]\n"
                + "				,[UserIDs]\n"
                + "				,[PostID]\n"
                + "				,[type])\n"
                + "			VALUES\n"
                + "				(@CursorUserIDr\n"
                + "				,@UserIDs\n"
                + "				,@PostID\n"
                + "				,@Type)\n"
                + "		END\n"
                + "	/* notification existed and seen -> update time and seen status to 0 */\n"
                + "	IF EXISTS (SELECT * FROM [Notification] WHERE [UserIDr] = @CursorUserIDr AND [UserIDs] = @UserIDs AND [PostID] = @PostID AND [type] = @Type AND [seen] = 1)\n"
                + "		BEGIN\n"
                + "			UPDATE [Notification]\n"
                + "			SET [time] = GETDATE()\n"
                + "			    ,[seen] = 0\n"
                + "			WHERE [UserIDr] = @CursorUserIDr AND [UserIDs] = @UserIDs AND [PostID] = @PostID AND [type] = @Type\n"
                + "		END\n"
                + "	/* notification existed but not seen -> update time */\n"
                + "	IF EXISTS (SELECT * FROM [Notification] WHERE [UserIDr] = @CursorUserIDr AND [UserIDs] = @UserIDs AND [PostID] = @PostID AND [type] = @Type AND [seen] = 0)\n"
                + "		BEGIN\n"
                + "			UPDATE [Notification]\n"
                + "			SET [time] = GETDATE()\n"
                + "			WHERE [UserIDr] = @CursorUserIDr AND [UserIDs] = @UserIDs AND [PostID] = @PostID AND [type] = @Type\n"
                + "		END\n"
                + "	FETCH NEXT FROM CUR_TEST INTO @CursorUserIDr\n"
                + "END\n"
                + "\n"
                + "CLOSE CUR_TEST\n"
                + "DEALLOCATE CUR_TEST";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            stm.setInt(2, userIDs);
            stm.setInt(3, type);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<Notification> getListNotification(int userId) {
        ArrayList<Notification> l = new ArrayList<>();
        String sql = "DECLARE @UserIDr INT = ?\n"
                + "\n"
                + "SELECT n.[ID]\n"
                + "      ,n.[UserIDr]\n"
                + "      ,n.[UserIDs]\n"
                + "	 ,us.[displayname] as 'DisplaynameSend'\n"
                + "      ,n.[PostID]\n"
                + "	 ,p.[UserID] as 'PosterID'\n"
                + "      ,n.[type]\n"
                + "      ,n.[time]\n"
                + "      ,n.[seen]\n"
                + "  FROM [Notification] n join [User] us on n.UserIDs = us.ID\n"
                + "						join [Post] p on n.PostID = p.ID\n"
                + "  WHERE n.[UserIDr] = @UserIDr\n"
                + "  ORDER BY n.[time] DESC";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                User userS = new User();
                Post p = new Post();
                User poster = new User();

                n.setID(rs.getInt("ID"));

                userS.setID(rs.getInt("UserIDs"));
                userS.setDisplayname(rs.getString("DisplaynameSend"));
                n.setUserS(userS);

                p.setID(rs.getInt("PostID"));
                poster.setID(rs.getInt("PosterID"));
                p.setUser(poster);
                n.setPost(p);

                n.setLike(!rs.getBoolean("type"));
                n.setTime(rs.getTimestamp("time"));
                n.setSeen(rs.getBoolean("seen"));
                l.add(n);
            }
        } catch (Exception ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return l;
    }

    public void updateSeenNotification(int userId) {
        String sql = "DECLARE @UserIDr INT = ?\n"
                + "\n"
                + "UPDATE [Notification]\n"
                + "   SET [seen] = 1\n"
                + " WHERE UserIDr = @UserIDr";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean checkIfHaveNewNotification(int userID) {
        boolean check = false;
        String sql = "DECLARE @UserIDr INT = ?\n"
                + "\n"
                + "SELECT * FROM [Notification]\n"
                + "  WHERE UserIDr = @UserIDr AND seen = 0";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userID);
            rs = stm.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(NotificationDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return check;
    }
}
