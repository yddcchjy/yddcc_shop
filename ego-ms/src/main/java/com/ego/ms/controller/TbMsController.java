package com.ego.ms.controller;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.MsOrderParam;
import com.ego.commons.pojo.TbMsItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.ms.pay.service.WeixinPay;
import com.ego.ms.pay.service.YinghangkaPay;
import com.ego.ms.pay.service.ZhifubaoPay;
import com.ego.ms.service.TbMsItemService;

@Controller
public class TbMsController {
	@Resource
	private TbMsItemService tbMsItemServiceImpl;
//	@Value("${passport.url}")
	private String passprtUrl = "http://localhost:8084/user/token/";
	
	@Resource
	private WeixinPay weixinPay;
	@Resource
	private YinghangkaPay yinghangkaPay;
	@Resource
	private ZhifubaoPay zhifubaoPay;
	
	/**
	 * 显示确认页面
	 * @param model
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("order/order-cart.html")
	public String showCartOrder(Model model,@RequestParam("id") Long id,HttpServletRequest request){
		/*List<TbMsItem> list = new ArrayList<>();
		list.add(tbMsItemServiceImpl.show(id));*/
		TbMsItemChild child =  tbMsItemServiceImpl.show(id);
		model.addAttribute("msitem",child);
		model.addAttribute("leftnum", child.getNum() - tbMsItemServiceImpl.getUsedCount(id));
//		model.addAttribute("cartList", tbOrderServiceImpl.showOrderCart(ids, request));
		return "order-cart";
	}
	
	/**
	 * 创建订单
	 * @param param
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="order/create.html")
	public String createOrder(MsOrderParam param,HttpServletRequest request, HttpServletResponse response) throws IOException{
		String returnurl = "";
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String result = HttpClientUtil.doPost(passprtUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
//		long userid = (long) ((LinkedHashMap)er.getData()).get("id");
		int  temp =  (int) ((LinkedHashMap)er.getData()).get("id");
		long userid = temp;
		
		//防秒杀机器实现
		long time = tbMsItemServiceImpl.getuservisitTime(userid);
		long curtime = System.currentTimeMillis();
		long shijiancha = curtime - time;
		long seconds = shijiancha/1000;
		long times = tbMsItemServiceImpl.visitTimes(userid);
		//1秒钟超过50次访问就跳转到首页
		if(times / seconds > 50){
			System.out.println("非法访问");
			return "index";
		}
		
		Map<String,Object> resultmap = tbMsItemServiceImpl.seckill( userid, param.getOrderItem().getItemId(),param);
		boolean issuccess = (Boolean) resultmap.get("success");
		if(issuccess){
			  System.out.println("秒杀成功2");
			  Map<String,String> datamap = (Map<String, String>) resultmap.get("datamap");
			  String totalFee = datamap.get("totalFee");
			  String tradeserialnumber = datamap.get("tradeserialnumber");
			  String productid = datamap.get("productid");
//			  String userid = datamap.get("userid");
			  System.out.println("userid"+userid);
			  System.out.println("productid"+productid);
			  System.out.println("tradeserialnumber"+tradeserialnumber);
			  System.out.println("totalFee"+totalFee);
			  returnurl = "redirect:topaywithorder?userid="+userid+"&&productid="+productid+"&&tradeserialnumber="+tradeserialnumber+"&&totalFee="+totalFee;
//			  returnurl = "redirect:topaywithorder2";
			 /* request.setAttribute("userid", userid);
			  request.setAttribute("productid", productid);
			  request.setAttribute("tradeserialnumber", tradeserialnumber);
			  request.setAttribute("totalFee", totalFee);
			  return "payreal";*/
		  }else{
			  System.out.println("秒杀失败"); 
			  returnurl = "index";
		  }
		return returnurl;
	}
	
	/**
	 * 支付前中转一下
	 * @param req
	 * @param userid
	 * @param productid
	 * @param totalFee
	 * @return
	 */
	@RequestMapping(value="order/topaywithorder",method=RequestMethod.GET)
	  public String topaywithorder(HttpServletRequest req,int userid,String productid ,String tradeserialnumber,int totalFee){
		req.setAttribute("totalFee", totalFee);
		req.setAttribute("userid", userid);
		req.setAttribute("productid", productid);
		req.setAttribute("tradeserialnumber", tradeserialnumber);
		return "payreal";
	  }
	
	
	
	/**
	   * 支付订单了
	   * @param req
	   * @param paytype
	   * @param tradeserialnumber
	   * @param payamount
	   * @return
	   */
	  @RequestMapping(value="order/paywithorder",method=RequestMethod.POST)
	  public String paywithorder(HttpServletRequest req,int paytype,int userid,String productid,String tradeserialnumber, int totalFee){
		  int paystatus = 2;
		  if(paytype == 1){//1支付宝
			  paystatus = zhifubaoPay.paywithorder(tradeserialnumber, totalFee);
		  }else if(paytype == 2){//2微信
			  paystatus = weixinPay.paywithorder(tradeserialnumber, totalFee);
		  }else if(paytype == 3){//3银行卡
			  paystatus = yinghangkaPay.paywithorder(tradeserialnumber, totalFee);
		  }
		  if(paystatus == 1){
			 boolean issuccess = tbMsItemServiceImpl.payorder(paytype, userid, productid,tradeserialnumber, totalFee);
			 if(issuccess){
				 System.out.println("支付成功");
			 }else{
				 System.out.println("支付失败");
			 }
		  }
		  return "redirect:queryorderbyuserid";
		  
	  }
	  
	 /**
	  * 找到用户的全部秒杀订单
	  * @param req
	  * @return
	  */
	 @RequestMapping("order/queryorderbyuserid")
	 public String queryorderbyuserid(HttpServletRequest req){
		String token = CookieUtils.getCookieValue(req, "TT_TOKEN");
		String result = HttpClientUtil.doPost(passprtUrl+token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
//		long userid = (long) ((LinkedHashMap)er.getData()).get("id");
		int  temp =  (int) ((LinkedHashMap)er.getData()).get("id");
		long userid = temp;
			
		List<MsOrderParam> list = tbMsItemServiceImpl.queryOrderByUserid(userid);
		req.setAttribute("list", list);
		return "listOrder";
	  } 
	
	/**
	 * 首页
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("search2")
	public String welcome(Model model,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="10") int rows){
		try {
			Map<String, Object> map = tbMsItemServiceImpl.selByQuery(page, rows);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "index";
	}
	
	@RequestMapping("producehtml")
	public void producehtml(HttpServletRequest req){
		String htmlPath=req.getRealPath("/WEB-INF/html/");
		String contextpath = req.getScheme() +"://" + req.getServerName()  + ":" +req.getServerPort() +req.getContextPath();
		contextpath = contextpath + "/search2";
		try{
			String entity = HttpClientUtil.doGet(contextpath);
			if(entity != null){
				 File file = new File(htmlPath+"/index.html");
	             Writer writer = new BufferedWriter(  
	                     new OutputStreamWriter(
	                             new FileOutputStream(file)));  
	             writer.write(entity);  
	             writer.flush();  
	             writer.close();
			}
		}catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	//for test
	@RequestMapping("a")
	public String a(){
		return "test";
	}
	
	@RequestMapping("producejs")
	public void producejs(HttpServletRequest req){
		String jsPath=req.getRealPath("/WEB-INF/js");
		String jscontent = "function remaintime(){" + "\n" +
		"var starttime = $(\"#starttime\").html();" +"\n" +
		"var s1 = new Date(starttime.replace(\"/-/g\",\"/\"));"+"\n" +
		"var s2 = new Date();"+"\n" +
		"var date3 = s1.getTime() - s2.getTime();//����һ�����ʱ���" +"\n" +
		"if(date3 > 2){"+"\n" +
		"$(\"#buybt\").attr({\"disabled\":\"disabled\"});" +"\n" +
		"var days = Math.floor(date3/(24*3600*1000));" +"\n" +
		"var leave = date3%(24*3600*1000)"+"\n" +
		"var hours = Math.floor(leave/(3600*1000));"+"\n" +
		"var leave1 = leave%(3600*1000)"+"\n" +
		"var minutes = Math.floor(leave1/(60*1000));"+"\n" +
		"var leave2 = leave1%(60*1000)"+"\n" +
		"var seconds = Math.floor(leave2/1000)"+"\n" +
		"$(\"#remainnoties\").html(\"s\"+days+\"d\"+ hours + \"h\" + minutes + \"m\"+seconds+\"s\");" +"\n" +
		"}else{" + "\n" +
		"$(\"#remainnoties\").html(\"\");" + "\n" +
		"$(\"#buybt\").removeAttr(\"disabled\");" +"\n" +
		"$(\"#ac\").attr(\"action\",\"http://localhost:8087/order/order-cart.html\");" +"\n" +
		"}" + "\n" +
		"}" +"\n" +
		"// test js new "+"\n" +
		"setInterval('remaintime()',500);";
		 File file = new File(jsPath+"/remain.js");
         Writer writer = null;
		try {
			writer = new BufferedWriter(  
			         new OutputStreamWriter(  
			                 new FileOutputStream(file), "utf-8"));
			writer.write(jscontent);  
	         writer.flush();  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} 
          
	}
	
	/**
	 * 跳转到秒杀商品的详情页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("item/{id}.html")
	public String showMsItemDetails(@PathVariable long id,Model model){
		TbMsItemChild child =  tbMsItemServiceImpl.show(id);
		model.addAttribute("msproduct", child);
		model.addAttribute("leftnum", child.getNum() - tbMsItemServiceImpl.getUsedCount(id));
		return "seckill-item";
	}
}
