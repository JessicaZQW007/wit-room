package com.yhxc.utils.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.poi.util.StringUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;


/**
 * @Author: 赵贺飞
 * @Date: 2018/8/17 11:36
 */
public class WaterMarkUtil {

    /**
     * 给图片增加图片水印
     *
     * @param inputImg  -源图片，要添加水印的图片
     * @param markImg   - 水印图片
     * @param outputImg -输出图片(可以是源图片)
     * @param width     - 水印图片宽度
     * @param height    -水印图片高度
     * @param x         -横坐标，相对于源图片
     * @param y         -纵坐标，同上
     */
    public static void mark(String inputImg, String markImg, String outputImg, int width, int height, int x, int y) {
        // 读取原图片信息
        File inputImgFile = null;
        File markImgFile = null;
        Image img = null;
        Image mark = null;
        try {
            if (inputImg != null && markImg != null) {
                inputImgFile = new File(inputImg);
                markImgFile = new File(markImg);
            }
            if (inputImgFile != null && inputImgFile.exists() && inputImgFile.isFile() && inputImgFile.canRead()) {

                img = ImageIO.read(inputImgFile);

            }
            if (markImgFile != null && markImgFile.exists() && markImgFile.isFile() && markImgFile.canRead()) {

                mark = ImageIO.read(markImgFile);

            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            mark(bufImg, img, mark, width, height, x, y);
            FileOutputStream outImgStream = new FileOutputStream(outputImg);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 给图片增加文字水印
     *
     * @param imgPath    -要添加水印的图片路径
     * @param outImgPath -输出路径
     * @param text-文字
     * @param font       -字体
     * @param color      -颜色
     * @param x          -文字位于当前图片的横坐标
     * @param y          -文字位于当前图片的竖坐标
     */
    public static void mark(String imgPath, String outImgPath, String text, Font font, Color color, int x, int y) {
        try {
            // 读取原图片信息
            File imgFile = null;
            Image img = null;
            if (imgPath != null) {
                imgFile = new File(imgPath);
            }
            if (imgFile != null && imgFile.exists() && imgFile.isFile() && imgFile.canRead()) {
                img = ImageIO.read(imgFile);
            }
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            // 加水印
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            mark(bufImg, img, text, font, color, x, y);
            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(outImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 加图片水印
    public static void mark(BufferedImage bufImg, Image img, Image markImg, int width, int height, int x, int y) {
        Graphics2D g = bufImg.createGraphics();

        // 水印坐标
        int wideth_biao = img.getWidth(null);
        int height_biao = img.getHeight(null);
        x = bufImg.getWidth() - wideth_biao;
        y = bufImg.getHeight() - height_biao;

        g.drawImage(img, x, y, bufImg.getWidth(), bufImg.getHeight(), null);
        g.drawImage(markImg, x, y, width, height, null);
        g.dispose();
    }


    // 加文字水印
    public static void mark(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y) {
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, x, y);
        g.dispose();
    }


    /**
     * 把水印印刷到图片上
     *
     * @param pressImg  -- 水印文件
     * @param targetImg -- 目标文件
     * @param loaction  水印位置：left-top：左上角，right-top：右上角，left-bottom：左下角，right-bottom：右下角
     * @param degree    水印旋转角度
     * @time 2015年10月13日
     * @author leon
     */
    public static void pressImage(String pressImg, String targetImg, String loaction, Integer degree) {
        try {
            // 目标文件
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);


            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            // 水印坐标
            int x = 0, y = 0;
            if (loaction.equals("left-top")) {
                g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
            } else if (loaction.equals("right-top")) {
                x = wideth - wideth_biao;
            } else if (loaction.equals("left-bottom")) {
                y = height - height_biao;
            } else if (loaction.equals("right-bottom")) {
                x = wideth - wideth_biao;
                y = height - height_biao;
            } else {
                x = (wideth - wideth_biao) / 2;
                y = (height - height_biao) / 2;
            }
            if (null != degree) {
                // 设置水印旋转
                g.rotate(Math.toRadians(degree), x, y);
            }
            g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder;
            encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
        }
    }




    /**
     * 改变图片的尺寸
     *
     * @param newWidth, newHeight, path
     * @return boolean
     */
    public static boolean changeSize(int newWidth, int newHeight, String path) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "jpg", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static void main(String[] args) {
        String srcImgPath = "D:\\image\\b.jpg"; //源图片地址
        String tarImgPath = "C:\\image\\a.jpg"; //待存储的地址
        String markImg = "D:\\image\\logo.png";

        Font font = new Font("宋体", Font.PLAIN, 55);
        // 原图位置, 输出图片位置, 水印文字颜色, 水印文字
        //mark(srcImgPath, tarImgPath, "水印效果测试", font, Color.ORANGE, 10, 50);
        pressImage(markImg, srcImgPath, "rightottom", 0);

        //图片水印
        // mark(srcImgPath, markImg, tarImgPath, 260, 100, 10, 14);

        //改变图片尺寸
        //changeSize(600, 600,srcImgPath);
    }
}
