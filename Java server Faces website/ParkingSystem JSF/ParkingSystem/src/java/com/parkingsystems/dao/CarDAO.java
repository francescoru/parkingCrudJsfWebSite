/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingsystems.dao;

/**
 *
 * @author Francesco Rubino
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.parkingsystems.util.DataConnect;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class CarDAO {
    
    static Connection con = null;
    static PreparedStatement ps = null;
    
    private static void logAction(int userID, String license, String duration, int reg){
        
        String action = reg ==1 ? "added for "+duration+" hours" : "removed";
        
        String message = "Car with license "+license+" was "+action;
        
         try{
             
            con = DataConnect.getConnection();
            
            ps = con.prepareStatement("Insert into History SET userID =?, message =?, registration = ? ");
            ps.setInt(1, userID);
            ps.setString(2, message);
            ps.setInt(3, reg);
            
            int result = ps.executeUpdate();
            
            
            
        } catch (SQLException ex) {
			System.out.println("Log error error -->" + ex.getMessage());
			//return false;
		} finally {
			DataConnect.close(con);
		}
    }
    
        public static Result getLogs(int userID){
        
        Result result = null;
        
        try{
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select message, dateOfOperation From History Where userID =? ORDER BY dateOfOperation DESC");
            ps.setInt(1, userID);
            
            ResultSet rs  = ps.executeQuery();
            
            result= ResultSupport.toResult(rs);
            
            //while(rs.next()){
            //    System.out.println("***********"+rs.getString(2));
            //}
            
            
        } catch (SQLException ex) {
			System.out.println("Car history error -->" + ex.getMessage());
			//return false;
		} finally {
			DataConnect.close(con);
		}
        
        return result;
    }
    
    
    public static Result getRegistrations(int userID){
        
        Result result = null;
        
        try{
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select parkingID, license, duration From CarParking Where userID =? and active =1");
            ps.setInt(1, userID);
            
            ResultSet rs  = ps.executeQuery();
            
            result= ResultSupport.toResult(rs);
            
            //while(rs.next()){
            //    System.out.println("***********"+rs.getString(2));
            //}
            
            
        } catch (SQLException ex) {
			System.out.println("Car register error -->" + ex.getMessage());
			//return false;
		} finally {
			DataConnect.close(con);
		}
        
        return result;
    }
    
    public static boolean register(String license, String duration, int userID){
        

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Insert into CarParking SET license = ?, duration = ?, active =?, userID =? ");
            ps.setString(1,license);
            ps.setString(2, duration);
            ps.setInt(3, 1);
            ps.setInt(4, userID);
            
            int i = ps.executeUpdate();
            
            logAction(userID, license, duration, 1);
            
            if(i >0)
                return true;
            
        } catch (SQLException ex) {
			System.out.println("Car register error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
        
        return false;
    }

    public static boolean unregister(int parkingID, String license, String duration, int userID) {
        
         try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Update CarParking SET active = 0 where parkingID =? ");
            ps.setInt(1,parkingID);
            
            int i = ps.executeUpdate();
            
            logAction(userID, license, duration, 2);
            
            if(i >0)
                return true;
            
        } catch (SQLException ex) {
			System.out.println("Car unregister error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
        
        return false;
    }
    
        public static Result searchCars(int userID, String searchString) {

        Result result = null;

        System.out.println("************* Searching " + searchString);

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select * From carparking Where userID =? AND active =1 AND license LIKE '%" + searchString + "%'");
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();
            
            
            result = ResultSupport.toResult(rs);

        } catch (SQLException ex) {
            System.out.println("Continent retrieval error" + ex.getMessage());
            //return false;
        } finally {
            DataConnect.close(con);
        }

        return result;
    }

}
