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

/**
 * FXML Controller class
 *
 * @author Nathan
 */
public class FXMLBugsController implements Initializable {
    
    CurrentUser currentUser;
    
    public FXMLBugsController (CurrentUser curr){
        currentUser = curr;
    }
    ////////////////////////////////
    //Tabs
    ///////////////////////////////
//    @FXML
//    private TabPane createBugTab;
    ////////////////////////////////
    //Search Fields
    ///////////////////////////////
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
    private TextField assignedBox;
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
    private TextField NewAssignedName;
    @FXML
    private ComboBox<String> newSeverity;
    @FXML
    private ComboBox<String> newPriority;
    @FXML
    private ComboBox<String> newStatus;
    @FXML
    private Button backButton;
    
    ////////////////////////////////
    //Comments
    ///////////////////////////////
    @FXML
    private TableView<?> selectedCmnt;
    @FXML
    private TableColumn<?, ?> selectedUsercmnt;
    @FXML
    private TableColumn<?, ?> selectedDatecmnt;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        searchBy.setValue("Search By");
        searchBy.getItems().addAll("Search By", "User","BugStatus","Priority");


        statusBox.setValue("Select");
        statusBox.getItems().addAll("Select", "Reported","Progressing","Solved");

        priorityBox.setValue("Select");
        priorityBox.getItems().addAll("Select", "Low", "Medium", "High", "Emergency");

        severityBox.setValue("Select");
        severityBox.getItems().addAll("Select", "Critical","Major","Minor", "Cosmetic");

        newStatus.setValue("Select");
        newStatus.getItems().addAll("Select", "Reported","Progressing","Solved");

        newPriority.setValue("Select");
        newPriority.getItems().addAll("Select", "Low", "Medium", "High", "Emergency");

        newSeverity.setValue("Select");
        newSeverity.getItems().addAll("Select", "Critical","Major","Minor", "Cosmetic");
        
        
        
        //make the following visible to reporter and up
        statusBox.setVisible(false);   
        priorityBox.setVisible(false);   
        severityBox.setVisible(false);
        
        
        switch(currentUser.getSecLevel()){
            case 1:
                createBugTab.setDisable(true);
                editButton.setVisible(false);
                saveButton.setVisible(false);
                increaseRepButton.setVisible(false);
                decreaseRepButton.setVisible(false);
                addCommentButton.setVisible(false);
                submitCommentButton.setVisible(false);
                uploadPatchButton.setVisible(false);
                break;
            case 2:
                editButton.setVisible(true);
                saveButton.setVisible(true);
                increaseRepButton.setVisible(true);
                decreaseRepButton.setVisible(true);
                addCommentButton.setVisible(true);
                submitCommentButton.setVisible(true);
                uploadPatchButton.setVisible(true);
                statusBox.setDisable(true);
                priorityBox.setDisable(true);  
                severityBox.setDisable(true);
                statusBox.setVisible(false);   
                priorityBox.setVisible(false);   
                severityBox.setVisible(false);
                break;
            case 3:
                editButton.setVisible(true);
                saveButton.setVisible(true);
                increaseRepButton.setVisible(true);
                decreaseRepButton.setVisible(true);
                addCommentButton.setVisible(true);
                submitCommentButton.setVisible(true);
                uploadPatchButton.setVisible(true);
                statusBox.setDisable(false);
                priorityBox.setDisable(false);  
                severityBox.setDisable(false);
                statusBox.setVisible(false);   
                priorityBox.setVisible(false);   
                severityBox.setVisible(false);
            case 5:
                break;
            default:
                editButton.setVisible(false);
                saveButton.setVisible(false);
                increaseRepButton.setVisible(false);
                decreaseRepButton.setVisible(false);
                addCommentButton.setVisible(false);
                submitCommentButton.setVisible(false);
                uploadPatchButton.setVisible(false);
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
                    break;
                case "Progressing":
                case "progressing":
                    reports.searchReportsByStatus("Progressing");
                    break;
                case "Solved":
                case "solved":
                    reports.searchReportsByStatus("Solved");
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
                    break;
                case "Medium":
                case "medium":
                    reports.searchReportsByPriority("Medium");
                    break;
                case "High":
                case "high":
                    reports.searchReportsByPriority("High");
                    break;
                case "Emergency":
                case "emergency":
                    reports.searchReportsByPriority("Emergency");
                    break;
                default:
                     searchError.setText("You must either choose: Low, Medium, High, or Emergency");
                     break;
            }
            
            //reports.searchReportsByPriority(searchBox.getText());
        }
        /*else if(searchBy.getValue().equals("Keywords")){
            //reports.searchReportsByKeywords(searchBox.getText());
        }*/
        else{
            searchError.setText("You have not selected a Search by choice");
            
        }
    

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

        BugReportExtDetails extDetails = new BugReportExtDetails(table.getBugReportID());

        System.out.println("YYY--- " + extDetails.getBugName());

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
        
        selectedReporter.setText(extDetails.getReporter());
        selectedAssign.setText(extDetails.getAssignedTo());
        
        //###THIS NEEDS TO BE ADDED SAME WITH RESOLUTION
        //selectedbugDesc.setText(extDetails.getDesc());
        //selectedbugReso.setText(extDetails.getReso());
        
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
        assignedBox.setText(extDetails.getAssignedTo());
        
        
        //#######You also need to populate the comment table####//
        //TableView<?> selectedCmnt;
        //TableColumn<?, ?> selectedUsercmnt;
        //TableColumn<?, ?> selectedDatecmnt;
        ///////////Same way you did the bug table data////////////
        

    }

    @FXML
    private void editBugDetails(ActionEvent event) {

        System.out.println("Edit Button Pressed");
        //On button press hides uneditable text
        selectedbugDesc.setEditable(true);
        selectedbugReso.setEditable(true);
        selectedBugName.setVisible(false);
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
        bugNamebox.setVisible(true);  
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
        assignedBox.setEditable(true);    
        cmpntBox.setEditable(true);   
        osBox.setEditable(true); 
        
        // need to fill editable fields with current data from database
        //if none show empty
        
        
        
        
    }

    @FXML
    private void saveBugDetails(ActionEvent event) {
        
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
        
    }
    
    @FXML
    private void backtoMenu(ActionEvent event) throws IOException {

        System.out.println("BACK TO MENU");

        switch(currentUser.getSecLevel()){
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
    private void submitnewBug(ActionEvent event) {
        
        //take all variables from the table and save into DB
        
    }
    
    @FXML
    private void addComment(ActionEvent event) {
        
        //adds an editable row into already existing content of the comment table
        //allows edit in said row
        
    }
    
    @FXML
    private void submitComment(ActionEvent event) {
        
        //take all variables from the table and save into DB
        //takes last row added and adds it to the db as a new comment
    }
    
    @FXML
    private void uploadPatch(ActionEvent event) {
        
        //opens new window to upload patch? How?
        
    }
    
    @FXML
    private void decreaseRep(ActionEvent event) {
        
        //obvious decrease rep. After 1 press disable both the button
        
    }
    
    @FXML
    private void increaseRep(ActionEvent event) {
        
        //obvious increase rep. After 1 press disable both the button
        
    }
    
}
