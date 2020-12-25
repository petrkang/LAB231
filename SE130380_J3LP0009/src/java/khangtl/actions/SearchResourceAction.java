/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.ResourceDTO;
import khangtl.models.ResourceBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class SearchResourceAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(SearchResourceAction.class);
    private static final String SEARCH = "search";

    private String name, category, usingDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(String usingDate) {
        this.usingDate = usingDate;
    }

    public SearchResourceAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = SEARCH;
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
            ResourceBean beans = new ResourceBean();
            beans.setName(name);
            beans.setCategory(category);
            if (!usingDate.isEmpty()) {
                String tmpUsingDate = usingDate.substring(0, usingDate.lastIndexOf("T"));
                beans.setUsingDate(tmpUsingDate);
            } else {
                beans.setUsingDate(usingDate);
            }
            int rows = beans.getNumberOfRows();
            int noOfPages = rows / recordsPerPage;
            noOfPages++;
            beans.setCurrentPage(currentPage);
            beans.setRecordsPerPage(recordsPerPage);
            List<ResourceDTO> result = beans.searchResourceWithCondition();
            request.setAttribute("NOW", new Date());
            request.setAttribute("resourceList", result);
            request.setAttribute("name", name);
            request.setAttribute("category", category);
            request.setAttribute("usingDate", usingDate);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("recordsPerPage", recordsPerPage);
        } catch (Exception e) {
            LOGGER.debug("Error at SearchResourceAction: " + e.getMessage());
        }
        return label;
    }

}
