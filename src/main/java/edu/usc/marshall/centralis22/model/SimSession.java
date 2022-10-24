package edu.usc.marshall.centralis22.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "session")
public class SimSession {
    @Id
    int seid;
    @NotNull
    LocalDate date;
    @NotNull
    int stage;

    public SimSession(int seid, LocalDate date, int stage) {
        this.seid = seid;
        this.date = date;
        this.stage = stage;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getSeid() {
        return seid;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public SimSession() {
    }
}
