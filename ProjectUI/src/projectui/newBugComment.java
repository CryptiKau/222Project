/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

/**
 *
 * @author Nathan
 */
public class newBugComment {

    
    int userID;
    int bugRepID;
    String CommentText;
    
    public void setNewComment(int uID, int brID, String cmntText){
    MySQLController DBConn = new MySQLController();
    userID = uID;
    bugRepID = brID;
    CommentText = cmntText;
    
    DBConn.registerNewComment(this);
    }
    
    public int getUserID() {
        return userID;
    }

    public int getBugRepID() {
        return bugRepID;
    }

    public String getCommentText() {
        return CommentText;
    }
}
