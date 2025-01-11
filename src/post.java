package com.kingdee.webapi.javasdk.src;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class post {

    public static String uploadFileAndGetUrl(String urlString, String filePath) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

            OutputStream os = connection.getOutputStream();
            FileInputStream fis = new FileInputStream(new File(filePath));

            // Writing the file part
            os.write(("\r\n--" + "----WebKitFormBoundary7MA4YWxkTrZu0gW" + "\r\n").getBytes());
            os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + new File(filePath).getName() + "\"\r\n").getBytes());
            os.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());

            // Writing the file data
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            // End of file part
            os.write(("\r\n--" + "----WebKitFormBoundary7MA4YWxkTrZu0gW" + "--\r\n").getBytes());

            fis.close();
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getJSONObject("data").getString("url");
            } else {
                System.out.println("请求失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}