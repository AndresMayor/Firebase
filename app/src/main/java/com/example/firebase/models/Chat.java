package com.example.firebase.models;

public class Chat {

    private String id;
    private String contactID;

    public Chat() {
    }

    public Chat(String id, String contactID) {
        this.id = id;
        this.contactID = contactID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}
