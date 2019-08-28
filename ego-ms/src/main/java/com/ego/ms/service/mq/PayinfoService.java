package com.ego.ms.service.mq;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.DateUtils;
import com.ego.dubbo.service.TbOrderDubboService;


public class PayinfoService implements MessageListener{
	@Reference
	private TbOrderDubboService tbOrderDubboService;
	
	@Override
	public void onMessage(Message message) {
		try {
			byte[] messsagebyte = message.getBody();
			ByteArrayInputStream in = new ByteArrayInputStream(messsagebyte);
			ObjectInputStream obj;
			obj = new ObjectInputStream(in);
			Map<String,String> datamap = (Map<String, String>) obj.readObject();
			String tradeserialnumber = datamap.get("tradeserialnumber");
			String paystatus = datamap.get("paystatus");
			String paytimestring = datamap.get("paytimestring");
			String paytype = datamap.get("paytype");
			
			tbOrderDubboService.updateorderbytrnumber(Integer.valueOf(paystatus), tradeserialnumber, Integer.valueOf(paytype), DateUtils.transferdate(paytimestring, "yyyy-MM-dd HH:mm:ss"));
			
	        System.out.println("mq支付更新成功" + message.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
