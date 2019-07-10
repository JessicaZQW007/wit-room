package com.yhxc.utils;

import com.yhxc.utils.image.ImgCompressUtil;
import com.yhxc.utils.image.WaterMarkUtil;
import com.yhxc.utils.settings.Setting;
import com.yhxc.utils.settings.SettingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 上传工具类
 *
 * @author zhaohf
 */
public class UploadUtil {
    private static final Log log = LogFactory.getLog(UploadUtil.class);


    /**
     * @param uploadFile
     * @param folder     文件所在文件夹
     * @return
     * @throws IOException
     */
    public static String upload(MultipartFile uploadFile, String folder) throws IOException {
        if (uploadFile == null) {
            return "error";
        }
        if (uploadFile.getSize() < 10) {
            return null;
        }
        try {
            String webPath = null;
            String uuid = UuidUtil.get32UUID();
            String uploadFileName = uploadFile.getOriginalFilename();
            log.info("上传的文件名称：" + uploadFileName.toString());
            //截取文件后缀名称suffix
            String suffix = "";
            if (uploadFileName.lastIndexOf(".") != -1) {
                suffix = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
            }
            Setting _setting = SettingUtils.get();
            String savePath = _setting.getWebFileRoot();
            savePath = savePath + "/" + folder;
            if (FileUtil.checkFolderLegal(savePath)) {
                String newFileName = uuid + suffix;
                FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(savePath, newFileName));
                String wenFileRootUrl = _setting.getWebFileRootUrl();
                webPath = wenFileRootUrl + "/" + folder + "/" + newFileName;
                log.info(webPath);
            }
            return webPath;
        } catch (Exception e) {
            log.error("文件上传异常：" + e.getMessage().toString());
            return (e.getMessage().toString());
        }
    }


    /**
     * 压缩之后的图片上传方法
     *
     * @param uploadFile 上传的文件
     * @param folder 文件存放的最近文件夹
     * @param request
     * @return
     * @throws IOException
     */
    public static String uploadImgCompress(MultipartFile uploadFile, String folder, HttpServletRequest request) throws IOException {
        if (uploadFile == null || uploadFile.getSize() < 10) {
            return "error";
        }
        //在根目录下创建一个tempfileDir的临时文件夹
        String contextpath = request.getSession().getServletContext().getRealPath("/") + "/tempfileDir";
        File f = new File(contextpath);
        if (!f.exists()) {
            f.mkdirs();
        }
        //在tempfileDir的临时文件夹下创建一个新的和上传文件名一样的文件
        String filename = uploadFile.getOriginalFilename();
        String filepath = contextpath + "/" + filename;
        File newFile = new File(filepath);
        uploadFile.transferTo(newFile);
        if (StringUtil.isEmpty(filename)) {
            return "error";
        }
        String webPath = null;
        String uuid = UuidUtil.get32UUID();
        log.info("上传的文件名称：" + filename);
        //截取文件后缀名称
        String suffix = "";
        if (filename.lastIndexOf(".") != -1) {
            suffix = filename.substring(filename.lastIndexOf("."), filename.length());
        }
        Setting _setting = SettingUtils.get();
        String savePath = _setting.getWebFileRoot();
        String filePath = _setting.getFileRoot();
        String wenFileRootUrl = _setting.getWebFileRootUrl();
        savePath = savePath + "/" + folder;
        if (FileUtil.checkFolderLegal(savePath)) {
            String newFileName = uuid + suffix;
            savePath = savePath + "/" + newFileName;
            webPath = wenFileRootUrl + "/" + folder + "/" + newFileName;
            log.info("web访问路径：" + webPath);
        }
        //执行压缩
        ImgCompressUtil imgCom = new ImgCompressUtil(newFile);
        imgCom.resizeFix(1200, 1200, savePath);
        //添加水印
        String markImg = filePath + "/img/logo.png";
        WaterMarkUtil.pressImage(markImg, savePath, "right-bottom", -45);
        //设置大小
        //WaterMarkUtil.changeSize(800, 600, savePath);
        //删除上面创建的临时文件
        if (newFile.exists()) {
            newFile.delete();
        }
        return webPath;
    }


    /**
     * 获取内部本地文件，不可给外部访问
     *
     * @param file 文件名。如果有文件夹，用/隔开
     */
    public static String input(String file) {
        Setting _setting = SettingUtils.get();
        String fontPath = _setting.getFileRoot() + "/" + file;
        log.info(fontPath);
        return fontPath;
    }

    /**
     * @param uploadFile
     * @param folder     文件上传本地
     * @return
     * @throws IOException
     */
    public static String uploadLocal(MultipartFile uploadFile, String folder) throws IOException {

        if (uploadFile == null) {
            return ("error");
        }
        try {

            String webPath = null;
            String uploadFileName = uploadFile.getOriginalFilename();
            log.info("上传的文件名称：" + uploadFileName.toString());
            // 截取文件后缀名称suffix
            String savePath = Const.fileRoot + folder;
            if (FileUtil.checkFolderLegal(savePath)) {
                String newFileName = uploadFileName;
                FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), new File(savePath, newFileName));
                webPath = savePath + "/" + newFileName;
                log.info(webPath);
            }
            return (webPath);
        } catch (Exception e) {
            log.error("图片上传异常：" + e.getMessage().toString());
            return (e.getMessage().toString());
        }
    }


    /**
     * 上传，获取
     *
     * @param uploadFile
     * @return
     */
    public static String uploadFile(MultipartFile uploadFile, String folder) {
        String webpath = null;
        if (uploadFile != null) {
            if (uploadFile.getSize() > 10) {
                try {
                    webpath = upload(uploadFile, folder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return webpath;
    }
}
