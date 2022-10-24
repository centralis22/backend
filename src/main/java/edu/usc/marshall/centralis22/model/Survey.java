package edu.usc.marshall.centralis22.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int suid;
    @NotNull
    int tmid;
    @NotNull
    int seid;
    @NotNull
    int svgrp;

    String q1;
    String q2;
    String q3;
    String q4;
    String q5;

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    public Survey(
            int suid, int tmid, int seid, int svgrp,
            String q1, String q2, String q3, String q4, String q5) {
        this.suid = suid;
        this.tmid = tmid;
        this.seid = seid;
        this.svgrp = svgrp;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
    }

    public Survey(int suid, int tmid, int seid, int svgrp) {
        this.suid = suid;
        this.tmid = tmid;
        this.seid = seid;
        this.svgrp = svgrp;
    }

    public Survey() {
    }
}
