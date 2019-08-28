package com.ego.dubbo.service;

import java.util.Date;
import java.util.List;

import com.ego.commons.pojo.MsOrderParam;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public interface TbOrderDubboService {
	/**
	 * 创建订单
	 * @param order
	 * @param list
	 * @param shipping
	 * @return
	 */
	int insOrder(TbOrder order,List<TbOrderItem> list,TbOrderShipping shipping) throws Exception;
	
	/**
	 * 创建秒杀订单
	 * @param order
	 * @param list
	 * @param shipping
	 * @return
	 */
	int insOrder(TbOrder order,TbOrderItem item,TbOrderShipping shipping) throws Exception;
	
	/**
	 * 支付后更新订单
	 * @param valueOf
	 * @param tradeserialnumber
	 * @param valueOf2
	 * @param transferdate
	 */
	void updateorderbytrnumber(int paystatus, String tradeserialnumber, int paytype, Date paytime)throws Exception;
	
	List<TbOrder> selTbOrderByStatus (int status);
	
	List<MsOrderParam> selMsOrderParamByUserid(long userid) ;
	
	String updateOrderStatusById(String orderId,int status);
	
	/**
	 * 根据orderid查找orderItem
	 * @param orderid
	 * @return
	 */
	TbOrderItem selTbOrderItemByOrderid(String orderid);
	
	/**
	 * 根据userid查找TbOrder
	 * @param userid
	 * @return
	 */
//	TbOrder selTbOrderByUserid(long userid);
	
	/**
	 * 根据orderid查找TbOrderItem
	 * @param orderid
	 * @return
	 */
//	TbOrderItem selTbOrderItemByOrderid(String orderid);
	
	/**
	 * 根据orderid查找TbOrderShipping
	 * @param orderid
	 * @return
	 */
//	TbOrderShipping selTbOrderShippingByOrderid(String orderid);
	
}
