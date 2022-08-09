/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.newsfeed;

import com.google.gson.Gson;
import dao.GameDao;
import dao.ImageDao;
import dao.PostDao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import modal.Game;
import modal.Image;
import modal.Post;
import modal.User;

/**
 *
 * @author Admin
 */
@MultipartConfig(maxFileSize = 16177215)
@WebServlet(name = "createPost", urlPatterns = {"/createPost"})
public class createPost extends HttpServlet {

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
            out.println("<title>Servlet createPost</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createPost at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        // Lay UserID qua session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int uID = user.getID();
        
        String text = request.getParameter("text");
        String gID_raw = request.getParameter("gID");
        int gID = Integer.parseInt(gID_raw);
        Post p = new Post();
        p.setContent(text);
        User u = new User();
        u.setID(uID);
        p.setUser(u);
        Game g = new Game();
        g.setID(gID);
        p.setGame(g);
        
        //Insert post to database and put out PostID
        PostDao dbPost = new PostDao();
        Integer postID = dbPost.createPost(p);
        
        //Save image to database
        String photo = request.getParameter("photo");
        if (photo != null) {
            ImageDao dbImage = new ImageDao();
            dbImage.saveImage(postID, photo);
        }
        // Put out new post from PostID
        Post post = dbPost.getListPostByID(postID);
        String displayname = user.getDisplayname();
        u.setDisplayname(displayname);
        String avatar = user.getAvatar();
        u.setAvatar(avatar);
        post.setUser(u);
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        String jsondata = gson.toJson(post);
        out.println(jsondata);

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
