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
public class SearchHistoryRequestAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(SearchHistoryRequestAction.class);

    private static final String SUCCESS = "success";
    private String searchRequestDate;

    public String getSearchRequestDate() {
        return searchRequestDate;
    }

    public void setSearchRequestDate(String searchRequestDate) {
        this.searchRequestDate = searchRequestDate;
    }

    public SearchHistoryRequestAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();

            RequestBean beans = new RequestBean();
            if (!searchRequestDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                Date tmpRequestDate = sdf.parse(searchRequestDate);
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                beans.setRequestDate(sdf.format(tmpRequestDate));
            } else {
                beans.setRequestDate(searchRequestDate);
            }
            beans.setUser((String) request.getSession().getAttribute("USER"));
            List<RequestDTO> result = beans.searchHistoryRequestWithCondition();
            request.setAttribute("requestList", result);
        } catch (Exception e) {
            LOGGER.debug("Error at SearchHistoryRequestAction: " + e.getMessage());
        }

        return label;
    }

}
