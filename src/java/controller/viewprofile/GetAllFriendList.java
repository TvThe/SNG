/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.viewprofile;


import dao.FriendDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modal.Friend;
import modal.User;

/**
 *
 * @author duynh
 */
@WebServlet(name = "GetAllFriendList", urlPatterns = {"/GetAllFriendList"})
public class GetAllFriendList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        FriendDao fdb = new FriendDao();
         Integer id = null;
        String id_str = request.getParameter("id");
        if(id_str != null){
        try{
            id = Integer.parseInt(id_str);
            
        }
        catch(NumberFormatException  e){
        }
        }
        else{
            if(user !=null){
                id = user.getID();
                
            }        
        }
        ArrayList<Friend> friendlist = fdb.getFriendList(id);
        int countFriend = fdb.getTotalFriend(id);
        
        request.setAttribute("friendlist", friendlist);
        request.setAttribute("countFriend", countFriend);
        request.getRequestDispatcher("view/Friend/viewUserFriend.jsp").forward(request, response);
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
