package com.yogee.youge.interfaces.util;

import java.text.SimpleDateFormat;
import java.util.*;

/*日期工具类
 * cheng
 */
public class DateUtil {

    private final static SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 根据起止日期取年份
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getYear(Date startDate,Date endDate){
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
     * @param year
     * @param month
     * @return
     */
    public static List<Map<String,String>> getMonthFullDay(int year , int month){
        List<Map<String,String>> fullDayList = new ArrayList<Map<String,String>>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            Map map = new HashMap();
            map.put("date",dateFormatYYYYMMDD.format(cal.getTime()));
            if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
                map.put("status","1");
            }else if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                map.put("status","1");
            }else {
                map.put("status","0");
            }
            fullDayList.add(map);
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }

    public static void main(String[] args) {
        List<Map<String,String>> list = getMonthFullDay(2018,12);
        System.out.println(list);
    }
}
