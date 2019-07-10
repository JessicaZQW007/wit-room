package com.yhxc.controller.system;

import com.yhxc.entity.equipment.Equipment;
import com.yhxc.service.equipment.EquipmentService;
import com.yhxc.service.project.ProjectService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.StringUtil;
import com.yhxc.utils.Tools;
import com.yhxc.utils.ZipUtil;
import com.yhxc.utils.settings.Setting;
import com.yhxc.utils.settings.SettingUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @Author: 李文光
 * @Date: 2019/5/15 9:38
 * 获取二维码，绑定
 */
@RequestMapping("/binding")
@Controller
public class BindController {
    @Resource
    private EquipmentService equidMentService;
    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;




    /**
     * 批量生成设备二维码
     */
    @RequestMapping("/getQrCodes")
    public void getQrCodes(String eids, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
                Equipment equipment=equidMentService.findByUUID(uuid);
                String content ="uuid="+uuid;
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
}
