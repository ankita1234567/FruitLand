/**********************************************
 * Developer: Ankita Deshmukh

 **********************************************/


package com.fruitland.fruitland.model;

import java.io.Serializable;

public class Fruits_Bean implements Serializable {

    String fruit_id;
    String quantity;
    String rate;
    String unit;
    String total;


    public String getFruit_id() {
        return fruit_id;
    }

    public void setFruit_id(String fruit_id) {
        this.fruit_id = fruit_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }




}
