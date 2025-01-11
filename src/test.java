package com.kingdee.webapi.javasdk.src;
//从金蝶获取销售订单数据对比发货数量和实际发货数量生成图片，并发送钉钉(测试用）
import com.google.gson.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import java.text.SimpleDateFormat;
import java.util.Date;


class DateUtils1 {
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class test {
    private static final Logger LOGGER = Logger.getLogger(GETSAL.class.getName());

    public static void main(String[] args) {
        K3CloudApi client = new K3CloudApi();
        String startDate = DateUtils1.getStartDate().replaceAll("'", "\\'");
        String endDate = DateUtils1.getEndDate().replaceAll("'", "\\'");
        System.out.println(startDate);
        System.out.println(endDate);
        /*
        FBILLNO 单据编号
        FCREATEDATE  创建时间
        FQTY  销售数量
        FSUMOUTQTY 累计出库数量
        * */

        //定义接口参数 jsonData
        String FormId = "SAL_DELIVERYNOTICE";
        String FieldKeys = "FBILLNO,FCREATEDATE,FDOCUMENTSTATUS,FQTY,FSUMOUTQTY";
        String FilterString = "FCREATEDATE between " +  startDate  + " and " + endDate + " and FDOCUMENTSTATUS = 'C' and FQTY <> FSUMOUTQTY";
        String OrderString = "";
        int TopRowCount = 0;
        int StartRow = 0;
        int EndRow = 0;
        int Limit = 2000;
        String SubSystemId = "";
        // 构造jsonData
        String jsonData = "{\"FormId\":\"" + FormId + "\"," +
                "\"FieldKeys\":\"" + FieldKeys + "\"," +
                "\"FilterString\":\"" + FilterString + "\"," +
                "\"OrderString\":\"" + OrderString + "\"," +
                "\"TopRowCount\":" + TopRowCount + "," +
                "\"StartRow\":" + StartRow + "," +
                "\"EndRow\":" + EndRow + "," +
                "\"Limit\":" + Limit + "," +
                "\"SubSystemId\":\"" + SubSystemId + "\"}";
        System.out.println("接口发送数据: " + jsonData);
        try {
            // 调用接口
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String resultJson = gson.toJson(client.executeBillQuery(jsonData));
            System.out.println("接口返回结果: " + resultJson);
            JsonParser parser = new JsonParser();
            // 解析JSON
            JsonElement jsonElement = parser.parse(resultJson);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<String[]> data = new ArrayList<>();
            // 遍历JSON数组 并将数据添加到List中
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
            //输出文件路径

            System.out.println("生成图片的路径: " + new File(".").getAbsolutePath() + File.separator + "table.png");


                    } catch (Exception e) {
            LOGGER.severe(e.getMessage());

        }
    }

   
}