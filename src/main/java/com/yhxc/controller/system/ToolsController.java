package com.yhxc.controller.system;

import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.Tools;
import com.yhxc.utils.ZipUtil;
import com.yhxc.utils.mail.SimpleMailSender;
import com.yhxc.utils.settings.Setting;
import com.yhxc.utils.settings.SettingUtils;
import com.yhxc.utils.sms.SmsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @Author: 赵贺飞
 * @Date: 2019/2/28 11:12
 */
@RequestMapping("tool")
@Controller
public class ToolsController {

    /**
     * 获取单个二维码
     *
     * @param response
     */
    @RequestMapping("/getQrCode")
    public void getQrCode(String param, HttpServletResponse response) {
        String content = "http://114.116.168.170:8082/upload/app/midea.apk";
        String note = "扫码下载APP";
        File logoFile = new File("D://xc-icon.jpg");
        File codeFile = new File("D://QrCode.jpg");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Tools.drawLogoQRCode(logoFile, codeFile, content, note);
        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量生成设备二维码
     */
    @RequestMapping("/getQrCodes")
    public void getQrCodes(String eids, HttpServletRequest req, HttpServletResponse res) throws Exception {
        String url = "http://192.168.1.129/binding/bind?uuid=";
        String note = "";
        Setting _setting = SettingUtils.get();
        String filePath = _setting.getFileRoot();
        String markImg = filePath + "/img/xc-icon.jpg";
        String codeAddr = filePath + "/" + "QrCodes";
        File f = new File(codeAddr);
        if (!f.exists()) {
            f.mkdirs();
        }
        File logoFile = new File(markImg);
        File codeFile = null;
        if (StringUtil.isNotEmpty(eids)) {
            String eidStr[] = eids.split(",");
            for (int i = 0; i < eidStr.length; i++) {
                String uuid = eidStr[i].trim();
                codeFile = new File(codeAddr + "/" + uuid + ".jpg");
                String content = url + uuid;
                note = uuid;
                Tools.drawLogoQRCode(logoFile, codeFile, content, note);
            }
            ZipUtil.downloadFilesTest(req, res);
            if (f.exists()) {
                f.delete();
            }

            if (f.isDirectory()) {//它是一个目录
                File[] files = f.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {//遍历目录下所有的文件
                    files[i].delete();//把每个文件用这个方法进行迭代
                }
                f.delete();//删除文件夹
            }

            //删除缓存的zip
            File zip = new File(filePath + "/" + "QrCode.zip");
            if (zip.exists()) {
                zip.delete();
            }
        }
    }


    /**
     * 短信提醒
     *
     * @param phone
     * @param content
     * @param session
     * @return
     */
    @RequestMapping("/sendMsg")
    @ResponseBody
    public ResultInfo sendMsg(String phone, String content, HttpSession session) {
        String info = "";
        if (Tools.checkMobileNumber(phone)) {
            String ret1 = SmsUtil.sendSms1(phone, content);
            if ("100".equals(ret1)) {
                info = "发送成功！";
            } else {
                info = "发送失败！失败代码：" + ret1;
            }
        } else {
            info = "手机号格式不正确!";
        }
        return new ResultInfo(StatusCode.SUCCESS, info);
    }


    /**
     * 邮件提醒
     *
     * @param email
     * @param title
     * @param content
     * @param attachmentPath 添加附件的全路径，包括附件名
     * @return
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public ResultInfo sendEmail(String email, String title, String content, String attachmentPath) {
        String info = "";
        if (Tools.checkEmail(email)) {
            try {
                info = SimpleMailSender.sendEmail(email, title, content, attachmentPath);
            } catch (Exception e) {
                e.printStackTrace();
                info = "发送失败！";
            }
        } else {
            info = "邮箱格式不正确！";
        }
        return new ResultInfo(StatusCode.SUCCESS, info);
    }
}
