package com.example.user.authentication;

/**
 * Created by USER on 2/8/2018.
 */

public class Doc {
    private String Name;
    private String fees;

    public Doc(){

    }

    public Doc(String name, String fees) {
        this.Name = name;
        this.fees = fees;
    }


    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
