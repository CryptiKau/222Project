/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

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
import javafx.stage.Stage;
import java.io.IOException;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLMenuController implements Initializable {

    MySQLController DBCon;
    
    public FXMLMenuController (MySQLController pDB){
        DBCon = pDB;
    }
    
    @FXML
    private Button viewProfile;
    @FXML
    private Button searchBugs;
    @FXML
    private Button logoutButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(DBCon.getUsername());
        System.out.println(DBCon.getsecLevel());
    }    

    @FXML
    private void viewProfileAction(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLViewProfile.fxml"));
        FXMLViewProfileController controller = new FXMLViewProfileController(DBCon);
        loader.setController(controller);
        Parent viewProfilePage_parent = loader.load();
        Scene viewProfilePage_scene = new Scene(viewProfilePage_parent);
        //takes to menu.
        app_stage.hide();
        app_stage.setScene(viewProfilePage_scene);
        app_stage.show();

    }

    @FXML
    private void searhBugsAction(ActionEvent event) throws IOException {

        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSearchBugs.fxml"));
        FXMLSearchBugsController controller = new FXMLSearchBugsController(DBCon);
        loader.setController(controller);
        Parent searchBugPage_parent = loader.load();
        Scene searchBugPage_scene = new Scene(searchBugPage_parent);
        //takes to menu.
        app_stage.hide();
        app_stage.setScene(searchBugPage_scene);
        app_stage.show();
        
        
    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        
        Parent loginPage_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene loginPage_scene = new Scene(loginPage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(loginPage_scene);
        app_stage.show();
        
    }
    
}
