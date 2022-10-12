package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimSession;
import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CreateSessionHandler implements RequestHandler {

    private SessionRepository sessr;

    /**
     * Loads all existing session IDs from DB. Generate a new unique ID.
     *
     * <p> It will require 500k sessions to be created to reach 50% collision.
     * Even then, 10 retries is sufficient for a 99.9% guarantee of a new ID.
     * TODO: Remove IDs over a certain date.
     */
    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {
        List<Integer> seids = sessr.getAllSeid();

        int seid = -1;
        while(seid == -1) {
            int randSeid = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
            if(!seids.contains(randSeid)) {
                seid = randSeid;
            }
        }

        SimSession simSession = new SimSession(seid, LocalDate.now(), 0);
        sessr.save(simSession);

        rre
                .setStatusCode(200)
                .setContent(seid);
    }

    @Autowired
    public void setSessr(SessionRepository sessr) {
        this.sessr = sessr;
    }
}