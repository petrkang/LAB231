/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.CartObj;
import khangtl.dtos.ResourceDTO;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class UpdateRequestListAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(UpdateRequestListAction.class);

    public UpdateRequestListAction() {
    }

    @Override
    public String execute() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request.getParameterMap().isEmpty()) {
                return "none";
            }
            ResourceDTO dto;
            CartObj cart = (CartObj) request.getSession().getAttribute("CART");
            int id = Integer.parseInt(request.getParameter("id"));
            dto = cart.getItemById(id);
            int amount = Integer.parseInt(request.getParameter("amount"));
            cart.updateItemCart(dto, amount);
            request.getSession().setAttribute("CART", cart);
        } catch (Exception e) {
            LOGGER.debug("Error at UpdateRequestList: " + e.getMessage());
        }
        return null;
    }

}
