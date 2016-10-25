/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        try{
            //TO DO: CHECK THE USER INPUT
            NewUser newU = new NewUser();
            newU.setNewUser(newUsername.getText(), newFname.getText(),newLname.getText(),newEmail.getText(), newPassword.getText());
            
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
