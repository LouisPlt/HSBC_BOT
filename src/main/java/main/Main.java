package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.APIConnector;
import messaging.HSBCJabberClientInstance;
import ml.HSBCSparkInstance;
import ml.Response;


public final class Main {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        HSBCSparkInstance sparkInstance = mapper.readValue(APIConnector.getAllIntents(), HSBCSparkInstance.class);

        sparkInstance.learnAndCreateModel();
        
        HSBCJabberClientInstance jabberInstance = new HSBCJabberClientInstance(sparkInstance,"hsbcbotanswer", "1234");

    }
}