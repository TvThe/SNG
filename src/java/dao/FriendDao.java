/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Friend;
import modal.User;

/**
 *
 * @author duynh
 */
public class FriendDao {

    DBContext db = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    Connection connection = null;

    public FriendDao() {
        db = new DBContext();
    }

    public ArrayList<Friend> getFriend(int id, int status) {
        String sql = "select [User].ID as 'UseriD', [User].displayname as 'Displayname', [User].avatar as 'UserAvatar',ftime from ( select UserID2,Friend.[time] as 'ftime' from [Friend]\n"
                + "                    where UserID1 = ? and [status] = ?\n"
                + "                union \n"
                + "                select UserID1,Friend.[time] as 'ftime' from [Friend]\n"
                + "                where UserID2 = ? and [status] = ?) as f\n"
                + "  join [User] on [User].[ID] = f.UserID2";
        ArrayList<Friend> printList = new ArrayList<>();
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, status);
            stm.setInt(3, id);
            stm.setInt(4, status);
            rs = stm.executeQuery();
            while (rs.next()) {
                User friend = new User();
                friend.setID(rs.getInt("UseriD"));
                if (rs.getBytes("UserAvatar") != null) {
                    friend.setAvatar(Base64.getEncoder().encodeToString(rs.getBytes("UserAvatar")));
                } else {
                    friend.setAvatar(null);
                }
                friend.setDisplayname(rs.getString("displayname"));

                Friend f = new Friend();
                f.setUserID2(friend);
                f.setStatus(0);
                f.setTime(rs.getDate("ftime"));
                printList.add(f);
            }
        } catch (Exception ex) {
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
        return printList;
    }

    public int getTotalFriend(int id) {
        ArrayList<Friend> getList = getFriend(id, 1);
        int count = 0;
        for (Friend f : getList) {
            count++;
        }
        return count;
    }

    public int isFriend(User u) {
        ArrayList<Friend> userf = getFriend(u.getID(), 1);
        ArrayList<Friend> userfp = getFriend(u.getID(), 0);

        for (int i = 0; i <= userf.size(); i++) {
            if (userf.get(i).getUserID2().getID() == u.getID()) {
                return 1;
            }
        }
        for (int i = 0; i < userfp.size(); i++) {
            if (userfp.get(i).getUserID2().getID() == u.getID()) {
                return 0;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        FriendDao db = new FriendDao();
        int g = db.getTotalFriend(2);
        System.out.println(g);
    }

    public void pendingFriend(User user1, User user2, Date time) {
        String sql = "INSERT INTO [Friend]\n"
                + "           ([UserID1]\n"
                + "           ,[UserID2]\n"
                + "           ,[status]\n"
                + "           ,[time])\n"
                + "     VALUES\n"
                + "           (?,\n"
                + "		   ?,\n"
                + "		   ?,\n"
                + "		   ?)";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, user1.getID());
            stm.setInt(2, user2.getID());
            stm.setInt(3, 0);
            stm.setDate(4, time);
            stm.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(FriendDao.class.getName()).log(Level.SEVERE, null, ex);
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

    }

    public void acceptFriend(int id, Date time) {

        String sql = "UPDATE [Friend]\n"
                + "   SET [status] = 1 \n"
                + "      ,[time] = ?\n"
                + " WHERE UserID1 = ?";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setDate(1, time);
            stm.setInt(2, id);
            stm.execute();
        } catch (Exception ex) {
            Logger.getLogger(FriendDao.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    public void removeFriend(User u) {
        String sql = "DELETE FROM [Friend]\n"
                + "      WHERE UserID1 = ?";
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, u.getID());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(FriendDao.class.getName()).log(Level.SEVERE, null, ex);
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
    }
}
