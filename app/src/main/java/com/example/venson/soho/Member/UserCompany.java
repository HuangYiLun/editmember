package com.example.venson.soho.Member;

public class UserCompany extends Company {
    private int userCompanyId;
    private int userId;



    public UserCompany(int userCompanyId, int userId, int companyId, int uniformNumber, String name, double latitude,
                       double longitude) {
        super(companyId, uniformNumber, name, latitude, longitude);
        this.userCompanyId = userCompanyId;
        this.userId = userId;

    }

    public UserCompany() {
        super();
    }

    public int getUserCompanyId() {
        return userCompanyId;
    }

    public void setUserCompanyId(int userCompanyId) {
        this.userCompanyId = userCompanyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



}
