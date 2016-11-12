/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Nathan
 */
public class BugComments {
    
    private final StringProperty createdDate;
    private final StringProperty commentText;
    private final StringProperty commenter;
    
    public BugComments(ResultSet rs) throws SQLException {
        this.createdDate = new SimpleStringProperty(rs.getString(1));
        this.commentText = new SimpleStringProperty(rs.getString(2));
        this.commenter = new SimpleStringProperty(rs.getString(3));

    }
    
    public String getCreatedDate(){return createdDate.get();}
    public String getCommentText(){return commentText.get();}
    public String getCommenter(){return commenter.get();}
    
}
