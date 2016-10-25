package projectui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michaelbeavis on 26/10/2016.
 */
public class SearchReports {

    private ObservableList<BugReportTableDetails> reportDetails = FXCollections.observableArrayList();

    public void searchReportsByStatus(String status) throws SQLException {
        MySQLController conn = new MySQLController();
        ResultSet rs =  conn.searchDetailsByStatus(status);

        while (rs.next()) {
            BugReportTableDetails temp = new BugReportTableDetails(rs);
            reportDetails.add(temp);
            //System.out.println(temp.getuserName());

        }
    }
    public void searchReportsByUser(String user) throws SQLException {
        MySQLController conn = new MySQLController();
        ResultSet rs =  conn.searchDetailsByUser(user);

        while (rs.next()) {
            BugReportTableDetails temp = new BugReportTableDetails(rs);
            reportDetails.add(temp);
            //System.out.println(temp.getuserName());

        }
    }
    public void searchReportsByPriority(String priority) throws SQLException {
        MySQLController conn = new MySQLController();
        ResultSet rs =  conn.searchDetailsByPriority(priority);

        while (rs.next()) {
            BugReportTableDetails temp = new BugReportTableDetails(rs);
            reportDetails.add(temp);
            //System.out.println(temp.getuserName());

        }
    }

    public ObservableList<BugReportTableDetails> getReportDetails(){return reportDetails;}
}
