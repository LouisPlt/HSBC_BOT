package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by louis on 24/01/17.
 */
public class APIConnector {

    public static String getAllIntents() throws IOException {
        return sendRequest("https://hsbc2.herokuapp.com/intents", "GET").toString();
    }

    public static StringBuffer sendRequest(String url, String method) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending "+method+" request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

}
