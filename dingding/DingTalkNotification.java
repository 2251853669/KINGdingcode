package com.kingdee.webapi.javasdk.dingding;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DingTalkNotification {

    private static final String WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send?access_token=c4de998d88fcec1c249c6712693278f293a30359620371dbd9ba30b97a8cd4ca"; // 请替换为你的Webhook URL

    public static void main(String[] args) {
        String urlString = "https://api.xinyew.cn/api/360tc";
        String filePath = "D:\\Senddingcode\\KINGdingcode\\table.png";
        String newurl = post.uploadFileAndGetUrl(urlString, filePath);
        if (newurl != null) {
            System.out.println("URL: " + newurl);
        } else {
            System.out.println("Failed to upload file and get URL.");
        }
        // 替换为实际的图片URL,实际url为post类输出的newurl
        String imageUrl = newurl ;
        String message = createMessage(imageUrl);
        sendNotification(message);
    }

    private static String createMessage(String imageUrl) {
        return "{ \"msgtype\": \"markdown\", " +
                "\"markdown\": { " +
                "\"title\": \"图片通知\", " +
                "\"text\": \"![java 钉钉通知发送图片_java](" + imageUrl + ")\" } }";
    }

    private static void sendNotification(String message) {
        try {
            URL url = new URL(WEBHOOK_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = message.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("通知发送成功!");
            } else {
                System.out.println("通知发送失败! 响应码: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}