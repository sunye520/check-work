package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.common.utils.excel.ExportExcel;
import com.yogee.youge.interfaces.util.DateUtil;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckDepartment;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckDepartmentService;
import com.yogee.youge.modules.check.service.CheckUserService;
import com.yogee.youge.modules.sys.service.DictService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 员工花名册
 * author:cheng
 */
@Controller
@RequestMapping("${apiPath}")
public class EmployeeInterface {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeInterface.class);

    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private DictService dictService;
    @Autowired
    private CheckDepartmentService checkDepartmentService;

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");






    /**
     * 员工列表
     * @param req
     * @return
     */
    @RequestMapping(value = "employeeList",method = RequestMethod.POST)
    @ResponseBody
    public String employeeList(HttpServletRequest req){
        logger.info("app employeeList---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String total = (String)jsonData.get("total");
        String count = (String)jsonData.get("count");
        String nameLike = (String)jsonData.get("nameLike");
        if (StringUtils.isEmpty(total) || StringUtils.isEmpty(count)){
            total = "0";
            count = "0";
        }
        if (StringUtils.isEmpty(nameLike)) nameLike = "";
        List<Map> employeeList = checkUserService.findListByNameLikeAndCounts(nameLike,total,count);
        if (employeeList.size() != 0){
            Date date = new Date();
            for (Map map : employeeList) {
                map.put("nowDate",sdf.format(date));   //现在日期
                String ruzhiDate = map.get("ruzhi_date")==null?"":map.get("ruzhi_date").toString(); //入职日期
                String canjiagongzuoDate = map.get("ruzhi_date")==null?"":map.get("ruzhi_date").toString();  //参加工作日期
                try {
                    String format = sdf.format(date);
                    Date parse = sdf.parse(format);
                    if(!ruzhiDate.isEmpty()){
                        Date date1 = sdf.parse(ruzhiDate);
                        int year1 = DateUtil.getYear(parse,date1);
                        int month1 = DateUtil.getMonth(parse,date1);
                        map.put("companyAgeMonth", String.valueOf(month1));//司龄
                        map.put("companyAgeYear", String.valueOf(year1));//司龄/年
                    }else{
                        map.put("companyAgeMonth", "0");//司龄
                        map.put("companyAgeYear", "0");//司龄/年
                    }

                    if(!canjiagongzuoDate.isEmpty()){
                        Date date2 = sdf.parse(canjiagongzuoDate);
                        int year2 = DateUtil.getYear(parse,date2);
                        int month2 = DateUtil.getMonth(parse,date2);

                        map.put("workAgeMonth", String.valueOf(month2));//工作年限
                        map.put("workAgeYear", String.valueOf(year2));//工作年限/年
                    }else{
                        map.put("workAgeMonth","0");//工作年限
                        map.put("workAgeYear", "0");//工作年限/年
                    }
                } catch (ParseException e) {
                    logger.debug("日期格式错误");
                }




            }
        }
        Map mapData = new HashMap();
        mapData.put("list",employeeList);
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 员工详情
     * @param req
     * @return
     */
    @RequestMapping(value = "showEmployee",method = RequestMethod.POST)
    @ResponseBody
    public String showEmployee(HttpServletRequest req){
        logger.info("app showEmployee---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String userId = (String)jsonData.get("userId");
        if (StringUtils.isEmpty(userId)) return HttpResultUtil.errorJson("用户id为空!");
        CheckUser checkUser = checkUserService.get(userId);
        if (checkUser == null) return HttpResultUtil.errorJson("无此用户!");


        String hetongLeixing = checkUser.getHetongLeixing();
        if(hetongLeixing.equals("1")){
            checkUser.setHetongLeixing("否");
        }else{
            checkUser.setHetongLeixing("是");
        }

        String shifouLizhi = checkUser.getShifouLizhi();
        if(shifouLizhi.equals("0")){
            checkUser.setShifouLizhi("在职");
        }else{
            checkUser.setShifouLizhi("离职");
        }

        String lizhiLeixing = checkUser.getLizhiLeixing();
        if(lizhiLeixing != null){
            if(lizhiLeixing.equals("0")){
                checkUser.setShifouLizhi("主动");
            }else if(lizhiLeixing.equals("1")){
                checkUser.setShifouLizhi("被动");
            }
        }

        String bumen = checkUser.getBumen();
        String DepartmenId = checkDepartmentService.findByname(bumen);
        Map mapData = new HashMap();
        mapData.put("user",checkUser);
        mapData.put("departmenId",DepartmenId);
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * 修改(新增)员工信息
     * @param req
     * @return
     */
    @RequestMapping(value = "updateEmployee",method = RequestMethod.POST)
    @ResponseBody
    public String updateEmployee(HttpServletRequest req){
        logger.info("app updateEmployee---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String type = (String)jsonData.get("type"); //0新增1修改
        if (StringUtils.isEmpty(type)) return HttpResultUtil.errorJson("类型为空!");
        String number = (String)jsonData.get("number");
        String name = (String)jsonData.get("name");
        String xingbie = (String)jsonData.get("xingbie");
        String bumen = (String)jsonData.get("bumen");
        String erjiBumen = (String)jsonData.get("erjiBumen");
        String gangwei = (String)jsonData.get("gangwei");
        String jishuLeibie = (String)jsonData.get("jishuLeibie");
        String cengjiLeibie = (String)jsonData.get("cengjiLeibie");
        String yuangongType = (String)jsonData.get("yuangongType");
        String ruzhiDate = (String)jsonData.get("ruzhiDate");
        String hetongType = (String)jsonData.get("hetongType");
        String hetongTime = (String)jsonData.get("hetongTime");
        String hetongdaoqiTime = (String)jsonData.get("hetongdaoqiTime");
        String hetongNumber = (String)jsonData.get("hetongNumber");
        String shiyongqiTime = (String)jsonData.get("shiyongqiTime");
        String shiyongqiDate = (String)jsonData.get("shiyongqiDate");
        String hetongLeixing = (String)jsonData.get("hetongLeixing");
        String zhuanzhengDate = (String)jsonData.get("zhuanzhengDate");
        String shenfenzheng = (String)jsonData.get("shenfenzheng");
        String birthday = (String)jsonData.get("birthday");
        String canjiagongzuoDate = (String)jsonData.get("canjiagongzuoDate");
        String jiguan = (String)jsonData.get("jiguan");
        String minzu = (String)jsonData.get("minzu");
        String zhengzhiMianmao = (String)jsonData.get("zhengzhiMianmao");
        String hunyinZhuangkuang = (String)jsonData.get("hunyinZhuangkuang");
        String hujiXingzhi = (String)jsonData.get("hujiXingzhi");
        String diyiXueli = (String)jsonData.get("diyiXueli");
        String diyiZhuanye = (String)jsonData.get("diyiZhuanye");
        String diyiYuanxiao = (String)jsonData.get("diyiYuanxiao");
        String shifouTongzhao = (String)jsonData.get("shifouTongzhao");
        String zuigaoXueli = (String)jsonData.get("zuigaoXueli");
        String zhuanye = (String)jsonData.get("zhuanye");
        String biyeYuanxiao = (String)jsonData.get("biyeYuanxiao");
        String telephone = (String)jsonData.get("telephone");
        String xianzhuzhi = (String)jsonData.get("xianzhuzhi");
        String jinjiLianxiren = (String)jsonData.get("jinjiLianxiren");
        String jinjiTelephone = (String)jsonData.get("jinjiTelephone");
        String yuangongzuodanwei = (String)jsonData.get("yuangongzuodanwei");
        CheckUser checkUser;
        switch (type){
            case "0":
                checkUser = new CheckUser();

                if(StringUtils.isEmpty(number)) return HttpResultUtil.errorJson("工号为空!");
                if(StringUtils.isEmpty(name)) return HttpResultUtil.errorJson("姓名为空!");
                if(StringUtils.isEmpty(xingbie)) return HttpResultUtil.errorJson("性别为空!");
                if(StringUtils.isEmpty(bumen)) return HttpResultUtil.errorJson("部门为空!");
                //if(StringUtils.isEmpty(erjiBumen)) return HttpResultUtil.errorJson("二级部门为空!");
                if(StringUtils.isEmpty(gangwei)) return HttpResultUtil.errorJson("岗位为空!");
                //if(StringUtils.isEmpty(jishuLeibie)) return HttpResultUtil.errorJson("技术类别为空!");
                if(StringUtils.isEmpty(cengjiLeibie)) return HttpResultUtil.errorJson("层级类别为空!");
                if(StringUtils.isEmpty(yuangongType)) return HttpResultUtil.errorJson("员工类型为空!");
                if(StringUtils.isEmpty(ruzhiDate)) return HttpResultUtil.errorJson("入职时间为空!");
                if(StringUtils.isEmpty(hetongType)) return HttpResultUtil.errorJson("合同类型为空!");
                if(StringUtils.isEmpty(hetongTime)) return HttpResultUtil.errorJson("合同年限为空!");
                if(StringUtils.isEmpty(hetongdaoqiTime)) return HttpResultUtil.errorJson("合同到期时间为空!");
                if(StringUtils.isEmpty(hetongNumber)) return HttpResultUtil.errorJson("第几次签订合同为空!");
                if(StringUtils.isEmpty(shiyongqiTime)) return HttpResultUtil.errorJson("试用期期限/月为空!");
                if(StringUtils.isEmpty(shiyongqiDate)) return HttpResultUtil.errorJson("试用期到期日期为空!");
                if(StringUtils.isEmpty(hetongLeixing)) return HttpResultUtil.errorJson("是否转正为空!");//是否转正
                if(StringUtils.isEmpty(zhuanzhengDate)) return HttpResultUtil.errorJson("转正日期为空!");
                if(StringUtils.isEmpty(shenfenzheng)) return HttpResultUtil.errorJson("身份证号为空!");
                if(StringUtils.isEmpty(birthday)) return HttpResultUtil.errorJson("出生日期为空!");
                if(StringUtils.isEmpty(canjiagongzuoDate)) return HttpResultUtil.errorJson("参加工作时间为空!");
                if(StringUtils.isEmpty(jiguan)) return HttpResultUtil.errorJson("籍贯为空!");
                if(StringUtils.isEmpty(minzu)) return HttpResultUtil.errorJson("民族为空!");
                if(StringUtils.isEmpty(zhengzhiMianmao)) return HttpResultUtil.errorJson("政治面貌为空!");
                if(StringUtils.isEmpty(hunyinZhuangkuang)) return HttpResultUtil.errorJson("婚姻状况为空!");
                if(StringUtils.isEmpty(hujiXingzhi)) return HttpResultUtil.errorJson("户籍性质为空!");
                //if(StringUtils.isEmpty(diyiXueli)) return HttpResultUtil.errorJson("第一学历为空!");
                //if(StringUtils.isEmpty(diyiZhuanye)) return HttpResultUtil.errorJson("第一专业为空!");
                //if(StringUtils.isEmpty(diyiYuanxiao)) return HttpResultUtil.errorJson("第一专业毕业院校为空!");
                if(StringUtils.isEmpty(shifouTongzhao)) return HttpResultUtil.errorJson("是否统招为空!");
                if(StringUtils.isEmpty(zuigaoXueli)) return HttpResultUtil.errorJson("最高学历为空!");
                //if(StringUtils.isEmpty(zhuanye)) return HttpResultUtil.errorJson("专业为空!");
                //if(StringUtils.isEmpty(biyeYuanxiao)) return HttpResultUtil.errorJson("毕业院校为空!");
                if(StringUtils.isEmpty(telephone)) return HttpResultUtil.errorJson("联系电话为空!");
                if(StringUtils.isEmpty(xianzhuzhi)) return HttpResultUtil.errorJson("现住址为空!");
                //if(StringUtils.isEmpty(jinjiLianxiren)) return HttpResultUtil.errorJson("紧急联系人为空!");
                //if(StringUtils.isEmpty(jinjiTelephone)) return HttpResultUtil.errorJson("紧急联系人电话为空!");
                //if(StringUtils.isEmpty(yuangongzuodanwei)) return HttpResultUtil.errorJson("原来工作单位为空!");
                break;
            case "1":
                String userId = (String)jsonData.get("userId");
                if (StringUtils.isEmpty(userId)) return HttpResultUtil.errorJson("用户id为空!");
                checkUser = checkUserService.get(userId);
                if (checkUser == null) return HttpResultUtil.errorJson("无此用户!");


                List<CheckUser> byNumber = checkUserService.findByNumber(number);
                if(byNumber.size() != 0){
                    if(!userId.equals(byNumber.get(0).getId())){
                        return HttpResultUtil.errorJson("员工工号重复，请重新填写!");
                    }
                }
                List<CheckUser> byName = checkUserService.findByName(name);
                if(byName.size() != 0){
                    if(!userId.equals(byName.get(0).getId())){
                        return HttpResultUtil.errorJson("员工姓名重复，请重新填写!");
                    }
                }
                break;
            default:
                return HttpResultUtil.errorJson("类型错误!");
        }


        if(type.equals("0")){

            checkUser.setShifouLizhi("0"); //新增 离职状态为 0-在职

            List<CheckUser> byNumber = checkUserService.findByNumber(number);
            if(byNumber.size() != 0){
                return HttpResultUtil.errorJson("员工工号重复，请重新填写!");
            }
            List<CheckUser> byName = checkUserService.findByName(name);
            if(byName.size() != 0){
                return HttpResultUtil.errorJson("员工姓名重复，请重新填写!");
            }
        }
        checkUser.setNumber(number);
        checkUser.setName(name);
        checkUser.setXingbie(xingbie);
        checkUser.setBumen(bumen);
        checkUser.setErjiBumen(erjiBumen);
        checkUser.setGangwei(gangwei);
        if(jishuLeibie.equals("无")) {
            jishuLeibie = "";
        }
        checkUser.setJishuLeibie(jishuLeibie);
        checkUser.setCengjiLeibie(cengjiLeibie);
        checkUser.setYuangongType(yuangongType);
        checkUser.setRuzhiDate(ruzhiDate);
        checkUser.setHetongType(hetongType);
        if(hetongTime.equals("无")) {
            hetongdaoqiTime = "无固定期限";
        }
        checkUser.setHetongTime(hetongTime);
        checkUser.setHetongdaoqiTime(hetongdaoqiTime);
        checkUser.setHetongNumber(hetongNumber);
        checkUser.setShiyongqiTime(shiyongqiTime);
        checkUser.setShiyongqiDate(shiyongqiDate);
        String str = "0";
        if(hetongLeixing.equals("是")){
            str = "0";
        }else{
            str = "1";
        }
        checkUser.setHetongLeixing(str);
        checkUser.setZhuanzhengDate(zhuanzhengDate);
        checkUser.setShenfenzheng(shenfenzheng);
        checkUser.setBirthday(birthday);
        checkUser.setCanjiagongzuoDate(canjiagongzuoDate);
        checkUser.setJiguan(jiguan);
        checkUser.setMinzu(minzu);
        checkUser.setZhengzhiMianmao(zhengzhiMianmao);
        checkUser.setHunyinZhuangkuang(hunyinZhuangkuang);
        checkUser.setHujiXingzhi(hujiXingzhi);
        checkUser.setDiyiXueli(diyiXueli);
        checkUser.setDiyiZhuanye(diyiZhuanye);
        checkUser.setDiyiYuanxiao(diyiYuanxiao);
        checkUser.setShifouTongzhao(shifouTongzhao);
        checkUser.setZuigaoXueli(zuigaoXueli);
        checkUser.setZhuanye(zhuanye);
        checkUser.setBiyeYuanxiao(biyeYuanxiao);
        checkUser.setTelephone(telephone);
        checkUser.setXianzhuzhi(xianzhuzhi);
        checkUser.setJinjiLianxiren(jinjiLianxiren);
        checkUser.setJinjiTelephone(jinjiTelephone);
        checkUser.setYuangongzuodanwei(yuangongzuodanwei);
        checkUserService.save(checkUser);
        return HttpResultUtil.successJson(new HashMap());
    }


    /**
     * 离职
     * @param req
     * @return
     */
    @RequestMapping(value = "updateDimission",method = RequestMethod.POST)
    @ResponseBody
    public String updateDimission(HttpServletRequest req){
        logger.info("app updateDimission---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(req);
        String userId = (String)jsonData.get("userId");
        String type = (String)jsonData.get("type"); //0-主动,1-被动
        if (StringUtils.isEmpty(userId)){
            return HttpResultUtil.errorJson("用户id为空!");
        }
        if (StringUtils.isEmpty(type)){
            return HttpResultUtil.errorJson("type为空!");
        }
        CheckUser checkUser = checkUserService.get(userId);
        if (checkUser == null) {
            return HttpResultUtil.errorJson("无此用户!");
        }
        Date date = new Date();
        checkUser.setShifouLizhi("1");//是否离职 1-离职
        checkUser.setLizhiLeixing(type);
        checkUser.setLizhiTime(sdf.format(date));
        checkUserService.save(checkUser);
        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }



    /**
     * 查询用户信息Excel
     * @return
     */
    @RequestMapping(value = "queryEmployee", method = RequestMethod.POST)
    @ResponseBody
    public String queryAdjustingPosts(HttpServletRequest request) {
        logger.info("查询用户信息Excel queryEmployee ----------Start--------");
        Map mapData = new HashMap();

        Date date = new Date();
        List<CheckUser> userList = checkUserService.findAll();
        List<CheckUser> newList = new ArrayList<>();
        if (userList.size() != 0) {
            int i = 1;
            for (CheckUser checkUser : userList) {
                checkUser.setNo(String.valueOf(i));//序号
                checkUser.setNowDate(sdf.format(date));//现在日期
                String ruzhiDate = checkUser.getRuzhiDate();
                String canjiagongzuoDate = checkUser.getCanjiagongzuoDate();
                try {
                    String format = sdf.format(date);
                    Date parse = sdf.parse(format);
                    if (!ruzhiDate.isEmpty()) {
                        Date date1 = sdf.parse(ruzhiDate);
                        int year1 = DateUtil.getYear(parse, date1);
                        int month1 = DateUtil.getMonth(parse, date1);
                        checkUser.setCompanyAgeMonth(String.valueOf(month1));//司龄
                        checkUser.setCompanyAgeYear(String.valueOf(year1));//司龄/年
                    } else {
                        checkUser.setCompanyAgeMonth("0");//司龄
                        checkUser.setCompanyAgeYear("0");//司龄/年
                    }

                    if (!canjiagongzuoDate.isEmpty()) {
                        Date date2 = sdf.parse(canjiagongzuoDate);
                        int year2 = DateUtil.getYear(parse, date2);
                        int month2 = DateUtil.getMonth(parse, date2);

                        checkUser.setWorkAgeMonth(String.valueOf(month2));//工作年限
                        checkUser.setWorkAgeYear(String.valueOf(year2));//工作年限/年
                    } else {
                        checkUser.setWorkAgeMonth("0");//工作年限
                        checkUser.setWorkAgeYear("0");//工作年限/年
                    }
                } catch (ParseException e) {
                    logger.debug("日期格式错误");
                }
                String bir = checkUser.getBirthday();
                if (bir != null) {
                    try {
                        String age = String.valueOf(DateUtil.getAge(DateUtil.parse(bir)));
                        checkUser.setAge(age);
                    } catch (Exception e) {
                        logger.debug("没有生日");
                    }
                }
                newList.add(checkUser);
                i++;
            }
        }
        mapData.put("userList", newList);
        return HttpResultUtil.successJson(mapData);
    }





    /**
     * 员工导入
     * @param req
     * @return
     */
    @RequestMapping(value = "employeeImport",method = RequestMethod.POST)
    @ResponseBody
    public String employeeImport(HttpServletRequest req,@RequestParam(value = "file", required = false) MultipartFile file){
        logger.info("app employeeImport---------- Start--------");
        try{
            InputStream inputStream1 = file.getInputStream();

            String fileName = file.getOriginalFilename();
            String substring = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
            if(substring.equals("xls")){
                return HttpResultUtil.errorJson("请上传以xlsx结尾的文件！");
            }

            List<CheckUser> checkUserList = new ArrayList<>();
            String number1 = "";
            String name1 = "";



                    //1、获取文件输入流
            //InputStream inputStream = new FileInputStream("D://123.xlsx");
            //2、获取Excel工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream1);
            //3、得到Excel工作表对象
            XSSFSheet sheetAt = workbook.getSheetAt(0);

//            Workbook workbook = null;
//            try {
//                workbook = WorkbookFactory.create(inputStream1);
//            } catch (InvalidFormatException e) {
//                e.printStackTrace();
//            }
//            //3、得到Excel工作表对象
//            Sheet sheetAt = workbook.getSheetAt(2);  //示意访问sheet


            //4、循环读取表格数据
            for (Row row : sheetAt) {
                //首行（即表头）不读取
                if (row.getRowNum() < 3) {
                    continue;
                }
                String stringCellValue = row.getCell(1).getStringCellValue();
                if( stringCellValue == null || stringCellValue.isEmpty()){
                    break;
                }
                //读取当前行中单元格数据，索引从0开始
                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                String number = row.getCell(1).getStringCellValue();

                String name = row.getCell(2).getStringCellValue();

                List<CheckUser> byNumber = checkUserService.findByNumber(number);
                if(byNumber.size() != 0) {
                     number1 = number1 + "," + byNumber.get(0).getNumber();
                }
                List<CheckUser> byName = checkUserService.findByName(name);
                if(byName.size() != 0) {
                     name1 = name1 + "," + byName.get(0).getName();
                }
                String xingbie = row.getCell(3).getStringCellValue();
                String bumen = row.getCell(4).getStringCellValue();
                String erjiBumen = row.getCell(5).getStringCellValue();
                String gangwei = row.getCell(6).getStringCellValue();
                String jishuLeibie = row.getCell(7).getStringCellValue();
                String cengjiLeibie = row.getCell(8).getStringCellValue();
                String yuangongType = row.getCell(9).getStringCellValue();
                String ruzhiDate = DateUtil.UpdateExcelDate(row.getCell(10));//入职时间
                //String ruzhiDate = row.getCell(10).getStringCellValue();
                String hetongType = row.getCell(14).getStringCellValue();
                String hetongTime = row.getCell(15).getStringCellValue();//合同年限

                String hetongdaoqiTime = "";//合同到期时间
                if(hetongTime.equals("无")) {
                    hetongdaoqiTime = "无固定期限";
                }else{
                    //入职时间+合同年限-1天
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse(ruzhiDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);//设置起时间
                    //合同年限截取，去掉后面的年字
                    String str = hetongTime.substring(0, hetongTime.length() - 1);
                    cal.add(Calendar.YEAR, Integer.valueOf(str));//增加N年
                    cal.add(Calendar.DATE, -1);//减1天  
                    Date time = cal.getTime();
                    hetongdaoqiTime = format.format(time);
                }
                //String hetongdaoqiTime = row.getCell(16).getStringCellValue();
                row.getCell(17).setCellType(HSSFCell.CELL_TYPE_STRING);
                String hetongNumber = row.getCell(17).getStringCellValue();
                row.getCell(18).setCellType(HSSFCell.CELL_TYPE_STRING);
                String shiyongqiTime = row.getCell(18).getStringCellValue();

                String shiyongqiDate = "";
                if(shiyongqiTime.isEmpty()) {
                    shiyongqiDate = "";
                }else{
                    //获取试用期到期时间
                    //入职时间+试用期限-1天
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse(ruzhiDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);//设置起时间
                    cal.add(Calendar.MONTH, Integer.valueOf(shiyongqiTime));//增加N月
                    cal.add(Calendar.DATE, -1);//减1天  
                    Date time = cal.getTime();
                    shiyongqiDate = format.format(time);
                }

                //String shiyongqiDate = row.getCell(19).getStringCellValue();
                String hetongLeixing = row.getCell(20).getStringCellValue();// 合同类型(0:未转正 1：转正)
                String zhuanzhengDate = DateUtil.UpdateExcelDate(row.getCell(21));
                //String zhuanzhengDate = row.getCell(21).getStringCellValue();
                row.getCell(22).setCellType(HSSFCell.CELL_TYPE_STRING);
                //通过身份证截取生成生日
                String shenfenzheng = row.getCell(22).getStringCellValue();
                String year = shenfenzheng.substring(6,10);
                String month = shenfenzheng.substring(10,12);
                String date1 = shenfenzheng.substring(12, 14);
                String birthday = year + '-' + month + "-" + date1;
                //String birthday = row.getCell(23).getStringCellValue();

                String canjiagongzuoDate = DateUtil.UpdateExcelDate(row.getCell(25));
                //String canjiagongzuoDate = row.getCell(25).getStringCellValue();
                String jiguan = row.getCell(28).getStringCellValue();
                String minzu = row.getCell(29).getStringCellValue();
                String zhengzhiMianmao = row.getCell(30).getStringCellValue();
                String hunyinZhuangkuang = row.getCell(31).getStringCellValue();
                String hujiXingzhi = row.getCell(32).getStringCellValue();
                String diyiXueli = row.getCell(33).getStringCellValue();
                String diyiZhuanye = row.getCell(34).getStringCellValue();
                String diyiYuanxiao = row.getCell(35).getStringCellValue();
                String shifouTongzhao = row.getCell(36).getStringCellValue();
                String zuigaoXueli = row.getCell(37).getStringCellValue();
                String zhuanye = row.getCell(38).getStringCellValue();
                String biyeYuanxiao = row.getCell(39).getStringCellValue();
                row.getCell(40).setCellType(HSSFCell.CELL_TYPE_STRING);
                String telephone = row.getCell(40).getStringCellValue();
                String xianzhuzhi = row.getCell(41).getStringCellValue();
                String jinjiLianxiren = row.getCell(42).getStringCellValue();
                row.getCell(43).setCellType(HSSFCell.CELL_TYPE_STRING);
                String jinjiTelephone = row.getCell(43).getStringCellValue();
                String yuangongzuodanwei = row.getCell(44).getStringCellValue();


                CheckUser checkUser = new CheckUser();
                checkUser.setNumber(number);
                checkUser.setName(name);
                checkUser.setXingbie(xingbie);
                checkUser.setBumen(bumen);
                checkUser.setErjiBumen(erjiBumen);
                checkUser.setGangwei(gangwei);
                checkUser.setJishuLeibie(jishuLeibie);
                checkUser.setCengjiLeibie(cengjiLeibie);
                checkUser.setYuangongType(yuangongType);
                checkUser.setRuzhiDate(ruzhiDate);
                checkUser.setHetongType(hetongType);
                checkUser.setHetongTime(hetongTime);
                checkUser.setHetongdaoqiTime(hetongdaoqiTime);
                checkUser.setHetongNumber(hetongNumber);
                checkUser.setShiyongqiTime(shiyongqiTime);
                checkUser.setShiyongqiDate(shiyongqiDate);
                String str = "0";
                if(hetongLeixing.equals("是")){
                    str = "0";
                }else{
                    str = "1";
                }
                checkUser.setHetongLeixing(str);
                checkUser.setZhuanzhengDate(zhuanzhengDate);
                checkUser.setShenfenzheng(shenfenzheng);
                checkUser.setBirthday(birthday);
                checkUser.setCanjiagongzuoDate(canjiagongzuoDate);
                checkUser.setJiguan(jiguan);
                checkUser.setMinzu(minzu);
                checkUser.setZhengzhiMianmao(zhengzhiMianmao);
                checkUser.setHunyinZhuangkuang(hunyinZhuangkuang);
                checkUser.setHujiXingzhi(hujiXingzhi);
                checkUser.setDiyiXueli(diyiXueli);
                checkUser.setDiyiZhuanye(diyiZhuanye);
                checkUser.setDiyiYuanxiao(diyiYuanxiao);
                checkUser.setShifouTongzhao(shifouTongzhao);
                checkUser.setZuigaoXueli(zuigaoXueli);
                checkUser.setZhuanye(zhuanye);
                checkUser.setBiyeYuanxiao(biyeYuanxiao);
                checkUser.setTelephone(telephone);
                checkUser.setXianzhuzhi(xianzhuzhi);
                checkUser.setJinjiLianxiren(jinjiLianxiren);
                checkUser.setJinjiTelephone(jinjiTelephone);
                checkUser.setYuangongzuodanwei(yuangongzuodanwei);
                checkUser.setShifouLizhi("0");
                checkUserList.add(checkUser);

            }

            if(name1.isEmpty() && number1.isEmpty()){
                for (CheckUser checkUser : checkUserList) {
                    checkUserService.save(checkUser);
                }
            }else{
                return HttpResultUtil.errorJson("员工名重复:"+ name1 +"  员工号重复:"+number1+"请修改后重新上传！");
            }

            //5、关闭流
            inputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return HttpResultUtil.successJson(new HashMap());
    }


    /**
     * 员工导出
     * @return
     */
    @RequestMapping(value = "employeeExport",method = RequestMethod.POST)
    @ResponseBody
    public String employeeExport(HttpServletRequest request, HttpServletResponse response){
        logger.info("app employeeExport---------- Start--------");

        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        List<CheckUser> userList = checkUserService.findAll();
        List<CheckUser> newList = new ArrayList<>();
        try{
            if (userList.size() != 0){
                int i = 1;
                for (CheckUser checkUser : userList) {
                    checkUser.setNo(String.valueOf(i));//序号
                    checkUser.setNowDate(sdf.format(date));//现在日期
                    String ruzhiDate = checkUser.getRuzhiDate();
                    String canjiagongzuoDate = checkUser.getCanjiagongzuoDate();

//                    String canjiagongzuo = canjiagongzuoDate.substring(0, canjiagongzuoDate.length() - 1);
//                    if(canjiagongzuo.equals("半")) {
//                        canjiagongzuo = "0.5";
//                    }


                    try {
                        String format = sdf.format(date);
                        Date parse = sdf.parse(format);
                        if(!ruzhiDate.isEmpty()){
                            Date date1 = sdf.parse(ruzhiDate);
                            int year1 = DateUtil.getYear(parse,date1);
                            int month1 = DateUtil.getMonth(parse,date1);
                            checkUser.setCompanyAgeMonth(String.valueOf(month1));//司龄
                            checkUser.setCompanyAgeYear(String.valueOf(year1));//司龄/年
                        }else{
                            checkUser.setCompanyAgeMonth("0");//司龄
                            checkUser.setCompanyAgeYear("0");//司龄/年
                        }

                        if(!canjiagongzuoDate.isEmpty()){
                            Date date2 = sdf.parse(canjiagongzuoDate);
                            int year2 = DateUtil.getYear(parse,date2);
                            int month2 = DateUtil.getMonth(parse,date2);

                            checkUser.setWorkAgeMonth(String.valueOf(month2));//工作年限
                            checkUser.setWorkAgeYear(String.valueOf(year2));//工作年限/年
                        }else{
                            checkUser.setWorkAgeMonth("0");//工作年限
                            checkUser.setWorkAgeYear("0");//工作年限/年
                        }
                    } catch (ParseException e) {
                        logger.debug("日期格式错误");
                    }
                    String bir = checkUser.getBirthday();
                    if (bir != null){
                        try{
                            String age = String.valueOf(DateUtil.getAge(DateUtil.parse(bir)));
                            checkUser.setAge(age);
                        }catch (Exception e){
                            logger.debug("没有生日");
                        }
                    }
                    newList.add(checkUser);
                    i++;
                }
            }
            map.put("userList",newList);

            File file = null;
            InputStream inputStream = null;
            ServletOutputStream out = null;
            request.setCharacterEncoding("UTF-8");
            //根据模板类型
            file = ExportExcel.createExcel(map, "myexcel", "employee.ftl", request);
            inputStream = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("员工花名册" + ".xls", "UTF-8"));
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
            return HttpResultUtil.errorJson("导出Excel失败!");
        }
    }



    /**
     * 员工模板
     * @return
     */
    @RequestMapping(value = "excelTemplet",method = RequestMethod.POST)
    @ResponseBody
    public String excelTemplet(HttpServletRequest request, HttpServletResponse response){
        logger.info("app excelTemplet---------- Start--------");

        Map<String, Object> map = new HashMap<String, Object>();
        try{
            File file = null;
            InputStream inputStream = null;
            ServletOutputStream out = null;
            request.setCharacterEncoding("UTF-8");
            //根据模板类型
            file = ExportExcel.createExcel(map, "myexcel", "employee.ftl", request);
            inputStream = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode("员工花名册" + ".xls", "UTF-8"));
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
            return HttpResultUtil.errorJson("导出Excel失败!");
        }
    }




    /**
     * 新增用户字典
     * @return
     */
        @RequestMapping(value = "queryDict",method = RequestMethod.POST)
    @ResponseBody
    public String queryDict(HttpServletRequest request){
        logger.info("app queryDict---------- Start--------");
        List<String> jishuleibie = dictService.findBytype("jishuleibie"); //技术类别
            List list = new ArrayList();
            for (int i = 0; i <jishuleibie.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", jishuleibie.get(i));
                list.add(map);
            }
        List<String> cengjileibie = dictService.findBytype("cengjileibie"); //层级类别
            List list1 = new ArrayList();
            for (int i = 0; i <cengjileibie.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", cengjileibie.get(i));
                list1.add(map);
            }
        List<String> yuangongleixing = dictService.findBytype("yuangong_leixing"); //员工类型
            List list2 = new ArrayList();
            for (int i = 0; i <yuangongleixing.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", yuangongleixing.get(i));
                list2.add(map);
            }
        List<String> hetongleixing = dictService.findBytype("hetong_leixing"); //合同类型
            List list3 = new ArrayList();
            for (int i = 0; i <hetongleixing.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", hetongleixing.get(i));
                list3.add(map);
            }
        List<String> hetongqixian = dictService.findBytype("hetong_qixian"); //合同期限
            List list4 = new ArrayList();
            for (int i = 0; i <hetongqixian.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", hetongqixian.get(i));
                list4.add(map);
            }
        List<String> yes_no = dictService.findBytype("yes_no"); //是-否
            List list5 = new ArrayList();
            for (int i = 0; i <yes_no.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", yes_no.get(i));
                list5.add(map);
            }
        List<String> zhengzhimianmao = dictService.findBytype("zhengzhimianmao"); //政治面貌
            List list6 = new ArrayList();
            for (int i = 0; i <zhengzhimianmao.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", zhengzhimianmao.get(i));
                list6.add(map);
            }
        List<String> hujixingzhi = dictService.findBytype("huji_xingzhi"); //户籍性质
            List list7 = new ArrayList();
            for (int i = 0; i <hujixingzhi.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", hujixingzhi.get(i));
                list7.add(map);
            }
        List<String> xueli = dictService.findBytype("xueli"); //学历
            List list8 = new ArrayList();
            for (int i = 0; i <xueli.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", xueli.get(i));
                list8.add(map);
            }
        List<String> hunyin = dictService.findBytype("hunyin"); //婚姻
            List list9 = new ArrayList();
            for (int i = 0; i <hunyin.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", hunyin.get(i));
                list9.add(map);
            }
        List<String> gangwei = dictService.findBytype("gangwei"); //岗位
            List list10 = new ArrayList();
            for (int i = 0; i <gangwei.size() ; i++) {
                Map map = new HashMap();
                map.put("id", i);
                map.put("a", gangwei.get(i));
                list10.add(map);
            }
        Map mapData = new HashMap();
        mapData.put("jishuleibie",list);
        mapData.put("cengjileibie",list1);
        mapData.put("yuangongleixing",list2);
        mapData.put("hetongleixing",list3);
        mapData.put("hetongqixian",list4);
        mapData.put("yesno",list5);
        mapData.put("zhengzhimianmao",list6);
        mapData.put("hujixingzhi",list7);
        mapData.put("xueli",list8);
        mapData.put("hunyinzhuangkuang",list9);
        mapData.put("gangwei",list10);
        return HttpResultUtil.successJson(mapData);
    }


    /**
     * 部门字典  findParentDepartment 一级部门
     * @return
     */
    @RequestMapping(value = "querySon",method = RequestMethod.POST)
    @ResponseBody
    public String querySon(HttpServletRequest request){
        logger.info("app querySon---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String id = (String)jsonData.get("id");
        if (StringUtils.isEmpty(id)){
            return HttpResultUtil.errorJson("无一级部门ID!");
        }
        List<CheckDepartment> list = checkDepartmentService.querySonById(id);

        Map mapData = new HashMap();
        mapData.put("list",list);
        return HttpResultUtil.successJson(mapData);
    }








    public static void main(String[] args) {
        //获取时间加一年或加一月或加一天
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        String str = "2018-08";
        Date date = null;
        try {
            date = format.parse(str);  // Thu Jan 18 00:00:00 CST 2007
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        //cal.add(Calendar.YEAR, 3);//增加一年
        cal.add(Calendar.MONDAY, -1);//减10天  
        Date time = cal.getTime();
        String format1 = format.format(time);
        System.out.println(format1 );


//        String str = "22018119910123091X";
//        String year = str.substring(6,10);
//        String month = str.substring(10,12);
//        String date = str.substring(12, 14);
//        System.out.println(year);
//        System.out.println(month);
//        System.out.println(date);

    }
}
