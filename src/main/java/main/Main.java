package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.APIConnector;
import io.netty.handler.codec.http.HttpResponse;
import messaging.HSBCJabberClientInstance;
import ml.HSBCSparkInstance;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public final class Main {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        HSBCSparkInstance sparkInstance = mapper.readValue(APIConnector.getAllIntents(), HSBCSparkInstance.class);

        sparkInstance.learnAndCreateModel();
        
        HSBCJabberClientInstance jabberInstance = new HSBCJabberClientInstance(sparkInstance,"hsbcbotanswer", "1234");
    }
}