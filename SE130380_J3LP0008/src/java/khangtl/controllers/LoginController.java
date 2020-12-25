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
import javax.servlet.http.HttpSession;
import khangtl.dtos.UserDTO;
import khangtl.errors.UserError;
import khangtl.models.UserBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class LoginController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final String INVALID = "login.jsp";
    private static final String HOME = "home.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID;
        try {
            String id = request.getParameter("txtId");
            String password = request.getParameter("txtPassword");
            UserError err = new UserError();

            boolean valid = true;
            if (id.trim().isEmpty()) {
                err.setId("Invalid id");
                valid = false;
            }

            if (password.isEmpty()) {
                err.setPassword("Invalid password");
                valid = false;
            }
            if (valid) {
                UserBean beans = new UserBean();
                beans.setId(id);
                beans.setPassword(password);

                UserDTO dto = beans.checkLogin();
                if (dto == null) {
                    request.setAttribute("ERROR", "Wrong id or password. Please try again");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", dto.getId());
                    session.setAttribute("FULLNAME", dto.getFirstname() + " " + dto.getLastname());
                    session.setAttribute("ROLE", dto.getRole());
                    if (!request.getParameter("pageAfterLogin").isEmpty()) {
                        url = request.getParameter("pageAfterLogin");
                    } else {
                        url = HOME;
                    }
                }
            } else {
                url = INVALID;
                request.setAttribute("userErr", err);
            }

        } catch (Exception e) {
            LOGGER.debug("Error at LoginController: " + e.getMessage());
        } finally {
            if (url.equals(HOME)) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
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
