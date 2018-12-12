package com.yogee.youge.interfaces.check;

import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.interfaces.util.HttpResultUtil;
import com.yogee.youge.interfaces.util.HttpServletRequestUtils;
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
    private CheckLeavePermitService checkLeavePermitService;//请假表




    /**
     * 我发起的审批列表
     * @param request
     * @return
     */
    @RequestMapping(value = "myLaunchApprover",method = RequestMethod.POST)
    @ResponseBody
    public String queryAd(HttpServletRequest request){
        logger.info("app myLaunchApprover---------- Start--------");
        Map jsonData = HttpServletRequestUtils.readJsonData(request);
        String total = (String) jsonData.get("total");
        String count = (String) jsonData.get("count");
        String userId = (String) jsonData.get("userId");
        if (StringUtils.isEmpty(total) || StringUtils.isEmpty(count)) {
            return HttpResultUtil.errorJson("total,count值为空");
        }








        Map mapData = new HashMap();
        return HttpResultUtil.successJson(mapData);
    }
}
