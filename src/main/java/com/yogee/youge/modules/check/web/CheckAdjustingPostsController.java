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
import com.yogee.youge.modules.check.entity.CheckAdjustingPosts;
import com.yogee.youge.modules.check.service.CheckAdjustingPostsService;

/**
 * 调岗记录表Controller
 * @author zhangjian
 * @version 2018-12-14
 */
@Controller
@RequestMapping(value = "${adminPath}/check/checkAdjustingPosts")
public class CheckAdjustingPostsController extends BaseController {

	@Autowired
	private CheckAdjustingPostsService checkAdjustingPostsService;
	
	@ModelAttribute
	public CheckAdjustingPosts get(@RequestParam(required=false) String id) {
		CheckAdjustingPosts entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = checkAdjustingPostsService.get(id);
		}
		if (entity == null){
			entity = new CheckAdjustingPosts();
		}
		return entity;
	}
	
	@RequiresPermissions("check:checkAdjustingPosts:view")
	@RequestMapping(value = {"list", ""})
	public String list(CheckAdjustingPosts checkAdjustingPosts, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CheckAdjustingPosts> page = checkAdjustingPostsService.findPage(new Page<CheckAdjustingPosts>(request, response), checkAdjustingPosts); 
		model.addAttribute("page", page);
		return "modules/check/checkAdjustingPostsList";
	}

	@RequiresPermissions("check:checkAdjustingPosts:view")
	@RequestMapping(value = "form")
	public String form(CheckAdjustingPosts checkAdjustingPosts, Model model) {
		model.addAttribute("checkAdjustingPosts", checkAdjustingPosts);
		return "modules/check/checkAdjustingPostsForm";
	}

	@RequiresPermissions("check:checkAdjustingPosts:edit")
	@RequestMapping(value = "save")
	public String save(CheckAdjustingPosts checkAdjustingPosts, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, checkAdjustingPosts)){
			return form(checkAdjustingPosts, model);
		}
		checkAdjustingPostsService.save(checkAdjustingPosts);
		addMessage(redirectAttributes, "保存调岗记录表成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkAdjustingPosts/?repage";
	}
	
	@RequiresPermissions("check:checkAdjustingPosts:edit")
	@RequestMapping(value = "delete")
	public String delete(CheckAdjustingPosts checkAdjustingPosts, RedirectAttributes redirectAttributes) {
		checkAdjustingPostsService.delete(checkAdjustingPosts);
		addMessage(redirectAttributes, "删除调岗记录表成功");
		return "redirect:"+Global.getAdminPath()+"/check/checkAdjustingPosts/?repage";
	}

}