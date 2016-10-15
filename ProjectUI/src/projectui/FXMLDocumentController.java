/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Nathan
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Label loginFail;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private TextField usernameField;
    
    @FXML 
    private TextField passwordField;
    
    
    //login screen start on completion will take to menu
    @FXML
    private void loginAction(ActionEvent event) throws IOException {
        Parent menuPage_parent = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        Scene menuPage_scene = new Scene(menuPage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        if(isValidCredentials())
        {
            //takes to menu.
            app_stage.hide();
            app_stage.setScene(menuPage_scene);
            app_stage.show();
        }
        else
        {
            //error incorrect details.
            usernameField.clear();
            passwordField.clear();
            loginFail.setText("Incorrect Login");
        }
        
           
    }
    
    //takes you to registration
    @FXML
    private void registerAction(ActionEvent event) throws IOException
    {
        Parent registerPage_parent = FXMLLoader.load(getClass().getResource("FXMLRegistration.fxml"));
        Scene registerPage_scene = new Scene(registerPage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(registerPage_scene);
        app_stage.show();
        
    }
    
    
    // Opens database checks username against passwords
    private boolean isValidCredentials()
    {
        boolean pass = false;
        System.out.println( "SELECT * FROM Users WHERE USERNAME= " + "'" + usernameField.getText() + "'" 
                + " AND PASSWORD= " + "'" + passwordField.getText() + "'");
        
        try{
            
        
            String driver = "com.mysql.jdbc.Driver";
            String dbURL = "jdbc:mysql://localhost:3306/projectdb";
            String dbUsername = "root";
            String dbPassword = "happy123";
            Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            
            Statement stmt = conn.createStatement();
            
            String SQLAccessor = "SELECT * FROM superuser WHERE Username= " + "'" + usernameField.getText() + "'" 
                + " AND Password= " + "'" + passwordField.getText() + "'";

            ResultSet rs = stmt.executeQuery(SQLAccessor);
            
            while(rs.next()){
                if(rs.getString("USERNAME") != null && rs.getString("PASSWORD") != null){
                    String username = rs.getString("USERNAME");
                    System.out.println("USERNAME = " + username);
                    String password = rs.getString("PASSWORD");
                    System.out.println("PASSWORD = " + password);
                    pass = true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
            
            
        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        return pass;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
