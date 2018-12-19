package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.CacheUtils;
import com.yogee.youge.common.utils.DateUtils;
import com.yogee.youge.common.utils.IdGen;
import com.yogee.youge.common.utils.StringUtils;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
                        punchMonth=year+"-"+month;
//                        System.out.println(year + "年" + month + "月有" + dayOfMonth + "天");
                    }
                    continue;
                }
                if(rowNum % 2 == 1){ //是奇数
                    List<Map<String,String>> dayList = new ArrayList<Map<String, String>>();
                    //是否可以导入标识
                    boolean isDaoru=false;
                    //查看用户表里是否有该员工
                    List<CheckUser> checkUserList = checkUserService.findListByName(name);
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
                                //打卡是否迟到
                                compareFour(cell,mapMorning,dayList);
                            }else{
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
                }else{
                    name= row.getCell(10).getStringCellValue();
                    System.out.println(name);
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
            xiaban=checkBusinessDate.getShangbanDate();
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
            xiaban=checkBusinessDate.getShangbanDate();
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
            if(! checkPunchCard.getNumber().toString().equals(parentId) ){
                parentMap.put("sonList",sonList);
                parentMap.put("size",sonList.size()+"");
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
        parentMap.put("size",sonList.size()+"");
        resultList.add(parentMap);
        mapData.put("resultList",resultList);
        return HttpResultUtil.successJson(mapData);
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
        if (StringUtils.isEmpty(shangChi)){
            return HttpResultUtil.errorJson("shangChi为空!");
        }
        String shangAskLeave = (String)jsonData.get("shangAskLeave");
        if (StringUtils.isEmpty(shangAskLeave)){
            return HttpResultUtil.errorJson("shangAskLeave为空!");
        }
        String shangAskLeaveTime = (String)jsonData.get("shangAskLeaveTime");
        //下午参数
        String xiaZao = (String)jsonData.get("xiaZao");
        if (StringUtils.isEmpty(xiaZao)){
            return HttpResultUtil.errorJson("xiaZao为空!");
        }
        String xiaAskLeave = (String)jsonData.get("xiaAskLeave");
        if (StringUtils.isEmpty(xiaAskLeave)){
            return HttpResultUtil.errorJson("xiaAskLeave为空!");
        }
        String xiaAskLeaveTime = (String)jsonData.get("xiaAskLeaveTime");

        CheckPunchCard checkPunchCard = checkPunchCardService.findCountByNumberAndPunchDate(number,punchDate);
        if(checkPunchCard !=null){
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
                Date time1=sdf.parse(checkPunchCard.getShangCellTime());
                double k = (time1.getTime() - b.getTime()) / (1000 * 60);
                checkPunchCard.setShangChiName("迟到");
                checkPunchCard.setShangChi(shangChi);
                checkPunchCard.setShangChiTime((int)k+"");
            }
            if(!shangAskLeave.equals("0")){
                checkPunchCard.setShangAskLeave(shangAskLeave);
                checkPunchCard.setShangAskLeaveTime(shangAskLeaveTime);
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
                    xiaban=checkBusinessDate.getShangbanDate();
                    CacheUtils.put("businessDate", "xiaban", xiaban);
                }
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
                Date d=sdf.parse(xiaban);
                Date time2=sdf.parse(checkPunchCard.getXiaCellTime());
                double k = (d.getTime() - time2.getTime() ) / (1000 * 60);
                checkPunchCard.setXiaZao(xiaZao);
                checkPunchCard.setXiaZaoTime((int)k+"");
                checkPunchCard.setXiaZaoName("早退");
            }
            if(!xiaAskLeave.equals("0")){
                checkPunchCard.setXiaAskLeave(xiaAskLeave);
                checkPunchCard.setXiaAskLeaveTime(xiaAskLeaveTime);
            }
            checkPunchCardService.save(checkPunchCard);
        }
        Map mapData = new HashMap();
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
        CheckBusinessDate checkBusinessDate = checkBusinessDateService.get("1");
        checkBusinessDate.setShangbanDate(shangban);
        checkBusinessDate.setWuxiuDate(wuxiu);
        checkBusinessDate.setXiabanDate(xiaban);
        checkBusinessDateService.save(checkBusinessDate);
        CacheUtils.put("businessDate", "shangban", shangban);
        CacheUtils.put("businessDate", "wuxiu", shangban);
        CacheUtils.put("businessDate", "xiaban", shangban);
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
        Map map = PunchCardInterface.getBusinessDate(checkBusinessDateService);
        return HttpResultUtil.successJson(map);
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
            xiaban=checkBusinessDate.getShangbanDate();
            CacheUtils.put("businessDate", "xiaban", xiaban);
        }
        Map mapData = new HashMap();
        mapData.put("shangban",shangban);
        mapData.put("wuxiu",wuxiu);
        mapData.put("xiaban",xiaban);
        return mapData;
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
            mapOne.put("key",dictOne.getLabel());
            mapOne.put("value",dictOne.getValue());
            listOne.add(mapOne);
        }
        List<Map<String,String>> listTwo = new ArrayList<>();
        for(int i=2;i<dictList.size();i++){
            Map mapTwo = new HashMap();
            mapTwo.put("key",dictList.get(i).getLabel());
            mapTwo.put("value",dictList.get(i).getValue());
            listTwo.add(mapTwo);
        }
        dataMap.put("listOne",listOne);
        dataMap.put("listTwo",listTwo);
        return HttpResultUtil.successJson(dataMap);
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
        Map labelMap = new LinkedHashMap();
        labelMap.put("number","姓名/工号");
        List<Dict> dictList = DictUtils.getDictList("qingjia_leixing");
        for(Dict dictOne:dictList){
            labelMap.put(dictOne.getValue(),dictOne.getLabel());
        }
        labelMap.put("chi0","迟到<10");
        labelMap.put("chi10","10<迟到<20");
        labelMap.put("chi20","20<迟到<30");
        labelMap.put("chi30","30<迟到");
        labelMap.put("zao0","早退<10");
        labelMap.put("zao10","10<早退<20");
        labelMap.put("zao20","20<早退<30");
        labelMap.put("zao30","30<早退");
        //查询某个月员工打卡人数
        List<CheckPunchCard> numberList = checkPunchCardService.findNumberByYearMonth(yearMonth);
        List parentList = new ArrayList();
        for(CheckPunchCard checkPunchCard:numberList){
            Map parentMap = new HashMap();
            //查询某个月员工请假的类型的时间
            List<CheckPunchCard> askLiveTimeList = checkPunchCardService.findAskLiveTimeByNumber(checkPunchCard.getNumber(),yearMonth);
            List<Dict> dictListOne = DictUtils.getDictList("qingjia_leixing");
            for(Dict dict:dictListOne){
                Boolean dictBoolean = true;
                for(CheckPunchCard card:askLiveTimeList){
                    if(dict.getValue().equals(card.getValue())){
                        parentMap.put(dict.getValue(),card.getSumTime()+"小时");
                        dictBoolean = false;
                        continue;
                    }
                }
                if(dictBoolean){
                    parentMap.put(dict.getValue(),"0小时");
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
            List<CheckPunchCard> chiList = checkPunchCardService.findShangChiByNumber(checkPunchCard.getNumber(),yearMonth);
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
            List<CheckPunchCard> zaoList = checkPunchCardService.findXiaZaoByNumber(checkPunchCard.getNumber(),yearMonth);
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
            parentList.add(parentMap);
        }
        mapData.put("labelMap",labelMap);
        mapData.put("list",parentList);
        return HttpResultUtil.successJson(mapData);
    }


    /**
     * 新增部门
     * @param request
     * @return
     */
    @RequestMapping(value = "insertDepartment", method = RequestMethod.POST)
    @ResponseBody
    public String insertDepartment(HttpServletRequest request,HttpServletResponse response) {
        logger.info("insertDepartment ----------Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String oneDepartmentName = (String)jsonData.get("oneDepartmentName");
        String oneDepartmentId = (String)jsonData.get("oneDepartmentId");
        String twoDepartmentName = (String)jsonData.get("twoDepartmentName");
        if(StringUtils.isNotEmpty(oneDepartmentName)){
            if(StringUtils.isNotEmpty(twoDepartmentName)){
                //添加一级部门同时添加二级部门

            }else{
                //只添加了一级部门

            }
        }else if(StringUtils.isNotEmpty(oneDepartmentId) && StringUtils.isNotEmpty(twoDepartmentName)){
            //添加二级部门

        }
        Map dataMap = new HashMap();
        return HttpResultUtil.successJson(dataMap);
    }





}
