/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingsystems.beans;


import com.parkingsystems.dao.CarDAO;
import com.parkingsystems.dao.LoginDAO;
import com.parkingsystems.util.SessionUtils;
import java.io.Serializable;
import javax.faces.application.FacesMessage;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Francesco Rubino
 */

@ManagedBean
@SessionScoped
public class Car implements Serializable{
    
    private static final long serialVersionUID = 1094801825228386363L;

    private String plate;

    private String duration;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public String withdrawParking(int parkingID, String license, String duration){
        
        int userID = Integer.parseInt(com.parkingsystems.util.SessionUtils.getUserId());
        
        boolean valid = CarDAO.unregister(parkingID, license, duration, userID);
        
        if (valid) {
                   // HttpSession session = SessionUtils.getSession();
                   // session.setAttribute("username", user);
                   FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                    "Unregistration Successful",
                                                    "Unregistation successful"));
                    return "admin";
            } else {
                    FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Error removing parking",
                                                    "Please try again later"));
                    return "admin";
            }
    }
    
    public String addParking(){
    
        int userID = Integer.parseInt(com.parkingsystems.util.SessionUtils.getUserId());
        
        boolean valid = CarDAO.register(plate, duration, userID);
        
          if (valid) {
                   // HttpSession session = SessionUtils.getSession();
                   // session.setAttribute("username", user);
                   FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                                    "Registration Successful",
                                                    "Registation successful"));
                    return "admin";
            } else {
                    FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Error registering parking",
                                                    "Please try again later"));
                    return "admin";
            }
    }
    
}
