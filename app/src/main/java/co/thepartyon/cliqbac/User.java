package co.thepartyon.cliqbac;

/**
 * Created by asymkowick on 3/6/16.
 */
public class User {
    int userId;
    String username;
    String phoneNumber;
    String firstName;
    String lastName;


    //Default Constructor
    public User() {
        this.firstName = "JOHN Q.";
        this.lastName = "SAMPLE";
    }

    public String getFullName() {
        return (this.firstName + " " + this.lastName);
    }





    /**********************/
    /** Getters/Setters **/
    /**********************/
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
