package com.example.appfirebase.Class;

public class ChefClass {
    private String fname;
    private String lname;
    private String emailid;
    private String password;
    private String confermpassword;
    private String mobile;
    private String house;
    private String area;


    public ChefClass() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfermpassword() {
        return confermpassword;
    }

    public void setConfermpassword(String confermpassword) {
        this.confermpassword = confermpassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }



    public ChefClass(String fname, String lname, String emailid, String password, String confermpassword, String mobile, String house, String area) {
        this.fname = fname;
        this.lname = lname;
        this.emailid = emailid;
        this.password = password;
        this.confermpassword = confermpassword;
        this.mobile = mobile;
        this.house = house;
        this.area = area;

    }
}
