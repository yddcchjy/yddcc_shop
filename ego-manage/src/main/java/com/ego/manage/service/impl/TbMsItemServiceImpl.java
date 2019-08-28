package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbMsItemDubboService;
import com.ego.manage.service.TbMsItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbMsItem;
import com.ego.redis.dao.JedisDao;
@Service
public class TbMsItemServiceImpl implements TbMsItemService {
	@Reference
	private TbMsItemDubboService tbMsItemDubboServiceImpl;
	/*@Resource
	private JedisDao jedisDaoImpl;*/
	/*@Value("${redis.msitem.key}")
	private String itemKey ;*/
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbMsItemDubboServiceImpl.show(page, rows);
	}

	@Override
	public int save(TbMsItem item) throws Exception {
		long id = IDUtils.genItemId();
		item.setId(id);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		item.setCheckStatus((byte) 1);
		return tbMsItemDubboServiceImpl.insTbMsItem(item);
	}

}
