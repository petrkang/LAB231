/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import khangtl.models.RequestBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class DeleteRequestAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(DeleteRequestAction.class);
    private static final String SUCCESS = "success";

    private String id, searchRequestDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchRequestDate() {
        return searchRequestDate;
    }

    public void setSearchRequestDate(String searchRequestDate) {
        this.searchRequestDate = searchRequestDate;
    }

    public DeleteRequestAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            RequestBean beans = new RequestBean();
            HttpServletRequest request = ServletActionContext.getRequest();
            beans.setId(id);
            beans.setRequestDate(searchRequestDate);
            if (beans.deleteRequestById()) {
                request.setAttribute("SUCCESS", "Delete successful");
            } else {
                request.setAttribute("ERROR", "Delete failed");
            }
        } catch (Exception e) {
            LOGGER.debug("Error at DeleteRequestAction: " + e.getMessage());
        }
        return label;
    }

}
