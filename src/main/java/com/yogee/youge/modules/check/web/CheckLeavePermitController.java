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
import com.yogee.youge.modules.check.entity.CheckLeavePermit;
import com.yogee.youge.modules.check.service.CheckLeavePermitService;

/**
 * 假条Controller
 * @author zhangjian
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkLeavePermit")
public class CheckLeavePermitController extends BaseController {

	@Autowired
	private CheckLeavePermitService checkLeavePermitService;
	
	@ModelAttribute
	public CheckLeavePermit get(@RequestParam(required=false) String id) {
		CheckLeavePermit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkLeavePermitService.get(id);
		}
		if (entity == null){
			entity = new CheckLeavePermit();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkLeavePermit:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckLeavePermit checkLeavePermit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckLeavePermit> page = checkLeavePermitService.findPage(new Page<CheckLeavePermit>(request, response), checkLeavePermit); 
		model.addAttribute("page", page);
		return "modules/check/checkLeavePermitList";
	}

	@RequiresPermissions("check:checkLeavePermit:view")
	@RequestMapping(value = "form")
	public String form(CheckLeavePermit checkLeavePermit, Model model) {
		model.addAttribute("checkLeavePermit", checkLeavePermit);
		return "modules/check/checkLeavePermitForm";
	}

	@RequiresPermissions("check:checkLeavePermit:edit")
	@RequestMapping(value = "save")
	public String save(CheckLeavePermit checkLeavePermit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkLeavePermit)){
			return form(checkLeavePermit, model);
		}
		checkLeavePermitService.save(checkLeavePermit);
		addMessage(redirectAttributes, "保存假条成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkLeavePermit/?repage";
	}
	
	@RequiresPermissions("check:checkLeavePermit:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckLeavePermit checkLeavePermit, RedirectAttributes redirectAttributes) {
		checkLeavePermitService.delete(checkLeavePermit);
		addMessage(redirectAttributes, "删除假条成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkLeavePermit/?repage";
	}

}