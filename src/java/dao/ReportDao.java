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
import modal.Product;
import modal.Report;

/**
 *
 * @author LENNOVO
 */
public class ReportDao {

    DBContext db = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    Connection connection = null;

    public ReportDao() {
        db = new DBContext();
    }

    public void InsertReportProduct(Report r) {
        String sql = "INSERT INTO [dbo].[Report]\n"
                + "           ([PurID]\n"
                + "           ,[ReportMessage]\n"
                + "           ,[UserID])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        stm = null;
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, r.getPurID());
            stm.setString(2, r.getReportMessage());
            stm.setInt(3, r.getUserID());

            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<Report> getReportProduct() {
        ArrayList<Report> list = new ArrayList<>();
        try {
            connection = db.getConnection();
            String sql = "SELECT [PurID]\n"
                    + "      ,[ReportMessage]\n"
                    + "      ,[UserID]\n"
                    + "  FROM [Report] where PurID != 0 order by [purid] asc";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Report r = new Report();
                r.setPurID(rs.getInt("purID"));
                r.setReportMessage(rs.getString("reportMessage"));
                r.setUserID(rs.getInt("userID"));
                list.add(r);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public void InsertReportPost(Report r) {
        String sql = "INSERT INTO [dbo].[Report]\n"
                + "           ([PostID]\n"
                + "           ,[ReportMessage]\n"
                + "           ,[UserID])\n"
                + "     VALUES\n"
                + "           (?,?,?)";
        stm = null;
        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, r.getPostID());
            stm.setString(2, r.getReportMessage());
            stm.setInt(3, r.getUserID());

            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ArrayList<Report> getReportPost() {
        ArrayList<Report> list = new ArrayList<>();
        try {
            connection = db.getConnection();
            String sql = "SELECT [PostID]\n"
                    + "      ,[ReportMessage]\n"
                    + "      ,[UserID]\n"
                    + "  FROM [SNG].[dbo].[Report] where PostID != 0";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Report r = new Report();
                r.setPostID(rs.getInt("postID"));
                r.setReportMessage(rs.getString("reportMessage"));
                r.setUserID(rs.getInt("userID"));
                list.add(r);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }
}
