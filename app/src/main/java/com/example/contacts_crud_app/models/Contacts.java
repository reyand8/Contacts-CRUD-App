package com.example.contacts_crud_app.models;

public class Contacts {
    private String firstName;
    private String lastName;
    private String avatar;
    private String email;
    private String id;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }


    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}