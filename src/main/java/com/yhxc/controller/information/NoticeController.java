package com.yhxc.controller.information;

import com.yhxc.common.Constant;
import com.yhxc.common.ResultInfo;
import com.yhxc.common.StatusCode;
import com.yhxc.entity.company.Organization;
import com.yhxc.entity.information.Notice;
import com.yhxc.entity.system.Log;
import com.yhxc.entity.system.User;
import com.yhxc.service.company.OrganizationService;
import com.yhxc.service.information.NoticeService;
import com.yhxc.service.system.LogService;
import com.yhxc.service.system.UserService;
import com.yhxc.utils.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 公告 Controller
 */

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private LogService logService;
    @Resource
    private NoticeService noticeService;

    /**
     * 保存公告
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ResultInfo save(MultipartFile uploadFile, Notice notice) throws Exception {
        User user = Jurisdiction.getCurrentUser();

        if (StringUtil.isNotEmpty(notice.getId())) {
            Notice n = noticeService.findById(notice.getId());
            if (uploadFile != null && uploadFile.getSize() > 10) {
                FileUtil.deleteDir(n.getAttachment(), Const.fileRoot + "notice");
                notice.setAttachment(UploadUtil.uploadFile(uploadFile, "notice"));
            } else {
                String img = n.getAttachment();
                if(StringUtil.isNotEmpty(img)){
                    notice.setAttachment(img);
                }
            }
            notice.setUserName(n.getUserName());
        } else {

            notice.setId(UuidUtil.get32UUID());
            notice.setUserName(user.getUserName());
            notice.setAttachment(UploadUtil.uploadFile(uploadFile, "notice"));
        }
        noticeService.save(notice);
        logService.save(new Log(Log.ADD_ACTION, "添加新的公告"));
        return new ResultInfo(StatusCode.SUCCESS, "发布成功！");
    }

    /**
     * 分页查询所有公告信息（带条件查询）
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ResultInfo pageList(Notice notice, Integer pageNum, Integer pageSize) {
        return new ResultInfo(StatusCode.SUCCESS, "查询成功！", noticeService.listAll(notice, pageNum, pageSize, Sort.Direction.DESC, "startTime"), null, Jurisdiction.getQX());
    }


    @RequestMapping(value = "/tree")
    @ResponseBody
    public ResultInfo tree() {
        return new ResultInfo(StatusCode.SUCCESS, "加载成功！", noticeService.listZzTree());
    }


    @ResponseBody
    @RequestMapping("/findById")
    public ResultInfo findByUid(String id) {
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", noticeService.findById(id));
    }


    /**
     * 发布公告
     * @param state
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/editState")
    public ResultInfo editState(Integer state, String id) {
        noticeService.editState(state,id);
        return new ResultInfo(StatusCode.SUCCESS, "发布成功！");
    }


    /**
     * 删除公告信息
     *
     * @param id
     * @return
     * @throws Exception
     */

    @RequestMapping("/del")
    @ResponseBody
    public ResultInfo delete(String id) throws Exception {
        Notice notice = noticeService.findById(id);
        String webAddr = notice.getAttachment();
        FileUtil.deleteDir(webAddr, Constant.fileRoot + "notice");// 更新时，删除之前的文件
        logService.save(new Log(Log.DELETE_ACTION, "删除公告信息" + noticeService.findById(id))); // 写入日志
        noticeService.deleteById(id);

        return new ResultInfo(StatusCode.SUCCESS, "删除成功！");
    }


    @RequestMapping("/dels")
    @ResponseBody
    public ResultInfo deleteBea(String ids) throws Exception {
        if (StringUtil.isNotEmpty(ids)) {
            String[] idStr = ids.split(",");
            for (int i = 0; i < idStr.length; i++) {
                Notice notice = noticeService.findById(idStr[i]);
                String webAddr = notice.getAttachment();
                FileUtil.deleteDir(webAddr, Constant.fileRoot + "notice");// 更新时，删除之前的文件
                logService.save(new Log(Log.DELETE_ACTION, "删除公告信息" + notice)); // 写入日志
                noticeService.deleteById(idStr[i]);

            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "success");
    }


    /**
     * 用来标识该用户已查看该公告
     *
     * @param id 公告id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updReadCount")
    @ResponseBody
    public ResultInfo goView(String id) throws Exception {
        Notice notice = noticeService.findById(id);
        // 返回公告详情时，把该用户放入数据库，用来判断该用户是否阅读这条公告
        String Flag = notice.getFlag();
        String userName = Jurisdiction.getUserName();
        String[] flags = null;
        int num = 0;        //浏览次数
        if (StringUtil.isNotEmpty(Flag)) {
            flags = Flag.split(",");
            num = flags.length;
            int a = 0;
            for (String name : flags) {
                if (userName.equals(name)) {
                    a++;
                }
            }
            if (a == 0) {
                noticeService.editFlag(Flag + userName + ",", id);
            }
        } else {
            noticeService.editFlag(userName + ",", id);
        }
        return new ResultInfo(StatusCode.SUCCESS, "查看成功！", num);
    }


    /**
     * 查询公告未读数量
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findMyNo")
    @ResponseBody
    public ResultInfo findMyNo() throws Exception {
        String user = Jurisdiction.getUserName();
        List<Notice> listPd = noticeService.findNewList();
        List<Notice> newListPd = new ArrayList<Notice>();
        for (Notice data : listPd) {
            String reci = data.getRecipient();
            String flag = data.getFlag();
            if (StringUtil.isNotEmpty(reci)) {
                String[] reciList = reci.split(",");
                int a = 0;
                for (String s : reciList) {
                    if (s.equals(user)) {
                        a++;
                    }
                }
                //当前用户可以查看该公告
                if (a != 0) {
                    if (StringUtil.isNotEmpty(flag)) {
                        String[] flags = flag.split(",");
                        int b = 0;
                        for (String s : flags) {
                            if (s.equals(user)) {
                                b++;
                            }
                        }
                        //当前用户没查看过该公告
                        if (b == 0) {
                            newListPd.add(data);
                        }
                    } else {
                        newListPd.add(data);
                    }
                }
            } else {
                if (StringUtil.isNotEmpty(flag)) {
                    String[] flags = flag.split(",");
                    int b = 0;
                    for (String s : flags) {
                        if (s.equals(user)) {
                            b++;
                        }
                    }
                    //当前用户没查看过该公告
                    if (b == 0) {
                        newListPd.add(data);
                    }
                } else {
                    newListPd.add(data);
                }
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "666", newListPd.size());
    }

    /**
     * 查看最新的通知
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/findNew")
    @ResponseBody
    public ResultInfo findNew() throws Exception {
        List<Notice> listPd = noticeService.findNewList();      //查询未过期的公告
        List<Map<String, Object>> listMap1 = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> listMap2 = new ArrayList<Map<String, Object>>();
        String user = Jurisdiction.getUserName();
        //拿到当前用户可以看到的公告数据
        if (listPd != null && listPd.size() > 0) {
            for (int i = 0; i < listPd.size(); i++) {
                Notice pd = listPd.get(i);
                String reci = pd.getRecipient();
                int x = 0;
                if (StringUtil.isNotEmpty(reci)) {
                    String[] re = reci.split(",");
                    for (String s : re) {
                        if (StringUtil.isNotEmpty(s)) {
                            if (user.equals(s)) {
                                x++;
                            }
                        }
                    }
                    //证明这条数据登录者可以看到
                    if (x != 0) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("notice", pd);
                        map.put("isMe", "个人");
                        listMap1.add(map);
                    }
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("notice", pd);
                    map.put("isMe", "公共");
                    listMap1.add(map);
                }
            }
        }

        //通过上面当前用户可以看到的数据，判断该条数据是否已被查看
        for (int i = 0; i < listMap1.size(); i++) {
            Map no = listMap1.get(i);
            Notice n = (Notice) no.get("notice");
            String flag = n.getFlag();
            if (StringUtil.isNotEmpty(flag)) {
                String[] flags = flag.split(",");
                int b = 0;
                for (String u : flags) {
                    if (user.equals(u)) {
                        b++;
                    }
                }
                if (b == 0) {
                    //没看过
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.putAll(m(no));
                    map.put("flag", 0);
                    map.put("num", flags.length);
                    listMap2.add(map);
                } else {
                    //证明已看过
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.putAll(m(no));
                    map.put("flag", 1);
                    map.put("num", flags.length);
                    listMap2.add(map);
                }
            } else {
                //没看过
                Map<String, Object> map = new HashMap<String, Object>();
                map.putAll(m(no));
                map.put("flag", 0);
                map.put("num", 0);
                listMap2.add(map);
            }
        }
        return new ResultInfo(StatusCode.SUCCESS, "SUCCESS", listMap2);
    }

    public Map<String, Object> m(Map no) {
        Map<String, Object> map = new HashMap<>();
        Notice n = (Notice) no.get("notice");
        map.put("isMe", no.get("isMe"));
        map.put("id", n.getId());
        map.put("title", n.getTitle());
        map.put("content", n.getContent());
        map.put("attachment", n.getAttachment());
        map.put("startTime", n.getStartTime());
        map.put("stopTime", n.getStopTime());
        map.put("userName", n.getUserName());
        //map.put("isAll", n.getIsAll());
        map.put("recipient", n.getRecipient());
        return map;
    }

}
