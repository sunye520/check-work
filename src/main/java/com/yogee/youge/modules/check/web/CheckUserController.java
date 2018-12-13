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
import com.yogee.youge.modules.check.entity.CheckUser;
import com.yogee.youge.modules.check.service.CheckUserService;

/**
 * 员工信息Controller
 * @author sunye
 * @version 2018-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkUser")
public class CheckUserController extends BaseController {

	@Autowired
	private CheckUserService checkUserService;
	
	@ModelAttribute
	public CheckUser get(@RequestParam(required=false) String id) {
		CheckUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkUserService.get(id);
		}
		if (entity == null){
			entity = new CheckUser();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckUser checkUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckUser> page = checkUserService.findPage(new Page<CheckUser>(request, response), checkUser); 
		model.addAttribute("page", page);
		return "modules/check/checkUserList";
	}

	@RequiresPermissions("check:checkUser:view")
	@RequestMapping(value = "form")
	public String form(CheckUser checkUser, Model model) {
		model.addAttribute("checkUser", checkUser);
		return "modules/check/checkUserForm";
	}

	@RequiresPermissions("check:checkUser:edit")
	@RequestMapping(value = "save")
	public String save(CheckUser checkUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkUser)){
			return form(checkUser, model);
		}
		checkUserService.save(checkUser);
		addMessage(redirectAttributes, "保存员工信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkUser/?repage";
	}
	
	@RequiresPermissions("check:checkUser:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckUser checkUser, RedirectAttributes redirectAttributes) {
		checkUserService.delete(checkUser);
		addMessage(redirectAttributes, "删除员工信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkUser/?repage";
	}

}