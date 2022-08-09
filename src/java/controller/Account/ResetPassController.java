/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Account;

import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.User;

/**
 *
 * @author LENNOVO
 */
@WebServlet(name = "ResetPassController", urlPatterns = {"/forgetpassword"})
public class ResetPassController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forgetuser = request.getParameter("forgetuser");
        String password = request.getParameter("password");
        UserDao dbUser = new UserDao();
        User u = new User();
        u.setPassword(password);
        u.setUsername(forgetuser);
        dbUser.ResetPassword(u);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
