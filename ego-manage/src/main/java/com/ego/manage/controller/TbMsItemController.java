package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbMsItemService;
import com.ego.pojo.TbMsItem;
@Controller
public class TbMsItemController {
	@Resource
	private TbMsItemService tbMsItemServiceImpl;
	
	/**
	 * 分页显示商品
	 */
	@RequestMapping("msitem/list")
	@ResponseBody
	public EasyUIDataGrid show(int page,int rows){
		return tbMsItemServiceImpl.show(page, rows);
	}
	
	
	/**
	 * 商品新增
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping("msitem/save")
	@ResponseBody
	public EgoResult insert(TbMsItem item){
		EgoResult er = new EgoResult();
		int index;
		try {
			index = tbMsItemServiceImpl.save(item);
			if(index==1){
				er.setStatus(200);
			}
		} catch (Exception e) {
			er.setData(e.getMessage());
		}
		return er;
	}
}
