package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class DownloadResultsHandler implements AbstractRequestHandler{
    public void runScript(String SessionId){
        Process process = null;
        try{
            Runtime.getRuntime().exec(new String[]{"pip", "install","matplotlib"});
            Runtime.getRuntime().exec(new String[]{"pip", "install","pandas"});
            Runtime.getRuntime().exec(new String[]{"pip", "install","pymysql"});
            process = Runtime.getRuntime().exec(new String[]{"python", "result.py",SessionId});
        }catch(Exception e) {
            System.out.println("Exception Raised" + e);
        }
        assert process != null;
        InputStream stdout = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
        String line;
        try{
            while((line = reader.readLine()) != null){
                System.out.println("stdout: "+ line);
            }
        }catch(IOException e){
            System.out.println("Exception in reading output"+ e);
        }
    }
    @Override
    public void handle(SimUser user, Object content, RequestResponseEntity rre) {
        runScript(String.valueOf(user.getSessionId()));
        rre
                .setStatusCode(200)
                .setStatusMessage(String.valueOf(user.getSessionId()));
    }
}
