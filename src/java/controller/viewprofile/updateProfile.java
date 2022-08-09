/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.viewprofile;

import dao.UserDao;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modal.User;

/**
 *
 * @author duynh
 */
@WebServlet(name = "updateProfile", urlPatterns = {"/updateProfile"})
@MultipartConfig(maxFileSize = 16177215)
public class updateProfile extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if(user == null){
            request.getRequestDispatcher("Login").forward(request, response);
        }
        UserDao db = new UserDao();
       
        request.setAttribute("user", user);
        request.getRequestDispatcher("view/Profile/UpdateUser.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        String displayname = request.getParameter("newDisplayname");
        String gender = request.getParameter("newGender");
        String dob = request.getParameter("newDob");

        try {
            Date newdob = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            java.sql.Date sqlDate = new java.sql.Date(newdob.getTime());
            System.out.println("a " + displayname);
            System.out.println("b " + gender);
            System.out.println("c " + dob);
            UserDao db = new UserDao();
            User updateUser = new User();
            updateUser.setDisplayname(displayname);
            updateUser.setGender(gender);
            updateUser.setDob(sqlDate);
            db.updatebasicprofile(user.getID(), updateUser);
            
        } catch (ParseException |IllegalStateException e ) {
        }
        
        response.sendRedirect("viewProfile");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
