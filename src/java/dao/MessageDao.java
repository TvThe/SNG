/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import modal.Message;
import modal.User;

/**
 *
 * @author window
 */
public class MessageDao {

    DBContext db = new DBContext();
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection connection = null;
    public MessageDao() {
        db = new DBContext();
    }

    public User loadUserByID(Integer id) {
        User user = new User();
        try {
            connection = db.getConnection();
            String sql = "select [User].ID,[User].displayname,[User].avatar from [User] where ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {

                user.setID(rs.getInt(1));
                user.setDisplayname(rs.getString(2));
                String avatar = rs.getBytes(3) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(3));
                user.setAvatar(avatar);
            }

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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
        return user;
    }

    public List<Message> loadMessage(Integer senderID, Integer receiveID) {
        List messageList = new ArrayList<Message>();
        try {
            connection = db.getConnection();
            String sql = "SELECT [ID]\n"
                    + "      ,[SenderID]\n"
                    + "      ,[ReceiveID]\n"
                    + "      ,[content]\n"
                    + "      ,[image]\n"
                    + "      ,[time]\n"
                    + "      ,[status]\n "
                    + "  FROM [SNG].[dbo].[Message]\n"
                    + "where ([SenderID] = ? and  [ReceiveID] = ? ) or ([SenderID] = ? and [ReceiveID] = ? )\n"
                    + "order by [time] asc";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, senderID);
            ps.setInt(2, receiveID);
            ps.setInt(3, receiveID);
            ps.setInt(4, senderID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                Integer senID = rs.getInt(2);
                Integer recID = rs.getInt(3);
                String content = rs.getString(4);
                boolean status = rs.getBoolean(7);

                String image = rs.getBytes(5) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(5));
                Date time = rs.getTimestamp(6);
                Message msg = new Message(id, senID, recID, content, image, time);
                msg.setStatus(status);
                messageList.add(msg);
            }

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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
        return messageList;
    }

    public Integer saveMessage(Message message) {
        Integer id = null;
        String sql = null;
        try {
            if (message.getImage() != null) {
                sql = "insert into [Message]([ReceiveID],[SenderID],[content],[image]) values (?,?,?,?)";

            } else {
                sql = "insert into [Message]([ReceiveID],[SenderID],[content]) values (?,?,?)";

            }
            connection = db.getConnection();
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getReceiveID());
            ps.setInt(2, message.getSenderID());
            ps.setString(3, message.getContent());
            if (message.getImage() != null) {
                if (message.getImage().contains("data:image/webp;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(message.getImage().replace("data:image/webp;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(4, b);
                } else if (message.getImage().contains("data:image/png;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(message.getImage().replace("data:image/png;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(4, b);
                } else {
                    byte[] decodedByte = Base64.getDecoder().decode(message.getImage().replace("data:image/jpeg;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(4, b);
                }
            }

            ps.executeUpdate();
            try {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new SQLException("Creating message failed, no ID obtained.");
                }
            } catch (SQLException e1) {

            }
        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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

    public List<User> loadUserChatWith(Integer senderID) {
        List userList = new ArrayList<User>();
        try {
            connection = db.getConnection();
            String sql = "select [User].ID,[User].displayname,[User].avatar from (SELECT [Message].SenderID from [Message]\n"
                    + "                    where [Message].SenderID = ? or [Message].ReceiveID = ?\n"
                    + "                    group by SenderID,ReceiveID\n"
                    + "                    Having [Message].SenderID != ?\n"
                    + "                    union \n"
                    + "                    SELECT [Message].ReceiveID from [Message]\n"
                    + "                    where [Message].SenderID = ? or [message].receiveiD = ? \n"
                    + "                    group by SenderID,ReceiveID \n"
                    + "                    Having [Message].ReceiveID != ?) b\n"
                    + "                    inner join [User] on [User].ID = b.SenderID";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, senderID);
            ps.setInt(2, senderID);
            ps.setInt(3, senderID);
            ps.setInt(4, senderID);
            ps.setInt(5, senderID);
            ps.setInt(6, senderID);

            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt(1));
                user.setDisplayname(rs.getString(2));
                String avatar = rs.getBytes(3) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(3));
                user.setAvatar(avatar);

                userList.add(user);
            }

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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

        return userList;
    }

    public List<User> loadFriend(Integer id) {
        List userList = new ArrayList<User>();
        try {
            connection = db.getConnection();

            String sql = " select [User].ID,[User].displayname,[User].avatar from ( select UserID2 from [Friend]\n"
                    + "                    where UserID1 = ? and [status] = 1\n"
                    + "                union \n"
                    + "                select UserID1 from [Friend]\n"
                    + "                where UserID2 = ? and [status] = 1) as f\n"
                    + "  join [User] on [User].[ID] = f.UserID2";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setID(rs.getInt(1));
                user.setDisplayname(rs.getString(2));
                String avatar = rs.getBytes(3) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(3));
                user.setAvatar(avatar);

                userList.add(user);
            }

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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
        return userList;
    }

    public Message loadLastMessage(Integer senderID, Integer receiveID) {

        try {
            connection = db.getConnection();

            String sql = "SELECT  top 1 [ID]\n"
                    + "      ,[SenderID]\n"
                    + "      ,[ReceiveID]\n"
                    + "      ,[content]\n"
                    + "      ,[image]\n"
                    + "      ,[time]\n"
                    + "      ,[status]\n"
                    + "  FROM [SNG].[dbo].[Message]\n"
                    + "where ([SenderID] = ? and  [ReceiveID] = ? ) or ([SenderID] = ? and [ReceiveID] = ? )\n"
                    + "order by [time] desc";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, senderID);
            ps.setInt(2, receiveID);
            ps.setInt(3, receiveID);
            ps.setInt(4, senderID);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                Integer senID = rs.getInt(2);
                Integer recID = rs.getInt(3);
                String content = rs.getString(4);
                String image = rs.getBytes(5) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(5));
                Date time = rs.getTimestamp(6);
                boolean status = rs.getBoolean(7);
                Message msg = new Message(id, senID, recID, content, image, time);
                msg.setStatus(status);
                return msg;
            }

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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

    public void deleteAllMessage(Integer senderID, Integer receiveID) {
        try {
            connection = db.getConnection();
            String sql = "  DELETE FROM Message WHERE\n"
                    + "(SenderID = ? and ReceiveID = ?) "
                    + "or (SenderID = ? and ReceiveID = ?);";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, senderID);
            ps.setInt(2, receiveID);
            ps.setInt(3, receiveID);
            ps.setInt(4, senderID);
            ps.executeUpdate();

        } catch (Exception e) {

        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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

    public void updateSeenMsg(Integer id) {
        try {
            connection = db.getConnection();
            String sql = "update Message\n"
                    + "  set status = 1\n"
                    + "  where ID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
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

}
