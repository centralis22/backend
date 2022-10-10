package edu.usc.marshall.centralis22.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Team {
    @Id
    // TODO: Gary fix database generation
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int tmid;
    @NotNull
    int seid;
    @NotNull
    String teamName;

    public Team(int tmid, int seid, String teamName) {
        this.tmid = tmid;
        this.seid = seid;
        this.teamName = teamName;
    }

    public Team() {
    }
}
