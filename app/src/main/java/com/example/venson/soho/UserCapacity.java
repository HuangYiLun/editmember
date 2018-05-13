package com.example.venson.soho;

public class UserCapacity {

    private int userCapacityId;
    private int userId;
    private Category c;
    public UserCapacity(int userCapacityId, int userId, Category c) {
        super();
        this.userCapacityId = userCapacityId;
        this.userId = userId;
        this.c = c;
    }
    public int getUserCapacityId() {
        return userCapacityId;
    }
    public void setUserCapacityId(int userCapacityId) {
        this.userCapacityId = userCapacityId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Category getC() {
        return c;
    }
    public void setC(Category c) {
        this.c = c;
    }



}
