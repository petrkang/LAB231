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
import khangtl.dtos.AnswerDTO;
import khangtl.dtos.QuestionDTO;
import khangtl.errors.QuestionError;
import khangtl.models.QuestionBean;

/**
 *
 * @author Peter
 */
@WebServlet(name = "EditController", urlPatterns = {"/EditController"})
public class EditController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("txtId"));
            String cont = request.getParameter("txtCont");
            String subject = request.getParameter("txtSubject");
            String answer1 = request.getParameter("txtAnswer1");
            String answer2 = request.getParameter("txtAnswer2");
            String answer3 = request.getParameter("txtAnswer3");
            String answer4 = request.getParameter("txtAnswer4");
            String correctAnswer = request.getParameter("txtCorrectAnswer");

            boolean valid = true;
            QuestionError questionErr = new QuestionError();

            if (cont.isEmpty()) {
                questionErr.setCont("Please input question content");
                valid = false;
            }
            if (subject.isEmpty()) {
                questionErr.setSubject("Invalid subject");
                valid = false;
            }
            if (answer1.isEmpty()) {
                questionErr.setAnswer1("Please input answer for the question");
                valid = false;
            }
            if (answer2.isEmpty()) {
                questionErr.setAnswer2("Please input answer for the question");
                valid = false;
            }
            if (answer3.isEmpty()) {
                questionErr.setAnswer3("Please input answer for the question");
                valid = false;
            }
            if (answer4.isEmpty()) {
                questionErr.setAnswer4("Please input answer for the question");
                valid = false;
            }
            if (correctAnswer == null) {
                request.setAttribute("radioErr", "Please select the correct answer");
                valid = false;
            }

            if (valid) {
                QuestionDTO dto;
                AnswerDTO ans1 = new AnswerDTO(answer1, false);
                AnswerDTO ans2 = new AnswerDTO(answer2, false);
                AnswerDTO ans3 = new AnswerDTO(answer3, false);
                AnswerDTO ans4 = new AnswerDTO(answer4, false);
                QuestionBean beans = new QuestionBean();
                ArrayList<AnswerDTO> answerList = new ArrayList<>();
                switch (correctAnswer) {
                    case "answer1":
                        ans1.setIsCorrect(true);
                        break;
                    case "answer2":
                        ans2.setIsCorrect(true);
                        break;
                    case "answer3":
                        ans3.setIsCorrect(true);
                        break;
                    case "answer4":
                        ans4.setIsCorrect(true);
                        break;
                }
                answerList.add(ans1);
                answerList.add(ans2);
                answerList.add(ans3);
                answerList.add(ans4);
                dto = new QuestionDTO(cont, subject, answerList);
                beans.setId(id);
                beans.setDto(dto);
                if (beans.updateQuestion()) {
                    request.setAttribute("SUCCESS", "Update question successfully");
                } else {
                    request.setAttribute("ERROR", "Update question failed");
                    request.setAttribute("subjectChosen", subject);
                }
            } else {
                request.setAttribute("ERROR", "");
                request.setAttribute("subjectChosen", subject);
                request.setAttribute("questionErr", questionErr);
            }
        } catch (Exception e) {
            log("Error at EditController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("HomeController").forward(request, response);
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
