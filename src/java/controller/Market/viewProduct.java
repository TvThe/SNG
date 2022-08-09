/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Market;

import dao.GameDao;
import dao.ProductDao;
import dao.UserDao;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.Game;
import modal.Product;
import modal.User;

/**
 *
 * @author LENNOVO
 */
@WebServlet(name = "Product", urlPatterns = {"/product"})
public class viewProduct extends HttpServlet {

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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productID = request.getParameter("id");
        int proID = Integer.parseInt(productID);
        ProductDao dbProd = new ProductDao();
        Product product = dbProd.getProductByID(proID);
        GameDao dbGame = new GameDao();
        ArrayList<Game> game = dbGame.listGame();
        UserDao dbUser = new UserDao();
        ArrayList<User> user = dbUser.getAllUser();
        request.setAttribute("p", product);
        request.setAttribute("g", game);
        request.setAttribute("u", user);
        request.getRequestDispatcher("/view/Market/Product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
