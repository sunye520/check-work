package com.yogee.youge.interfaces.quartz;

import com.yogee.youge.modules.check.entity.CheckChangeResult;
import com.yogee.youge.modules.check.service.CheckAdjustingPostsService;
import com.yogee.youge.modules.check.service.CheckChangeResultService;
import com.yogee.youge.modules.check.service.CheckDepartmentService;
import com.yogee.youge.modules.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 定时任务
 * Created by Haipeng.Ren on 2017/8/2.
 */
@Service("taskJob")
@Lazy(false)
public class QuartzListener  {

    @Autowired
    private CheckAdjustingPostsService checkAdjustingPostsService;
    @Autowired
    private CheckChangeResultService checkChangeResultService;
    @Autowired
    private DictService dictService;
    @Autowired
    private CheckDepartmentService checkDepartmentService;

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // 定时轮询
//    @Scheduled(cron = "0 0 1 1 * ?")      //每月1号凌晨1点执行
//    @Scheduled(cron = "0 0/10 * * * ?")    //每隔10分钟执行
//    @Scheduled(cron = "0/10 * * * * ?")     //10秒钟
    public synchronized void executeLapsedOrder() {


    }



    /**
     *
     * 生成这个月的统计数据
     */

    @Scheduled(cron = "0 0 1 * * ?")
    public void getToken() {

        Date date1 = new Date();
        String format2 = sdf.format(date1);

        String year = format2.substring(0, 4);
        String month = format2.substring(5, 7);


        Map mapData = new HashMap();

        //组装传递过来的参数  样例：2018-12
        String createDate = year + "-" + month;

        List<CheckChangeResult> listDepartment = null;
        List<CheckChangeResult> listTechnology = null;
        List<CheckChangeResult> listCount = null;

        //获取当前时间
        Date newDate = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM");

        Date date = null;
        try {
            date = format.parse(createDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        //cal.add(Calendar.YEAR, 3);//增加一年
        cal.add(Calendar.MONDAY, -1);//减10天  
        Date time = cal.getTime();
        String formatDate = format.format(time);  //用于查询上个月人数



        //截取当前时间 样例：2018-12
        String format1 = sdf.format(newDate);
        String substring = format1.substring(0, 7);


        /**
         * 根据传递过来的时间进行查询
         */
        //查询当前时间的
        if(createDate.equals(substring)){
            //先查询统计表，如果没有的话 进行拼装（部门）
            listDepartment = checkChangeResultService.queryChangeResultByType(sdf.format(newDate), "1");

            //先查询统计表，如果没有的话 进行拼装（总数）
            listCount = checkChangeResultService.queryChangeResultCount(sdf.format(newDate));
            //先查询统计表，如果没有的话 进行拼装（技术）
            listTechnology = checkChangeResultService.queryChangeResultByType(sdf.format(newDate), "2");

        }else {  //查询历史

            //先查询统计表，如果没有的话 进行拼装（部门）
            listDepartment = checkChangeResultService.queryChangeResultByTypeYM(createDate, "1");
            if (listDepartment == null || listDepartment.size() == 0) {
                List<String> bumenList = checkDepartmentService.findByDepartmentType("1");
                for (String str : bumenList) {
                    CheckChangeResult checkChangeResult = new CheckChangeResult();
                    checkChangeResult.setBumen(str);//部门
                    checkChangeResult.setBenyueRenshu("");//本月
                    checkChangeResult.setShangyuemoRenshu("");//上月
                    checkChangeResult.setRuzhi("");//入职
                    checkChangeResult.setLizhiZhudong("");//主动离职
                    checkChangeResult.setLizhiBeidong("");//被动离职
                    checkChangeResult.setZhuanzheng("");//转正
                    checkChangeResult.setTiaogang("");//调动
                    checkChangeResult.setTiaoxin("");//调薪
                    checkChangeResult.setBeizhu("");//备注
                    checkChangeResult.setType("1");//类型(1-部门，2-技术岗)
                    listDepartment.add(checkChangeResult);
                }
            }

            //先查询统计表，如果没有的话 进行拼装（技术类别）
            listTechnology = checkChangeResultService.queryChangeResultByTypeYM(createDate, "2");
            if (listTechnology == null || listTechnology.size() == 0) {
                List<String> jishuleibieList = dictService.findBytype("jishuleibie");
                for (String str : jishuleibieList) {
                    CheckChangeResult checkChangeResult = new CheckChangeResult();
                    checkChangeResult.setBumen(str);//部门
                    checkChangeResult.setBenyueRenshu("");//本月
                    checkChangeResult.setShangyuemoRenshu("");//上月
                    checkChangeResult.setRuzhi("");//入职
                    checkChangeResult.setLizhiZhudong("");//主动离职
                    checkChangeResult.setLizhiBeidong("");//被动离职
                    checkChangeResult.setZhuanzheng("");//转正
                    checkChangeResult.setTiaogang("");//调动
                    checkChangeResult.setTiaoxin("");//调薪
                    checkChangeResult.setBeizhu("");//备注
                    checkChangeResult.setType("2");//类型(1-部门，2-技术岗)
                    listTechnology.add(checkChangeResult);
                }
            }

            listCount = checkChangeResultService.queryChangeResultCountYM(sdf.format(newDate));
            if (listCount.get(0) == null) {
                listCount = new ArrayList<>();
                CheckChangeResult checkChangeResult = new CheckChangeResult();
                checkChangeResult.setBenyueRenshu("");//本月
                checkChangeResult.setShangyuemoRenshu("");//上月
                checkChangeResult.setRuzhi("");//入职
                checkChangeResult.setLizhiZhudong("");//主动离职
                checkChangeResult.setLizhiBeidong("");//被动离职
                checkChangeResult.setZhuanzheng("");//转正
                checkChangeResult.setTiaogang("");//调动
                checkChangeResult.setTiaoxin("");//调薪
                listCount.add(checkChangeResult);
            }
            mapData.put("listDepartment", listDepartment);
            mapData.put("listTechnology", listTechnology);
            mapData.put("listCount", listCount);

        }
    }
}
