package de.hsh.dbs;

public class User {

    private String username ="tnn-867-u1";
    private String password ="testit2_";

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
