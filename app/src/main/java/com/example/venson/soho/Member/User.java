package com.example.venson.soho.Member;

import java.sql.Date;

public class User {

    private int userId;
    private String email;
    private String password;
    private Date registerTime;
    private boolean isFBRegistered;
    private String userName;
    private String userLINE;
    private String userSelfDes;
    private boolean userGender;//0=F,1=M //false=F,true=M
    private String userPicPath;





    public User(String email, String password, String userName, boolean userGender) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.userGender = userGender;
    }

    public User() {
    }

    public User(int userId, String userName, String userLINE, String userSelfDes, boolean userGender) {
        this.userId = userId;
        this.userName = userName;
        this.userLINE = userLINE;
        this.userSelfDes = userSelfDes;
        this.userGender = userGender;
    }

    public User(int userId, String email, String password, Date registerTime, boolean isFBRegistered, String userName,
                String userLINE, String userSelfDes, boolean userGender, String userPicPath) {
        super();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.registerTime = registerTime;
        this.isFBRegistered = isFBRegistered;
        this.userName = userName;
        this.userLINE = userLINE;
        this.userSelfDes = userSelfDes;
        this.userGender = userGender;
        this.userPicPath = userPicPath;
    }

    //email regex
    //"/^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})*$/"
    public User(int userId, String email, Date registerTime, boolean isFBRegistered, String userName, String userLINE,
                String userSelfDes, boolean userGender, String userPicPath) {
        super();
        this.userId = userId;
        this.email = email;
        this.registerTime = registerTime;
        this.isFBRegistered = isFBRegistered;
        this.userName = userName;
        this.userLINE = userLINE;
        this.userSelfDes = userSelfDes;
        this.userGender = userGender;
        this.userPicPath = userPicPath;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isFBRegistered() {
        return isFBRegistered;
    }

    public void setFBRegistered(boolean isFBRegistered) {
        this.isFBRegistered = isFBRegistered;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLINE() {
        return userLINE;
    }

    public void setUserLINE(String userLINE) {
        this.userLINE = userLINE;
    }

    public String getUserSelfDes() {
        return userSelfDes;
    }

    public void setUserSelfDes(String userSelfDes) {
        this.userSelfDes = userSelfDes;
    }

    public boolean isUserGender() {
        return userGender;
    }

    public void setUserGender(boolean userGender) {
        this.userGender = userGender;
    }

    public String getUserPicPath() {
        return userPicPath;
    }

    public void setUserPicPath(String userPicPath) {
        this.userPicPath = userPicPath;
    }


}


//public class User {
//    private int id;
//    private String password;
//    private String name;
//    private String email;
//    private int gender;
//    private  String phone;
//    private  String live;
//    private  String line;
//    private  String expertise;
//    private  String workexperience;
//
//    public User(int id) {
//        this.id = id;
//    }
//
//    public User(int id, String password, String name, String email, int gender, String line) {
//        this.id = id;
//        this.password = password;
//        this.name = name;
//        this.email = email;
//        this.gender = gender;
//        this.line = line;
//    }
//
//    public User(int id, String password, String name, String email) {
//        super();
//        this.id = id;
//        this.password = password;
//        this.name = name;
//        this.email = email;
//    }
//
//    public User(String email, String password, String name,int gender) {
//        super();
//
//        this.password = password;
//        this.name = name;
//        this.email = email;
//        this.gender = gender;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public int getGender() {
//        return gender;
//    }
//
//    public void setGender(int gender) {
//        this.gender = gender;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getLive() {
//        return live;
//    }
//
//    public void setLive(String live) {
//        this.live = live;
//    }
//
//    public String getLine() {
//        return line;
//    }
//
//    public void setLine(String line) {
//        this.line = line;
//    }
//
//    public String getExpertise() {
//        return expertise;
//    }
//
//    public void setExpertise(String expertise) {
//        this.expertise = expertise;
//    }
//
//    public String getWorkexperience() {
//        return workexperience;
//    }
//
//    public void setWorkexperience(String workexperience) {
//        this.workexperience = workexperience;
//    }
