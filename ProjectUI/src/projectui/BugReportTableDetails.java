package projectui;

import javafx.beans.property.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michaelbeavis on 25/10/2016.
 */
public class BugReportTableDetails {
    //From Bug Table

    //From BugReports Table
    
    
    private int bugReportID;
    private final StringProperty createdDate;
    private final StringProperty shortDesc;
    private final StringProperty bugStatus;
    private final StringProperty priority;
    
    private final StringProperty bugName;
    private final StringProperty reporter;
    
    public BugReportTableDetails(ResultSet rs) throws SQLException {
        this.bugReportID = rs.getInt(1);
        this.createdDate = new SimpleStringProperty(rs.getString(2));
        this.shortDesc = new SimpleStringProperty(rs.getString(3));
        this.bugStatus = new SimpleStringProperty(rs.getString(4));
        this.priority = new SimpleStringProperty(rs.getString(5));
        //COULD NOT GET THIS TO WORK
        this.bugName = new SimpleStringProperty(rs.getString(6));
        System.out.println(rs.getString(7));
        this.reporter = new SimpleStringProperty(rs.getString(7));
        System.out.println(rs.getString(7));
        //THIS WORKS

    }


    //BugReportDetails getters
    public String getBugName(){return bugName.get();}
    public String getReporter(){return reporter.get();}
    public int getBugReportID(){return bugReportID;}
    
    public String getCreatedDate(){return createdDate.get();}
    public String getShortDesc(){return shortDesc.get();}
    public String getBugStatus(){return bugStatus.get();}
    public String getPriority(){return priority.get();}

    


}
