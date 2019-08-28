package com.ego.ms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.MsOrderParam;
import com.ego.commons.pojo.TbMsItemChild;
import com.ego.commons.utils.DateUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbMsItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.ms.service.TbMsItemService;
import com.ego.pojo.TbMsItem;
import com.ego.redis.dao.JedisDao;
@Service
public class TbMsItemServiceImpl implements TbMsItemService{
	@Reference
	private TbMsItemDubboService tbMsItemDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	
//	@Value("${redis.msitem.key}")
	private String itemKey = "msitem:";
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Override
	public boolean payorder(int paytype, int userid, String productid, String tradeserialnumber, int totalFee) {
		String key = "userid:"+userid+"==productid:"+productid;
		String value = (String) jedisDaoImpl.get(key);
		String[] splitvalues = value.split("==");
		splitvalues[0] = "2";//paystatus
		value = "";
		for(String temp:splitvalues){
			value += temp+"==";
		}
		boolean issuccess = jedisDaoImpl.set(key, value);
		Map<String,String> datamap = new HashMap<String,String>();
		datamap.put("tradeserialnumber", tradeserialnumber);
		datamap.put("paystatus", "2");
		String paytimestring = DateUtils.transferdate(new Date(), "yyyy-MM-dd HH:mm:ss");
		datamap.put("paytimestring", paytimestring);
		datamap.put("paytype",paytype+"");
		amqpTemplate.convertAndSend("ms_exchange", "payinfomation", datamap);
		return issuccess;
	}
	
	@Override
	public Map<String,Object> seckill(long userid, String productid, MsOrderParam param) {
		Map<String,Object> resultmap = new HashMap<String,Object>(); 
		if(jedisDaoImpl.getkeylistsize(productid+"") > param.getStockcount()){
			//秒杀失败
	    	System.out.println("秒杀失败");
	    	resultmap.put("success", false);
	    	return resultmap;
	    }
		//秒杀成功
		jedisDaoImpl.pushlist(productid+"", userid+"");
	    System.out.println("秒杀成功");
	    String key = "userid:"+userid+"==productid:"+productid;
	    String value = "";
	    
	    String createtimestring = DateUtils.transferdate(new Date(), "yyyy-MM-dd HH:mm:ss");
	    String totalFee = param.getOrderItem().getTotalFee()+"";
	    
	    String receiverAddress = param.getOrderShipping().getReceiverAddress();
	    String receiverName = param.getOrderShipping().getReceiverName();
	    String receiverPhone = param.getOrderShipping().getReceiverPhone();
	    String stockcount = param.getStockcount()+"";
	    String paystatus = "1";
	    String tradeserialnumber = createtimestring+UUID.randomUUID();//MsOrderParam -> TbOrderItem -> id
	    
	    value += paystatus + "==" +tradeserialnumber+"==" + createtimestring + "==" + totalFee + "==" + receiverAddress + "==" + receiverName + "==" 
	    + receiverPhone + "==" + stockcount;
	    jedisDaoImpl.set(key, value);
	    
	    Map<String,String> datamap = new HashMap<String,String>();
	    datamap.put("createtime", createtimestring);
	    datamap.put("totalFee", totalFee);
	    datamap.put("receiverAddress", receiverAddress);
	    datamap.put("receiverName", receiverName);
	    datamap.put("receiverPhone", receiverPhone);
	    datamap.put("stockcount", stockcount);
	    datamap.put("tradeserialnumber", tradeserialnumber);
	    datamap.put("paystatus", paystatus);
	    datamap.put("productid", productid+"");
	    datamap.put("userid", userid+"");
	    datamap.put("productid", productid+"");
	    
	    amqpTemplate.convertAndSend("ms_exchange", "orderinfomation", datamap);
	    
	    resultmap.put("success", true);
	    resultmap.put("datamap", datamap);
		return resultmap;
	}
	
	/**
	 * 获取某个商品已经被买的数量
	 */
	@Override
	public int getUsedCount(Long itemid) {
		return (int)((long)jedisDaoImpl.getkeylistsize(itemid+""));
	}
	
