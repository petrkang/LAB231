/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.RequestDTO;
import khangtl.dtos.RequestStatusDTO;
import khangtl.models.RequestBean;
import khangtl.models.RequestStatusBean;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class ShowRequestAction extends ActionSupport {
    private static final String REQUEST_LIST = "request_list_manager";
    public ShowRequestAction() {
    }
    
    @Override
    public String execute() throws Exception {
        String label = REQUEST_LIST;
        RequestBean requestBeans = new RequestBean();
        RequestStatusBean requestStatusBeans = new RequestStatusBean();
        List<RequestDTO> requestList = requestBeans.getAllRequests();
        List<RequestStatusDTO> requestStatusList = requestStatusBeans.getAllRequestStatus();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("requestShowList", requestList);
        request.getSession().setAttribute("requestStatusList", requestStatusList);
        return label;
    }
    
}
