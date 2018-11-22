package HTTP;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequester {

    public String sendGetRequest(String urlParams, FileWriter fileWriter) throws IOException {

        String url = "http://i2j.openode.io";
        url = url + urlParams;
        URL server = new URL(url);
        HttpURLConnection con = (HttpURLConnection) server.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        // System.out.println("\nSending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();

    }
}
