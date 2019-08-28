package com.ego.ms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.MsOrderParam;
import com.ego.commons.pojo.TbMsItemChild;
import com.ego.pojo.TbMsItem;

public interface TbMsItemService {
	List<TbMsItem> listmsproduct(TbMsItem item);
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	Map<String,Object> selByQuery(int page,int rows);
	
	TbMsItemChild show (long id);
	int getUsedCount(Long itemid);
	
	/**
	 * 尝试创建秒杀订单
	 * @param userid
	 * @param productid
	 * @param param
	 * @return
	 */
	Map<String,Object>  seckill(long userid,String productid,MsOrderParam param);
	
	/**
	 * 支付订单了
	 * @param paytype
	 * @param userid
	 * @param productid
	 * @param tradeserialnumber
	 * @param totalFee
	 * @return
	 */
	boolean payorder(int paytype, int userid, String productid, String tradeserialnumber, int totalFee);
	
	/**
	 * 找用户的秒杀订单
	 * @param userid
	 * @return
	 */
	List<MsOrderParam> queryOrderByUserid(long userid);
	
	/**
	 * 用户访问一次，
	 * @param userid
	 * @return
	 */
	long visitTimes(long userid);
	
	/**
	 * 
	 * @param userid
	 * @return
	 */
	long getuservisitTime(long userid);
}
