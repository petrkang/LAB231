/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangtl.utils.PaymentServices;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class ReviewPaymentController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ReviewPaymentController.class);
    private static final String REVIEW = "review.jsp";
    private static final String ERROR = "error.jsp";
    private static final String HOME = "home.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = REVIEW;
        try {
            if (request.getParameterMap().isEmpty()) {
                url = HOME;
                response.sendRedirect(url);
                return;
            }
            String paymentId = request.getParameter("paymentId");
            String payerId = request.getParameter("PayerID");
            PaymentServices pmSer = new PaymentServices();
            Payment pm = pmSer.getPaymentDetails(paymentId);
            PayerInfo payerInfo = pm.getPayer().getPayerInfo();
            Transaction trans = pm.getTransactions().get(0);
            ItemList items = trans.getItemList();
            request.setAttribute("PAYER", payerInfo);
            request.setAttribute("transaction", trans);
            request.setAttribute("itemList", items.getItems());
            url += "?paymentId=" + paymentId + "&PayerID=" + payerId;
        } catch (Exception e) {
            url = ERROR;
            request.setAttribute("ERROR", e.getMessage());
            LOGGER.debug("Error at ReviewPaymentController: " + e.getMessage());
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
