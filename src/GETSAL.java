package com.kingdee.webapi.javasdk.src;

import com.google.gson.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateUtils {
    public static String getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -100);
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
    private static final String PROCESSED_DATA_FILE = "processed_data.txt";

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

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String resultJson = gson.toJson(client.executeBillQuery(jsonData));
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(resultJson);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<String[]> newData = new ArrayList<>();
            Set<String> processedData = readProcessedData();

            for (JsonElement element : jsonArray) {
                JsonArray innerArray = element.getAsJsonArray();
                String billNo = innerArray.get(0).getAsString();
                if (!processedData.contains(billNo)) {
                    int arraySize = innerArray.size();
                    String[] row = new String[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        row[i] = innerArray.get(i).getAsString();
                    }
                    newData.add(row);
                    processedData.add(billNo);
                }
            }

            if (!newData.isEmpty()) {
                TableToImage.createTableImage(newData, "table.png");
                System.out.println("生成图片的路径: " + new File(".").getAbsolutePath() + File.separator + "table.png");

                String urlString = "https://api.xinyew.cn/api/360tc";
                String filePath = "table.png";
                DingTalkNotification.sendDingTalkNotification(urlString, filePath);

                writeProcessedData(processedData);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

    }

    private static Set<String> readProcessedData() {
        Set<String> processedData = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PROCESSED_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processedData.add(line);
            }
        } catch (FileNotFoundException e) {
            LOGGER.warning("已处理数据文件不存在，将创建新文件: " + PROCESSED_DATA_FILE);
        } catch (IOException e) {
            LOGGER.severe("读取已处理数据文件时发生错误: " + e.getMessage());
        }
        return processedData;
    }

    private static void writeProcessedData(Set<String> processedData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROCESSED_DATA_FILE))) {
            for (String billNo : processedData) {
                writer.write(billNo);
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.severe("写入已处理数据文件时发生错误: " + e.getMessage());
        }
    }

    public static void clearProcessedData() {
        try {
            new File(PROCESSED_DATA_FILE).delete();
            LOGGER.info("已处理数据文件已清理: " + PROCESSED_DATA_FILE);
        } catch (Exception e) {
            LOGGER.severe("清理已处理数据文件时发生错误: " + e.getMessage());
        }
    }
}