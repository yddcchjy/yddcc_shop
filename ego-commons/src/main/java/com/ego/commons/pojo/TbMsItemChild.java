package com.ego.commons.pojo;

import com.ego.pojo.TbMsItem;

public class TbMsItemChild extends TbMsItem{
	private String [] images;
	
	//剩余库存
	private int leftnum;
	
	public int getLeftnum() {
		return leftnum;
	}

	public void setLeftnum(int leftnum) {
		this.leftnum = leftnum;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
	
}
