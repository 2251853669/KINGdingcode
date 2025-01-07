package com.kingdee.webapi.javasdk.dingding;

import com.google.gson.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateUtils {
    public static String getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -60);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "'" + dateFormat.format(date) + "'";
    }

    public static String getEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "'" + dateFormat.format(new Date()) + "'";
    }
}

public class GETSAL {
    private static final Logger LOGGER = Logger.getLogger(GETSAL.class.getName());

    public static void execute() {
        K3CloudApi client = new K3CloudApi();
        String startDate = DateUtils.getStartDate().replaceAll("'", "\\'");
        String endDate = DateUtils.getEndDate().replaceAll("'", "\\'");
        System.out.println(startDate);
        System.out.println(endDate);

        String FormId = "SAL_DELIVERYNOTICE";
        String FieldKeys = "FBILLNO,FCREATEDATE,FDOCUMENTSTATUS,FQTY,FSUMOUTQTY";
        String FilterString = "FCREATEDATE between " + startDate + " and " + endDate + " and FDOCUMENTSTATUS = 'C' and FQTY <> FSUMOUTQTY";
        String OrderString = "";
        int TopRowCount = 0;
        int StartRow = 0;
        int EndRow = 0;
        int Limit = 2000;
        String SubSystemId = "";

        String jsonData = "{\"FormId\":\"" + FormId + "\"," +
                "\"FieldKeys\":\"" + FieldKeys + "\"," +
                "\"FilterString\":\"" + FilterString + "\"," +
                "\"OrderString\":\"" + OrderString + "\"," +
                "\"TopRowCount\":" + TopRowCount + "," +
                "\"StartRow\":" + StartRow + "," +
                "\"EndRow\":" + EndRow + "," +
                "\"Limit\":" + Limit + "," +
                "\"SubSystemId\":\"" + SubSystemId + "\"}";
/*
        System.out.println("接口发送数据: " + jsonData);
        */
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String resultJson = gson.toJson(client.executeBillQuery(jsonData));
            /*
            System.out.println("接口返回结果: " + resultJson);
            */
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(resultJson);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<String[]> data = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                JsonArray innerArray = element.getAsJsonArray();
                int arraySize = innerArray.size();
                String[] row = new String[arraySize];
                for (int i = 0; i < arraySize; i++) {
                    row[i] = innerArray.get(i).getAsString();
                }
                data.add(row);
            }
            TableToImage.createTableImage(data, "table.png");
            System.out.println("生成图片的路径: " + new File(".").getAbsolutePath() + File.separator + "table.png");

            String urlString = "https://api.xinyew.cn/api/360tc";
            String filePath = "D:\\Senddingcode\\KINGdingcode\\table.png";
            DingTalkNotification.sendDingTalkNotification(urlString, filePath);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }
}