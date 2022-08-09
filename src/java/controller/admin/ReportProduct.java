/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import com.google.gson.Gson;
import dao.ReportDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modal.User;
import modal.Report;

@WebServlet(name = "Report", urlPatterns = {"/Report"})
public class ReportProduct extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("account") != null) {
            if (((User) request.getSession().getAttribute("account")).isAdmin()) {
                ReportDao dbReport = new ReportDao();
                ArrayList<Report> r = dbReport.getReportProduct();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String json = new Gson().toJson(r);
                PrintWriter printWriter = response.getWriter();
                printWriter.print(json);
            } else {
                response.sendRedirect("viewNewsfeed");
            }
        } else {
            response.sendRedirect("Login");

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("----------------");
        String raw_id = request.getParameter("productID");
        int pid = Integer.parseInt(raw_id);
        String report = request.getParameter("report");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        int uid = user.getID();
        System.out.println(uid + report + pid);

        Report p = new Report();
        p.setPurID(pid);
        p.setReportMessage(report);
        p.setUserID(uid);

        ReportDao rep = new ReportDao();
        rep.InsertReportProduct(p);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
