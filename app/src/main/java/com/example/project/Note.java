package com.example.project;

import com.google.firebase.database.Exclude;

public class Note {

    private String documentId;
    private String title;
    private String description;
    private String user;
    public Note() {
        //public no-arg constructor needed
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

//    public void setUser(String user)
//    {this.user = user;}
//
//    public void getUser(String user)
//    {this.user = user;}

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
