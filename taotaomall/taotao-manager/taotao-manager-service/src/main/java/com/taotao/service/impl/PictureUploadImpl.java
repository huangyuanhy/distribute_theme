package com.taotao.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtils;
import com.taotao.common.utils.IDutil;
import com.taotao.service.PictureService;
@Service
public class PictureUploadImpl implements PictureService {
	@Value("${hostName}")
	private String hostName;
	@Value("${port}")
	private int port;
	@Value("${name}")
	private String userName;
	@Value("${userPassWord}")
	private String userPassWord;
	@Value("${basePath}")
	private String basePath;
	//图片服务器的基础路径
	@Value("${imageUrl}")
	private String imageUrl;

	@Override
	public Map<String, Object> uploadPicture(byte[] file,String originName) {
		System.out.println("图片上传服务开始。。。。。。。。。");
		Map<String, Object> res=new HashMap<>();
		//默认添加错误信息
		res.put("error", 1);
		res.put("message", "图片上传失败");
		
		String newName = IDutil.getImageName();
		newName=newName+originName.substring(originName.lastIndexOf("."));
		String endPath = new DateTime().toString("/yyyy/MM/dd");
		//上传图片
		/**
		 * map 封装返回结果
		 * error:1,0  成功0  或是失败 1
		 * url 图片url
		 * message 失败的错误信息
		 * 
		 */
		try {
			FtpUtils ftpUtils = new FtpUtils();
			boolean flag=ftpUtils.uploadFile(hostName, port, userName, userPassWord, 
					endPath, newName, new ByteArrayInputStream(file));
			if (flag) {
				//成功
				res.put("error", 0);
				String url=imageUrl+endPath+"/"+newName;
				res.put("url", url);
				System.out.println("图片上传成功:url"+url);
				 return res;
			}
			 return res;
		} catch (Exception e) {
			res.put("message", String.format("图片上传异常：%s", e.getMessage()));
			return res;
		}
	}
}
