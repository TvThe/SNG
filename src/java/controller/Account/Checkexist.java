/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Account;

import com.google.gson.Gson;
import dao.QuestionDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Question;
import modal.User;

/**
 *
 * @author LENNOVO
 */
@WebServlet(name = "Checkexist", urlPatterns = {"/Checkexist"})
public class Checkexist extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forgetuser = request.getParameter("forgetuser");
        int question = Integer.parseInt(request.getParameter("question"));
        String answer = request.getParameter("answer");
        UserDao dbUser = new UserDao();
        User user = dbUser.getPassword(forgetuser, question, answer);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(user);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String forgetuser = request.getParameter("forgetuser");

        UserDao db = new UserDao();
        User check = db.getUsername(forgetuser);

        if (check == null) {
            PrintWriter out = response.getWriter();
            out.print("Username not found!");
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = new Gson().toJson(check);
            PrintWriter printWriter = response.getWriter();
            printWriter.print(json);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
