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

public class LoginDAO {
    
    static Connection con = null;
    static PreparedStatement ps = null;
    
     public static boolean updatePassword(String password, int userID){
         
         System.out.println("******* "+userID +" password is "+password);
         try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Update Users SET password = ? where id=? ");
            ps.setString(1,password);
            ps.setInt(2, userID);
            
            
            int i = ps.executeUpdate();
            
            if(i >0)
                return true;
            
        } catch (SQLException ex) {
			System.out.println("Error changing password" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
        
        return false;
    }
     
    public static boolean register(String firstName, String lastName, String userName, String password,
                                String phone, String address, String email){
        

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Insert into Users SET uname = ?, password = ?, firstName =?, lastName=? , phone=?, address=?, email=? ");
            ps.setString(1,userName);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, email);
            
            int i = ps.executeUpdate();
            
            if(i >0)
                return true;
            
        } catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return false;
		} finally {
			DataConnect.close(con);
		}
        
        return false;
    }
    
    public static int validate(String user, String password) {
		

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("Select id, uname, password from Users where uname = ? and password = ?");
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				//result found, means valid inputs
                                int id = rs.getInt(1);
                                
                                
				return id;
			}
		} catch (SQLException ex) {
			System.out.println("Login error -->" + ex.getMessage());
			return 0;
		} finally {
			DataConnect.close(con);
		}
		return 0;
	}
}
