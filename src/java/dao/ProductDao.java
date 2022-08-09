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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Product;

/**
 *
 * @author duynh
 */
public class ProductDao {
    
    DBContext db = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    Connection connection = null;

    public ProductDao() {
        db = new DBContext();
    }

    public Product getDetailProductById(int id) {
        Product p = new Product();
        ArrayList<String> listImage = new ArrayList<>();

        try {
            connection = db.getConnection();
            String sql = "select p.ID,p.UserID,p.GameID,p.nameProduct,p.[description],p.price,p.[status],p.[time],g.name,i.[image] from Product p left join [Image] i on p.ID = i.PurID left join Game g on p.GameID = g.ID where p.ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                p.setPurID(rs.getInt("ID"));
                p.setUserID(rs.getInt("UserID"));
                p.setGameID(rs.getInt("GameID"));
                p.setName(rs.getString("nameProduct"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getInt("price"));
                p.setStock(rs.getBoolean("status"));
                p.setTime(rs.getDate("time"));
                p.setGameName(rs.getString("name"));

                String imageProduct = rs.getBytes("image") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("image"));
                listImage.add(imageProduct);

                p.setImages(listImage);
            }
            return p;
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public ArrayList<Product> getProduct(String pricefrom, String priceto, int page, String type) {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "";
        if (priceto.equals("More than 5000")) {
            priceto = "(select Max(price) from Product)";
        }

        switch (type) {
            case "Price Decrease":
                sql = "Select * from \n"
                        + "(select *, ROW_NUMBER() over (Order by price DESC) as row_index from Product where price between ? and "+priceto+") tb\n"
                        + "Where row_index >= (?-1)*10+1 and row_index <= ?*10";
                break;
            case "Price Increase":
                sql = "Select * from \n"
                        + "(select *, ROW_NUMBER() over (Order by price ASC) as row_index from Product where price between ? and "+priceto+") tb\n"
                        + "Where row_index >= (?-1)*10+1 and row_index <= ?*10";
                break;
            case "Oldest":
                sql = "Select * from \n"
                        + "(select *, ROW_NUMBER() over (Order by time ASC) as row_index from Product where price between ? and "+priceto+") tb\n"
                        + "Where row_index >= (?-1)*10+1 and row_index <= ?*10";
                break;
            case "Newest":
                sql = "Select * from \n"
                        + "(select *, ROW_NUMBER() over (Order by time DESC) as row_index from Product where price between ? and "+priceto+") tb\n"
                        + "Where row_index >= (?-1)*10+1 and row_index <= ?*10";
                break;
        }

        try {
            connection = db.getConnection();
//            String sql = "Select * from \n"
//                        + "(select *, ROW_NUMBER() over (Order by time DESC) as row_index from Product) tb\n"
//                        + "Where row_index >= (?-1)*10+1 and row_index <= ?*10";
            stm = connection.prepareStatement(sql);
            stm.setString(1, pricefrom);
            stm.setInt(2, page);
            stm.setInt(3, page);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setPurID(rs.getInt("ID"));
                p.setUserID(rs.getInt("UserID"));
                p.setGameID(rs.getInt("GameID"));
                p.setPrice(rs.getInt("price"));
                p.setName(rs.getString("nameProduct"));
                p.setDescription(rs.getString("description"));
                p.setStock(rs.getBoolean("status"));
                p.setTime(rs.getDate("time"));
                list.add(p);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }

    public List<Product> ShowUserProduct(Integer id) {
        List<Product> myProduct = new ArrayList<>();
        try {
            connection = db.getConnection();
            String sql = "select p.ID as 'ProductID', p.UserID as 'UserID',p.GameID as 'GameID',g.[name] as 'Gamename',p.nameProduct,p.[description] as 'GameDes',p.[time] as 'pTime'\n"
                    + "from Product p\n"
                    + "join [User] u on u.ID = p.UserID \n"
                    + "join [Game] g on g.ID = p.GameID\n"
                    + "where \n"
                    + "UserID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Product ptemp = new Product();
                ptemp.setPurID(rs.getInt("ProductID"));
                ptemp.setUserID(rs.getInt("UserID"));
                ptemp.setGameID(rs.getInt("GameID"));
                ptemp.setName(rs.getString("nameProduct"));
                ptemp.setDescription(rs.getString("GameDes"));
                ptemp.setTime(rs.getDate("pTime"));
                myProduct.add(ptemp);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return myProduct;
    }

    public int countProduct(String pricefrom, String priceto) {
        String sql = "";
        if (priceto.equals("More than 5000")) {
            priceto = "(select Max(price) from Product)";
        }
        try {
            connection = db.getConnection();
            sql = "select count(*) as Total from Product where price between ? and "+priceto ;
            stm = connection.prepareStatement(sql);
            stm.setString(1, pricefrom);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (stm != null) {
                        try {
                            stm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        return -1;
    }

    public Integer CreateProduct(Product p) {
        Integer productId = null;
        try {
            String sql = "INSERT INTO [dbo].[Product]\n"
                    + "           ([UserID]\n"
                    + "           ,[GameID]\n"
                    + "           ,[price]\n"
                    + "           ,[nameProduct]\n"
                    + "           ,[description]\n"
                    + "          )\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?)";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1, p.getUserID());
            stm.setInt(2, p.getGameID());
            stm.setInt(3, p.getPrice());
            stm.setString(4, p.getName());
            stm.setString(5, p.getDescription());
            stm.executeUpdate();
            try {
                rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    productId = rs.getInt(1);
                }
            } catch (Exception e1) {

            }
        } catch (Exception ex) {
            System.out.println(ex);
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
        return productId;
    }

    public void UpdateProduct(Product p) {
        try {
            connection = db.getConnection();
            String sql = "UPDATE [dbo].[Product]\n"
                    + "   SET"
                    + "      [GameID] = ?\n"
                    + "      ,[price] = ?\n"
                    + "      ,[nameProduct] = ?\n"
                    + "      ,[description] = ?\n"
                    + "      \n"
                    + " WHERE [ID] = ? ";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, p.getGameID());
            stm.setInt(2, p.getPrice());
            stm.setString(3, p.getName());
            stm.setString(4, p.getDescription());
            stm.setInt(5, p.getPurID());
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public Product getProductByID(int productID) {
        Product p = new Product();
        ArrayList<String> listImage = new ArrayList<>();

        try {
            connection = db.getConnection();
            String sql = "select p.ID,p.UserID,p.GameID,p.nameProduct,p.[description],p.price,p.[status],p.[time],g.name,i.[image] from Product p left join [Image] i on p.ID = i.PurID left join Game g on p.GameID = g.ID where p.ID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productID);
            rs = stm.executeQuery();
            while (rs.next()) {

                p.setPurID(rs.getInt("ID"));
                p.setUserID(rs.getInt("UserID"));
                p.setGameID(rs.getInt("GameID"));
                p.setName(rs.getString("nameProduct"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getInt("price"));
                p.setStock(rs.getBoolean("status"));
                p.setTime(rs.getDate("time"));
                p.setGameName(rs.getString("name"));
                String imageProduct = rs.getBytes("image") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("image"));
                listImage.add(imageProduct);

            }
            p.setImages(listImage);
            return p;
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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

    public void DeleteProduct(int productID, int userID) {
        try {
            connection = db.getConnection();
            String sql = "DELETE FROM [dbo].[Product]\n"
                    + "      WHERE ID = ? AND UserID = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, productID);
            stm.setInt(2, userID);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public ArrayList<Product> searchProduct(String nameProduct){
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "Select p.ID,p.price,p.[time],p.nameProduct,u.displayname,g.[image] as 'imageGame' from Product p left join [User] u On p.UserID = u.ID left join Game g On p.GameID = g.ID  where p.nameProduct like ?";
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%"+nameProduct+"%");
            rs = stm.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setPurID(rs.getInt("ID"));
                product.setPrice(rs.getInt("price"));
                product.setTime(rs.getDate("time"));
                product.setName(rs.getString("nameProduct"));
                product.setDisplaynameUser(rs.getString("displayname"));
                String imageGame = rs.getBytes("imageGame") == null ? "null" : Base64.getEncoder().encodeToString(rs.getBytes("imageGame"));
                product.setImageGame(imageGame);
                products.add(product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
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
        return products;
    }
    public static void main(String[] args) {
        ProductDao db = new ProductDao();
        List<Product> product = db.ShowUserProduct(2);
        for(Product p : product){
            System.out.println(p.getPurID() + p.getName()  + p.getPrice());
        }
    }
}
