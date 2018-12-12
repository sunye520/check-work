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
import com.yogee.youge.modules.check.entity.CheckWorkDate;
import com.yogee.youge.modules.check.service.CheckWorkDateService;

/**
 * 排班Controller
 * @author cheng
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkWorkDate")
public class CheckWorkDateController extends BaseController {

	@Autowired
	private CheckWorkDateService checkWorkDateService;
	
	@ModelAttribute
	public CheckWorkDate get(@RequestParam(required=false) String id) {
		CheckWorkDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkWorkDateService.get(id);
		}
		if (entity == null){
			entity = new CheckWorkDate();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkWorkDate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckWorkDate checkWorkDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckWorkDate> page = checkWorkDateService.findPage(new Page<CheckWorkDate>(request, response), checkWorkDate); 
		model.addAttribute("page", page);
		return "modules/check/checkWorkDateList";
	}

	@RequiresPermissions("check:checkWorkDate:view")
	@RequestMapping(value = "form")
	public String form(CheckWorkDate checkWorkDate, Model model) {
		model.addAttribute("checkWorkDate", checkWorkDate);
		return "modules/check/checkWorkDateForm";
	}

	@RequiresPermissions("check:checkWorkDate:edit")
	@RequestMapping(value = "save")
	public String save(CheckWorkDate checkWorkDate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkWorkDate)){
			return form(checkWorkDate, model);
		}
		checkWorkDateService.save(checkWorkDate);
		addMessage(redirectAttributes, "保存排班成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkWorkDate/?repage";
	}
	
	@RequiresPermissions("check:checkWorkDate:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckWorkDate checkWorkDate, RedirectAttributes redirectAttributes) {
		checkWorkDateService.delete(checkWorkDate);
		addMessage(redirectAttributes, "删除排班成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkWorkDate/?repage";
	}

}