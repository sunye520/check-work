package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.excel.ExportExcel;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckEmployeeAnalystService;
import com.yogee.youge.modules.check.service.CheckUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工结构分析
 * author:cheng
 */
@Controller
@RequestMapping("${apiPath}")
public class EmployeeAnalystInterface {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeAnalystInterface.class);
    private final static SimpleDateFormat sdf1;

    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private CheckEmployeeAnalystService checkEmployeeAnalystService;

    static {
        sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 员工统计分析
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "export", method = RequestMethod.POST)
    @ResponseBody
    public String exportEmployeeAnalystList(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response) {
        logger.info("app exportEmployeeAnalystList---------- Start--------");
        Map<String, Object> mapData = new HashMap<String, Object>();
        try {
            long total = checkUserService.findAllCount(checkUser);//在职员工总数

            List<Map> genderMap = checkEmployeeAnalystService.findCheckUserByGender();
            for (Map m : genderMap) {
                String gender = (String) m.get("xingbie");
                long count = (long) m.get("count");
                double d = count * 1.0 / total * 1.f;
                if("男".equals(gender)){
                    mapData.put("manCount",m.get("count"));
                    mapData.put("manRatio", d);
                }else if("女".equals(gender)){
                    mapData.put("womanCount",m.get("count"));
                    mapData.put("womanRatio", d);
                }
            }

            File file = null;
            InputStream inputStream = null;
            ServletOutputStream out = null;
            request.setCharacterEncoding("UTF-8");
            //根据模板类型
            file = ExportExcel.createExcel(mapData, "myexcel", "user.ftl", request);
            inputStream = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工结构分析" + ".xls", "UTF-8"));
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
            return HttpResultUtil.errorJson("异常错误!");
        }
    }

    /**
     * 员工统计分析
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "analyst", method = RequestMethod.POST)
    @ResponseBody
    public String employeeAnalystList(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response) {
        logger.info("app employeeList---------- Start--------");
        Map<String,Object> mapData = new HashMap<>();
        List<Map> genderMap = checkEmployeeAnalystService.findCheckUserByGender();//性别分布
        List<Map> educationMap = checkEmployeeAnalystService.findCheckUserByEducation();//学历分布
        List<Map> technologyMap = checkEmployeeAnalystService.findCheckUserByTechnology();//技术类型分布
        List<Map> levelMap = checkEmployeeAnalystService.findCheckUserByLevel();//层级(高层,中层,基层)分布
        List<Map> politicsMap = checkEmployeeAnalystService.findCheckUserByPolitics();//政治面貌分布
        List<Map> departmentMap = checkEmployeeAnalystService.findCheckUserByDepartment();//部门分布
        List<Map<String,Object>> ageMap = checkEmployeeAnalystService.findCheckUserByAge();//年龄分布
        List<Map<String,Object>> workingYearsMap = checkEmployeeAnalystService.findCheckUserByWorkingYears();//司龄分布
        mapData.put("gender",genderMap);
        mapData.put("education",educationMap);
        mapData.put("technology",technologyMap);
        mapData.put("level",levelMap);
        mapData.put("politics",politicsMap);
        mapData.put("department",departmentMap);
        mapData.put("age",ageMap);
        mapData.put("workingYears",workingYearsMap);
        return HttpResultUtil.successJson(mapData);
    }

    /**
     * excel 数据填充
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "excelData", method = RequestMethod.POST)
    @ResponseBody
    public String excelDataList(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response) {
        logger.info("app employeeList---------- Start--------");
        Map<String,Object> mapData = new HashMap<>();
        Map excelMap = checkEmployeeAnalystService.findExcelData();
        mapData.put("excelMap",excelMap);
        return HttpResultUtil.successJson(mapData);
    }
}
