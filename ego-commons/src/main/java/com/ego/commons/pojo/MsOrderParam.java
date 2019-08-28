package com.ego.commons.pojo;

import java.io.Serializable;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class MsOrderParam implements Serializable {
	private int paymentType;
	private String payment;
	private String payStatus;
	private TbOrderItem orderItem;
	private TbOrderShipping orderShipping;
	private int stockcount;
	private long userid;
	
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public int getStockcount() {
		return stockcount;
	}
	public void setStockcount(int stockcount) {
		this.stockcount = stockcount;
	}
	public TbOrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(TbOrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
