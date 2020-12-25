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
import khangtl.dtos.CartObj;
import khangtl.dtos.TourDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class UpdateCartController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UpdateCartController.class);
    private static final String CART = "cart.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CART;
        try {
            if (request.getParameterMap().isEmpty()) {
                response.sendRedirect(url);
                return;
            }
            TourDTO dto;
            CartObj cart = (CartObj) request.getSession().getAttribute("CART");
            int id = Integer.parseInt(request.getParameter("id"));
            dto = cart.getItemById(id);
            int amount = Integer.parseInt(request.getParameter("amount"));
            cart.updateItemCart(dto, amount);
            request.getSession().setAttribute("CART", cart);
        } catch (Exception e) {
            LOGGER.debug("Error at UpdateCartController: " + e.getMessage());
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
