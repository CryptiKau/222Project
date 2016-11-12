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
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLViewAdminProfileController implements Initializable {

    CurrentUser currentUser;
    CurrentUser searchUser;
    
    public  FXMLViewAdminProfileController (CurrentUser curr){
        currentUser = curr;
    }

    @FXML
    private Button backToMenu;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Text currRep;
    @FXML
    private Text dateJoined;
    @FXML
    private TextField firstNameBox;
    @FXML
    private TextField emailBox;
    @FXML
    private TextField lastNameBox;
    @FXML
    private Text currUsername;
    @FXML
    private PasswordField newPassword;
    @FXML
    private Text errorText;
    @FXML
    private ComboBox<String> setAccesslevel;
    @FXML
    private TextField searchField;
    @FXML
    private Button increaseRepButton;
    @FXML
    private Button decreaseRepbutton;
    @FXML
    private Button deleteProfileButton;
    String currSearchEmail ="";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currUsername.setText("");
        currRep.setText("");
        dateJoined.setText("");
        errorText.setText("");
        
        setAccesslevel.getItems().addAll(
        "Auth User",
        "Reporter",
        "Developer",
        "Triage",
        "Admin"
        );
        setAccesslevel.setValue("Auth User");
        
        increaseRepButton.setDisable(true);
        decreaseRepbutton.setDisable(true);
        
        firstNameBox.setDisable(true);
        emailBox.setDisable(true);
        lastNameBox.setDisable(true);
        currUsername.setDisable(true);
        newPassword.setDisable(true);
        
        
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
        System.out.println("Saving Changes");
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
        MySQLController conn = new MySQLController();
        List<String> Emails = conn.getEmailList();
        int eMsent = 0;
        
        for (int i = 0; i < Emails.size(); i++){
           
           if(Emails.get(i).equals(emailBox.getText())){
               eMsent = 1;
           }
           
        }
        
        if(emailBox.getText().equals(currSearchEmail)){
            eMsent = 0;
        }
        
        
        if(firstNameBox.getText().isEmpty()){
            errorText.setText("First Name empty");
        }
        else if(lastNameBox.getText().isEmpty()){
            errorText.setText("Last Name empty");
        }
        else if(emailBox.getText().isEmpty()){
            errorText.setText("Email is empty");
        }
        else if(eMsent == 1){
            errorText.setText("Email is Already taken");
        }
        else{
            int newRep = 0;
            newRep = Integer.parseInt(currRep.getText());
            if(newPassword.getText().isEmpty()){
                searchUser.updateUserNoP(firstNameBox.getText(), lastNameBox.getText(), emailBox.getText(), newRep, acsLvl);
                errorText.setText("Changes Saved");
                eMsent = 0;
                currSearchEmail = emailBox.getText();
            } else {
                searchUser.updateUser(firstNameBox.getText(), lastNameBox.getText(), emailBox.getText(), newPassword.getText(), newRep, acsLvl);
                errorText.setText("Changes Saved");
                currSearchEmail = emailBox.getText();
                eMsent = 0;
                //errorText
            }
        }
    }

    @FXML
    private void findUser(ActionEvent event) throws SQLException {
        System.out.println("Searching for User");
        //searches user and fills all the fields
        searchUser = new CurrentUser(searchField.getText());
        searchUser.searchUser();
        if(searchUser.getAccessLevel() == 0){
            errorText.setText("User Not Found");
        } else {
            
            firstNameBox.setDisable(false);
            emailBox.setDisable(false);
            lastNameBox.setDisable(false);
            currUsername.setDisable(false);
            newPassword.setDisable(false);
            
            currUsername.setText(searchUser.getUserName());
            firstNameBox.setText(searchUser.getfName());
            lastNameBox.setText(searchUser.getlName());
            emailBox.setText(searchUser.getEmail());
            currSearchEmail = emailBox.getText();
            setAccesslevel.setValue(searchUser.determineAccessLevelStr());
            currRep.setText(String.valueOf(searchUser.getUserRep()));
            dateJoined.setText(searchUser.getJoinDate());
            errorText.setText("");
        }
        increaseRepButton.setDisable(false);
        decreaseRepbutton.setDisable(false);
    }

    @FXML
    private void increaseRep(ActionEvent event) {
        //increases rep
        int count = 0;
        while(count == 0){
            increaseRepButton.setDisable(false);
            System.out.println("INCREASE REP");
            searchUser.incRep();
            currRep.setText(String.valueOf(searchUser.getUserRep()));
            count++;
        }
        increaseRepButton.setDisable(true);
        decreaseRepbutton.setDisable(false);
    }

    @FXML
    private void decreaseRep(ActionEvent event) {
        //decreases rep
        int count = 0;
        while(count == 0){
            decreaseRepbutton.setDisable(false);
            System.out.println("DECREASE REP");
            searchUser.decRep();
            currRep.setText(String.valueOf(searchUser.getUserRep()));
            count++;
        }
        decreaseRepbutton.setDisable(true);
        increaseRepButton.setDisable(false);
    }
    
    @FXML
    private void deleteProfile(ActionEvent event) {
        //delete current profile
        System.out.println("Deleting User");
        searchUser.deleteUser();
        errorText.setText("User Deleted");
        clearScreen();
    }

    private void clearScreen(){
        currUsername.setText("");
        currRep.setText("");
        dateJoined.setText("");
        //errorText.setText("");
        currUsername.setText("");
        firstNameBox.setText("");
        lastNameBox.setText("");
        emailBox.setText("");
        setAccesslevel.setValue("Auth User");
        currRep.setText("");
        dateJoined.setText("");
    }
    
}
