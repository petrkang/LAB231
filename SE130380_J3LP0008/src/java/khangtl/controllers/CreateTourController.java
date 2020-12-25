package khangtl.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.text.SimpleDateFormat;
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
public class CreateTourController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CreateTourController.class);
    private static final String CREATE = "createTour.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CREATE;
        try {
            String name, departure, destination, fromDate, toDate, image, price, quota;
            name = request.getParameter("txtName");
            departure = request.getParameter("departure");
            destination = request.getParameter("destination");
            fromDate = request.getParameter("fromDate");
            toDate = request.getParameter("toDate");
            image = request.getParameter("txtImage");
            price = request.getParameter("txtPrice");
            quota = request.getParameter("txtQuota");
            boolean valid = true;
            TourError err = new TourError();
            if (name.trim().isEmpty()) {
                valid = false;
                err.setName("Please input tour name");
            }
            if (!departure.isEmpty() && !destination.isEmpty()) {
                if (departure.equals(destination)) {
                    valid = false;
                    err.setDeparture("Departure must different from destination");
                    err.setDestination("Destination must different from departure");
                }
            } else {
                valid = false;
                if (departure.isEmpty()) {
                    err.setDeparture("Please select departure");
                }
                if (destination.isEmpty()) {
                    err.setDestination("Please select destination");
                }
            }

            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                Date start = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fromDate);
                Date end = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(toDate);
                if (!(start.compareTo(end) < 0)) {
                    valid = false;
                    err.setFromDate("Start date must less than end date");
                    err.setToDate("End date must greater than start date");
                }
            } else {
                valid = false;
                if (fromDate.isEmpty()) {
                    err.setFromDate("Please input from date");
                }
                if (toDate.isEmpty()) {
                    err.setToDate("Please input to date");
                }
            }

            if (image.isEmpty()) {
                valid = false;
                err.setImage("Please select image");
            }

            if (!price.matches("^(0*[1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*)$")) {
                valid = false;
                err.setPrice("Please input a number greater than 0");
            }

            if (!quota.matches("^(0*[1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*)$")) {
                valid = false;
                err.setQuota("Please input a number greater than 0");
            }

            if (valid) {
                TourBean beans = new TourBean();
                TourDTO dto = new TourDTO(name, image, departure, destination, Integer.parseInt(price), Integer.parseInt(quota), fromDate, toDate);
                beans.setDto(dto);
                if (beans.checkExistTour()) {
                    request.setAttribute("ERROR", "Insert failed. This tour is existed");
                } else {
                    if (beans.insertTour()) {
                        request.setAttribute("SUCCESS", "Insert successful");
                    } else {
                        request.setAttribute("ERROR", "Insert failed");
                    }
                }
            } else {
                request.setAttribute("tourErr", err);
                request.setAttribute("ERROR", "Validation failed");
            }
        } catch (Exception e) {
            LOGGER.debug("Error at CreateTourController: " + e.getMessage());
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
