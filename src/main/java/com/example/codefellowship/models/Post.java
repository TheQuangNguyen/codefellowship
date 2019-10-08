package com.example.codefellowship.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Post {

    String body;
    Timestamp createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    ApplicationUser applicationUser;

    public Post() {}

    public Post(String body, ApplicationUser applicationUser) {
        this.body = body;
        this.applicationUser = applicationUser;
        this.createdAt = new Timestamp(new Date().getTime());
    }


    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }
}
