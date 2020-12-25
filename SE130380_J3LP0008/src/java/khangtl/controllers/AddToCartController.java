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
import khangtl.dtos.CartObj;
import khangtl.dtos.TourDTO;
import khangtl.models.BookingDetailsBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class AddToCartController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(AddToCartController.class);
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
            String name, fromDate, toDate, image, departure, destination;
            int price, quota;
            TourDTO dto;
            HttpSession session = request.getSession();
            CartObj cart = (CartObj) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObj();
            }
            int id = Integer.parseInt(request.getParameter("id"));
            if (!cart.getStoreId().contains(id)) {
                name = request.getParameter("name");
                fromDate = request.getParameter("fromDate");
                toDate = request.getParameter("toDate");
                price = Integer.parseInt(request.getParameter("price"));
                quota = Integer.parseInt(request.getParameter("quota"));
                image = request.getParameter("image");
                image = image.replace(" ", "+");
                departure = request.getParameter("departure");
                destination = request.getParameter("destination");
                dto = new TourDTO(id, name, image, departure, destination, price, quota, fromDate, toDate);
            } else {
                dto = cart.getItemById(id);
                quota = dto.getQuota();
            }
            int amount = Integer.parseInt(request.getParameter("amount"));
            BookingDetailsBean detailsBeans = new BookingDetailsBean();
            int amountBooked = detailsBeans.getAmountTourBooked();
            int balance = 0;
            if (cart.getItems() != null) {
                int amountInCart;
                if (cart.getItems().containsKey(cart.getItemById(id))) {
                    amountInCart = cart.getItems().get(cart.getItemById(id));
                    balance = quota - amountBooked - amountInCart;
                } else {
                    balance = quota - amountBooked;
                }
            } else {
                balance = quota - amountBooked;
            }
            if (amount <= balance) {
                cart.saveId(id);
                cart.addItemToCart(dto, amount);
                session.setAttribute("CART", cart);
                response.getWriter().write("Add cart successful");
            } else {
                response.getWriter().write("This tour just have " + balance + " slot left");
            }
        } catch (Exception e) {
            LOGGER.debug("Error at AddToCartController: " + e.getMessage());
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
