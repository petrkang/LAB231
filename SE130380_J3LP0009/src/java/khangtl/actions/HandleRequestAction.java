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
public class HandleRequestAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(HandleRequestAction.class);
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private String id, searchRequestDate, searchUser, searchResourceName, searchStatus, buttonHandle;
    private int currentPage, recordsPerPage;

    public String getSearchRequestDate() {
        return searchRequestDate;
    }

    public void setSearchRequestDate(String searchRequestDate) {
        this.searchRequestDate = searchRequestDate;
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser;
    }

    public String getSearchResourceName() {
        return searchResourceName;
    }

    public void setSearchResourceName(String searchResourceName) {
        this.searchResourceName = searchResourceName;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getButtonHandle() {
        return buttonHandle;
    }

    public void setButtonHandle(String buttonHandle) {
        this.buttonHandle = buttonHandle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    public HandleRequestAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            RequestBean beans = new RequestBean();
            HttpServletRequest request = ServletActionContext.getRequest();
            beans.setId(id);
            beans.setHandleStatus(buttonHandle);
            beans.setUser(searchUser);
            beans.setResourceName(searchResourceName);
            beans.setRequestDate(searchRequestDate);
            beans.setStatus(searchStatus);
            beans.setCurrentPage(currentPage);
            beans.setRecordsPerPage(recordsPerPage);
            if (beans.handleRequest()) {
                request.setAttribute("SUCCESS", "Handle successful");
            } else {
                request.setAttribute("SUCCESS", "Handle failed");
            }
        } catch (Exception e) {
            LOGGER.debug("Error at HandleRequestAction: " + e.getMessage());
        }

        return label;
    }

}
