/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.chat;

import com.google.gson.Gson;
import dao.MessageDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ChatRestController",
        urlPatterns = {"/chat-rest-controller",
            "/get-infor-chat-with",
            "/remove-conversation",
            "/get-friend",
            "/update-seen-msg","/get-new-msg-notseen"})
public class ChatRestController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // return current url in browser
    public String getCurrentUrl(HttpServletRequest request) throws MalformedURLException {
        URL url = new URL(request.getRequestURL().toString());
        String path = url.getFile();
        return path;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        MessageDao dbMes = new MessageDao();
        List<Message> messages = null;
        String json = null;
        String url = getCurrentUrl(request);
        // get all message bewteen two user
        if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/chat-rest-controller")) {
            String sender = request.getParameter("sender");
            String receiver = request.getParameter("receiver");
            try {
                messages = dbMes.loadMessage(Integer.parseInt(sender), Integer.parseInt(receiver));
            } catch (Exception ex) {

            }

            json = new Gson().toJson(messages);
            // get info of user
        } else if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/get-infor-chat-with")) {

            String idStr = request.getParameter("id");
            User user = dbMes.loadUserByID(Integer.parseInt(idStr));
            System.out.println(user);
            json = new Gson().toJson(user);
            // get list friend of user
        } else if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/get-friend")) {

            String idStr = request.getParameter("id");
            try {
                List<User> userList = dbMes.loadFriend(Integer.parseInt(idStr));
                json = new Gson().toJson(userList);
            } catch (Exception e) {
            }

        } else if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/get-new-msg-notseen")) {
            List<Message> msgLastList = new ArrayList<>();
            MessageDao dbMessage = new MessageDao();
            Integer meID = ((User) request.getSession(false).getAttribute("account")).getID();
            List<User> userList = dbMessage.loadUserChatWith(meID);
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
            try {
                json = "{\"countNewMsg\":"+countNewMsg+",\"isSenderStrs\":\""+idSenderStrs+"\"}";
               
            } catch (Exception e) {
            }

        }
        // return json 
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = getCurrentUrl(request);
        MessageDao dbMes = new MessageDao();
        // remover converstation
        if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/remove-conversation")) {

            String sender = request.getParameter("sender");
            String receiver = request.getParameter("receiver");
            try {
                dbMes.deleteAllMessage(Integer.parseInt(sender), Integer.parseInt(receiver));
            } catch (Exception e) {
            }

        } else if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/update-seen-msg")) {
            String idMsgStr = request.getParameter("id");
            try {
                dbMes.updateSeenMsg(Integer.parseInt(idMsgStr));
            } catch (Exception e) {
            }
        }
    }

}
