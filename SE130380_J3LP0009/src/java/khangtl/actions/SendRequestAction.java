/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.CartObj;
import khangtl.dtos.RequestDTO;
import khangtl.dtos.RequestDetailsDTO;
import khangtl.dtos.ResourceDTO;
import khangtl.models.RequestBean;
import khangtl.models.ResourceBean;
import khangtl.utils.Utility;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class SendRequestAction extends ActionSupport {
    
    private static final Logger LOGGER = Logger.getLogger(SendRequestAction.class);
    private static final String INVALID = "invalid";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    
    public SendRequestAction() {
    }
    
    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            RequestDTO dto;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date requestDate = new Date();
            requestDate = sf.parse(sf.format(requestDate));
            List<RequestDetailsDTO> requestList = new ArrayList<>();
            String responseStr = null, name, requestId = null;
            int id, amount, newBalance;
            boolean isFirst = true;
            ResourceBean resourceBeans = new ResourceBean();
            CartObj cart = (CartObj) request.getSession().getAttribute("CART");
            for (Map.Entry<ResourceDTO, Integer> entry : cart.getItems().entrySet()) {
                id = entry.getKey().getId();
                name = entry.getKey().getName();
                amount = entry.getValue();
                resourceBeans.setId(id);
                newBalance = resourceBeans.getBalanceById();
                if (amount > newBalance) {
                    if (isFirst) {
                        responseStr = name + " (" + newBalance + " slot left)";
                    } else {
                        responseStr = ", " + name + " (" + newBalance + " slot left)";
                    }
                }
                requestId = Utility.hashStringSHA256((String) request.getSession().getAttribute("USER") + requestDate.getTime());
                requestList.add(new RequestDetailsDTO(requestId, id, amount));
            }
            dto = new RequestDTO(requestId, (String) request.getSession().getAttribute("USER"), "Pending", requestDate);
            dto.setDetails(requestList);
            if (responseStr != null) {
                label = INVALID;
                request.setAttribute("ERROR", "Some of your resource is out of stock: " + responseStr);
            } else {
                RequestBean requestBeans = new RequestBean();
                requestBeans.setDto(dto);
                if (requestBeans.sendRequest()) {
                    request.setAttribute("SUCCESS", "Send request successful, please wait for response of manager");
                    request.getSession().removeAttribute("CART");
                } else {
                    label = ERROR;
                    request.setAttribute("ERROR", "Send request fail, please try again.");
                }
            }
        } catch (Exception e) {
            LOGGER.debug("Error at SendRequestAction: " + e.getMessage());
        }
        return label;
    }
    
}
