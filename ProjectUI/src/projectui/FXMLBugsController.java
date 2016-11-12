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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLBugsController implements Initializable {
    
    CurrentUser currentUser;
    
    int bReportID;
    public FXMLBugsController (CurrentUser curr){
        currentUser = curr;
    }
    @FXML
    private Tab createBugTab;
    @FXML
    private TextField searchBox;
    @FXML
    private ComboBox<String> searchBy;
    @FXML
    private Button searchButton;
    @FXML
    private Text searchError;
    
    ////////////////////////////////
    //Search Bugs
    ///////////////////////////////
    @FXML
    private TableView<BugReportTableDetails> tablebugSearch;
    @FXML
    private TableColumn<BugReportTableDetails, String> tablebugName;
    @FXML
    private TableColumn<BugReportTableDetails, String> tableshortDesc;
    @FXML
    private TableColumn<BugReportTableDetails, String> tablebStatus;
    @FXML
    private TableColumn<BugReportTableDetails, String> tablePriority;
    @FXML
    private TableColumn<BugReportTableDetails, String> tableDate;
    @FXML
    private TableColumn<BugReportTableDetails, String> tablebugReporter;
    
    ////////////////////////////////
    //Bug Details
    ///////////////////////////////
    @FXML
    private TextArea selectedbugDesc;
    @FXML
    private TextArea selectedbugReso;
    @FXML
    private Text selectedBugName;
    @FXML
    private Text selectedBugID;
    @FXML
    private Text selectedStatus;
    @FXML
    private Text selectedPriority;
    @FXML
    private Text selectedDate;
    @FXML
    private Text selectedReporter;
    @FXML
    private Text selectedProduct;
    @FXML
    private Text selectedOS;
    @FXML
    private Text selectedCmpnt;
    @FXML
    private Text selectedVer;
    @FXML
    private Text selectedSev;
    @FXML
    private Text selectedAssign;
    
    ////////////////////////////////
    //Edit Bugs
    ///////////////////////////////
    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextField productBox;
    @FXML
    private TextField bugNamebox;
    @FXML
    private TextField versionBox;
    @FXML
    public ComboBox<String> assignedBox;
    @FXML
    private TextField cmpntBox;
    @FXML
    private TextField osBox;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private ComboBox<String> priorityBox;
    @FXML
    private ComboBox<String> severityBox;
    @FXML
    private Button increaseRepButton;
    @FXML
    private Button decreaseRepButton;
    @FXML
    private Button addCommentButton;
    @FXML
    private Button submitCommentButton;
    @FXML
    private Button uploadPatchButton;
    @FXML
    private Text repLevel;
    
    
    ////////////////////////////////
    //Create Bugs
    ///////////////////////////////
    @FXML
    private TextArea newDesc;
    @FXML
    private TextArea newKeywords;
    @FXML
    private TextField newBugName;
    @FXML
    private TextField newProductname;
    @FXML
    private TextField newVersionName;
    @FXML
    private TextField newComponentname;
    @FXML
    private TextField newOSName;
    @FXML
    private ComboBox<String> newSeverity;
    @FXML
    private ComboBox<String> newPriority;
    @FXML
    private Button backButton;
    @FXML
    private TextField newShortDesc;
    @FXML
    private Text createErrorText;
    @FXML
    private Button newrefreshButton;
    
    ////////////////////////////////
    //Comments
    ///////////////////////////////
    @FXML
    private TableView<BugComments> selectedCmntTable;
    @FXML
    private TableColumn<BugComments, String> selectedUsercmnt;
    @FXML
    private TableColumn<BugComments, String> commentText;
    @FXML
    private TableColumn<BugComments, String> selectedDatecmnt;
    int selectedUID;
    int repSentinal = 0;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        searchBy.setValue("Search By");
        searchBy.getItems().addAll("Search By", "User","BugStatus","Priority","Keyword", "Get All", "Bug Name");


        statusBox.setValue("Reported");
        statusBox.getItems().addAll("Reported", "Approved","Progressing","Solved");

        priorityBox.setValue("Low");
        priorityBox.getItems().addAll("Low", "Medium", "High", "Emergency");

        severityBox.setValue("Critical");
        severityBox.getItems().addAll("Critical","Major","Minor", "Cosmetic");
        
        newPriority.setValue("Low");
        newPriority.getItems().addAll("Low", "Medium", "High", "Emergency");

        newSeverity.setValue("Critical");
        newSeverity.getItems().addAll("Critical","Major","Minor", "Cosmetic");
        
        
        
        //make the following visible to reporter and up
        statusBox.setVisible(false);   
        priorityBox.setVisible(false);   
        severityBox.setVisible(false);
        assignedBox.setVisible(false); 
        editButton.setDisable(true);
        saveButton.setDisable(true);
        
        decreaseRepButton.setDisable(true);
        increaseRepButton.setDisable(true);
        uploadPatchButton.setDisable(true);
        tablebugSearch.setDisable(true);
        newrefreshButton.setDisable(true);
        
        
        switch(currentUser.getAccessLevel()){
            case 1:
                createBugTab.setDisable(true);
                editButton.setVisible(false);
                saveButton.setVisible(false);
                increaseRepButton.setVisible(false);
                decreaseRepButton.setVisible(false);
                addCommentButton.setVisible(false);
                uploadPatchButton.setVisible(false);
                decreaseRepButton.setDisable(true);
                increaseRepButton.setDisable(true);
                break;
            case 2:
                editButton.setVisible(true);
                saveButton.setVisible(true);
                increaseRepButton.setVisible(true);
                decreaseRepButton.setVisible(true);
                addCommentButton.setVisible(true);
                uploadPatchButton.setVisible(false);
                statusBox.setDisable(true);
                priorityBox.setDisable(true);  
                severityBox.setDisable(true);
                statusBox.setVisible(false);   
                priorityBox.setVisible(false);   
                severityBox.setVisible(false);
                assignedBox.setDisable(true);
                break;
            case 3:
                editButton.setVisible(true);
                saveButton.setVisible(true);
                increaseRepButton.setVisible(true);
                decreaseRepButton.setVisible(true);
                addCommentButton.setVisible(true);
                uploadPatchButton.setVisible(true);
                statusBox.setDisable(true);
                priorityBox.setDisable(false);  
                severityBox.setDisable(false);
                statusBox.setVisible(false);   
                priorityBox.setVisible(false);   
                severityBox.setVisible(false);
                assignedBox.setDisable(true);
            case 4:
            case 5:
                break;
            default:
                createBugTab.setDisable(true);
                editButton.setVisible(false);
                saveButton.setVisible(false);
                increaseRepButton.setVisible(false);
                decreaseRepButton.setVisible(false);
                addCommentButton.setVisible(false);
                uploadPatchButton.setVisible(false);
                decreaseRepButton.setDisable(true);
                increaseRepButton.setDisable(true);
                break;
                
        }
        
        
        //dev and up
        
        
    }    

    @FXML
    private void searchBugs(ActionEvent event) throws SQLException {
        SearchReports reports = new SearchReports();

        if(searchBy.getValue().equals("BugStatus")){
            
            switch(searchBox.getText()){
                case "Reported":
                case "reported":
                    reports.searchReportsByStatus("Reported");
                    searchError.setText("");
                    break;
                case "Progressing":
                case "progressing":
                    reports.searchReportsByStatus("Progressing");
                    searchError.setText("");
                    break;
                case "Solved":
                case "solved":
                    reports.searchReportsByStatus("Solved");
                    searchError.setText("");
                    break;
                case "Approved":
                case "approved":
                    reports.searchReportsByStatus("Approved");
                    searchError.setText("");
                    break;
                default:
                     searchError.setText("You must either choose: Reported, Progressing, or Solved");
                     break;
            }
        }
        else if(searchBy.getValue().equals("User")){
            reports.searchReportsByUser(searchBox.getText());
        }
        else if(searchBy.getValue().equals("Priority")){
            
            switch(searchBox.getText()){
                case "Low":
                case "low":
                    reports.searchReportsByPriority("Low");
                    searchError.setText("");
                    break;
                case "Medium":
                case "medium":
                    reports.searchReportsByPriority("Medium");
                    searchError.setText("");
                    break;
                case "High":
                case "high":
                    reports.searchReportsByPriority("High");
                    searchError.setText("");
                    break;
                case "Emergency":
                case "emergency":
                    reports.searchReportsByPriority("Emergency");
                    searchError.setText("");
                    break;
                default:
                     searchError.setText("You must either choose: Low, Medium, High, or Emergency");
                     break;
            }
            
            //reports.searchReportsByPriority(searchBox.getText());
        }
        else if(searchBy.getValue().equals("Get All")){
             reports.searchReportsAll();
        }
        else if(searchBox.getText().trim().isEmpty()){
            searchError.setText("Search Field Empty");
        }
        else if(searchBy.getValue().equals("Keyword")){
            reports.searchReportsByKeywords(searchBox.getText());
        }
        else if(searchBy.getValue().equals("Bug Name")){
            System.out.println(searchBox.getText());
            reports.searchReportsByBName(searchBox.getText());
        }
        else{
            searchError.setText("You have not selected a Search by choice");
            
        }
        
        tablebugSearch.setDisable(false);
    

        System.out.println("SEARCHING");
        
        tablebugName.setCellValueFactory(new PropertyValueFactory<>("bugName"));
        tableshortDesc.setCellValueFactory(new PropertyValueFactory<>("shortDesc"));
        tablebStatus.setCellValueFactory(new PropertyValueFactory<>("bugStatus"));
        tablePriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        tablebugReporter.setCellValueFactory(new PropertyValueFactory<>("reporter"));
        
        tablebugSearch.setItems(null);
        tablebugSearch.setItems(reports.getReportDetails());

    }
    
    @FXML
    private void bugSelected(MouseEvent event) throws SQLException {
        BugReportTableDetails table = tablebugSearch.getSelectionModel().getSelectedItem();
        System.out.println(table.getBugReportID() + " --XXXXX");
        
        MySQLController conn = new MySQLController();
        ObservableList<String> Devs = conn.getDevList();
        assignedBox.setItems(Devs);

        BugReportExtDetails extDetails = new BugReportExtDetails(table.getBugReportID());

        System.out.println("YYY--- " + extDetails.getBugName());
        editButton.setDisable(false);
        saveButton.setDisable(false);
        
        selectedUID = extDetails.getReporterIDnum();

        selectedBugName.setText(extDetails.getBugName());
        selectedBugID.setText(extDetails.getBugID());
        selectedStatus.setText(extDetails.getStatus());
        selectedPriority.setText(extDetails.getPriority());
        String Date = extDetails.getDate();
        String[] FixedDate = Date.split("\\s+");
        selectedDate.setText(FixedDate[0]);
        selectedProduct.setText(extDetails.getProduct());
        selectedOS.setText(extDetails.getOperSys());
        selectedCmpnt.setText(extDetails.getComponent());
        selectedVer.setText(extDetails.getVersion());
        selectedSev.setText(extDetails.getSeverity());
        statusBox.setValue(extDetails.getStatus());
        priorityBox.setValue(extDetails.getPriority());
     
        severityBox.setValue(extDetails.getSeverity());
        assignedBox.setValue(extDetails.getAssignedTo());
        selectedReporter.setText(extDetails.getReporter());
        selectedAssign.setText(extDetails.getAssignedTo());
        
        //###THIS NEEDS TO BE ADDED SAME WITH RESOLUTION
        selectedbugDesc.setText(extDetails.getLongDesc());
        selectedbugReso.setText(extDetails.getResolution());
        
        bugNamebox.setText(extDetails.getBugName());
        selectedBugID.setText(extDetails.getBugID());
        selectedStatus.setText(extDetails.getStatus());
        selectedPriority.setText(extDetails.getPriority());
        productBox.setText(extDetails.getProduct());
        osBox.setText(extDetails.getOperSys());
        cmpntBox.setText(extDetails.getComponent());
        versionBox.setText(extDetails.getVersion());
        selectedSev.setText(extDetails.getSeverity());
        statusBox.setValue(extDetails.getStatus());
        priorityBox.setValue(extDetails.getPriority());
        severityBox.setValue(extDetails.getSeverity());
        selectedReporter.setText(extDetails.getReporter());
        String newRp = Integer.toString(extDetails.getUserRep());
        repLevel.setText(newRp);
        
        decreaseRepButton.setDisable(false);
        increaseRepButton.setDisable(false);
        uploadPatchButton.setDisable(false);
        newrefreshButton.setDisable(false);
 
        SearchComments comments = new SearchComments();
        comments.searchCommentDetails(table.getBugReportID());
        bReportID = table.getBugReportID();
        
        selectedUsercmnt.setCellValueFactory(new PropertyValueFactory<>("commenter"));
        commentText.setCellValueFactory(new PropertyValueFactory<>("commentText"));
        selectedDatecmnt.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        
        selectedCmntTable.setItems(null);
        selectedCmntTable.setItems(comments.getCommentDetails());
    }

    @FXML
    private void editBugDetails(ActionEvent event) {

        System.out.println("Edit Button Pressed");
        //On button press hides uneditable text
        selectedbugDesc.setEditable(true);
        selectedbugReso.setEditable(true);
        //selectedBugName.setVisible(false);
        selectedStatus.setVisible(false);
        selectedPriority.setVisible(false);
        selectedProduct.setVisible(false);
        selectedOS.setVisible(false);
        selectedCmpnt.setVisible(false);
        selectedVer.setVisible(false);
        selectedSev.setVisible(false);
        selectedAssign.setVisible(false);
        
        //Show editable fields
        
        
        productBox.setVisible(true);   
        //bugNamebox.setVisible(true);  
        versionBox.setVisible(true);  
        assignedBox.setVisible(true);    
        cmpntBox.setVisible(true);    
        osBox.setVisible(true);    
        statusBox.setVisible(true);   
        priorityBox.setVisible(true);   
        severityBox.setVisible(true);
        
        productBox.setEditable(true);
        bugNamebox.setEditable(true);
        versionBox.setEditable(true);   
        cmpntBox.setEditable(true);   
        osBox.setEditable(true); 
        
        // need to fill editable fields with current data from database
        //if none show empty
        
        
        
        
        
        
    }

    @FXML
    private void saveBugDetails(ActionEvent event) throws SQLException {
        
        //TAKE NEW VALUES FROM FIELDS AND USE UPDATE STATEMENT//
        
        //reveal non editable fields
        selectedbugDesc.setEditable(false);
        selectedbugReso.setEditable(false);
        selectedBugName.setVisible(true);
        selectedStatus.setVisible(true);
        selectedPriority.setVisible(true);
        selectedProduct.setVisible(true);
        selectedOS.setVisible(true);
        selectedCmpnt.setVisible(true);
        selectedVer.setVisible(true);
        selectedSev.setVisible(true);
        selectedAssign.setVisible(true);
        
        //make editable fields invisible and disabled
        productBox.setVisible(false);   
        bugNamebox.setVisible(false);  
        versionBox.setVisible(false);  
        assignedBox.setVisible(false);    
        cmpntBox.setVisible(false);    
        osBox.setVisible(false);    
        statusBox.setVisible(false);   
        priorityBox.setVisible(false);   
        severityBox.setVisible(false);
        
        productBox.setEditable(false);
        bugNamebox.setEditable(false);
        versionBox.setEditable(false);
        assignedBox.setEditable(false);    
        cmpntBox.setEditable(false);   
        osBox.setEditable(false); 
        
        MySQLController conn = new MySQLController();
        try{
            NewBugReport report = new NewBugReport();
            report.setBugName(selectedBugName.getText());
            int BID = Integer.parseInt(selectedBugID.getText());
            report.setBugID(BID);
            report.setProduct(productBox.getText());
            report.setBugStatus(statusBox.getValue());
            report.setPriority(priorityBox.getValue());
            report.setVersion(versionBox.getText());
            report.setOperSys(osBox.getText());
            report.setBugSev(severityBox.getValue());
            report.setComponent(cmpntBox.getText());
            report.setAssigned(assignedBox.getSelectionModel().getSelectedItem());
            report.setReporter(selectedUID);
            System.out.println(report.getAssigned());
            System.out.println(report.getAssigned());
        
            report.setResolution(selectedbugReso.getText());
            report.setLongDesc(selectedbugDesc.getText());
            int uRep = Integer.parseInt(repLevel.getText());
            report.setUserRep(uRep);
            report.updateReport();
            searchError.setFill(Color.WHITE);
            searchError.setText("Bug Edits Saved, Please Search Again");
            increaseRepButton.setDisable(false);
            decreaseRepButton.setDisable(false);
            repSentinal = 0;
            
            clearSearch();
            tablebugSearch.setDisable(true);
            newrefreshButton.setDisable(true);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        //conn.closeConnection();
        
    }
    
    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {

        System.out.println("BACK TO MENU");

        switch(currentUser.getAccessLevel()){
            case 1:
            {
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
            case 2:
            case 3:
            case 4:
            case 5:
            {
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAuthMenu.fxml"));
                FXMLAuthMenuController controller = new FXMLAuthMenuController(currentUser);
                loader.setController(controller);
                Parent menuPage_parent = loader.load();
                Scene menuPage_scene = new Scene(menuPage_parent);
                app_stage.hide();
                app_stage.setScene(menuPage_scene);
                app_stage.show();
            }
        }
    }
    
    @FXML
    private void submitnewBug(ActionEvent event) throws SQLException {
        //take all variables from the table and save into DB
        
        if(newBugName.getText().trim().isEmpty() ||
           newComponentname.getText().trim().isEmpty() ||
           newProductname.getText().trim().isEmpty() ||
           newOSName.getText().trim().isEmpty() ||
           newVersionName.getText().trim().isEmpty() ||
           newDesc.getText().trim().isEmpty() ||
           newKeywords.getText().trim().isEmpty() ||
           newShortDesc.getText().trim().isEmpty()){
             createErrorText.setText("One or More Fields are Blank");
        }
        else{
            MySQLController conn = new MySQLController();

            NewBugReport report = new NewBugReport();
            report.setReporter(currentUser.getUserID());
            report.setBugName(newBugName.getText());
            report.setComponent(newComponentname.getText());
            report.setBugSev(newSeverity.getValue());
            report.setProduct(newProductname.getText());
            report.setOperSys(newOSName.getText());
            report.setPriority(newPriority.getValue());
            report.setVersion(newVersionName.getText());
            report.setBugStatus("Reported");
            report.setLongDesc(newDesc.getText());
            report.setKeywords(newKeywords.getText());
            report.setShortDesc(newShortDesc.getText());
            report.submitReport();
            createErrorText.setText("Report Submitted");
            clearScreen();
        }

        


        
    }
    
    @FXML
    private void addComment(ActionEvent event) throws IOException {
        
        Stage cmmt_stage = new Stage();
        FXMLLoader cmmtloader = new FXMLLoader(getClass().getResource("FXMLAddComment.fxml"));
        FXMLAddCommentController controller = new FXMLAddCommentController(currentUser, bReportID);
        cmmtloader.setController(controller);
        Parent cmmt_parent = cmmtloader.load();
        Scene cmmt_scene = new Scene(cmmt_parent);
        cmmt_stage.setScene(cmmt_scene);
        cmmt_stage.show(); 
        
    }
    
    
    @FXML
    private void uploadPatch(ActionEvent event) throws IOException {
        
        Stage cmmt_stage = new Stage();
        FXMLLoader cmmtloader = new FXMLLoader(getClass().getResource("FXMLPatch.fxml"));
        FXMLPatchController controller = new FXMLPatchController(currentUser, bReportID);
        cmmtloader.setController(controller);
        Parent cmmt_parent = cmmtloader.load();
        Scene cmmt_scene = new Scene(cmmt_parent);
        cmmt_stage.setScene(cmmt_scene);
        cmmt_stage.show(); 
        
    }
    
    @FXML
    private void decreaseRep(ActionEvent event) {
        
        int uRep = Integer.parseInt(repLevel.getText());
        uRep--;
        String newRep = Integer.toString(uRep);
        repLevel.setText(newRep);
        repSentinal--;
        decreaseRepButton.setDisable(true);
        if(repSentinal == 0)
        {
            increaseRepButton.setDisable(false);
            decreaseRepButton.setDisable(false);
        }
        
        
    }
    
    @FXML
    private void increaseRep(ActionEvent event) {
        
        int uRep = Integer.parseInt(repLevel.getText());
        uRep++;
        String newRep = Integer.toString(uRep);
        repLevel.setText(newRep);
        repSentinal++;
        increaseRepButton.setDisable(true);
        if(repSentinal == 0)
        {
            decreaseRepButton.setDisable(false);
            increaseRepButton.setDisable(false);
        }
        
        
    }
    
    
    @FXML
    private void doRefresh(MouseEvent event) throws SQLException{
        SearchComments coms = new SearchComments();
        coms.searchCommentDetails(bReportID);
              
        selectedUsercmnt.setCellValueFactory(new PropertyValueFactory<>("commenter"));
        commentText.setCellValueFactory(new PropertyValueFactory<>("commentText"));
        selectedDatecmnt.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        
        selectedCmntTable.setItems(null);
        selectedCmntTable.setItems(coms.getCommentDetails());
        
    }
    
    private void clearScreen(){
        newBugName.setText("");
        newComponentname.setText("");
        newSeverity.setValue("");
        newProductname.setText("");
        newOSName.setText("");
        newPriority.setValue("");
        newVersionName.setText("");
        newShortDesc.setText("");
        newDesc.setText("");
        newKeywords.setText("");
    }
    
    private void clearSearch(){
        selectedUID = 0;

        selectedBugName.setText("");
        selectedBugID.setText("");
        selectedStatus.setText("");
        selectedPriority.setText("");
        selectedDate.setText("");
        selectedProduct.setText("");
        selectedOS.setText("");
        selectedCmpnt.setText("");
        selectedVer.setText("");
        selectedSev.setText("");
        statusBox.setValue("Status");
        priorityBox.setValue("Priority");
     
        severityBox.setValue("Severity");
        
        selectedReporter.setText("");
        selectedAssign.setText("");
        
        //###THIS NEEDS TO BE ADDED SAME WITH RESOLUTION
        selectedbugDesc.setText("");
        selectedbugReso.setText("");
        
        bugNamebox.setText("");
        selectedBugID.setText("");
        selectedStatus.setText("");
        selectedPriority.setText("");
        productBox.setText("");
        osBox.setText("");
        cmpntBox.setText("");
        versionBox.setText("");
        selectedSev.setText("");
        statusBox.setValue("Status");
        priorityBox.setValue("Priority");
        severityBox.setValue("Severity");
        selectedReporter.setText("");
        //assignedBox.getItems().clear();
        repLevel.setText("");
        
        selectedCmntTable.getItems().clear();
        tablebugSearch.getItems().clear();
        editButton.setDisable(true);
        saveButton.setDisable(true);
        increaseRepButton.setDisable(true);
        decreaseRepButton.setDisable(true);
        
        
    }
    
}
