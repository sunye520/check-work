package com.yogee.youge.interfaces.util;

import java.text.SimpleDateFormat;
import java.util.*;

/*日期工具类
 * cheng
 */
public class DateUtil {

    private final static SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");

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
