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
import khangtl.errors.UserError;
import khangtl.models.UserBean;

/**
 *
 * @author Peter
 */
public class RegisterController extends HttpServlet {

    private static final String ERROR = "register.jsp";
    private static final String INVALID = "register.jsp";
    private static final String SUCCESS = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String lastname = request.getParameter("txtLastname");
            String firstname = request.getParameter("txtFirstname");
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String confirmPassword = request.getParameter("txtConfirm");
            String role = request.getParameter("txtRole");
            if (role == null) {
                role = "Student";
            }

            UserError err = new UserError();
            boolean valid = true;

            if (lastname.isEmpty()) {
                err.setLastname("Invalid empty input");
                valid = false;
            }

            if (firstname.isEmpty()) {
                err.setFirstname("Invalid empty input");
                valid = false;
            }

            if (!email.matches("^[\\w!#$%&'*+=?`{|}~^-]+(?:\\.[\\w!#$%&'*+=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                err.setEmail("Invalid email");
                valid = false;
            }

            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$")) {
                err.setPassword("Invalid password");
                valid = false;
            }

            if (!confirmPassword.equals(password)) {
                err.setConfirm("Invalid not match password above");
                valid = false;
            }

            if (valid) {
                UserBean beans = new UserBean();
                beans.setLastname(lastname);
                beans.setFirstname(firstname);
                beans.setEmail(email);
                beans.setPassword(password);
                beans.setRole(role);

                if (beans.checkExistAccount()) {
                    request.setAttribute("ERROR", "Account is already existed. Please try again");
                } else {
                    if (beans.registerAccount()) {
                        request.setAttribute("SUCCESS", "Your account registered successfully. Please login");
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Account registered failed");
                    }
                }
            } else {
                url = INVALID;
                request.setAttribute("userErr", err);
            }
        } catch (Exception e) {
            log("Error at RegisterController: " + e.getMessage());
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
        request.setCharacterEncoding("UTF-8");
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
