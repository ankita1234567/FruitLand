/**********************************************
 * Developer: Ankita Deshmukh

 **********************************************/


package com.fruitland.fruitland.model;

import java.io.Serializable;
import java.util.Hashtable;

public class Purchase_Bean implements Serializable {

    String description;
    String total;
    String date;
    Fruits_Bean fruits_bean;
    Hashtable<Integer,Fruits_Bean> fruits_beanArrayList;

    public  Hashtable<Integer,Fruits_Bean>  getFruits_beanArrayList() {
        return fruits_beanArrayList;
    }

    public void setFruits_beanArrayList( Hashtable<Integer,Fruits_Bean>  fruits_beanArrayList) {
        this.fruits_beanArrayList = fruits_beanArrayList;
    }



    public Fruits_Bean getFruits_bean() {
        return fruits_bean;
    }

    public void setFruits_bean(Fruits_Bean fruits_bean) {
        this.fruits_bean = fruits_bean;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
