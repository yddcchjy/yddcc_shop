package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbMsItem;

public interface TbMsItemDubboService {
		/**
		 * 商品分页查询
		 * @param page
		 * @param rows
		 * @return
		 */
		EasyUIDataGrid show(int page,int rows);
		
		
		/**
		 * 根据id修改状态
		 * @param id
		 * @param status
		 * @return
		 */
		int updMsItemStatus(TbMsItem tbItem);
		/**
		 * 商品新增
		 * @param tbItem
		 * @return
		 */
		int insTbMsItem(TbMsItem tbItem)throws Exception;
		
		/**
		 * 通过状态查询全部可用数据
		 * @return
		 */
		List<TbMsItem> selAllByStatus(byte status);
		
		/**
		 * 通过状态查询全部可用数据（且可秒杀，有时间限制）
		 * @param status
		 * @param page
		 * @param rows
		 * @return
		 */
		EasyUIDataGrid selAllByStatusAndNow(byte status,int page,int rows);
		/**
		 * 根据主键查询
		 * @param id
		 * @return
		 */
		TbMsItem selById(long id);

}
