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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLViewEditProfileController implements Initializable {

    CurrentUser currentUser;
    
    public  FXMLViewEditProfileController (CurrentUser curr){
        currentUser = curr;
    }
    
    
    @FXML
    private Button backToMenu;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Text currAccessLevel;
    @FXML
    private Text currRep;
    @FXML
    private Text currNoReports;
    @FXML
    private Text dateJoined;
    @FXML
    private TextField firstNameBox;
    @FXML
    private TextField emailBox;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private TextField lastNameBox;
    @FXML
    private Text currUsername;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Text errorText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String currentRole = "";
        switch(currentUser.getAccessLevel()){
            case 1:
                currentRole = "AuthUser";
                break;
            case 2:
                currentRole = "Reporter";
                break;
            case 3:
                currentRole = "Developer";
                break;
            case 4:
                currentRole = "Triage";
                break;
            case 5:
                currentRole = "Admin";
                break;
        }
        
        
        currUsername.setText(currentUser.getUserName());
        firstNameBox.setText(currentUser.getfName());
        lastNameBox.setText(currentUser.getlName());
        emailBox.setText(currentUser.getEmail());
        currAccessLevel.setText(currentRole);
        currRep.setText(String.valueOf(currentUser.getUserRep()));
        dateJoined.setText(currentUser.getJoinDate());
        errorText.setText("");
    }    

    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAuthMenu.fxml"));
        FXMLAuthMenuController controller = new FXMLAuthMenuController(currentUser);
        loader.setController(controller);
        Parent menuPage_parent = loader.load();
        Scene menuPage_scene = new Scene(menuPage_parent);
            
        //takes to menu.
        app_stage.hide();
        app_stage.setScene(menuPage_scene);
        app_stage.show();
    }

    @FXML
    private void saveChanges(ActionEvent event) throws SQLException {
        
        //TO DO: VERIFY USER INPUT
         MySQLController conn = new MySQLController();
        List<String> Emails = conn.getEmailList();
        int eMsent = 0;
        
        for (int i = 0; i < Emails.size(); i++){
           
           if(Emails.get(i).equals(emailBox.getText())){
               eMsent = 1;
           }
           
        }
        if(currentUser.getEmail().equals(emailBox.getText())){
            eMsent = 0;
        }
        
        if(passwordBox.getText().isEmpty()){
            System.out.println("new password empty");
            errorText.setText("Please Enter your password to make changes");
        }else if(eMsent == 1){      
            errorText.setText("Email Already Taken");
        }
        else if(newPassword.getText().isEmpty() && !passwordBox.getText().isEmpty() && passwordBox.getText().equals(currentUser.getCurrPass())){
            errorText.setText("");
            System.out.println("Update no new password");
            //update everything but password
            try{
                currentUser.updateAdminNoP(firstNameBox.getText(), lastNameBox.getText(), emailBox.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            errorText.setText("Changes Saved");

        }else if(!newPassword.getText().isEmpty() && !passwordBox.getText().isEmpty() && passwordBox.getText().equals(currentUser.getCurrPass())){
            //update everything including password
            errorText.setText("");
            System.out.println("Update new password");
            try{
                currentUser.updateAdmin(firstNameBox.getText(), lastNameBox.getText(), emailBox.getText(), newPassword.getText());
                currentUser.setNewPass();
            } catch (Exception e){
                e.printStackTrace();
            }
            errorText.setText("Changes Saved");
        }
        else if(!passwordBox.getText().equals(currentUser.getCurrPass())){
            
            errorText.setText("Incorrect Password Changes not saved");
        }
        eMsent = 0;

//        if(passwordBox.getText().equals(DBCon.getPassword())){
//
//            System.out.println("Entering");
//
//            if(newPassword.getText().trim().isEmpty() || newPassword.getText() == null)
//            {
//                DBCon.updateUser(firstNameBox.getText(),lastNameBox.getText(), passwordBox.getText(), emailBox.getText());
//                System.out.println("Update1");
//                errorText.setText("Saved");
//            }
//            else if(!newPassword.getText().trim().isEmpty())
//            {
//                DBCon.updateUser(firstNameBox.getText(),lastNameBox.getText(), newPassword.getText(), emailBox.getText());
//                System.out.println("Update2");
//                errorText.setText("Saved");
//            }
//        }
//        else
//        {
//            errorText.setText("Password Incorrect");
//        }

    }
    
}
