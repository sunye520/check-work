package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.common.utils.excel.ExportExcel;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckAdjustingPosts;
import com.yogee.youge.modules.check.entity.CheckChangeResult;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckAdjustingPostsService;
import com.yogee.youge.modules.check.service.CheckChangeResultService;
import com.yogee.youge.modules.check.service.CheckDepartmentService;
import com.yogee.youge.modules.check.service.CheckUserService;
import com.yogee.youge.modules.sys.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人员异动情况
 * @author Administrator
 */

@RequestMapping("${apiPath}")
@Controller
public class AbnormalSituationInterface {

    private static final Logger logger = LoggerFactory.getLogger(AbnormalSituationInterface.class);

    @Autowired
    private CheckUserService checkUserService;
    @Autowired
    private CheckAdjustingPostsService checkAdjustingPostsService;
    @Autowired
    private CheckChangeResultService checkChangeResultService;
    @Autowired
    private DictService dictService;
    @Autowired
    private CheckDepartmentService checkDepartmentService;


    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 保存调动信息(修改部门，二级部门，岗位)
     * @return
     */
    @RequestMapping(value = "saveAdjustingPosts", method = RequestMethod.POST)
    @ResponseBody
    public String saveAdjustingPosts(HttpServletRequest request) {
        logger.info("保存调动信息 saveAdjustingPosts ----------Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String userId = (String)jsonData.get("userId");
        String bumen = (String)jsonData.get("bumen");
        String erjiBumen = (String)jsonData.get("erjiBumen");
        String gangwei = (String)jsonData.get("gangwei");
        String jishuLeibie = (String)jsonData.get("jishuLeibie");


        String fromBumen = (String)jsonData.get("fromBumen"); //原部门
        String fromErjiBumen = (String)jsonData.get("fromErjiBumen"); //原二级部门
        String fromGangwei = (String)jsonData.get("fromGangwei"); //原岗位

        if (StringUtils.isEmpty(userId)){
            return HttpResultUtil.errorJson("用户id为空!");
        }
        if (StringUtils.isEmpty(bumen)){
            return HttpResultUtil.errorJson("bumen为空!");
        }
//        if (StringUtils.isEmpty(erjiBumen)){
//            return HttpResultUtil.errorJson("erjiBumen为空!");
//        }
        if (StringUtils.isEmpty(gangwei)){
            return HttpResultUtil.errorJson("gangwei为空!");
        }
//        if (StringUtils.isEmpty(jishuLeibie)){
//            return HttpResultUtil.errorJson("jishuLeibie为空!");
//        }

        CheckUser checkUser = checkUserService.get(userId);
        if (checkUser == null){
            return HttpResultUtil.errorJson("无此用户!");
        }

        if( !bumen.equals(fromBumen) && !erjiBumen.equals(fromErjiBumen) && !gangwei.equals(fromGangwei)){
            checkUser.setBumen(bumen);//部门
            checkUser.setErjiBumen(erjiBumen);//二级部门
            checkUser.setGangwei(gangwei);//岗位

            //岗位变动信息
            CheckAdjustingPosts checkAdjustingPosts = new CheckAdjustingPosts();
            checkAdjustingPosts.setBumen(bumen);
            checkAdjustingPosts.setErjiBumen(erjiBumen);
            checkAdjustingPosts.setGangwei(gangwei);
            checkAdjustingPosts.setJishuLeibie(jishuLeibie);
            checkAdjustingPosts.setFromBumen(fromBumen);
            checkAdjustingPosts.setFromErjiBumen(fromErjiBumen);
            checkAdjustingPosts.setFromGangwei(fromGangwei);

            try {
                checkUserService.save(checkUser);
                checkAdjustingPostsService.save(checkAdjustingPosts);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return HttpResultUtil.errorJson("异常错误!");
            }

        }
            return HttpResultUtil.successJson(new HashMap());
        }


    /**
     * 查询调动信息
     * @return
     */
    @RequestMapping(value = "queryAdjustingPosts", method = RequestMethod.POST)
    @ResponseBody
    public String queryAdjustingPosts(HttpServletRequest request) {
        logger.info("查询调动信息 queryAdjustingPosts ----------Start--------");

        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String year = (String)jsonData.get("year");
        String month = (String)jsonData.get("month");
        if (StringUtils.isEmpty(year)){
            return HttpResultUtil.errorJson("year为空!");
        }
        if (StringUtils.isEmpty(month)){
            return HttpResultUtil.errorJson("month为空!");
        }
        //查询数据
        Map mapData = query(year, month);

        return HttpResultUtil.successJson(mapData);
    }



    /**
     * 导出调动信息Excel
     * @return
     */
    @RequestMapping(value = "downloadAdjustingExcel")
    @ResponseBody
    public String downloadAdjustingExcel(HttpServletRequest request, HttpServletResponse response) {
        logger.info("导出调动信息Excel downloadAdjustingExcel ----------Start--------");
//        Map jsonData = HttpServletRequestUtils.readJsonData(request);
//        String year = (String)jsonData.get("year");
//        String month = (String)jsonData.get("month");
//        String year = request.getParameter("year");
//        String month = request.getParameter("month");
        String yearMonth = request.getParameter("yearMonth");
        if (StringUtils.isEmpty(yearMonth)){
            return HttpResultUtil.errorJson("yearMonth为空!");
        }
        String year = yearMonth.substring(0,4);
        String month =yearMonth.substring(5,7);
//        if (StringUtils.isEmpty(year)){
//            return HttpResultUtil.errorJson("year为空!");
//        }
//        if (StringUtils.isEmpty(month)){
//            return HttpResultUtil.errorJson("month为空!");
//        }

//        String year = "2018";
//        String month = "12";


        try {

            //查询数据
            Map mapData = query(year, month);
            mapData.put("year", year);
            mapData.put("month", month);

            File file = null;
            InputStream inputStream = null;
            ServletOutputStream out = null;
            request.setCharacterEncoding("UTF-8");
            //根据模板类型
            file = ExportExcel.createExcel(mapData,"myexcel","adjusting.ftl",request);
            inputStream = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(year+"-"+month+"月末人员异动统计表空" + ".xls", "UTF-8"));
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



    //查询组装数据
    public Map query(String year, String month) {
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

        }else{  //查询历史

            //先查询统计表，如果没有的话 进行拼装（部门）
            listDepartment = checkChangeResultService.queryChangeResultByTypeYM(createDate, "1");
            if(listDepartment == null || listDepartment.size() == 0){
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
            if(listTechnology == null || listTechnology.size() == 0){
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
            if(listCount.get(0) == null){
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

            return mapData;
        }












        /**
         * 当前月如果没有数据 进行下面操作 查询用户表组成数据
         */

        if(listDepartment == null || listDepartment.size() == 0) {

            //TODO  这里需要获取 部门 进行循环拼接
            //通过type查询部门
            List<String> bumenList = checkDepartmentService.findByDepartmentType("1");
            String str = bumenList.get(0);
            bumenList.remove(0);
            //根据部门查询所有数据
            List<Map> countByTimeDepartment = checkAdjustingPostsService.findCountByTimeDepartment(str, createDate, bumenList, formatDate);
            if (countByTimeDepartment != null) {
                //先删除这个月的数据在新增
                //checkChangeResultService.deleteByDate(createDate,"1"); //真删除
                checkChangeResultService.updateByDate(createDate,"1"); //假删除
                for (Map map : countByTimeDepartment) {
                    CheckChangeResult checkChangeResult = new CheckChangeResult();
                    checkChangeResult.setBumen(map.get("from_bumen") == null ? "" : map.get("from_bumen").toString());//部门
                    checkChangeResult.setBenyueRenshu(map.get("benyue") == null ? "" : map.get("benyue").toString());//本月
                    checkChangeResult.setShangyuemoRenshu(map.get("shangyue") == null ? "" : map.get("shangyue").toString());//上月
                    checkChangeResult.setRuzhi(map.get("ruzhi") == null ? "" : map.get("ruzhi").toString());//入职
                    checkChangeResult.setLizhiZhudong(map.get("zhudonglizhi") == null ? "" : map.get("zhudonglizhi").toString());//主动离职
                    checkChangeResult.setLizhiBeidong(map.get("beidonglizhi") == null ? "" : map.get("beidonglizhi").toString());//被动离职
                    checkChangeResult.setZhuanzheng(map.get("zhuanzheng") == null ? "" : map.get("zhuanzheng").toString());//转正
                    checkChangeResult.setTiaogang(map.get("tiaodong") == null ? "" : map.get("tiaodong").toString());//调动
                    checkChangeResult.setTiaoxin(map.get("tiaoxin") == null ? "" : map.get("tiaoxin").toString());//调薪
                    checkChangeResult.setBeizhu(map.get("beizhu") == null ? "" : map.get("beizhu").toString());//备注
                    checkChangeResult.setType("1");//类型(1-部门，2-技术岗)
                    checkChangeResultService.save(checkChangeResult);
                    listDepartment.add(checkChangeResult);
                }
            }
        }

        if(listTechnology == null || listTechnology.size() == 0) {

            //TODO  这里需要获取 技术类 进行循环拼接
            //通过type查询技术类别
            List<String> jishuleibieList = dictService.findByjishuleibie("jishuleibie");
            String str = jishuleibieList.get(0);
            jishuleibieList.remove(0);
            //根据技术岗位查询所有数据
            List<Map> countByTimeTechnology = checkAdjustingPostsService.findCountByTimeTechnology(str, createDate,jishuleibieList,formatDate);

            if (countByTimeTechnology != null) {
                //先删除这个月的数据在新增
                //checkChangeResultService.deleteByDate(createDate,"2"); //真删除
                checkChangeResultService.updateByDate(createDate,"2"); //假删除
                for (Map map : countByTimeTechnology) {
                    CheckChangeResult checkChangeResult = new CheckChangeResult();
                    checkChangeResult.setBumen(map.get("jishu_leibie") == null ? "" : map.get("jishu_leibie").toString());//部门
                    checkChangeResult.setBenyueRenshu(map.get("benyue") == null ? "" : map.get("benyue").toString());//本月
                    checkChangeResult.setShangyuemoRenshu(map.get("shangyue") == null ? "" : map.get("shangyue").toString());//上月
                    checkChangeResult.setRuzhi(map.get("ruzhi") == null ? "" : map.get("ruzhi").toString());//入职
                    checkChangeResult.setLizhiZhudong(map.get("zhudonglizhi") == null ? "" : map.get("zhudonglizhi").toString());//主动离职
                    checkChangeResult.setLizhiBeidong(map.get("beidonglizhi") == null ? "" : map.get("beidonglizhi").toString());//被动离职
                    checkChangeResult.setZhuanzheng(map.get("zhuanzheng") == null ? "" : map.get("zhuanzheng").toString());//转正
                    checkChangeResult.setTiaogang(map.get("tiaodong") == null ? "" : map.get("tiaodong").toString());//调动
                    checkChangeResult.setTiaoxin(map.get("tiaoxin") == null ? "" : map.get("tiaoxin").toString());//调薪
                    checkChangeResult.setBeizhu(map.get("beizhu") == null ? "" : map.get("beizhu").toString());//备注
                    checkChangeResult.setType("2");//类型(1-部门，2-技术岗)
                    checkChangeResultService.save(checkChangeResult);
                    listTechnology.add(checkChangeResult);
                }
            }
        }


        List<String> bumenLists = checkDepartmentService.findByDepartmentType("1");
        String str = "";
        for (String bumenList : bumenLists) {

            str += "'"+bumenList+"'"+",";
        }
        String bumenlist = str.substring(0,str.length()-1);


        if(listCount.get(0) == null ){
            List<Map> timeTechnologyCount = checkAdjustingPostsService.findTimeTechnologyCount(createDate,formatDate,bumenlist);
            if(timeTechnologyCount != null) {
                listCount = new ArrayList<>();
                CheckChangeResult checkChangeResult = new CheckChangeResult();
                checkChangeResult.setBenyueRenshu(timeTechnologyCount.get(0).get("benyue") == null ? "" : timeTechnologyCount.get(0).get("benyue").toString());//本月
                checkChangeResult.setShangyuemoRenshu(timeTechnologyCount.get(0).get("shangyue") == null ? "" : timeTechnologyCount.get(0).get("shangyue").toString());//上月
                checkChangeResult.setRuzhi(timeTechnologyCount.get(0).get("ruzhi") == null ? "" : timeTechnologyCount.get(0).get("ruzhi").toString());//入职
                checkChangeResult.setLizhiZhudong(timeTechnologyCount.get(0).get("zhudonglizhi") == null ? "" : timeTechnologyCount.get(0).get("zhudonglizhi").toString());//主动离职
                checkChangeResult.setLizhiBeidong(timeTechnologyCount.get(0).get("beidonglizhi") == null ? "" : timeTechnologyCount.get(0).get("beidonglizhi").toString());//被动离职
                checkChangeResult.setZhuanzheng(timeTechnologyCount.get(0).get("zhuanzheng") == null ? "" : timeTechnologyCount.get(0).get("zhuanzheng").toString());//转正
                checkChangeResult.setTiaogang(timeTechnologyCount.get(0).get("tiaodong") == null ? "" : timeTechnologyCount.get(0).get("tiaodong").toString());//调动
                checkChangeResult.setTiaoxin(timeTechnologyCount.get(0).get("tiaoxin") == null ? "" : timeTechnologyCount.get(0).get("tiaoxin").toString());//调薪
                listCount.add(checkChangeResult);
            }
        }
        mapData.put("listDepartment", listDepartment);
        mapData.put("listTechnology", listTechnology);
        mapData.put("listCount", listCount);

        return mapData;
    }



    @RequestMapping(value = "asd",method = RequestMethod.POST)
    @ResponseBody
    public String asd(HttpServletRequest req){
        logger.info("app asd---------- Start--------");

        Map mapData = new HashMap();
        List<String> list = new ArrayList<>();
        list.add("技术部");
        list.add("总裁办");
        List<CheckChangeResult> asd = checkChangeResultService.selectPokemons(list, "经理");
        mapData.put("asd",asd);
        return HttpResultUtil.successJson(mapData);
    }


    public static void main(String[] args){

        String format1 = sdf.format(new Date());


        String str = "2018-12-01";



        String substring = format1.substring(0, 7);
        System.out.println(substring);
    }

}
