package com.ego.ms.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.redis.dao.JedisDao;


@Controller
public class CheckOrderTimeoutTask
{
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	public void CheckOrderTimeout(long miniutes){
        try {
        	
        	List<TbOrder> list = tbOrderDubboServiceImpl.selTbOrderByStatus(1);//haven't pay
        	if( list != null || list.size() > 0){
        		for(TbOrder order : list){
            		Date createTime = order.getCreateTime();
            		Date payTime = order.getPaymentTime();
            		if(payTime != null){
            			continue;
            		}
            		Long timeInstance = System.currentTimeMillis() - createTime.getTime();
            		long miniute = timeInstance / 60 * 1000;
            		if(miniute > miniutes){
            			tbOrderDubboServiceImpl.updateOrderStatusById(order.getOrderId(), 6);//6:交易关闭
            		}
            		System.out.println("订单号为： " + order.getOrderId() + "  订单已关闭");
            		
            		TbOrderItem tbOrderItem = tbOrderDubboServiceImpl.selTbOrderItemByOrderid(order.getOrderId());
            		System.out.println(jedisDaoImpl);
            		System.out.println("itemid---------:"+tbOrderItem.getItemId());
            		System.out.println("UserId---------:"+order.getUserId());
            		jedisDaoImpl.removelist(tbOrderItem.getItemId(), (long)(-1), Long.toString(order.getUserId()));
            		System.out.println("已删除： " + order.getUserId() + " + " + tbOrderItem.getItemId());
            	}
        	}
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
