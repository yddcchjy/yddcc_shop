package com.ego.dubbo.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbMsItemDubboService;
import com.ego.mapper.TbMsItemMapper;
import com.ego.pojo.TbMsItem;
import com.ego.pojo.TbMsItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbMsItemDubboServiceImpl implements TbMsItemDubboService {
	@Resource
	private TbMsItemMapper tbMsItemMapper;
	
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		PageHelper.startPage(page, rows);
		//查询全部
		List<TbMsItem> list = tbMsItemMapper.selectByExample(new TbMsItemExample());
		//分页代码
		//设置分页条件
		PageInfo<TbMsItem> pi = new PageInfo<>(list);
		
		//放入到实体类
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());
		return datagrid;
	}
	
	@Override
	public EasyUIDataGrid selAllByStatusAndNow(byte status,int page,int rows) {
		PageHelper.startPage(page, rows);
		
		TbMsItemExample example = new TbMsItemExample();
		Date date = new Date();
		example.createCriteria().andCheckStatusEqualTo(status).andFinishTimeGreaterThan(date);
		List<TbMsItem> list = tbMsItemMapper.selectByExample(example);
		
		//分页代码
		//设置分页条件
		PageInfo<TbMsItem> pi = new PageInfo<>(list);
		
		EasyUIDataGrid datagrid = new EasyUIDataGrid();
		datagrid.setRows(pi.getList());
		datagrid.setTotal(pi.getTotal());//先不用返回了再说
		return datagrid;
	}
	
	@Override
	public int updMsItemStatus(TbMsItem tbItem) {
		return 0;
	}

	@Override
	public int insTbMsItem(TbMsItem tbItem) throws Exception {
		int index =0;
		try {
			index= tbMsItemMapper.insertSelective(tbItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(index==1){
			return 1;
		}else{
			throw new Exception("新增失败,数据还原");
		}
	}

	@Override
	public List<TbMsItem> selAllByStatus(byte status) {
		TbMsItemExample example = new TbMsItemExample();
		example.createCriteria().andCheckStatusEqualTo(status);
		return tbMsItemMapper.selectByExample(example);
	}
	


	@Override
	public TbMsItem selById(long id) {
		return tbMsItemMapper.selectByPrimaryKey(id);
	}
}
