package Model;

import java.io.Serializable;
import static watchit.watchit_project.WatchitApp.admins;
import static watchit.watchit_project.WatchitApp.users;

abstract public class Person implements Serializable {
    private static final long serialVesionUID=1L;
    public static int counter = 0;//delete
    long ID;
    String username;
    String password;
    String first_name;
    String last_name;
    String email;


    public Person() {//delete
        counter++;
    }
    abstract public void setID();
    public void setPassword(String password) {// make sure strong password
        this.password = password;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public static boolean isEmailCorrect(String email) {
        if (!email.contains("@") || !email.endsWith(".com")) {
            System.out.println("Invalid email. Must contain '@' and end with '.com'.");
            return false;
        }
        return true;
    }
    public static boolean isEmailUnique(String email) {
        for(User x:users){
            if(x.getEmail().equals(email)){
                return false;
            }
        }
        for(Admin x: admins){
            if(x.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }
    public static boolean searchUsernameIsValid(String username) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {
                System.out.println("This username is already taken. Please choose a different one.");
                return false;
            }
        }
        for (User user :users) {
            if (user.getUsername().equals(username)) {
                System.out.println("This username is already taken. Please choose a different one.");
                return false;
            }
        }
        return true;
    }



    public long getID() {
        return ID;
    }
    public String getUsername() {
        return username;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFull_name(){
        return (this.first_name+' '+this.last_name);
    }

    public void update_username(String s){
        this.setUsername(s);
    }
    public void update_first_name(String s){
        this.setFirst_name(s);
    }
    public void update_last_name(String s){
        this.setLast_name(s);
    }
    public void update_email(String s){
        this.setEmail(s);
    }
    public boolean update_password(String old_pass,String new_pass){
        if(this.getPassword().equals(old_pass)) {
            this.setPassword(new_pass);
            return true;
        }
        else {
            return false;// incorrect old password
        }}


}
