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
import khangtl.dtos.DiscountDTO;
import khangtl.models.DiscountBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class CheckDiscountController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckDiscountController.class);
    private static final String HOME = "Home";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HOME;
        try {
            if (request.getParameterMap().isEmpty()) {
                response.sendRedirect(url);
                return;
            }
            String code = request.getParameter("code");
            DiscountBean beans = new DiscountBean();
            beans.setCode(code);
            DiscountDTO dto = beans.checkAvailableDiscount();
            if (dto == null) {
                response.getWriter().write("Your code is invalid or expired");
            } else {
                response.getWriter().write(String.valueOf(dto.getPercent()));
            }
        } catch (Exception e) {
            LOGGER.debug("Error at CheckDiscountController: " + e.getMessage());
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
