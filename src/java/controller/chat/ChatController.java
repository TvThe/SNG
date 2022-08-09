/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chat;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import dao.MessageDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Message;
import modal.User;

/**
 *
 * @author window
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChatServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChatServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    //compare by time
    public static Comparator<Message> StuRollno = new Comparator<Message>() {
        // Method
        public int compare(Message s1, Message s2) {
            long rollno1 = s1.getTime().getTime();
            long rollno2 = s2.getTime().getTime();

            // For ascending order
            return (int) (rollno2 - rollno1);
            // For descending order
            // rollno2-rollno1;
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("account") != null) {
            Integer meID = ((User) request.getSession(false).getAttribute("account")).getID();
            MessageDao dbMessage = new MessageDao();
            String youID = request.getParameter("id");
            try {
                // get list user chat with
                List<User> userList = dbMessage.loadUserChatWith(meID);
                List<Message> msgLastList = new ArrayList<>();
               
                for (int i = 0; i < userList.size(); i++) {
                    // get last message of conversation
                    msgLastList.add(dbMessage.loadLastMessage(meID, userList.get(i).getID()));
                }
                int countNewMsg = 0;
                String idSenderStrs = "";
                for (int i = 0; i < msgLastList.size(); i++) {
                    if (!Objects.equals(meID, msgLastList.get(i).getSenderID()) && !msgLastList.get(i).isStatus()) {
                        countNewMsg++;
                        idSenderStrs += msgLastList.get(i).getSenderID() + " ";
                    }
                }
                Collections.sort(msgLastList, StuRollno);
                List<User> friendList = dbMessage.loadFriend(meID);
                request.setAttribute("friendList", friendList);
                request.setAttribute("countNewMsg", countNewMsg);
                request.setAttribute("idSenderStrs", idSenderStrs);
                
                request.setAttribute("userList", userList);
                request.setAttribute("msgLastList", msgLastList);

                // Comparator for sorting the list by roll no
            } catch (Exception ex) {

            }
            request.setAttribute("senderID", meID);
            request.setAttribute("receiveID", youID);

            request.getRequestDispatcher("view/chat/chat.jsp").forward(request, response);
        } else {
            response.sendRedirect("Login");
        }

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
        processRequest(request, response);
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
