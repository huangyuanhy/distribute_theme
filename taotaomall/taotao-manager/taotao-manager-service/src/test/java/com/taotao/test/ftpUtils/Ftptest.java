package com.taotao.test.ftpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.service.impl.PictureUploadImpl;

public class Ftptest {
	public static void main() throws FileNotFoundException, IOException {
		//初始化容器
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		PictureUploadImpl picService = cxt.getBean(PictureUploadImpl.class);
		byte[] bt=IOUtils.toByteArray(new FileInputStream(new File("G:\\2222.jpg")));
		picService.uploadPicture(bt, "2222.jpg");
	}
}
