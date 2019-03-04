package com.example.pumpkinsoftware.travelmate.user;

public class User {
    private String name;
    private String photo_profile;

    public User(String n, String pp){
        name=n;
        photo_profile=pp;
    }

    public String getName(){
        return name;
    }
    public String getPhoto_profile(){
        return photo_profile;
    }
}
