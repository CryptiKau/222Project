/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Nathan
 */
public class InputValidation {
    
    public boolean isUserName(String uName){
        
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(uName);
        
        if(uName.length() > 20){
            return false;
        }
        else if (matcher.find()){
            return false;
        }
        return true;
    }
    public boolean isEmail(String eMail){
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(eMail);
        
        if(eMail.length() > 40){
            return false;
        }
        else if (matcher.find()){
            return false;
        }
        return true;
    }
    public boolean isFname(String fName){
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(fName);
        
        if(fName.length() > 30){
            return false;
        }
        else if (matcher.find()){
            return false;
        }
        return true;
    }
    public boolean isLname(String lName){
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(lName);
        
        if(lName.length() > 30){
            return false;
        }
        else if (matcher.find()){
            return false;
        }
        return true;
    }
    public boolean isPword(String fName){
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(fName);
        
        if(fName.length() > 16){
            return false;
        }
        else if (matcher.find()){
            return false;
        }
        return true;
    }
}
