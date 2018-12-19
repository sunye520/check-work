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
import com.yogee.youge.modules.check.entity.CheckBusinessDate;
import com.yogee.youge.modules.check.service.CheckBusinessDateService;

/**
 * 时间设置Controller
 * @author sunye
 * @version 2018-12-17
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkBusinessDate")
public class CheckBusinessDateController extends BaseController {

	@Autowired
	private CheckBusinessDateService checkBusinessDateService;
	
	@ModelAttribute
	public CheckBusinessDate get(@RequestParam(required=false) String id) {
		CheckBusinessDate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkBusinessDateService.get(id);
		}
		if (entity == null){
			entity = new CheckBusinessDate();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkBusinessDate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckBusinessDate checkBusinessDate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckBusinessDate> page = checkBusinessDateService.findPage(new Page<CheckBusinessDate>(request, response), checkBusinessDate); 
		model.addAttribute("page", page);
		return "modules/check/checkBusinessDateList";
	}

	@RequiresPermissions("check:checkBusinessDate:view")
	@RequestMapping(value = "form")
	public String form(CheckBusinessDate checkBusinessDate, Model model) {
		model.addAttribute("checkBusinessDate", checkBusinessDate);
		return "modules/check/checkBusinessDateForm";
	}

	@RequiresPermissions("check:checkBusinessDate:edit")
	@RequestMapping(value = "save")
	public String save(CheckBusinessDate checkBusinessDate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkBusinessDate)){
			return form(checkBusinessDate, model);
		}
		checkBusinessDateService.save(checkBusinessDate);
		addMessage(redirectAttributes, "保存时间设置成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkBusinessDate/?repage";
	}
	
	@RequiresPermissions("check:checkBusinessDate:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckBusinessDate checkBusinessDate, RedirectAttributes redirectAttributes) {
		checkBusinessDateService.delete(checkBusinessDate);
		addMessage(redirectAttributes, "删除时间设置成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkBusinessDate/?repage";
	}

}