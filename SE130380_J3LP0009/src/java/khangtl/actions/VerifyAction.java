/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import khangtl.models.UserBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class VerifyAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(VerifyAction.class);
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public VerifyAction() {
    }

    @Override
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            UserBean beans = new UserBean();
            HttpServletRequest request = ServletActionContext.getRequest();
            beans.setEmail((String) request.getSession().getAttribute("USER"));
            beans.setCode(code);
            if (beans.checkVerifyCode()) {
                if (beans.changeAccountStatus()) {
                    request.setAttribute("SUCCESS", "Verify successful, please login");
                } else {
                    label = ERROR;
                    request.setAttribute("ERROR", "Verify failed");
                }
            } else {
                label = ERROR;
                request.setAttribute("ERROR", "Please input code we sended to your email");
            }
        } catch (Exception e) {
            LOGGER.debug("Error at VerifyAction: " + e.getMessage());
        }
        return label;
    }

}
