/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import khangtl.dtos.CartObj;
import khangtl.dtos.ResourceDTO;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class DeleteResourceRequestAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(DeleteResourceRequestAction.class);
    private static final String SUCCESS = "success";

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeleteResourceRequestAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            ResourceDTO dto;
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            CartObj cart = (CartObj) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObj();
            }
            dto = cart.getItemById(id);
            cart.removeItemFromCart(dto);
            cart.removeId(id);
            if (cart.getItems() == null) {
                session.removeAttribute("CART");
            } else {
                session.setAttribute("CART", cart);
            }
        } catch (Exception e) {
            LOGGER.debug("Error at DeleteTourController: " + e.getMessage());
        }
        return label;
    }

}
