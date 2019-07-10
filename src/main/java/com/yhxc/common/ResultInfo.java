package com.yhxc.common;

/**
 * 返回到前端的结果类
 *
 * @author 赵贺飞
 */
public class ResultInfo {

    private int statuscode = 1;             //状态码，默认1
    private String info = "操作成功！";
    private Object data;                    //返回到前端的数据
    private Object page;                    //分页

    private Object qx;

    public ResultInfo(int statuscode, String info, Object data,Object page, Object qx) {
        this.statuscode = statuscode;
        this.info = info;
        this.data = data;
        this.page = page;
        this.qx = qx;
    }


    public ResultInfo(int statuscode, String info, Object data) {
        this.statuscode = statuscode;
        this.info = info;
        this.data = data;
    }

    /**
     * 返回封装的数据
     *
     * @param statuscode 状态编码
     * @param info    错误提示的信息
     * @param data    业务数据
     * @param page    分页对象当前信息
     */
    public ResultInfo(int statuscode, String info, Object data, Object page) {

        this.statuscode = statuscode;
        this.info = info;
        this.data = data;
        this.page = page;
    }

    public ResultInfo(int statuscode, String info) {
        this.statuscode = statuscode;
        this.info = info;
    }

    public ResultInfo(Object data) {
        this.info = "查询成功！";
        this.data = data;
    }

    public ResultInfo(int statuscode) {
        this.statuscode = statuscode;
        this.info = "操作失败！";
    }

    public ResultInfo(String info) {
        this.statuscode = 0;
        this.info = info;
    }

    public ResultInfo() {

    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Object getQx() {
        return qx;
    }

    public void setQx(Object qx) {
        this.qx = qx;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "statuscode=" + statuscode +
                ", info='" + info + '\'' +
                ", data=" + data +
                ", page=" + page +
                '}';
    }
}
