package de.hsh.dbs;

public class User {

    private String username ="";
    private String password ="";

    public User(String usr, String pwd) {
        this.username = usr;
        this.password = pwd;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }
}
