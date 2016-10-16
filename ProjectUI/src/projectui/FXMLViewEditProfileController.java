/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLViewEditProfileController implements Initializable {

    MySQLController DBCon;
    
    public  FXMLViewEditProfileController (MySQLController pDB){
        DBCon = pDB;
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
        //UPDATE superuser SET Email ='jt@test.net', JoinedDate ='2016-10-14' WHERE UserID = '1';
     
        String fullName = DBCon.getfullName();
        
        String[] parts = fullName.split("\\s+");
        String fName = parts[0];
        String lName = parts[1];
        
        firstNameBox.setText(fName);
        lastNameBox.setText(lName);
        
        emailBox.setText(DBCon.getEmail());

        String accessLevel = Integer.toString(DBCon.getsecLevel());
        currAccessLevel.setText(accessLevel);
        String noReports = Integer.toString(DBCon.getnumReports());
        currNoReports.setText(noReports);
        String repLevel = Integer.toString(DBCon.getrepLevel());
        currRep.setText(repLevel);
        dateJoined.setText(DBCon.getdateJoined());
        
        errorText.setText(" ");
    }    

    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAuthMenu.fxml"));
        FXMLAuthMenuController controller = new FXMLAuthMenuController(DBCon);
        loader.setController(controller);
        Parent menuPage_parent = loader.load();
        Scene menuPage_scene = new Scene(menuPage_parent);
            
            //takes to menu.
        app_stage.hide();
        app_stage.setScene(menuPage_scene);
        app_stage.show();
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        
        if(passwordBox.getText().equals(DBCon.getPassword())){
           
            System.out.println("Entering");
            
            if(newPassword.getText().trim().isEmpty() || newPassword.getText() == null)
            {
                DBCon.updateUser(firstNameBox.getText(),lastNameBox.getText(), passwordBox.getText(), emailBox.getText());
                System.out.println("Update1");
                errorText.setText("Saved");
            }
            else if(!newPassword.getText().trim().isEmpty())
            {
                DBCon.updateUser(firstNameBox.getText(),lastNameBox.getText(), newPassword.getText(), emailBox.getText());
                System.out.println("Update2");
                errorText.setText("Saved");
            }
        }
        else
        {
            errorText.setText("Password Incorrect");
        }
        
    }
    
}
