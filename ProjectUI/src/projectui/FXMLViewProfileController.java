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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLViewProfileController implements Initializable {
    
    MySQLController DBCon;
    
    public FXMLViewProfileController (MySQLController pDB){
        DBCon = pDB;
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
    private Text currNoReports;
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
        

        currUsername.setText(DBCon.getUsername());
        currEmail.setText(DBCon.getEmail());
        currName.setText(DBCon.getfullName());
        String accessLevel = Integer.toString(DBCon.getsecLevel());
        currAccessLevel.setText(accessLevel);
        String noReports = Integer.toString(DBCon.getnumReports());
        currNoReports.setText(noReports);
        String repLevel = Integer.toString(DBCon.getrepLevel());
        currRep.setText(repLevel);
        dateJoined.setText(DBCon.getdateJoined());
        
    }

    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenu.fxml"));
        FXMLMenuController controller = new FXMLMenuController(DBCon);
        loader.setController(controller);
        Parent menuPage_parent = loader.load();
        Scene menuPage_scene = new Scene(menuPage_parent);
        //takes to menu.
        app_stage.hide();
        app_stage.setScene(menuPage_scene);
        app_stage.show();
    }
    
}