	@Override
	public List<TbMsItem> listmsproduct(TbMsItem item) {
		return tbMsItemDubboServiceImpl.selAllByStatus((byte) 1);
	}

	@Override
	public Map<String, Object> selByQuery(int page, int rows) {
		Map<String,Object> resultMap = new HashMap<>();
		
		//返回商品list集合
		List<TbMsItemChild> listChild = new ArrayList<>();
		EasyUIDataGrid eudg = (EasyUIDataGrid) tbMsItemDubboServiceImpl.selAllByStatusAndNow((byte) 1,page,rows);
		List<TbMsItem> list = (List<TbMsItem>) eudg.getRows();
		for(TbMsItem item: list){
			TbMsItemChild child = new TbMsItemChild();
			child.setBarcode(item.getBarcode());
			child.setBeginTime(item.getBeginTime());
			child.setCheckStatus(item.getCheckStatus());
			child.setCheckTime(item.getCheckTime());
			child.setCid(item.getCid());
			child.setCreated(item.getCreated());
			child.setFinishTime(item.getFinishTime());
			child.setId(item.getId());
			child.setMerchantId(item.getMerchantId());
			child.setMsPrice(item.getMsPrice());
			child.setNum(item.getNum());
			child.setOldPrice(item.getOldPrice());
			child.setSellPoint(item.getSellPoint());
			child.setTitle(item.getTitle());
			child.setUpdated(item.getUpdated());
			String image_temp = item.getImage();
			child.setImages(image_temp == null || image_temp.equals("") ? new String[1] : image_temp.split(","));
			child.setLeftnum(child.getNum() - (int)((long)jedisDaoImpl.getkeylistsize(item.getId() + "")));
			listChild.add(child);
			
		}
		
		resultMap.put("itemList", listChild);
		//分页
		resultMap.put("totalPages", eudg.getTotal()%rows==0?eudg.getTotal()/rows:eudg.getTotal()/rows+1);
		return resultMap;
	}
	
	/**
	 * change return type
	 */
	@Override
	public TbMsItemChild show(long id) {
		String key = itemKey+id;
		//fisrt search redis
		if(jedisDaoImpl.exists(key)){
			String json = jedisDaoImpl.get(key);
			if(json!=null&&!json.equals("")){
				return JsonUtils.jsonToPojo(json, TbMsItemChild.class);
			}
		}
		//else search db
		TbMsItem item = tbMsItemDubboServiceImpl.selById(id);
		TbMsItemChild child = new TbMsItemChild();
		
		child.setBeginTime(item.getBeginTime());
		child.setFinishTime(item.getFinishTime());
		child.setId(item.getId());
		child.setMsPrice(item.getMsPrice());
		child.setOldPrice(item.getOldPrice());
		child.setNum(item.getNum());
		child.setSellPoint(item.getSellPoint());
		child.setTitle(item.getTitle());
		String image_temp = item.getImage();
		child.setImages(image_temp == null || image_temp.equals("") ? new String[1] : image_temp.split(","));
		
		//存到数据库中
		jedisDaoImpl.set(key, JsonUtils.objectToJson(child));
		return child;
	}

	@Override
	public List<MsOrderParam> queryOrderByUserid(long userid) {
		
		return tbOrderDubboServiceImpl.selMsOrderParamByUserid(userid);
	}
	
	@Override
	public long visitTimes(long userid){
		long times = System.currentTimeMillis();
		jedisDaoImpl.set(userid+"##",times+"");
		if(jedisDaoImpl.get(userid+"==") == null){
			jedisDaoImpl.incr(userid+"==",1);
			jedisDaoImpl.expire(userid+"==", 1);
			return Long.parseLong(jedisDaoImpl.get(userid+"=="));
		}else{
			return jedisDaoImpl.incr(userid+"==",1);
		}
	}
	
	@Override
	public long getuservisitTime(long userid){
		long times = Long.valueOf(jedisDaoImpl.get(userid+"##")==null ? "0":jedisDaoImpl.get(userid+"##")+"");
		return times;
	}

}
