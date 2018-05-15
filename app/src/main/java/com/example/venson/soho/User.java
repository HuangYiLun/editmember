package com.example.venson.soho;

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
	private boolean userGender;
	//0=F,1=M 
	//false=F,true=M
	
	private String userPicPath;
//	private String userlive;

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
	public User(int userId,String email, Date registerTime, boolean isFBRegistered, String userName, String userLINE,
			String userSelfDes, boolean userGender, String userPicPath,String userlive) {
		super();
		this.userId = userId;
		this.email=email;
		this.registerTime = registerTime;
		this.isFBRegistered = isFBRegistered;
		this.userName = userName;
		this.userLINE = userLINE;
		this.userSelfDes = userSelfDes;
		this.userGender = userGender;
		this.userPicPath = userPicPath;
//		this.userlive = userlive;
	}

	public User(int userId, String email, Date registerTime, boolean isFBRegistered, String userName, String userLINE, String userSelfDes, boolean userGender, String userPicPath) {
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

//	public String getUserlive() {
//		return userlive;
//	}
//
//	public void setUserlive(String userlive) {
//		this.userlive = userlive;
//	}
}
