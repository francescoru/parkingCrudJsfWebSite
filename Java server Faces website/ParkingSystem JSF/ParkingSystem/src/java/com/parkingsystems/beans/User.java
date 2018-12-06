/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingsystems.beans;

/**
 *
 * @author Francesco Rubino
 */
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.parkingsystems.dao.LoginDAO;
import com.parkingsystems.util.SessionUtils;

@ManagedBean
@SessionScoped
public class User implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String firstname;
    private String lastname;
    private String emailAdd;
    private String phone;
    private String address;
    private String pwd;
    private String msg;
    private String user;

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String add() {
        boolean valid = LoginDAO.register(firstname, lastname, user, pwd, phone, address, emailAdd);

        if (valid) {
            // HttpSession session = SessionUtils.getSession();
            // session.setAttribute("username", user);
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Registration Successful - You can now login",
                            "You can now proceed to login"));
            return "login";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error registering user",
                            "Ensure database is running and that credentials are correct"));
            return "register";
        }
    }

    //validate login
    public String validateUsernamePassword() {

        int id = LoginDAO.validate(user, pwd);

        if (id > 0) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user);
            session.setAttribute("userid", id);
            session.setAttribute("password", this.pwd);

            return "admin";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect username or password",
                            "Please enter correct username and Password"));
            return "login";
        }

    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "login";
    }

    public String updatePassword(String oldPassword, String newPassword) {

        if (oldPassword.equals(this.getPwd())) {

            int userID = Integer.parseInt(SessionUtils.getUserId());

            boolean valid = LoginDAO.updatePassword(newPassword, userID);

            if (valid) {
               
                return "Password update successful";
            } else {
               
                return "Error updating password";
            }
        } else {
            return "Your current password does not match. Please try again";
        }
    }

}
