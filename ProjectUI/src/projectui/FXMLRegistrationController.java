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
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLRegistrationController implements Initializable {

    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newFname;
    @FXML
    private TextField newLname;
    @FXML
    private Label errUserTaken;
    @FXML
    private Label errEmailTaken;
    @FXML
    private Button newRegister;
    @FXML
    private Button backtoLog;

    @FXML
    //function for back button
    private void backAction(ActionEvent event) throws IOException
    {
        
        Parent loginPage_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene loginPage_scene = new Scene(loginPage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(loginPage_scene);
        app_stage.show();
        
    }
    
    
    @FXML
    //function for registering new acc.
    private void registerAcc(ActionEvent event) throws IOException
    {
        /*Completed on Function
        -On Register button execute 
        -Take data and insert into table
        */
        
        /*To be done
        -Textfields need to be validated before entering.
        */
        
        //Gets current date to insert into date joined.
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        
        try{
            
            String driver = "com.mysql.jdbc.Driver";
            
            /////For testing please input own DB details////////////
            String dbURL = "jdbc:mysql://localhost:3306/projectdb";
            String dbUsername = "root";
            String dbPassword = "happy123";
            ///////////////////////////////////////////////////////
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            
            Statement stmt = conn.createStatement();
            
            String fname = newFname.getText();
            String lname = newLname.getText();
            String uname = newUsername.getText();
            String email = newEmail.getText();
            String password = newPassword.getText();
           
            
            PreparedStatement newUser; 
            newUser = conn.prepareStatement("INSERT INTO superuser (SecLevel, UserName, FName, LName, Email, Password, JoinedDate) VALUES (1,'"
                    + uname +"', '"+ fname +"', '" + lname
                    +"', '"+ email +"', '" + password +"', '"+ date + "')");
            
            newUser.executeUpdate();
            
            stmt.close();
            conn.close();
            
            System.out.println("Added");
            
            Parent loginPage_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene loginPage_scene = new Scene(loginPage_parent);
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(loginPage_scene);
            app_stage.show();
            
        }catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
