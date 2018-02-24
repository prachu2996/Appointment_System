package com.example.user.authentication;

import java.io.Serializable;

/**
 * Created by USER on 2/23/2018.
 */

public class DoctorList implements Serializable {

   public String name;



    public DoctorList(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DoctorList(String name) {
        this.name = name;

    }
}
