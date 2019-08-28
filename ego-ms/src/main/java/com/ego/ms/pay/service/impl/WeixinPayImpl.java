package com.ego.ms.pay.service.impl;

import org.springframework.stereotype.Service;

import com.ego.ms.pay.service.WeixinPay;
@Service
public class WeixinPayImpl implements WeixinPay {

	@Override
	public int paywithorder(String tradeserialnumber, int totalFee) {
		System.out.println("微信支付成功！");
		return 1;
	}

	@Override
	public int refundwithorder(String tradeserialnumber, int totalFee) {
		System.out.println("微信退款成功！");
		return 1;
	}

}
