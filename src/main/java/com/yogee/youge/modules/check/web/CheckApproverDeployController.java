/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.check.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yogee.youge.common.config.Global;
import com.yogee.youge.common.persistence.Page;
import com.yogee.youge.common.web.BaseController;
import com.yogee.youge.common.utils.StringUtils;
import com.yogee.youge.modules.check.entity.CheckApproverDeploy;
import com.yogee.youge.modules.check.service.CheckApproverDeployService;

/**
 * 审核人员配置表Controller
 * @author zhangjian
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkApproverDeploy")
public class CheckApproverDeployController extends BaseController {

	@Autowired
	private CheckApproverDeployService checkApproverDeployService;
	
	@ModelAttribute
	public CheckApproverDeploy get(@RequestParam(required=false) String id) {
		CheckApproverDeploy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkApproverDeployService.get(id);
		}
		if (entity == null){
			entity = new CheckApproverDeploy();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkApproverDeploy:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckApproverDeploy checkApproverDeploy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckApproverDeploy> page = checkApproverDeployService.findPage(new Page<CheckApproverDeploy>(request, response), checkApproverDeploy); 
		model.addAttribute("page", page);
		return "modules/check/checkApproverDeployList";
	}

	@RequiresPermissions("check:checkApproverDeploy:view")
	@RequestMapping(value = "form")
	public String form(CheckApproverDeploy checkApproverDeploy, Model model) {
		model.addAttribute("checkApproverDeploy", checkApproverDeploy);
		return "modules/check/checkApproverDeployForm";
	}

	@RequiresPermissions("check:checkApproverDeploy:edit")
	@RequestMapping(value = "save")
	public String save(CheckApproverDeploy checkApproverDeploy, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkApproverDeploy)){
			return form(checkApproverDeploy, model);
		}
		checkApproverDeployService.save(checkApproverDeploy);
		addMessage(redirectAttributes, "保存审核人员配置表成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkApproverDeploy/?repage";
	}
	
	@RequiresPermissions("check:checkApproverDeploy:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckApproverDeploy checkApproverDeploy, RedirectAttributes redirectAttributes) {
		checkApproverDeployService.delete(checkApproverDeploy);
		addMessage(redirectAttributes, "删除审核人员配置表成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkApproverDeploy/?repage";
	}

}