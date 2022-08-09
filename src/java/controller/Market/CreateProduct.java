/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Market;

import dao.ImageDao;
import dao.ProductDao;
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
import modal.Product;
import modal.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CreateProduct", urlPatterns = {"/CreateProduct"})
@MultipartConfig(maxFileSize = 16177215)
public class CreateProduct extends HttpServlet {

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
            out.println("<title>Servlet CreateProduct</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateProduct at " + request.getContextPath() + "</h1>");
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
//        GameDBContext dbGame = new GameDBContext();
//        ArrayList<Game> g = dbGame.listGame();
//        request.setAttribute("listgame", g);
//        request.getRequestDispatcher("resource/components/PopUpCreateProduct.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int uID = user.getID();
        
        String nameProduct = request.getParameter("nameProduct");
        String description = request.getParameter("description");
        String price_raw = request.getParameter("price");
      
      
        int price = Integer.parseInt(price_raw);
        String gID_raw = request.getParameter("gID");
        int gID = Integer.parseInt(gID_raw);
        Product p = new Product();
        p.setUserID(uID);
        p.setGameID(gID);
        p.setName(nameProduct);
        p.setDescription(description);
        p.setPrice(price);
//        System.out.println(p.getUserID());
//        System.out.println(p.getGameID());
//        System.out.println(p.getName());
//        System.out.println(p.getDescription());
        
        ProductDao db = new ProductDao();
        
        Integer productID = db.CreateProduct(p);
        
        System.out.println(productID);
        ImageDao dbImage = new ImageDao();
        List<Part> fileParts = request.getParts().stream()
                .filter(part -> "photo".equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());

        for (Part filePart : fileParts) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();
            dbImage.saveImageProduct(productID, fileContent);
            
        }
        response.sendRedirect("viewpurchase");
        
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
