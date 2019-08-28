package com.ego.ms.pay.service.impl;

import org.springframework.stereotype.Service;

import com.ego.ms.pay.service.YinghangkaPay;
@Service
public class YinghangkaPayImpl implements YinghangkaPay {

	@Override
	public int paywithorder(String tradeserialnumber, int totalFee) {
		System.out.println("银行卡支付成功！");
		return 1;
	}

	@Override
	public int refundwithorder(String tradeserialnumber, int totalFee) {
		System.out.println("银行卡退款成功！");
		return 1;
	}

}
