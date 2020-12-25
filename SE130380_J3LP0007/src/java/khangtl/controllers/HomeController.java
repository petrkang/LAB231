/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangtl.dtos.StatusDTO;
import khangtl.dtos.SubjectDTO;
import khangtl.models.StatusBean;
import khangtl.models.SubjectBean;

/**
 *
 * @author Peter
 */
@WebServlet(name = "HomeController", urlPatterns = {"/HomeController"})
public class HomeController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String STUDENT = "student.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String role = (String) request.getSession().getAttribute("ROLE");
            SubjectBean subjectBeans = new SubjectBean();
            StatusBean statusBeans = new StatusBean();
            ArrayList<SubjectDTO> subjectList = subjectBeans.getAllSubjects();
            ArrayList<StatusDTO> statusList = statusBeans.getAllStatus();
            switch (role) {
                case "Admin":
                    url = ADMIN;
                    request.setAttribute("subjectList", subjectList);
                    request.setAttribute("statusList", statusList);
                    break;
                case "Student":
                    url = STUDENT;
                    request.getSession().removeAttribute("submitList");
                    request.setAttribute("subjectList", subjectList);
                    break;
                default:
                    request.setAttribute("ERROR", "Your role is invalid");
                    break;
            }
        } catch (Exception e) {
            log("Error at HomeController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
        processRequest(request, response);
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
