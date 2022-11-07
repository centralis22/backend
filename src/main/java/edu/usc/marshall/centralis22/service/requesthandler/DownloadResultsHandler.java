package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DownloadResultsHandler implements AbstractRequestHandler{

    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {
        System.out.println("here!");
        rre
                .setStatusCode(200)
                .setStatusMessage("https://www.7-zip.org/a/7z2201-x64.exe");
    }
}
