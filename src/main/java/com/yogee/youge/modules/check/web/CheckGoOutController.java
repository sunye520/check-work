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
import com.yogee.youge.modules.check.entity.CheckGoOut;
import com.yogee.youge.modules.check.service.CheckGoOutService;

/**
 * 外出Controller
 * @author zhangjian
 * @version 2018-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkGoOut")
public class CheckGoOutController extends BaseController {

	@Autowired
	private CheckGoOutService checkGoOutService;
	
	@ModelAttribute
	public CheckGoOut get(@RequestParam(required=false) String id) {
		CheckGoOut entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkGoOutService.get(id);
		}
		if (entity == null){
			entity = new CheckGoOut();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkGoOut:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckGoOut checkGoOut, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckGoOut> page = checkGoOutService.findPage(new Page<CheckGoOut>(request, response), checkGoOut); 
		model.addAttribute("page", page);
		return "modules/check/checkGoOutList";
	}

	@RequiresPermissions("check:checkGoOut:view")
	@RequestMapping(value = "form")
	public String form(CheckGoOut checkGoOut, Model model) {
		model.addAttribute("checkGoOut", checkGoOut);
		return "modules/check/checkGoOutForm";
	}

	@RequiresPermissions("check:checkGoOut:edit")
	@RequestMapping(value = "save")
	public String save(CheckGoOut checkGoOut, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkGoOut)){
			return form(checkGoOut, model);
		}
		checkGoOutService.save(checkGoOut);
		addMessage(redirectAttributes, "保存外出成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkGoOut/?repage";
	}
	
	@RequiresPermissions("check:checkGoOut:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckGoOut checkGoOut, RedirectAttributes redirectAttributes) {
		checkGoOutService.delete(checkGoOut);
		addMessage(redirectAttributes, "删除外出成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkGoOut/?repage";
	}

}