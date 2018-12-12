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
import com.yogee.youge.modules.check.entity.CheckApprover;
import com.yogee.youge.modules.check.service.CheckApproverService;

/**
 * 审批Controller
 * @author zhangjian
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkApprover")
public class CheckApproverController extends BaseController {

	@Autowired
	private CheckApproverService checkApproverService;
	
	@ModelAttribute
	public CheckApprover get(@RequestParam(required=false) String id) {
		CheckApprover entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkApproverService.get(id);
		}
		if (entity == null){
			entity = new CheckApprover();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkApprover:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckApprover checkApprover, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckApprover> page = checkApproverService.findPage(new Page<CheckApprover>(request, response), checkApprover); 
		model.addAttribute("page", page);
		return "modules/check/checkApproverList";
	}

	@RequiresPermissions("check:checkApprover:view")
	@RequestMapping(value = "form")
	public String form(CheckApprover checkApprover, Model model) {
		model.addAttribute("checkApprover", checkApprover);
		return "modules/check/checkApproverForm";
	}

	@RequiresPermissions("check:checkApprover:edit")
	@RequestMapping(value = "save")
	public String save(CheckApprover checkApprover, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkApprover)){
			return form(checkApprover, model);
		}
		checkApproverService.save(checkApprover);
		addMessage(redirectAttributes, "保存审批成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkApprover/?repage";
	}
	
	@RequiresPermissions("check:checkApprover:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckApprover checkApprover, RedirectAttributes redirectAttributes) {
		checkApproverService.delete(checkApprover);
		addMessage(redirectAttributes, "删除审批成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkApprover/?repage";
	}

}