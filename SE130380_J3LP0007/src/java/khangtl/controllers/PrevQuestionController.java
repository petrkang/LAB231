/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangtl.dtos.QuestionDTO;

/**
 *
 * @author Peter
 */
@WebServlet(name = "PrevQuestionController", urlPatterns = {"/PrevQuestionController"})
public class PrevQuestionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            ArrayList<QuestionDTO> result = (ArrayList<QuestionDTO>) request.getSession().getAttribute("questionListTest");
            int currentQuestionIndex = Integer.parseInt(request.getParameter("currentQuestionIndex"));

            HashMap<Integer, Integer> submitList;
            if (request.getSession().getAttribute("submitList") == null) {
                submitList = new HashMap<>();
                request.getSession().setAttribute("submitList", submitList);
            } else {
                submitList = (HashMap<Integer, Integer>) request.getSession().getAttribute("submitList");
            }
            if (request.getParameter("txtSelect") != null) {
                int answerId = Integer.parseInt(request.getParameter("txtSelect"));
                int questionId = Integer.parseInt(request.getParameter("txtQuestion"));
                boolean checkExist = false;
                for (Map.Entry pair : submitList.entrySet()) {
                    if (pair.getKey().equals(questionId)) {
                        if (!pair.getValue().equals(answerId)) {
                            pair.setValue(answerId);
                        }
                        checkExist = true;
                    }
                }
                if (!checkExist) {
                    submitList.put(questionId, answerId);
                }
            }
            request.setAttribute("submitList", submitList);
            currentQuestionIndex--;
            request.setAttribute("currentQuestionIndex", currentQuestionIndex);
            request.setAttribute("questionTest", result.get(currentQuestionIndex));
        } catch (Exception e) {
            log("Error at PrevQuestionController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("startQuiz.jsp").forward(request, response);
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
