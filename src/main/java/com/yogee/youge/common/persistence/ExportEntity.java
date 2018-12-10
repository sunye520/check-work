/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.common.persistence;

import com.yogee.youge.modules.sys.entity.User;
import com.yogee.youge.modules.sys.utils.UserUtils;

/**
 * 数据Entity类
 * @author ThinkGem
 * @version 2014-05-16
 */
public abstract class ExportEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;


	public ExportEntity() {
		super();
	}

	public ExportEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
//		if (!this.isNewRecord){
//			setId(IdGen.uuid());
//		}
		setIsNewRecord(true);
//		setId();
		User user = UserUtils.getUser();
//		if (StringUtils.isNotBlank(user.getId())){
//			this.updateBy = user;
//			this.createBy = user;
//		}
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
//		if (StringUtils.isNotBlank(user.getId())){
//			this.updateBy = user;
//		}
	}

}
