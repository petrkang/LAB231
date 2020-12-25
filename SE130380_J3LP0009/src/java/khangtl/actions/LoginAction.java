/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import khangtl.dtos.UserDTO;
import khangtl.models.UserBean;
import khangtl.utils.VerifyUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class LoginAction extends ActionSupport {

    private static final Logger LOGGER = Logger.getLogger(LoginAction.class);
    private static final String HOME = "home";
    private static final String ERROR = "error";
    private static final String VERIFY = "verify";
    private String email, password;

    public LoginAction() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String execute() {
        String label = ERROR;
        String err = null;
        boolean valid;
        try {
            UserBean userBeans = new UserBean();
            userBeans.setEmail(email);
            userBeans.setPassword(password);
                HttpServletRequest request = ServletActionContext.getRequest();
            if (userBeans.checkActiveAccount()) {
                UserDTO dto = userBeans.checkLogin();
                if (dto != null) {
                    String recaptcha = request.getParameter("g-recaptcha-response");
                    valid = VerifyUtils.verify(recaptcha);
                    if (!valid) {
                        err = "Captcha invalid";
                    }
                } else {
                    valid = false;
                    err = "Invalid username or password";
                }
                if (!valid) {
                    request.setAttribute("ERROR", err);
                } else {
                    label = HOME;
                    request.getSession().setAttribute("USER", dto.getEmail());
                    request.getSession().setAttribute("FULLNAME", dto.getFirstname() + " " + dto.getLastname());
                    request.getSession().setAttribute("ROLE", dto.getRole());
                    request.getSession().setAttribute("STATUS", dto.getStatus());
                }
            } else {
                label = VERIFY;
                request.setAttribute("ERROR", "Your account still hasn't been verified");
                request.getSession().setAttribute("USER", email);
            }
        } catch (Exception e) {
            LOGGER.debug("Error at LoginAction: " + e.getMessage());
        }
        return label;
    }

}
