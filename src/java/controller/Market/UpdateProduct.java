/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Market;

import com.google.gson.Gson;
import dao.ImageDao;
import dao.ProductDao;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
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
import modal.Product;
import modal.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateProduct", urlPatterns = {"/UpdateProduct"})
@MultipartConfig(maxFileSize = 16177215)
public class UpdateProduct extends HttpServlet {

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
            out.println("<title>Servlet UpdateProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateProduct at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        String productID_raw = request.getParameter("productID");
        int productID = Integer.parseInt(productID_raw);
        ProductDao db = new ProductDao();
        Product p = db.getProductByID(productID);
        PrintWriter out = response.getWriter();
//        String html = "";
//        html+="<div class=\"modal\" id=\"myModal\">\n" +
//                    "            <div class=\"modal-dialog modal-lg\">\n" +
//                    "                <div class=\"modal-content\">\n" +
//                    "\n" +
//                    "                    <!-- Modal Header -->\n" +
//                    "\n" +
//                    "                    <!-- Modal body -->\n" +
//                    "                    <form enctype=\"multipart/form-data\">\n" +
//                    "                        <div class=\"modal-header\">\n" +
//                    "\n" +
//                    "                            <input type=\"hidden\" name=\"uID\" value=\""+user.getID()+"\"/>\n" +
//                    "                            <h4 class=\"modal-title\">"+user.getDisplayname()+"</h4>\n" +
//                    "                            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"modal\"></button>\n" +
//                    "                        </div>\n" +
//                    "                        <div class=\"modal-body\">\n" +
//                    "                            <label>Name Product: </label>\n" +
//                    "                            <input class=\"form-control\" id=\"nameProduct\" name=\"nameProduct\"value=\""+p.getName()+"\" rows=\"3\">\n" +
//                    "                            <label>Description: </label>\n" +
//                    "                            <input class=\"form-control\" id=\"description\" name=\"description\"value=\""+p.getDescription()+"\" rows=\"3\">\n" +
//                    "                            <label>Price: </label>\n" +
//                    "                            <input class=\"form-control\" id=\"price\" name=\"price\"value=\""+p.getPrice()+"\" rows=\"3\">\n" +
//                    "\n" +
//                    "                            <label class=\"mt-3\">Choose Image: </label>\n" +
//                    "                            <input type=\"file\" name=\"photo\"id=\"uploadImage\"multiple=\"true\" size=\"50\" onchange=\"loadFile()\"/>\n" +
//                    "                            <div id=\"images\" class=\"mt-3\">\n" +
//                    "\n" +                          "<img style=\"width:50px\"src=\""+p.getImage()+"\">\n"+
//                    "                            </div>\n" +
//                    "\n" +
//                    "                        </div>\n" +
//                    "                        <div class=\"modal-body\">\n" +
//                    "                            Type of game: <div id=\"selectGame\" ></div>\n" +
//                    "                           \n" +
//                    "                        </div>\n" +
//                    "                        <div class=\"modal-footer\">\n" +
//                    "                            <button id=\"creatPostbutton\"class=\"btn\">Save</button>\n" +
//                    "                        </div>\n" +
//                    "                    </form>\n" +
//                    "                    <!-- Modal footer -->\n" +
//                    "\n" +
//                    "\n" +
//                    "                </div>\n" +
//                    "            </div>             \n" +
//                    "        </div>";
//       
//       out.print(html);

        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new Gson().toJson(p);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
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
        String productID_raw = request.getParameter("productID");
        int productID = Integer.parseInt(productID_raw);
        String nameProduct = request.getParameter("nameProduct");
        String description = request.getParameter("description");
        String price_raw = request.getParameter("price");
        int price = Integer.parseInt(price_raw);
        String gID_raw = request.getParameter("gID");
        int gID = Integer.parseInt(gID_raw);
        Product p = new Product();
        p.setPurID(productID);
        p.setGameID(gID);
        p.setName(nameProduct);
        p.setDescription(description);
        p.setPrice(price);
        ProductDao db = new ProductDao();
        db.UpdateProduct(p);
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
