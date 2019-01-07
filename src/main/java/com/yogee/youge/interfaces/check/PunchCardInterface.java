package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.CacheUtils;
import com.yogee.youge.common.utils.IdGen;
import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.common.utils.excel.ExportExcel;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckBusinessDate;
import com.yogee.youge.modules.check.entity.CheckPunchCard;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckBusinessDateService;
import com.yogee.youge.modules.check.service.CheckPunchCardService;
import com.yogee.youge.modules.check.service.CheckUserService;
import com.yogee.youge.modules.sys.entity.Dict;
import com.yogee.youge.modules.sys.utils.DictUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 打卡机信息
 * Created by sun.ye  on 2018/12/17.
 */

@Controller
@RequestMapping("${apiPath}")
public class PunchCardInterface {
    @Autowired
    private CheckPunchCardService checkPunchCardService;
    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private CheckBusinessDateService checkBusinessDateService;


    private static final Logger logger = LoggerFactory.getLogger(PunchCardInterface.class);


    /**
     * 导入打卡机信息
     * @return
     */
    @RequestMapping(value = "importPunchCard", method = RequestMethod.POST)
    @ResponseBody
    public String importPunchCard(MultipartFile file, HttpServletResponse response) throws IOException {
        logger.info("importPunchCard ----------Start--------");
        Map mapData = new HashMap();
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            //1、获取文件输入流
            InputStream inputStream1 = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String substring = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
            if(!substring.equals("xls")){
                return HttpResultUtil.errorJson("请上传以xls结尾的文件");
            }
            //2、获取Excel工作簿对象
            Workbook workbook = null;
            try {
                workbook = WorkbookFactory.create(inputStream1);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            //3、得到Excel工作表对象
            Sheet sheetAt = workbook.getSheetAt(2);  //示意访问sheet
            //4、循环读取表格数据
            String name="";
            String time ="";
            int dayOfMonth=0;
            String punchMonth="";
            for (Row row : sheetAt) {
                //首行（即表头）不读取
                int rowNum = row.getRowNum();
                System.out.println("--------------------"+rowNum+"-----------------------------------");
                if (rowNum < 4) {
                    if(rowNum==2){
                        time = row.getCell(2).getStringCellValue();
                        //判断当前月份有多少天
                        int year = Integer.parseInt(time.substring(0,4));
                        int month = Integer.parseInt(time.substring(5,7));
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, 0); //输入类型为int类型
                        dayOfMonth= c.get(Calendar.DAY_OF_MONTH);
                        System.out.println(dayOfMonth);
                        punchMonth=year+"-"+month;
//                        System.out.println(year + "年" + month + "月有" + dayOfMonth + "天");
                    }
                    continue;
                }
                if(rowNum % 2 == 1){ //是奇数行
                    List<Map<String,String>> dayList = new ArrayList<Map<String, String>>();
                    //是否可以导入标识
                    boolean isDaoru=false;
                    //查看用户表里是否有该员工
                    List<CheckUser> checkUserList = checkUserService.findListByName(name,punchMonth);
                    for(int j=1;j< dayOfMonth+1; j++){
                        String punchDate="";
                        if(j<10){
                            punchDate=punchMonth+"-0"+j;
                        }else{
                            punchDate=punchMonth+"-"+j;
                        }
                        if(checkUserList.size()==1){
                            //查看是否已经导入该信息
                            List<CheckPunchCard> listCard =checkPunchCardService.findCountByNameAndPunchDate(name,punchDate);
                            if(listCard.size()==0){
                                isDaoru=true;
                                successNum++;
                            }else{
                                //如果导入删除原有信息  重新导入
                                checkPunchCardService.deleteByNameAndPunchDate(name,punchDate);
                                isDaoru=true;
                                successNum++;
                            }
                        }else{
                            failureNum++;
                            continue;
                        }
                        if(isDaoru){
                            //读取导入信息
                            Map mapMorning = new HashMap();
                            //公共信息
                            mapMorning.put("id",IdGen.uuid());
                            mapMorning.put("name",name);
                            mapMorning.put("number",checkUserList.get(0).getNumber());
                            mapMorning.put("punchDate",punchDate);
                            mapMorning.put("shangAskLeave","0");
                            mapMorning.put("shangAskLeaveTime","0");
                            mapMorning.put("xiaAskLeave","0");
                            mapMorning.put("xiaAskLeaveTime","0");
                            String cell = row.getCell(j-1).getStringCellValue();
                            int cellLength = cell.length();
                            if(cellLength==0){
                                //上午未打卡
                                mapMorning.put("shangCellTime","");//打卡时间
                                mapMorning.put("shangChi","0");//迟到标识
                                mapMorning.put("shangChiTime","0");
                                mapMorning.put("shangChiName","");
                                //下午未打卡
                                mapMorning.put("xiaCellTime","");//打卡时间
                                mapMorning.put("xiaZao","0");//迟到标识
                                mapMorning.put("xiaZaoTime","0");
                                mapMorning.put("xiaZaoName","");
                                dayList.add(mapMorning);
                            }else if(cellLength==4){
                                //只打一次卡
                                compareFour(cell,mapMorning,dayList);
                            }else{
                                //打卡超过2次
                                compareThanFour(cell,mapMorning,dayList);
                            }
                            checkPunchCardService.saveList(dayList);
                            dayList.clear();
                            isDaoru=false;
                        }
                    }
                    System.out.println(dayList);
                    //清空name
                    name="";
                }else {

                    Cell cell = row.getCell(10);
                    if( cell == null ){
                        break;
                    }else{
                        //偶数行 取名字
                        name = row.getCell(10).getStringCellValue();
                        System.out.println(name);
                    }
                }
            }
            //5、关闭流
            inputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return HttpResultUtil.successJson(mapData);
    }
    //查找上午还是下午打卡
    public List<Map<String,String>> compareFour(String cell,Map mapMorning,List<Map<String,String>> dayList) throws ParseException, ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        //取出打卡时间
        Date time1=sdf.parse(cell);
        String shangban = (String) CacheUtils.get("businessDate", "shangban");
        String wuxiu = (String) CacheUtils.get("businessDate", "wuxiu");
        String xiaban = (String) CacheUtils.get("businessDate", "xiaban");
        if(StringUtils.isBlank(shangban) || StringUtils.isBlank(wuxiu) || StringUtils.isBlank(xiaban)){
            CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
            shangban=checkBusinessDate.getShangbanDate();
            CacheUtils.put("businessDate", "shangban", shangban);
            wuxiu=checkBusinessDate.getWuxiuDate();
            CacheUtils.put("businessDate", "wuxiu", wuxiu);
            xiaban=checkBusinessDate.getXiabanDate();
            CacheUtils.put("businessDate", "xiaban", xiaban);
        }
        Date b=sdf.parse(shangban);
        Date c=sdf.parse(wuxiu);
        Date d=sdf.parse(xiaban);
        if(time1.before(c)){
            //上午签到
            if(time1.before(b) || time1.equals(b)){
                //上午正常
                mapMorning.put("shangCellTime",cell);//打卡时间
                mapMorning.put("shangChi","0");//迟到标识(未迟到  time1小)
                mapMorning.put("shangChiTime","0");
                mapMorning.put("shangChiName","");
            }else{
                //上午迟到
                mapMorning.put("shangCellTime",cell);//打卡时间
                mapMorning.put("shangChi","1");//迟到标识(已迟到  time1大)
                double k = (time1.getTime() - b.getTime()) / (1000 * 60);
                mapMorning.put("shangChiTime",(int)k+"");//迟到分钟数
                mapMorning.put("shangChiName","迟到");
            }
            //下午未打卡
            mapMorning.put("xiaCellTime","");//打卡时间
            mapMorning.put("xiaZao","0");//迟到标识
            mapMorning.put("xiaZaoTime","0");
            mapMorning.put("xiaZaoName","");
            dayList.add(mapMorning);
            return dayList;
        }else {
            //下午签到
            if(time1.before(d)){
                //下午早退
                mapMorning.put("xiaCellTime",cell);//打卡时间
                mapMorning.put("xiaZao","1");//早退标识
                double k = (d.getTime() - time1.getTime() ) / (1000 * 60);
                mapMorning.put("xiaZaoTime",(int)k+"");//早退分钟数
                mapMorning.put("xiaZaoName","早退");
            }else{
                //下午正常
                mapMorning.put("xiaCellTime",cell);//打卡时间
                mapMorning.put("xiaZao","0");//迟到标识
                mapMorning.put("xiaZaoTime","0");
                mapMorning.put("xiaZaoName","");//正常下班
            }
            //上午未打卡
            mapMorning.put("shangCellTime","");//打卡时间
            mapMorning.put("shangChi","0");//迟到标识
            mapMorning.put("shangChiTime","0");
            mapMorning.put("shangChiName","");
            dayList.add(mapMorning);
            return dayList;
        }
    }
    //查找上午还是下午打卡
    public List<Map<String,String>> compareThanFour(String cell,Map mapMorning,List<Map<String,String>> dayList) throws ParseException, ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        //取出第一次和最后一次打卡时间
        String timeOne =cell.substring(0,5);
        String timeTwo =cell.substring(cell.length()-5,cell.length());
        Date time1=sdf.parse(timeOne);
        Date time2=sdf.parse(timeTwo);
        String shangban = (String) CacheUtils.get("businessDate", "shangban");
        String wuxiu = (String) CacheUtils.get("businessDate", "wuxiu");
        String xiaban = (String) CacheUtils.get("businessDate", "xiaban");
        if(StringUtils.isBlank(shangban) || StringUtils.isBlank(wuxiu) || StringUtils.isBlank(xiaban)){
            CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
            shangban=checkBusinessDate.getShangbanDate();
            CacheUtils.put("businessDate", "shangban", shangban);
            wuxiu=checkBusinessDate.getWuxiuDate();
            CacheUtils.put("businessDate", "wuxiu", wuxiu);
            xiaban=checkBusinessDate.getXiabanDate();
            CacheUtils.put("businessDate", "xiaban", xiaban);
        }
        Date b=sdf.parse(shangban);
        Date c=sdf.parse(wuxiu);
        Date d=sdf.parse(xiaban);
        //2次签到都在上午  签到时间取第一次
        if( (time1.before(c)|| time1.equals(c))  && (time2.before(c)||time2.equals(c)) ){
            if(time1.before(b) || time1.equals(b)){
                //上午正常
                mapMorning.put("shangCellTime",timeOne);//打卡时间
                mapMorning.put("shangChi","0");//迟到标识(未迟到  time1小)
                mapMorning.put("shangChiTime","0");
                mapMorning.put("shangChiName","");
            }else{
                //上午迟到
                mapMorning.put("shangCellTime",timeOne);//打卡时间
                mapMorning.put("shangChi","1");//迟到标识(已迟到  time1大)
                double k = (time1.getTime() - b.getTime()) / (1000 * 60);
                mapMorning.put("shangChiTime",(int)k+"");//迟到分钟数
                mapMorning.put("shangChiName","迟到");
            }
            //下午未打卡
            mapMorning.put("xiaCellTime","");//打卡时间
            mapMorning.put("xiaZao","0");//迟到标识
            mapMorning.put("xiaZaoTime","0");
            mapMorning.put("xiaZaoName","");
            dayList.add(mapMorning);
            return dayList;
            //2次签到一个上午   一个下午
        }else if((time1.before(c)||time1.equals(c)) && time2.after(c)){
            //上午签到
            if(time1.before(b) || time1.equals(b)){
                //上午正常
                mapMorning.put("shangCellTime",timeOne);//打卡时间
                mapMorning.put("shangChi","0");//迟到标识(未迟到  time1小)
                mapMorning.put("shangChiTime","0");
                mapMorning.put("shangChiName","");
            }else{
                //上午迟到
                mapMorning.put("shangCellTime",timeOne);//打卡时间
                mapMorning.put("shangChi","1");//迟到标识(已迟到  time1大)
                double k = (time1.getTime() - b.getTime()) / (1000 * 60);
                mapMorning.put("shangChiTime",(int)k+"");//迟到分钟数
                mapMorning.put("shangChiName","迟到");
            }
            //下午签到
            if(time2.before(d)){
                //下午早退
                mapMorning.put("xiaCellTime",timeTwo);//打卡时间
                mapMorning.put("xiaZao","1");//早退标识
                double k = (d.getTime() - time2.getTime() ) / (1000 * 60);
                mapMorning.put("xiaZaoTime",(int)k+"");//早退分钟数
                mapMorning.put("xiaZaoName","早退");
            }else{
                //下午正常
                mapMorning.put("xiaCellTime",timeTwo);//打卡时间
                mapMorning.put("xiaZao","0");//早退标识
                mapMorning.put("xiaZaoTime","0");//早退分钟数
                mapMorning.put("xiaZaoName","");
            }
            dayList.add(mapMorning);
            return dayList;
            //2次签到  2个下午
        }else{
            //上午未打卡
            mapMorning.put("shangCellTime","");//打卡时间
            mapMorning.put("shangChi","0");//迟到标识
            mapMorning.put("shangChiTime","0");
            mapMorning.put("shangChiName","");
            //下午打卡  取最后一条数据
            if(time2.before(d)){
                //下午早退
                mapMorning.put("xiaCellTime",timeTwo);//打卡时间
                mapMorning.put("xiaZao","1");//早退标识
                double k = (d.getTime() - time2.getTime() ) / (1000 * 60);
                mapMorning.put("xiaZaoTime",(int)k+"");//早退分钟数
                mapMorning.put("xiaZaoName","早退");
            }else{
                //下午正常
                mapMorning.put("xiaCellTime",timeTwo);//打卡时间
                mapMorning.put("xiaZao","0");//早退标识
                mapMorning.put("xiaZaoTime","0");//早退分钟数
                mapMorning.put("xiaZaoName","");
            }
            dayList.add(mapMorning);
            return dayList;
        }
    }

    /**
     * 员工考勤表
     * @param request
     * @return
     */
    @RequestMapping(value = "checkOnWork", method = RequestMethod.POST)
    @ResponseBody
    public String checkOnWork(HttpServletRequest request,HttpServletResponse response) {
        logger.info("checkOnWork ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String yearMonth = (String)jsonData.get("yearMonth");
        if (StringUtils.isEmpty(yearMonth)){
            return HttpResultUtil.errorJson("月份为空!");
        }
        String parentId ="";
        Map mapData = new HashMap();
        List resultList = new ArrayList();
        List sonList = new ArrayList();
        Map parentMap = new HashMap();
        List<CheckPunchCard> checkPunchCardList =  checkPunchCardService.findByYearMonth(yearMonth);
        for(CheckPunchCard checkPunchCard:checkPunchCardList){
            if(StringUtils.isEmpty(parentId)){
                parentMap = new HashMap<>();
                sonList = new ArrayList();
                parentId = checkPunchCard.getNumber();
            }
            if(! checkPunchCard.getNumber().equals(parentId) ){
                parentMap.put("sonList",sonList);
                resultList.add(parentMap);
                parentMap = new HashMap<>();
                sonList = new ArrayList();
                parentId =  checkPunchCard.getNumber();
            }
            parentMap.put("number", checkPunchCard.getNumber());
            parentMap.put("name", checkPunchCard.getName());
            Map <String,String> sonMap = new HashMap<>();
            sonMap.put("punchDate",checkPunchCard.getPunchDate());
            sonMap.put("shangCellTime",checkPunchCard.getShangCellTime());
            sonMap.put("shangChi",checkPunchCard.getShangChi());
            sonMap.put("shangChiTime",checkPunchCard.getShangChiTime()+"/分");
            sonMap.put("shangChiName",checkPunchCard.getShangChiName());
            sonMap.put("shangAskLeave",checkPunchCard.getShangAskLeave());
            sonMap.put("shangAskLeaveName",DictUtils.getDictLabel(checkPunchCard.getShangAskLeave(),"qingjia_leixing",""));
            sonMap.put("shangAskLeaveTime",checkPunchCard.getShangAskLeaveTime());
            sonMap.put("xiaCellTime",checkPunchCard.getXiaCellTime());
            sonMap.put("xiaZao",checkPunchCard.getXiaZao());
            sonMap.put("xiaZaoTime",checkPunchCard.getXiaZaoTime()+"/分");
            sonMap.put("xiaZaoName",checkPunchCard.getXiaZaoName());
            sonMap.put("xiaAskLeave",checkPunchCard.getXiaAskLeave());
            sonMap.put("xiaAskLeaveName",DictUtils.getDictLabel(checkPunchCard.getXiaAskLeave(),"qingjia_leixing",""));
            sonMap.put("xiaAskLeaveTime",checkPunchCard.getXiaAskLeaveTime());
            sonList.add(sonMap);
        }
        parentMap.put("sonList", sonList);
        resultList.add(parentMap);
        //计算本月天数
        int year = Integer.parseInt(yearMonth.substring(0,4));
        int month =Integer.parseInt(yearMonth.substring(5,7));
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        int dayOfMonth= c.get(Calendar.DAY_OF_MONTH);
        mapData.put("resultList",resultList);
        mapData.put("dayOfMonth",dayOfMonth+"");
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 请假类型
     * @param request
     * @return
     */
    @RequestMapping(value = "workType", method = RequestMethod.POST)
    @ResponseBody
    public String workType(HttpServletRequest request,HttpServletResponse response) {
        logger.info("workType ----------Start--------");
        Map dataMap = new HashMap();
        List<Dict> dictList = DictUtils.getDictList("qingjia_leixing");
        List<Map<String,String>> listOne = new ArrayList<>();
        for(Dict dictOne:dictList){
            Map mapOne = new HashMap();
            mapOne.put("label",dictOne.getLabel());
            mapOne.put("value",dictOne.getValue());
            listOne.add(mapOne);
        }
        List<Map<String,String>> listTwo = new ArrayList<>();
        for(int i=2;i<dictList.size();i++){
            Map mapTwo = new HashMap();
            mapTwo.put("label",dictList.get(i).getLabel());
            mapTwo.put("value",dictList.get(i).getValue());
            listTwo.add(mapTwo);
        }
        dataMap.put("listOne",listOne);
        dataMap.put("listTwo",listTwo);
        return HttpResultUtil.successJson(dataMap);
    }

    /**
     * 保存考勤设置
     * @param request
     * @return
     */
    @RequestMapping(value = "saveCheckOnWork", method = RequestMethod.POST)
    @ResponseBody
    public String saveCheckOnWork(HttpServletRequest request,HttpServletResponse response) throws ParseException {
        logger.info("saveCheckOnWork ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String number = (String)jsonData.get("number");
        if (StringUtils.isEmpty(number)){
            return HttpResultUtil.errorJson("工号为空!");
        }
        String punchDate = (String)jsonData.get("punchDate");
        if (StringUtils.isEmpty(punchDate)){
            return HttpResultUtil.errorJson("日期为空!");
        }
        //上午参数
        String shangChi = (String)jsonData.get("shangChi");
        String shangAskLeave = (String)jsonData.get("shangAskLeave");
        String shangAskLeaveTime = (String)jsonData.get("shangAskLeaveTime");
        //下午参数
        String xiaZao = (String)jsonData.get("xiaZao");
        String xiaAskLeave = (String)jsonData.get("xiaAskLeave");
        String xiaAskLeaveTime = (String)jsonData.get("xiaAskLeaveTime");
        try {
            CheckPunchCard checkPunchCard = checkPunchCardService.findCountByNumberAndPunchDate(number,punchDate);
            if(checkPunchCard !=null){
                if (StringUtils.isNotEmpty(shangChi)){
                    if (StringUtils.isEmpty(shangAskLeave)){
                        return HttpResultUtil.errorJson("shangAskLeave为空!");
                    }
                    //上午参数
                    if(shangChi.equals("0")){
                        //未迟到
                        checkPunchCard.setShangChi(shangChi);
                        checkPunchCard.setShangChiTime("0");
                        checkPunchCard.setShangChiName("");
                    }else{
                        //获取上班时间
                        String shangban = (String) CacheUtils.get("businessDate", "shangban");
                        if(StringUtils.isBlank(shangban)){
                            CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
                            shangban=checkBusinessDate.getShangbanDate();
                            CacheUtils.put("businessDate", "shangban", shangban);
                        }
                        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                        Date b=sdf.parse(shangban);
                        if(StringUtils.isEmpty(checkPunchCard.getShangCellTime())){
                            return HttpResultUtil.errorJson("未打卡不能标记迟到!");
                        }
                        Date time1=sdf.parse(checkPunchCard.getShangCellTime());
                        double k = (time1.getTime() - b.getTime()) / (1000 * 60);
                        if(k<0){
                            List<CheckUser> byNumber  = checkUserService.findByNumber(number);
                            if(byNumber.size()>0){
                                return HttpResultUtil.errorJson(byNumber.get(0).getName()+"未迟到，不能标记迟到!");
                            }else{
                                return HttpResultUtil.errorJson("该数据未迟到，不能标记迟到!");
                            }
                        }
                        checkPunchCard.setShangChiName("迟到");
                        checkPunchCard.setShangChi(shangChi);
                        checkPunchCard.setShangChiTime((int)k+"");
                    }
                    if(!shangAskLeave.equals("0")){
                        checkPunchCard.setShangAskLeave(shangAskLeave);
                        checkPunchCard.setShangAskLeaveTime(shangAskLeaveTime);
                    }
                }else{
                    if (StringUtils.isEmpty(xiaAskLeave)){
                        return HttpResultUtil.errorJson("xiaAskLeave为空!");
                    }
                    //下午参数
                    if(xiaZao.equals("0")){
                        //未早退
                        checkPunchCard.setXiaZao(xiaZao);
                        checkPunchCard.setXiaZaoTime("0");
                        checkPunchCard.setXiaZaoName("");
                    }else{
                        //获取下班时间
                        String xiaban = (String) CacheUtils.get("businessDate", "xiaban");
                        if(StringUtils.isBlank(xiaban)){
                            CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
                            xiaban=checkBusinessDate.getXiabanDate();
                            CacheUtils.put("businessDate", "xiaban", xiaban);
                        }
                        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                        Date d=sdf.parse(xiaban);
                        if(StringUtils.isEmpty(checkPunchCard.getXiaCellTime())){
                            return HttpResultUtil.errorJson("未打卡不能标记早退!");
                        }
                        Date time2=sdf.parse(checkPunchCard.getXiaCellTime());
                        double k = (d.getTime() - time2.getTime() ) / (1000 * 60);
                        if(k<0){
                            List<CheckUser> byNumber  = checkUserService.findByNumber(number);
                            if(byNumber.size()>0){
                                return HttpResultUtil.errorJson(byNumber.get(0).getName()+"未早退，不能标记早退!");
                            }else{
                                return HttpResultUtil.errorJson("该数据未早退，不能标记早退!");
                            }
                        }
                        checkPunchCard.setXiaZao(xiaZao);
                        checkPunchCard.setXiaZaoTime((int)k+"");
                        checkPunchCard.setXiaZaoName("早退");
                    }
                    if(!xiaAskLeave.equals("0")){
                        checkPunchCard.setXiaAskLeave(xiaAskLeave);
                        checkPunchCard.setXiaAskLeaveTime(xiaAskLeaveTime);
                    }
                }
                checkPunchCardService.save(checkPunchCard);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 查询上下班时间
     * @param request
     * @return
     */
    @RequestMapping(value = "selectBusinessDate", method = RequestMethod.POST)
    @ResponseBody
    public String selectBusinessDate(HttpServletRequest request,HttpServletResponse response) {
        logger.info("selectBusinessDate ----------Start--------");
        String shangban = (String) CacheUtils.get("businessDate", "shangban");
        String wuxiu = (String) CacheUtils.get("businessDate", "wuxiu");
        String xiaban = (String) CacheUtils.get("businessDate", "xiaban");
        if(StringUtils.isBlank(shangban) || StringUtils.isBlank(wuxiu) || StringUtils.isBlank(xiaban)){
            CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
            shangban=checkBusinessDate.getShangbanDate();
            CacheUtils.put("businessDate", "shangban", shangban);
            wuxiu=checkBusinessDate.getWuxiuDate();
            CacheUtils.put("businessDate", "wuxiu", wuxiu);
            xiaban=checkBusinessDate.getXiabanDate();
            CacheUtils.put("businessDate", "xiaban", xiaban);
        }
        Map mapData = new HashMap();
        mapData.put("shangban",shangban+":00");
        mapData.put("wuxiu",wuxiu+":00");
        mapData.put("xiaban",xiaban+":00");
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 设置上下班时间
     * @param request
     * @return
     */
    @RequestMapping(value = "saveBusinessDate", method = RequestMethod.POST)
    @ResponseBody
    public String saveBusinessDate(HttpServletRequest request,HttpServletResponse response) {
        logger.info("saveBusinessDate ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String shangban = (String)jsonData.get("shangban");
        if (StringUtils.isEmpty(shangban)){
            return HttpResultUtil.errorJson("上班时间为空!");
        }
        String wuxiu = (String)jsonData.get("wuxiu");
        if (StringUtils.isEmpty(wuxiu)){
            return HttpResultUtil.errorJson("午休时间为空!");
        }
        String xiaban = (String)jsonData.get("xiaban");
        if (StringUtils.isEmpty(xiaban)){
            return HttpResultUtil.errorJson("下班时间为空!");
        }
        try{
            shangban=shangban.substring(0,5);
            wuxiu=wuxiu.substring(0,5);
            xiaban=xiaban.substring(0,5);
        }catch (Exception e){
            return HttpResultUtil.errorJson("时间格式有误!");
        }
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        try {
            Date shangbanDate = sdf.parse(shangban);
            Date wuxiuDate = sdf.parse(wuxiu);
            Date xiabanDate = sdf.parse(xiaban);
            if(shangbanDate.equals(wuxiuDate) || shangbanDate.after(wuxiuDate) ){
                return HttpResultUtil.errorJson("上班时间不能晚于午休时间!");
            }
            if(shangbanDate.equals(xiabanDate) || shangbanDate.after(xiabanDate) ){
                return HttpResultUtil.errorJson("上班时间不能晚于下班时间!");
            }
            if(wuxiuDate.equals(xiabanDate) || wuxiuDate.after(xiabanDate) ){
                return HttpResultUtil.errorJson("午休时间不能晚于下班时间!");
            }
        } catch (ParseException e) {
            return HttpResultUtil.errorJson("时间格式有误!");
        }
        CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
        checkBusinessDate.setShangbanDate(shangban);
        checkBusinessDate.setWuxiuDate(wuxiu);
        checkBusinessDate.setXiabanDate(xiaban);
        checkBusinessDateService.save(checkBusinessDate);
        CacheUtils.put("businessDate", "shangban", shangban);
        CacheUtils.put("businessDate", "wuxiu", wuxiu);
        CacheUtils.put("businessDate", "xiaban", xiaban);
        Map mapData = new HashMap();
        mapData.put("shangban",shangban);
        mapData.put("wuxiu",wuxiu);
        mapData.put("xiaban",xiaban);
        return HttpResultUtil.successJson(mapData);
    }


    //获取上班、午休、下班时间
    public static Map getBusinessDate(CheckBusinessDateService checkBusinessDateService){
        String shangban = (String) CacheUtils.get("businessDate", "shangban");
        String wuxiu = (String) CacheUtils.get("businessDate", "wuxiu");
        String xiaban = (String) CacheUtils.get("businessDate", "xiaban");
        if(StringUtils.isBlank(shangban) || StringUtils.isBlank(wuxiu) || StringUtils.isBlank(xiaban)){
            CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
            shangban=checkBusinessDate.getShangbanDate();
            CacheUtils.put("businessDate", "shangban", shangban);
            wuxiu=checkBusinessDate.getWuxiuDate();
            CacheUtils.put("businessDate", "wuxiu", wuxiu);
            xiaban=checkBusinessDate.getXiabanDate();
            CacheUtils.put("businessDate", "xiaban", xiaban);
        }
        Map mapData = new HashMap();
        mapData.put("shangban",shangban);
        mapData.put("wuxiu",wuxiu);
        mapData.put("xiaban",xiaban);
        return mapData;
    }




    /**
     * 考勤汇总表
     * @param request
     * @return
     */
    @RequestMapping(value = "checkOnWorkCollect", method = RequestMethod.POST)
    @ResponseBody
    public String checkOnWorkCollect(HttpServletRequest request,HttpServletResponse response) {
        logger.info("checkOnWorkCollect ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String yearMonth = (String)jsonData.get("yearMonth");
        if (StringUtils.isEmpty(yearMonth)){
            return HttpResultUtil.errorJson("月份为空!");
        }
        Map mapData = new HashMap();
//        Map labelMap = new LinkedHashMap();
        List LabelList = new LinkedList();
//        labelMap.put("number","姓名/工号");
//        labelMap.put("chi0","迟到<10");
//        labelMap.put("chi10","10<迟到<20");
//        labelMap.put("chi20","20<迟到<30");
//        labelMap.put("chi30","30<迟到");
//        labelMap.put("zao0","早退<10");
//        labelMap.put("zao10","10<早退<20");
//        labelMap.put("zao20","20<早退<30");
//        labelMap.put("zao30","30<早退");
//        List<Dict> dictList = DictUtils.getDictList("qingjia_leixing");
//        for(Dict dictOne:dictList){
//            labelMap.put(dictOne.getValue(),dictOne.getLabel());
//        }
        Map mapOne = new HashMap();
        mapOne.put("label","姓名/工号");
        mapOne.put("value","number");
        LabelList.add(mapOne);
        List<Dict> chizaoList = DictUtils.getDictList("chidao_zaotui");
        for(Dict dictTwo:chizaoList){
            Map mapTwo = new HashMap();
            mapTwo.put("label",dictTwo.getLabel());
            mapTwo.put("value",dictTwo.getValue());
            LabelList.add(mapTwo);
        }
        List<Dict> dictList = DictUtils.getDictList("qingjia_leixing");
        for(Dict dictThree:dictList){
            Map mapThree = new HashMap();
            mapThree.put("label",dictThree.getLabel());
            mapThree.put("value",dictThree.getValue());
            LabelList.add(mapThree);
        }

        //查询某个月员工打卡人数
        List<CheckPunchCard> numberList = checkPunchCardService.findNumberByYearMonth(yearMonth);
        List parentList = new LinkedList();
        for(CheckPunchCard checkPunchCard:numberList){
            List sonList = new LinkedList();
            List sonTwoList = new LinkedList();
            Map parentMap = new HashMap();
            Map mapZero = new HashMap();
            mapZero.put("name",checkPunchCard.getName());
            mapZero.put("number",checkPunchCard.getNumber());
            sonTwoList.add(mapZero);
            //查询某个月员工迟到的时间
            int chi0Num=0;
            int chi10Num=0;
            int chi20Num=0;
            int chi30Num=0;
            int chi0=0;
            int chi10=0;
            int chi20=0;
            int chi30=0;
            List<CheckPunchCard> chiList = checkPunchCardService.findShangChiByNumber(checkPunchCard.getNumber(),yearMonth);
            for(CheckPunchCard chi:chiList){
                int chiDaoTime = Integer.parseInt(chi.getShangChiTime());
                if(0<chiDaoTime && chiDaoTime<=10){
                    chi0=chi0+chiDaoTime;
                    chi0Num++;
                }else if(10<chiDaoTime && chiDaoTime<=20){
                    chi10=chi10+chiDaoTime;
                    chi10Num++;
                }else if(20<chiDaoTime && chiDaoTime<=30){
                    chi20=chi20+chiDaoTime;
                    chi20Num++;
                }else{
                    chi30=chi30+chiDaoTime;
                    chi30Num++;
                }
            }
            //查询某个月员工早退的时间
            int zao0Num=0;
            int zao10Num=0;
            int zao20Num=0;
            int zao30Num=0;
            int zao0=0;
            int zao10=0;
            int zao20=0;
            int zao30=0;
            List<CheckPunchCard> zaoList = checkPunchCardService.findXiaZaoByNumber(checkPunchCard.getNumber(),yearMonth);
            for(CheckPunchCard zao:zaoList){
                int zaoTuiTime = Integer.parseInt(zao.getXiaZaoTime());
                if(0<zaoTuiTime && zaoTuiTime<=10){
                    zao0=zao0+zaoTuiTime;
                    zao0Num++;
                }else if(10<zaoTuiTime && zaoTuiTime<=20){
                    zao10=zao10+zaoTuiTime;
                    zao10Num++;
                }else if(20<zaoTuiTime && zaoTuiTime<=30){
                    zao20=zao20+zaoTuiTime;
                    zao20Num++;
                }else{
                    zao30=zao30+zaoTuiTime;
                    zao30Num++;
                }
            }
            List<Dict> dictListOne = DictUtils.getDictList("chidao_zaotui");
            for(Dict dict:dictListOne){
                Map oneMap = new HashMap();
                switch (dict.getValue()){
                    case "chi0":
                        oneMap.put("name",chi0Num+"次 "+chi0+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "chi10":
                        oneMap.put("name",chi10Num+"次 "+chi10+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "chi20":
                        oneMap.put("name",chi20Num+"次 "+chi20+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "chi30":
                        oneMap.put("name",chi30Num+"次 "+chi30+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "zao0":
                        oneMap.put("name",zao0Num+"次 "+zao0+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "zao10":
                        oneMap.put("name",zao10Num+"次 "+zao10+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "zao20":
                        oneMap.put("name",zao20Num+"次 "+zao20+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                    case "zao30":
                        oneMap.put("name",zao30Num+"次 "+zao30+"分钟");
                        oneMap.put("value",dict.getValue());
                        sonList.add(oneMap);
                    break;
                }

            }
            //查询某个月员工请假的类型的时间
            List<CheckPunchCard> askLiveTimeList = checkPunchCardService.findAskLiveTimeByNumber(checkPunchCard.getNumber(),yearMonth);
            List<Dict> dictListTwo = DictUtils.getDictList("qingjia_leixing");
            for(Dict dict:dictListTwo){
                Map mapTwo = new HashMap();
                Boolean dictBoolean = true;
                for(CheckPunchCard card:askLiveTimeList){
                    if(dict.getValue().equals(card.getValue())){
                        mapTwo.put("name",card.getSumTime()+"小时");
                        mapTwo.put("value",dict.getValue());
                        dictBoolean = false;
                        continue;
                    }
                }
                if(dictBoolean){
                    mapTwo.put("name","0小时");
                    mapTwo.put("value",dict.getValue());
                }
                sonList.add(mapTwo);
            }
            parentMap.put("sonList",sonList);
            parentMap.put("sonTwoList",sonTwoList);
            parentList.add(parentMap);
        }
        mapData.put("LabelList",LabelList);
        mapData.put("parentList",parentList);
        return HttpResultUtil.successJson(mapData);
    }


    /**
     * 导出员工考勤表excel
     * @param request
     * @return
     */
    @RequestMapping(value = "exportCheckOnWork", method = RequestMethod.POST)
    @ResponseBody
    public String exportCheckOnWork(HttpServletRequest request,HttpServletResponse response) {
        logger.info("exportCheckOnWork ----------Start--------");
//        Map jsonData = HttpServletRequestUtils.readJsonData(request);
//        String yearMonth = (String)jsonData.get("yearMonth");
//        if (StringUtils.isEmpty(yearMonth)){
//            return HttpResultUtil.errorJson("yearMonth为空!");
//        }
        String yearMonth =  request.getParameter("yearMonth");
        if (StringUtils.isEmpty(yearMonth)){
            return HttpResultUtil.errorJson("yearMonth为空!");
        }
        try {
//            String yearMonth = "2018-11";
            Map<String, Object> map = checkOnWorkMap(yearMonth);
            File file = null;
            InputStream inputStream = null;
            ServletOutputStream out = null;
            request.setCharacterEncoding("UTF-8");
            //根据模板类型
            file = ExportExcel.createExcel(map,"myexcel","check.ftl",request);
            inputStream = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("员工考勤表" + ".xls", "UTF-8"));
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中
            while ((bytesToRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
            out.flush();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResultUtil.errorJson("导出失败!");
        }

    }
    public Map checkOnWorkMap(String punchDate) {
        Map map = new HashMap();
        List<Map<String,String>> resultList = new ArrayList<>();
        List<CheckPunchCard> numberList =  checkPunchCardService.findNumberByYearMonth(punchDate);
        for(int i=0;i<numberList.size();i++){
            List<CheckPunchCard> checkPunchCardList = checkPunchCardService.findByNumberAndPunchDate(numberList.get(i).getNumber(),punchDate);

            Map dataMap = new HashMap();
            dataMap.put("xuhao",i+1+"");
            dataMap.put("name",numberList.get(i).getName());
            dataMap.put("number",numberList.get(i).getNumber());
            for(int j=1;j<checkPunchCardList.size()+1;j++){
                //上午打卡时间
                dataMap.put("shangCellDay"+j,checkPunchCardList.get(j-1).getShangCellTime());

                //上午迟到，请假事件
                StringBuffer sb = new StringBuffer();
                CheckPunchCard bean = checkPunchCardList.get(j-1);
                if(bean.getShangChi().equals("1")){
                    sb.append("迟到"+bean.getShangChiTime()+"/分；");
                }
                if(!bean.getShangAskLeave().equals("0")){
                    sb.append(DictUtils.getDictLabel(bean.getShangAskLeave(),"qingjia_leixing","")+bean.getShangAskLeaveTime()+"/时");
                }
                dataMap.put("shangChiDay"+j,sb.toString());

                //下班打卡时间
                dataMap.put("xiaCellDay"+j,checkPunchCardList.get(j-1).getXiaCellTime());

                //下午早退，请假事件
                StringBuffer sb2 = new StringBuffer();
                CheckPunchCard bean2 = checkPunchCardList.get(j-1);
                if(bean2.getXiaZao().equals("1")){
                    sb2.append("早退"+bean2.getXiaZaoTime()+"/分；");
                }
                if(!bean2.getXiaAskLeave().equals("0")){
                    sb2.append(DictUtils.getDictLabel(bean2.getXiaAskLeave(),"qingjia_leixing","")+bean2.getXiaAskLeaveTime()+"/时");
                }
                dataMap.put("xiaZaoDay"+j,sb2.toString());
            }
            resultList.add(dataMap);

        }
        map.put("punchDate",punchDate);
        map.put("resultList",resultList);
        map.put("numberSize",numberList.size()*4+4+"");
        return map;
    }

    /**
     * 导出员工考勤汇总表excel
     * @param request
     * @return
     */
    @RequestMapping(value = "exportCheckOnWorkCollect", method = RequestMethod.POST)
    @ResponseBody
    public String exportCheckOnWorkCollect(HttpServletRequest request,HttpServletResponse response) {
        logger.info("exportCheckOnWorkCollect ----------Start--------");
//        Map jsonData = HttpServletRequestUtils.readJsonData(request);
//        String yearMonth = (String)jsonData.get("yearMonth");
//        if (StringUtils.isEmpty(yearMonth)){
//            return HttpResultUtil.errorJson("yearMonth为空!");
//        }
        String  yearMonth =  request.getParameter("yearMonth");
        if (StringUtils.isEmpty(yearMonth)){
            return HttpResultUtil.errorJson("yearMonth为空!");
        }
        try {
//            String yearMonth = "2018-11";
            Map<String, Object> map = checkOnWorkCollectMap(yearMonth);
            File file = null;
            InputStream inputStream = null;
            ServletOutputStream out = null;
            request.setCharacterEncoding("UTF-8");
            //根据模板类型
            file = ExportExcel.createExcel(map,"myexcel","checkCollect.ftl",request);
            inputStream = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("员工考勤汇总表" + ".xls", "UTF-8"));
            out = response.getOutputStream();
            byte[] buffer = new byte[512]; // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Excel文件的内容输出到浏览器中
            while ((bytesToRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
            out.flush();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResultUtil.errorJson("导出失败!");
        }

    }
    public Map checkOnWorkCollectMap(String punchDate) {
        Map map = new HashMap();
        //表头信息
        List labelList = new ArrayList();
        Map labelMap = new LinkedHashMap();
        labelMap.put("xuhao","序号");
        labelMap.put("number","姓名/工号");
        labelMap.put("chi0","迟到<10分");
        labelMap.put("chi10","10分<迟到<20分");
        labelMap.put("chi20","20分<迟到<30分");
        labelMap.put("chi30","30分<迟到");
        labelMap.put("zao0","早退<10分");
        labelMap.put("zao10","10分<早退<20分");
        labelMap.put("zao20","20分<早退<30分");
        labelMap.put("zao30","30分<早退");
        List<Dict> dictList = DictUtils.getDictList("qingjia_leixing");
        for(int i=0;i<dictList.size();i++){
            labelMap.put("key"+i,dictList.get(i).getLabel());
        }
        labelList.add(labelMap);
        //汇总信息
        //查询某个月员工打卡人数
        List<CheckPunchCard> numberList = checkPunchCardService.findNumberByYearMonth(punchDate);
        List resultList = new ArrayList();
        int k=1;
        for(CheckPunchCard checkPunchCard:numberList){
            Map parentMap = new HashMap();
            //查询某个月员工请假的类型的时间
            List<CheckPunchCard> askLiveTimeList = checkPunchCardService.findAskLiveTimeByNumber(checkPunchCard.getNumber(),punchDate);
            List<Dict> dictListOne = DictUtils.getDictList("qingjia_leixing");
            for(int i=0;i<dictListOne.size();i++){
                Boolean dictBoolean = true;
                for(CheckPunchCard card:askLiveTimeList){
                    if(dictListOne.get(i).getValue().equals(card.getValue())){
                        parentMap.put("key"+i,card.getSumTime()+"小时");
                        dictBoolean = false;
                        continue;
                    }
                }
                if(dictBoolean){
                    parentMap.put("key"+i,"0小时");
                }
            }
            //查询某个月员工迟到的时间
            int chiNum0=0;
            int chiNum10=0;
            int chiNum20=0;
            int chiNum30=0;
            int Chi0=0;
            int Chi10=0;
            int Chi20=0;
            int Chi30=0;
            List<CheckPunchCard> chiList = checkPunchCardService.findShangChiByNumber(checkPunchCard.getNumber(),punchDate);
            for(CheckPunchCard chi:chiList){
                int chiDaoTime = Integer.parseInt(chi.getShangChiTime());
                if(0<chiDaoTime && chiDaoTime<=10){
                    Chi0=Chi0+chiDaoTime;
                    chiNum0++;
                }else if(10<chiDaoTime && chiDaoTime<=20){
                    Chi10=Chi10+chiDaoTime;
                    chiNum10++;
                }else if(20<chiDaoTime && chiDaoTime<=30){
                    Chi20=Chi20+chiDaoTime;
                    chiNum20++;
                }else{
                    Chi30=Chi30+chiDaoTime;
                    chiNum30++;
                }
            }
            parentMap.put("chi0",chiNum0+"次 "+Chi0+"分钟");
            parentMap.put("chi10",chiNum10+"次 "+Chi10+"分钟");
            parentMap.put("chi20",chiNum20+"次 "+Chi20+"分钟");
            parentMap.put("chi30",chiNum30+"次 "+Chi30+"分钟");
            //查询某个月员工早退的时间
            int zaoNum0=0;
            int zaoNum10=0;
            int zaoNum20=0;
            int zaoNum30=0;
            int zao0=0;
            int zao10=0;
            int zao20=0;
            int zao30=0;
            List<CheckPunchCard> zaoList = checkPunchCardService.findXiaZaoByNumber(checkPunchCard.getNumber(),punchDate);
            for(CheckPunchCard zao:zaoList){
                int zaoTuiTime = Integer.parseInt(zao.getXiaZaoTime());
                if(0<zaoTuiTime && zaoTuiTime<=10){
                    zao0=zao0+zaoTuiTime;
                    zaoNum0++;
                }else if(10<zaoTuiTime && zaoTuiTime<=20){
                    zao10=zao10+zaoTuiTime;
                    zaoNum10++;
                }else if(20<zaoTuiTime && zaoTuiTime<=30){
                    zao20=zao20+zaoTuiTime;
                    zaoNum20++;
                }else{
                    zao30=zao30+zaoTuiTime;
                    zaoNum30++;
                }
            }
            parentMap.put("zao0",zaoNum0+"次 "+zao0+"分钟");
            parentMap.put("zao10",zaoNum10+"次 "+zao10+"分钟");
            parentMap.put("zao20",zaoNum20+"次 "+zao20+"分钟");
            parentMap.put("zao30",zaoNum30+"次 "+zao30+"分钟");
            parentMap.put("number",checkPunchCard.getNumber());
            parentMap.put("name",checkPunchCard.getName());
            parentMap.put("xuhao",k++);
            resultList.add(parentMap);
        }
        map.put("punchDate",punchDate);
        map.put("labelList",labelList);
        map.put("resultList",resultList);
        return map;
    }


}
