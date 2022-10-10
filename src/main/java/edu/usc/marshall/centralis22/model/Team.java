package edu.usc.marshall.centralis22.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int tmid;
    @NotNull
    int seid;
    @NotNull
    int teamName;

    public Team(int tmid, int seid, int teamName) {
        this.tmid = tmid;
        this.seid = seid;
        this.teamName = teamName;
    }

    public Team() {
    }
}
