/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Nathan
 */
public class MySQLController {
    
    private class currUser {
        
        String username;
        String fullName;
        String email;
        String dateJoined;
        int secLevel = -1;
        int repLevel;
        int numReports;
        
        
        private void setFullName(String FName, String LName){
            fullName = FName + " " + LName;
        }
        private void setEmail(String newEmail){
            email = newEmail;
        }
        private void setUsername(String uName){
            username = uName;
        }
        private void setdateJoined(String newDate){
            dateJoined = newDate;
        }
        private void setSecLevel(int newLevel){
            secLevel = newLevel;
        }
        private void setrepLevel(int newRep){
            repLevel = newRep;
        }
        private void setnumReports(int nReports){
            numReports = nReports;
        }
        
        
        public String getEmail(){
            return email;
        }
        public String getfullName(){
            return fullName;
        }
        public String getdateJoined(){
            return dateJoined;
        }
        public String getUsername(){
            return username;
        }
        public int getsecLevel(){
            return secLevel;
        }
        public int getrepLevel(){
            return repLevel;
        }
        public int getnumReports(){
            return numReports;
        }
    }
    
    currUser currentUser = new currUser();
    
    String driver = "com.mysql.jdbc.Driver";
    String dbURL = "jdbc:mysql://localhost:3306/projectdb";
    String dbUsername = "root";
    String dbPassword = "happy123";
    Statement stmt;
    Connection conn;
    
    public int getsecLevel(){
        return currentUser.getsecLevel();
    }
    public int getrepLevel(){
            return currentUser.getrepLevel();
        }
    public int getnumReports(){
            return currentUser.getnumReports();
        }
    
    public String getUsername(){
        return currentUser.getUsername();
    }
    public String getEmail(){
            return currentUser.getEmail();
        }
    public String getfullName(){
            return currentUser.getfullName();
        }
    public String getdateJoined(){
            return currentUser.getdateJoined();
        }

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
       
        
        try{
            
            getConnection();
            String SQLAccessor = "SELECT * FROM superuser WHERE Username= " + "'" + uName + "'" 
                + " AND Password= " + "'" + pWord + "'";

            ResultSet rs = stmt.executeQuery(SQLAccessor);
            
            while(rs.next()){
                if(rs.getString("USERNAME") != null && rs.getString("PASSWORD") != null){
                    currentUser.setUsername(rs.getString("USERNAME"));
                    String password = rs.getString("PASSWORD");
                    currentUser.setSecLevel(rs.getInt("SecLevel"));
                    currentUser.setFullName(rs.getString("FName"),rs.getString("LName"));
                    currentUser.setEmail(rs.getString("Email"));
                    currentUser.setnumReports(0);
                    currentUser.setrepLevel(0);
                    currentUser.setdateJoined(rs.getString("JoinedDate"));
                    
                    
                }
            }
            closeConnection();
            rs.close();
            
            
            
        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        return getsecLevel();
        
    }
    public void updateUser(String fName, String lName, String pWord, String Email){
        
        currentUser.setEmail(Email);
        currentUser.setFullName(fName, lName);
        getConnection();
        
        try{
            PreparedStatement ps = conn.prepareStatement("UPDATE superuser SET Email = ?, FName = ?, LName = ?, Password = ? WHERE Username = ?");
            
            ps.setString(1, Email);
            ps.setString(2, fName);
            ps.setString(3, lName);
            ps.setString(4, pWord);
            ps.setString(5, currentUser.getUsername());
            ps.executeUpdate();
            ps.close();

            closeConnection();

            
        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        
    }
    public String getPassword(){
        
        getConnection();
        String password = "";
        
        try{
            
            
            String SQLAccessor = "SELECT * FROM superuser WHERE Username = " + "'" + currentUser.getUsername() + "'";

            ResultSet rs = stmt.executeQuery(SQLAccessor);
            while(rs.next()){
                password = rs.getString("PASSWORD");
            }
            
            closeConnection();
            rs.close();

        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        return password;
        
    }
    
    
}
