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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Comment;
import modal.User;

/**
 *
 * @author TuanAnh
 */
public class CommentDao {

    private DBContext db = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;
    private Connection connection = null;

    public CommentDao() {
        db = new DBContext();
    }

    public ArrayList<Comment> getCommentList(int postID) {
        ArrayList<Comment> l = new ArrayList<>();
        try {
            String sql = "SELECT c.[ID]\n"
                    + "      ,c.[PostID]\n"
                    + "      ,c.[UserID]\n"
                    + "      ,c.[content]\n"
                    + "      ,c.[time]\n"
                    + "	     ,u.[displayname]\n"
                    + "	     ,u.[avatar]\n"
                    + "  FROM [Comment] c join [User] u on c.UserID = u.ID\n"
                    + "  WHERE [PostID] = ?\n"
                    + "  ORDER BY c.[time] desc";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            rs = stm.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("ID"));
                c.setPostID(postID);
                c.setContent(rs.getString("content"));
                c.setTime(rs.getTimestamp("time"));
            
                User u = new User();
                u.setID(rs.getInt("UserID"));
                u.setDisplayname(rs.getString("displayname"));
                if (rs.getBytes("avatar") != null) {
                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
                }
                c.setUser(u);
                l.add(c);
            }

        } catch (Exception ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return l;
    }

    public int createComment(int postID, int userID, String content) {
        int id = 0;
        try {
            String sql = "INSERT INTO [Comment]\n"
                    + "           ([PostID]\n"
                    + "           ,[UserID]\n"
                    + "           ,[content])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, postID);
            stm.setInt(2, userID);
            stm.setString(3, content);
            stm.executeUpdate();
            try {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return id;
    }

    public void deleteComment(int cmtID) {
        try {
            String sql = "DELETE FROM [Comment]\n"
                    + "      WHERE [ID] = ?";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, cmtID);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void updateComment(int cmtID, String text) {
        try {
            String sql = "UPDATE [Comment]\n"
                    + "   SET [content] = ?\n"
                    + " WHERE [ID] = ?";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setString(1, text);
            stm.setInt(2, cmtID);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public Comment getCommentById(int id) {
        Comment c = new Comment();
        try {
            String sql = "SELECT c.[ID]\n"
                    + "      ,c.[PostID]\n"
                    + "      ,c.[UserID]\n"
                    + "      ,c.[content]\n"
                    + "      ,c.[time]\n"
                    + "	  ,u.[displayname]\n"
                    + "	  ,u.[avatar]\n"
                    + "  FROM [Comment] c join [User] u on c.UserID = u.ID\n"
                    + "  WHERE c.[ID] = ?";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt("ID"));
                c.setPostID(id);
                c.setContent(rs.getString("content"));
                c.setTime(rs.getTimestamp("time"));

                User u = new User();
                u.setID(rs.getInt("UserID"));
                u.setDisplayname(rs.getString("displayname"));
                if (rs.getBytes("avatar") != null) {
                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
                }
                c.setUser(u);
            }
        } catch (Exception ex) {
            Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CommentDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return c;
    }
}
