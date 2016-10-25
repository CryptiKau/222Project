/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.sql.*;

/**
 *
 * @author Nathan
 */
public class MySQLController {

    //CurrentUser currentUser = new CurrentUser();
    
    String driver = "com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/BugTrackerPrime";
    String dbUsername = "newUser";
    String dbPassword = "pass123";
    Statement stmt;
    Connection conn;
    

    public void getConnection(){
        try{
            
            Class.forName(driver);
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            stmt = conn.createStatement();
            
        }catch(Exception e){
            
            System.out.println(e);
            System.exit(0);  
            
        }
    }
    public void closeConnection(){
        
        try{
            
            stmt.close();
            conn.close();
        
        } catch(Exception e){
         
            System.out.println(e);
            System.exit(0);
            
        }
    }
    public int loginDB(String uName, String pWord){
        getConnection();
        int access = 0;
        String sql = "SELECT BugTrackerPrime.verifyLogIn(\'" + uName + "\' , \'" + pWord + "\')";
        try{
            ResultSet seclevel = stmt.executeQuery(sql);
            while(seclevel.next()){
                access = seclevel.getInt(1);
            }
            closeConnection();
            seclevel.close();
        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        System.out.println("access : " + access);
        return access;
        
    }

    public void registerNewUser(NewUser newUser){

        getConnection();
        try{
            String statement = "CALL BugTrackerPrime.insertNewUser(\'" + newUser.getUserName() + "\', \'"  + newUser.getfName() + "\', \'" +
                    newUser.getlName() + "\', \'" +  newUser.getEmail() + "\', \'" + newUser.getPassWord() + "\')";
            //System.out.println(statement);
            stmt.executeQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    
    public void registerNewUserAdmin(NewUser newUser){

        getConnection();
        try{
            String statement = "CALL BugTrackerPrime.insertNewUserAdmin(\'" +newUser.getAccessLevel() + "\', \'"+ newUser.getUserName() + "\', \'"  + newUser.getfName() + "\', \'" +
                    newUser.getlName() + "\', \'" +  newUser.getEmail() + "\', \'" + newUser.getPassWord() + "\')";
            System.out.println(statement);
            stmt.executeQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    

    public ResultSet setCurrentUser(CurrentUser currUser) {
        getConnection();
        ResultSet rs = null;
        try {
            String sql = "CALL BugTrackerPrime.setCurrentUser(\'" + currUser.getUserName() + "\')";
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet setCurrentAdmin(CurrentUser currUser) {
        getConnection();
        ResultSet rs = null;
        try{
            String sql = "CALL BugTrackerPrime.setCurrentAdmin(\'" + currUser.getUserName() + "\')";
            rs = stmt.executeQuery(sql);
        }catch (Exception e){
            e.printStackTrace();
        }

        return rs;
    }

    public void updateAdminNoP(CurrentUser currUser){
        getConnection();
        try{
            String sql = "CALL BugTrackerPrime.updateAdminNoP(\'" + currUser.getUserName() + "\' , \'" +
                    currUser.getfName() + "\' , \'" + currUser.getlName() + "\' , \'" +
                    currUser.getEmail() + "\')";
            stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateAdmin(CurrentUser currUser){
        getConnection();
        try{
            String sql = "CALL BugTrackerPrime.updateAdmin(\'" + currUser.getUserName() + "\' , \'" +
                    currUser.getfName() + "\' , \'" + currUser.getlName() + "\' , \'" +
                    currUser.getEmail() + "\' , \'" + currUser.getNewPass() + "\')";
            stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResultSet searchDetailsByStatus(String search){
        getConnection();
        ResultSet rs = null;
        try{
            String sql = "CALL BugTrackerPrime.setSearchDetailsByStatus(\'" + search + "\')";
            rs = stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet searchDetailsByUser(String search){
        getConnection();
        ResultSet rs = null;
        try{
            String sql = "CALL BugTrackerPrime.setSearchDetailsByUser(\'" + search + "\')";
            rs = stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet searchDetailsByPriority(String search){
        getConnection();
        ResultSet rs = null;
        try{
            String sql = "CALL BugTrackerPrime.setSearchDetailsByPriority(\'" + search + "\')";
            rs = stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet populateExtDetails(int id){
        getConnection();
        ResultSet rs = null;
        try{
            String sql = "CALL BugTrackerPrime.populateExtReportDetails(" + id + ")";
            rs = stmt.executeQuery(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
}
