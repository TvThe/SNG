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
import modal.Question;

/**
 *
 * @author LENNOVO
 */
public class QuestionDao {

    DBContext db = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    Connection connection = null;
    public QuestionDao() {
            db = new DBContext();
    }
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> question = new ArrayList<>();
        try {
            connection = db.getConnection();
            String sql = "select * from Question";
            stm = db.getConnection().prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setID(rs.getInt(1));
                q.setContent(rs.getString(2));
                question.add(q);
            }
        } catch (Exception ex) {
            Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return question;
    }
}
