package com.example.venson.soho.Case.Caseapply;

/**
 * Created by ricky on 2018/5/10.
 */

public class Case {
    private int id;
    private String casename;
    private String casecontent;

    public Case(int id, String casename, String casecontent) {
        this.id = id;
        this.casename = casename;
        this.casecontent = casecontent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getCasecontent() {
        return casecontent;
    }

    public void setCasecontent(String casecontent) {
        this.casecontent = casecontent;
    }
}
