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
import com.yogee.youge.modules.check.entity.CheckDepartment;
import com.yogee.youge.modules.check.service.CheckDepartmentService;

/**
 * 部门信息Controller
 * @author sunye
 * @version 2018-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkDepartment")
public class CheckDepartmentController extends BaseController {

	@Autowired
	private CheckDepartmentService checkDepartmentService;
	
	@ModelAttribute
	public CheckDepartment get(@RequestParam(required=false) String id) {
		CheckDepartment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkDepartmentService.get(id);
		}
		if (entity == null){
			entity = new CheckDepartment();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkDepartment:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckDepartment checkDepartment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckDepartment> page = checkDepartmentService.findPage(new Page<CheckDepartment>(request, response), checkDepartment); 
		model.addAttribute("page", page);
		return "modules/check/checkDepartmentList";
	}

	@RequiresPermissions("check:checkDepartment:view")
	@RequestMapping(value = "form")
	public String form(CheckDepartment checkDepartment, Model model) {
		model.addAttribute("checkDepartment", checkDepartment);
		return "modules/check/checkDepartmentForm";
	}

	@RequiresPermissions("check:checkDepartment:edit")
	@RequestMapping(value = "save")
	public String save(CheckDepartment checkDepartment, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkDepartment)){
			return form(checkDepartment, model);
		}
		checkDepartmentService.save(checkDepartment);
		addMessage(redirectAttributes, "保存部门信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkDepartment/?repage";
	}
	
	@RequiresPermissions("check:checkDepartment:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckDepartment checkDepartment, RedirectAttributes redirectAttributes) {
		checkDepartmentService.delete(checkDepartment);
		addMessage(redirectAttributes, "删除部门信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkDepartment/?repage";
	}

}