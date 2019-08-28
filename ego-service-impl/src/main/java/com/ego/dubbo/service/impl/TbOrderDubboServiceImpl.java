package com.ego.dubbo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.MsOrderParam;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderExample;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderItemExample;
import com.ego.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService{
	@Resource
	private TbOrderMapper tbOrderMapper;
	@Resource
	private TbOrderItemMapper tbOrderItemMapper;
	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;
	
	@Override
	public void updateorderbytrnumber(int paystatus, String tradeserialnumber, int paytype, Date paytime) throws Exception {
		try {
			TbOrder order = tbOrderMapper.selectByPrimaryKey(tradeserialnumber);
			order.setStatus(paystatus);
			order.setPaymentType(paytype);
			order.setPaymentTime(paytime);
			int a = tbOrderMapper.updateByPrimaryKeySelective(order);
			if(a != 1){
				throw new Exception("支付：更新订单失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("支付：更新订单失败");
		}
		
	}
	@Override
	public int insOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception {
		int index = tbOrderMapper.insertSelective(order);
		for (TbOrderItem tbOrderItem : list) {
			index+=tbOrderItemMapper.insertSelective(tbOrderItem);
		}
		index+=tbOrderShippingMapper.insertSelective(shipping);
		if(index==2+list.size()){
			return 1;
		}else{
			throw new Exception("创建订单失败");
		}
	}
	
	@Override
	public int insOrder(TbOrder order, TbOrderItem item, TbOrderShipping shipping) throws Exception {
		try {
			int index = tbOrderMapper.insertSelective(order);
			index+=tbOrderItemMapper.insertSelective(item);
			index+=tbOrderShippingMapper.insertSelective(shipping);
			
			if(index == 3){
				return 1;
			}else{
				throw new Exception("创建订单失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return 0;
	}
	
	@Override
	public List<MsOrderParam> selMsOrderParamByUserid(long userid) {
		List<MsOrderParam> list = new ArrayList<>();
		
		TbOrderExample example = new TbOrderExample();
		example.createCriteria().andUserIdEqualTo(userid);
		List<TbOrder> listOrder = tbOrderMapper.selectByExample(example);
		
		if(listOrder.size() <= 0){
			return null;
		}else{
			for(int i = 0;i < listOrder.size();i++){
				MsOrderParam msOrderParam = new MsOrderParam();
				TbOrder tbOrder = listOrder.get(i);
				String orderid = listOrder.get(i).getOrderId();
				
				TbOrderItemExample  example2 = new TbOrderItemExample();
				example2.createCriteria().andOrderIdEqualTo(orderid);
//				TbOrderItem tbOrderItem = tbOrderItemMapper.selectByExample(example2).get(0);
				
				List<TbOrderItem> l = tbOrderItemMapper.selectByExample(example2);
				TbOrderItem tbOrderItem = l.get(0);
				
				TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(orderid);
				
				msOrderParam.setOrderItem(tbOrderItem);
				msOrderParam.setOrderShipping(tbOrderShipping);
				msOrderParam.setPayment(Long.toString(tbOrderItem.getTotalFee()));
				msOrderParam.setPaymentType(tbOrder.getPaymentType());
				msOrderParam.setPayStatus(Integer.toString(tbOrder.getStatus()));
				msOrderParam.setUserid(userid);
				list.add(msOrderParam);
			}
		}
		return list;
	}
	
	@Override
	public List<TbOrder> selTbOrderByStatus(int status) {
		TbOrderExample example = new TbOrderExample();
		example.createCriteria().andStatusEqualTo(status);
		return tbOrderMapper.selectByExample(example);
	}
	
	@Override
	public String updateOrderStatusById(String orderId, int status) {
		TbOrder order = tbOrderMapper.selectByPrimaryKey(orderId);
		order.setStatus(status);
		tbOrderMapper.updateByPrimaryKeySelective(order);
		return orderId;
	}
	
	@Override
	public TbOrderItem selTbOrderItemByOrderid(String orderid){
		TbOrderItemExample example = new TbOrderItemExample();
		example.createCriteria().andOrderIdEqualTo(orderid);
		return tbOrderItemMapper.selectByExample(example).get(0);
	}
}
