package projectui;

/**
 * Created by michaelbeavis on 17/10/2016.
 */
public class NewUser {

    private String userName;
    private String fName;
    private String lName;
    private String email;
    private String passWord;
    private int AccessLevel;


    //added a second caller function that can set the access level
    public void setNewUserAdmin(String uName, String first, String last, String mail, String pWord, int nAsclvl){
        MySQLController DBConn = new MySQLController();
        userName = uName;
        fName = first;
        lName = last;
        email = mail;
        passWord = pWord;
        AccessLevel = nAsclvl; 

        DBConn.registerNewUserAdmin(this);


    }
    
    public void setNewUser(String uName, String first, String last, String mail, String pWord){
        MySQLController DBConn = new MySQLController();
        userName = uName;
        fName = first;
        lName = last;
        email = mail;
        passWord = pWord;

        DBConn.registerNewUser(this);
      

    }

    public String getUserName(){return userName;}

    public String getfName() {return fName;}

    public String getlName() {return lName;}

    public String getEmail() {return email;}

    public String getPassWord() {return passWord;}
    
    public int getAccessLevel() {return AccessLevel;}
}
