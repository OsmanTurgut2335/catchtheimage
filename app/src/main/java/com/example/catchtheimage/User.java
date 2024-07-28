package com.example.catchtheimage;


import com.google.firebase.database.PropertyName;

public class User {
    @PropertyName("name")
    public String name;
    @PropertyName("score")
    public int score;



 public  User(){

 }

    public User(String name , int score){

        this.name = name;
        this.score=score;


    }

    public User setUser(User user ){
      user.setName(user.name);
      user.setScore(user.score);

     return user;

    }


    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

