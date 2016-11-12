package projectui;

import java.sql.SQLException;

/**
 * Created by michaelbeavis on 26/10/2016.
 */
public class NewBugReport {

    //Bug Table
    private String bugName;
    private String product;
    private String component;
    private String version;
    private String operSys;

    //BugReport Table
    //private String shortDesc; //no field for this
    private String bugStatus;
    private String keywords;
    private String priority;
    private String bugSev;
    private String longDesc;
    private String assigned;
    private String shortDesc;
    private String reso;
    private int bID;
    private int userRep;
    
    //current userID
    private int reporterID;
    private String reporterName;
    
    public void submitReport() throws SQLException {
        MySQLController conn = new MySQLController();
        try {
            conn.submitReport(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateReport() throws SQLException {
        MySQLController conn = new MySQLController();
        try {
            conn.updateReport(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //NewBugReport class setters

    public void setBugName(String bugName){this.bugName = bugName;}
    public void setProduct(String product){this.product = product;}
    public void setComponent(String component){this.component = component;}
    public void setVersion(String version){this.version = version;}
    public void setOperSys(String operSys){this.operSys = operSys;}
    public void setBugStatus(String bugStatus){this.bugStatus = bugStatus;}
    public void setKeywords(String keywords){this.keywords = keywords;}
    public void setPriority(String priority){this.priority = priority;}
    public void setBugSev(String bugSev){this.bugSev = bugSev;}
    public void setLongDesc(String longDesc){this.longDesc = longDesc;}
    public void setAssigned(String assigned){this.assigned = assigned;}
    public void setReporter(int reporterID){this.reporterID = reporterID;}
    public void setShortDesc(String shortDesc){this.shortDesc = shortDesc;}
    public void setBugID(int bID){this.bID = bID;}
    public void setUserRep(int userRep){this.userRep = userRep;}
    public void setResolution(String reso){this.reso = reso;}
    public void setReporterName(String reporterName){this.reporterName = reporterName;}
    
    
    

    //NewBugReport class getters
    public String getBugName(){return bugName;}
    public String getProduct(){return product;}
    public String getComponent(){return component;}
    public String getVersion() {return version;}
    public String getOperSys() {return operSys;}
    public String getBugStatus(){return bugStatus;}
    public String getKeywords(){return keywords;}
    public String getPriority(){return priority;}
    public String getBugSev(){return bugSev;}
    public String getLongDesc(){return longDesc;}
    public String getAssigned(){return assigned;}
    public int getReporterID(){return reporterID;}
    public String getShortDesc(){return shortDesc;}
    public String getResolution(){return reso;}
    public int getBugID(){return bID;}
    public int getUserRep(){return userRep;}
    public String getReporterName(){return reporterName;}
}
