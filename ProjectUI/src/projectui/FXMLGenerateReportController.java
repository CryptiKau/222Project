/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import static javax.management.Query.value;

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
    private Text wreportDate;
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
    private Text mreportDate;
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
    private Text hreportDate;
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
    private Text yreportDate;
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
        try {
            generateWeekly();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        
        yreportDate.setText(date);
        mreportDate.setText(date);
        wreportDate.setText(date);
        hreportDate.setText(date);

    }    

    @FXML
    private void generateWeekly() throws SQLException {
        //get the counts of each text and set text
        ReportStats week = new ReportStats(7);

        newReportCountText.setText(week.getNewBugReports());
        newReportAssText.setText(week.getReportsAssigned());
        newReportunAssText.setText(week.getReportsUnassigned());
        reportedBugsText.setText(week.getReportedBugs());
        progresBugsText.setText(week.getProgressingBugs());
        solvedBugsTest.setText(week.getSolvedBugs());
        lowPrioText.setText(week.getLowPriority());
        mediumPrioText.setText(week.getMedPriority());
        highPrioText.setText(week.getHighPriority());
        emergPrioText.setText(week.getEmergencyPriority());
        cosSevText.setText(week.getCosmeticSev());
        minorSevText.setText(week.getMinorSev());
        majorSevText.setText(week.getMajorSev());
        critSevText.setText(week.getCriticalSev());
    }

    @FXML
    private void generateMonthly(Event event) throws SQLException {
        ReportStats month = new ReportStats(30);

        mnewReportCountText.setText(month.getNewBugReports());
        mnewReportAssText.setText(month.getReportsAssigned());
        mnewReportunAssText.setText(month.getReportsUnassigned());
        mreportedBugsText.setText(month.getReportedBugs());
        mprogresBugsText.setText(month.getProgressingBugs());
        msolvedBugsTest.setText(month.getSolvedBugs());
        mlowPrioText.setText(month.getLowPriority());
        mmediumPrioText.setText(month.getMedPriority());
        mhighPrioText.setText(month.getHighPriority());
        memergPrioText.setText(month.getEmergencyPriority());
        mcosSevText.setText(month.getCosmeticSev());
        mminorSevText.setText(month.getMinorSev());
        mmajorSevText.setText(month.getMajorSev());
        mcritSevText.setText(month.getCriticalSev());
    }

    @FXML
    private void generateHalfYearly(Event event) throws SQLException {
        ReportStats halfYear = new ReportStats(183);

        hnewReportCountText.setText(halfYear.getNewBugReports());
        hnewReportAssText.setText(halfYear.getReportsAssigned());
        hnewReportunAssText.setText(halfYear.getReportsUnassigned());
        hreportedBugsText.setText(halfYear.getReportedBugs());
        hprogresBugsText.setText(halfYear.getProgressingBugs());
        hsolvedBugsTest.setText(halfYear.getSolvedBugs());
        hlowPrioText.setText(halfYear.getLowPriority());
        hmediumPrioText.setText(halfYear.getMedPriority());
        hhighPrioText.setText(halfYear.getHighPriority());
        hemergPrioText.setText(halfYear.getEmergencyPriority());
        hcosSevText.setText(halfYear.getCosmeticSev());
        hminorSevText.setText(halfYear.getMinorSev());
        hmajorSevText.setText(halfYear.getMajorSev());
        hcritSevText.setText(halfYear.getCriticalSev());
    }

    @FXML
    private void generateYearly(Event event) throws SQLException {
        ReportStats year = new ReportStats(366);

        ynewReportCountText.setText(year.getNewBugReports());
        ynewReportAssText.setText(year.getReportsAssigned());
        ynewReportunAssText.setText(year.getReportsUnassigned());
        yreportedBugsText.setText(year.getReportedBugs());
        yprogresBugsText.setText(year.getProgressingBugs());
        ysolvedBugsTest.setText(year.getSolvedBugs());
        ylowPrioText.setText(year.getLowPriority());
        ymediumPrioText.setText(year.getMedPriority());
        yhighPrioText.setText(year.getHighPriority());
        yemergPrioText.setText(year.getEmergencyPriority());
        ycosSevText.setText(year.getCosmeticSev());
        yminorSevText.setText(year.getMinorSev());
        ymajorSevText.setText(year.getMajorSev());
        ycritSevText.setText(year.getCriticalSev());
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
