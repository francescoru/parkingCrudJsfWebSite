/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingsystems.beans;

import com.parkingsystems.dao.CarDAO;
import com.parkingsystems.util.SessionUtils;

import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.jstl.sql.Result;



@SessionScoped
@ManagedBean
public class Search {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   
    public Result getParkings() {

        int userID = Integer.parseInt(SessionUtils.getUserId());
        
        System.out.println("*********** "+name);

        return CarDAO.searchCars(userID, name);
    }

    
}
