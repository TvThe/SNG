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
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.Game;
import modal.User;

/**
 *
 * @author Admin
 */
public class GameDao {

    DBContext db = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    Connection connection = null;

    public GameDao() {
        db = new DBContext();
    }

    public ArrayList<Game> listGame() {
        ArrayList<Game> list = new ArrayList<>();
        try {
            connection = db.getConnection();
            String sql = "select * from Game order by name asc";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Game g = new Game();
                g.setID(rs.getInt(1));
                g.setName(rs.getString(2));
                g.setImage(Base64.getEncoder().encodeToString(rs.getBytes(3)));
                list.add(g);
            }
        } catch (Exception ex) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, null, ex);
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
        return list;
    }
    //getUserGame
    public List<Game> getUsergame(Integer id) {
        List<Game> displayList = new ArrayList();
        String sql = "select g.ID as 'GameID',g.[name] as 'GameName' ,g.[image] as 'GameImage'  from user_game	ug \n"
                + "                inner join game g on ug.GameID = g.ID\n"
                + "                inner join [User] u on u.ID = ug.UserID\n"
                + "                where UserID = ?";

        try {
            connection = db.getConnection();
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                Game gtemp = new Game();
                gtemp.setID(rs.getInt("GameID"));
                gtemp.setName(rs.getString("GameName"));
                if (rs.getBytes("GameImage") != null) {
                    gtemp.setImage(Base64.getEncoder().encodeToString(rs.getBytes("GameImage")));
                } else {
                    gtemp.setImage(null);
                }
                displayList.add(gtemp);
            }
        } catch (Exception ex) {
            Logger.getLogger(GameDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
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
        return displayList;
    }   
    
    
    public static void main(String[] args) {
        GameDao gdb = new GameDao();
        List<Game> trys = gdb.getUsergame(2);   
        for(Game e : trys){
            System.out.println(e.getID() + " " + e.getName());
        }
    }

}
