package com.ego.ms.pay.service.impl;

import org.springframework.stereotype.Service;

import com.ego.ms.pay.service.ZhifubaoPay;
@Service
public class ZhifubaoPayImpl implements ZhifubaoPay {

	@Override
	public int paywithorder(String tradeserialnumber, int totalFee) {
		System.out.println("支付宝支付成功！");
		return 1;
	}

	@Override
	public int refundwithorder(String tradeserialnumber, int totalFee) {
		System.out.println("支付宝退款成功！");
		return 1;
	}

}
