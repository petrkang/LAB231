/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khangtl.dtos.UserDTO;
import khangtl.models.UserBean;
import khangtl.utils.APIWrapper;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class LoginFacebookController extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginFacebookController.class);
    private static final String HOME = "home.jsp";

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
            APIWrapper wrapper = new APIWrapper();
            String accessToken = wrapper.getAccessToken(code);
            wrapper.setAccessToken(accessToken);
            JsonElement jsonEle = wrapper.getUserDTO();
            JsonObject jsonObj = jsonEle.getAsJsonObject();
            UserBean beans = new UserBean();
            beans.setId(jsonObj.get("id").toString().replace("\"", ""));
            if (beans.checkLoginViaFacebook() == null) {
                beans.setFirstname(jsonObj.get("name").toString().replace("\"", ""));
                beans.setLastname("");
                beans.registerAccountViaFacebook();
            }
            UserDTO dto = beans.checkLoginViaFacebook();
            HttpSession session = request.getSession();
            session.setAttribute("USER", beans.getId());
            session.setAttribute("FULLNAME", dto.getFirstname() + " " + dto.getLastname());
            session.setAttribute("ROLE", dto.getRole());
        } catch (Exception e) {
            LOGGER.debug("Error at LoginFacebookController: " + e.getMessage());
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
