/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/yogee/youge">JeeSite</a> All rights reserved.
 */
package com.yogee.youge.modules.sys.service;

import com.yogee.youge.common.service.CrudService;
import com.yogee.youge.common.utils.CacheUtils;
import com.yogee.youge.modules.sys.dao.DictDao;
import com.yogee.youge.modules.sys.entity.Dict;
import com.yogee.youge.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	public List<String> findBytype(String type) {
		return dao.findBytype(type);

	}



	public List<Dict> findDictByMap(Map map) {
		return dao.findDictByMap(map);
	}
	@Transactional(readOnly = false)
	public void insetDict(Dict dict) {
		dao.insetDict(dict);
	}

	public List<String> findByjishuleibie(String type) {
		return dao.findByjishuleibie(type);

	}

}
