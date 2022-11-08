package edu.usc.marshall.centralis22.controller;


import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class FileController {
    @GetMapping("/downloadFile/{sessionid}")
    public ResponseEntity<InputStreamResource> download(@PathVariable(name = "sessionid") String sessionID) throws Exception {
        File abc = new File("a");
        String absolutePath = abc.getAbsolutePath();
        absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        String path = absolutePath + "\\zipped results\\" + sessionID + ".zip";
        String last = absolutePath.substring(absolutePath.length() - 1);
        path = path.replace(last, last + last);
        FileSystemResource file = new FileSystemResource(path);
        HttpHeaders httpHeaders = new HttpHeaders();
        String headerVal = "attachment; filename=" + sessionID + ".zip";
        httpHeaders.add("Content-Disposition", headerVal);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }
}
