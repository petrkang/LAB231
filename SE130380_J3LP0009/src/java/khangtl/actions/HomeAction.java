/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.CategoryDTO;
import khangtl.models.CategoryBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class HomeAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(HomeAction.class);

    private static final String HOME = "home";
    public HomeAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = HOME;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            CategoryBean categoryBeans = new CategoryBean();
            List<CategoryDTO> categories = categoryBeans.getAllCategory();
            request.getSession().setAttribute("categories", categories);
        } catch (Exception e) {
            LOGGER.debug("Error at HomeAction: " + e.getMessage());
        }
        return label;
    }

}
