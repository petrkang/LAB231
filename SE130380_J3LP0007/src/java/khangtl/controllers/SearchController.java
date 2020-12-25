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
import khangtl.dtos.QuestionDTO;
import khangtl.models.QuestionBean;

/**
 *
 * @author Peter
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "HomeController";
        try {
            String search, subject, status;
            int currentPage = 1;
            String action = request.getParameter("action");
            if (action.equals("Search")) {
                search = request.getParameter("txtSearch");
                subject = request.getParameter("txtSubject");
                status = request.getParameter("txtStatus");
            } else {
                search = (String) request.getSession().getAttribute("searchInput");
                subject = (String) request.getSession().getAttribute("subjectSelected");
                status = (String) request.getSession().getAttribute("statusSelected");
                if (action.equals("Prev")) {
                    if (currentPage > 1) {
                        currentPage--;
                    }
                } else if (action.equals("Next")) {
                    currentPage++;
                }
            }
            request.getSession().setAttribute("searchInput", search);
            request.getSession().setAttribute("subjectSelected", subject);
            request.getSession().setAttribute("statusSelected", status);
            QuestionBean beans = new QuestionBean();
            beans.setCont(search);
            beans.setSubject(subject);
            beans.setStatus(status);
            ArrayList<QuestionDTO> result = beans.searchQuestionWithCondition();
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("questionList", result);
        } catch (Exception e) {
            log("Error at SearchController: " + e.getMessage());
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
