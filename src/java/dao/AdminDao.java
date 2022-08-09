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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import modal.Game;
import modal.User;

/**
 *
 * @author window
 */
public class AdminDao {

    DBContext db = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection connection = null;

    public AdminDao() {
        db = new DBContext();
    }

    public List<Game> loadGames() {
        List gamesList = new ArrayList<Game>();
        try {
            connection = db.getConnection();
            String sql = "SELECT  [ID]\n"
                    + "      ,[name]\n"
                    + "      ,[image]\n"
                    + "  FROM [SNG].[dbo].[Game]";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String image = rs.getBytes(3) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(3));

                gamesList.add(new Game(id, name, image));
            }

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
        return gamesList;
    }

    public Integer createGame(String name, String image) {
        Integer id = null;

        try {
            connection = db.getConnection();
            String sql = "insert into [Game]([name],[image]) values (?,?)";
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            if (image != null) {
                if (image.contains("data:image/webp;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/webp;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                } else if (image.contains("data:image/png;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/png;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                } else if (image.contains("data:image/jpg;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/jpg;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                } else {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/jpeg;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                }
            }
            ps.executeUpdate();
            try {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception e1) {
                System.out.println(e1);

            }
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
        return id;
    }

    public void removeGame(Integer id) {
        try {
            connection = db.getConnection();
            String sql = "delete from [Game] where ID = ?";
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

    public void removePost(Integer id) {
        try {
            connection = db.getConnection();
            String sql = "DELETE FROM [dbo].[Post] where [GameID] = ?";
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

    public void removeProduct(Integer id) {
        try {
            connection = db.getConnection();
            String sql = "DELETE FROM [dbo].[Product] where [GameID] = ?";
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

    public void updateGame(Integer id, String name, String image) {
        try {
            connection = db.getConnection();
            String sql = "UPDATE Game\n"
                    + "SET name = ?, image = ?\n"
                    + "WHERE ID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            if (image != null) {
                if (image.contains("data:image/webp;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/webp;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                } else if (image.contains("data:image/png;base64,")) {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/png;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                } else {
                    byte[] decodedByte = Base64.getDecoder().decode(image.replace("data:image/jpeg;base64,", ""));
                    Blob b = new SerialBlob(decodedByte);
                    ps.setBlob(2, b);
                }
            }
            ps.setInt(3, id);
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
