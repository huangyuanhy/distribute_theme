package com.taotao.common.utils;

import java.util.Random;

public class IDutil {
	//生成图片名称
	public static String getImageName() {
		long mills = System.currentTimeMillis();
		//加上三位随机数
		int end=new Random().nextInt(999);
		//如果不足三位，前面补0
		String res=mills+String.format("%03d", end);
		return res;
	}

	public static void main(String[] args) {
		System.out.println(IDutil.getImageName());
	}
	
	
	public static long getItemId() {
		 
			long mills = System.currentTimeMillis();
			//加上2位随机数
			int end=new Random().nextInt(99);
			//如果不足三位，前面补0
			String res=mills+String.format("%02d", end);
			long id=new Long(res);
			return id;
	}
	
}
