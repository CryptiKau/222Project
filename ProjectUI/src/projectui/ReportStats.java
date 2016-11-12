package projectui;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michaelbeavis on 26/10/2016.
 */
public class ReportStats {

    private int range; //this will determine week/monthly/half-year/year counts

    //bug report tallies
    private String newBugReports;
    private String reportsAssigned;
    private String reportsUnassigned;

    //bug status tallies
    private String reportedBugs;
    private String progressingBugs;
    private String solvedBugs;

    //bug priority tallies
    private String lowPriority;
    private String medPriority;
    private String highPriority;
    private String emergencyPriority;

    //bug severity tallies
    private String cosmeticSev;
    private String minorSev;
    private String majorSev;
    private String criticalSev;

    public ReportStats(int r) throws SQLException {
        range = r;
        MySQLController conn = new MySQLController();
        ResultSet rs;
        rs = conn.getNewBugReports(range);
        if(rs.next()){
            newBugReports = rs.getString(1);
        }

        rs = conn.getReportsAssigned(range);
        if(rs.next()){
            reportsAssigned = rs.getString(1);
        }

        rs = conn.getReportsUnassigned(range);
        if(rs.next()){
            reportsUnassigned = rs.getString(1);
        }

        rs = conn.getReportedBugs(range);
        if(rs.next()){
            reportedBugs = rs.getString(1);
        }

        rs = conn.getProgressingBugs(range);
        if(rs.next()){
            progressingBugs = rs.getString(1);
        }

        rs = conn.getSolvedBugs(range);
        if(rs.next()){
            solvedBugs = rs.getString(1);
        }
        
        rs = conn.getLowPriority(range);
        if(rs.next()){
            lowPriority = rs.getString(1);
        }

        rs = conn.getMedPriority(range);
        if(rs.next()){
            medPriority = rs.getString(1);
        }

        rs = conn.getHighPriority(range);
        if(rs.next()){
            highPriority = rs.getString(1);
        }

        rs = conn.getEmergencyPriority(range);
        if(rs.next()){
            emergencyPriority = rs.getString(1);
        }

        rs = conn.getCosmeticSev(range);
        if(rs.next()){
            cosmeticSev = rs.getString(1);
        }

        rs = conn.getMinorSev(range);
        if(rs.next()){
            minorSev = rs.getString(1);
        }

        rs = conn.getMajorSev(range);
        if(rs.next()){
            majorSev = rs.getString(1);
        }

        rs = conn.getCriticalSev(range);
        if(rs.next()){
            criticalSev = rs.getString(1);
        }

        
    }


    //ReportStats getters
    public String getNewBugReports(){return newBugReports;}
    public String getReportsAssigned(){return reportsAssigned;}
    public String getReportsUnassigned(){return reportsUnassigned;}
    public String getReportedBugs(){return reportedBugs;}
    public String getProgressingBugs(){return progressingBugs;}
    public String getSolvedBugs(){return solvedBugs;}
    public String getLowPriority(){return lowPriority;}
    public String getMedPriority(){return medPriority;}
    public String getHighPriority(){return highPriority;}
    public String getEmergencyPriority(){return emergencyPriority;}
    public String getCosmeticSev(){return cosmeticSev;}
    public String getMinorSev(){return minorSev;}
    public String getMajorSev(){return majorSev;}
    public String getCriticalSev(){return criticalSev;}
}
