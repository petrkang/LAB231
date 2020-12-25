/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import khangtl.dtos.CartObj;
import khangtl.dtos.ResourceDTO;
import khangtl.models.ResourceBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class AddToRequestListAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(AddToRequestListAction.class);

    public AddToRequestListAction() {
    }

    @Override
    public String execute() throws Exception {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            if (request.getParameterMap().isEmpty()) {
                return "none";
            }
            String name, category, usingDate;
            int id, balance = 0;
            ResourceDTO dto;
            HttpSession session = request.getSession();
            CartObj cart = (CartObj) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartObj();
            }
            id = Integer.parseInt(request.getParameter("id"));
            if (!cart.getStoreId().contains(id)) {
                name = request.getParameter("name");
                category = request.getParameter("category");
                usingDate = request.getParameter("usingDate");
                balance = Integer.parseInt(request.getParameter("balance"));
                dto = new ResourceDTO(id, balance, name, usingDate, category);
            } else {
                dto = cart.getItemById(id);
                balance = dto.getBalance();
            }
            int amount = Integer.parseInt(request.getParameter("amount"));
            ResourceBean beans = new ResourceBean();
            beans.setId(id);
            int slotLeft = beans.getBalanceById();
            if (slotLeft != balance) {
                response.getWriter().write("Balance has changed. Please try again");
            } else {
                int finalBalance;
                if (cart.getItems() != null) {
                    int amountInCart;
                    if (cart.getItems().containsKey(cart.getItemById(id))) {
                        amountInCart = cart.getItems().get(cart.getItemById(id));
                        finalBalance = balance - amountInCart;
                    } else {
                        finalBalance = balance;
                    }
                } else {
                    finalBalance = balance;
                }
                if (amount <= finalBalance) {
                    cart.saveId(id);
                    cart.addItemToCart(dto, amount);
                    session.setAttribute("CART", cart);
                    response.getWriter().write("Add to request list successful");
                } else {
                    response.getWriter().write("This resource just have " + finalBalance + " slot left");
                }
            }
        } catch (Exception e) {
            LOGGER.debug("Error at AddToRequestListAction: " + e.getMessage());
        }
        return null;
    }

}
