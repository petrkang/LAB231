/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Peter
 */
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "LoginController";
    private static final String HOME = "HomeController";
    private static final String SEARCH = "SearchController";
    private static final String NEW_QUESTION = "NewQuestionController";
    private static final String CREATE_QUESTION = "CreateQuestionController";
    private static final String UPDATE = "UpdateController";
    private static final String EDIT = "EditController";
    private static final String START_QUIZ = "StartQuizController";
    private static final String PREV_QUESTION = "PrevQuestionController";
    private static final String NEXT_QUESTION = "NextQuestionController";
    private static final String SUBMIT = "SubmitQuizController";
    private static final String DELETE = "DeleteController";
    private static final String REGISTER = "RegisterController";
    private static final String LOGOUT = "LogoutController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
                String action = request.getParameter("action");
                switch (action) {
                    case "Login":
                        url = LOGIN;
                        break;
                    case "Home":
                        url = HOME;
                        break;
                    case "Search":
                        url = SEARCH;
                        break;
                    case "NewQuestion":
                        url = NEW_QUESTION;
                        break;
                    case "CreateQuestion":
                        url = CREATE_QUESTION;
                        break;
                    case "Update":
                        url = UPDATE;
                        break;
                    case "Edit":
                        url = EDIT;
                        break;
                    case "StartQuiz":
                        url = START_QUIZ;
                        break;
                    case "Prev":
                        url = PREV_QUESTION;
                        break;
                    case "Next":
                        url = NEXT_QUESTION;
                        break;
                    case "Submit":
                        url = SUBMIT;
                        break;
                    case "Delete":
                        url = DELETE;
                        break;
                    case "Register":
                        url = REGISTER;
                        break;
                    case "Logout":
                        url = LOGOUT;
                        break;
                }
        } catch (Exception e) {
            log("Error at MainController: " + e.getMessage());
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
