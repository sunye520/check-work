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
import com.yogee.youge.modules.check.entity.CheckTime;
import com.yogee.youge.modules.check.service.CheckTimeService;

/**
 * 上班时间Controller
 * @author cheng
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkTime")
public class CheckTimeController extends BaseController {

	@Autowired
	private CheckTimeService checkTimeService;
	
	@ModelAttribute
	public CheckTime get(@RequestParam(required=false) String id) {
		CheckTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkTimeService.get(id);
		}
		if (entity == null){
			entity = new CheckTime();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkTime:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckTime checkTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckTime> page = checkTimeService.findPage(new Page<CheckTime>(request, response), checkTime); 
		model.addAttribute("page", page);
		return "modules/check/checkTimeList";
	}

	@RequiresPermissions("check:checkTime:view")
	@RequestMapping(value = "form")
	public String form(CheckTime checkTime, Model model) {
		model.addAttribute("checkTime", checkTime);
		return "modules/check/checkTimeForm";
	}

	@RequiresPermissions("check:checkTime:edit")
	@RequestMapping(value = "save")
	public String save(CheckTime checkTime, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkTime)){
			return form(checkTime, model);
		}
		checkTimeService.save(checkTime);
		addMessage(redirectAttributes, "保存上班时间成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkTime/?repage";
	}
	
	@RequiresPermissions("check:checkTime:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckTime checkTime, RedirectAttributes redirectAttributes) {
		checkTimeService.delete(checkTime);
		addMessage(redirectAttributes, "删除上班时间成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkTime/?repage";
	}

}