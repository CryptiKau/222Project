package projectui;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michaelbeavis on 26/10/2016.
 */
public class BugReportExtDetails {

        //not in DB yet
//        private String bugDesc;
//        private String bugReso;

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

            }
        } catch (Exception e){
            System.out.println(e);
        }


        //populate table based on id value passed

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
    
}
