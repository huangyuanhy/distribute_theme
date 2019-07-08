package com.taotao.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
//图片上传服务
	/**
	 * 
	 * @param bt 文件流转为的字节数组
	 * @param name 文件的源名字
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> uploadPicture(byte[] bt,String name) throws Exception;
}
