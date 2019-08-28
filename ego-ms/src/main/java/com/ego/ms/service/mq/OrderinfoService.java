package com.ego.ms.service.mq;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.DateUtils;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class OrderinfoService  implements MessageListener{
	@Reference
	private TbOrderDubboService tbOrderDubboService;
	
	@Override
	public void onMessage(Message message) {
		try {
			byte[] messsagebyte = message.getBody();
			ByteArrayInputStream in = new ByteArrayInputStream(messsagebyte);
			ObjectInputStream obj = new ObjectInputStream(in);
			Map<String,String> datamap = (Map<String, String>) obj.readObject();
		    
			String createtime = datamap.get("createtime");
			String totalFee = datamap.get("totalFee");
			String receiverAddress = datamap.get("receiverAddress");
			String receiverName = datamap.get("receiverName");
			String receiverPhone = datamap.get("receiverPhone");
			String stockcount = datamap.get("stockcount");
			String tradeserialnumber = datamap.get("tradeserialnumber");
			String paystatus = datamap.get("paystatus");
			String productid = datamap.get("productid");
			String userid= datamap.get("userid");
			System.out.println("---------------------"+tradeserialnumber);
			
			TbOrder order = new TbOrder();
			order.setOrderId(tradeserialnumber);
			order.setPayment(totalFee);
			order.setStatus(Integer.parseInt(paystatus));
			order.setCreateTime(DateUtils.transferdate(createtime, "yyyy-MM-dd HH:mm:ss"));
			order.setUpdateTime(DateUtils.transferdate(createtime, "yyyy-MM-dd HH:mm:ss"));
			order.setShippingName(receiverName);
			order.setUserId(Long.parseLong(userid));
			
			TbOrderItem item = new TbOrderItem();
			item.setItemId(productid);
			item.setOrderId(tradeserialnumber);
			item.setNum(1);
			item.setPrice(Long.parseLong(totalFee));
			item.setTotalFee(Long.parseLong(totalFee));
			item.setId(IDUtils.genItemId()+"");
			
			TbOrderShipping shipping = new TbOrderShipping();
			shipping.setCreated(DateUtils.transferdate(createtime, "yyyy-MM-dd HH:mm:ss"));
			shipping.setOrderId(tradeserialnumber);
			shipping.setReceiverAddress(receiverAddress);
			shipping.setReceiverPhone(receiverPhone);
			shipping.setReceiverName(receiverName);
			
			int a = tbOrderDubboService.insOrder(order, item, shipping);
			if(a != 1){
				throw new Exception("mq创建订单失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        System.out.println("mq创建订单成功" + message.toString());
	}
}
