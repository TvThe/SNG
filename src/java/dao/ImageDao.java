/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author Admin
 */
public class ImageDao {

    DBContext db = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    Connection connection = null;

    public ImageDao() {
        db = new DBContext();
    }

    public Long saveImage(Integer PostID, String image) {
        Long id = null;

        try {
            connection = db.getConnection();
            String sql = "insert into Image(PostID,image) values (?,?)";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, PostID);
            if (image != null) {
                if (image.contains("data:image/webp;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/webp;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    stm.setBlob(2, b);
                } else if (image.contains("data:image/png;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/png;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    stm.setBlob(2, b);
                } else {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/jpeg;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    stm.setBlob(2, b);
                }
            }
            
            else {

            }
            stm.executeUpdate();
            //     ps.setBlob(2, image);

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
        return id;
    }

    public void deleteImage(int id) {
        try {
            connection = db.getConnection();

            String sql = "DELETE FROM [Image]\n"
                    + "      WHERE [PostID] = ?";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void updateImage(int id, String image) {
        try {
//            String sql = "UPDATE [Image]\n"
//                    + "   SET [image] = ?\n"
//                    + " WHERE [PostID] = ?";
            connection = db.getConnection();
            String sql = "IF EXISTS (SELECT [PostID] from [Image] WHERE [PostID]=?)\n"
                    + "BEGIN\n"
                    + "	UPDATE [Image] SET [image] = ? WHERE [PostID] = ?\n"
                    + "END\n"
                    + "ELSE\n"
                    + "BEGIN\n"
                    + "	INSERT into [Image]([PostID],[image]) values (?,?)\n"
                    + "END";
            stm = connection.prepareStatement(sql);

            byte[] decodedByte;
            if (image.contains("data:image/webp;base64,")) {
                decodedByte = Base64.getDecoder().decode(image.replace("data:image/webp;base64,", ""));
            } else if (image.contains("data:image/png;base64,")) {
                decodedByte = Base64.getDecoder().decode(image.replace("data:image/png;base64,", ""));
            } else {
                decodedByte = Base64.getDecoder().decode(image.replace("data:image/jpeg;base64,", ""));
            }
            Blob b = new SerialBlob(decodedByte);
            stm.setBlob(2, b);
            stm.setBlob(5, b);

            stm.setInt(1, id);
            stm.setInt(3, id);
            stm.setInt(4, id);

            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void saveImageProduct(int PurID, InputStream file) {

        try {
            connection = db.getConnection();
            String sql = "insert into Image(PurID,image) values (?,?)";
            stm = connection.prepareStatement(sql);

            stm.setInt(1, PurID);
            stm.setBlob(2, file);

            stm.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(ImageDao.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
