package controller.Account;

import dao.QuestionDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modal.Question;
import modal.User;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println();
    }
//truyen list question

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuestionDao db = new QuestionDao();
        ArrayList<Question> quest = db.getQuestions();
        request.setAttribute("quest", quest);
        request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
    }
//login

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int typeOfAccount = Integer.parseInt(request.getParameter("typeOfAccount"));
        if (typeOfAccount == 1) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println(username + " a " + password);
            UserDao db = new UserDao();
            User account1 = db.Login(username, password, typeOfAccount);

            if (account1 != null) {

                User account = db.getUserById(account1.getID());
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                String remember = request.getParameter("remember");
                System.out.println(remember);
                Cookie c_user = new Cookie("username", username);
                Cookie c_pass = new Cookie("password", password);
                if (remember != null) {
                    c_user.setMaxAge(24 * 3600 * 7);
                    c_pass.setMaxAge(24 * 3600 * 7);

                }
                if (account1.isAdmin() == false) {
                    response.sendRedirect("viewNewsfeed");
                } else {
                    response.sendRedirect("admin");
                }

            } else {
                QuestionDao dbQuest = new QuestionDao();
                ArrayList<Question> quest = dbQuest.getQuestions();
                request.setAttribute("quest", quest);
                request.setAttribute("message", "Wrong username or password!");
                request.getRequestDispatcher("view/login/login.jsp").forward(request, response);
            }
        }
        if (typeOfAccount == 2) {
            String fb_id = request.getParameter("fb_id");
            UserDao db = new UserDao();
            User account1 = db.Login(fb_id, fb_id, typeOfAccount);
            if (account1 != null) {
                User account = db.getUserById(account1.getID());
                HttpSession session = request.getSession();
                session.setAttribute("account", account);
                String remember = request.getParameter("remember");
                Cookie c_user = new Cookie("username", fb_id);
                Cookie c_pass = new Cookie("password", fb_id);
                if (remember != null) {
                    c_user.setMaxAge(24 * 3600 * 7);
                    c_pass.setMaxAge(24 * 3600 * 7);

                }
                PrintWriter out = response.getWriter();
                out.print("");
            } else {
//                String fb_displayname = request.getParameter("fb_displayname");
//                String fb_avatar = request.getParameter("fb_avatar");
                RequestDispatcher reqd = request.getRequestDispatcher("Register");
                reqd.forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
