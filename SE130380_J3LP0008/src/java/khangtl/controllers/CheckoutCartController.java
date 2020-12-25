/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import com.paypal.base.rest.PayPalRESTException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangtl.dtos.BookingDTO;
import khangtl.dtos.BookingDetailsDTO;
import khangtl.dtos.CartObj;
import khangtl.dtos.DiscountDTO;
import khangtl.dtos.TourDTO;
import khangtl.models.BookingDetailsBean;
import khangtl.utils.PaymentServices;
import khangtl.utils.Utility;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class CheckoutCartController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CheckoutCartController.class);
    private static final String INVALID = "cart.jsp";
    private static final String SUCCESS = "payment.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SUCCESS;
        try {
            BookingDTO dto;
            String discountCode = request.getParameter("txtTmpDiscountCode");
            String discountPercent = request.getParameter("txtDiscountPercent");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date bookingDate = new Date();
            bookingDate = sf.parse(sf.format(bookingDate));
            ArrayList<BookingDetailsDTO> bookingList = new ArrayList<>();
            String responseStr = null, name, bookingId = null;
            int price, totalPrice = 0, amount, amountBooked, balance, quota, id;
            boolean isFirst = true;
            BookingDetailsBean beans = new BookingDetailsBean();
            CartObj cart = (CartObj) request.getSession().getAttribute("CART");

            for (Map.Entry<TourDTO, Integer> entry : cart.getItems().entrySet()) {
                id = entry.getKey().getId();
                name = entry.getKey().getName();
                price = entry.getKey().getPrice();
                quota = entry.getKey().getQuota();
                amount = entry.getValue();
                beans.setTourId(id);
                amountBooked = beans.getAmountTourBooked();
                balance = quota - amountBooked;
                if (amount > balance) {
                    if (isFirst) {
                        responseStr = name + " (" + balance + " slot left)";
                    } else {
                        responseStr = ", " + name + " (" + balance + " slot left)";
                    }
                }
                totalPrice += amount * price;
                bookingId = Utility.hashStringSHA256((String) request.getSession().getAttribute("USER") + bookingDate.getTime());
                bookingList.add(new BookingDetailsDTO(bookingId, name, id, amount, price));
            }
            if (discountCode.trim().isEmpty()) {
                dto = new BookingDTO(bookingId, (String) request.getSession().getAttribute("USER"), totalPrice, bookingDate, bookingList);
            } else {
                dto = new BookingDTO(bookingId, (String) request.getSession().getAttribute("USER"), totalPrice, bookingDate, bookingList, new DiscountDTO(discountCode, Integer.parseInt(discountPercent)));
            }
            if (responseStr != null) {
                url = INVALID;
                request.setAttribute("ERROR", "Some of your tour is out of stock: " + responseStr);
            } else {
                PaymentServices pmSer = new PaymentServices();
                String approvalLink = pmSer.authorizePayment(dto);
                request.getSession().setAttribute("BOOKING", dto);
                response.sendRedirect(approvalLink);
            }
        } catch (PayPalRESTException pe) {
            url = ERROR;
            if (pe.getMessage().contains("INTERNAL_SERVICE_ERROR")) {
                request.setAttribute("ERROR", "An internal service occured with Paypal Server");
            } else {
                request.setAttribute("ERROR", pe.getMessage());
            }
            LOGGER.debug("Error at CheckoutCartController: " + pe.getMessage());
        } catch (Exception e) {
            LOGGER.debug("Error at CheckoutCartController: " + e.getMessage());
        } finally {
            if (!response.isCommitted()) {
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
