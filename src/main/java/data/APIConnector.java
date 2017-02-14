package data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by louis on 24/01/17.
 */
public class APIConnector {

    public static String getAllIntents() throws IOException {
        return sendRequest("https://rails-app-cloud.herokuapp.com/intents", "GET", null);
    }


    public static String createUserQuestion(String jsonInString) throws IOException {
        return sendRequest("https://rails-app-cloud.herokuapp.com/feedback", "POST", jsonInString);
    }

    private static String sendRequest(String urlString, String method, String parameters ) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);

        if(method.equals("POST")){
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            mapper.writeValue(wr, parameters);
            wr.flush();
            wr.close();
        }


        int responseCode = conn.getResponseCode();
        System.out.println("\nSending "+ method+" request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return  response.toString();
    }



}
