package com.taotao.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.taotao.service.PictureService;
@Controller
public class FileController {
	@Autowired
	PictureService picService;
	
	
    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String, Object> uploadFile(MultipartFile uploadFile) throws Exception{
    	/**
    	 * MultipartFile 对象不能通过dubbo协议跨节点传输（似乎hessen协议可以，但是要加入hessain协议的包，没测试过）
    	 * 转为字节数组
    	 */
    	byte[] byteArray = IOUtils.toByteArray(uploadFile.getInputStream());
    	return picService.uploadPicture(uploadFile.getBytes(),uploadFile.getOriginalFilename());
    }
    
    // 采用file.Transto 来保存上传的文件
    public String  fileUpload11(@RequestParam("uploadFile") CommonsMultipartFile file) throws IOException {
         long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());
        String path="E:/"+new Date().getTime()+file.getOriginalFilename();
         
        File newFile=new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        long  endTime=System.currentTimeMillis();
        System.out.println("运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "/success"; 
    }
    /**
    *采用spring提供的上传文件的方法
    */
   public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
   {
        long  startTime=System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
       CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
               request.getSession().getServletContext());
       //检查form中是否有enctype="multipart/form-data"
       if(multipartResolver.isMultipart(request))
       {
           //将request变成多部分request
           MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
          //获取multiRequest 中所有的文件名
           Iterator iter=multiRequest.getFileNames();
            
           while(iter.hasNext())
           {
               //一次遍历所有文件
               MultipartFile file=multiRequest.getFile(iter.next().toString());
               if(file!=null)
               {
                   String path="E:/springUpload"+file.getOriginalFilename();
                   //上传
                   file.transferTo(new File(path));
               }
                
           }
          
       }
       long  endTime=System.currentTimeMillis();
       System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
   return "/success"; 
   }
}