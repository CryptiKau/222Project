/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
        
         //This will need to be changed as a parameter recieved from prev window
        String uName = "Gaz";
        //////////////////////
        //////////Also needed to be added select statements for rep and noReports////////////
        String fname;
        String lname;
        String fullName;

        
        try{
            
        
            String driver = "com.mysql.jdbc.Driver";
            String dbURL = "jdbc:mysql://localhost:3306/projectdb";
            String dbUsername = "root";
            String dbPassword = "happy123";
            Class.forName(driver);
            
            Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM superuser WHERE Username = " + "'" + uName + "'");
            
            if(rs.next()){
                //Sets each textbox to pulled variables.
                //Probably need to add date 
                fname = rs.getString("FName");
                lname = rs.getString("LName");
                
                fullName = fname + " " + lname;
                
                currUsername.setText(uName);
                currEmail.setText(rs.getString("Email"));
                currName.setText(fname);
                currAccessLevel.setText(rs.getString("SecLevel"));
            }
            
         
            stmt.close();
            conn.close();
            rs.close();
            
            
            
            
        } catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        
    }

    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {
        
        //make sure security level is still set correctly
        Parent menuPage_parent = FXMLLoader.load(getClass().getResource("FXMLMenu.fxml"));
        Scene menuPage_scene = new Scene(menuPage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide();
        app_stage.setScene(menuPage_scene);
        app_stage.show();
    }
    
}
