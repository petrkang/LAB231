/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import khangtl.dtos.TourDTO;
import khangtl.errors.TourError;
import khangtl.models.TourBean;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class SearchController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchController.class);
    private static final String HOME = "home.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = HOME;
        try {
            if (request.getParameterMap().isEmpty()) {
                response.sendRedirect(url);
                return;
            }
            String departure, destination, fromDate, toDate, minPrice, maxPrice;
            int min = 0, max = 0, currentPage, recordsPerPage;
            if (request.getParameter("currentPage") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
            if (request.getParameter("recordsPerPage") == null) {
                recordsPerPage = 5;
            } else {
                recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
            }
            Date start = null, end = null;
            departure = request.getParameter("departure");
            destination = request.getParameter("destination");
            fromDate = request.getParameter("fromDate");
            toDate = request.getParameter("toDate");
            minPrice = request.getParameter("txtMin");
            maxPrice = request.getParameter("txtMax");
            TourError err = new TourError();
            boolean valid = true;
            if (!departure.isEmpty() && !destination.isEmpty()) {
                if (departure.equals(destination)) {
                    valid = false;
                    err.setDeparture("Departure must different with destination");
                    err.setDestination("Destination must different with departure");
                }
            }
            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fromDate);
                end = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(toDate);
                if (!(start.compareTo(end) < 0)) {
                    valid = false;
                    err.setFromDate("Start date must less than end date");
                    err.setToDate("End date must greater than start date");
                }
            }
            if (!minPrice.isEmpty()) {
                min = Integer.valueOf(minPrice);
            }
            if (!maxPrice.isEmpty()) {
                max = Integer.valueOf(maxPrice);
            }
            if (min != 0 && max != 0) {
                if (min > max) {
                    valid = false;
                    err.setMinPrice("Min must less than max");
                    err.setMaxPrice("Max must greater than min");
                }
            }
            if (valid) {
                TourBean beans = new TourBean();
                beans.setDeparture(departure);
                beans.setDestination(destination);
                if (start != null) {
                    beans.setFromDate(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(start));
                } else {
                    beans.setFromDate(null);
                }
                if (end != null) {
                    beans.setToDate(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(end));
                } else {
                    beans.setToDate(null);
                }
                beans.setMinPrice(min);
                beans.setMaxPrice(max);
                int rows = beans.getNumberOfRows();
                int noOfPages = rows / recordsPerPage;
                if (noOfPages % recordsPerPage > 0) {
                    noOfPages++;
                }
                beans.setCurrentPage(currentPage);
                beans.setRecordsPerPage(recordsPerPage);
                ArrayList<TourDTO> result = beans.searchTourWithCondition();
                request.setAttribute("tourList", result);
                request.setAttribute("departureSelected", departure);
                request.setAttribute("destinationSelected", destination);
                request.setAttribute("fromDate", fromDate);
                request.setAttribute("toDate", toDate);
                request.setAttribute("txtMin", minPrice);
                request.setAttribute("txtMax", maxPrice);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("recordsPerPage", recordsPerPage);
            } else {
                request.setAttribute("tourErr", err);
            }
        } catch (Exception e) {
            LOGGER.debug("Error at SearchController: " + e.getMessage());
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
