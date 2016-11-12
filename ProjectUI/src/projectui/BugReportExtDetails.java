package projectui;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michaelbeavis on 26/10/2016.
 */
public class BugReportExtDetails {

    private String BugName;
    private String BugID;
    private String Status;
    private String Priority;
    private String Date;
    private String Reporter;
    private String Product;
    private String operSys;
    private String component;
    private String version;
    private String severity;
    private String assignedTo;
    private String longDesc;
    private String resolution;
    private int uRep;
    private int reporterIDnum;
    

    public BugReportExtDetails(int id) throws SQLException {
        MySQLController conn = new MySQLController();
        try (ResultSet rs = conn.populateExtDetails(id)) {
            while (rs.next()) {
                BugName = rs.getString(1);
                BugID = rs.getString(2);
                Status = rs.getString(3);
                Priority = rs.getString(4);
                Date = rs.getString(5);
                Product = rs.getString(6);
                operSys = rs.getString(7);
                component = rs.getString(8);
                version = rs.getString(9);
                severity = rs.getString(10);
                Reporter = rs.getString(11);
                assignedTo = rs.getString(12);
                longDesc = rs.getString(13);
                resolution = rs.getString(14);
                uRep = rs.getInt(15);
                reporterIDnum = rs.getInt(16);

            }
        } catch (Exception e){
            System.out.println(e);
        }


    }

    //BugReportExtDetails getters
    public String getBugName(){return BugName;}
    public String getBugID(){return BugID;}
    public String getStatus(){return Status;}
    public String getPriority(){return Priority;}
    public String getDate(){return Date;}
    public String getProduct(){return Product;}
    public String getOperSys(){return operSys;}
    public String getComponent(){return component;}
    public String getVersion(){return version;}
    public String getSeverity(){return severity;}
    public String getReporter(){return Reporter;}
    public String getAssignedTo(){return assignedTo;}
    public String getLongDesc(){return longDesc;}
    public String getResolution(){return resolution;}
    public int getUserRep(){return uRep;}
    public int getReporterIDnum(){return reporterIDnum;}
    
    
}