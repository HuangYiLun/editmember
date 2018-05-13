package com.example.venson.soho;
import java.sql.Date;

public class UserExp {
    private int expId;
    private int userId;
    private String expDes;
    private Date lastEditTime;
    private Date startDate;
    private Date endDate;
    public UserExp(int expId, int userId, String expDes, Date lastEditTime, Date startDate, Date endDate) {
        super();
        this.expId = expId;
        this.userId = userId;
        this.expDes = expDes;
        this.lastEditTime = lastEditTime;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public int getExpId() {
        return expId;
    }
    public void setExpId(int expId) {
        this.expId = expId;
    }
    public int getUserid() {
        return userId;
    }
    public void setUserid(int userid) {
        this.userId = userid;
    }
    public String getExpDes() {
        return expDes;
    }
    public void setExpDes(String expDes) {
        this.expDes = expDes;
    }
    public Date getLastEditTime() {
        return lastEditTime;
    }
    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }



}
