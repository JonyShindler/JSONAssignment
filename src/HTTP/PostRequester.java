package HTTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostRequester {

    public String sendPostRequest(String url, String urlParameters) throws IOException {

        String serverUrl = "http://i2j.openode.io";
        String finalUrl = serverUrl+url;
        URL obj = new URL(finalUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + finalUrl + ", with parameters " + urlParameters + ", returned response code " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        // System.out.println("Post request response " + response.toString());
        return response.toString();
    }
}
