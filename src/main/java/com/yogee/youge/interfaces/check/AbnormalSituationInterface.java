package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckAdjustingPosts;
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckAdjustingPostsService;
import com.yogee.youge.modules.check.service.CheckUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    /**
     * 查询企划文件列表
     * @param request
     * @return
     */
    @RequestMapping(value = "downloadTransaction", method = RequestMethod.POST)
    @ResponseBody
    public String downloadTransaction(HttpServletRequest request,HttpServletResponse response) {
        logger.info("查询企划文件列表 downloadTransaction ----------Start--------");

        Map mapData = new HashMap();

        return HttpResultUtil.successJson(mapData);
    }


    /**
     * 保存调动信息
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
        if (StringUtils.isEmpty(erjiBumen)){
            return HttpResultUtil.errorJson("erjiBumen为空!");
        }
        if (StringUtils.isEmpty(gangwei)){
            return HttpResultUtil.errorJson("gangwei为空!");
        }
        if (StringUtils.isEmpty(jishuLeibie)){
            return HttpResultUtil.errorJson("jishuLeibie为空!");
        }

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
        Map mapData = new HashMap();


        List<String> str = new ArrayList<>();
        str.add("行政部");
        str.add("人事部");

        List<CheckAdjustingPosts> asdasda = checkAdjustingPostsService.selectPokemons(str, "技术部");
        for (CheckAdjustingPosts checkAdjustingPosts : asdasda) {
            System.out.println(checkAdjustingPosts.getId());
        }


        List<Map> listDepartment = new ArrayList<>();
        List<Map> listTechnology = new ArrayList<>();

        //获取当前年月
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String createDate = sdf.format(d);

        //根据部门查询调岗记录数
        List<Map> countByTimeDepartment = checkAdjustingPostsService.findCountByTimeDepartment("技术部",createDate);
        for (Map map : countByTimeDepartment) {
            Map map1 = new HashMap();
            map1.put("bumen",map.get("from_bumen") == null ? "" : map.get("from_bumen").toString());//部门
            map1.put("benyue",map.get("benyue") == null ? "" : map.get("benyue").toString());//本月
            map1.put("shangyue",map.get("shangyue") == null ? "" : map.get("shangyue").toString());//上月
            map1.put("ruzhi",map.get("ruzhi") == null ? "" : map.get("ruzhi").toString());//入职
            map1.put("zhudonglizhi",map.get("zhudonglizhi") == null ? "" : map.get("zhudonglizhi").toString());//主动离职
            map1.put("beidonglizhi",map.get("beidonglizhi") == null ? "" : map.get("beidonglizhi").toString());//被动离职
            map1.put("zhuangzheng",map.get("zhuangzheng") == null ? "" : map.get("zhuangzheng").toString());//转正
            map1.put("diaodong",map.get("diaodong") == null ? "" : map.get("diaodong").toString());//调动
            map1.put("tiaoxin",map.get("tiaoxin") == null ? "" : map.get("tiaoxin").toString());//调薪
            map1.put("beizhu",map.get("beizhu") == null ? "" : map.get("beizhu").toString());//备注
            listDepartment.add(map1);
        }

        //根据技术岗位查询调岗记录数
        List<Map> countByTimeTechnology = checkAdjustingPostsService.findCountByTimeTechnology("JAVA", createDate);
        for (Map map : countByTimeTechnology) {
            Map map1 = new HashMap();
            map1.put("bumen",map.get("jishu_leibie") == null ? "" : map.get("jishu_leibie").toString());//部门
            map1.put("benyue",map.get("benyue") == null ? "" : map.get("benyue").toString());//本月
            map1.put("shangyue",map.get("shangyue") == null ? "" : map.get("shangyue").toString());//上月
            map1.put("ruzhi",map.get("ruzhi") == null ? "" : map.get("ruzhi").toString());//入职
            map1.put("zhudonglizhi",map.get("zhudonglizhi") == null ? "" : map.get("zhudonglizhi").toString());//主动离职
            map1.put("beidonglizhi",map.get("beidonglizhi") == null ? "" : map.get("beidonglizhi").toString());//被动离职
            map1.put("zhuangzheng",map.get("zhuangzheng") == null ? "" : map.get("zhuangzheng").toString());//转正
            map1.put("diaodong",map.get("diaodong") == null ? "" : map.get("diaodong").toString());//调动
            map1.put("tiaoxin",map.get("tiaoxin") == null ? "" : map.get("tiaoxin").toString());//调薪
            map1.put("beizhu",map.get("beizhu") == null ? "" : map.get("beizhu").toString());//备注
            listTechnology.add(map1);
        }
        mapData.put("listDepartment", listDepartment);
        mapData.put("listTechnology", listTechnology);
        return HttpResultUtil.successJson(mapData);
    }

}
