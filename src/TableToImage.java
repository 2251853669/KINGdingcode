package com.kingdee.webapi.javasdk.src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TableToImage {

    public static void createTableImage(List<String[]> data, String filePath) throws IOException {
        // 定义表格的宽度
        int width = 800;
        // 根据行数计算高度，每行高度为30像素，加上标题的高度
        int height = 30 * (data.size() + 1) + 50;

        // 创建图像
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景颜色
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置字体和颜色
        Font font = new Font("微软雅黑", Font.BOLD, 16);
        g2d.setFont(font);
        g2d.setPaint(Color.BLACK);

        // 绘制标题
        String title = "未发货单据通知";
        FontMetrics fm = g2d.getFontMetrics();
        int titleWidth = fm.stringWidth(title);
        g2d.drawString(title, (width - titleWidth) / 2, 30);

        // 设置字体和颜色为表头和数据
        font = new Font("宋体", Font.PLAIN, 12);
        g2d.setFont(font);
        g2d.setPaint(Color.BLACK);

        // 定义表头
        String[] headers = {"单据号", "创建日期", "单据状态", "销售数量", "累计出库数量"};
        int cellWidth = width / headers.length;
        int cellHeight = 30; // 每行高度固定为30像素

        // 绘制表格线
        for (int i = 0; i <= headers.length; i++) {
            g2d.drawLine(i * cellWidth, 50, i * cellWidth, height);
        }
        for (int i = 0; i <= data.size() + 1; i++) {
            g2d.drawLine(0, 50 + i * cellHeight, width, 50 + i * cellHeight);
        }

        // 绘制表头文本
        for (int i = 0; i < headers.length; i++) {
            g2d.drawString(headers[i], i * cellWidth + 5, 70);
        }

        // 绘制数据文本
        for (int i = 0; i < data.size(); i++) {
            String[] rowData = data.get(i);
            for (int j = 0; j < rowData.length; j++) {
                g2d.drawString(rowData[j], j * cellWidth + 5, 70 + (i + 1) * cellHeight);
            }
        }

        // 释放资源
        g2d.dispose();

        // 保存图片
        ImageIO.write(image, "png", new File(filePath));
    }
}
