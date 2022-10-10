package edu.usc.marshall.centralis22.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Session {
    @Id
    int seid;
    @NotNull
    LocalDate date;
    @NotNull
    int stage;

    public Session(int seid, LocalDate date, int stage) {
        this.seid = seid;
        this.date = date;
        this.stage = stage;
    }

    public Session() {
    }
}
