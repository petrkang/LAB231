/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "StartQuizController", urlPatterns = {"/StartQuizController"})
public class StartQuizController extends HttpServlet {

    private static final String INVALID = "HomeController";
    private static final String SUCCESS = "startQuiz.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SUCCESS;
        try {
            String subject = request.getParameter("txtSubject");
            boolean valid = true;
            if (subject.isEmpty()) {
                request.setAttribute("ERROR", "Please select a subject to start your test");
                valid = false;
            }

            if (valid) {
                QuestionBean beans = new QuestionBean();
                beans.setSubject(subject);
                ArrayList<QuestionDTO> result = beans.getListQuestionToTest();
                int testTime = beans.getTimeToTest();
                Date now = new Date();
                
                Timestamp endTime = new Timestamp(now.getTime() + testTime * 60 * 1000);
                
                int currentQuestionIndex = 0;
                request.getSession().setAttribute("subjectTest", subject);
                request.getSession().setAttribute("startTime", now);
                request.getSession().setAttribute("endTime", endTime);
                request.getSession().setAttribute("questionListTest", result);
                request.getSession().setAttribute("questionListTestSize", result.size());
                request.setAttribute("currentQuestionIndex", currentQuestionIndex);
                request.setAttribute("questionTest", result.get(currentQuestionIndex));
            } else {
                url = INVALID;
            }
        } catch (Exception e) {
            log("Error at StartQuizController: " + e.getMessage());
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
