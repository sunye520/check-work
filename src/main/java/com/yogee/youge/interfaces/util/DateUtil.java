package com.yogee.youge.interfaces.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*日期工具类
 * cheng
 */
public class DateUtil {

    private final static SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 根据起止日期取年份
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getYear(Date startDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        // 获取年的差值 
        return year1 - year2;
    }

    /**
     * 根据起止日期取月份
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMonth(Date startDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startDate);
        c2.setTime(endDate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2; // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2)
            yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2)
            monthInterval--;
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }

    /**
     * 获取指定月份全部日期
     *
     * @param year
     * @param month
     * @return
     */
    public static List<Map<String, String>> getMonthFullDay(int year, int month) {
        List<Map<String, String>> fullDayList = new ArrayList<Map<String, String>>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month - 1);
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count; j++) {
            Map map = new HashMap();
            map.put("date", dateFormatYYYYMMDD.format(cal.getTime()));
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                map.put("status", "1");
            } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                map.put("status", "1");
            } else {
                map.put("status", "0");
            }
            fullDayList.add(map);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return fullDayList;
    }

    public static void main(String[] args) {
//        List<Map<String, String>> list = getMonthFullDay(2018, 12);
//        System.out.println(list);
        try {
            int  age = getAge(parse("1992-10-11"));           //由出生日期获得年龄***
            System.out.println("age:"+age);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据生日算年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    public static  Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }
}
