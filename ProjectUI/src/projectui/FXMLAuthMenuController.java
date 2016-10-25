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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLAuthMenuController implements Initializable {

    
    CurrentUser currentUser;
    
    public FXMLAuthMenuController (CurrentUser curr){
        currentUser = curr;
    }
    
    @FXML
    private Button vieweditProfile;
    @FXML
    private Button searchcommentBugs;
    @FXML
    private Button logoutButton;
    @FXML
    private Button generateReports;
    @FXML
    private Button viewadminProfile;
    @FXML
    private Text viewadminText;
    @FXML
    private Text createUserText;
    @FXML
    private Button createnewUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(currentUser.getSecLevel());
        switch(currentUser.getSecLevel()){
            case 1:
            case 2:
            case 3:
                viewadminProfile.setVisible(false);
                viewadminText.setText("");
                createnewUser.setVisible(false);
                createUserText.setText("");
                System.out.println(currentUser.getSecLevel());
                break;
            case 5:
                viewadminProfile.setVisible(true);
                createnewUser.setVisible(true);
                break;
            default:
                viewadminProfile.setVisible(false);
                viewadminText.setText("");
                createnewUser.setVisible(false);
                createUserText.setText("");
                break;
                
        }
    }    

    @FXML
    private void vieweditProfileAction(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLViewEditProfile.fxml"));
        FXMLViewEditProfileController controller = new FXMLViewEditProfileController(currentUser);
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
        
    }

    @FXML
    private void searhcommentBugsAction(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLBugs.fxml"));
        FXMLBugsController controller = new FXMLBugsController(currentUser);
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }

    @FXML
    private void logoutAction(ActionEvent event)throws IOException {
        
        Parent loginPage_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene loginPage_scene = new Scene(loginPage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(loginPage_scene);
        app_stage.show();
    }

    @FXML
    private void generateReportAction(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGenerateReport.fxml"));
        FXMLGenerateReportController controller = new FXMLGenerateReportController(currentUser);
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }
    
    @FXML
    private void viewadminProfileAction(ActionEvent event) throws IOException {
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLViewAdminProfile.fxml"));
        FXMLViewAdminProfileController controller = new FXMLViewAdminProfileController(currentUser);
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
        
    }
    
    @FXML void newUserAction(ActionEvent event) throws IOException {
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdminCreateUser.fxml"));
        FXMLAdminCreateUserController controller = new FXMLAdminCreateUserController(currentUser);
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        app_stage.hide();
        app_stage.setScene(scene);
        app_stage.show();
    }
}
