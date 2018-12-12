package com.yogee.youge.interfaces.check;


import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.DateUtil;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckTime;
import com.yogee.youge.modules.check.entity.CheckWorkDate;
import com.yogee.youge.modules.check.service.CheckTimeService;
import com.yogee.youge.modules.check.service.CheckWorkDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考勤模块
 * cheng
 */


@Controller
@RequestMapping("${apiPath}")
public class ClockingInInterface {

    private static final Logger logger = LoggerFactory.getLogger(ClockingInInterface.class);
    private static final SimpleDateFormat sdf;
    private static final SimpleDateFormat sdf1;

    @Autowired
    private CheckTimeService checkTimeService;
    @Autowired
    private CheckWorkDateService checkWorkDateService;

    static {
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf1 = new SimpleDateFormat("yyyy-MM");
    }

    /**
     * 修改上班时间
     * @param req
     * @return
     */
    @RequestMapping(value = "updateWorkTime",method = RequestMethod.POST)
    @ResponseBody
    public String updateWorkTime(HttpServletRequest req){
        logger.info("app updateWorkTime---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String forenoonTime = (String)jsonData.get("forenoonTime");//00:00-00:00
        String afternoonTime = (String)jsonData.get("afternoonTime");
        CheckTime checkTime = checkTimeService.getOne();
        if (checkTime == null) return HttpResultUtil.errorJson("数据错误");
        if (StringUtils.isNotEmpty(forenoonTime)) checkTime.setMorning(forenoonTime);
        if (StringUtils.isNotEmpty(afternoonTime)) checkTime.setAfternoon(afternoonTime);
        checkTimeService.save(checkTime);
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 展示上班时间
     * @param req
     * @return
     */
    @RequestMapping(value = "showWorkTime",method = RequestMethod.POST)
    @ResponseBody
    public String showWorkTime(HttpServletRequest req){
        logger.info("app showWorkTime---------- Start--------");
//        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        CheckTime checkTime = checkTimeService.getOne();
        if (checkTime == null) return HttpResultUtil.errorJson("数据错误");
        Map mapData = new HashMap();
        mapData.put("forenoonTime",checkTime.getMorning());
        mapData.put("afternoonTime",checkTime.getAfternoon());
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 修改排班
     * @param req
     * @return
     */
    @RequestMapping(value = "updateScheduling",method = RequestMethod.POST)
    @ResponseBody
    public String updateScheduling(HttpServletRequest req){
        logger.info("app updateScheduling---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String date = (String)jsonData.get("date");
        String status = (String)jsonData.get("status");
        if (StringUtils.isEmpty(date)) return HttpResultUtil.errorJson("请选择日期");
        if (StringUtils.isEmpty(status)) return HttpResultUtil.errorJson("类型为空");
        try {
            Date test = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return HttpResultUtil.errorJson("日期格式错误");
        }
        CheckWorkDate checkWorkDate = checkWorkDateService.findByDate(date);
        if (checkWorkDate == null){
            checkWorkDate = new CheckWorkDate();
            checkWorkDate.setAllDate(date);
            checkWorkDate.setDates(date.substring(0,7));
        }
        checkWorkDate.setStatus(status);
        checkWorkDateService.save(checkWorkDate);
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 排班列表
     * @param req
     * @return
     */
    @RequestMapping(value = "showScheduling",method = RequestMethod.POST)
    @ResponseBody
    public String showScheduling(HttpServletRequest req){
        logger.info("app showScheduling---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String date = (String)jsonData.get("date"); //2018-12
        if (StringUtils.isEmpty(date)){
            date = sdf1.format(new Date());
        }else {
            try {
                Date test = sdf1.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return HttpResultUtil.errorJson("日期格式错误");
            }
        }
        List<CheckWorkDate> workList = checkWorkDateService.findByYearAndMonth(date);
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        Map mapData = new HashMap();
        List<Map<String,String>> list = DateUtil.getMonthFullDay(year,month);
        if (workList.size() != 0){
            for (CheckWorkDate cwd : workList) {
                String allDate = cwd.getAllDate();
                String day = allDate.substring(8);
                int index = Integer.parseInt(day)-1;
                Map<String,String> newMap = list.get(index);
                newMap.put("status",cwd.getStatus());
                list.set(index,newMap);
            }
        }
        mapData.put("list",list);
        return HttpResultUtil.successJson(mapData);
    }


}
