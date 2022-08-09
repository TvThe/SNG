/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import modal.Game;
import modal.Image;

import modal.Post;
import modal.User;

/**
 *
 * @author TuanAnh
 */
public class PostDao {

    private DBContext db = null;
    private PreparedStatement stm = null;
    private ResultSet rs = null;
    private Connection connection = null;

    public ArrayList<Post> viewMorePost() {
        return null;

    }

    public PostDao() {
        db = new DBContext();
    }

// test feature    
    public ArrayList<Post> getPostList() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            db = new DBContext();
        } catch (Exception e) {

        }
        return null;
    }

// test feature    
//    public ArrayList<Post> getPostList() {
//        ArrayList<Post> posts = new ArrayList<>();
//        try {
//            String sql = "SELECT p.[ID]\n"
//                    + "      ,p.[UserID]\n"
//                    + "      ,p.[GameID]\n"
//                    + "      ,p.[content]\n"
//                    + "      ,p.[like]\n"
//                    + "      ,p.[time]\n"
//                    + "      ,u.[displayname]\n"
//                    + "      ,g.[name]\n"
//                    + "	  ,g.[image] as 'image game'\n"
//                    + "	  ,i.[image] as 'image post'\n"
//                    + "    FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
//                    + "                   left join [Game] g on p.GameID = g.ID\n"
//                    + "			  left join [image] i on p.ID = i.PostID";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                Post p = new Post();
//                Game g = new Game();
//                User u = new User();
//
//                u.setDisplayname(rs.getString("displayname"));
//                u.setID(rs.getInt("UserID"));
//
//                g.setID(rs.getInt("GameID"));
//                g.setName(rs.getString("name"));
//                g.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image game")));
//
//                p.setID(rs.getInt("ID"));
//                p.setUser(u);
//                p.setGame(g);
//                p.setContent(rs.getString("content"));
//                p.setLike(rs.getInt("like"));
//                p.setTime(rs.getDate("time"));
//                if (rs.getBytes("image post") != null) {
//                    p.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image post")));
//                } else {
//                    p.setImage("null");
//                }
//
//                posts.add(p);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return posts;
//    }
    public ArrayList<Post> loadMorePost(String type, String post_id, int id) {
        ArrayList<Post> posts = new ArrayList<>();
        String sql = "";
        switch (type) {
            case "newest":
                sql = "SELECT TOP(1) p.[ID]\n"
                        + "      ,p.[UserID]\n"
                        + "      ,p.[GameID]\n"
                        + "      ,p.[content]\n"
                        + "      ,p.[like]\n"
                        + "      ,p.[time]\n"
                        + "      ,u.[displayname]\n"
                        + "      ,u.[avatar]\n"
                        + "      ,g.[name]\n"
                        + "      ,g.[image] as 'image game'\n"
                        + "      ,i.[image] as 'image post'\n"
                        + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
                        + "                left join [Game] g on p.GameID = g.ID\n"
                        + "		       left join [image] i on p.ID = i.PostID\n"
                        + "	WHERE p.[ID] not in " + post_id
                        + "ORDER BY [time] desc";
                break;
            case "friend":
                sql = "SELECT TOP(4) p.[ID]\n"
                        + "			 ,p.[UserID]\n"
                        + "			 ,p.[GameID]\n"
                        + "			 ,p.[content]\n"
                        + "			 ,p.[like]\n"
                        + "			 ,p.[time]\n"
                        + "			 ,u.[displayname]\n"
                        + "			 ,u.[avatar]\n"
                        + "			 ,g.[name]\n"
                        + "			 ,g.[image] as 'image game'\n"
                        + "			 ,i.[image] as 'image post'\n"
                        + "			 ,f.[status]\n"
                        + "			 ,f.[UserID2] as 'friend id'\n"
                        + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
                        + "                   left join [Game] g on p.GameID = g.ID\n"
                        + "		           left join [image] i on p.ID = i.PostID\n"
                        + "				   join [Friend] f on p.UserID = f.UserID2\n"
                        + "	WHERE [status] = 1 and f.UserID1 = " + id + " and p.[ID] not in " + post_id + "\n"
                        + "UNION all\n"
                        + "SELECT p.[ID]\n"
                        + "			 ,p.[UserID]\n"
                        + "			 ,p.[GameID]\n"
                        + "			 ,p.[content]\n"
                        + "			 ,p.[like]\n"
                        + "			 ,p.[time]\n"
                        + "			 ,u.[displayname]\n"
                        + "			 ,u.[avatar]\n"
                        + "			 ,g.[name]\n"
                        + "			 ,g.[image] as 'image game'\n"
                        + "			 ,i.[image] as 'image post'\n"
                        + "			 ,f.[status]\n"
                        + "			 ,f.[UserID1] as 'friend id'\n"
                        + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
                        + "                   left join [Game] g on p.GameID = g.ID\n"
                        + "		           left join [image] i on p.ID = i.PostID\n"
                        + "				   full join [Friend] f on p.UserID = f.UserID1\n"
                        + "	WHERE [status] = 1 and f.UserID2 = " + id + " and p.[ID] not in " + post_id + "\n"
                        + "	ORDER BY [time] DESC";
                break;
            case "game":
                sql = "SELECT TOP(1) p.[ID]\n"
                        + "      ,p.[UserID]\n"
                        + "      ,p.[GameID]\n"
                        + "      ,p.[content]\n"
                        + "      ,p.[like]\n"
                        + "      ,p.[time]\n"
                        + "      ,u.[displayname]\n"
                        + "      ,u.[avatar]\n"
                        + "      ,g.[name]\n"
                        + "      ,g.[image] as 'image game'\n"
                        + "      ,i.[image] as 'image post'\n"
                        + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
                        + "                left join [Game] g on p.GameID = g.ID\n"
                        + "		       left join [image] i on p.ID = i.PostID\n"
                        + "	WHERE p.[ID] not in " + post_id
                        + "ORDER BY [time] desc";
                break;
        }
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                Game g = new Game();
                User u = new User();

                u.setDisplayname(rs.getString("displayname"));
                u.setID(rs.getInt("UserID"));
                if (rs.getBytes("avatar") != null) {
                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
                }
                g.setID(rs.getInt("GameID"));
                g.setName(rs.getString("name"));
                if (rs.getBytes("image game") != null) {
                    g.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image game")));
                } else {
                    g.setImage("null");
                }
                p.setID(rs.getInt("ID"));
                p.setUser(u);
                p.setGame(g);
                p.setContent(rs.getString("content"));

                LikeDao ldb = new LikeDao();
                p.setLike(ldb.amountLikeOfPost(rs.getInt("ID"), id));
                p.setTime(rs.getDate("time"));
                if (rs.getBytes("image post") != null) {
                    p.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image post")));
                } else {
                    p.setImage("null");
                }

                posts.add(p);
            }

        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return posts;
    }

    public Post getAPost(int postID, int userID) {
        Post p = new Post();
        try {
            String sql = "SELECT TOP(1) p.[ID]\n"
                    + "      ,p.[UserID]\n"
                    + "      ,p.[GameID]\n"
                    + "      ,p.[content]\n"
                    + "      ,p.[like]\n"
                    + "      ,p.[time]\n"
                    + "      ,u.[displayname]\n"
                    + "      ,u.[avatar]\n"
                    + "      ,g.[name]\n"
                    + "      ,g.[image] as 'image game'\n"
                    + "      ,i.[image] as 'image post'\n"
                    + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
                    + "                left join [Game] g on p.GameID = g.ID\n"
                    + "		       left join [image] i on p.ID = i.PostID\n"
                    + "	WHERE p.[ID] = ? ";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            rs = stm.executeQuery();
            while (rs.next()) {
                Game g = new Game();
                User u = new User();

                u.setDisplayname(rs.getString("displayname"));
                u.setID(rs.getInt("UserID"));
                if (rs.getBytes("avatar") != null) {
                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
                }
                g.setID(rs.getInt("GameID"));
                g.setName(rs.getString("name"));
                if (rs.getBytes("image game") != null) {
                    g.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image game")));
                } else {
                    g.setImage("null");
                }
                p.setID(rs.getInt("ID"));
                p.setUser(u);
                p.setGame(g);
                p.setContent(rs.getString("content"));

                LikeDao ldb = new LikeDao();
                p.setLike(ldb.amountLikeOfPost(rs.getInt("ID"), userID));
                p.setTime(rs.getDate("time"));
                if (rs.getBytes("image post") != null) {
                    p.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image post")));
                } else {
                    p.setImage("null");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return p;
    }

//    public ArrayList<Post> loadMorePost_newest(String s) {
//        ArrayList<Post> posts = new ArrayList<>();
//        try {
//            String sql = "SELECT TOP(1) p.[ID]\n"
//                    + "      ,p.[UserID]\n"
//                    + "      ,p.[GameID]\n"
//                    + "      ,p.[content]\n"
//                    + "      ,p.[like]\n"
//                    + "      ,p.[time]\n"
//                    + "      ,u.[displayname]\n"
//                    + "      ,u.[avatar]\n"
//                    + "      ,g.[name]\n"
//                    + "      ,g.[image] as 'image game'\n"
//                    + "      ,i.[image] as 'image post'\n"
//                    + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
//                    + "                left join [Game] g on p.GameID = g.ID\n"
//                    + "		       left join [image] i on p.ID = i.PostID\n";
//            if (!s.equals("()")) {
//                sql += "	WHERE p.[ID] not in " + s;
//            }
//            sql += "ORDER BY [time] desc";
//
//            connection = db.getConnection();
//            stm = connection.prepareStatement(sql);
//            rs = stm.executeQuery();
//
//            while (rs.next()) {
//                Post p = new Post();
//                Game g = new Game();
//                User u = new User();
//
//                u.setDisplayname(rs.getString("displayname"));
//                u.setID(rs.getInt("UserID"));
//                if (rs.getBytes("avatar") != null) {
//                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
//                }
//                g.setID(rs.getInt("GameID"));
//                g.setName(rs.getString("name"));
//                if (rs.getBytes("image game") != null) {
//                    g.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image game")));
//                } else {
//                    g.setImage("null");
//                }
//                p.setID(rs.getInt("ID"));
//                p.setUser(u);
//                p.setGame(g);
//                p.setContent(rs.getString("content"));
//                p.setLike(rs.getInt("like"));
//                p.setTime(rs.getDate("time"));
//                if (rs.getBytes("image post") != null) {
//                    p.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image post")));
//                } else {
//                    p.setImage("null");
//                }
//
//                posts.add(p);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (stm != null) {
//                try {
//                    stm.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return posts;
//    }
//
//    public ArrayList<Post> loadMorePost_friend(String s, int ID) {
//        ArrayList<Post> posts = new ArrayList<>();
//        try {
//            String sql = "SELECT p.[ID]\n"
//                    + "			 ,p.[UserID]\n"
//                    + "			 ,p.[GameID]\n"
//                    + "			 ,p.[content]\n"
//                    + "			 ,p.[like]\n"
//                    + "			 ,p.[time]\n"
//                    + "			 ,u.[displayname]\n"
//                    + "			 ,u.[avatar]\n"
//                    + "			 ,g.[name]\n"
//                    + "			 ,g.[image] as 'image game'\n"
//                    + "			 ,i.[image] as 'image post'\n"
//                    + "			 ,f.[status]\n"
//                    + "			 ,f.[UserID2] as 'friend id'\n"
//                    + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
//                    + "                   left join [Game] g on p.GameID = g.ID\n"
//                    + "		           left join [image] i on p.ID = i.PostID\n"
//                    + "				   join [Friend] f on p.UserID = f.UserID2\n"
//                    + "	WHERE [status] = 1 and f.UserID1 = ? and p.[ID] not in " + s + "\n"
//                    + "UNION all\n"
//                    + "SELECT p.[ID]\n"
//                    + "			 ,p.[UserID]\n"
//                    + "			 ,p.[GameID]\n"
//                    + "			 ,p.[content]\n"
//                    + "			 ,p.[like]\n"
//                    + "			 ,p.[time]\n"
//                    + "			 ,u.[displayname]\n"
//                    + "			 ,u.[avatar]\n"
//                    + "			 ,g.[name]\n"
//                    + "			 ,g.[image] as 'image game'\n"
//                    + "			 ,i.[image] as 'image post'\n"
//                    + "			 ,f.[status]\n"
//                    + "			 ,f.[UserID1] as 'friend id'\n"
//                    + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
//                    + "                   left join [Game] g on p.GameID = g.ID\n"
//                    + "		           left join [image] i on p.ID = i.PostID\n"
//                    + "				   full join [Friend] f on p.UserID = f.UserID1\n"
//                    + "	WHERE [status] = 1 and f.UserID2 = ? and p.[ID] not in " + s + "\n"
//                    + "	ORDER BY [time] DESC";
////            if (!s.equals("()")) {
////                sql += "	WHERE p.[ID] not in " + s;
////            }
////            sql += "ORDER BY [time] desc";
//            connection = db.getConnection();
//            stm = connection.prepareStatement(sql);
//
//            stm.setInt(1, ID);
//            stm.setInt(2, ID);
//
//            rs = stm.executeQuery();
//
//            int count = 4;
//            while (rs.next()) {
//                count--;
//                Post p = new Post();
//                Game g = new Game();
//                User u = new User();
//
//                u.setDisplayname(rs.getString("displayname"));
//                u.setID(rs.getInt("UserID"));
//                if (rs.getBytes("avatar") != null) {
//                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
//                }
//
//                g.setID(rs.getInt("GameID"));
//                g.setName(rs.getString("name"));
//                g.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image game")));
//
//                p.setID(rs.getInt("ID"));
//                p.setUser(u);
//                p.setGame(g);
//                p.setContent(rs.getString("content"));
//                p.setLike(rs.getInt("like"));
//                p.setTime(rs.getDate("time"));
//                if (rs.getBytes("image post") != null) {
//                    p.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image post")));
//                } else {
//                    p.setImage("null");
//                }
//
//                posts.add(p);
//                if (count == 0) {
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (stm != null) {
//                try {
//                    stm.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return posts;
//    }
//
//    public ArrayList<Post> loadMorePost_game(String s) {
//        ArrayList<Post> posts = new ArrayList<>();
//        try {
//            String sql = "SELECT TOP(1) p.[ID]\n"
//                    + "      ,p.[UserID]\n"
//                    + "      ,p.[GameID]\n"
//                    + "      ,p.[content]\n"
//                    + "      ,p.[like]\n"
//                    + "      ,p.[time]\n"
//                    + "      ,u.[displayname]\n"
//                    + "      ,u.[avatar]\n"
//                    + "      ,g.[name]\n"
//                    + "      ,g.[image] as 'image game'\n"
//                    + "      ,i.[image] as 'image post'\n"
//                    + "	FROM ([Post] p left join [User] u on p.UserID = u.ID)\n"
//                    + "                left join [Game] g on p.GameID = g.ID\n"
//                    + "		       left join [image] i on p.ID = i.PostID\n";
//            if (!s.equals("()")) {
//                sql += "	WHERE p.[ID] not in " + s;
//            }
//            sql += "ORDER BY [time] desc";
//
//            connection = db.getConnection();
//            stm = connection.prepareStatement(sql);
//            rs = stm.executeQuery();
//
//            while (rs.next()) {
//                Post p = new Post();
//                Game g = new Game();
//                User u = new User();
//
//                u.setDisplayname(rs.getString("displayname"));
//                u.setID(rs.getInt("UserID"));
//                if (rs.getBytes("avatar") != null) {
//                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("avatar")));
//                }
//
//                g.setID(rs.getInt("GameID"));
//                g.setName(rs.getString("name"));
//                g.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image game")));
//
//                p.setID(rs.getInt("ID"));
//                p.setUser(u);
//                p.setGame(g);
//                p.setContent(rs.getString("content"));
//                p.setLike(rs.getInt("like"));
//                p.setTime(rs.getDate("time"));
//                if (rs.getBytes("image post") != null) {
//                    p.setImage(Base64.getEncoder().encodeToString(rs.getBytes("image post")));
//                } else {
//                    p.setImage("null");
//                }
//
//                posts.add(p);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (stm != null) {
//                try {
//                    stm.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return posts;
//    }
    public Integer createPost(Post p) {
        Integer id = null;
        try {
            connection = db.getConnection();
            String sql = "INSERT INTO [dbo].[Post]\n"
                    + "           ([UserID]\n"
                    + "           ,[content]\n"
                    + "           ,[GameID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?)";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, p.getUser().getID());
            stm.setString(2, p.getContent());
            stm.setInt(3, p.getGame().getID());
            stm.executeUpdate();
            try {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception e1) {

            }
        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return id;
    }

    public int getTotalPost(int id) {
        String sql = "SELECT count([ID])as 'count'\n"
                + "  FROM [Post]\n"
                + "  where [UserID] = ? ";
        int count = 0;
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }

        return count;
    }

    public ArrayList<Post> getHighLightPost(int id) {
        String sql = "Select top(3) u.ID as 'Userid',u.username as 'Username',u.displayname as 'Displayname',\n"
                + "u.avatar as 'USerAvatar',g.ID as 'GameID',g.[name] as 'Gamename',p.content as 'postcontent',\n"
                + "p.[like] as 'plike',p.[time] as 'ptime' \n"
                + "from  Post p \n"
                + "left join [User] u on p.UserID = u.ID\n"
                + "left join Game g on g.ID = p.GameID\n"
                + "where UserID = ? \n"
                + "order by [like] desc";
        ArrayList<Post> getAllHighLightPost = new ArrayList<>();
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setID(rs.getInt("Userid"));
                u.setUsername(rs.getString("Username"));
                u.setDisplayname(rs.getString("Displayname"));
                if (rs.getBytes("avatar") != null) {
                    u.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("USerAvatar")));
                } else {
                    u.setAvatar(null);
                }
                Game g = new Game();
                g.setID(rs.getInt("GameID"));
                g.setName(rs.getString("Gamename"));

                Post p = new Post();
                p.setUser(u);
                p.setGame(g);
                p.setContent(rs.getString("postcontent"));
                p.setLike(rs.getInt("plike"));
                p.setTime(rs.getDate("ptime"));

                getAllHighLightPost.add(p);
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return getAllHighLightPost;
    }

    public Post getListPostByID(Integer PostID) {

        try {
            connection = db.getConnection();
            String sql = "Select p.ID,p.UserID,p.GameID,p.content,p.[like],p.[time],g.name,g.[image] as 'image game',i.[image] as'image post' from Post p left join Game g ON p.GameID = g.ID left join [Image] i ON i.PostID = p.ID where p.ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, PostID);
            rs = stm.executeQuery();
            while (rs.next()) {
                Post p = new Post();
                p.setID(rs.getInt("ID"));
                User u = new User();
                u.setID(rs.getInt("UserID"));
                p.setUser(u);
                p.setContent(rs.getString("content"));
                p.setLike(rs.getInt("like"));
                p.setTime(rs.getTimestamp("time"));
                Game g = new Game();
                g.setID(rs.getInt("GameID"));
                g.setName(rs.getString("name"));
                String imageGame = rs.getBytes("image game") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("image game"));
                g.setImage(imageGame);
                p.setGame(g);
                String imagePost = rs.getBytes("image post") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("image post"));
                p.setImage(imagePost);
                return p;
            }
        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    public void deletePost(int postID, int uID) {
        try {
            connection = db.getConnection();
            String sql = "DELETE FROM [dbo].[Post]\n"
                    + "      WHERE ID = ? And UserID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, postID);
            stm.setInt(2, uID);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
    }
//    public Image getImageByPostID(Integer PostID){
//        try {
//            String sql ="select p.ID,i.[image] from Post p inner join [Image] i on p.ID = i.PostID where p.ID = ?";
//            PreparedStatement stm = connection.prepareStatement(sql);
//            stm.setInt(1, PostID);
//            ResultSet rs = stm.executeQuery();
//            while(rs.next()){
//                Image i = new Image();
//                i.setPostID(rs.getInt("ID"));
//                String image = rs.getBytes("image") == null ? null : Base64.getEncoder().encodeToString(rs.getBytes("image"));
//                i.setImage(image);
//                return i;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return null;
//    }

    public void updatePost(int postID, int gID, String content) {
        try {
            connection = db.getConnection();
            String sql = "UPDATE [Post]\n"
                    + "   SET [GameID] = ?\n"
                    + "      ,[content] = ?\n"
                    + " WHERE [ID] = ?";
            stm = connection.prepareStatement(sql);

            stm.setInt(1, gID);
            stm.setString(2, content);
            stm.setInt(3, postID);

            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<Post> displayhighlight(Integer id) {
        String sql = "select top(3)p.ID as 'PostID',p.UserID as 'UserID',u.displayname as 'udisplay',u.avatar as 'userava',p.GameID as 'gameid', g.[name] as 'gamename',g.[image]"
                + " as 'gameimage',p.content as 'postcontent',p.[like] as 'Postlike',p.[time] as 'Uploadtime' from Post p  inner join Game g  on p.GameID = g.ID join [User]"
                + " u on p.UserID = u.ID where p.UserID = ? order by [like] desc ";
        ArrayList<Post> getHighlight = new ArrayList<>();
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Post ptemp = new Post();
                ptemp.setID(rs.getInt("PostID"));
                User utemp = new User();
                utemp.setID(rs.getInt("UserID"));
                utemp.setDisplayname(rs.getString("udisplay"));
                if (rs.getBytes("userava") != null) {
                    utemp.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("userava")));
                } else {
                    utemp.setAvatar(null);
                }
                ptemp.setUser(utemp);
                Game gtemp = new Game();
                gtemp.setID(rs.getInt("gameid"));
                gtemp.setName(rs.getString("gamename"));
                if (rs.getBytes("gameimage") != null) {
                    gtemp.setImage(Base64.getEncoder().encodeToString(rs.getBytes("gamename")));
                } else {
                    gtemp.setImage(null);
                }
                ptemp.setGame(gtemp);
                ptemp.setContent(rs.getString("postcontent"));
                ptemp.setLike(rs.getInt("Postlike"));
                ptemp.setTime(rs.getDate("Uploadtime"));
                getHighlight.add(ptemp);

            }

        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
        return getHighlight;
    }

    public ArrayList<Post> SearchPost(String content) {
        ArrayList<Post> searchPosts = new ArrayList<>();
        try {
            String sql = "Select p.ID,p.UserID,u.displayname,u.avatar,p.GameID,p.content,p.[like],p.[time],g.[image] as 'image game',i.[image] as'image post' from Post p left join [User] u on p.UserID = u.ID left join Game g ON p.GameID = g.ID left join [Image] i ON i.PostID = p.ID where p.content like ?";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + content + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setID(rs.getInt("ID"));
                User user = new User();
                user.setID(rs.getInt("UserID"));
                user.setDisplayname(rs.getString("displayname"));
                String avatar = rs.getBytes("avatar") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("avatar"));
                user.setAvatar(avatar);
                post.setUser(user);
                Game game = new Game();
                game.setID(rs.getInt("GameID"));
                String imageGame = rs.getBytes("image game") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("image game"));
                game.setImage(imageGame);
                post.setGame(game);
                post.setImage(imageGame);
                post.setContent(rs.getString("content"));
                post.setLike(rs.getInt("like"));
                post.setTime(rs.getDate("time"));
                String imagePost = rs.getBytes("image post") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("image post"));
                post.setImage(imagePost);
                searchPosts.add(post);

            }
        } catch (Exception ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchPosts;
    }

    public static void main(String[] args) {
        PostDao db = new PostDao();
        ArrayList<Post> posts = db.getHighLightPost(2);
        for(Post p : posts){
            System.out.println(p.getContent());
        }
    }

//    public static void main(String[] args) {
//        PostDBContext db = new PostDBContext();
//        ArrayList<Post> trys = db.displayhighlight(2);
//        for (Post p : trys) {
//            System.out.println(p);
//        }
//    }
//    public static void main(String[] args) {
//        PostDBContext db = new PostDBContext();
//        ArrayList<Post> trys = db.displayhighlight(2);
//        for (Post p : trys) {
//            System.out.println(p);
//        }
//    }
}
