package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import messaging.HSBCJabberClientInstance;
import ml.HSBCSparkInstance;

import java.io.File;


public final class Main {

    public static void main(String[] args) throws Exception {

        /**Create HSBCSparkInstance from json of intents**/
        ObjectMapper mapper = new ObjectMapper();
        HSBCSparkInstance sparkInstance = mapper.readValue(new File("files/intents.json"), HSBCSparkInstance.class);

        sparkInstance.learnAndCreateModel();
        
        HSBCJabberClientInstance jabberInstance = new HSBCJabberClientInstance(sparkInstance,"hsbcbotanswer", "1234");
    }
}