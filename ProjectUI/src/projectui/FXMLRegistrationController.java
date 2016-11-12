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
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

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
    private Text errorText;
    @FXML
    private Text createdText;
    
    InputValidation valid;

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
        /*if(newUsername.getText().trim().isEmpty() || 
                    newFname.getText().trim().isEmpty() || 
                    newLname.getText().trim().isEmpty() || 
                    newEmail.getText().trim().isEmpty() || 
                    newPassword.getText().trim().isEmpty() )
        {
            errorText.setText("ERROR: Input fields are Empty");
        }
        else if(valid.isUserName(newUsername.getText()) && 
                    valid.isFname(newFname.getText()) && 
                    valid.isLname(newLname.getText())&& 
                    valid.isEmail(newEmail.getText())&& 
                    valid.isPword(newPassword.getText()))
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
        else
        {
            errorText.setText("ERROR: Check input details");
        }*/
        
        MySQLController conn = new MySQLController();
        List<String> Users = conn.getUserList();
        List<String> Emails = conn.getEmailList();
        int eMsent = 0;
        int uNsent = 0;
        
        
        for (int i = 0; i < Users.size(); i++){
           
            if(Users.get(i).equals(newUsername.getText())){
               uNsent = 1;
            }
           
        }
        
        for (int i = 0; i < Emails.size(); i++){
           
           if(Emails.get(i).equals(newEmail.getText())){
               eMsent = 1;
           }
           
        }
        if(uNsent == 0 && eMsent == 0)
        {
            try{
                //TO DO: CHECK THE USER INPUT
                NewUser newU = new NewUser();
                
                newU.setNewUser(newUsername.getText(), newFname.getText(),newLname.getText(),newEmail.getText(), newPassword.getText());
            
            }catch(Exception e){
                System.out.println(e);
                errorText.setText("Incorrect Inputs");  
            }
            errorText.setText("");
            createdText.setVisible(true);
            newUsername.setText(""); 
            newFname.setText("");
            newLname.setText("");
            newEmail.setText("");
            newPassword.setText("");
        }
        else if(eMsent == 1 && uNsent == 1){
            errorText.setText("Email and User already taken");
        }
        else if(uNsent == 1 && eMsent == 0){
            errorText.setText("Username already taken");
        }
        else if(eMsent == 1 && uNsent == 0){
            errorText.setText("Email already taken");
        }
        eMsent = 0;
        uNsent = 0;
        
        
        
   
            
       
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createdText.setVisible(false);
    }    
    
}
