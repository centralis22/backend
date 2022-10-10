package edu.usc.marshall.centralis22.util;

import edu.usc.marshall.centralis22.model.Session;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Used for backend self testing only.
 */
@Service
public class OnStartTest {

    @Autowired
    SessionRepository sr;

    @EventListener(ApplicationReadyEvent.class)
    public void createDummySession() {
        Session s = new Session(424200, LocalDate.now(), 1);
        sr.save(s);
        Session t = new Session(424201, LocalDate.now(), 2);
        sr.save(t);

        List<Integer> seids = sr.getAllSeid();
        for(Integer seid : seids) {
            System.out.println(seid);
        }
    }
}
