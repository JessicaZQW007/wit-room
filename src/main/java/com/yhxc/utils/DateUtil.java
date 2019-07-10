package com.yhxc.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期工具类
 *
 * @author
 */
public class DateUtil {

    private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat sdfDayAndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");
    private final static SimpleDateFormat sdfHourAndMinutes = new SimpleDateFormat("HH:mm");


    /**
     * 获取HH:mm格式
     *
     * @return
     */
    public static String getHoursAndMinutes() {
        return sdfHourAndMinutes.format(new Date());
    }


    /**
     * 把日期对象根据生成指定格式的字符串
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        String result = "";
        if (date != null) {
            result = sdfTime.format(date);
        }
        return result;
    }

    /**
     * 把日期字符串生成指定格式的日期对象
     *
     * @param str
     * @param format
     * @return
     * @throws Exception
     */
    public static Date formatString(String str, String format) throws Exception {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    /**
     * 生成当前年月日字符串
     *
     * @return
     * @throws Exception
     */
    public static String getCurrentDateStr(){
        Date date = new Date();
        return sdfDays.format(date);
    }


    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getSdfTimes() {
        return sdfTimes.format(new Date());
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return sdfDay.format(new Date());
    }


    /**
     * 获取YYYY-MM-DD格式 前一天的日期
     *
     * @return
     */
    public static String getLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return yesterday;
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return sdfTime.format(new Date());
    }


    public static long getTime(String str){
        long time = 0;
        try {
            time = sdfHourAndMinutes.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 求上个月是几月份
     *
     * @return
     */
    public static String getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        return time;
    }

    /**
     * 求本个月是几月份
     *
     * @return
     */
    public static String getMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        return time;
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
     * @author fh
     */
    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) == null || fomatDate(e) == null) {
            return false;
        }
        return fomatDate(s).getTime() >= fomatDate(e).getTime();
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date fomatDate(String date) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //long aa=0;
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        //System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }


    /**
     * 获取日期和时间
     */
    public static String getDayAndTime(Date date) {
        return sdfDayAndTime.format(date);
    }

    /**
     * 设置日期的指定时间
     *
     * @param date    　日期
     * @param hours   　时
     * @param minutes 　分
     * @param seconds 　秒
     * @return
     */
    public static Date setDate(Date date, int hours, int minutes, int seconds) {
        Date d = DateUtils.setHours(date, hours);
        d = DateUtils.setMinutes(d, minutes);
        d = DateUtils.setSeconds(d, seconds);
        return d;
    }

    /**
     * 设置日期的指定时间
     *
     * @param date         　日期
     * @param hours        　时
     * @param minutes      　分
     * @param seconds      　秒
     * @param milliseconds 毫秒
     * @return
     */
    public static Date setDate(Date date, int hours, int minutes, int seconds, int milliseconds) {
        Date d = DateUtils.setHours(date, hours);
        d = DateUtils.setMinutes(d, minutes);
        d = DateUtils.setSeconds(d, seconds);
        d = DateUtils.setMilliseconds(d, milliseconds);
        return d;
    }

    /**
     * 根据规则格式化日期
     *
     * @param date    　要格式化的日期
     * @param pattern 格式化规则
     * @return
     * @throws Exception
     */
    public static String fomat(Date date, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


    /**
     * 获取精确到秒的时间戳     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }


    public static Boolean compare(String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Boolean b = true;
        try {
            Date bt = sdf.parse(sdfDay.format(new Date()));
            Date et = sdf.parse(endTime);
            if (bt.before(et)) {
                b = true;
            } else {
                b = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b;
    }


//    public static void main(String[] args) {
//        System.out.println(compare("2018-12-20"));
//    }


}
