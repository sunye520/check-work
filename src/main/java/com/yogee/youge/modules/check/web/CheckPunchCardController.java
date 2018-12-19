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
import com.yogee.youge.modules.check.entity.CheckPunchCard;
import com.yogee.youge.modules.check.service.CheckPunchCardService;

/**
 * 打卡记录信息Controller
 * @author sunye
 * @version 2018-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkPunchCard")
public class CheckPunchCardController extends BaseController {

	@Autowired
	private CheckPunchCardService checkPunchCardService;
	
	@ModelAttribute
	public CheckPunchCard get(@RequestParam(required=false) String id) {
		CheckPunchCard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkPunchCardService.get(id);
		}
		if (entity == null){
			entity = new CheckPunchCard();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkPunchCard:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckPunchCard checkPunchCard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckPunchCard> page = checkPunchCardService.findPage(new Page<CheckPunchCard>(request, response), checkPunchCard); 
		model.addAttribute("page", page);
		return "modules/check/checkPunchCardList";
	}

	@RequiresPermissions("check:checkPunchCard:view")
	@RequestMapping(value = "form")
	public String form(CheckPunchCard checkPunchCard, Model model) {
		model.addAttribute("checkPunchCard", checkPunchCard);
		return "modules/check/checkPunchCardForm";
	}

	@RequiresPermissions("check:checkPunchCard:edit")
	@RequestMapping(value = "save")
	public String save(CheckPunchCard checkPunchCard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkPunchCard)){
			return form(checkPunchCard, model);
		}
		checkPunchCardService.save(checkPunchCard);
		addMessage(redirectAttributes, "保存打卡记录信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkPunchCard/?repage";
	}
	
	@RequiresPermissions("check:checkPunchCard:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckPunchCard checkPunchCard, RedirectAttributes redirectAttributes) {
		checkPunchCardService.delete(checkPunchCard);
		addMessage(redirectAttributes, "删除打卡记录信息成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkPunchCard/?repage";
	}

}