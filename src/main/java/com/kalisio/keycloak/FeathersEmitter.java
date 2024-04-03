package com.kalisio.keycloak;

//import java.io.BufferedReader;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
// import java.net.MalformedURLException;
import java.net.URL;

public class FeathersEmitter {

    public static void postEvent(String data) {
        try {

            URL url = new URL("https://613933731fcce10017e78a68.mockapi.io/api/v1/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            OutputStream os = conn.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
// just development
//            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}