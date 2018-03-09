package com.example.user.authentication;

/**
 * Created by USER on 2/25/2018.
 */

public class DocDetail {

    private String name;
    private String email;
    private String dob;
    private String mob;
    private String time;
    private String location;
    private String gender;
    private String fees;
    private String id;
    private String avg_time;




    public DocDetail(String name, String email, String dob, String mob, String time, String location, String gender, String fees, String id) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.mob = mob;
        this.time = time;
        this.location = location;
        this.gender = gender;
        this.fees = fees;

        this.id = id;


    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DocDetail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
