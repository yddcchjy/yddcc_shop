package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbMsItem;

public interface TbMsItemService {
	/**
	 * 显示商品
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
	
	/**
	 * 商品新增
	 * @param item
	 * @param desc
	 * @return
	 */
	int save(TbMsItem item ) throws Exception;
}
