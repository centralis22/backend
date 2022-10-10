package edu.usc.marshall.centralis22.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Instructor {
    @Id
    // TODO: Fix database generation
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int inid;
    @NotNull
    @Column(name = "uname")
    String userName;
    @NotNull
    @Column(name = "pswd")
    String password;

    public int getInid() {
        return inid;
    }

    public void setInid(int inid) {
        this.inid = inid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instructor(int inid, String userName, String password) {
        this.inid = inid;
        this.userName = userName;
        this.password = password;
    }

    public Instructor() {
    }
}
