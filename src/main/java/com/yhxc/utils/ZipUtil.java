package com.yhxc.utils;


import com.yhxc.utils.settings.Setting;
import com.yhxc.utils.settings.SettingUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: 赵贺飞
 * @Date: 2018/9/15 10:12
 * 文件压缩
 */
public class ZipUtil {

    //普通java文件下载方法，适用于所有框架
    public static void downloadFilesTest(HttpServletRequest request, HttpServletResponse res) throws IOException {
        //获取文件根目录，不同框架获取的方式不一样，可自由切换
        //String basePath = request.getSession().getServletContext().getRealPath("/fileDir");


        Setting _setting = SettingUtils.get();
        String filePath = _setting.getFileRoot();
        String basePath = filePath +"/"+ "QrCodes";



        //获取文件名称（包括文件格式）
        String fileName = "1.jpg";

        //组合成完整的文件路径
        String targetPath = basePath + File.separator;

        //模拟多一个文件，用于测试多文件批量下载
        String targetPath1 = basePath + File.separator + "2.jpg";
        //模拟文件路径下再添加个文件夹，验证穷举
        String targetPath2 = basePath + File.separator + "test";

        System.out.println("文件名：" + fileName);
        System.out.println("文件路径：" + targetPath);

        //IO流实现下载的功能
        res.setContentType("text/html; charset=UTF-8"); //设置编码字符
        res.setContentType("application/octet-stream"); //设置内容类型为下载类型
        res.setHeader("Content-disposition", "attachment;filename=" + fileName);//设置下载的文件名称
        OutputStream out = res.getOutputStream();   //创建页面返回方式为输出流，会自动弹出下载框

        //创建压缩文件需要的空的zip包
        String zipName = "QrCode.zip";
        String zipFilePath =  filePath +"/"+ zipName;
        //创建需要下载的文件路径的集合
        List<String> filePaths = new ArrayList<String>();
        filePaths.add(basePath);
        //filePaths.add(targetPath1);
        //filePaths.add(targetPath2);

        //压缩文件
        File zip = new File(zipFilePath);
        if (!zip.exists()) {
            zip.createNewFile();
        }
        //创建zip文件输出流
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
        zipFile(filePaths, zos);
        zos.close();
        res.setHeader("Content-disposition", "attachment;filename=" + zipName);//设置下载的压缩文件名称

        //将打包后的文件写到客户端，输出的方法同上，使用缓冲流输出
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilePath));
        byte[] buff = new byte[bis.available()];
        bis.read(buff);
        bis.close();
        out.write(buff);//输出数据文件
        out.flush();//释放缓存
        out.close();//关闭输出流
        //return null;
    }

    /**
     * 压缩文件
     *
     * @param filePaths   需要压缩的文件路径集合
     * @throws IOException
     */
    private static String zipFile(List<String> filePaths, ZipOutputStream zos) throws IOException {
        //循环读取文件路径集合，获取每一个文件的路径
        for (String filePath : filePaths) {
            File inputFile = new File(filePath);  //根据文件路径创建文件
            if (inputFile.exists()) { //判断文件是否存在
                if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹
                    //创建输入流读取文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));

                    //将文件写入zip内，即将文件进行打包
                    zos.putNextEntry(new ZipEntry(inputFile.getName()));

                    //写入文件的方法，同上
                    int size = 0;
                    byte[] buffer = new byte[1024];  //设置读取数据缓存大小
                    while ((size = bis.read(buffer)) > 0) {
                        zos.write(buffer, 0, size);
                    }
                    //关闭输入输出流
                    zos.closeEntry();
                    bis.close();
                } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    try {
                        File[] files = inputFile.listFiles();
                        List<String> filePathsTem = new ArrayList<String>();
                        for (File fileTem : files) {
                            filePathsTem.add(fileTem.toString());
                        }
                        return zipFile(filePathsTem, zos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
