/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Nathan
 */
public class SearchComments {
    
    private ObservableList<BugComments> commentDetails = FXCollections.observableArrayList();

    public void searchCommentDetails(int search) throws SQLException {
        MySQLController conn = new MySQLController();
        ResultSet rs =  conn.searchComments(search);

        while (rs.next()) {
            BugComments temp = new BugComments(rs);
            commentDetails.add(temp);

        }
        conn.closeConnection();
    }
    
    public ObservableList<BugComments> getCommentDetails(){return commentDetails;}
}
