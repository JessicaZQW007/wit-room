package com.yhxc.utils.image;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.awt.*;
import java.awt.image.*;
import java.util.List;
import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.*;

/**
 * @Author: 赵贺飞
 * @Date: 2018/8/16 16:00
 */
public class ImgCompress {
    private Image img;
    private int width;
    private int height;

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        System.out.println("开始：" + new Date().toLocaleString());

        List<File> listFile = readFile("D:\\img\\about");
        for(File f : listFile){
            String file = f.toString();
            ImgCompress imgCom = new ImgCompress(file);
            String outFile = file.replaceAll("D", "C");
            imgCom.resizeFix(1000, 1000, outFile);
        }

        System.out.println("结束：" + new Date().toLocaleString());
    }

    /**
     * 构造函数
     */
    public ImgCompress(String fileName) throws IOException {
        File file = new File(fileName);// 读入文件
        img = ImageIO.read(file);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }

    /**
     * 按照宽度还是高度进行压缩
     *
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public void resizeFix(int w, int h, String disk) throws IOException {
        if (width / height > w / h) {
            resizeByWidth(w, disk);
        } else {
            resizeByHeight(h, disk);
        }
    }

    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w int 新宽度
     */
    public void resizeByWidth(int w, String disk) throws IOException {
        int h = (int) (height * w / width);
        resize(w, h, disk);
    }

    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h int 新高度
     */
    public void resizeByHeight(int h, String disk) throws IOException {
        int w = (int) (width * h / height);
        resize(w, h, disk);
    }

    /**
     * 强制压缩/放大图片到固定的大小
     *
     * @param w int 新宽度
     * @param h int 新高度
     */
    public void resize(int w, int h, String disk) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File(disk);
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }


    /**
     * 读取文件夹下的文件列表
     * @param fileDir
     */
    private static List<File> readFile(String fileDir) {
        List<File> fileList = new ArrayList<File>();
        File file = new File(fileDir);
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
        if (files == null) {// 如果目录为空，直接退出
            return null;
        }

        // 遍历，目录下的所有文件
        for (File f : files) {
            if (f.isFile()) {
                fileList.add(f);
            } else if (f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
                readFile(f.getAbsolutePath());
            }
        }
        for (File f1 : fileList) {
            System.out.println(f1.getName());
        }
        return fileList;
    }
}
