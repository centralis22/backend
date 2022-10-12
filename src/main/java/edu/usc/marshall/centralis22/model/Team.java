package edu.usc.marshall.centralis22.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tmid;
    @NotNull
    int seid;
    @NotNull
    @Column(name = "teamname")
    String teamName;

    public Team(int tmid, int seid, String teamName) {
        this.tmid = tmid;
        this.seid = seid;
        this.teamName = teamName;
    }

    public Team() {
    }
}
