package edu.bzu.group_assignment1_1170271_1172738.Models;

public class User {
    private Integer id;
    private String emailAddress;
    private String hashedPassword;
    private String firstName;
    private String lastName;


    public User() {
    }

    public User(Integer id, String emailAddress, String hashedPassword, String firstName,
                String lastName) {

        this.id = id;
        this.emailAddress = emailAddress;
        this.hashedPassword = hashedPassword;
        this.firstName = firstName;
        this.lastName = lastName;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", passwordHash='" + hashedPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
