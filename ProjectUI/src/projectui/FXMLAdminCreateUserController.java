/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLAdminCreateUserController implements Initializable {

    CurrentUser currentUser;
    
    public FXMLAdminCreateUserController (CurrentUser curr){
        currentUser = curr;
    }
    
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newFname;
    @FXML
    private TextField newLname;
    @FXML
    private Button newRegister;
    @FXML
    private Button backtoLog;
    @FXML
    private PasswordField newPassword;
    @FXML
    private ComboBox<String> setAccesslevel;
    @FXML
    private Text inputError;
    @FXML
    private Text createdText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputError.setText("");    
        createdText.setVisible(false);
        
        setAccesslevel.getItems().addAll(
        "Auth User",
        "Reporter",
        "Developer",
        "Triage",
        "Admin"
        );
        setAccesslevel.setValue("Auth User");
        
        
    }    

    @FXML
    private void registerAcc(ActionEvent event) {
        MySQLController conn = new MySQLController();
        List<String> Users = conn.getUserList();
        List<String> Emails = conn.getEmailList();
         //dont forget already exists checks on username and email
        int eMsent = 0;
        int uNsent = 0;
        int acsLvl;
        switch(setAccesslevel.getValue()){
            case "Auth User":
                acsLvl = 1;
                break;
            case "Reporter":
                acsLvl = 2;
                break;
            case "Developer":
                acsLvl = 3;
                break;
            case "Triage":
                acsLvl = 4;
                break;
            case "Admin":
                acsLvl = 5;
                break;
            default:
                acsLvl = 1;
                break;
  
        }
       
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
        
        if(newUsername.getText().trim().isEmpty() || 
                    newFname.getText().trim().isEmpty() || 
                    newLname.getText().trim().isEmpty() || 
                    newEmail.getText().trim().isEmpty() || 
                    newPassword.getText().trim().isEmpty() )
        {
            inputError.setText("ERROR: Input fields are Empty");
        }else if(uNsent == 0 && eMsent == 0)
        {
            try{
                //TO DO: CHECK THE USER INPUT
                NewUser newU = new NewUser();
                newU.setNewUserAdmin(newUsername.getText(), newFname.getText(),newLname.getText(),newEmail.getText(), newPassword.getText(), acsLvl);
            
            }catch(Exception e){
                System.out.println(e);
                inputError.setText("Incorrect Inputs");  
            }
            inputError.setText("");
            createdText.setVisible(true);
            newUsername.setText(""); 
            newFname.setText("");
            newLname.setText("");
            newEmail.setText("");
            newPassword.setText("");
            setAccesslevel.setValue("Auth User");
        }
        else if(eMsent == 1 && uNsent == 1){
            inputError.setText("Email and User already taken");
        }
        else if(uNsent == 1 && eMsent == 0){
            inputError.setText("Username already taken");
        }
        else if(eMsent == 1 && uNsent == 0){
            inputError.setText("Email already taken");
        }
        eMsent = 0;
        uNsent = 0;
        
    }

    @FXML
    private void backAction(ActionEvent event) throws IOException {
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAuthMenu.fxml"));
        FXMLAuthMenuController controller = new FXMLAuthMenuController(currentUser);
        loader.setController(controller);
        Parent menuPage_parent = loader.load();
        Scene menuPage_scene = new Scene(menuPage_parent);
        app_stage.hide();
        app_stage.setScene(menuPage_scene);
        app_stage.show();
    }
    
}
