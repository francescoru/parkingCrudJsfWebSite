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

import javax.faces.bean.ManagedBean;
import javax.servlet.jsp.jstl.sql.Result;

import com.parkingsystems.dao.CarDAO;

@ManagedBean
public class History {
        
    public Result getHistory(){
        
        int userID = Integer.parseInt(com.parkingsystems.util.SessionUtils.getUserId());
        
        return CarDAO.getLogs(userID);
    }
}
