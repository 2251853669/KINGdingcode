package com.kingdee.webapi.javasdk.dingding;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.util.UUID;
import static org.junit.Assert.fail;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class DateUtils {
        public static String getStartDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -21);
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
    public class GETSAL {
        private static final Logger LOGGER = Logger.getLogger(GETSAL.class.getName());

        public static void main(String[] args) {
            K3CloudApi client = new K3CloudApi();
            String startDate = DateUtils.getStartDate().replaceAll("'", "\\'");
            String endDate = DateUtils.getEndDate().replaceAll("'", "\\'");
            System.out.println(startDate);
            System.out.println(endDate);
        /*
        FBILLNO 单据编号
        FCREATEDATE  创建时间
        FQTY  销售数量
        FSUMOUTQTY 累计出库数量
        * */

            String jsonData = "{\"FormId\":\"SAL_DELIVERYNOTICE\",\"FieldKeys\":\"FBILLNO,FCREATEDATE,FDOCUMENTSTATUS,FQTY,FSUMOUTQTY\",\"FilterString\":\"FCREATEDATE between \" +  startDate  + \" and \" + endDate + \" and FDOCUMENTSTATUS = 'C' and FQTY <> FSUMOUTQTY\",\"OrderString\":\"\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":2000,\"SubSystemId\":\"\"}";
            System.out.println("接口发送数据: " + jsonData);
            try {
                // 调用接口
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String resultJson = gson.toJson(client.executeBillQuery(jsonData));
                System.out.println("接口返回结果: " + resultJson);
                // 处理返回结果
                resultJson = resultJson.replaceAll("\"", "");
                System.out.println("处理后的数据: " + resultJson);
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(resultJson);
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                List<String[]> data = new ArrayList<>();

                for (JsonElement element : jsonArray) {
                    JsonArray innerArray = element.getAsJsonArray();
                    String[] row = new String[5];
                    row[0] = innerArray.get(0).getAsString(); // 获取第一个元素
                    row[1] = innerArray.get(1).getAsString(); // 获取第二个元素
                    row[2] = innerArray.get(2).getAsString();
                    row[3] = innerArray.get(3).getAsString();
                    data.add(row);
                }
                String newResultJson = new Gson().toJson(data);
                System.out.println("生成图片的数据: " + newResultJson);

                // 生成图片
                createImage(data, "output.png");
                //输出图片全路径
                System.out.println("生成图片的路径: " + new File(".").getAbsolutePath() + File.separator + "output.png");

            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
                fail(e.getMessage());
            }
        }

        public static void createImage(List<String[]> data, String filePath) {
            // 自适应宽度
            int width = 1000;
            // 每行高度自适应根据字体自动变化
            int rowHeight = 30;

            // 图片高度比数据总行数高 10% 的高度
            int height = (int) (data.size() * 1.5 * rowHeight);



            // 创建一个空白的BufferedImage对象
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            // 设置背景色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);

            // 设置字体和颜色
            g2d.setFont(new Font("宋体", Font.PLAIN, 20));
            g2d.setColor(Color.BLACK);


            // 绘制表头
            String header = "编号 | 创建时间 | 状态 | 销售数 | 已发数";
            g2d.drawString(header, 10, 20); // 表头的y坐标为20
            // 绘制每一行数据
            int y = 40; // 初始y坐标
            for (String[] row : data) {
                String text = row[0] + " | " + row[1] + " | " + row[2] + " | " + row[3];
                g2d.drawString(text, 10, y);
                y += rowHeight;
            }

            // 释放资源
            g2d.dispose();

            // 将BufferedImage写入文件
            try {
                ImageIO.write(bufferedImage, "png", new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }