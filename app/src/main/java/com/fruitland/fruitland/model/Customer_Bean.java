/**********************************************
 * Developer: Ankita Deshmukh

 **********************************************/


package com.fruitland.fruitland.model;

import java.io.Serializable;

public class Customer_Bean implements Serializable {

    String name;
    String contact;
    String address;
    String lat;
    int checked;

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    String customer_id;
    public String getDeliveyid() {
        return deliveyid;
    }

    public void setDeliveyid(String deliveyid) {
        this.deliveyid = deliveyid;
    }

    String lng;
    String packages;
    String deliveyid;
    String fruits_avoided;

    public String getFruits_avoided() {
        return fruits_avoided;
    }

    public void setFruits_avoided(String fruits_avoided) {
        this.fruits_avoided = fruits_avoided;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

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


}
