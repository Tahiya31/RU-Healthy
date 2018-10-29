package com.example.softwareengineering;

/**
 * Created by HIMABINDU on 12/1/2017.
 */

public class UserInformation {

    String id;
    String name;
    String email;
    String age;
    String gender;
    String height;
    String weight;
    String target;
    String heartRate;


    public UserInformation()
    {

    }

    public UserInformation(String id, String name, String email, String age, String gender, String height, String weight, String target, String heartRate)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender =gender;
        this.height = height;
        this.weight = weight;
        this.target = target;
        this.heartRate = heartRate;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getTarget() {
        return target;
    }

    public String getHeartRate() {
        return heartRate;
    }
}


