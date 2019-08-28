package com.ego.ms.pay.service;

public interface Ordertopay {
	/**
	 * 支付
	 * @param tradeserialnumber
	 * @param totalFee
	 * @return
	 */
	public int paywithorder(String tradeserialnumber, int totalFee);
	
	/**
	 * 退款
	 * @param tradeserialnumber
	 * @param totalFee
	 * @return
	 */
	public int refundwithorder(String tradeserialnumber, int totalFee);
}
