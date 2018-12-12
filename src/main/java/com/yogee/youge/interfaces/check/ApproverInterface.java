package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
import com.yogee.youge.modules.check.entity.CheckApproverDeploy;
import com.yogee.youge.modules.check.entity.CheckGoOut;
import com.yogee.youge.modules.check.entity.CheckLeavePermit;
import com.yogee.youge.modules.check.service.CheckApproverDeployService;
import com.yogee.youge.modules.check.service.CheckGoOutService;
import com.yogee.youge.modules.check.service.CheckLeavePermitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/12.
 */
@Controller
@RequestMapping("${apiPath}")
public class ApproverInterface {

    private static final Logger logger = LoggerFactory.getLogger(ApproverInterface.class);

    @Autowired
    private CheckGoOutService checkGoOutService; //外出表
    @Autowired
    private CheckLeavePermitService checkLeavePermitService; //请假表
    @Autowired
    private CheckApproverDeployService checkApproverDeployService; //审核人员配置表



    /**
     * 我发起的审批列表
     * @param request
     * @return
     */
    @RequestMapping(value = "myLaunchApprover",method = RequestMethod.POST)
    @ResponseBody
    public String myLaunchApprover(HttpServletRequest request){
        logger.info("app myLaunchApprover---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String total = (String) jsonData.get("total");
        String count = (String) jsonData.get("count");
        String type = (String) jsonData.get("type");   //1-外出，2-假条
        String userId = (String) jsonData.get("userId");
        if (StringUtils.isEmpty(total) || StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("total,count值为空");
        }
        if (StringUtils.isEmpty(type)) {
            return HttpResultUtil.errorJson("type值为空!");
        }
        if (StringUtils.isEmpty(userId)) {
            return HttpResultUtil.errorJson("userId值为空!");
        }
        Map mapData = new HashMap();
        if(type.equals("1")) {
            List<CheckGoOut> checkGoOuts = checkGoOutService.queryAllByUserid(total, count, userId);
            String sum = checkGoOutService.queryAllCount();
            mapData.put("list", checkGoOuts);
            mapData.put("sum", sum);
        }else {
            List<CheckLeavePermit> checkLeavePermits = checkLeavePermitService.queryAllByUserid(total, count, userId);
            String sum = checkLeavePermitService.queryAllCount();
            mapData.put("list", checkLeavePermits);
            mapData.put("sum", sum);
        }
        return HttpResultUtil.successJson(mapData);
    }


    /**
     * 我发起的审批列表
     * @param request
     * @return
     */
    @RequestMapping(value = "myLaunchApproverInfo",method = RequestMethod.POST)
    @ResponseBody
    public String myLaunchApproverInfo(HttpServletRequest request){
        logger.info("app myLaunchApproverInfo---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String type = (String) jsonData.get("type");   //1-外出，2-假条
        String id = (String) jsonData.get("id");
        if (StringUtils.isEmpty(type)) {
            return HttpResultUtil.errorJson("type值为空!");
        }
        if (StringUtils.isEmpty(id)) {
            return HttpResultUtil.errorJson("id值为空!");
        }
        Map mapData = new HashMap();
        if(type.equals("1")) {  //外出
            CheckGoOut checkGoOut = checkGoOutService.get(id);





        }else {

            CheckLeavePermit checkLeavePermit = checkLeavePermitService.get(id);
            mapData.put("userName", checkLeavePermit.getUserName()); //用户名称
            mapData.put("approverType", checkLeavePermit.getApproverType()); //审核状态（1-审核成功，2-审核失败，3-审核中）

            String departmentId = checkLeavePermit.getDepartmentId();
            //所在部门
            String departmentName = "";
            mapData.put("departmentName", departmentName); //部门名称
            String leaveType = checkLeavePermit.getType();
            mapData.put("leaveType", leaveType); //请假类型
            String time = checkLeavePermit.getTime();
            mapData.put("time" , time); //时长
            mapData.put("content" , checkLeavePermit.getContent()); //请假事由
            mapData.put("startTime", checkLeavePermit.getStartTime()); //开始时间
            mapData.put("endTime", checkLeavePermit.getEndTime()); //结束时间

            Integer integer = Integer.valueOf(time);
            if(integer <= 1) {
                time = "1";               //1天
            }else if(integer > 1 && integer < 4){
                time = "2";              //1天以上~3天
            }else if(integer > 4){
                time = "3";              //3天以上
            }
            //获取抄送人列表
            CheckApproverDeploy checkApproverDeploy = checkApproverDeployService.queryDeployInfo(departmentId, leaveType, time);
            String ccId = checkApproverDeploy.getCcId();







        }
        return HttpResultUtil.successJson(mapData);
    }






}
