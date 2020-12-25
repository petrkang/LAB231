/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.actions;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import khangtl.daos.UserDAO;
import khangtl.models.UserBean;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Peter
 */
public class RegisterAction extends ActionSupport {
    private static final Logger LOGGER = Logger.getLogger(RegisterAction.class);
    private String firstname, lastname, email, password, confirm;
    
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    
    public RegisterAction() {
    }
    
    public String execute() throws Exception {
        String label = SUCCESS;
        try {
            UserBean beans = new UserBean();
            UserDAO dao = new UserDAO();
            String code = dao.generateRandomString();
            beans.setEmail(email);
            beans.setFirstname(firstname);
            beans.setLastname(lastname);
            beans.setPassword(password);
            beans.setCode(code);
            HttpServletRequest request = ServletActionContext.getRequest();
            if (beans.registerAccount()) {
                dao.sendMail("haphongpk12@gmail.com", email, "ResourceSharing - Welcome to our program", "Welcome " + email + ", ", code);
                request.getSession().setAttribute("USER", email);
            } else {
                label = ERROR;
                request.setAttribute("ERROR", "Register failed");
            }
        } catch (Exception e) {
            LOGGER.debug("Error at RegisterAction: " + e.getMessage());
        }
        return label;
    }
    
}
