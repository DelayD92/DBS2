package de.hsh.dbs;

public class User {

    //enter dboracleserv username & password
    private String user = "";
    private String pwd = "";


    public User(String user, String pwd){
        this.user = user;
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public String getUser(){
        return user;
    }
}
