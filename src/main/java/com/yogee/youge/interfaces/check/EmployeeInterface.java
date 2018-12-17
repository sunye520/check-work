package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.common.utils.excel.ExportExcel;
import com.yogee.youge.interfaces.util.DateUtil;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckUserService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private final static SimpleDateFormat sdf1;

    @Autowired
    private CheckUserService checkUserService;

    static {
        sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    }

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
                map.put("nowDate",sdf1.format(date));   //现在日期
                String ruzhiDate = map.get("ruzhi_date")==null?"":map.get("ruzhi_date").toString(); //入职日期
                String canjiagongzuoDate = map.get("ruzhi_date")==null?"":map.get("ruzhi_date").toString();  //参加工作日期
                try {
                    Date date1 = sdf1.parse(ruzhiDate);
                    Date date2 = sdf1.parse(canjiagongzuoDate);
                    int year1 = DateUtil.getYear(date1,date);
                    int month1 = DateUtil.getMonth(date1,date);
                    int year2 = DateUtil.getYear(date2,date);
                    int month2 = DateUtil.getMonth(date2,date);
                    map.put("workAgeYear",year2);
                    map.put("workAgeMonth",month2);
                    map.put("companyAgeYear",year1);
                    map.put("companyAgeMonth",month1);
                } catch (ParseException e) {
                    e.printStackTrace();
                    map.put("workAgeYear","");
                    map.put("workAgeMonth","");
                    map.put("companyAgeYear","");
                    map.put("companyAgeMonth","");
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
        Map mapData = new HashMap();
        mapData.put("user",checkUser);
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
                checkUser.setShifouLizhi("0");
                if(StringUtils.isEmpty(number)) return HttpResultUtil.errorJson("工号为空!");
                if(StringUtils.isEmpty(name)) return HttpResultUtil.errorJson("姓名为空!");
                if(StringUtils.isEmpty(xingbie)) return HttpResultUtil.errorJson("性别为空!");
                if(StringUtils.isEmpty(bumen)) return HttpResultUtil.errorJson("部门为空!");
                if(StringUtils.isEmpty(erjiBumen)) return HttpResultUtil.errorJson("二级部门为空!");
                if(StringUtils.isEmpty(gangwei)) return HttpResultUtil.errorJson("岗位为空!");
                if(StringUtils.isEmpty(jishuLeibie)) return HttpResultUtil.errorJson("技术类别为空!");
                if(StringUtils.isEmpty(cengjiLeibie)) return HttpResultUtil.errorJson("层级类别为空!");
                if(StringUtils.isEmpty(yuangongType)) return HttpResultUtil.errorJson("员工类型为空!");
                if(StringUtils.isEmpty(ruzhiDate)) return HttpResultUtil.errorJson("入职时间为空!");
                if(StringUtils.isEmpty(hetongType)) return HttpResultUtil.errorJson("合同类型为空!");
                if(StringUtils.isEmpty(hetongTime)) return HttpResultUtil.errorJson("合同年限为空!");
                if(StringUtils.isEmpty(hetongNumber)) return HttpResultUtil.errorJson("第几次签订合同为空!");
                if(StringUtils.isEmpty(shiyongqiTime)) return HttpResultUtil.errorJson("试用期期限/月为空!");
                if(StringUtils.isEmpty(shiyongqiDate)) return HttpResultUtil.errorJson("试用期到期日期为空!");
                if(StringUtils.isEmpty(hetongLeixing)) return HttpResultUtil.errorJson("合同类型为空!");
                if(StringUtils.isEmpty(zhuanzhengDate)) return HttpResultUtil.errorJson("转正日期为空!");
                if(StringUtils.isEmpty(shenfenzheng)) return HttpResultUtil.errorJson("身份证号为空!");
                if(StringUtils.isEmpty(birthday)) return HttpResultUtil.errorJson("出生日期为空!");
                if(StringUtils.isEmpty(canjiagongzuoDate)) return HttpResultUtil.errorJson("参加工作时间为空!");
                if(StringUtils.isEmpty(jiguan)) return HttpResultUtil.errorJson("籍贯为空!");
                if(StringUtils.isEmpty(minzu)) return HttpResultUtil.errorJson("民族为空!");
                if(StringUtils.isEmpty(zhengzhiMianmao)) return HttpResultUtil.errorJson("政治面貌为空!");
                if(StringUtils.isEmpty(hunyinZhuangkuang)) return HttpResultUtil.errorJson("婚姻状况为空!");
                if(StringUtils.isEmpty(hujiXingzhi)) return HttpResultUtil.errorJson("户籍性质为空!");
                if(StringUtils.isEmpty(diyiXueli)) return HttpResultUtil.errorJson("第一学历为空!");
                if(StringUtils.isEmpty(diyiZhuanye)) return HttpResultUtil.errorJson("第一专业为空!");
                if(StringUtils.isEmpty(diyiYuanxiao)) return HttpResultUtil.errorJson("第一专业毕业院校为空!");
                if(StringUtils.isEmpty(shifouTongzhao)) return HttpResultUtil.errorJson("是否统招为空!");
                if(StringUtils.isEmpty(zuigaoXueli)) return HttpResultUtil.errorJson("最高学历为空!");
                if(StringUtils.isEmpty(zhuanye)) return HttpResultUtil.errorJson("专业为空!");
                if(StringUtils.isEmpty(biyeYuanxiao)) return HttpResultUtil.errorJson("毕业院校为空!");
                if(StringUtils.isEmpty(telephone)) return HttpResultUtil.errorJson("联系电话为空!");
                if(StringUtils.isEmpty(xianzhuzhi)) return HttpResultUtil.errorJson("现住址为空!");
                if(StringUtils.isEmpty(jinjiLianxiren)) return HttpResultUtil.errorJson("紧急联系人为空!");
                if(StringUtils.isEmpty(jinjiTelephone)) return HttpResultUtil.errorJson("紧急联系人电话为空!");
                if(StringUtils.isEmpty(yuangongzuodanwei)) return HttpResultUtil.errorJson("原来工作单位为空!");
                break;
            case "1":
                String userId = (String)jsonData.get("userId");
                if (StringUtils.isEmpty(userId)) return HttpResultUtil.errorJson("用户id为空!");
                checkUser = checkUserService.get(userId);
                if (checkUser == null) return HttpResultUtil.errorJson("无此用户!");
                break;
            default:
                return HttpResultUtil.errorJson("类型错误!");
        }
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
        checkUser.setHetongNumber(hetongNumber);
        checkUser.setShiyongqiTime(shiyongqiTime);
        checkUser.setShiyongqiDate(shiyongqiDate);
        checkUser.setHetongLeixing(hetongLeixing);
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
     * 员工导入
     * @param req
     * @return
     */
    @RequestMapping(value = "employeeImport",method = RequestMethod.POST)
    @ResponseBody
    public String employeeImport(HttpServletRequest req,MultipartFile file){
        logger.info("app employeeImport---------- Start--------");
        List<List<String>> lists = new ArrayList<List<String>>();
        String name = file.getOriginalFilename();
        System.out.println(name);
        String fileType = name.substring(name.lastIndexOf(".") + 1);
        CommonsMultipartFile cf = (CommonsMultipartFile)file;
        DiskFileItem fi = (DiskFileItem)cf.getFileItem();
        File f = fi.getStoreLocation();
        InputStream is;
        try {
            is = new FileInputStream(f);
            //获取工作薄
            Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }

            //读取第一个工作页sheet
            Sheet sheet = wb.getSheetAt(0);
            //第一行为标题
            for (Row row : sheet) {
                ArrayList<String> list = new ArrayList<String>();
                for (Cell cell : row) {
                    //根据不同类型转化成字符串
                    if (cell.equals("")){
                        list.add("");
                    }else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        list.add(cell.getStringCellValue());
                    }
                }
                lists.add(list);
            }
            System.out.println(lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResultUtil.successJson(new HashMap());
    }


    /**
     * 员工导出
     * @param req
     * @return
     */
    @RequestMapping(value = "employeeExport",method = RequestMethod.POST)
    @ResponseBody
    public String employeeExport(HttpServletRequest req, HttpServletResponse res){
        logger.info("app employeeExport---------- Start--------");
//        try {
//            String fileName = "xxx.xlsx";
//            ExportExcel exportExcel = new ExportExcel("123", CheckUser.class);
//            exportExcel.setDataList(new ArrayList<>());
//            exportExcel.write(res, fileName).dispose();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
