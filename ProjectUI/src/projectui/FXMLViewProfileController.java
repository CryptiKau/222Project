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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLViewProfileController implements Initializable {
    
    CurrentUser currentUser;
    
    public FXMLViewProfileController (CurrentUser curr){
        currentUser = curr;
    }
    
    
    @FXML
    private Text currUsername;
    @FXML
    private Text currEmail;
    @FXML
    private Text currName;
    @FXML
    private Text currAccessLevel;
    @FXML
    private Text currRep;
    @FXML
    private Text dateJoined;
    @FXML
    private Button backToMenu;
    
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         // TODO
         // Add count rep and reports to populate details function
        populateDetails();
        
        
    }

    private void populateDetails(){
        System.out.println("Populate Details");
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
        currName.setText(currentUser.getfName() + " " + currentUser.getlName());
        currEmail.setText(currentUser.getEmail());
        currRep.setText(String.valueOf(currentUser.getUserRep()));
        dateJoined.setText(currentUser.getJoinDate());
        currAccessLevel.setText(currentRole);
        
    }

    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenu.fxml"));
        FXMLMenuController controller = new FXMLMenuController(currentUser);
        loader.setController(controller);
        Parent menuPage_parent = loader.load();
        Scene menuPage_scene = new Scene(menuPage_parent);
        //takes to menu.
        app_stage.hide();
        app_stage.setScene(menuPage_scene);
        app_stage.show();
    }
}
