/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangtl.daos.QuestionDAO;

/**
 *
 * @author Peter
 */
@WebServlet(name = "SubmitQuizController", urlPatterns = {"/SubmitQuizController"})
public class SubmitQuizController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HashMap<Integer, Integer> submitList = (HashMap<Integer, Integer>) request.getSession().getAttribute("submitList");
            QuestionDAO dao = new QuestionDAO();
            int numOfTrue = dao.getNumOfTrue(submitList);
            int totalQuestion = (int) request.getSession().getAttribute("questionListTestSize");
            double point = (double) numOfTrue * 10 / totalQuestion;
            String email = (String) request.getSession().getAttribute("USER");
            String subject = (String) request.getSession().getAttribute("subjectTest");
            Date startTime = (Date) request.getSession().getAttribute("startTime");
            if (dao.finalSubmit(email, point, subject, startTime, submitList)) {
                request.setAttribute("SUCCESS", "This is your point of");
            } else {
                request.setAttribute("ERROR", "Submit failed");
            }
            request.setAttribute("numOfTrue", numOfTrue);
            request.setAttribute("totalQuestion", totalQuestion);
            request.setAttribute("point", point);
        } catch (Exception e) {
            log("Error at SubmitQuizController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("showPoint.jsp").forward(request, response);
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
