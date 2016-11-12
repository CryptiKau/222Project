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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLPatchController implements Initializable {
    
    CurrentUser currentUser;
    int brID;
    
    public FXMLPatchController (CurrentUser curr, int bReportID){
        currentUser = curr;
        brID = bReportID;
    }
    
    @FXML
    private Hyperlink patchLink;
    @FXML
    private Button uploadButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField patchLocal;
    @FXML
    private Button backButton;
    @FXML
    private Text patchUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MySQLController conn = new MySQLController();
        String patchPath = conn.getPatch(brID);
        
        patchLink.setText(patchPath);
        patchLocal.setText(patchPath);
        patchUser.setText(conn.getPatchUser(brID));
    }    

    @FXML
    private void uploadPatch(ActionEvent event) {
        patchLocal.setVisible(true);
        
    }

    @FXML
    private void savePatch(ActionEvent event) {
        MySQLController conn = new MySQLController();
        conn.setPatch(patchLocal.getText(), currentUser.getUserID(), brID);
        patchLink.setText(patchLocal.getText());
        patchUser.setText(conn.getPatchUser(brID));
        patchLocal.setVisible(false);
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.close();
    }
    
}
