/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import com.google.gson.Gson;
import dao.AdminDao;
import dao.StatisticDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Game;
import modal.Message;
import modal.User;

/**
 *
 * @author window
 */
@WebServlet(name = "AdminRestController",
        urlPatterns = {"/get-all-games", "/create-game", "/remove-game", "/update-game"})
public class AdminRestController extends HttpServlet {

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
    // return current url in browser
    public String getCurrentUrl(HttpServletRequest request) throws MalformedURLException {
        URL url = new URL(request.getRequestURL().toString());
        String path = url.getFile();
        return path;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (((User) request.getSession().getAttribute("account")).isAdmin()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                AdminDao dbA = new AdminDao();
                String json = null;
                String url = getCurrentUrl(request);
                // get all message bewteen two user
                if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/get-all-games")) {
                    List<Game> listGame = null;
                    try {
                        listGame = dbA.loadGames();
                    } catch (Exception ex) {

                    }

                    json = new Gson().toJson(listGame);
                    // get info of user
                } 
                // return json 
                PrintWriter printWriter = response.getWriter();
                printWriter.print(json);
                printWriter.flush();
            } else {
                response.sendRedirect("viewNewsfeed");
            }

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
        if (request.getSession().getAttribute("account") != null) {
            if (((User) request.getSession().getAttribute("account")).isAdmin()) {
                String url = getCurrentUrl(request);
                AdminDao dbA = new AdminDao();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String json = null;
                User user = (User) request.getSession().getAttribute("account");
                if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/create-game")) {

                    String name = request.getParameter("name");
                    String image = request.getParameter("image");
                    Integer idG = dbA.createGame(name, image);
                    json = "{\"id\":\"" + idG + "\"}";

                } else if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/remove-game")) {
                    String id = request.getParameter("id");
                    try {
                        dbA.removeGame(Integer.parseInt(id));
                        dbA.removePost(Integer.parseInt(id));
                        dbA.removeProduct(Integer.parseInt(id));
                        System.out.println(id);
                        json = "{\"msg\":\"done\"}";
                    } catch (Exception e) {
                    }
                } else if (url.equals("/nhom-4-se1604ks-swp391-social-network-for-gamer/update-game")) {
                    String id = request.getParameter("id");
                    String name = request.getParameter("name");
                    String image = request.getParameter("image");
                    try {
                        dbA.updateGame(Integer.parseInt(id), name, image);
                        json = "{\"id\":\"" + id + "\"}";
                    } catch (Exception e) {
                    }
                }
                // return json 
                PrintWriter printWriter = response.getWriter();
                printWriter.print(json);
                printWriter.flush();
            } else {
                response.sendRedirect("viewNewsfeed");
            }

        } else {
            response.sendRedirect("Login");

        }

    }

}
