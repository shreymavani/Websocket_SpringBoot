package com.cloudera.liveDatadogWebsocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class FileController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/file")   //Not required just to send a file.If you want to any argument from the UI and send it to the function then only it should be set.
    @SendTo("/topic/files")
    public String sendFile() throws Exception {
        // Load the text file from the local system
        System.out.println("inside the sendfile");
        File file = new File("/Users/smavani/INPUT_OUTPUT_FOR_TESTING/YarnJobData/Sample.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
        }
        reader.close();
        String fileData = stringBuilder.toString();

        // Send the file data to the subscribed clients
        this.template.convertAndSend("/topic/file", fileData);
        return fileData;
    }

}

