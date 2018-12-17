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
import com.yogee.youge.modules.check.entity.CheckChangeResult;
import com.yogee.youge.modules.check.service.CheckChangeResultService;

/**
 * 人员异动结果表Controller
 * @author zhangjian
 * @version 2018-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkChangeResult")
public class CheckChangeResultController extends BaseController {

	@Autowired
	private CheckChangeResultService checkChangeResultService;
	
	@ModelAttribute
	public CheckChangeResult get(@RequestParam(required=false) String id) {
		CheckChangeResult entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkChangeResultService.get(id);
		}
		if (entity == null){
			entity = new CheckChangeResult();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkChangeResult:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckChangeResult checkChangeResult, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckChangeResult> page = checkChangeResultService.findPage(new Page<CheckChangeResult>(request, response), checkChangeResult); 
		model.addAttribute("page", page);
		return "modules/check/checkChangeResultList";
	}

	@RequiresPermissions("check:checkChangeResult:view")
	@RequestMapping(value = "form")
	public String form(CheckChangeResult checkChangeResult, Model model) {
		model.addAttribute("checkChangeResult", checkChangeResult);
		return "modules/check/checkChangeResultForm";
	}

	@RequiresPermissions("check:checkChangeResult:edit")
	@RequestMapping(value = "save")
	public String save(CheckChangeResult checkChangeResult, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkChangeResult)){
			return form(checkChangeResult, model);
		}
		checkChangeResultService.save(checkChangeResult);
		addMessage(redirectAttributes, "保存人员异动结果表成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkChangeResult/?repage";
	}
	
	@RequiresPermissions("check:checkChangeResult:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckChangeResult checkChangeResult, RedirectAttributes redirectAttributes) {
		checkChangeResultService.delete(checkChangeResult);
		addMessage(redirectAttributes, "删除人员异动结果表成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkChangeResult/?repage";
	}

}