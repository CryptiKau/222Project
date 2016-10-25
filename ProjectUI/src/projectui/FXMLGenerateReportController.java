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
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLGenerateReportController implements Initializable {
    
    CurrentUser currentUser;
    
    public FXMLGenerateReportController (CurrentUser curr){
        currentUser = curr;
    }
    ///Weekly///
    @FXML
    private Tab weeklyTab;
    @FXML
    private Text reportDate;
    @FXML
    private Text newReportCountText;
    @FXML
    private Text newReportAssText;
    @FXML
    private Text newReportunAssText;
    @FXML
    private Text reportedBugsText;
    @FXML
    private Text progresBugsText;
    @FXML
    private Text solvedBugsTest;
    @FXML
    private Text lowPrioText;
    @FXML
    private Text mediumPrioText;
    @FXML
    private Text highPrioText;
    @FXML
    private Text emergPrioText;
    @FXML
    private Text cosSevText;
    @FXML
    private Text minorSevText;
    @FXML
    private Text majorSevText;
    @FXML
    private Text critSevText;
    ///Monthly///
    @FXML
    private Tab monthlyTab;
    @FXML
    private Text reportDate1;
    @FXML
    private Text mnewReportCountText;
    @FXML
    private Text mnewReportAssText;
    @FXML
    private Text mnewReportunAssText;
    @FXML
    private Text mreportedBugsText;
    @FXML
    private Text mprogresBugsText;
    @FXML
    private Text msolvedBugsTest;
    @FXML
    private Text mlowPrioText;
    @FXML
    private Text mmediumPrioText;
    @FXML
    private Text mhighPrioText;
    @FXML
    private Text memergPrioText;
    @FXML
    private Text mcosSevText;
    @FXML
    private Text mminorSevText;
    @FXML
    private Text mmajorSevText;
    @FXML
    private Text mcritSevText;
    ///half-yearly///
    @FXML
    private Tab halfyearlyTab;
    @FXML
    private Text reportDate11;
    @FXML
    private Text hnewReportCountText;
    @FXML
    private Text hnewReportAssText;
    @FXML
    private Text hnewReportunAssText;
    @FXML
    private Text hreportedBugsText;
    @FXML
    private Text hprogresBugsText;
    @FXML
    private Text hsolvedBugsTest;
    @FXML
    private Text hlowPrioText;
    @FXML
    private Text hmediumPrioText;
    @FXML
    private Text hhighPrioText;
    @FXML
    private Text hemergPrioText;
    @FXML
    private Text hcosSevText;
    @FXML
    private Text hminorSevText;
    @FXML
    private Text hmajorSevText;
    @FXML
    private Text hcritSevText;
    ///yearly///
    @FXML
    private Tab yearlyTab;
    @FXML
    private Text reportDate111;
    @FXML
    private Text ynewReportCountText;
    @FXML
    private Text ynewReportAssText;
    @FXML
    private Text ynewReportunAssText;
    @FXML
    private Text yreportedBugsText;
    @FXML
    private Text yprogresBugsText;
    @FXML
    private Text ysolvedBugsTest;
    @FXML
    private Text ylowPrioText;
    @FXML
    private Text ymediumPrioText;
    @FXML
    private Text yhighPrioText;
    @FXML
    private Text yemergPrioText;
    @FXML
    private Text ycosSevText;
    @FXML
    private Text yminorSevText;
    @FXML
    private Text ymajorSevText;
    @FXML
    private Text ycritSevText;
    @FXML
    private Button backToMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //call the generate weekly function
        
    }    

    @FXML
    private void generateWeekly(Event event) {
        //get the counts of each text and set text
    }

    @FXML
    private void generateMonthly(Event event) {
    }

    @FXML
    private void generateHalfYearly(Event event) {
    }

    @FXML
    private void generateYearly(Event event) {
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
    
}
