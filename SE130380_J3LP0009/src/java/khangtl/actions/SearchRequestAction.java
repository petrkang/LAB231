/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.RequestDTO;
import khangtl.models.RequestBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class SearchRequestAction extends ActionSupport {
    
    private static final Logger LOGGER = Logger.getLogger(SearchRequestAction.class);
    
    private static final String SUCCESS = "success";
    
    private String searchUser, searchResourceName, searchStatus, searchRequestDate;
    
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
    
    public SearchRequestAction() {
    }
    
    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            int currentPage, recordsPerPage;
            if (request.getParameter("currentPage") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
            if (request.getParameter("recordsPerpage") == null) {
                recordsPerPage = 5;
            } else {
                recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
            }
            RequestBean beans = new RequestBean();
            beans.setResourceName(searchResourceName);
            beans.setStatus(searchStatus);
            beans.setUser(searchUser);
            if (!searchRequestDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                Date tmpRequestDate = sdf.parse(searchRequestDate);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                beans.setRequestDate(sdf.format(tmpRequestDate));
            } else {
                beans.setRequestDate(searchRequestDate);
            }
            int rows = beans.getNumberOfRows();
            int noOfPages = rows / recordsPerPage;
            noOfPages++;
            beans.setCurrentPage(currentPage);
            beans.setRecordsPerPage(recordsPerPage);
            List<RequestDTO> result = beans.searchRequestWithCondition();
            request.setAttribute("requestList", result);
            request.setAttribute("searchResourceName", searchResourceName);
            request.setAttribute("searchRequestDate", searchRequestDate);
            request.setAttribute("searchUser", searchUser);
            request.setAttribute("searchStatus", searchStatus);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("recordsPerPage", recordsPerPage);
        } catch (Exception e) {
            LOGGER.debug("Error at SearchRequestAction: " + e.getMessage());
        }
        return label;
    }
    
}
