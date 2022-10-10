package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.Session;
import edu.usc.marshall.centralis22.repository.SessionRepository;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CreateSessionHandler implements RequestHandler {

    @Autowired
    private SessionRepository sessr;

    @Override
    public void handle(Object content, RequestResponseEntity rre) {
        List<Integer> seids = sessr.getAllSeid();

        int seid = -1;
        while(seid == -1) {
            int randSeid = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
            if(!seids.contains(randSeid)) {
                seid = randSeid;
            }
        }

        Session session = new Session(seid, LocalDate.now(), 0);
        sessr.save(session);

        rre
                .setStatusCode(200)
                .setContent(seid);
    }
}
