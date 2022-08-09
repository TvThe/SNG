/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import modal.User;

/**
 *
 * @author window
 */
public class StatisticDao {

    DBContext db = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Connection connection = null;

    public StatisticDao() {
        db = new DBContext();
    }

    public Integer getNumberOfProductInDay() {
        Integer total = null;
        try {
            connection = db.getConnection();
            String sql = "select count(*)  from Product\n"
                    + " Where day(time) = day( GETDATE()) and month(time) = month(GETDATE())";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
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
        return total;
    }

    public Integer getNumberOfPostInDay() {
        Integer total = null;
        try {
            connection = db.getConnection();
            String sql = "select count(*)  from Post\n"
                    + " Where day(time) = day( GETDATE()) and month(time) = month(GETDATE())";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
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
        return total;
    }

    public Integer getNumberOfCommentInDay() {
        Integer total = null;
        try {
            connection = db.getConnection();
            String sql = "  select count(*)  from [Comment]\n"
                    + "  Where day(time) = day( GETDATE()) and month(time) = month(GETDATE())";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
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
        return total;
    }

    public List<User> loadListUserActive() {
        List userList = new ArrayList<User>();
        try {
            connection = db.getConnection();
            String sql = "select a.ID,a.displayname,a.avatar,b.active from [User] as a\n"
                    + "inner join (select UserID,sum(number) as active\n"
                    + "from (select UserID,count(*) as number  from Post\n"
                    + "group by UserID\n"
                    + "union\n"
                    + "select UserID,count(*) as number from Product\n"
                    + "group by UserID\n"
                    + "union\n"
                    + "select UserID,count(*) as number from Comment\n"
                    + "group by UserID\n"
                    + ") as a\n"
                    + "group by UserID) as b\n"
                    + "on b.UserID = a.ID\n"
                    + "order by b.active desc";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String image = rs.getBytes(3) == null ? null : Base64.getEncoder().encodeToString(rs.getBytes(3));
                User u = new User();
                u.setID(id);
                u.setDisplayname(name);
                u.setAvatar(image);
                userList.add(u);
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
        return userList;
    }

    public List<String> getTotalPostByDay() {

        List valueList = new ArrayList<String>();
        try {
            connection = db.getConnection();
            String sql = "select CAST(month(a.date) as varchar) + '/' +cast(day(a.date) as varchar)+ '/' +cast(year(a.date) as varchar)  +'-'+cast(a.number as varchar)\n"
                    + "                    from (select GETDATE() as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()) and month(time) = month(GETDATE())\n"
                    + "                    union\n"
                    + "                    select GETDATE()-1 as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()-1) and month(time) = month(GETDATE()-1)\n"
                    + "                    union\n"
                    + "                    select GETDATE()-2 as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()-2) and month(time) = month(GETDATE()-2)\n"
                    + "                    union\n"
                    + "                    select GETDATE()-3 as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()-3) and month(time) = month(GETDATE()-3)\n"
                    + "                    union\n"
                    + "                    select GETDATE()-4 as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()-4) and month(time) = month(GETDATE()-4)\n"
                    + "					union\n"
                    + "                    select GETDATE()-5 as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()-5) and month(time) = month(GETDATE()-5)\n"
                    + "					 union\n"
                    + "                    select GETDATE()-6 as date,count(*) as number  from Post\n"
                    + "                    where day(time) = day(GETDATE()-6) and month(time) = month(GETDATE()-6)\n"
                    + "                  \n"
                    + "					) as a\n"
                    + "                   order by a.date asc";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                valueList.add(rs.getString(1));
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
        return valueList;
    }

    public List<String> getTotalProductByDay() {

        List valueList = new ArrayList<String>();
        try {
            connection = db.getConnection();
            String sql = "select CAST(month(a.date) as varchar) + '/' +cast(day(a.date) as varchar)+ '/' +cast(year(a.date) as varchar)  +'-'+cast(a.number as varchar)\n"
                    + "                    from (select GETDATE() as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()) and month(time) = month(GETDATE())\n"
                    + "                    union\n"
                    + "                    select GETDATE()-1 as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()-1) and month(time) = month(GETDATE()-1)\n"
                    + "                    union\n"
                    + "                    select GETDATE()-2 as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()-2) and month(time) = month(GETDATE()-2)\n"
                    + "                    union\n"
                    + "                    select GETDATE()-3 as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()-3) and month(time) = month(GETDATE()-3)\n"
                    + "                    union\n"
                    + "                    select GETDATE()-4 as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()-4) and month(time) = month(GETDATE()-4)\n"
                    + "					union\n"
                    + "                    select GETDATE()-5 as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()-5) and month(time) = month(GETDATE()-5)\n"
                    + "					 union\n"
                    + "                    select GETDATE()-6 as date,count(*) as number  from Product\n"
                    + "                    where day(time) = day(GETDATE()-6) and month(time) = month(GETDATE()-6)\n"
                    + "                  \n"
                    + "					) as a\n"
                    + "                   order by a.date asc";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                valueList.add(rs.getString(1));
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
        return valueList;
    }
}
