/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package miniProject;

/**
 *
 * @author Francis
 */
public class Account {
    int ID;
    String userName;
    String password;
    
    public String getUserName() { return userName;}
    public void setUserName(String value) {userName = value;}
    public void setPassword(String value) {password = value;}
    public String getPassword() {return password;}
    public int getID() {return ID; }
    public void setID(int ID){this.ID=ID;}
}