/**********************************************
 * Developer: Ankita Deshmukh

 **********************************************/


package com.fruitland.fruitland.model;

import java.io.Serializable;

public class Feedback_Bean implements Serializable {

    String name;
    String contact;
    String rating;
    String comment;
    String packages;

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
