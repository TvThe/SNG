/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Market;

import com.google.gson.Gson;
import dao.GameDao;
import dao.MessageDao;
import dao.ProductDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "ViewPurchase", urlPatterns = {"/viewpurchase"})
public class ViewMarket extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_page = request.getParameter("page");
        String type = request.getParameter("selectid");
        if (type == null || type.trim().length() == 0) {
            type = "Newest";
        }
        String pricefrom = request.getParameter("pricefrom");
        String priceto = request.getParameter("priceto");
        if (pricefrom == null || pricefrom.trim().length() == 0) {
            pricefrom = "0";
        }
        if (priceto == null || priceto.trim().length() == 0) {
            priceto = "More than 5000";
        }
        ProductDao dbProduct = new ProductDao();
        if (raw_page == null || raw_page.trim().length() == 0) {
            raw_page = "1";
        }
        int products = dbProduct.countProduct(pricefrom, priceto);
        int totalpage = (products % 10 == 0) ? (products / 10) : (products / 10) + 1;
        int page = Integer.parseInt(raw_page);
        GameDao dbGame = new GameDao();
        UserDao dbUser = new UserDao();
        ArrayList<Product> p = dbProduct.getProduct(pricefrom, priceto, page, type);
        ArrayList<Game> g = dbGame.listGame();
        ArrayList<User> u = dbUser.getAllUser();
        request.setAttribute("pricefrom", pricefrom);
        request.setAttribute("priceto", priceto);
        request.setAttribute("search", type);
        request.setAttribute("totalpage", totalpage);
        request.setAttribute("page", page);
        request.setAttribute("u", u);
        request.setAttribute("p", p);
        request.setAttribute("g", g);
        MessageDao dbMessage = new MessageDao();
        Integer meID = ((User) request.getSession(false).getAttribute("account")).getID();
        List<User> friendList = dbMessage.loadFriend(meID);
        request.setAttribute("friendList", friendList);
        request.getRequestDispatcher("/view/Market/Market.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_id = request.getParameter("productID");

        int id = Integer.parseInt(raw_id);
        ProductDao dbProduct = new ProductDao();
        Product p = dbProduct.getDetailProductById(id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(p);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        System.out.println();
//        printWriter.flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
